/**
 */
package eMISA_Xtend;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.State#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link eMISA_Xtend.State#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link eMISA_Xtend.State#getAssignment <em>Assignment</em>}</li>
 *   <li>{@link eMISA_Xtend.State#isIsInitial <em>Is Initial</em>}</li>
 * </ul>
 *
 * @see eMISA_Xtend.EMISA_XtendPackage#getState()
 * @model
 * @generated
 */
public interface State extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link eMISA_Xtend.Transition}.
	 * It is bidirectional and its opposite is '{@link eMISA_Xtend.Transition#getSuccessor <em>Successor</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see eMISA_Xtend.EMISA_XtendPackage#getState_Incoming()
	 * @see eMISA_Xtend.Transition#getSuccessor
	 * @model opposite="successor"
	 * @generated
	 */
	EList<Transition> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link eMISA_Xtend.Transition}.
	 * It is bidirectional and its opposite is '{@link eMISA_Xtend.Transition#getPredecessor <em>Predecessor</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see eMISA_Xtend.EMISA_XtendPackage#getState_Outgoing()
	 * @see eMISA_Xtend.Transition#getPredecessor
	 * @model opposite="predecessor"
	 * @generated
	 */
	EList<Transition> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Assignment</b></em>' containment reference list.
	 * The list contents are of type {@link eMISA_Xtend.Assignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignment</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignment</em>' containment reference list.
	 * @see eMISA_Xtend.EMISA_XtendPackage#getState_Assignment()
	 * @model containment="true"
	 * @generated
	 */
	EList<Assignment> getAssignment();

	/**
	 * Returns the value of the '<em><b>Is Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Initial</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Initial</em>' attribute.
	 * @see #setIsInitial(boolean)
	 * @see eMISA_Xtend.EMISA_XtendPackage#getState_IsInitial()
	 * @model
	 * @generated
	 */
	boolean isIsInitial();

	/**
	 * Sets the value of the '{@link eMISA_Xtend.State#isIsInitial <em>Is Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Initial</em>' attribute.
	 * @see #isIsInitial()
	 * @generated
	 */
	void setIsInitial(boolean value);

} // State
