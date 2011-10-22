package com.dubture.symfony.core.util;

public class TranslationUtils {
	
	
	
	public static String getLanguageFromFilename(String filename) throws Exception {
		
		String[] parts = filename.split("\\.");

		if (parts.length < 3) {
			throw new Exception("Translation file has wrong format");
		}
		
		return parts[parts.length-2];		
	}

}
