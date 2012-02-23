/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.annotation.parser.antlr.error;

import org.antlr.runtime.RecognitionException;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.builder.IBuildContext;

import com.dubture.symfony.annotation.parser.antlr.AnnotationLexer;
import com.dubture.symfony.annotation.parser.antlr.AnnotationParser;
//import com.dubture.symfony.core.preferences.SymfonyCorePreferences;

/**
 *
 * The {@link AnnotationErrorReporter} gets called
 * by {@link AnnotationLexer} and {@link AnnotationParser}
 * when an error is encountered during lexing/parsing.
 * 
 * It reports errors to the {@link IProblemReporter} retrieved
 * from the {@link IBuildContext};
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class AnnotationErrorReporter implements IAnnotationErrorReporter {

	private IBuildContext context;	
	private int sourceStart = 0;
	private int lineNo = 1;

	public AnnotationErrorReporter(IBuildContext context, int sourceStart) {

		this.context = context;
		this.sourceStart = sourceStart;

	}


	@Override
	public void reportError(String header, String message, RecognitionException e) {

		if (context == null || e.token == null)  {
			return;
		}

		// calculate the correct line number
		if(lineNo == 1)
		{
			char[] content = context.getContents();
			for(int i=0; i < sourceStart; i++)
			{
				if(content[i] == '\n')
				{
					lineNo++;
				}
			}
		}
		
//		String filename = context.getFile().getName();
//		int start = sourceStart+e.token.getCharPositionInLine();
//		int end = start+e.token.getText().length();
//		
//		// retrieve severity from preferences
//		ProblemSeverity severity = SymfonyCorePreferences.getAnnotationSeverity();
//		
//		IProblem problem = new DefaultProblem(filename, message, IProblem.Syntax,
//				new String[0], severity, start+1, end+1, lineNo);
//		
//		
//		context.getProblemReporter().reportProblem(problem);

	}

	public IBuildContext getContext() {
		return context;
	}
}
