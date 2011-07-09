package org.eclipse.symfony.ui.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.internal.text.html.HTMLPrinter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.symfony.index.dao.Route;
import org.eclipse.symfony.ui.SymfonyPluginImages;
import org.eclipse.symfony.ui.SymfonyUiPlugin;
import org.osgi.framework.Bundle;


/**
 * 
 * Convert various ModelElements to a HTML representation
 * for codeassist popups etc. 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings({ "restriction" })
public class HTMLUtils {


	private static String fgStyleSheet;
	
	
	private void foo() {
		
//		StringBuffer buffer = new StringBuffer();
//		boolean hasContents = false;
//		IModelElement element = null;
//		int leadingImageWidth = 20;
//		for (int i = 0; i < elements.length; i++) {
//			element = elements[i];
//			if (element instanceof IMember) {
//				IMember member = (IMember) element;
//				HTMLPrinter.addSmallHeader(buffer,
//						getInfoText(member, constantValue, true, i == 0));
//				Reader reader = null;
//				try {
//					reader = getHTMLContent(member);
//				} catch (ModelException e) {
//				}
//
//				if (reader != null) {
//					HTMLPrinter.addParagraph(buffer, reader);
//				}
//				if (i != elements.length - 1) {
//					buffer.append("<hr>");
//				}
//				hasContents = true;
//			} else if (element.getElementType() == IModelElement.FIELD) {
//				HTMLPrinter.addSmallHeader(buffer,
//						getInfoText(element, constantValue, true, i == 0));
//				hasContents = true;
//			}
//		}
//
//		if (!hasContents)
//			return null;
//
//		if (buffer.length() > 0) {
//			HTMLPrinter.insertPageProlog(buffer, 0, getStyleSheet());
//			HTMLPrinter.addPageEpilog(buffer);
//			return new PHPDocumentationBrowserInformationControlInput(
//					previousInput, element, buffer.toString(),
//					leadingImageWidth);
//		}		
	}

	public static String route2Html (Route route) {

		StringBuffer info = new StringBuffer();
		String styles = getStyleSheet();
		HTMLPrinter.insertPageProlog(info, 0, styles);
		
		URL imageUrl = SymfonyUiPlugin.getDefault().getImagesOnFSRegistry().getImageURL(SymfonyPluginImages.DESC_OBJS_ROUTE);
		
		if (imageUrl != null) {
			
			StringBuffer header = new StringBuffer();
			String imageName = imageUrl.toExternalForm();
			addImageAndLabel(header, imageName, 16, 16, 2, 2, route.getViewPath(), 20, 2, true);
			HTMLPrinter.addSmallHeader(info, header.toString());

		}
		
		
		StringBuffer content = new StringBuffer();

		content.append("<b>Bundle:</b>");
		content.append(route.bundle);
		content.append("<br/><b>Controller:</b> ");
		content.append(route.controller);
		content.append("<br/><b>Action:</b> ");
		content.append(route.action);	

		HTMLPrinter.addParagraph(info, new StringReader(content.toString()));
		HTMLPrinter.addPageEpilog(info);
		
		return info.toString();		

	}
	
	private static String getStyleSheet() {
		
		if (fgStyleSheet == null)
			fgStyleSheet = loadStylesheet();
		String css = fgStyleSheet;
		if (css != null) {
			FontData fontData = JFaceResources.getFontRegistry().getFontData(
					PreferenceConstants.APPEARANCE_DOCUMENTATION_FONT)[0];
			css = HTMLPrinter.convertTopLevelFont(css, fontData);
		}

		return css;		
	}
	
	private static String loadStylesheet() {
		
		Bundle bundle = Platform.getBundle(SymfonyUiPlugin.PLUGIN_ID);
		
		URL styleSheetURL = bundle
				.getEntry("/RouteDocumentationStylesheet.css"); //$NON-NLS-1$
		if (styleSheetURL != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(styleSheetURL
						.openStream()));
				StringBuffer buffer = new StringBuffer(1500);
				String line = reader.readLine();
				while (line != null) {
					buffer.append(line);
					buffer.append('\n');
					line = reader.readLine();
				}
				return buffer.toString();
			} catch (IOException ex) {
				ex.printStackTrace();
				return ""; //$NON-NLS-1$
			} finally {
				try {
					if (reader != null)
						reader.close();
				} catch (IOException e) {
				}
			}
		}
		return null;		
		
	}
	
	private static void addImageAndLabel(StringBuffer buf, String imageName,
			int imageWidth, int imageHeight, int imageLeft, int imageTop,
			String label, int labelLeft, int labelTop, boolean isFirstElement) {

		// workaround to make the window wide enough
	
		label = label + "&nbsp";
		if (imageName != null) {
			StringBuffer imageStyle = new StringBuffer("position: absolute; "); //$NON-NLS-1$
			imageStyle.append("width: ").append(imageWidth).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			imageStyle.append("height: ").append(imageHeight).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			if (isFirstElement) {
				imageStyle.append("top: ").append(imageTop).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
				imageStyle.append("left: ").append(imageLeft).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				imageStyle
						.append("margin-top: ").append(imageTop).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
				imageStyle
						.append("margin-left: ").append(-imageLeft).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			}

			
			buf.append("<img style='").append(imageStyle).append("' src='").append(imageName).append("'/>\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		buf.append("<div style='word-wrap:break-word;"); //$NON-NLS-1$
		if (imageName != null) {
			buf.append("margin-left: ").append(labelLeft).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
			buf.append("margin-top: ").append(labelTop).append("px; "); //$NON-NLS-1$ //$NON-NLS-2$
		}
		buf.append("'>"); //$NON-NLS-1$
		buf.append(label);
		buf.append("</div>"); //$NON-NLS-1$
	}	
	

}
