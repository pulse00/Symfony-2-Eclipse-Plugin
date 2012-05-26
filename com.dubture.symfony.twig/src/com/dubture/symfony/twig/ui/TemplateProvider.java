package com.dubture.symfony.twig.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptFolder;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.dubture.symfony.core.builder.SymfonyNature;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.ViewPath;
import com.dubture.twig.core.log.Logger;
import com.dubture.twig.core.parser.SourceParserUtil;
import com.dubture.twig.core.parser.ast.node.BlockStatement;
import com.dubture.twig.core.parser.ast.node.TwigModuleDeclaration;
import com.dubture.twig.ui.wizards.ITemplateProvider;

/**
 * 
 * 
 * Provides the "New twig template" dialog with a parent/block choice.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class TemplateProvider implements ITemplateProvider
{

    private SymfonyModelAccess model = SymfonyModelAccess.getDefault();
    private Text parentTemplate;
    private IScriptProject scriptProject;
    private CheckboxTableViewer blockTable;    
    private List<String> blocks = new ArrayList<String>();    
    private List<String> checkedBlocks = new LinkedList<String>();
    private String parent = "";
    
    // make sure to inject the dialog only in Symfony projects
    private boolean isValid = true;
    
    
    @Override
    public void createContentControls(IScriptFolder folder, Composite container)
    {
        
        initialize(folder);
        
        if (!isValid) {
            return;
        }
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        gd.heightHint = 20;

        Label separator = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(gd);

        Label parentLabel = new Label(container, SWT.NONE);
        parentLabel.setText("Parent template:");

        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.widthHint = 200;

        parentTemplate = new Text(container, SWT.BORDER | SWT.SINGLE);
        parentTemplate.setLayoutData(gd);
        
        ControlDecoration dec = new ControlDecoration(parentTemplate, SWT.TOP | SWT.LEFT);        
        FieldDecoration indicator = FieldDecorationRegistry.getDefault().
                getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);
                
        dec.setImage(indicator.getImage());
        dec.setDescriptionText(indicator.getDescription() + "(Ctrl+Space)"); 
        dec.setShowOnlyOnFocus(true);
        
        Label filler = new Label(container, SWT.NONE);        
        filler.setVisible(false);
        
        gd = new GridData();
        gd.verticalAlignment = SWT.TOP;
        
        Label blockLabel = new Label(container, SWT.NONE);
        blockLabel.setText("Override blocks:");
        blockLabel.setLayoutData(gd);
        
        GridData gridData = new GridData();
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;       
        
        blockTable = CheckboxTableViewer.newCheckList(container, SWT.H_SCROLL | SWT.V_SCROLL  | SWT.BORDER);
        blockTable.addCheckStateListener(new ICheckStateListener()
        {            
            @Override
            public void checkStateChanged(CheckStateChangedEvent event)
            {
                if (event.getChecked()) {
                    if (!checkedBlocks.contains(event.getElement())) {
                        checkedBlocks.add((String) event.getElement());
                    }
                } else {
                    checkedBlocks.remove(event.getElement());
                }                
            }
        });
        
        blockTable.setContentProvider(ArrayContentProvider.getInstance());
        blockTable.setInput(blocks);
        blockTable.getControl().setLayoutData(gridData);
        
        setupAutocomplete();

    }
    
    private void initialize(IScriptFolder folder)
    {
        try {            
            isValid = true;            
            
            if (folder == null) {
                isValid = false;
            }
            
            scriptProject = folder.getScriptProject();            
            
            if (!scriptProject.getProject().hasNature(SymfonyNature.NATURE_ID)) {
                isValid = false;
            }
            
        } catch (CoreException e) {
            Logger.logException(e);
            isValid = false;
        }
    }

    private void setupAutocomplete()
    {

        List<String> proposals = new LinkedList<String>();
        List<Bundle> bundles = model.findBundles(scriptProject);

        for (Bundle bundle : bundles) {

            IType[] controllers = model.findBundleControllers(
                    bundle.getElementName(), scriptProject);

            if (controllers == null) {
                continue;
            }

            IModelElement[] rootTemplates = model.findBundleRootTemplates(
                    bundle.getElementName(), scriptProject);

            for (IModelElement tpl : rootTemplates) {
                String path = String.format("%s::%s", bundle.getElementName(),
                        tpl.getElementName());
                proposals.add(path);
            }

            for (IType controller : controllers) {
                IModelElement[] templates = model.findTemplates(
                        bundle.getElementName(), controller.getElementName(),
                        scriptProject);

                for (IModelElement template : templates) {
                    String path = String.format("%s:%s:%s", bundle
                            .getElementName(), controller.getElementName()
                            .replace("Controller", ""), template
                            .getElementName());
                    proposals.add(path);
                }
            }
        }
        
        try {
            
            KeyStroke keyStroke = KeyStroke.getInstance("Ctrl+Space");
            SimpleContentProposalProvider provider = new SimpleContentProposalProvider(
                    (String[]) proposals.toArray(new String[proposals.size()]));
            provider.setFiltering(true);
            
            ContentProposalAdapter contentProposalAdapter = new ContentProposalAdapter(parentTemplate, new ViewpathProposalAdapter(),
                    provider, keyStroke, null);
            
            contentProposalAdapter.setFilterStyle(ContentProposalAdapter.FILTER_CHARACTER);
            contentProposalAdapter.setAutoActivationDelay(10);
            contentProposalAdapter.setPropagateKeys(true);
            
            contentProposalAdapter.addContentProposalListener(new IContentProposalListener()
            {
                
                @Override
                public void proposalAccepted(IContentProposal proposal)
                {
                    parent = proposal.getContent();                    
                    IModelElement template = model.findTemplate(new ViewPath(parent), scriptProject);
                    
                    if (template != null && template instanceof SourceModule) {
                        
                        try {
                            
                            SourceModule module = (SourceModule) template;
                            TwigModuleDeclaration twig = (TwigModuleDeclaration) SourceParserUtil.parseSourceModule(module);
                            
                            blocks = new LinkedList<String>();
                            if (twig != null) {
                                for (BlockStatement block : twig.getBlocks()) {
                                    
                                    if (block.isBlock()) {
                                        blocks.add(block.getBlockName().getValue());
                                    }
                                }
                                blockTable.setInput(blocks);
                                checkedBlocks = new LinkedList<String>();
                            }
                        } catch (Exception e) {
                            Logger.logException(e);
                        }
                    }
                }
            });
            
        } catch (ParseException e) {
            Logger.logException(e);
        }

    }

    @Override
    public String getContents()
    {        
        if (!isValid || parent == null || parent.length() == 0) {
            return "";
        }
        
        String delim = System.getProperty("line.separator");
        String content = String.format("{%% extends '%s' %%}%s%s", parent, delim, delim);
        
        for (String block : checkedBlocks) {
            content += String.format("{%% block %s %%}%s%s{%% endblock %%}%s%s", block, delim, delim, delim, delim);
        }
        
        return content;
    }
}
