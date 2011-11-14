/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index;

import com.dubture.symfony.index.dao.RoutingResource;

public interface IResourceHandler {

	
	void handle(RoutingResource resource);
}
