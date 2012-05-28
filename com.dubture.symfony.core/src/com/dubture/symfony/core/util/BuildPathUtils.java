/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.dltk.internal.core.util.Util;

@SuppressWarnings("restriction")
public class BuildPathUtils extends
        org.eclipse.php.internal.core.buildpath.BuildPathUtils
{
    
    public static boolean isContainedInBuildpath(IResource resource,
            IScriptProject project) {
        
        IPath resourcePath = resource.getFullPath();
        boolean result = false;
        if (resourcePath == null) {
            return false;
        }

        IBuildpathEntry[] buildpath = null;
        try {
            buildpath = project.getRawBuildpath();
        } catch (ModelException e) {
            if (DLTKCore.DEBUG) {
                e.printStackTrace();
            }
            return false;
        }
        
        // go over the build path entries and for each one of the "sources"
        // check if they are the same as the given include path entry or if they
        // contain it
        for (IBuildpathEntry buildpathEntry : buildpath) {
            if (buildpathEntry.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
                IPath buildPathEntryPath = buildpathEntry.getPath();
                if (buildPathEntryPath.isPrefixOf(resourcePath)) {
                    BuildpathEntry entry = (BuildpathEntry) buildpathEntry;
                    entry.fullExclusionPatternChars();
                    if (Util.isExcluded(resource, entry.fullInclusionPatternChars(), entry.fullExclusionPatternChars())) {
                        result = false;
                        break;
                    }
                    
                    if (buildPathEntryPath.isPrefixOf(resourcePath)
                            || resourcePath.toString().equals(
                                    buildPathEntryPath.toString())) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
