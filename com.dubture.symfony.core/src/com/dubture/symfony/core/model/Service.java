/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.model.PhpModelAccess;
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


    // just for bc purposec
    public static final String NAME = com.dubture.symfony.index.model.Service.NAME;
    public static final String CLASS = com.dubture.symfony.index.model.Service.CLASS;
    public static final Object SYNTHETIC = com.dubture.symfony.index.model.Service.SYNTHETIC;


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

    private boolean _isPublic = true;
    private List<String> tags = new ArrayList<String>();
    private List<String> aliases = new ArrayList<String>();

    private IPath path;

    private String _stringTags;
    private String _stringAliases;

    public Service(ModelElement parent, String name) {
        super(parent, name);
    }


    public void setId(String id) {

        this.id = id;

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

    @Override
    public String getElementName() {


        if (className != null)
            return className;

        return super.getElementName();


    }

    public Service(String id, String phpClass, String path, Scalar scalar) {

        super(null, id);
        this.namespace = PHPModelUtils.extractNameSpaceName(phpClass);
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

//    public INamespace getNamespace() {
//
//        return namespace;
//    }

    public String getClassName() {
        return className;
    }

    public static Service fromIndex(com.dubture.symfony.index.model.Service s) {

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

    public String getSimpleNamespace() {

        return namespace;

    }


    @Override
    public ISourceModule getSourceModule()
    {

        if (className != null && namespace != null) {
            IScriptProject project = getScriptProject();
            IDLTKSearchScope scope = project != null ? SearchEngine.createSearchScope(project) : SearchEngine.createWorkspaceScope(PHPLanguageToolkit.getDefault());
            IType[] types = PhpModelAccess.getDefault().findTypes(namespace, className, MatchRule.EXACT, 0, 0, scope, null);
            if (types.length >= 1) {
                return types[0].getSourceModule();
            }
        }

        return super.getSourceModule();

    }

    public void setTags(String tags) {

        _stringTags = tags;
        String[] _tags  = tags.split(",");

        for (String tag : _tags) {
            this.tags.add(tag);
        }

    }

    public void setAliases(String aliases) {

        _stringAliases = aliases;
        String[] _aliases  = aliases.split(",");

        for (String alias : _aliases) {
            this.aliases.add(alias);
        }
    }


    public List<String> getTags() {

        return tags;
    }

    public String getStringAliases() {

        return _stringAliases;
    }

    public String getStringTags() {

        return _stringTags;
    }

    public String getPublicString() {

        return _isPublic ? "true" : "false";
    }


    public void setPublic(String _public)
    {

        _isPublic = true;

        if (_public != null && _public.equals("false"))
            _isPublic = false;

    }

    public boolean isPublic() {

        return _isPublic;
    }


    @Override
    public boolean equals(Object o)
    {

        if (!(o instanceof Service))
            return false;

        Service other = (Service) o;

        return id.equals(other.getId());

    }


    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

}
