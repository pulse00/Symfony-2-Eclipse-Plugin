package org.eclipse.symfony.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.symfony.core.parser.IConfigParser;
import org.eclipse.symfony.core.yaml.exception.InvalidYamlFileException;

public class YamlRoutingParser implements IConfigParser {

	@Override
	public void parse(IFile file) throws Exception {
		
		if (file.getFullPath().toString().contains("skeleton"))
			throw new InvalidYamlFileException("Cannot add skeleton files to the model " + file.getFullPath().toString());
		
		
		

	}

}
