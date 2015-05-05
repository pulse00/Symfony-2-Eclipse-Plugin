/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package com.dubture.symfony.test.codeassist;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.core.tests.PdttFile;

import com.dubture.symfony.core.model.ISymfonyModelElement;

/**
 * This is an extension of {@link PdttFile} that parses the --EXPECT-- section
 * into expected proposals list. The format of this section must be as follows:
 * 
 * <pre>
 * type(A)
 * method(A)
 * field(A)
 * </pre>
 */
public class CodeAssistPdttFile extends PdttFile {

	static public class ExpectedProposal {
		/** Element name: {@link IModelElement#getElementName()} */
		public String name;
		/** Element type: {@link IModelElement#getElementType()} */
		public int type;
	}

	private ExpectedProposal[] expectedProposals;

	public CodeAssistPdttFile(String fileName) throws Exception {
		super(fileName);
	}

	public ExpectedProposal[] getExpectedProposals() {
		return expectedProposals;
	}

	protected void parse(InputStream stream) throws Exception {
		super.parse(stream, "UTF-8");

		List<ExpectedProposal> expectedProposals = new LinkedList<ExpectedProposal>();
		String[] lines = getExpected().split("\n");
		for (String line : lines) {
			int i = line.indexOf('(');
			int j = line.indexOf(')');
			if (i == -1 || j == -1) { // wrong format
				continue;
			}
			String type = line.substring(0, i);
			ExpectedProposal proposal = new ExpectedProposal();
			proposal.name = line.substring(i + 1, j);
			if ("type".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.TYPE;
			} else if ("method".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.METHOD;
			} else if ("field".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.FIELD;
			} else if ("sourceModule".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.SOURCE_MODULE;
			} else if ("archiveFolder".equalsIgnoreCase(type)) {
				proposal.type = IModelElement.SCRIPT_FOLDER; 
			} else if ("bundle".equalsIgnoreCase(type)) {
				proposal.type = ISymfonyModelElement.BUNDLE;
			} else if ("keyword".equalsIgnoreCase(type)) {
				
			} else { // wrong format
				continue;
			}
			expectedProposals.add(proposal);
		}
		this.expectedProposals = (ExpectedProposal[]) expectedProposals
				.toArray(new ExpectedProposal[expectedProposals.size()]);
	}
}
