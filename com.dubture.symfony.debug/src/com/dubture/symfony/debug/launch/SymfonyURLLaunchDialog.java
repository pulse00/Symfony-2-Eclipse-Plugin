/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.debug.launch;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.dubture.symfony.debug.server.SymfonyServer;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.index.dao.RouteParameter;

/**
 * A Launch dialog to offer the possibility of altering the route parameters of a Launch URL.
 *
 *
 *
 *
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class SymfonyURLLaunchDialog extends MessageDialog {


    private static Set<String> previousURLs = new TreeSet<String>();
    private Combo combo;
    private ILaunchConfigurationWorkingCopy launchConfiguration;
    private Route route;

    public SymfonyURLLaunchDialog(ILaunchConfigurationWorkingCopy launchConfiguration,String title, Route route) {

        super(PHPDebugUIPlugin.getActiveWorkbenchShell(), title, null, "",
                INFORMATION, new String[] { IDialogConstants.OK_LABEL,
                        IDialogConstants.CANCEL_LABEL }, 0);

        this.launchConfiguration = launchConfiguration;
        this.route = route;
        message = "Note that no files will be published to the server.";
    }


    @Override
    protected Control createCustomArea(Composite parent) {

        Group group = new Group(parent, SWT.NONE);
        group.setText("Launch URL");
        group.setLayout(new GridLayout(1, true));
        group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true,
                false));
        combo = new Combo(group, SWT.SINGLE | SWT.BORDER);
        GridData data = new GridData(GridData.FILL, GridData.FILL, true, false,
                1, 1);
        data.widthHint = convertWidthInCharsToPixels(80);
        combo.setLayoutData(data);
        Object[] urls = previousURLs.toArray();
        for (Object element : urls) {
            combo.add(element.toString());
        }
        try {
            String selectedURL = launchConfiguration.getAttribute(
                    Server.BASE_URL, "");
            int comboIndex = combo.indexOf(selectedURL);
            if (comboIndex > -1) {
                combo.select(comboIndex);
            } else {
                combo.add(selectedURL, 0);
                combo.select(0);
            }

            Map<String, RouteParameter> params = route.getParameters();
            if (params.size() == 1) {

                RouteParameter param = params.entrySet().iterator().next().getValue();
                int start = selectedURL.indexOf(param.getName());
                if (start > 0) {
                    Point point = new Point(start, param.getName().length() + start);
                    combo.setSelection(point);

                }
            }
        } catch (CoreException e) {
            Logger.logException(e);
        }

        return parent;
    }

    /**
    * Override the okPressed to save the URL to the URLs history for this PHP
    * IDE session. Also, add the URL to the launch configuration attributes.
    */
    protected void buttonPressed(int buttonId) {
        if (buttonId == OK) {

            String url = combo.getText().trim();
            previousURLs.add(url);
            launchConfiguration.setAttribute(Server.BASE_URL, url);
            launchConfiguration.setAttribute(SymfonyServer.URL, url);

        }
        super.buttonPressed(buttonId);
    }


}
