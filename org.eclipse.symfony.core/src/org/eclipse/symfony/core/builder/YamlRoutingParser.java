package org.eclipse.symfony.core.builder;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.symfony.core.parser.IConfigParser;
import org.eclipse.symfony.core.yaml.exception.InvalidYamlFileException;
import org.yaml.snakeyaml.Yaml;

public class YamlRoutingParser implements IConfigParser {

	@SuppressWarnings("rawtypes")
	@Override
	public void parse() throws Exception {
		
//		if (file.getFullPath().toString().contains("skeleton"))
//			throw new InvalidYamlFileException("Cannot add skeleton files to the model " + file.getFullPath().toString());
//		
//		try {
//			
//
//			Yaml yaml = new Yaml();
//			Object list = yaml.load(file.getContents());
//			
//			if (list instanceof LinkedHashMap) {
//				
//				LinkedHashMap map = (LinkedHashMap) list;
//				
//				Iterator it = map.keySet().iterator();
//				
//				while(it.hasNext()) {
//					
//					Object key = it.next();					
//					Object val = map.get(key);
//					
//					System.out.println(key);
//					System.out.println(val);
//					
//					
//				}
//				
//			}
//			
//			System.out.println(list.getClass());
//			
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		

	}

}
