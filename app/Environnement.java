package app;

import java.util.Random;

import app.Case;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Environnement extends Application {
	
	
	public Rectangle rect = new Rectangle();
	Rectangle[] dust = new Rectangle[20];
	Rectangle[] jewel = new Rectangle[20];
	Rectangle[] two = new Rectangle[20];
 	Rectangle test = new Rectangle();
	public static final int SCENE_SIZE = 800;
	public Room[][] cases;
	Path path = new Path();
	Random rand = new Random();
	int a=0,b=0, k=0, c=0, stop=0;
	
	
	public static void main(String[] args) { launch(args); }
	public Environnement() {
		cases = new Room[10][10];
				 		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cases[j][i] = new Room(Case.empty);
			}
		}
		/*cases[2][1].setTypeC(Case.dust);
		cases[2][3].setTypeC(Case.two);*/
		
	}

    public void start(Stage stage) {
	        Group root = new Group();
	        for(int j=0;j<10;j++)
		    {
		    	dust[j] = new Rectangle(68,68);
		    	dust[j].setFill(Color.GREEN);
		    	jewel[j] = new Rectangle(68,68);
		    	jewel[j].setFill(Color.GREEN);
		    	two[j] = new Rectangle(68,68);
		    	two[j].setFill(Color.GREEN);
		    	
		    }
	        Scene scene = new Scene(root, 700, 700);
	        rect.setWidth(700);
			rect.setHeight(700);
			rect.setFill(Color.GREEN);
			root.getChildren().add(rect);
			
			
	        //draw rooms
			for (int i = 0; i <= 9; i++) {
				path.getElements().add(new MoveTo(-70.0f, (70 * i)));
				path.getElements().add(new HLineTo(9 * 70));
				path.getElements().add(new MoveTo((70 * i), -70.0f));
				path.getElements().add(new VLineTo(9 * 70));
			}
			root.getChildren().add(path);
			
			//spawn dust or jewel
			Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			    	if(stop==0)
			    	{	
			    	do
					{
						a=rand.nextInt(9);
						b=rand.nextInt(9);
						c=rand.nextInt(2); //0=dust 1=jewel
						
						System.out.println("a"+a+" b "+b+" c"+c);
					}while(cases[a][b].getTypeC()==Case.two );
			    	
			    	//conditions for content of room
			    	if(c==0 && cases[a][b].getTypeC()==Case.empty)
			    		cases[a][b].setTypeC(Case.dust);
			    	else if(c==1 && cases[a][b].getTypeC()==Case.empty)
			    		cases[a][b].setTypeC(Case.jewel);
			    	else if((c==0 && cases[a][b].getTypeC()==Case.jewel) || (c==1 && cases[a][b].getTypeC()==Case.dust) )
			    		cases[a][b].setTypeC(Case.two);
			    	
			    	
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 9; j++) {
							if (cases[i][j].getTypeC() == Case.dust)
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
							if (cases[i][j].getTypeC() == Case.jewel)
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
							if (cases[i][j].getTypeC() == Case.two)
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
						}
					}
					if(k==10)stop=1;
					k=0;
			    }
			    }	
			}));
									
			fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
			fiveSecondsWonder.play();
	        stage.setTitle("My JavaFX Application");
	        stage.setScene(scene);
	        stage.show();
    }	
}