package com.dubture.symfony.core.model;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;


/**
 * 
 * The Service class represents a Symfony2 Service 
 * retrievable from the DependencyInjection container.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("restriction")
public class Service extends SourceType {
	
	
	public static final String NAME = "name";
	public static final String CLASS = "class";
	public static final Object SYNTHETIC = "synthetic";
	
	
	
	private IFile file;
	
	/**
	 * The fully qualified class name.
	 */
	private String fqcn;
	

	/***
	 * The namespace only.
	 */
	private String namespace;
	
	private Scalar scalar;	
	
	/**
	 * The name of the PHP class
	 */
	private String className;
	

	private String id;
	


	private IPath path;
	
	public Service(ModelElement parent, String name) {
		super(parent, name);
	}
	
	
	public Service(IFile resource, String id, String clazz) {
		
		super(null, id);
		
		file = resource;
		setFqcn(clazz);
		this.id = id;
		
		int lastPart = clazz.lastIndexOf("\\");
				
		if (lastPart == -1) {			
			namespace = "";
			className = clazz;
		} else {
			namespace = clazz.substring(0,lastPart);
			className = clazz.substring(lastPart + 1);
		}
	}

	public Service(String id, String phpClass, String path, Scalar scalar) {

		super(null, id);
		this.namespace = PHPModelUtils.extractNameSapceName(phpClass);
		this.className = PHPModelUtils.extractElementName(phpClass);		
		setFqcn(phpClass);
		this.id = id;
		this.path = new Path(path);
		this.scalar = scalar;
		
	}
	
	public Service(String id2, String phpClass, String path2) {
		this(id2,phpClass,path2, null);
	}
	
	public void setFqcn(String fqcn) {
		
		this.fqcn = fqcn;
	}
	

	public Scalar getScalar() {
		
		return scalar;
		
	}

	public IFile getFile() {
 
		return file;
	}

	public String getFullyQualifiedName() {
		
		return fqcn;
		
	}

	public String getId() {
		return id;
	}

//	public INamespace getNamespace() {
//		
//		return namespace;
//	}
	
	public String getClassName() {
		return className;
	}

	public static Service fromIndex(com.dubture.symfony.index.dao.Service s) {
	
		Service service = new Service(s.id, s.phpClass, s.path, null);
		return service;

	}
	
	public IPath getPath() {
		
		return path;
	}
	
	@Override
	public Object getElementInfo() throws ModelException {

		return new FakeTypeElementInfo();

	}
	
	
	@Override
	protected Object openWhenClosed(Object info, IProgressMonitor monitor)
			throws ModelException {

		return new FakeTypeElementInfo();

	}
	

}