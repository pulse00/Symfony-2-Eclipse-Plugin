package com.dubture.symfony.ui.contentassist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.ast.nodes.AST;
import org.eclipse.php.internal.core.ast.nodes.ASTParser;
import org.eclipse.php.internal.core.ast.nodes.Block;
import org.eclipse.php.internal.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.internal.core.ast.nodes.MethodDeclaration;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.texteditor.ITextEditor;

@SuppressWarnings("restriction")
public class InterfaceMethodCompletionProposal extends PHPCompletionProposal {

	public InterfaceMethodCompletionProposal(String replacementString,
			int replacementOffset, int replacementLength, Image image,
			String displayString, int relevance) {
		super(replacementString, replacementOffset, replacementLength, image,
				displayString, relevance);

	}
	
	
	@Override
	public void apply(IDocument document, char trigger, int offset) {

		System.err.println("apply");
		super.apply(document, trigger, offset);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void apply(ITextViewer viewer, char trigger, int stateMask,
			int offset) {

		IDocument document = viewer.getDocument();
		ITextEditor textEditor = ((PHPStructuredTextViewer) viewer)
				.getTextEditor();

		if (textEditor instanceof PHPStructuredEditor) {
			IModelElement editorElement = ((PHPStructuredEditor) textEditor)
					.getModelElement();
			if (editorElement != null) {
				ISourceModule sourceModule = ((ModelElement) editorElement)
						.getSourceModule();
				
				try {
					ASTParser parser = ASTParser
							.newParser(sourceModule);
					parser.setSource(document.get().toCharArray());
					Program program = parser.createAST(null);
					
					program.recordModifications();
					AST ast = program.getAST();
					
					List<org.eclipse.php.internal.core.ast.nodes.FormalParameter> params = new ArrayList<org.eclipse.php.internal.core.ast.nodes.FormalParameter>();
					Block block = ast.newBlock();

					FunctionDeclaration function = ast.newFunctionDeclaration(ast.newIdentifier("someFunction"), params, block, false);
					MethodDeclaration method = ast.newMethodDeclaration(Modifiers.AccPublic, function);
					
					program.statements().add(method);
					Map options = new HashMap(PHPCorePlugin.getOptions());
					
					TextEdit edits = program.rewrite(document, options);
					edits.apply(document);
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}