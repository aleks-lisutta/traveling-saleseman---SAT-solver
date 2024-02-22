
/* 
 I, Aleks Lisutta (321157117), assert that the work I submitted is entirely my own.
 I have not received any part from any other student in the class, nor did I give parts of it for use to others.
 I realize that if my work is found to contain code that is not originally my own, a
 formal case will be opened against me with the BGU disciplinary committee.
*/

// Last Update: 16/12/2019

public class Assignment2 {

	
	/*--------------------------------------------------------
	   Part a: instance representation & Solution verification 
	  -------------------------------------------------------
	 */
	
	// Task 1
	public static boolean hasFlight(int[][] flights, int i, int j) { 
		// Add your code here
		boolean ini=false;
		for(int k=0;k<flights[i].length;k++) {
			if (flights[i][k] == j) {
				ini = true;
			}
		}
		return (ini);
	}
	
	// Task 2
	public static boolean isLegalInstance(int[][] flights) {
		boolean legal=true;
		for(int i=0;i<flights.length;i++){
			for(int j=0;j<flights[i].length;j++){
				if(!(hasFlight(flights,i,flights[i][j]) && hasFlight(flights,flights[i][j],i)) || flights[i][j]==i){
					//condition: if city i has a flight to a city and it doesn't have a flight back or city has flight to itself.
					legal=false;
				}
			}
		}
		return legal;
	}


	// Task 3
	public static boolean isSolution(int[][] flights, int[] tour) {
		// Add your code here
		boolean issol=true;
		if(tour.length!=flights.length){
			throw new IllegalArgumentException("tour too long");
		}
		for(int i=0;i<tour.length;i++){
			if(tour[i]<0 || tour[i]>flights.length-1){
				throw new IllegalArgumentException("Tour out of range at "+i);
			}
			if(i==tour.length-1){
				if(!hasFlight(flights,tour[i],tour[0])){
					issol=false;
				}
			}
			else{
				if(!hasFlight(flights,tour[i],tour[i+1])){
					issol=false;
				}
			}
		}
		for(int i=0;i<flights.length;i++){
			boolean visit=false;
			for(int j=0;j<tour.length;j++){
				if(i==tour[j]){
					visit=true;
				}
			}
			if(!visit){
				issol=false;
			}
		}
		return issol;
	}
	
	/*------------------------------------------------------
	  Part b: Express the problem as a CNF formula, solve 
	  it with a SAT solver, and decode the solution
	  from the satisfying assignment
	  -----------------------------------------------------
	 */
	
	// Task 4
	public static int[][] atLeastOne(int[] vars) {
		// Add your code here
		int len=vars.length;
		int[][] output = new int[1][len];
		output[0]=vars;
		return output;
	}
	
	// Task 5
	public static int[][] atMostOne(int[] vars){
		// Add your code here
		int len=(vars.length*(vars.length-1))/2;// langth of the output array is 1+2+...+n=n(n-1)/2 with n= length of vars
		int[][] output=new int[len][];
		int k=0;//keeps the running index in the output array since i don't run on it directly
		for(int i=1;i<vars.length;i++){
			int[] clause=new int[2];
			for(int j=0;j<i;j++){//runs up to i
				clause[0]=-vars[i];
				clause[1]=-vars[j];
				output[k]=clause;
				k++;
			}
		}
		return output;
	}

	// Task 6
	public static int[][] append(int[][] arr1, int[][] arr2) {
		// Add your code here
		int len1=arr1.length;
		int len2=arr2.length;
		int[][] output=new int[len1+len2][];
		for(int i=0;i<len1;i++){
			output[i]=arr1[i];
		}
		for(int i=0;i<len2;i++){
			output[len1+i]=arr2[i];
		}
		return output;
	}
	
	// Task 7
	public static int[][] exactlyOne(int[] vars){
		// Add your code here
		int[][] output=append(atMostOne(vars),atLeastOne(vars));
		return output;
	}
	
	// Task 8
	public static int[][] diff(int[] I1, int[] I2){
		// Add your code here
		int len=I1.length;
		int[][] output=new int[len][];
		for(int i=0;i<len;i++){
			int[] clause={-I1[i],-I2[i]};
			output[i]=clause;
		}

		return output;
	}

