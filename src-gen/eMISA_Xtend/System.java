/**
 */
package eMISA_Xtend;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.System#getBlock <em>Block</em>}</li>
 * </ul>
 *
 * @see eMISA_Xtend.EMISA_XtendPackage#getSystem()
 * @model
 * @generated
 */
public interface System extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Block</b></em>' containment reference list.
	 * The list contents are of type {@link eMISA_Xtend.Block}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Block</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Block</em>' containment reference list.
	 * @see eMISA_Xtend.EMISA_XtendPackage#getSystem_Block()
	 * @model containment="true"
	 * @generated
	 */
	EList<Block> getBlock();

} // System
