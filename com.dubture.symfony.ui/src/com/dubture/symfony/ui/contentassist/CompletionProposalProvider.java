package com.dubture.symfony.ui.contentassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.jface.resource.ImageDescriptor;

import com.dubture.doctrine.core.model.Entity;
import com.dubture.symfony.core.model.Bundle;
import com.dubture.symfony.core.model.Controller;
import com.dubture.symfony.core.model.RouteSource;
import com.dubture.symfony.core.model.Service;
import com.dubture.symfony.core.model.Template;
import com.dubture.symfony.core.model.Translation;
import com.dubture.symfony.ui.SymfonyPluginImages;

public class CompletionProposalProvider 
{

    public static ProposalInfo createScriptCompletionProposal(
            CompletionProposal proposal, ScriptCompletionProposalCollector collector)
    {
        
        IScriptProject project = collector.getScriptProject();
        IModelElement element = proposal.getModelElement();

        if (element == null) {
            return null;
        }
        
        if (element.getClass() == RouteSource.class) {
            return new RouteProposalInfo(project, proposal);
        } else if (element.getClass() == Service.class) {           
            return new ServiceProposalInfo(project, proposal);
        } else if (element.getClass() == Bundle.class) {
            return new BundleProposalInfo(project, proposal);
        } else if (element.getClass() == Controller.class) {
            return new ControllerProposalInfo(project, proposal);
        } else if (element.getClass() == Template.class) {          
            return new TemplateProposalInfo(project, proposal);            
        } else if (element.getClass() == Entity.class) {
            return new EntityProposalInfo(project, proposal);
        } else if (element.getClass() == Translation.class) {
            return new TranslationProposalInfo(project, proposal);
        }
        
        return null;
    }
    
    public static ImageDescriptor createTypeImageDescriptor(CompletionProposal proposal)
    {
        IModelElement element = proposal.getModelElement();
        
        if (element.getClass() == RouteSource.class) {
            
            return SymfonyPluginImages.DESC_OBJS_ROUTE;

        } else if (element.getClass() == Service.class) {
            
            return SymfonyPluginImages.DESC_OBJS_SERVICE;
            
        } else if (element.getClass() == Bundle.class) {
            
            return SymfonyPluginImages.DESC_OBJS_BUNDLE;
            
        } else if (element.getClass() == Controller.class) {
            
            return SymfonyPluginImages.DESC_OBJS_CONTROLLER;
            
        } else if (element.getClass() == Template.class) {
            
            return SymfonyPluginImages.DESC_OBJS_TEMPLATE;
        }
        
        return null;
    }
}
