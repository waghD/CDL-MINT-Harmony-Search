/**
 */
package eMISA_Xtend;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.Block#getStatemachine <em>Statemachine</em>}</li>
 *   <li>{@link eMISA_Xtend.Block#getProperty <em>Property</em>}</li>
 * </ul>
 *
 * @see eMISA_Xtend.EMISA_XtendPackage#getBlock()
 * @model
 * @generated
 */
public interface Block extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Statemachine</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statemachine</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statemachine</em>' containment reference.
	 * @see #setStatemachine(StateMachine)
	 * @see eMISA_Xtend.EMISA_XtendPackage#getBlock_Statemachine()
	 * @model containment="true"
	 * @generated
	 */
	StateMachine getStatemachine();

	/**
	 * Sets the value of the '{@link eMISA_Xtend.Block#getStatemachine <em>Statemachine</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Statemachine</em>' containment reference.
	 * @see #getStatemachine()
	 * @generated
	 */
	void setStatemachine(StateMachine value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link eMISA_Xtend.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see eMISA_Xtend.EMISA_XtendPackage#getBlock_Property()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperty();

} // Block
