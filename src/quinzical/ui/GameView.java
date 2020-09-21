package quinzical.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import quinzical.controller.GameController;

public class GameView {
	
	private Scene main;
	
	private Label title;
	private Label catlabel_0,catlabel_1,catlabel_2,catlabel_3,catlabel_4;
	private Label[] catlabels;
	private Button cluebtn_01,cluebtn_02,cluebtn_03,cluebtn_04,cluebtn_05,cluebtn_11,cluebtn_12,cluebtn_13,
	cluebtn_14,cluebtn_15,cluebtn_21,cluebtn_22,cluebtn_23,cluebtn_24,cluebtn_25,cluebtn_31,cluebtn_32,cluebtn_33,
	cluebtn_34,cluebtn_35,cluebtn_41,cluebtn_42,cluebtn_43,cluebtn_44,cluebtn_45;
	private Button[][] cluebtns;
	private Button returnToMenuButton;
	
	public GameView(GameController controller, int width, int height) {
		
		GridPane mainPane = new GridPane();
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(20);
		mainPane.setHgap(20);
		main = new Scene(mainPane, width, height);
		// Initialize buttons and labels.
		title = new Label("Game View");
		catlabels= new Label[] {catlabel_0,catlabel_1,catlabel_2,catlabel_3,catlabel_4};
		for(int i=0;i<5;i++) {
			catlabels[i]=new Label("{category name}");
		}
		cluebtns = new Button[5][5];
		for(int col=0;col<5;col++) {
			for (int row=1;row<=5;row++) {
				cluebtns[col][row-1]=new Button(Integer.toString(row*100));
				if(row>=2) {
					cluebtns[col][row-1].setDisable(true);
				}
				
			}
		}
		returnToMenuButton = new Button("Return to menu");
		
		// Add buttons and labels to the view.
		mainPane.add(title, 2, 0);
		for(int i=0;i<5;i++) {
			mainPane.add(catlabels[i], i, 1);
		}
		for(int col=0;col<5;col++) {
			for (int row=0;row<5;row++) {
				mainPane.add(cluebtns[col][4-row], col, row+2);
			}
		}
		mainPane.add(returnToMenuButton, 2, 10);
		// Button functionality
		returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.returnToMenu();
			}
		});		
	}
	
	public Scene getScene() {
		return main;
	}
}
