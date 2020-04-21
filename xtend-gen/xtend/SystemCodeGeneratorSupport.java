package xtend;

import org.eclipse.xtext.resource.generic.AbstractGenericResourceSupport;
import xtend.SystemCodeGeneratorModule;

@SuppressWarnings("all")
public class SystemCodeGeneratorSupport extends AbstractGenericResourceSupport {
  @Override
  public SystemCodeGeneratorModule createGuiceModule() {
    return new SystemCodeGeneratorModule();
  }
}
