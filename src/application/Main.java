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
	public CaseState close;
	
	Thread envi = new Thread() {
		 public void run(){
			 System.out.println("Thread1");
			 environment = new Environnement();
			 environment.add_stuff_to_clean();  
		} 	
	};
	
	Thread agen = new Thread() {
		public void run() {
			try{Thread.sleep(5000);}catch(InterruptedException e){System.out.println(e);} 
			System.out.println("Thread2");
			agent = new AgentA();
			EnvironmentList=agent.Observe(environment);
			close=agent.ChooseNewCase(EnvironmentList);
			System.out.println("x: " + close.posCaseX +" y: " + close.posCaseY + " state :" + close.state);
		
		}
	};
	
	@Override
	public void start(Stage primaryStage) {
		envi.start();
		agen.start();
		try {
			
			//Configuration Fenetre
			Group root = new Group();
			Scene scene = new Scene(root, 700, 700, Color.GREEN);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Mansion");
	        primaryStage.setResizable(false);
	        
	        //init fenetre
	        Fenetre fenetre = new Fenetre();
	        
	        agent = new AgentA();
	        
	        
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
