/**
 */
package eMISA_Xtend.impl;

import eMISA_Xtend.EMISA_XtendPackage;
import eMISA_Xtend.State;
import eMISA_Xtend.StateMachine;
import eMISA_Xtend.Transition;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.impl.StateMachineImpl#getState <em>State</em>}</li>
 *   <li>{@link eMISA_Xtend.impl.StateMachineImpl#getTransition <em>Transition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateMachineImpl extends NamedElementImpl implements StateMachine {
	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected EList<State> state;

	/**
	 * The cached value of the '{@link #getTransition() <em>Transition</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransition()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> transition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateMachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EMISA_XtendPackage.Literals.STATE_MACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getState() {
		if (state == null) {
			state = new EObjectContainmentEList<State>(State.class, this, EMISA_XtendPackage.STATE_MACHINE__STATE);
		}
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getTransition() {
		if (transition == null) {
			transition = new EObjectContainmentEList<Transition>(Transition.class, this,
					EMISA_XtendPackage.STATE_MACHINE__TRANSITION);
		}
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EMISA_XtendPackage.STATE_MACHINE__STATE:
			return ((InternalEList<?>) getState()).basicRemove(otherEnd, msgs);
		case EMISA_XtendPackage.STATE_MACHINE__TRANSITION:
			return ((InternalEList<?>) getTransition()).basicRemove(otherEnd, msgs);
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
		case EMISA_XtendPackage.STATE_MACHINE__STATE:
			return getState();
		case EMISA_XtendPackage.STATE_MACHINE__TRANSITION:
			return getTransition();
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
		case EMISA_XtendPackage.STATE_MACHINE__STATE:
			getState().clear();
			getState().addAll((Collection<? extends State>) newValue);
			return;
		case EMISA_XtendPackage.STATE_MACHINE__TRANSITION:
			getTransition().clear();
			getTransition().addAll((Collection<? extends Transition>) newValue);
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
		case EMISA_XtendPackage.STATE_MACHINE__STATE:
			getState().clear();
			return;
		case EMISA_XtendPackage.STATE_MACHINE__TRANSITION:
			getTransition().clear();
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
		case EMISA_XtendPackage.STATE_MACHINE__STATE:
			return state != null && !state.isEmpty();
		case EMISA_XtendPackage.STATE_MACHINE__TRANSITION:
			return transition != null && !transition.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateMachineImpl
