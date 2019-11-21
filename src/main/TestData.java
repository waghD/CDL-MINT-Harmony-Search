package main;

import java.util.ArrayList;
import java.util.List;

import design.Block;
import design.Property;
import design.State;

public class TestData {
	
	public static Block setUpDataStream(StreamCount streamCount) {
		switch(streamCount) {
		case FIVE:
			return TestData.setUpDataFiveStreams();
		case THREE:
			return TestData.setUpDataThreeStreams();
		case SINGLE:
		default:
			return TestData.setUpDataSingleStream();
		}
	}
	
	
	private static Block setUpDataSingleStream() {
		// setUp Information for state DriveDown
		Property p1 = new Property("gp", 1.5);

		List<Property> props1 = new ArrayList<Property>();
		props1.add(p1);
		State s1 = new State("DriveDown", props1);

		// setUp Information for state PickUp
		Property p2 = new Property("gp", -0.4);
		List<Property> props2 = new ArrayList<Property>();
		props2.add(p2);
		State s2 = new State("PickUp", props2);

		// List states
		List<State> states = new ArrayList<State>();
		states.add(s1);
		states.add(s2);

		// SetUp Block
		Block roboticArm = new Block("roboticArm", states);
		return roboticArm;
	}

	private static Block setUpDataThreeStreams() {
		// setUp Information for state DriveDown
		Property p1 = new Property("bp", 0);
		Property p2 = new Property("map", 1.5);
		Property p3 = new Property("gp", 1.5);

		List<Property> props1 = new ArrayList<Property>();
		props1.add(p1);
		props1.add(p2);
		props1.add(p3);
		State s1 = new State("DriveDown", props1);

		// setUp Information for state PickUp
		Property p4 = new Property("bp", 0);
		Property p5 = new Property("map", 1.5);
		Property p6 = new Property("gp", -0.4);

		List<Property> props2 = new ArrayList<Property>();
		props2.add(p4);
		props2.add(p5);
		props2.add(p6);
		State s2 = new State("PickUp", props2);

		// List states
		List<State> states = new ArrayList<State>();
		states.add(s1);
		states.add(s2);

		// SetUp Block
		Block roboticArm = new Block("roboticArm", states);
		return roboticArm;
	}

	private static Block setUpDataFiveStreams() {
		// setUp Information for state DriveDown
		Property p1 = new Property("bp", 0);
		Property p2 = new Property("map", 1.5);
		Property p3 = new Property("gp", 1.5);
		Property p4 = new Property("sap", -0.12);
		Property p5 = new Property("wp", 0);

		List<Property> props1 = new ArrayList<Property>();
		props1.add(p1);
		props1.add(p2);
		props1.add(p3);
		props1.add(p4);
		props1.add(p5);
		State s1 = new State("DriveDown", props1);

		// setUp Information for state PickUp
		Property p6 = new Property("bp", 0);
		Property p7 = new Property("map", 1.5);
		Property p8 = new Property("gp", -0.4);
		Property p9 = new Property("sap", -0.12);
		Property p10 = new Property("wp", 0);
		List<Property> props2 = new ArrayList<Property>();
		props2.add(p6);
		props2.add(p7);
		props2.add(p8);
		props2.add(p9);
		props2.add(p10);
		State s2 = new State("PickUp", props2);

		// List states
		List<State> states = new ArrayList<State>();
		states.add(s1);
		states.add(s2);

		// SetUp Block
		Block roboticArm = new Block("roboticArm", states);
		return roboticArm;
	}
}
