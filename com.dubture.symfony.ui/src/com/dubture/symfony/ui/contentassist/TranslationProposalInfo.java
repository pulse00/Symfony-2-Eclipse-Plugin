/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.contentassist;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ui.text.completion.MemberProposalInfo;

import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.core.model.Translation;
import com.dubture.symfony.index.model.TransUnit;
import com.dubture.symfony.ui.utils.HTMLUtils;

public class TranslationProposalInfo extends MemberProposalInfo {

	public TranslationProposalInfo(IScriptProject project,
			CompletionProposal proposal) {
		super(project, proposal);

	}
	
	@Override
	public String getInfo(IProgressMonitor monitor) {
 
		try {
			
			Translation translation = (Translation) getModelElement();			
			
			SymfonyModelAccess model = SymfonyModelAccess.getDefault();
			List<TransUnit> units = model.findTranslations(translation);
			
			String html = HTMLUtils.translation2Html(translation, units);
			
			if (html != null && html.length() > 0)
				return html;
			
			return translation.getElementName();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}	
	

}
