package application;

import java.util.ArrayList;

import app.Environnement;
import app.Fenetre;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import vacuum.AgentA;
import vacuum.CaseState;


public class Main extends Application {
	
	Environnement environment;
	AgentA agent;
	public ArrayList<CaseState> EnvironmentList;
	public ArrayList<CaseState> Movelist;
	public CaseState close;
	public CaseState[][] envir;
	int dist = 0;
	int stuff_to_spawn=10;
	int stop=10;
	
	//Environment
	Thread envi = new Thread() {
		 public void run(){
			 System.out.println("Thread1");
			 environment = new Environnement();
			 
			 while(stuff_to_spawn>=0)
			 {
			 environment.add_stuff_to_clean(); //Environnement ajoute poussi�re ou bijou
			 try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);}
			 stuff_to_spawn--;
			 }
		} 	
	};
	//Agent methode inform�e 
	Thread agen = new Thread() {
		public void run() {			
			System.out.println("Thread2");
			agent = new AgentA();
			
			//start later to let environment initialize
			try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}			
			
			while(stop>0){
			envir = new CaseState[10][10];
			Movelist = new ArrayList<CaseState>();
			
			EnvironmentList=agent.Observe(environment); //Agent observe l'environnement (toutes les salles ayant un objet sont not�es)
			close=agent.ChooseNewCase(EnvironmentList); //Agent choisit la case non vide la plus proche
			
			//envir=agent.init_dist(EnvironmentList, close, envir);
			//Movelist=agent.exploration_inform�e(close, EnvironmentList, envir); //Exploration inform�e note le chemin dans movelist
			//agent.execute_move(Movelist); //Effectue les d�placements de la movelist
			
			try{Thread.sleep(100);}catch(InterruptedException e){System.out.println(e);}
			
			agent.Move_to_Room(close, environment); //Agent se d�place � la case en x puis en y
			agent.Clean(close, environment); //Agent nettoie
			stop=EnvironmentList.size();
			stop--;
			
			}
		}
	};
	// Agent m�thode non inform�e.
	Thread agen2 = new Thread() {
		public void run() {			
			System.out.println("Thread2");
			agent = new AgentA();
			//start later to let environment initialize
			
			try{Thread.sleep(7000);
			
			}catch(InterruptedException e){System.out.println(e);}
			while(stop>0)
			{
			System.out.println("il y a "+ agent.Observe(environment).size()+ " cases � visiter");	
	
			
			ArrayList<CaseState> itineraire = new ArrayList<CaseState>();
			itineraire = agent.explorationNonInform�e(environment);
			
			System.out.println("l itineraire est de " + itineraire.size() + " de cases");
			for (int i=0 ; i<itineraire.size();i++)
			{
				System.out.println("il est en x = " + itineraire.get(i).getPosCaseX() + " y = " + itineraire.get(i).getPosCaseY() );
			}
			for(int i = 0 ; i<=itineraire.size();i++)
			{
				agent.Move_to_Room(itineraire.get(i), environment);
				agent.Clean(itineraire.get(i), environment);
				
				int reste = itineraire.size() - i;
				System.out.println(" il reste "+ reste + " cases � visiter");
			}
			stop--;
			}
		}
	};
	@Override
	public void start(Stage primaryStage) {
		envi.start();
		agen2.start();
		
		try {
			
			//Configuration Fenetre
			Group root = new Group();
			Scene scene = new Scene(root, 700, 700, Color.GREEN);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Mansion");
	        primaryStage.setResizable(false);
	        
	        //init fenetre
	        Fenetre fenetre = new Fenetre();        
	        
	        fenetre.init_scene_environment(root);
	        
	        fenetre.update_scene(root, environment,agent);
	        
	        //Show Interface
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
