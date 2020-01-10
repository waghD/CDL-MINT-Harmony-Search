package timeSeries;

import java.util.List;
import java.util.Map;

import design.Property;
import main.PropertyBoundaries;

public class StateQuery {
	
	/***
	 * Generate the query string for TSDB
	 * @param measurement Name of the measurement saved in the TSDB
	 * @param stateAssignments All state assignments for the query
	 * @param variationRange Variation range in percent (between 0 and 1)
	 * @return Query statement
	 */
	public static String createQuery(String measurement, List<Property> stateAssignments,  Map<String,PropertyBoundaries> propertyMap) {
		String result ="";
		result = new StringBuilder()
				.append(generateFrom(stateAssignments))
				.append(generateWhere(stateAssignments, propertyMap))
				.toString();
		
		return result;
	}
	
	/**
	 * Based on the given assignments and the variation range the lower and upper bound for the interval of the values are set
	 * The result is assigned as FROM clause to the sql query
	 * @param stateAssignments All state assignments for the query
	 * @param measurement Name of the measurement saved in the TSDB
	 * @return FROM clause part
	 */
	private static String generateFrom(List<Property> stateAssignments) {
		String result="SELECT ";
		for (Property state : stateAssignments) {
			result += state.getName()+", ";
		}
		result += "time FROM roboticArm ";
		return result;
	}
	
	/**
	 * Based on the given assignments and the variation range the lower and upper bound for the interval of the values are set
	 * The result is assigned as WHERE clause to the sql query
	 * @param stateAssignments All state assignments for the query
	 * @param vR Variation range in percent (between 0 and 1)
	 * @return WHERE clause part
	 */
	private static String generateWhere(List<Property> stateAssignments,  Map<String,PropertyBoundaries> propertyMap) {
		String result="WHERE ";
		int counter = stateAssignments.size();
		int i=1;
		for (Property state : stateAssignments) {
			PropertyBoundaries propBoundaries = propertyMap.get(state.getName());
			if(i<counter) {
				result += state.getName()+">= "+ String.format("%.10f",lowerBound(state.getValue(),propBoundaries.getLower()))+" and " + state.getName()+"<= "+String.format("%.10f",upperBound(state.getValue(),propBoundaries.getUpper()))+" and ";	
			}
			else {
				result += state.getName()+">= "+String.format("%.10f",lowerBound(state.getValue(),propBoundaries.getLower()))+" and " + state.getName()+"<= "+String.format("%.10f",upperBound(state.getValue(),propBoundaries.getUpper()));
			}
			i++;
		}
		result = result.replace(',', '.');
		return result;
	}	

	/***
	 * Calculate upper bound of the interval for the value
	 * @param value Desired value of the assigned property
	 * @param vR Variation range in percent (between 0 and 1)
	 * @return Upper bound of the interval
	 */
	private static double upperBound(double value, double vR) {
		return value + vR;
	}

	/***
	 * Calculate lower bound of the interval for the value
	 * @param value Desired value of the assigned property
	 * @param vR Variation range in percent (between 0 and 1)
	 * @return Lower bound of the interval
	 */
	private static double lowerBound(double value, double vR) {
		return value - vR;
	}
	
	
}
