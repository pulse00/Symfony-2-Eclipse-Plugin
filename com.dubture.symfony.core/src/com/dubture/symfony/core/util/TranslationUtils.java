/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
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
