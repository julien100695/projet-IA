package vacuum;

import java.util.ArrayList;

public class AgentA {
	/*
	 * ****** structure of environment*******
	 * enum = {empty, jewel, dust, two}
	 * tableau[0][0];
	 * 
	 * */
	
	
	//time in sec for moving of 1 case
	public final int MOVE_TIME = 5;
	//time in sec for observing
	public final int OBSERVE_TIME = 3;
	
	//Agent's position
	public int posx = 0;
	public int posy = 0;
	
	//tab of actual environment
	public String[][] environment; 
	
	//public ArrayList<CaseState> EnvironmentList;
	
	public void NewListEnvironnement(){
		//destruct all couple then list
		//then create new List
	}
	
	public void init(){
		
		//environment = actualEnvironnement;
	}
	
	//return list of caseState
	//caseState contains object in environment(dust or jewel) and position 
	public ArrayList<CaseState> Observe(){
		int i, j;
		CaseState caseState;
		ArrayList<CaseState> EnvironmentList = new ArrayList<CaseState>();
		
		for (i=0; i < environment.length; i++){
			for (j=0; j < environment.length; j++){
				if (environment[i][j] != "empty"){					
					caseState = new CaseState(i,j,environment[i][j]);
					EnvironmentList.add(caseState);
				}
			}
		}
		
		return EnvironmentList;
	}
	
	//return first caseState at lowest distance
	public CaseState ChooseNewCase(ArrayList<CaseState> EnvironmentList){		
		CaseState caseState = null;
		int sPrec = 20;
		int s;
		for(CaseState casestatet : EnvironmentList){
			
			s = PathCalculValue(casestatet, posx, posy);	
			if(s < sPrec){
				sPrec = s;
				caseState = casestatet;
			}
		}
		return caseState;
	}
	
	//return state of first nearest case from postion in parameter
	public CaseState ChooseAnotherCase (ArrayList<CaseState> EnvironmentList , int posxTemp , int posyTemp){
		CaseState caseState = null;
		int sPrec = 20;
		int s;
		for(CaseState casestatet : EnvironmentList){
			
			s = PathCalculValue(casestatet, posxTemp, posyTemp);	
			if(s < sPrec){
				sPrec = s;
				caseState = casestatet;
			}
		}
		return caseState;
	}
	public int PathCalculValue(CaseState goal, int posxA, int posyA){
		
		return Math.abs(goal.getPosCaseX() + goal.getPosCaseY() - posxA - posyA);
	}
	
	public String Clean(CaseState caseS){
		if(caseS.getState() == "jewel"){
			caseS.setState("empty");
			return "PickUp";
		}
		else
		{
			caseS.setState("empty");
			return "Vaccum";
		}
		
	}
/*	
	public void PickUp(){
		
	}
	*/
}
