/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model.listener;

/**
 * 
 * Interface for listening to model clear events.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public interface IModelClearListener {
	
	
	/**
	 * Will be called when the model is completely
	 * wiped during a full-build.
	 * 
	 */
	public void modelCleared();

}
