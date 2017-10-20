package vacuum;

import java.util.ArrayList;

import app.Case;
import app.Environnement;

public class AgentA {		
	//time in sec for moving of 1 case
	public final int MOVE_TIME = 250;
	//time in sec for observing
	public final int OBSERVE_TIME = 3;
	
	//Agent's position
	public int posx = 0;
	public int posy = 0;
	
	public int energie = 0;
	
	//tab of actual environment
	//public app.Room[][] environment; 
	
	public ArrayList<CaseState> EnvironmentList;
	public ArrayList<CaseState> Movelist;
	public CaseState[][] envir;
	
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
					caseState = new CaseState(i,j,environment.cases[i][j].getTypeC(),0);
					EnvironmentList.add(caseState);
				}
			}
		}
		
		return EnvironmentList;
	}
	
	public CaseState[][] envir_init (Environnement environment) {
		CaseState[][] envir = new CaseState[10][10];
		CaseState caseState;
		for (int i=0; i < 10; i++){
			for (int j=0; j < 10; j++){
				caseState = new CaseState(i,j,environment.cases[i][j].getTypeC(),0);
				envir[i][j]=caseState;
			}
		}	
		return envir;
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
	//set distance to goal from all rooms
	public CaseState[][] init_dist(ArrayList<CaseState> EnvironmentList, CaseState goal, CaseState[][] envir) {
		for(CaseState casestate : EnvironmentList) {
			casestate.dist=PathCalculValue(goal, casestate.posCaseX, casestate.posCaseY);
		}
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				envir[i][j].dist=PathCalculValue(goal, envir[i][j].posCaseX, envir[i][j].posCaseY);
				//System.out.println(envir[i][j].dist);
			}
		}
		return envir;
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
			try{Thread.sleep(MOVE_TIME);}catch(InterruptedException e){System.out.println(e);}
			posx++;
			energie++;
			}
		}
		else {
			for(int i=posx;i>caseS.posCaseX;i--) {
				try{Thread.sleep(MOVE_TIME);}catch(InterruptedException e){System.out.println(e);}
				posx--;
				energie++;
			}
		}	
		if(posy<caseS.posCaseY) {
			for(int j=posy;j<caseS.posCaseY;j++) {
				try{Thread.sleep(MOVE_TIME);}catch(InterruptedException e){System.out.println(e);}
				posy++;
				energie++;
			}
		}	
			else {
				for(int i=posy;i>caseS.posCaseY;i--) {
					try{Thread.sleep(MOVE_TIME);}catch(InterruptedException e){System.out.println(e);}
					posy--;
					energie++;
				}
			}
				energie++;
				System.out.println("posx: " + posx + " posy " + posy);
				
	}
	
	public void Move_left()
	{
		posx--;
		energie++;
	}
	public void Move_right()
	{
		posx++;
		energie++;
	}
	public void Move_up()
	{
		posy--;
		energie++;
	}
	public void Move_down()
	{
		posy++;
		energie++;
	}
	
	/*public ArrayList<CaseState> exploration_informée(CaseState goal, ArrayList<CaseState> EnvironmentList, CaseState[][] envir)
	{
		int posinitialex=posx, posinitialey=posy;
		int distance=envir[posx][posy].dist;
		System.out.println(posx+" "+posy);
		ArrayList<CaseState> Movelist = new ArrayList<CaseState>();
		while(distance!=0)
		{
			try{Thread.sleep(MOVE_TIME);}catch(InterruptedException e){System.out.println(e);}
			System.out.print("distance: "+distance+" ");
			
			
			if(posinitialex==9 && posinitialey<9 && posinitialey>0) {
				if(envir[posinitialey+1][posinitialex].dist<distance) {
				distance=envir[posinitialey+1][posinitialex].dist;
				Movelist.add(envir[posinitialey+1][posinitialex]);
				posinitialey++;
				}
				if(envir[posinitialey][posinitialex-1].dist<distance) {
					distance=envir[posinitialey][posinitialex-1].dist;
					Movelist.add(envir[posinitialey][posinitialex-1]);
					posinitialex--;
				}
				if(envir[posinitialey-1][posinitialex].dist<distance) {
					distance=envir[posinitialey-1][posinitialex].dist;
					Movelist.add(envir[posinitialey-1][posinitialex]);
					posinitialey--;
				}
				System.out.println("1");
			}
			
			else if(posinitialex<9 && posinitialey==9 && posinitialex>0) {
				if(envir[posinitialey][posinitialex+1].dist<distance) {
				distance=envir[posinitialey][posinitialex+1].dist;
				Movelist.add(envir[posinitialey][posinitialex+1]);
				posinitialex++;
				}
				if(envir[posinitialey][posinitialex-1].dist<distance) {
					distance=envir[posinitialey][posinitialex-1].dist;
					Movelist.add(envir[posinitialey][posinitialex-1]);
					posinitialex--;
				}
				if(envir[posinitialey-1][posinitialex].dist<distance) {
					distance=envir[posinitialey-1][posinitialex].dist;
					Movelist.add(envir[posinitialey-1][posinitialex]);
					posinitialey--;
				}
				System.out.println("2");
			}
			else if(posinitialex==0 && posinitialey<9 && posinitialey>0) {
				if(envir[posinitialey][posinitialex+1].dist<distance) {
					distance=envir[posinitialey][posinitialex+1].dist;
					Movelist.add(envir[posinitialey][posinitialex+1]);
					posinitialex++;
				}
				if(envir[posinitialey+1][posinitialex].dist<distance) {
				distance=envir[posinitialey+1][posinitialex].dist;
				Movelist.add(envir[posinitialey+1][posinitialex]);
				posinitialey++;
				}
				if(envir[posinitialey-1][posinitialex].dist<distance) {
					distance=envir[posinitialey-1][posinitialex].dist;
					Movelist.add(envir[posinitialey-1][posinitialex]);
					posinitialey--;
				}
			}
			else if(posinitialex<9 && posinitialex>0 && posinitialey==0) {
				if(envir[posinitialey][posinitialex+1].dist<distance) {
					distance=envir[posinitialey][posinitialex+1].dist;
					Movelist.add(envir[posinitialey][posinitialex+1]);
					posinitialex++;
					System.out.println("3");
				}
				else if(envir[posinitialey+1][posinitialex].dist<distance) {
				distance=envir[posinitialey+1][posinitialex].dist;
				Movelist.add(envir[posinitialey+1][posinitialex]);
				posinitialey++;
				System.out.println("4");
				}
				else if(envir[posinitialey][posinitialex-1].dist<distance) {
					distance=envir[posinitialey][posinitialex-1].dist;
					Movelist.add(envir[posinitialey][posinitialex-1]);
					posinitialex--;
					System.out.println("5");}
			}
			
			else if(posinitialex==0 && posinitialey==0) {
				if(envir[posinitialey][posinitialex+1].dist<distance) {
					distance=envir[posinitialey][posinitialex+1].dist;
					Movelist.add(envir[posinitialey][posinitialex+1]);
					posinitialex++;					
				}
				if(envir[posinitialey+1][posinitialex].dist<distance) {
				distance=envir[posinitialey+1][posinitialex].dist;
				Movelist.add(envir[posinitialey+1][posinitialex]);
				posinitialey++;
				}
				
			}
			else if(posinitialex==9 && posinitialey==9) {
				if(envir[posinitialey][posinitialex-1].dist<distance) {
					distance=envir[posinitialey][posinitialex-1].dist;
					Movelist.add(envir[posinitialey][posinitialex-1]);
					posinitialex--;					
				}
				if(envir[posinitialey-1][posinitialex].dist<distance) {
				distance=envir[posinitialey-1][posinitialex].dist;
				Movelist.add(envir[posinitialey-1][posinitialex]);
				posinitialey--;					}
				System.out.println("6");
			}
			else if(posinitialex==0 && posinitialey==9) {
				if(envir[posinitialey][posinitialex+1].dist<distance) {
					distance=envir[posinitialey][posinitialex+1].dist;
					Movelist.add(envir[posinitialey][posinitialex+1]);
					posinitialex++;					
				}
				else if(envir[posinitialey-1][posinitialex].dist<distance) {
					distance=envir[posinitialey-1][posinitialex].dist;
					Movelist.add(envir[posinitialey-1][posinitialex]);
					posinitialey--;				}
				System.out.println("7");
			}
			else if(posinitialex==9 && posinitialey==0) {
				if(envir[posinitialey+1][posinitialex].dist<distance) {
					distance=envir[posinitialey+1][posinitialex].dist;
					Movelist.add(envir[posinitialey+1][posinitialex]);
					posinitialey++;					
				}
				else if(envir[posinitialey][posinitialex-1].dist<distance) {
					distance=envir[posinitialey][posinitialex-1].dist;
					Movelist.add(envir[posinitialey][posinitialex-1]);
					posinitialex--;				}
				System.out.println("7");
			}
			else if(posinitialex<9 && posinitialex>0 && posinitialey<9 && posinitialey>0)
			{
				if(envir[posinitialey][posinitialex+1].dist<distance) {
					distance=envir[posinitialey][posinitialex+1].dist;
					Movelist.add(envir[posinitialey][posinitialex+1]);
					posinitialex++;					
				}
				else if(envir[posinitialey+1][posinitialex].dist<distance) {
				distance=envir[posinitialey+1][posinitialex].dist;
				Movelist.add(envir[posinitialey+1][posinitialex]);
				posinitialey++;				}
				else if(envir[posinitialey][posinitialex-1].dist<distance) {
					distance=envir[posinitialey][posinitialex-1].dist;
					Movelist.add(envir[posinitialey][posinitialex-1]);
					posinitialex--;				}
				else if(envir[posinitialey-1][posinitialex].dist<distance) {
					distance=envir[posinitialey-1][posinitialex].dist;
					Movelist.add(envir[posinitialey-1][posinitialex]);
					posinitialey--;				}
				System.out.println("7");
			}
		}
		return Movelist;
	}
	public void execute_move(ArrayList<CaseState> Movelist)
	{
		System.out.println("move");
		for(int i=0;i<Movelist.size();i++){
			try{Thread.sleep(MOVE_TIME);}catch(InterruptedException e){System.out.println(e);}
			posx=Movelist.get(i).posCaseX;
			posy=Movelist.get(i).posCaseY;
		}
	}*/
}
