/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import org.eclipse.dltk.internal.core.SourceTypeElementInfo;

/**
 * 
 * A dummy ElementInfo for Routes.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
@SuppressWarnings("restriction")
public class FakeTypeElementInfo extends SourceTypeElementInfo {

	
	@Override
	public String getFileName() {

		return "";
	}	
}
