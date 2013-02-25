/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.actions;

import java.util.ArrayList;
import java.util.List;

import com.dubture.symfony.core.model.SymfonyModelAccess;
import com.dubture.symfony.index.model.Parameter;

public class InsertParameterHandler extends BaseTextInsertionHandler {
	@Override
	protected List<String[]> getInput() {

		SymfonyModelAccess modelAccess = SymfonyModelAccess.getDefault();
		List<Parameter> parameters = modelAccess.findParameters(project);
		List<String[]> input = new ArrayList<String[]>();

		for (Parameter parameter : parameters) {
			input.add(new String[] { parameter.key, parameter.key });
		}

		return input;
	}

	@Override
	protected String getTitle() {
		return "Select a container parameter to insert";
	}
}
