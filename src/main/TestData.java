package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import design.Block;
import design.Property;
import design.State;

public class TestData {

	public static Block setUpDataStream(StreamCount streamCount) {
		switch (streamCount) {
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

	/**
	 * Sets up block object with data streams
	 * 
	 * @return Block
	 */
	/*
	 * private static Block setUpDataFiveStreams() { // setUp Information for state
	 * DriveDown Property p1 = new Property("bp", 0); Property p2 = new
	 * Property("map", 1.5); Property p3 = new Property("gp", 1.5); Property p4 =
	 * new Property("sap", -0.12); Property p5 = new Property("wp", 0);
	 * 
	 * List<Property> props1 = new ArrayList<Property>(); props1.add(p1);
	 * props1.add(p2); props1.add(p3); props1.add(p4); props1.add(p5); State s1 =
	 * new State("DriveDown", props1);
	 * 
	 * // setUp Information for state PickUp Property p6 = new Property("bp", 0);
	 * Property p7 = new Property("map", 1.5); Property p8 = new Property("gp",
	 * -0.4); Property p9 = new Property("sap", -0.12); Property p10 = new
	 * Property("wp", 0); List<Property> props2 = new ArrayList<Property>();
	 * props2.add(p6); props2.add(p7); props2.add(p8); props2.add(p9);
	 * props2.add(p10); State s2 = new State("PickUp", props2);
	 * 
	 * // List states List<State> states = new ArrayList<State>(); states.add(s1);
	 * states.add(s2);
	 * 
	 * // SetUp Block Block roboticArm = new Block("roboticArm", states); return
	 * roboticArm; }
	 */
	/**
	 * Sets up block object with data streams
	 * 
	 * @return Block
	 */
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

		/*
		
		 */
		// setUp Information for state Lift
		Property p11 = new Property("bp", 0);
		Property p12 = new Property("map", 1.1);
		Property p13 = new Property("gp", -0.4);
		Property p14 = new Property("sap", -0.12);
		Property p15 = new Property("wp", 0);
		List<Property> props3 = new ArrayList<Property>();
		props3.add(p11);
		props3.add(p12);
		props3.add(p13);
		props3.add(p14);
		props3.add(p15);
		State s3 = new State("Lift", props3);
		// setUp Information for state Park
		Property p16 = new Property("bp", 3.1);
		Property p17 = new Property("map", 1.1);
		Property p18 = new Property("gp", -0.4);
		Property p19 = new Property("sap", -1.55);
		Property p20 = new Property("wp", -1.5);
		List<Property> props4 = new ArrayList<Property>();
		props4.add(p16);
		props4.add(p17);
		props4.add(p18);
		props4.add(p19);
		props4.add(p20);
		State s4 = new State("Park", props4);
		//05.04.2018;15:07:08;3.139745474;1.398978949;-1.549136877;-1.500983715;-0.392698646;Park

		
		// setUp Information for state HalfRelease
		Property p21 = new Property("bp", 3.142);
		Property p22 = new Property("map", 1.36);
		Property p23 = new Property("gp", -0.2);
		Property p24 = new Property("sap", -1.34);
		Property p25 = new Property("wp", -1.5);
		List<Property> props5 = new ArrayList<Property>();
		props5.add(p21);
		props5.add(p22);
		props5.add(p23);
		props5.add(p24);
		props5.add(p25);
		State s5 = new State("HalfRelease", props5);
		// setUp Information for state FullRelease
		Property p26 = new Property("bp", 3.142);
		Property p27 = new Property("map", 1.36);
		Property p28 = new Property("gp", 1.0);
		Property p29 = new Property("sap", -1.334);
		Property p30 = new Property("wp", -1.5);
		List<Property> props6 = new ArrayList<Property>();
		props6.add(p26);
		props6.add(p27);
		props6.add(p28);
		props6.add(p29);
		props6.add(p30);
		State s6 = new State("FullRelease", props6);
		// setUp Information for state Wait

		Property p31 = new Property("bp", 3.142);
		Property p32 = new Property("map", 1.36);
		Property p33 = new Property("gp", 1.0);
		Property p34 = new Property("sap", -1.334);
		Property p35 = new Property("wp", -1.5);
		List<Property> props7 = new ArrayList<Property>();
		props7.add(p31);
		props7.add(p32);
		props7.add(p33);
		props7.add(p34);
		props7.add(p35);
		State s7 = new State("Wait", props7);
		// setUp Information for state Retrieve
		Property p36 = new Property("bp", 3.142);
		Property p37 = new Property("map", 1.3);
		Property p38 = new Property("gp", 1.0);
		Property p39 = new Property("sap", -1.3);
		Property p40 = new Property("wp", -1.5);
		List<Property> props8 = new ArrayList<Property>();
		props8.add(p36);
		props8.add(p37);
		props8.add(p38);
		props8.add(p39);
		props8.add(p40);
		State s8 = new State("Retrieve", props8);
		// setUp Information for state RetrieveGrip
		Property p41 = new Property("bp", 3.142);
		Property p42 = new Property("map", 1.3);
		Property p43 = new Property("gp", -0.4);
		Property p44 = new Property("sap", -1.3);
		Property p45 = new Property("wp", -1.5);
		List<Property> props9 = new ArrayList<Property>();
		props9.add(p41);
		props9.add(p42);
		props9.add(p43);
		props9.add(p44);
		props9.add(p45);
		State s9 = new State("RetrieveGrip", props9);
		// setUp Information for state DepositRed
		//05.04.2018;15:05:34;-1.450107694;0.942512572;-0.89017415;1.500982881;-0.392698735;DepositRed
		
		Property p46 = new Property("bp", 1.449);
		Property p47 = new Property("map", 0.942);
		Property p48 = new Property("gp", -0.4);
		Property p49 = new Property("sap", -0.89);
		Property p50 = new Property("wp", 1.5);

		
		List<Property> props10 = new ArrayList<Property>();
		props10.add(p46);
		props10.add(p47);
		props10.add(p48);
		props10.add(p49);
		props10.add(p50);
		State s10 = new State("DepositRed", props10);
		// setUp Information for state ReleaseRed
		Property p51 = new Property("bp", 1.449);
		Property p52 = new Property("map", 0.942);
		Property p53 = new Property("gp", 0.5);
		Property p54 = new Property("sap", -0.89);
		Property p55 = new Property("wp", 1.5);
		List<Property> props11 = new ArrayList<Property>();
		props11.add(p51);
		props11.add(p52);
		props11.add(p53);
		props11.add(p54);
		props11.add(p55);
		/*double bp = Double.parseDouble(information[2]);
		double map = Double.parseDouble(information[3]);
		double sap = Double.parseDouble(information[4]);
		double wp = Double.parseDouble(information[5]);
		double gp = Double.parseDouble(information[6]);
		05.04.2018;15:05:37;-1.450107694;0.942512572;-0.89017415;1.500982881;0.497419268;ReleaseRed*/
		State s11 = new State("ReleaseRed", props11);
		// setUp Information for state DepositGreen
		Property p56 = new Property("bp", 1.5);
		Property p57 = new Property("map", 0.942);
		Property p58 = new Property("gp", 0.4);
		Property p59 = new Property("sap", -0.89);
		Property p60 = new Property("wp", 1.5);
		List<Property> props12 = new ArrayList<Property>();
		props12.add(p56);
		props12.add(p57);
		props12.add(p58);
		props12.add(p59);
		props12.add(p60);
		State s12 = new State("DepositGreen", props12);
		// setUp Information for state ReleaseGreen
		Property p61 = new Property("bp", 1.745);
		Property p62 = new Property("map", 0.942);
		Property p63 = new Property("gp", 0.5);
		Property p64 = new Property("sap", -0.89);
		Property p65 = new Property("wp", 1.5);
		List<Property> props13 = new ArrayList<Property>();
		props13.add(p61);
		props13.add(p62);
		props13.add(p63);
		props13.add(p64);
		props13.add(p65);
		State s13 = new State("ReleaseGreen", props13);
		// setUp Information for state Idle
		Property p66 = new Property("bp", 0);
		Property p67 = new Property("map", 0);
		Property p68 = new Property("gp", 0);
		Property p69 = new Property("sap", 0);
		Property p70 = new Property("wp", 0);
		List<Property> props14 = new ArrayList<Property>();
		props14.add(p66);
		props14.add(p67);
		props14.add(p68);
		props14.add(p69);
		props14.add(p70);
		State s14 = new State("Idle", props14);
		// List states
		List<State> states = new ArrayList<State>();
		states.add(s1);
		states.add(s2);
		states.add(s3);
		states.add(s4);
		states.add(s5);
		states.add(s6);
		states.add(s7);
		states.add(s8);
		states.add(s9);
		states.add(s10);
		states.add(s11);
		states.add(s12);
		states.add(s13);
		states.add(s14);

		// SetUp Block
		Block roboticArm = new Block("roboticArm", states);
		return roboticArm;
	}
	/*
	 * 2 -> gp 0 -> bp 1 -> map 3 -> sap 4 -> wp
	 */
}
