/**
 */
package eMISA_Xtend;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.Transition#getSuccessor <em>Successor</em>}</li>
 *   <li>{@link eMISA_Xtend.Transition#getPredecessor <em>Predecessor</em>}</li>
 * </ul>
 *
 * @see eMISA_Xtend.EMISA_XtendPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends EObject {
	/**
	 * Returns the value of the '<em><b>Successor</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link eMISA_Xtend.State#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Successor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Successor</em>' reference.
	 * @see #setSuccessor(State)
	 * @see eMISA_Xtend.EMISA_XtendPackage#getTransition_Successor()
	 * @see eMISA_Xtend.State#getIncoming
	 * @model opposite="incoming" required="true"
	 * @generated
	 */
	State getSuccessor();

	/**
	 * Sets the value of the '{@link eMISA_Xtend.Transition#getSuccessor <em>Successor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Successor</em>' reference.
	 * @see #getSuccessor()
	 * @generated
	 */
	void setSuccessor(State value);

	/**
	 * Returns the value of the '<em><b>Predecessor</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link eMISA_Xtend.State#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Predecessor</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Predecessor</em>' reference.
	 * @see #setPredecessor(State)
	 * @see eMISA_Xtend.EMISA_XtendPackage#getTransition_Predecessor()
	 * @see eMISA_Xtend.State#getOutgoing
	 * @model opposite="outgoing" required="true"
	 * @generated
	 */
	State getPredecessor();

	/**
	 * Sets the value of the '{@link eMISA_Xtend.Transition#getPredecessor <em>Predecessor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Predecessor</em>' reference.
	 * @see #getPredecessor()
	 * @generated
	 */
	void setPredecessor(State value);

} // Transition
