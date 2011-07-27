package com.dubture.symfony.core.model.listener;

/**
 * 
 * Listeners to be notified when the Symfony model has 
 * been changed.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public interface IModelChangedListener {
		
	void modelChanged();

}
