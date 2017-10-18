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
	public final int REFRESH=200;
	Rectangle[][] color = new Rectangle[10][10];
	Circle agent = new Circle(35,35,20, Color.WHITE);
	
	
	
	Path path = new Path();
	int k=0;
	
	public Fenetre() {
	}
	
	public void init_scene_environment(Group root) {
		//draw rooms
		for (int i = 0; i <= 10; i++) {
			path.getElements().add(new MoveTo(-70.0f, (70 * i)));
			path.getElements().add(new HLineTo(10 * 70));
			path.getElements().add(new MoveTo((70 * i), -70.0f));
			path.getElements().add(new VLineTo(10 * 70));
		}
		
		//init stuff to clean in scene
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++) {
				color[i][j] = new Rectangle(68,68);
			}
		}
		root.getChildren().add(path);
		root.getChildren().add(agent);
	}
	
	public void update_scene(Group root, Environnement env, AgentA age) {
		//taux de rafraichissement de l'interface
		Task<Void> sleeper2 = new Task<Void>() {
			protected Void call() throws Exception {
				try {
					Thread.sleep(REFRESH);
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
					
					color[i][j].setX(1+70 * i);
					color[i][j].setY(1+70* j);
					color[i][j].setWidth(70);
					color[i][j].setHeight(70);
					if(color[i][j].getFill()!=Color.RED)
					{
						color[i][j].setFill(Color.RED);
						root.getChildren().add(color[i][j]);
					}
				}
				if (env.cases[i][j].getTypeC() == Case.jewel)
				{
					
					color[i][j].setX(1+70 * i);
					color[i][j].setY(1+70* j);
					color[i][j].setWidth(70);
					color[i][j].setHeight(70);
					if(color[i][j].getFill()!=Color.BLUE)
					{
						color[i][j].setFill(Color.BLUE);
						root.getChildren().add(color[i][j]);
					}										                	
				}
				if (env.cases[i][j].getTypeC() == Case.two)
				{
					
					color[i][j].setX(1+70 * i);
					color[i][j].setY(1+70* j);
					color[i][j].setWidth(70);
					color[i][j].setHeight(70);
					if(color[i][j].getFill()!=Color.VIOLET)
					{
						color[i][j].setFill(Color.VIOLET);
					}								                	
				}
				if (env.cases[i][j].getTypeC() == Case.empty)
					root.getChildren().remove(color[i][j]);

				agent.setCenterX(age.posx*70+35);
				agent.setCenterY(age.posy*70+35);
				
				//System.out.println("posxdisplay: " + age.posx + " posydisplay " + age.posy);
			}
		}
		update_scene(root, env, age);
            }
        });
        new Thread(sleeper2).start();
	}

}