	// Task 9
	public static int[][] createVarsMap(int n) {
		// Add your code here
		int[][] map=new int[n][n];
		for(int i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				map[i-1][j-1]=n*(i-1)+j;
			}
		}
		return map;
	}

	// Task 10
	public static int[][] declareInts(int[][] map) {
		// Add your code here
		int len=map.length;
		int[][] output={};
		for(int i=0;i<len;i++){
			int[][] row=exactlyOne(map[i]);
			output=append(output,row);
		}
		return output;
	}
	
	// Task 11
	public static int[][] allDiff(int[][] map) {
		// Add your code here
		int len=map.length;
		int[][] output={};
		for(int i=1;i<len;i++){
			for(int j=0;j<i;j++){
				int[][] row=diff(map[i],map[j]);
				output=append(output,row);
			}
		}
		return output;
	}
	
	// Task 12
	public static int[][] allStepsAreLegal(int[][] flights, int[][] map) {
		// Add your code here
		int mlen=map.length;
		int[][] output={};
		for(int i=0;i<mlen-1;i++){
			for(int j=0;j<mlen;j++){
				for(int k=0;k<mlen;k++){
					if(!hasFlight(flights,j,k)){
						int[][] clause={{-map[i][j],-map[i+1][k]}};
						output=append(output,clause);
					}
				}
			}
		}
		for(int j=0;j<mlen;j++){
			for(int k=0;k<mlen;k++){
				if(!hasFlight(flights,j,k)){
					int[][] clause={{-map[mlen-1][j],-map[0][k]}};
					output=append(output,clause);
				}
			}
		}

		return output;
	}
	
	// Task 13
	public static void encode(int[][] flights, int[][] map) {
		// Add your code here
		int mlen=map.length;
		int[][] cnf={};
		if(!isLegalInstance(flights)){
			throw new IllegalArgumentException("flights is not a valid instance");
		}
		SATSolver.init(mlen*mlen);
		SATSolver.addClauses(declareInts(map));
		SATSolver.addClauses(allDiff(map));
		boolean[] assignment = SATSolver.getSolution();
		if(assignment != null && assignment.length == 0){
			throw new IllegalArgumentException("map is not a valid instance");
		}

		SATSolver.init(mlen*mlen);
		SATSolver.addClauses(declareInts(map));
		SATSolver.addClauses(allDiff(map));
		cnf=append(cnf,allStepsAreLegal(flights,map));
		SATSolver.addClauses(cnf);
	}
	 
	// Task 14
	public static int[] decode(boolean[] assignment, int[][] map) {
		// Add your code here
		int mlen=map.length;
		int[] output=new int[mlen];
		if(!(assignment.length==mlen*mlen+1)){
			throw new IllegalArgumentException("assignment is either too long or too short");
		}
		for(int i=0;i<mlen;i++){
			for(int j=0;j<mlen;j++){
				if(assignment[i*mlen+j+1]){
					output[i]=j;
				}
			}
		}
		return output;
	}
	
	// Task 15
	public static int[] solve(int[][] flights) {
		// Add your code here
		int n=flights.length;
		if(!isLegalInstance(flights)){
			throw new IllegalArgumentException("flights is not a valid instance");
		}
		int[][] map=createVarsMap(n);
		encode(flights,map);
		boolean[] assignment = SATSolver.getSolution();
		if(assignment==null){
			throw new IllegalArgumentException("given instance unsolvable due to SAT timeout");
		}
		if(assignment.length==0){
			return null;
		}
		int[] s=decode(assignment,map);
		if(!isSolution(flights,s)){
			throw new IllegalArgumentException("solution is invalid");
		}
		return s;
	}
	
	// Task 16
	public static boolean solve2(int[][] flights, int s, int t) {
		// Add your code here
		int n=flights.length;
		if(s>=n||t>=n){//check legality of s and t
			throw new IllegalArgumentException("start or end city in invalid range");
		}
		int[][] st={{s+1},{n*(n-1)+t+1}};//force start location s and end location t

		int[][] map=createVarsMap(n);
		encode(flights,map);
		SATSolver.addClauses(st);//add new condition

		boolean[] assignment = SATSolver.getSolution();
		if(assignment==null){
			throw new IllegalArgumentException("given instance unsolvable due to SAT timeout");
		}
		if(assignment.length==0){
			throw new IllegalArgumentException("no valid solution");
		}
		int[] sol=decode(assignment,map);//1st solution
		if(!isSolution(flights,sol)){
			throw new IllegalArgumentException("solution is invalid");
		}
		//force exclusion of 1st solution
		int[] row=new int[(n-2)];
		int c=0;
		for(int i=1;i<n-1;i++){
			row[c]=-(n*i+sol[i]+1);//
			c++;
		}

		encode(flights,map);
		SATSolver.addClauses(st);//add new conditions
		SATSolver.addClause(row);

		boolean[] assignment2 = SATSolver.getSolution();
		if(assignment2==null){
			throw new IllegalArgumentException("given instance unsolvable due to SAT timeout");
		}
		if(assignment2.length==0){
			throw new IllegalArgumentException("no valid solution");
		}
		int[] sol2=decode(assignment2,map);//2nd solution
		if(!isSolution(flights,sol)){
			throw new IllegalArgumentException("solution is invalid");
		}

		return sol!=sol2;
	}

}


