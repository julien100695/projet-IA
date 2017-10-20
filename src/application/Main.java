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
	
	//Environment
	Thread envi = new Thread() {
		 public void run(){
			 System.out.println("Thread1");
			 environment = new Environnement();
			 environment.add_stuff_to_clean(); //Environnement ajoute poussière ou bijou  
		} 	
	};
	//Agent methode informée 
	Thread agen = new Thread() {
		public void run() {			
			System.out.println("Thread2");
			agent = new AgentA();
			
			//start later to let environment initialize
			try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);}			
			for(int i=0;i<10;i++)
			{
			try{Thread.sleep(1000);}catch(InterruptedException e){System.out.println(e);}
			envir = new CaseState[10][10];
			Movelist = new ArrayList<CaseState>();
			EnvironmentList=agent.Observe(environment); //Agent observe l'environnement
			//envir=agent.envir_init(environment);
			close=agent.ChooseNewCase(EnvironmentList); //Agent choisit la case non vide la plus proche
			//envir=agent.init_dist(EnvironmentList, close, envir);
			//Movelist=agent.exploration_informée(close, EnvironmentList, envir);
			//agent.execute_move(Movelist);		
			agent.Move_to_Room(close, environment); //Agent se déplace à la case
			agent.Clean(close, environment); //Agent nettoie
			}
		
		}
	};
	// Agent méthode non informée.
	Thread agen2 = new Thread() {
		public void run() {			
			System.out.println("Thread2");
			agent = new AgentA();
			//start later to let environment initialize
			
			try{Thread.sleep(7000);
			
			}catch(InterruptedException e){System.out.println(e);}
			for(int j = 0 ; j<5; j++)
			{
			System.out.println("il y a "+ agent.Observe(environment).size()+ " cases à visiter");	
	
			
			ArrayList<CaseState> itineraire = new ArrayList<CaseState>();
			itineraire = agent.explorationNonInformée(environment);
			
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
				System.out.println(" il reste "+ reste + " cases à visiter");
			}
		}}
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
