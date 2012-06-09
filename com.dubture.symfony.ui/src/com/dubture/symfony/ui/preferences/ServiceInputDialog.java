/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.preferences;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.php.internal.ui.wizards.fields.DialogField;
import org.eclipse.php.internal.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.internal.ui.wizards.fields.LayoutUtil;
import org.eclipse.php.internal.ui.wizards.fields.StringDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


@SuppressWarnings("restriction")
public class ServiceInputDialog extends StatusDialog {


    private StringDialogField fNameDialogField;
    private StringDialogField fClassDialogField;

    final private SyntheticService service;

    public ServiceInputDialog(Shell parent, SyntheticService s) {
        super(parent);

        this.service = s;

        String name = "";
        String className  = "";

        if (service != null) {

            name = service.name != null ? service.name : ""; //$NON-NLS-N$
            className = service.className != null ? service.className : ""; //$NON-NLS-N$

        }

        fNameDialogField = new StringDialogField();
        fNameDialogField
                .setLabelText("Service name");
        fNameDialogField.setDialogFieldListener(new IDialogFieldListener() {
            @Override
            public void dialogFieldChanged(DialogField field) {
                //TODO: validate
            }
        });

        fNameDialogField.setText(name);

        fClassDialogField = new StringDialogField();
        fClassDialogField.setLabelText("Service class");
        fClassDialogField.setText(className);
        fClassDialogField.setDialogFieldListener(new IDialogFieldListener() {

            @Override
            public void dialogFieldChanged(DialogField field) {

                //TODO: validate

            }
        });

    }


    @Override
    protected Control createDialogArea(Composite parent) {

        Composite composite = (Composite) super.createDialogArea(parent);

        Composite inner = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.numColumns = 2;
        inner.setLayout(layout);

        fNameDialogField.doFillIntoGrid(inner, 2);
        fClassDialogField.doFillIntoGrid(inner, 2);

        LayoutUtil.setHorizontalGrabbing(fNameDialogField.getTextControl(null));
        LayoutUtil.setWidthHint(fNameDialogField.getTextControl(null),
                convertWidthInCharsToPixels(45));

        fNameDialogField.postSetFocusOnDialogField(parent.getDisplay());

        applyDialogFont(composite);
        return composite;

    }

    public SyntheticService getResult() {

        SyntheticService service = new SyntheticService();

        service.name = fNameDialogField.getText();
        service.className = fClassDialogField.getText();
        return service;


    }
}
