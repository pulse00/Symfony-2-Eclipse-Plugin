package com.dubture.symfony.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.WorkingCopyOwner;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.eclipse.php.core.tests.filenetwork.FileUtil;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.IPHPCompletionRequestorExtension;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.strategies.CompletionStrategyFactory;
import org.eclipse.php.internal.core.facet.PHPFacets;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.osgi.framework.Bundle;

import com.dubture.doctrine.core.DoctrineNature;
import com.dubture.doctrine.core.codeassist.DoctrineCompletionContextResolver;
import com.dubture.doctrine.core.codeassist.DoctrineCompletionStrategyFactory;
import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.SymfonyVersion;
import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.codeassist.SymfonyCompletionContextResolver;
import com.dubture.symfony.core.codeassist.SymfonyCompletionStrategyFactory;
import com.dubture.symfony.core.facet.FacetManager;
import com.dubture.symfony.core.preferences.CorePreferenceConstants.Keys;
import com.dubture.symfony.test.codeassist.CodeAssistPdttFile;
import com.dubture.symfony.test.codeassist.CodeAssistPdttFile.ExpectedProposal;

@SuppressWarnings("restriction")
abstract public class AbstractCodeAssistTest extends TestCase {

	protected boolean displayName = false;
	protected String endChar = ",";

	// infos for invalid results
	protected int tabs = 2;

	// working copies usage
	protected ISourceModule[] workingCopies;
	protected WorkingCopyOwner wcOwner;
	protected boolean discard;
	protected String bundleName = "com.dubture.symfony.test";
	protected final String projectName;
	protected final char OFFSET_CHAR = '|';

	protected IProject project;
	protected IFile testFile;

