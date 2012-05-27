package com.dubture.symfony.ui.quickassist;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.core.resources.SymfonyMarker;

public class XMLQuickassistProcessor implements IMarkerResolutionGenerator2 {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {

		try {
			if (SymfonyMarker.MISSING_SERVICE_CLASS.equals(marker.getType())) {
				
				XMLMarkerResolution resolution = new XMLMarkerResolution(marker);
				return new XMLMarkerResolution[]{resolution};
				
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		return null;
		
	}

	@Override
	public boolean hasResolutions(IMarker marker)
	{
		try {
			if (SymfonyMarker.MISSING_SERVICE_CLASS.equals(marker.getType())) {
				return true;
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
		
		return false;
	}

}
