package app;

import java.util.Random;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Path;

public class Environnement {
	public final int SPAWN_TIMER=1300;
	public Room[][] cases;
	Path path = new Path();
	Random rand = new Random();
	int a=0,b=0, k=0, c=0, stop=0;
	
	public Environnement() {
		cases = new Room[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				cases[i][j] = new Room(Case.empty);
			}
		}
		
	}
	
	public void add_stuff_to_clean() {
		//Every 2 Seconds spawns stuff
		
		Task<Void> sleeper = new Task<Void>() {
			protected Void call() throws Exception {
				try {
					Thread.sleep(SPAWN_TIMER);
					} 
				catch (InterruptedException e) {
		        }
		        return null;
		        }
		};
		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
            	do
        		{
        			a=rand.nextInt(9);
        			b=rand.nextInt(9);
        			c=rand.nextInt(2); //0=dust 1=jewel
        			
        			System.out.println("a: "+a+" b: "+b+" c: "+c);
        		}while(cases[a][b].getTypeC()==Case.two);
            	
            	if(c==0 && cases[a][b].getTypeC()==Case.empty)
            		cases[a][b].setTypeC(Case.dust);
            	else if(c==1 && cases[a][b].getTypeC()==Case.empty)
            		cases[a][b].setTypeC(Case.jewel);
            	else if((c==0 && cases[a][b].getTypeC()==Case.jewel) || (c==1 && cases[a][b].getTypeC()==Case.dust) )
            		cases[a][b].setTypeC(Case.two);
                add_stuff_to_clean();

            }
        });
        new Thread(sleeper).start();
	}
}