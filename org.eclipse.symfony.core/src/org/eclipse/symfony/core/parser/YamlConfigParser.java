package org.eclipse.symfony.core.parser;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.yaml.snakeyaml.Yaml;

public class YamlConfigParser implements IConfigParser {
	
	@Override
	@SuppressWarnings("unchecked")
	public void parse(IFile file) {

		
		try {
			
			System.out.println("parse yml file");
			Yaml yaml = new Yaml();
			List<String> list = (List<String>) yaml.load(file.getContents());
			
			System.out.println(list);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
