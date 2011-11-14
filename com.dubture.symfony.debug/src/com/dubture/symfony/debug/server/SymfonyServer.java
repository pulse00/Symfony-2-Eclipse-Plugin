/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.debug.server;

import org.eclipse.php.internal.server.core.Server;

@SuppressWarnings("restriction")
public class SymfonyServer extends Server {

	public static final String KERNELS 			= "app_kernels";
	public static final String ENVIRONMENT 		= "environment";
	public static final String ROUTE 			= "route";
	public static final String URL  			= "symfony_url";
}
