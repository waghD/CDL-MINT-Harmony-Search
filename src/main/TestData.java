package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import design.Block;
import design.Property;
import design.State;

public class TestData {

	/**
	 * Sets up block object with data streams
	 * 
	 * @return Block
	 */
	public static Block setUpDataStream(List<AxisStream> axisList) {
		List<State> states = new ArrayList<State>();
		states.add(TestData.getDriveDownState(axisList));
		states.add(TestData.getPickupState(axisList));
		states.add(TestData.getDepositGreenState(axisList));
		states.add(TestData.getDepositRedState(axisList));
		states.add(TestData.getFullReleaseState(axisList));
		states.add(TestData.getHalfReleaseState(axisList));
		states.add(TestData.getIdleState(axisList));
		states.add(TestData.getLiftState(axisList));
		states.add(TestData.getParkState(axisList));
		states.add(TestData.getReleaseGreenState(axisList));
		states.add(TestData.getReleaseRedState(axisList));
		states.add(TestData.getRetrieveGripState(axisList));
		states.add(TestData.getRetrieveState(axisList));
		//states.add(TestData.getWaitState(axisList));
		

		// SetUp Block
		return new Block("roboticArm", states);
	}
	
	private static State getDriveDownState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 0));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.5));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", 1.5));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -0.12));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 0));
		}
		return new State("DriveDown", props);
	}
	
	private static State getPickupState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 0));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.5));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", -0.4));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -0.12));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 0));
		}
		return new State("PickUp", props);
	}
	private static State getLiftState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 0));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.1));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", -0.4));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -0.12));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 0));
		}
		return new State("Lift", props);
	}
	private static State getParkState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 3.1));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.1));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", -0.4));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -1.55));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", -1.5));
		}
		return new State("Park", props);
	}
	
	private static State getHalfReleaseState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 3.142));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.36));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", -0.2));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -1.34));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", -1.5));
		}
		return new State("HalfRelease", props);
	}
	
	private static State getFullReleaseState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 3.142));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.36));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", 1.0));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -1.334));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", -1.5));
		}
		return new State("FullRelease", props);
	}
	
	
	private static State getWaitState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 3.142));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.36));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", 1.0));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -1.334));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", -1.5));
		}
		return new State("Wait", props);
	}
	
	private static State getRetrieveState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 3.142));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.3));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", 1.0));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -1.3));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", -1.5));
		}
		return new State("Retrieve", props);
	}
	
	private static State getRetrieveGripState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 3.142));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 1.3));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", -0.4));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -1.3));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", -1.5));
		}
		return new State("RetrieveGrip", props);
	}
	
	private static State getDepositRedState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", -1.449));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 0.942));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", -0.4));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -0.89));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 1.5));
		}
		return new State("DepositRed", props);
	}
	
	private static State getReleaseRedState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", -1.449));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 0.942));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", 0.5));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -0.89));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 1.5));
		}
		return new State("ReleaseRed", props);
	}
	
	private static State getDepositGreenState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", -1.745));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 0.942));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", -0.4));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -0.89));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 1.5));
		}
		return new State("DepositGreen", props);
	}
	
	private static State getReleaseGreenState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", -1.745));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 0.942));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", 0.5));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", -0.89));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 1.5));
		}
		return new State("ReleaseGreen", props);
	}
	
	private static State getIdleState(List<AxisStream> axisList) {
		List<Property> props = new ArrayList<Property>();
		if (axisList.contains(AxisStream.BP)) {
			props.add(new Property("bp", 0));
		}
		if (axisList.contains(AxisStream.MAP)) {
			props.add(new Property("map", 0));
		}
		if (axisList.contains(AxisStream.GP)) {
			props.add(new Property("gp", 0));
		}
		if (axisList.contains(AxisStream.SAP)) {
			props.add(new Property("sap", 0));
		}
		if (axisList.contains(AxisStream.WP)) {
			props.add(new Property("wp", 0));
		}
		return new State("Idle", props);
	}
}