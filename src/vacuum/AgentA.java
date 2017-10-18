package vacuum;

import java.util.ArrayList;

import app.Case;
import app.Environnement;

public class AgentA {		
	//time in sec for moving of 1 case
	public final int MOVE_TIME = 5;
	//time in sec for observing
	public final int OBSERVE_TIME = 3;
	
	//Agent's position
	public int posx = 0;
	public int posy = 0;
	
	//tab of actual environment
	//public app.Room[][] environment; 
	
	public ArrayList<CaseState> EnvironmentList;
	
	public void NewListEnvironnement(){
		//destruct all couple then list
		//then create new List
	}
	
	public void init(){

	}
	
	//return list of caseState
	//caseState contains object in environment(dust or jewel) and position 
	public ArrayList<CaseState> Observe(Environnement environment){
		int i, j;
		CaseState caseState;
		ArrayList<CaseState> EnvironmentList = new ArrayList<CaseState>();
		
		
		for (i=0; i < 10; i++){
			for (j=0; j < 10; j++){
				if (environment.cases[i][j].getTypeC() != Case.empty){					
					caseState = new CaseState(i,j,environment.cases[i][j].getTypeC());
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
	
	//return state of first nearest case from position in parameter
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
	
	public String Clean(CaseState caseS, Environnement environment){
		if(caseS.getState() == Case.jewel){
			caseS.setState(Case.empty);
			environment.cases[caseS.posCaseX][caseS.posCaseY].setTypeC(Case.empty);
			return "PickUp";
		}
		else
		{
			caseS.setState(Case.empty);
			environment.cases[caseS.posCaseX][caseS.posCaseY].setTypeC(Case.empty);
			return "Vaccum";
		}
	}	
		
	public void Move_to_Room(CaseState caseS, Environnement environment){
		if(posx<caseS.posCaseX) {
			for(int i=posx;i<caseS.posCaseX;i++) {
			try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
			posx++;
			}
		}
		else {
			for(int i=posx;i>caseS.posCaseX;i--) {
				try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
				posx--;	
			}
		}	
		if(posy<caseS.posCaseY) {
			for(int j=posy;j<caseS.posCaseY;j++) {
				try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
				posy++;
			}
		}	
			else {
				for(int i=posy;i>caseS.posCaseY;i--) {
					try{Thread.sleep(500);}catch(InterruptedException e){System.out.println(e);}
					posy--;	
				}
			}		
				System.out.println("posx: " + posx + " posy " + posy);			
	}
	public void Move_left()
	{
		posx--;
	}
	public void Move_right()
	{
		posx++;
	}
	public void Move_up()
	{
		posy--;
	}
	public void Move_down()
	{
		posy++;
	}
/*	
	public void PickUp(){
		
	}
	*/
}