	public void setUp() throws Exception {

		if (this.discard) {
			this.workingCopies = null;
		}

		this.discard = true;

		if (project != null && project.exists()) {
			return;
		}

		project = setUpProject(projectName);

		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] { PHPNature.ID, SymfonyNature.NATURE_ID, DoctrineNature.NATURE_ID });
		project.setDescription(desc, null);

		ProjectOptions.setPhpVersion(PHPVersion.PHP5_3, project);

		PHPFacets.setFacetedVersion(project, PHPVersion.PHP5_3);
		FacetManager.installFacets(project, PHPVersion.PHP5_3, SymfonyVersion.Symfony2_2_1, new NullProgressMonitor());

		IEclipsePreferences node = new ProjectScope(project).getNode(SymfonyCorePlugin.ID);
		node.put(Keys.DUMPED_CONTAINER, "dumpedContainer.xml");
		node.flush();

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		SymfonyTestPlugin.waitForIndexer();

	}

	public void tearDown() throws Exception {

		if (this.discard && this.workingCopies != null) {
			discardWorkingCopies(this.workingCopies);
			this.wcOwner = null;
		}

		if (testFile != null) {
			testFile.delete(true, null);
			testFile = null;
		}

		for (IResource res : project.members()) {
			if (res.getName().startsWith(".")) {
				continue;
			}
			res.delete(true, null);
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.close(null);
		project.delete(true, true, null);
		project = null;
	}

	public AbstractCodeAssistTest(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Creates test file with the specified content and calculates the offset at
	 * OFFSET_CHAR. Offset character itself is stripped off.
	 *
	 * @param data
	 *            File data
	 * @return offset where's the offset character set.
	 * @throws Exception
	 */
	protected int createFile(String data) throws Exception {
		int offset = data.lastIndexOf(OFFSET_CHAR);
		if (offset == -1) {
			throw new IllegalArgumentException("Offset character is not set");
		}

		// replace the offset character
		data = data.substring(0, offset) + data.substring(offset + 1);

		testFile = project.getFile(UUID.randomUUID().toString() + ".php");
		testFile.create(new ByteArrayInputStream(data.getBytes()), true, null);
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		SymfonyTestPlugin.waitForIndexer();

		return offset;
	}

	protected ISourceModule getSourceModule() {
		return DLTKCore.createSourceModuleFrom(testFile);
	}

	public CompletionProposal[] getProposals(String data) throws Exception {
		int offset = createFile(data);
		return getProposals(offset);
	}

	public CompletionProposal[] getProposals(int offset) throws ModelException {
		return getProposals(getSourceModule(), offset);
	}

	abstract class TestCompletionRequestor extends CompletionRequestor implements IPHPCompletionRequestorExtension {

		@Override
		public ICompletionContextResolver[] getContextResolvers() {
			List<ICompletionContextResolver> asList = new ArrayList<ICompletionContextResolver>(Arrays.asList(CompletionContextResolver.getActive()));
			asList.add(new DoctrineCompletionContextResolver());
			asList.add(new SymfonyCompletionContextResolver());
			return asList.toArray(new ICompletionContextResolver[asList.size()]);
		}

		@Override
		public ICompletionStrategyFactory[] getStrategyFactories() {
			List<ICompletionStrategyFactory> asList = new ArrayList<ICompletionStrategyFactory>(Arrays.asList(CompletionStrategyFactory.getActive()));
			asList.add(new DoctrineCompletionStrategyFactory());
			asList.add(new SymfonyCompletionStrategyFactory());
			return asList.toArray(new ICompletionStrategyFactory[asList.size()]);
		}

	}

	public CompletionProposal[] getProposals(ISourceModule sourceModule, int offset) throws ModelException {
		final List<CompletionProposal> proposals = new LinkedList<CompletionProposal>();
		sourceModule.codeComplete(offset, new TestCompletionRequestor() {
			public void accept(CompletionProposal proposal) {
				proposals.add(proposal);
			}
		});

		return (CompletionProposal[]) proposals.toArray(new CompletionProposal[proposals.size()]);
	}

	public void compareProposals(CompletionProposal[] proposals, CodeAssistPdttFile pdttFile) throws Exception {

		ExpectedProposal[] expectedProposals = pdttFile.getExpectedProposals();

		boolean proposalsEqual = true;
		if (proposals.length == expectedProposals.length) {
			for (ExpectedProposal expectedProposal : pdttFile.getExpectedProposals()) {
				boolean found = false;
				for (CompletionProposal proposal : proposals) {
					IModelElement modelElement = proposal.getModelElement();

					if (modelElement == null) {
						if (new String(proposal.getName().trim()).equalsIgnoreCase(expectedProposal.name)) { // keyword
							found = true;
							break;
						}
					} else if (modelElement.getElementType() == expectedProposal.type) {
						if (modelElement instanceof AliasType) {
							if (((AliasType) modelElement).getAlias().trim().equals(expectedProposal.name)) {
								found = true;
								break;
							}
						} else if ((modelElement instanceof FakeConstructor) && (modelElement.getParent() instanceof AliasType)) {
							if (((AliasType) modelElement.getParent()).getAlias().trim().equals(expectedProposal.name)) {
								found = true;
								break;
							}
						} else {
							if (modelElement.getElementName().trim().equalsIgnoreCase(expectedProposal.name)) {
								found = true;
								break;
							}
						}
					} else if (modelElement.getElementType() == expectedProposal.type
							&& new String(proposal.getName()).trim().equalsIgnoreCase(expectedProposal.name)) {
						// for phar include
						found = true;
						break;
					}
				}
				if (!found) {
					proposalsEqual = false;
					break;
				}
			}
		} else {
			proposalsEqual = false;
		}

		if (!proposalsEqual) {
			StringBuilder errorBuf = new StringBuilder();
			errorBuf.append("\nEXPECTED COMPLETIONS LIST:\n-----------------------------\n");
			errorBuf.append(pdttFile.getExpected());
			errorBuf.append("\nACTUAL COMPLETIONS LIST:\n-----------------------------\n");
			for (CompletionProposal p : proposals) {
				IModelElement modelElement = p.getModelElement();
				if (modelElement == null || modelElement.getElementName() == null) {
					errorBuf.append("keyword(").append(p.getName()).append(")\n");
				} else {
					switch (modelElement.getElementType()) {
					case IModelElement.FIELD:
						errorBuf.append("field");
						break;
					case IModelElement.METHOD:
						errorBuf.append("method");
						break;
					case IModelElement.TYPE:
						errorBuf.append("type");
						break;
					}
					if (modelElement instanceof AliasType) {
						errorBuf.append('(').append(((AliasType) modelElement).getAlias()).append(")\n");
					} else {
						errorBuf.append('(').append(modelElement.getElementName()).append(")\n");
					}
				}
			}
			fail(errorBuf.toString());
		}
	}

	protected String[] getPDTTFiles(String testsDirectory) {
		return getPDTTFiles(testsDirectory, Platform.getBundle(SymfonyTestPlugin.PLUGIN_ID));
	}

	protected String[] getPDTTFiles(String testsDirectory, Bundle bundle) {
		return getFiles(testsDirectory, bundle, ".pdtt");
	}

	protected String[] getFiles(String testsDirectory, String ext) {
		return getFiles(testsDirectory, Platform.getBundle(SymfonyTestPlugin.PLUGIN_ID), ext);
	}

	protected String[] getFiles(String testsDirectory, Bundle bundle, String ext) {
		List<String> files = new LinkedList<String>();
		Enumeration<String> entryPaths = bundle.getEntryPaths(testsDirectory);
		if (entryPaths != null) {
			while (entryPaths.hasMoreElements()) {
				final String path = (String) entryPaths.nextElement();
				URL entry = bundle.getEntry(path);
				// check whether the file is readable:
				try {
					entry.openStream().close();
				} catch (Exception e) {
					continue;
				}
				int pos = path.lastIndexOf('/');
				final String name = (pos >= 0 ? path.substring(pos + 1) : path);
				if (!name.endsWith(ext)) { // check fhe file extention
					continue;
				}
				files.add(path);
			}
		}
		return (String[]) files.toArray(new String[files.size()]);
	}

	protected void assertContents(String expected, String actual) {
		String diff = SymfonyTestPlugin.compareContents(expected, actual);
		if (diff != null) {
			fail(diff);
		}
	}

	public IProject setUpProject(final String projectName) throws CoreException, IOException {
		return setUpProjectTo(projectName, projectName);
	}

	protected IProject setUpProjectTo(final String projectName, final String fromName) throws CoreException, IOException {
		// copy files in project from source workspace to target workspace
		final File sourceWorkspacePath = getSourceWorkspacePath();
		final File targetWorkspacePath = getWorkspaceRoot().getLocation().toFile();
		final File source = new File(sourceWorkspacePath, fromName);
		if (!source.isDirectory()) {
			throw new IllegalArgumentException(NLS.bind("Source directory \"{0}\" doesn't exist", source));
		}
		copyDirectory(source, new File(targetWorkspacePath, projectName));
		return createProject(projectName);
	}

	public File getSourceWorkspacePath() {
		return new File(getPluginDirectoryPath(), "workspace");
	}

	protected File getPluginDirectoryPath() {
		try {
			final Bundle bundle = Platform.getBundle(this.bundleName);
			if (bundle == null) {
				throw new IllegalStateException(NLS.bind("Bundle \"{0}\" with test data not found", bundleName));
			}
			URL platformURL = bundle.getEntry("/");
			return new File(FileLocator.toFileURL(platformURL).getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void copyDirectory(File source, File target) throws IOException {
		FileUtil.copyDirectory(source, target);
	}

	public IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public IWorkspaceRoot getWorkspaceRoot() {
		return getWorkspace().getRoot();
	}

	public IProject getProject(String project) {
		return getWorkspaceRoot().getProject(project);
	}

	protected IProject createProject(final String projectName) throws CoreException {
		final IProject project = getProject(projectName);
		IWorkspaceRunnable create = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				project.create(null);
				project.open(null);
			}
		};
		getWorkspace().run(create, null);
		return project;
	}

	protected void discardWorkingCopies(ISourceModule[] units) throws ModelException {
		if (units == null)
			return;
		for (int i = 0, length = units.length; i < length; i++)
			if (units[i] != null)
				units[i].discardWorkingCopy();
	}

	protected void runPdttTest(String filename) throws Exception {
		File projectFile = new File(getSourceWorkspacePath().toString(), projectName);
		IPath path = new Path(projectFile.getAbsolutePath());
		final CodeAssistPdttFile pdttFile = new CodeAssistPdttFile(path.append(filename).toOSString());
		CompletionProposal[] proposals = getProposals(pdttFile.getFile());
		compareProposals(proposals, pdttFile);
	}
}
