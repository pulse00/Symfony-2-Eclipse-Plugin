package org.eclipse.symfony.ui.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.internal.text.html.HTMLPrinter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.php.internal.ui.documentation.PHPDocumentationContentAccess;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.symfony.core.log.Logger;
import org.eclipse.symfony.core.model.Service;
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
	
	
	public static String service2Html(Service service) {
		
		StringBuffer info = new StringBuffer();
		String styles = getStyleSheet();
		HTMLPrinter.insertPageProlog(info, 0, styles);
		
		URL imageUrl = SymfonyUiPlugin.getDefault().getImagesOnFSRegistry().getImageURL(SymfonyPluginImages.DESC_OBJS_SERVICE);
		String body = null;
		
		if (imageUrl != null) {
			
			StringBuffer header = new StringBuffer();
			String imageName = imageUrl.toExternalForm();
			
			SourceModule module = (SourceModule) service.getSourceModule();
			String name = service.getElementName();
			
			try {
				
				if (module.getTypes().length > 0) {
					IType type = module.getTypes()[0];					
					if (type.getTypes().length > 0) {
						IType sType = type.getTypes()[0];
						name = sType.getFullyQualifiedName().replace("$", "\\");						
						
						body = PHPDocumentationContentAccess.getHTMLContent(sType);
						
					}
				}
				
				
			} catch (ModelException e) {
				Logger.logException(e);

			}
			addImageAndLabel(header, imageName, 16, 16, 2, 2, name, 20, 2, true);
			HTMLPrinter.addSmallHeader(info, header.toString());

		}
		
		
		StringBuffer content = new StringBuffer();
		

		if (body != null)
			content.append(body);
			
		HTMLPrinter.addParagraph(info, new StringReader(content.toString()));
		HTMLPrinter.addPageEpilog(info);
		
		return info.toString();		
		
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
