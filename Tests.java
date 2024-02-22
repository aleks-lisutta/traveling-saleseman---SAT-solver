import java.util.Arrays;

public class Tests {

	public static boolean testHasFlight1(){
		int [][] flights={{2,3},{2,3},{0,1,3},{0,1,2}};
		return Assignment2.hasFlight(flights,1,2) == true;
	}
	
	public static boolean testHasFlight2(){
		int [][] flights={{2,3},{2,3},{0,1,3},{0,1,2}};
		return Assignment2.hasFlight(flights,0,1) == false;
	}
	
	public static boolean testHasFlight3(){
		int [][] flights={{2,3},{2,3},{0,1,3},{0,1,2}};
		return Assignment2.hasFlight(flights,1,1) == false;
	}
	
	public static boolean testIsLegalInstance1(){
		int [][] flights={{2,3},{2,3},{0,1,3},{0,1,2}};
		return Assignment2.isLegalInstance(flights) == true;
	}
	
	public static boolean testIsLegalInstance2(){
		int [][] flights={{2,3},{2,3},{0,3},{0,1,2}};
		return Assignment2.isLegalInstance(flights) == false;
	}
	
	public static boolean testIsLegalInstance3(){
		int [][] flights={{2,3},{1,3},{0,1,3},{0,2}};
		return Assignment2.isLegalInstance(flights) == false;
	}
	
	public static boolean testIsLegalInstance4(){
		int [][] flights={{2,3},{1,2,3},{0,1,3},{0,1,2}};
		return Assignment2.isLegalInstance(flights) == false;
	}
	
	public static boolean testIsSolution1(){
		int [][] flights={{2,3},{2,3},{0,1,3},{0,1,2}};
		int [] tour={2,0,3,1};
		return Assignment2.isSolution(flights,tour) == true;
	}
	
	public static boolean testIsSolution2(){
		int [][] flights={{2,3},{2,3},{0,1,3},{0,1,2}};
		int [] tour={1,3,2,0};
		return Assignment2.isSolution(flights,tour) == false;
	}
	
	public static boolean testIsSolution3(){
		int [][] flights={{2,3},{2,3},{0,1,3},{0,1,2}};
		int [] tour={1,5,2,0};
		boolean gotException = false;
		try {
			Assignment2.isSolution(flights,tour);
		} 
		catch(IllegalArgumentException e) {
			gotException = true;
		}
		return gotException;
	}
	
	public static boolean testAtLeastOne1() {
		int[] vars = {1,2,3,4};
		
		SATSolver.init(vars.length);
		SATSolver.addClauses(Assignment2.atLeastOne(vars));
		// fix all but x4 to be false
		SATSolver.addClauses(new int[][]{{-1},{-2},{-3}});
		// expect that the model is satisfiable and the assignment of x4 is true
		boolean[] assignment = SATSolver.getSolution();
		return assignment != null &&
				assignment.length == vars.length + 1 && 
				assignment[4];
	}
	
	public static boolean testAtLeastOne2() {
		int[] vars = {1,2,3,4};
		
		SATSolver.init(vars.length);
		SATSolver.addClauses(Assignment2.atLeastOne(vars));
		// fix all vars to be false
		SATSolver.addClauses(new int[][]{{-1},{-2},{-3},{-4}});
		// model should be unsatisfiable 
		return isUnsatisfiable();
	}
	
	public static boolean testAtMostOne1() {
		int[] vars = {1,2,3,4};
		
		SATSolver.init(vars.length);
		SATSolver.addClauses(Assignment2.atMostOne(vars));
		// fix all vars to be false
		SATSolver.addClauses(new int[][]{{-1},{-2},{-3},{-4}});
		// model should be unsatisfiable 
		return isSatisfiable();
	}
	
	public static boolean testAtMostOne2() {
		int[] vars = {1,2,3,4};
		
		SATSolver.init(vars.length);
		SATSolver.addClauses(Assignment2.atMostOne(vars));
		// fix all but x1=true,x2=true
		SATSolver.addClauses(new int[][]{{1},{2}});
		// model should be unsatisfiable 
		return isUnsatisfiable();
	}
	
	public static boolean testExactlyOne1() {
		int[] vars = {1,2,3,4};
		
		SATSolver.init(vars.length);
		SATSolver.addClauses(Assignment2.exactlyOne(vars));
		// fix x1=true and the rest as false
		SATSolver.addClauses(new int[][]{{1},{-2},{-3},{-4}});
		// model should be satisfiable 
		return isSatisfiable();
	}
	
	public static boolean testExactlyOne2() {
		int[] vars = {1,2,3,4};
		
		SATSolver.init(vars.length);
		SATSolver.addClauses(Assignment2.exactlyOne(vars));
		// // fix x1=true and x2=true and the rest as false
		SATSolver.addClauses(new int[][]{{1},{2},{-3},{-4}});
		// model should be unsatisfiable 
		return isUnsatisfiable();
	}
	
	public static boolean testDiff1() {
		
		int[] I1 = {1,2,3,4};
		int[] I2 = {5,6,7,8};
	
		SATSolver.init(8);
		SATSolver.addClauses(Assignment2.exactlyOne(I1));
		SATSolver.addClauses(Assignment2.exactlyOne(I2));
		SATSolver.addClauses(Assignment2.diff(I1,I2));
		// add constraints which fix the integers: I1=3, I2=1
		SATSolver.addClauses(new int[][]{{4},{6}});
		
		return isSatisfiable();
	}
	
