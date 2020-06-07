package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Tetris extends Application {

// Variable
	
	public static final int MOVE = 25;
	public static final int SIZE = 25;
	public static int XMAX = SIZE *12;
	public static int YMAX = SIZE * 24;
	public static int [][] MESH = new int [XMAX/SIZE][YMAX/SIZE];
	private static Pane groupe = new Pane();
	private static Form object;
	private static Scene scene = new Scene(groupe, XMAX + 150, YMAX);
	public static int score = 0;
	public static int top = 0;
	private static boolean game = true;
	private static Form nextObj = Controller.makeRect();
	private static int LinesNo = 0;
	
	
	//criando as cenas e incializando o jogo
	
	public void main (String[] args) { launch(args);}
	
	@Override
	public void start(Stage stage) throws Exception {
		for (int [] a: MESH) {
			Arrays.fill(a, 0);	
		}
		
		
	//Placar e o Level
		
		Line line = new Line (XMAX, 0, XMAX, YMAX);
		Text scoretext = new Text ("Score: ");
		scoretext.setStyle("-fx-font: 20 arials");
		scoretext.setY(50);
		scoretext.setX(XMAX + 5);
		Text level = new Text("Lines: ");
		level.setStyle("-fx-font: 20 arials");
		level.setY(100);
		level.setX(XMAX + 5);
		level.setFill(Color.BLUE);
		groupe.getChildren().addAll(scoretext, line, level);
		
		
		//Criando o primeiro bloco e fase
		Form a = nextObj;
		groupe.getChildren().addAll(a.a, a.b, a.c, a.d);
		moveOnKeyPressed(a);
		object = a;
		nextObj = Controller.makeRect();
		stage.setScene(scene);
		stage.setTitle("TETRIS DO LEOZERA");
		stage.show();
		
		
		//Temporizador
		Timer fall = new Timer();
		TimerTask task = new TimerTask() {
				public void run () {
				Platform.runLater(new Runnable(){
						public void run () {
							if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0)
								top ++;
							else
								top = 0;
							
							if (top == 2) {
								//Fime de jogo
								Text over = new Text (" JÁ ERA RAPÁ");
								over.setFill(Color.MEDIUMVIOLETRED);
								over.setStyle("-fx-font: 70 arial;");
								over.setY(250);
								over.setX(10);
								groupe.getChildren().add(over);
								game = false;
								
							}
							
							if (top == 15) {
								System.exit(0);			
								
							}
							
							if (game) {
								MoveDown(object);
								scoretext.setText("Score: " + Integer.toString(score));
								level.setText("Lines: " + Integer.toString(LinesNo));
								
								
								
								
							}
							
							
							
							
						}
						
						
				});
				
				}	

				
				
		};
		fall.schedule(task,0, 300);		
	}
	
	
	
	private void moveOnKeyPressed (Form form) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle (KeyEvent event) {
				switch (event.getCode()) {
				
				case RIGHT:
					Controller.MoveRight(form);
					break;
				case DOWN:
					MoveDown(form);
					score++;
					break;
				case LEFT:
					Controller.MoveLeft(form);
					break;
				case UP:
					MoveTurn(form);
					break;

				}
				
			}
			
			
		});
		
		
		
		
	}
	
	
	
	
}
