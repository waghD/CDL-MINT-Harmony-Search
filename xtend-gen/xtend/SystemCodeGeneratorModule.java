package xtend;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;
import xtend.SystemCodeGenerator;

@SuppressWarnings("all")
public class SystemCodeGeneratorModule extends AbstractGenericResourceRuntimeModule {
  public static class Utf8EncodingProvider implements IEncodingProvider {
    @Override
    public String getEncoding(final URI uri) {
      return "UTF-8";
    }
  }
  
  @Override
  public String getLanguageName() {
    return "system";
  }
  
  @Override
  public String getFileExtensions() {
    return "xmi";
  }
  
  @Override
  public Class<? extends IEncodingProvider> bindIEncodingProvider() {
    return SystemCodeGeneratorModule.Utf8EncodingProvider.class;
  }
  
  public Class<? extends IGenerator> bindIGenerator() {
    return SystemCodeGenerator.class;
  }
  
  public Class<? extends ResourceSet> bindResourceSet() {
    return ResourceSetImpl.class;
  }
}