	public static boolean testDiff2() {
		
		int[] I1 = {1,2,3,4};
		int[] I2 = {5,6,7,8};
	
		SATSolver.init(8);
		SATSolver.addClauses(Assignment2.exactlyOne(I1));
		SATSolver.addClauses(Assignment2.exactlyOne(I2));
		SATSolver.addClauses(Assignment2.diff(I1,I2));
		// add constraints which fix the integers: I1=3, I2=3
		SATSolver.addClauses(new int[][]{{4},{8}});
		
		return isUnsatisfiable();
	}
	
	
	/* 
	 * This test checks allDiff constraint assuming that
	 * exactlyOne is correct
	 */
	public static boolean testAllDiff1() {
		
		int[] I1 = {1,2,3};
		int[] I2 = {4,5,6};
		int[] I3 = {7,8,9};
		
		SATSolver.init(9);
		SATSolver.addClauses(Assignment2.exactlyOne(I1));
		SATSolver.addClauses(Assignment2.exactlyOne(I2));
		SATSolver.addClauses(Assignment2.exactlyOne(I3));
		SATSolver.addClauses(Assignment2.allDiff(new int[][] {I1,I2,I3}));
		// add constraints which fix the integers: I1=0, I2=1, I3=2
		SATSolver.addClauses(new int[][]{{1},{5},{9}});

		return isSatisfiable();
	}
	

	public static boolean testAllDiff2() {
		
		int[] I1 = {1,2,3};
		int[] I2 = {4,5,6};
		int[] I3 = {7,8,9};
		
		SATSolver.init(9);
		SATSolver.addClauses(Assignment2.exactlyOne(I1));
		SATSolver.addClauses(Assignment2.exactlyOne(I2));
		SATSolver.addClauses(Assignment2.exactlyOne(I3));
		SATSolver.addClauses(Assignment2.allDiff(new int[][] {I1,I2,I3}));
		// add constraints which fix the integers:I1=0, I2=0, I3=2
		SATSolver.addClauses(new int[][]{{1},{4},{9}});
		
		return isUnsatisfiable();
	}
	
	
	public static boolean testDecode() {
		
		boolean[] assignment = {false, 
			    true, false, false, false,  
			    false, false, false, true,
			    false, true, false, false, 
			    false, false, true, false};

			int[][] map = {
			    {1,2,3,4},
			    {5,6,7,8},
			    {9,10,11,12},
			    {13,14,15,16}};

			int[] decoded = Assignment2.decode(assignment, map);
			int[] expected = {0,3,1,2};
			
			return Arrays.equals(decoded, expected);
	}
	
	public static boolean testSolve1() {
		return testSolve(ExamplesFlights.getExampleSatN4(), true);
	}
	
	public static boolean testSolve2() {
		return testSolve(ExamplesFlights.getExampleUnsatN4(), false);
	}
	
	public static boolean testSolve3() {
		return testSolve(ExamplesFlights.getExampleUnsatN10(), false);
	}
	
	public static boolean testSolve4() {
		return testSolve(ExamplesFlights.getExampleSatN16(), true);
	}
	
	public static boolean testSolve(int[][] flights, boolean isSat) {
		int[] sol = null;
		try {
			sol = Assignment2.solve(flights);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (isSat) {
			return sol != null;
		}
		return sol == null;
	}
	
	
	public static void report(String testcase, boolean res) {
		System.out.println(testcase + ": " + (res? "ok" : "wrong"));
	}
	
	
	public static boolean isSatisfiable() {
		boolean[] assignment = SATSolver.getSolution();
		return assignment != null &&
				assignment.length > 0;
	}
	
	public static boolean isUnsatisfiable() {
		boolean[] assignment = SATSolver.getSolution();
		return assignment != null &&
				assignment.length == 0;
	}
	
	
	public static void main(String[] args) {
		
		report("testHasFlight1()", testHasFlight1());
		report("testHasFlight2()", testHasFlight2());
		report("testHasFlight3()", testHasFlight3());

		report("testIsLegalInstance1()", testIsLegalInstance1());
		report("testIsLegalInstance2()", testIsLegalInstance2());
		report("testIsLegalInstance3()", testIsLegalInstance3());
		
		report("testIsSolution1()", testIsSolution1());
		report("testIsSolution2()", testIsSolution2());
		report("testIsSolution3()", testIsSolution3());
		
		report("testAtLeastOne1()", testAtLeastOne1());
		report("testAtLeastOne2()", testAtLeastOne2());
		
		report("testAtMostOne1()", testAtMostOne1());
		report("testAtMostOne2()", testAtMostOne2());
		
		report("testExactlyOne1()", testExactlyOne1());
		report("testExactlyOne2()", testExactlyOne2());
		
		report("testDiff1()", testDiff1());
		report("testDiff2()", testDiff2());
		
		report("testAllDiff1()", testAllDiff1());
		report("testAllDiff2()", testAllDiff2());
		
		report("testDecode()", testDecode());
		
		report("testSolve1()", testSolve1());
		report("testSolve2()", testSolve2());
		report("testSolve3()", testSolve3());
		report("testSolve4()", testSolve4());
		
	}
}

