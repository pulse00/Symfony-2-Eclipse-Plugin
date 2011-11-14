/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model.exception;

@SuppressWarnings("serial")
public class InvalidBundleException extends Exception {

	public InvalidBundleException(String string) {
		super(string);
	}
}
