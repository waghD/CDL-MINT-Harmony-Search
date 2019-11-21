package xtend;

import java.io.File;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Some utility methods used in StatesMLCodeGeneratorStrings.
 */
@SuppressWarnings("all")
public class EclipseCodeGeneratorUtil {
  /**
   * Given a file, recursively looks for the parent folder that contains
   * an eclipse project, ie. containing a file named ".project".
   */
  private static File findParentEclipseProjectFolder(final File f) {
    boolean _isDirectory = f.isDirectory();
    if (_isDirectory) {
      final File projectFile = new File(f, ".project");
      boolean _exists = projectFile.exists();
      if (_exists) {
        return f;
      }
    }
    return EclipseCodeGeneratorUtil.findParentEclipseProjectFolder(f.getParentFile());
  }
  
  /**
   * Given an EObject, returns the name of the eclipse project
   * folder that contains its model file.
   */
  public static String getEclipseProjectName(final EObject eObject) {
    final Resource eResource = eObject.eResource();
    final URI eUri = eResource.getURI();
    String _xifexpression = null;
    if ((eUri.isPlatformResource() || eUri.isPlatformPlugin())) {
      _xifexpression = eUri.segment(1);
    } else {
      String _xifexpression_1 = null;
      boolean _isFile = eUri.isFile();
      if (_isFile) {
        String _xblockexpression = null;
        {
          String _fileString = eUri.toFileString();
          final File file = new File(_fileString);
          _xblockexpression = EclipseCodeGeneratorUtil.findParentEclipseProjectFolder(file).getName();
        }
        _xifexpression_1 = _xblockexpression;
      }
      _xifexpression = _xifexpression_1;
    }
    final String projectName = _xifexpression;
    return projectName;
  }
}
