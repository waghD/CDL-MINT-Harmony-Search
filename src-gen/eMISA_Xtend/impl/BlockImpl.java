/**
 */
package eMISA_Xtend.impl;

import eMISA_Xtend.Block;
import eMISA_Xtend.EMISA_XtendPackage;
import eMISA_Xtend.Property;
import eMISA_Xtend.StateMachine;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.impl.BlockImpl#getStatemachine <em>Statemachine</em>}</li>
 *   <li>{@link eMISA_Xtend.impl.BlockImpl#getProperty <em>Property</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockImpl extends NamedElementImpl implements Block {
	/**
	 * The cached value of the '{@link #getStatemachine() <em>Statemachine</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatemachine()
	 * @generated
	 * @ordered
	 */
	protected StateMachine statemachine;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> property;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EMISA_XtendPackage.Literals.BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine getStatemachine() {
		return statemachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatemachine(StateMachine newStatemachine, NotificationChain msgs) {
		StateMachine oldStatemachine = statemachine;
		statemachine = newStatemachine;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					EMISA_XtendPackage.BLOCK__STATEMACHINE, oldStatemachine, newStatemachine);
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
	public void setStatemachine(StateMachine newStatemachine) {
		if (newStatemachine != statemachine) {
			NotificationChain msgs = null;
			if (statemachine != null)
				msgs = ((InternalEObject) statemachine).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - EMISA_XtendPackage.BLOCK__STATEMACHINE, null, msgs);
			if (newStatemachine != null)
				msgs = ((InternalEObject) newStatemachine).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - EMISA_XtendPackage.BLOCK__STATEMACHINE, null, msgs);
			msgs = basicSetStatemachine(newStatemachine, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EMISA_XtendPackage.BLOCK__STATEMACHINE,
					newStatemachine, newStatemachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList<Property>(Property.class, this, EMISA_XtendPackage.BLOCK__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EMISA_XtendPackage.BLOCK__STATEMACHINE:
			return basicSetStatemachine(null, msgs);
		case EMISA_XtendPackage.BLOCK__PROPERTY:
			return ((InternalEList<?>) getProperty()).basicRemove(otherEnd, msgs);
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
		case EMISA_XtendPackage.BLOCK__STATEMACHINE:
			return getStatemachine();
		case EMISA_XtendPackage.BLOCK__PROPERTY:
			return getProperty();
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
		case EMISA_XtendPackage.BLOCK__STATEMACHINE:
			setStatemachine((StateMachine) newValue);
			return;
		case EMISA_XtendPackage.BLOCK__PROPERTY:
			getProperty().clear();
			getProperty().addAll((Collection<? extends Property>) newValue);
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
		case EMISA_XtendPackage.BLOCK__STATEMACHINE:
			setStatemachine((StateMachine) null);
			return;
		case EMISA_XtendPackage.BLOCK__PROPERTY:
			getProperty().clear();
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
		case EMISA_XtendPackage.BLOCK__STATEMACHINE:
			return statemachine != null;
		case EMISA_XtendPackage.BLOCK__PROPERTY:
			return property != null && !property.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BlockImpl
