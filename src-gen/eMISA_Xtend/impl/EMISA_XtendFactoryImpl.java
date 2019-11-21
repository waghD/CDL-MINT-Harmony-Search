/**
 */
package eMISA_Xtend.impl;

import eMISA_Xtend.Assignment;
import eMISA_Xtend.Block;
import eMISA_Xtend.EMISA_XtendFactory;
import eMISA_Xtend.EMISA_XtendPackage;
import eMISA_Xtend.Property;
import eMISA_Xtend.State;
import eMISA_Xtend.StateMachine;
import eMISA_Xtend.ToleranceRange;
import eMISA_Xtend.Transition;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EMISA_XtendFactoryImpl extends EFactoryImpl implements EMISA_XtendFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EMISA_XtendFactory init() {
		try {
			EMISA_XtendFactory theEMISA_XtendFactory = (EMISA_XtendFactory) EPackage.Registry.INSTANCE
					.getEFactory(EMISA_XtendPackage.eNS_URI);
			if (theEMISA_XtendFactory != null) {
				return theEMISA_XtendFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EMISA_XtendFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMISA_XtendFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case EMISA_XtendPackage.BLOCK:
			return createBlock();
		case EMISA_XtendPackage.STATE_MACHINE:
			return createStateMachine();
		case EMISA_XtendPackage.SYSTEM:
			return createSystem();
		case EMISA_XtendPackage.PROPERTY:
			return createProperty();
		case EMISA_XtendPackage.STATE:
			return createState();
		case EMISA_XtendPackage.TRANSITION:
			return createTransition();
		case EMISA_XtendPackage.ASSIGNMENT:
			return createAssignment();
		case EMISA_XtendPackage.TOLERANCE_RANGE:
			return createToleranceRange();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Block createBlock() {
		BlockImpl block = new BlockImpl();
		return block;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine createStateMachine() {
		StateMachineImpl stateMachine = new StateMachineImpl();
		return stateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public eMISA_Xtend.System createSystem() {
		SystemImpl system = new SystemImpl();
		return system;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assignment createAssignment() {
		AssignmentImpl assignment = new AssignmentImpl();
		return assignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ToleranceRange createToleranceRange() {
		ToleranceRangeImpl toleranceRange = new ToleranceRangeImpl();
		return toleranceRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMISA_XtendPackage getEMISA_XtendPackage() {
		return (EMISA_XtendPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EMISA_XtendPackage getPackage() {
		return EMISA_XtendPackage.eINSTANCE;
	}

} //EMISA_XtendFactoryImpl
