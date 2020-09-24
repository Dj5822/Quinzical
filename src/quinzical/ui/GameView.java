package quinzical.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import quinzical.controller.GameController;

public class GameView {
	
	private Scene main;
	
	private Button startbtn;
	private Label catlabel_0,catlabel_1,catlabel_2,catlabel_3,catlabel_4;
	private Label[] catlabels;
	private Button[][] cluebtns;
	private Label hintlabel;
	private TextField input;
	private Button submitbtn,dkbtn;
	private Button returnToMenuButton;
	private int colindex,rowindex;
	
	private String[] categories;
	private String[][][] questions;
	public GameView(GameController controller, int width, int height) {
		
		GridPane mainPane = new GridPane();
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(20);
		mainPane.setHgap(20);
		main = new Scene(mainPane, width, height);
		categories = new String[5];
		questions = new String[5][5][3];
		// Initialize buttons and labels.
		startbtn = new Button("Start/Reset the game");
		catlabels= new Label[] {catlabel_0,catlabel_1,catlabel_2,catlabel_3,catlabel_4};
		for(int i=0;i<5;i++) {
			catlabels[i]=new Label("{Category No: "+i+"}");
		};
		cluebtns = new Button[5][5];
		for(int col=0;col<5;col++) {
			for (int row=1;row<=5;row++) {
				cluebtns[col][row-1]=new Button(Integer.toString(row*100));
				cluebtns[col][row-1].setDisable(true);				
			}
		}
		hintlabel = new Label("Click one of the available buttons above to hear a clue~");
		hintlabel.setVisible(false);
		input = new TextField("Type your answer here and click submit!");
		input.setVisible(false);
		submitbtn = new Button("Submit my answer!");
		submitbtn.setVisible(false);
		dkbtn = new Button("don't know");
		dkbtn.setVisible(false);
		returnToMenuButton = new Button("Return to menu");
		returnToMenuButton.setPrefWidth(200);

		
		// Add buttons and labels to the view.
		mainPane.add(startbtn, 2, 0);
		for(int i=0;i<5;i++) {
			mainPane.add(catlabels[i], i, 1);
		}
		for(int col=0;col<5;col++) {
			for (int row=0;row<5;row++) {
				mainPane.add(cluebtns[col][4-row], col, row+2);
			}
		}
		mainPane.add(hintlabel, 0, 7, 5, 1);
		mainPane.add(input, 0, 8, 5, 1);
		mainPane.add(submitbtn, 0, 9, 2, 1);
		mainPane.add(dkbtn, 2, 9, 2, 1);
		mainPane.add(returnToMenuButton, 0, 10, 3, 1);
		// Button functionality
		startbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.generatedata(categories,questions);
				for(int i=0;i<5;i++) {
					catlabels[i].setText(categories[i]);
				}
				hintlabel.setVisible(true);
				for(int i=0;i<5;i++) {
					cluebtns[i][0].setDisable(false);
				}
			}
		});
		for(colindex=0;colindex<5;colindex++) {
			for (rowindex=0;rowindex<5;rowindex++) {
				cluebtns[colindex][rowindex].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						input.setVisible(true);
						submitbtn.setVisible(true);
						dkbtn.setVisible(true);
					}
				});		
			}
		}
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
