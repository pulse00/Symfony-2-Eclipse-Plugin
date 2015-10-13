package com.dubture.symfony.core.refactoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.core.text.TextSearchScope;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEditGroup;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.index.SymfonyIndexer;
import com.dubture.symfony.index.handler.IServiceHandler;

public class RenameServiceClass extends RenameParticipant {

	private IType type;
	
	public RenameServiceClass() {
	}

	@Override
	protected boolean initialize(Object element) {
		if (element instanceof IType) {
			type = (IType) element;
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "Rename Service Class";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (type.getScriptProject() == null) {
			return null;
		}
		
		try {
			final List<IResource> resources = new ArrayList<IResource>();
			SymfonyIndexer.getInstance().findServicesByClassName(PHPModelUtils.getFullName(type), type.getScriptProject().getProject().getFullPath().toString(), new IServiceHandler() {
				
				@Override
				public void handle(String id, String phpClass, String path, String _public, String tags) {
					try {
						IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
						if (file.exists()) {
							resources.add(file);
						}
					} catch (Exception e) {
						// ignore if not exists
					}
				}
			});
			if (resources.isEmpty()) {
				return null;
			}
			resources.add(type.getScriptProject().getProject().getFolder("app/config")); // search by default
			List<Bundle> findBundles = SymfonyModelAccess.getDefault().findBundles(type.getScriptProject());
			for (Bundle bundle : findBundles) {
				resources.add(ResourcesPlugin.getWorkspace().getRoot().getFolder(bundle.getPath().append("Resources").append("config")));
			}
			TextSearchScope searchScope = TextSearchScope.newSearchScope(resources.toArray(new IResource[resources.size()]), Pattern.compile(".*"), true);
			Pattern search = TextSearchEngine.createPattern(PHPModelUtils.getFullName(type), false, false);
			
			final String newName = createNewName();
		
			final HashMap<IFile, Change> changes= new HashMap<IFile, Change>();
			TextSearchRequestor collector= new TextSearchRequestor() {
				public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
					IFile file= matchAccess.getFile();
					if (matchAccess.getMatchOffset() == 0) {
						return true;
					}
					char fileContentChar = matchAccess.getFileContentChar(matchAccess.getMatchOffset()-1);
					if (!(Character.isWhitespace(fileContentChar) || fileContentChar == '\'' || fileContentChar == '"' || fileContentChar == ':' || fileContentChar == '>')) {
						return false;
					}
					int end = matchAccess.getMatchOffset()+matchAccess.getMatchLength();
					if (end < matchAccess.getFileContentLength()) {
						fileContentChar = matchAccess.getFileContentChar(end);
						if (!(Character.isWhitespace(fileContentChar) || fileContentChar == '\'' || fileContentChar == '"' || fileContentChar == '<')) {
							return false;
						}
					}
					
					TextFileChange change= (TextFileChange) changes.get(file);
					if (change == null) {
						TextChange textChange= getTextChange(file); // an other participant already modified that file?
						if (textChange != null) {
							return false; // don't try to merge changes
						}
						change= new TextFileChange(file.getName(), file);
						change.setEdit(new MultiTextEdit());
						changes.put(file, change);
					}
					ReplaceEdit edit= new ReplaceEdit(matchAccess.getMatchOffset(), matchAccess.getMatchLength(), newName);
					change.addEdit(edit);
					change.addTextEditGroup(new TextEditGroup("Update service reference", edit)); //$NON-NLS-1$
					return true;
				}
			};
			TextSearchEngine.create().search(searchScope, collector, search, null);
			
			
			if (changes.isEmpty()) {
				return null;
			}
			CompositeChange result= new CompositeChange("Rename service class");
			result.addAll(changes.values().toArray(new Change[changes.size()]));
			return result;
		} catch (Exception e) {
			Logger.logException(e);
		}
		return null;
	}

	private String createNewName() throws ModelException {
		StringBuilder newName = new StringBuilder();
		if (type.getParent() instanceof IType) {
			newName.append(type.getParent().getElementName());
			newName.append("\\");
		}
		newName.append(getArguments().getNewName());
		return newName.toString();
	}

}
