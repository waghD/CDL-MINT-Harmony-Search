package xtend;

import eMISA_Xtend.Assignment;
import eMISA_Xtend.Block;
import eMISA_Xtend.State;
import eMISA_Xtend.StateMachine;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

/**
 * Code generator for automated System.
 */
@SuppressWarnings("all")
public class SystemCodeGenerator implements IGenerator {
  /**
   * Method automatically called by the MWE2 script of workflow
   * It is called one time per .xmi file of EMISA_Xtend/model.
   */
  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    EObject _get = input.getContents().get(0);
    final eMISA_Xtend.System root = ((eMISA_Xtend.System) _get);
    EList<Block> _block = root.getBlock();
    for (final Block b : _block) {
      StateMachine _statemachine = b.getStatemachine();
      boolean _tripleNotEquals = (_statemachine != null);
      if (_tripleNotEquals) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("timeSeries/StateQuery.java");
        fsa.generateFile(_builder.toString(), SystemCodeGenerator.generateStateFile(b.getStatemachine(), b));
      }
    }
  }
  
  private static CharSequence generateStateFile(final StateMachine sm, final Block b) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package timeSeries;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class StateQuery {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static String createQuery(String state){");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("String result =\"\";");
    _builder.newLine();
    {
      EList<State> _state = sm.getState();
      for(final State s : _state) {
        _builder.append("\t\t");
        _builder.append("if(state.equals(\"");
        String _name = s.getName();
        _builder.append(_name, "\t\t");
        _builder.append("\")){");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("result = ");
        CharSequence _generateQuery = SystemCodeGenerator.generateQuery(s, b);
        _builder.append(_generateQuery, "\t\t\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.append("return result;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  private static CharSequence generateQuery(final State state, final Block b) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"SELECT ");
    {
      EList<Assignment> _assignment = state.getAssignment();
      boolean _hasElements = false;
      for(final Assignment a : _assignment) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        _builder.append(" ");
        String _name = a.getProperty().getName();
        _builder.append(_name);
        _builder.append(" ");
      }
    }
    _builder.append(", time\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("+ \" FROM ");
    String _name_1 = b.getName();
    _builder.append(_name_1, "\t\t\t");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("+ \" WHERE ");
    {
      EList<Assignment> _assignment_1 = state.getAssignment();
      boolean _hasElements_1 = false;
      for(final Assignment a_1 : _assignment_1) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder.appendImmediate(" AND ", "\t\t\t");
        }
        _builder.append(" ");
        String _name_2 = a_1.getProperty().getName();
        _builder.append(_name_2, "\t\t\t");
        _builder.append(">=");
        float _value = a_1.getValue();
        float _value_1 = a_1.getProperty().getTolerance().getValue();
        float _minus = (_value - _value_1);
        _builder.append(_minus, "\t\t\t");
        _builder.append(" AND ");
        String _name_3 = a_1.getProperty().getName();
        _builder.append(_name_3, "\t\t\t");
        _builder.append("<=");
        float _value_2 = a_1.getValue();
        float _value_3 = a_1.getProperty().getTolerance().getValue();
        float _plus = (_value_2 + _value_3);
        _builder.append(_plus, "\t\t\t");
      }
    }
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
}
