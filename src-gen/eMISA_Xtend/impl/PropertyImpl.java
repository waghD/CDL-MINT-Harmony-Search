/**
 */
package eMISA_Xtend.impl;

import eMISA_Xtend.EMISA_XtendPackage;
import eMISA_Xtend.Property;
import eMISA_Xtend.ToleranceRange;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.impl.PropertyImpl#getTolerance <em>Tolerance</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyImpl extends NamedElementImpl implements Property {
	/**
	 * The cached value of the '{@link #getTolerance() <em>Tolerance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTolerance()
	 * @generated
	 * @ordered
	 */
	protected ToleranceRange tolerance;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EMISA_XtendPackage.Literals.PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ToleranceRange getTolerance() {
		return tolerance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTolerance(ToleranceRange newTolerance, NotificationChain msgs) {
		ToleranceRange oldTolerance = tolerance;
		tolerance = newTolerance;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					EMISA_XtendPackage.PROPERTY__TOLERANCE, oldTolerance, newTolerance);
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
	public void setTolerance(ToleranceRange newTolerance) {
		if (newTolerance != tolerance) {
			NotificationChain msgs = null;
			if (tolerance != null)
				msgs = ((InternalEObject) tolerance).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - EMISA_XtendPackage.PROPERTY__TOLERANCE, null, msgs);
			if (newTolerance != null)
				msgs = ((InternalEObject) newTolerance).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - EMISA_XtendPackage.PROPERTY__TOLERANCE, null, msgs);
			msgs = basicSetTolerance(newTolerance, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EMISA_XtendPackage.PROPERTY__TOLERANCE, newTolerance,
					newTolerance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EMISA_XtendPackage.PROPERTY__TOLERANCE:
			return basicSetTolerance(null, msgs);
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
		case EMISA_XtendPackage.PROPERTY__TOLERANCE:
			return getTolerance();
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
		case EMISA_XtendPackage.PROPERTY__TOLERANCE:
			setTolerance((ToleranceRange) newValue);
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
		case EMISA_XtendPackage.PROPERTY__TOLERANCE:
			setTolerance((ToleranceRange) null);
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
		case EMISA_XtendPackage.PROPERTY__TOLERANCE:
			return tolerance != null;
		}
		return super.eIsSet(featureID);
	}

} //PropertyImpl
