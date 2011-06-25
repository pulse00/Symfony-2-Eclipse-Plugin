package org.eclipse.symfony.core.builder;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.symfony.core.parser.XMLConfigParser;
import org.eclipse.symfony.core.parser.YamlConfigParser;
import org.eclipse.symfony.index.IServiceDao;
import org.eclipse.symfony.index.SymfonyDbFactory;


/**
 * 
 * The {@link ResourceVisitor} is a standard buildvisitor to 
 * parse xml/yml config files from a Symfony2 project during
 * a full build.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ResourceVisitor extends AbstractSymfonyVisitor 
implements IResourceVisitor {


	private IFile file;
	private IPath path;
	private IResource resource;
	private IContainer resourceContainer;


	@Override
	public boolean visit(IResource resource) throws CoreException {

		if (resource instanceof IFile && resource.getFileExtension() != null) {

			file = (IFile) resource;
			path = resource.getFullPath();
			this.resource = resource;

			resourceContainer = resource.getParent();


			if (resource.getFileExtension().equals("xml"))
			{				
				loadXML();

			} else if (resource.getFileExtension().equals("yml")) 
			{
				loadYaml();
			}
		}

		return true;
	}


	private void loadYaml() {

		try {

			YamlConfigParser parser = new YamlConfigParser(file.getContents());
			parser.parse();


			loadServices(parser.getServices());

		} catch (Exception e1) {

			System.err.println(e1.getMessage());

		}

	}

	private void loadXML() {

		try {

			XMLConfigParser parser;					
			parser = new XMLConfigParser(file.getContents());
			parser.parse();

			if (parser.hasServices()) {
				loadServices(parser.getServices());
			}

		} catch (Exception e) {

			//System.err.println("xml error: " + e.getMessage());
		}


	}


	@SuppressWarnings("rawtypes")
	private void loadServices(HashMap<String, String> services) {

		Connection connection = null;

		try {

			SymfonyDbFactory factory = SymfonyDbFactory.getInstance();
			connection = factory.createConnection();
			IServiceDao serviceDao = factory.getServiceDao();

			Iterator it = services.keySet().iterator();

			while(it.hasNext()) {

				String id = (String) it.next();
				String value = services.get(id);
				
				serviceDao.insert(connection, path.toString(), id, value, (int) resource.getLocalTimeStamp());

			}



		} catch (Exception e) {

			e.printStackTrace();
		} finally {

			try {				
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}