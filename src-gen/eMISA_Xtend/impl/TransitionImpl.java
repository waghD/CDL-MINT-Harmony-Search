/**
 */
package eMISA_Xtend.impl;

import eMISA_Xtend.EMISA_XtendPackage;
import eMISA_Xtend.State;
import eMISA_Xtend.Transition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.impl.TransitionImpl#getSuccessor <em>Successor</em>}</li>
 *   <li>{@link eMISA_Xtend.impl.TransitionImpl#getPredecessor <em>Predecessor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransitionImpl extends MinimalEObjectImpl.Container implements Transition {
	/**
	 * The cached value of the '{@link #getSuccessor() <em>Successor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuccessor()
	 * @generated
	 * @ordered
	 */
	protected State successor;

	/**
	 * The cached value of the '{@link #getPredecessor() <em>Predecessor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredecessor()
	 * @generated
	 * @ordered
	 */
	protected State predecessor;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EMISA_XtendPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getSuccessor() {
		if (successor != null && successor.eIsProxy()) {
			InternalEObject oldSuccessor = (InternalEObject) successor;
			successor = (State) eResolveProxy(oldSuccessor);
			if (successor != oldSuccessor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EMISA_XtendPackage.TRANSITION__SUCCESSOR,
							oldSuccessor, successor));
			}
		}
		return successor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetSuccessor() {
		return successor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuccessor(State newSuccessor, NotificationChain msgs) {
		State oldSuccessor = successor;
		successor = newSuccessor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					EMISA_XtendPackage.TRANSITION__SUCCESSOR, oldSuccessor, newSuccessor);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuccessor(State newSuccessor) {
		if (newSuccessor != successor) {
			NotificationChain msgs = null;
			if (successor != null)
				msgs = ((InternalEObject) successor).eInverseRemove(this, EMISA_XtendPackage.STATE__INCOMING,
						State.class, msgs);
			if (newSuccessor != null)
				msgs = ((InternalEObject) newSuccessor).eInverseAdd(this, EMISA_XtendPackage.STATE__INCOMING,
						State.class, msgs);
			msgs = basicSetSuccessor(newSuccessor, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EMISA_XtendPackage.TRANSITION__SUCCESSOR,
					newSuccessor, newSuccessor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State getPredecessor() {
		if (predecessor != null && predecessor.eIsProxy()) {
			InternalEObject oldPredecessor = (InternalEObject) predecessor;
			predecessor = (State) eResolveProxy(oldPredecessor);
			if (predecessor != oldPredecessor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EMISA_XtendPackage.TRANSITION__PREDECESSOR, oldPredecessor, predecessor));
			}
		}
		return predecessor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetPredecessor() {
		return predecessor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPredecessor(State newPredecessor, NotificationChain msgs) {
		State oldPredecessor = predecessor;
		predecessor = newPredecessor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					EMISA_XtendPackage.TRANSITION__PREDECESSOR, oldPredecessor, newPredecessor);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPredecessor(State newPredecessor) {
		if (newPredecessor != predecessor) {
			NotificationChain msgs = null;
			if (predecessor != null)
				msgs = ((InternalEObject) predecessor).eInverseRemove(this, EMISA_XtendPackage.STATE__OUTGOING,
						State.class, msgs);
			if (newPredecessor != null)
				msgs = ((InternalEObject) newPredecessor).eInverseAdd(this, EMISA_XtendPackage.STATE__OUTGOING,
						State.class, msgs);
			msgs = basicSetPredecessor(newPredecessor, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EMISA_XtendPackage.TRANSITION__PREDECESSOR,
					newPredecessor, newPredecessor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EMISA_XtendPackage.TRANSITION__SUCCESSOR:
			if (successor != null)
				msgs = ((InternalEObject) successor).eInverseRemove(this, EMISA_XtendPackage.STATE__INCOMING,
						State.class, msgs);
			return basicSetSuccessor((State) otherEnd, msgs);
		case EMISA_XtendPackage.TRANSITION__PREDECESSOR:
			if (predecessor != null)
				msgs = ((InternalEObject) predecessor).eInverseRemove(this, EMISA_XtendPackage.STATE__OUTGOING,
						State.class, msgs);
			return basicSetPredecessor((State) otherEnd, msgs);
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
		case EMISA_XtendPackage.TRANSITION__SUCCESSOR:
			return basicSetSuccessor(null, msgs);
		case EMISA_XtendPackage.TRANSITION__PREDECESSOR:
			return basicSetPredecessor(null, msgs);
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
		case EMISA_XtendPackage.TRANSITION__SUCCESSOR:
			if (resolve)
				return getSuccessor();
			return basicGetSuccessor();
		case EMISA_XtendPackage.TRANSITION__PREDECESSOR:
			if (resolve)
				return getPredecessor();
			return basicGetPredecessor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EMISA_XtendPackage.TRANSITION__SUCCESSOR:
			setSuccessor((State) newValue);
			return;
		case EMISA_XtendPackage.TRANSITION__PREDECESSOR:
			setPredecessor((State) newValue);
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
		case EMISA_XtendPackage.TRANSITION__SUCCESSOR:
			setSuccessor((State) null);
			return;
		case EMISA_XtendPackage.TRANSITION__PREDECESSOR:
			setPredecessor((State) null);
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
		case EMISA_XtendPackage.TRANSITION__SUCCESSOR:
			return successor != null;
		case EMISA_XtendPackage.TRANSITION__PREDECESSOR:
			return predecessor != null;
		}
		return super.eIsSet(featureID);
	}

} //TransitionImpl
