package com.dubture.symfony.core.index.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayCreation;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayElement;
import org.eclipse.php.internal.core.compiler.ast.nodes.InfixExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

import com.dubture.symfony.core.log.Logger;

/**
 * 
 * Parses namespace paths from the autoload script.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class RegisterNamespaceVisitor extends PHPASTVisitor {
	

	private ISourceModule source;	
	private IPath path;
	
	private List<IPath> paths = new ArrayList<IPath>();
	
	
	public List<IPath> getNamespaces() {
		
		return paths;
		
	}
	
	public RegisterNamespaceVisitor(ISourceModule sourceModule) {

		source = sourceModule;		
		path = source.getPath().removeLastSegments(1);
		
	}

	@Override
	@SuppressWarnings("unchecked")	
	public boolean visit(PHPCallExpression call) throws Exception {
			
		List<ASTNode> nodes = call.getArgs().getChilds();
		
		if (nodes.size() == 1 && nodes.get(0) instanceof ArrayCreation) {
			
			ArrayCreation array = (ArrayCreation) nodes.get(0);			
			List<ASTNode> args = array.getChilds();
			
			for (ASTNode node : args) {
				
				if (node instanceof ArrayCreation) {
					
					for (Object subNode : ((ArrayCreation) node).getChilds()) {
						
						if (subNode instanceof ArrayElement) {
							resolveNamespace((ArrayElement) subNode);
						}
						
					}
					
				} else if (node instanceof ArrayElement) {
					
					resolveNamespace((ArrayElement) node);

				}
			}						
		}					
		
		return true;
	
	}
	
	private void resolveNamespace(ArrayElement element) {
		
		if (element.getValue() instanceof InfixExpression) {
			
			InfixExpression infix = (InfixExpression) element.getValue();
			
			try {

				if (!(infix.getLeft() instanceof Scalar) || !(infix.getRight() instanceof Scalar)) {
					return;
				}
				
				Scalar left = (Scalar) infix.getLeft();				
				Scalar right = (Scalar) infix.getRight();
				
				if ("__DIR__".equals(left.getValue())) {
										
					String rightPath = right.getValue().replace("'", "").replace("\"", "").replaceFirst("/", "");
					paths.add(path.append(rightPath));
					
				}
								
			} catch (Exception e) {
				Logger.logException(e);
			}								
		}
	}
}