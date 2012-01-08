package com.dubture.symfony.twig.ui;

import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.sse.core.utils.StringUtils;

import com.dubture.symfony.core.model.ViewPath;

public class ViewpathProposalAdapter extends TextContentAdapter
{

    @Override
    public String getControlContents(Control control) {
        
        return super.getControlContents(control);
//        String content = ((Text) control).getText();
//                
//        ViewPath path = new ViewPath(content);
//        
//        if(path.getTemplate() != null) {
//            content = path.getTemplate();
//        }
//        
//        if (path.getController() != null) {
//            content = path.getController();
//        }
//        
//        content = content.replaceAll(":", "");
//
//        return content;
    }
    
    public int getCursorPosition(Control control) {
        
        int pos = ((Text) control).getCaretPosition();
        return pos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IControlContentAdapter#getInsertionBounds(org.eclipse.swt.widgets.Control)
     */
    public Rectangle getInsertionBounds(Control control) {
        
        Text text = (Text) control;
        Point caretOrigin = text.getCaretLocation();
        System.err.println("get insertion");
        // We fudge the y pixels due to problems with getCaretLocation
        // See https://bugs.eclipse.org/bugs/show_bug.cgi?id=52520
        return new Rectangle(caretOrigin.x + text.getClientArea().x,
                caretOrigin.y + text.getClientArea().y + 3, 1, text.getLineHeight());
    }
}
