/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IType;

import com.dubture.doctrine.core.goals.IEntityResolver;
import com.dubture.symfony.core.model.EntityAlias;
import com.dubture.symfony.core.model.SymfonyModelAccess;

public class SymfonyEntityResolver implements IEntityResolver {

	@Override
	public IType resolve(String entity, IScriptProject project) {

		EntityAlias alias = new EntityAlias(entity);
		
		if (alias == null || alias.getBundleAlias() == null) {
			return null;
		}
		
		return SymfonyModelAccess.getDefault().findEntity(alias, project);
	}
}
