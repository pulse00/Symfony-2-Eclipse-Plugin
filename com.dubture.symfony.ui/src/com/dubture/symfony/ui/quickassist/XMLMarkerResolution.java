/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.quickassist;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.resources.SymfonyMarker;
import com.dubture.symfony.ui.utils.DialogUtils;

public class XMLMarkerResolution implements IMarkerResolution2
{

    protected IMarker marker;

    public XMLMarkerResolution(IMarker marker)
    {
        this.marker = marker;
    }

    @Override
    public String getLabel()
    {
        try {
            String label = (String) marker
                    .getAttribute(SymfonyMarker.RESOLUTION_TEXT);
            return label;
        } catch (CoreException e) {
            Logger.logException(e);
        }

        return "Create class ";
    }

    @Override
    public void run(IMarker marker)
    {
        DialogUtils.launchClassWizardFromMarker(marker);
    }

    @Override
    public String getDescription()
    {
        return "Open the 'New PHP Class' dialog";
    }

    @Override
    public Image getImage()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
