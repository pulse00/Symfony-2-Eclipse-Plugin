/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.hyperlink;

import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.internal.filebuffers.FileBuffersPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.internal.Workbench;

import com.dubture.symfony.core.log.Logger;

@SuppressWarnings("restriction")
public class XmlHyperlinkDetector extends AbstractHyperlinkDetector {

	private static String PHP_SOURCE = "org.eclipse.php.core.phpsource";

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {

		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();
		ITextFileBuffer textFileBuffer = FileBuffersPlugin.getFileBufferManager().getTextFileBuffer(document);
		if (textFileBuffer == null || textFileBuffer.getFileStore() == null) {
			return null;
		}

		try {
			if (PHP_SOURCE.equals(textFileBuffer.getContentType().getId())) {
				// PHP Editor is resolved as XML due WTP extension
				return null;
			}

			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(textFileBuffer.getFileStore().toURI(),
					IWorkspaceRoot.INCLUDE_HIDDEN);

			if (files == null || files.length != 1 || files[0].getProject() == null || !files[0].getProject().hasNature(PHPNature.ID)) {
				return null;
			}
			IDLTKSearchScope scope = SearchEngine.createSearchScope(DLTKCore.create(files[0].getProject()));
			IRegion wordRegion = findWord(document, offset);

			if (wordRegion == null)
				return null;

			String path = document.get(wordRegion.getOffset(), wordRegion.getLength());
			PHPModelAccess model = PHPModelAccess.getDefault();

			if (path == null || path.length() == 0) {
				return null;
			}
			IType[] types = model.findTypes(path, MatchRule.EXACT, 0, 0, scope, new NullProgressMonitor());
			IWorkbenchPartSite site = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getSite();

			if (types.length > 10) {
				Logger.debugMSG("Found more than ten (" + types.length + ") types during xml hyperlink detection...");
				return null;
			}
			if (types != null && types.length > 0) {

				IHyperlink[] links = new IHyperlink[types.length];
				for (int i = 0; i < types.length; i++) {
					links[i] = new ModelElementHyperlink(wordRegion, types[i], new OpenAction(site));
				}

				return links;
			}

		} catch (Exception e) {
			Logger.logException(e);
		}

		return null;
	}

	public static IRegion findWord(IDocument document, int offset) {

		int start = -2;
		int end = -1;

		try {

			int pos = offset;
			char c;

			char separator = '>';
			int length = document.getLength();

			// search backwards until a string delimiter
			// to find the start position
			while (pos >= 0) {
				c = document.getChar(pos);

				if (c == '"' || c == ' ' || c == '>') {
					separator = c;
					break;
				}
				--pos;
			}

			start = pos;
			pos++;

			if (separator == '>') {
				separator = '<';
			}

			// search forward until a string delimiter
			// to find the end position
			while (pos < length) {
				c = document.getChar(pos);
				if (c == separator) {
					end = pos;
					break;
				}
				++pos;
			}

			if (start >= 0 && end != 0) {
				start++;

				int rlength = end - start;
				return new Region(start, rlength);
			}

		} catch (BadLocationException x) {
		}

		return null;
	}
}
