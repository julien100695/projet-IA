package app;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;
import vacuum.AgentA;

public class Fenetre {
		
	public final int STUFF_TO_CLEAN = 10;
	Rectangle[] dust = new Rectangle[STUFF_TO_CLEAN];
	Rectangle[] jewel = new Rectangle[STUFF_TO_CLEAN];
	Rectangle[] two = new Rectangle[STUFF_TO_CLEAN];
	Circle agent = new Circle(35,35,20, Color.WHITE);
	
	
	
	Path path = new Path();
	int k=0;
	
	public Fenetre() {
	}
	
	public void init_scene_environment(Group root) {
		//draw rooms
		for (int i = 0; i <= 9; i++) {
			path.getElements().add(new MoveTo(-70.0f, (70 * i)));
			path.getElements().add(new HLineTo(9 * 70));
			path.getElements().add(new MoveTo((70 * i), -70.0f));
			path.getElements().add(new VLineTo(9 * 70));
		}
		
		//init stuff to clean in scene
		for(int j=0;j<10;j++){
			dust[j] = new Rectangle(68,68);
			jewel[j] = new Rectangle(68,68);
			two[j] = new Rectangle(68,68);   	
		}
		root.getChildren().add(path);
		root.getChildren().add(agent);
	}
	
	public void update_scene(Group root, Environnement env, AgentA age) {
		//taux de rafraichissement de l'interface
		Task<Void> sleeper2 = new Task<Void>() {
			protected Void call() throws Exception {
				try {
					Thread.sleep(200);
					} 
				catch (InterruptedException e) {
		        }
		        return null;
		        }
		};
		sleeper2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (env.cases[i][j].getTypeC() == Case.dust)
				{
					
						dust[k].setX(1+70 * i);
						dust[k].setY(1+70* j);
					 	dust[k].setWidth(70);
						dust[k].setHeight(70);
						if(dust[k].getFill()!=Color.RED)
						{
							dust[k].setFill(Color.RED);
							root.getChildren().add(dust[k]);
						}
						k++;
				}
				if (env.cases[i][j].getTypeC() == Case.jewel)
				{
					
						jewel[k].setX(70 * i);
						jewel[k].setY(70 * j);
					 	jewel[k].setWidth(70);
						jewel[k].setHeight(70);
						if(jewel[k].getFill()!=Color.BLUE)
						{
							jewel[k].setFill(Color.BLUE);
						root.getChildren().add(jewel[k]);
						}	
						k++;									                	
				}
				if (env.cases[i][j].getTypeC() == Case.two)
				{
					
						two[k].setX(1+70 * i);
						two[k].setY(1+70 * j);
					 	two[k].setWidth(70);
						two[k].setHeight(70);
						if(two[k].getFill()!=Color.VIOLET)
						{
							two[k].setFill(Color.VIOLET);
						root.getChildren().add(two[k]);
						}	
						k++;									                	
				}
				agent.setCenterX((age.posx+1)*35);
				agent.setCenterY((age.posy+1)*35);
			}
		}
		k=0;
		update_scene(root, env, age);
            }
        });
        new Thread(sleeper2).start();
	}

}
