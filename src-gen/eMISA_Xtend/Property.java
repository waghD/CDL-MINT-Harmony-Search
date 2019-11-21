/**
 */
package eMISA_Xtend;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eMISA_Xtend.Property#getTolerance <em>Tolerance</em>}</li>
 * </ul>
 *
 * @see eMISA_Xtend.EMISA_XtendPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Tolerance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tolerance</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tolerance</em>' containment reference.
	 * @see #setTolerance(ToleranceRange)
	 * @see eMISA_Xtend.EMISA_XtendPackage#getProperty_Tolerance()
	 * @model containment="true"
	 * @generated
	 */
	ToleranceRange getTolerance();

	/**
	 * Sets the value of the '{@link eMISA_Xtend.Property#getTolerance <em>Tolerance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tolerance</em>' containment reference.
	 * @see #getTolerance()
	 * @generated
	 */
	void setTolerance(ToleranceRange value);

} // Property
