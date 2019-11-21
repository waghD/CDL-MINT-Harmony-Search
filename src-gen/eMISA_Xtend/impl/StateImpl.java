/**
 */
package eMISA_Xtend.impl;

import eMISA_Xtend.Assignment;
import eMISA_Xtend.EMISA_XtendPackage;
import eMISA_Xtend.State;
import eMISA_Xtend.Transition;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.impl.StateImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link eMISA_Xtend.impl.StateImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link eMISA_Xtend.impl.StateImpl#getAssignment <em>Assignment</em>}</li>
 *   <li>{@link eMISA_Xtend.impl.StateImpl#isIsInitial <em>Is Initial</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateImpl extends NamedElementImpl implements State {
	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> incoming;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> outgoing;

	/**
	 * The cached value of the '{@link #getAssignment() <em>Assignment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignment()
	 * @generated
	 * @ordered
	 */
	protected EList<Assignment> assignment;

	/**
	 * The default value of the '{@link #isIsInitial() <em>Is Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsInitial()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INITIAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsInitial() <em>Is Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsInitial()
	 * @generated
	 * @ordered
	 */
	protected boolean isInitial = IS_INITIAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EMISA_XtendPackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Transition>(Transition.class, this,
					EMISA_XtendPackage.STATE__INCOMING, EMISA_XtendPackage.TRANSITION__SUCCESSOR);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<Transition>(Transition.class, this,
					EMISA_XtendPackage.STATE__OUTGOING, EMISA_XtendPackage.TRANSITION__PREDECESSOR);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Assignment> getAssignment() {
		if (assignment == null) {
			assignment = new EObjectContainmentEList<Assignment>(Assignment.class, this,
					EMISA_XtendPackage.STATE__ASSIGNMENT);
		}
		return assignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsInitial() {
		return isInitial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsInitial(boolean newIsInitial) {
		boolean oldIsInitial = isInitial;
		isInitial = newIsInitial;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EMISA_XtendPackage.STATE__IS_INITIAL, oldIsInitial,
					isInitial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EMISA_XtendPackage.STATE__INCOMING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncoming()).basicAdd(otherEnd, msgs);
		case EMISA_XtendPackage.STATE__OUTGOING:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoing()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EMISA_XtendPackage.STATE__INCOMING:
			return ((InternalEList<?>) getIncoming()).basicRemove(otherEnd, msgs);
		case EMISA_XtendPackage.STATE__OUTGOING:
			return ((InternalEList<?>) getOutgoing()).basicRemove(otherEnd, msgs);
		case EMISA_XtendPackage.STATE__ASSIGNMENT:
			return ((InternalEList<?>) getAssignment()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EMISA_XtendPackage.STATE__INCOMING:
			return getIncoming();
		case EMISA_XtendPackage.STATE__OUTGOING:
			return getOutgoing();
		case EMISA_XtendPackage.STATE__ASSIGNMENT:
			return getAssignment();
		case EMISA_XtendPackage.STATE__IS_INITIAL:
			return isIsInitial();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EMISA_XtendPackage.STATE__INCOMING:
			getIncoming().clear();
			getIncoming().addAll((Collection<? extends Transition>) newValue);
			return;
		case EMISA_XtendPackage.STATE__OUTGOING:
			getOutgoing().clear();
			getOutgoing().addAll((Collection<? extends Transition>) newValue);
			return;
		case EMISA_XtendPackage.STATE__ASSIGNMENT:
			getAssignment().clear();
			getAssignment().addAll((Collection<? extends Assignment>) newValue);
			return;
		case EMISA_XtendPackage.STATE__IS_INITIAL:
			setIsInitial((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case EMISA_XtendPackage.STATE__INCOMING:
			getIncoming().clear();
			return;
		case EMISA_XtendPackage.STATE__OUTGOING:
			getOutgoing().clear();
			return;
		case EMISA_XtendPackage.STATE__ASSIGNMENT:
			getAssignment().clear();
			return;
		case EMISA_XtendPackage.STATE__IS_INITIAL:
			setIsInitial(IS_INITIAL_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EMISA_XtendPackage.STATE__INCOMING:
			return incoming != null && !incoming.isEmpty();
		case EMISA_XtendPackage.STATE__OUTGOING:
			return outgoing != null && !outgoing.isEmpty();
		case EMISA_XtendPackage.STATE__ASSIGNMENT:
			return assignment != null && !assignment.isEmpty();
		case EMISA_XtendPackage.STATE__IS_INITIAL:
			return isInitial != IS_INITIAL_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (isInitial: ");
		result.append(isInitial);
		result.append(')');
		return result.toString();
	}

} //StateImpl
