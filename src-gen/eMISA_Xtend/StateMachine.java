/**
 */
package eMISA_Xtend;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.StateMachine#getState <em>State</em>}</li>
 *   <li>{@link eMISA_Xtend.StateMachine#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @see eMISA_Xtend.EMISA_XtendPackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends NamedElement {
	/**
	 * Returns the value of the '<em><b>State</b></em>' containment reference list.
	 * The list contents are of type {@link eMISA_Xtend.State}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' containment reference list.
	 * @see eMISA_Xtend.EMISA_XtendPackage#getStateMachine_State()
	 * @model containment="true"
	 * @generated
	 */
	EList<State> getState();

	/**
	 * Returns the value of the '<em><b>Transition</b></em>' containment reference list.
	 * The list contents are of type {@link eMISA_Xtend.Transition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transition</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transition</em>' containment reference list.
	 * @see eMISA_Xtend.EMISA_XtendPackage#getStateMachine_Transition()
	 * @model containment="true"
	 * @generated
	 */
	EList<Transition> getTransition();

} // StateMachine
