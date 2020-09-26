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
	private Label winning;
	private Label[] catlabels;
	private Button[][] cluebtns;
	private Label endinglabel;
	private Label hintlabel;
	private TextField input;
	private Button submitbtn,dkbtn;
	private Button returnToMenuButton;

	
	public GameView(GameController controller, int width, int height) {
		
		GridPane mainPane = new GridPane();
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(20);
		mainPane.setHgap(20);
		main = new Scene(mainPane, width, height);
		// Initialize buttons and labels.
		startbtn = new Button("Start/Reset the game");
		winning = new Label("Current Worth: $0");
		catlabels= new Label[5] ;
		for(int i=0;i<5;i++) {
			catlabels[i]=new Label("{Category No: "+i+"}");
			catlabels[i].setMinWidth(150);
		};
		cluebtns = new Button[5][5];
		for(int col=0;col<5;col++) {
			for (int row=1;row<=5;row++) {
				cluebtns[col][row-1]=new Button(Integer.toString(row*100));
				cluebtns[col][row-1].setDisable(true);				
			}
		}
		endinglabel = new Label("");
		endinglabel.setVisible(false);
		hintlabel = new Label("Click one of the available buttons above to hear a clue~");
		hintlabel.setWrapText(true);
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
		mainPane.add(startbtn, 0, 0, 2, 1);
		mainPane.add(winning, 2, 0, 3, 1);
		for(int col=0;col<5;col++) {
			mainPane.add(catlabels[col], col, 1);
			for (int row=0;row<5;row++) {
				mainPane.add(cluebtns[col][4-row], col, row+2);
			}
		}
		mainPane.add(endinglabel, 0, 2, 5, 2);
		mainPane.add(hintlabel, 0, 7, 5, 2);
		mainPane.add(input, 0, 9, 5, 1);
		mainPane.add(submitbtn, 0, 10, 2, 1);
		mainPane.add(dkbtn, 2, 10, 2, 1);
		mainPane.add(returnToMenuButton, 0, 11, 3, 1);
		// Button functionality
		startbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				regenerategame(controller);
			}
		});
		//set up all clue buttons here
		for(int colindex=0;colindex<5;colindex++) {
			for (int rowindex=0;rowindex<5;rowindex++) {
				cluebtns[colindex][rowindex].setId(Integer.toString(10*(colindex)+rowindex));
				cluebtns[colindex][rowindex].setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						hintlabel.setText("You can click the button if you want to listen to it again.");
						Button x =(Button)arg0.getSource();
						int colnum = (Integer.parseInt(x.getId())/10);
						int rownum = (Integer.parseInt(x.getId())%10);
						input.setVisible(true);
						submitbtn.setVisible(true);
						dkbtn.setVisible(true);
						controller.cluebtnclicked(colnum,rownum);
						int[] btns = controller.getenablebtns();
						for(int i=0;i<5;i++) {
							if(i != colnum) {
								cluebtns[i][btns[i]].setDisable(true);
							}
						}
					}
				});		
			}
		}
		submitbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				submitAnswer(controller);
				updatebtns(controller);
				winning.setText("Current Worth: $"+Integer.toString(controller.getcurrentwinning()));
				updateQscomponents(controller);
			}
		});
		dkbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.dkbtnclicked();
				updatebtns(controller);
				hintlabel.setText("The correct answer was:"+controller.getans()+". Click one of the available buttons above to hear a clue~");
				updateQscomponents(controller);
			}
		});		
		returnToMenuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				regenerategame(controller);
				controller.returnToMenu();
			}
		});		
	}
	/*
	 * This method is used to regenerate all the components and send notice
	 * for the game controller to to initialize the game data.
	 */
	public void regenerategame(GameController controller) {
		controller.generatedata();
		winning.setText("Current Worth: $0");
		for(int i=0;i<5;i++) {
			catlabels[i].setText(controller.getcat()[i]);
			for(int j=0;j<5;j++) {
				cluebtns[i][j].setVisible(true);
				cluebtns[i][j].setDisable(true);
			}
			cluebtns[i][0].setDisable(false);
		}
		endinglabel.setVisible(false);
		hintlabel.setText("Click one of the available buttons above to hear a clue~");
		hintlabel.setVisible(true);
		input.setVisible(false);
		submitbtn.setVisible(false);
		dkbtn.setVisible(false);
	}
	/*
	 * This method is used to update the view of clickable buttons
	 */
	public void updatebtns(GameController controller) {
		int[] pos = controller.getqspos();
		cluebtns[pos[0]][pos[1]].setVisible(false);
		int [] btns = controller.getenablebtns();
		for(int i=0;i<5;i++) {
			cluebtns[i][btns[i]].setDisable(false);
		}
	}
	/*
	 * This method is used to update the question showing label and 
	 * visibility of submit and dont know buttons
	 */
	public void updateQscomponents(GameController controller) {
		submitbtn.setVisible(false);
		input.setVisible(false);
		input.setText("Type your answer here: ");
		dkbtn.setVisible(false);
		if(controller.getcount()== 25) {
			endinglabel.setText("Congrats! All questions completed!! You have a reward of $"
					+controller.getcurrentwinning()+" . Click restart"
					+" button to start a new game or return to the menu.");
			endinglabel.setVisible(true);
		}
	}
	/*
	 * This method is used to send request to the game controller and
	 * check if the user's answer is correct
	 */
	public void submitAnswer(GameController controller) {
		if(controller.checkAnswer(input.getText())) {
			hintlabel.setText("Correct! You can now continue on the next one~");
		}else {
			hintlabel.setText("Wrong. The correct answer was:"+controller.getans()
			+". Click availabel buttons above to continue.");
		}	
	}

	public Scene getScene() {
		return main;
	}
}
