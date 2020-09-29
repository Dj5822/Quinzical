package quinzical.ui;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import quinzical.controller.PracticeController;

public class PracticeView {
	
	private Scene main;
	
	private Label title;
	private Button returnToMenuButton;
	private Button showQuestionButton;
	private Label categoryLabel;
	private ObservableList<String> categoryOptions;
	private ComboBox<String> categoryCB;
	
	private Label answerLabel;
	private Label hintLabel;
	private TextArea answerTextBox;
	private Button checkAnswerButton;
	
	public PracticeView(PracticeController controller, int width, int height) {
		
		// Setup category pane.
		GridPane categoryPane = new GridPane();
		categoryPane.setVgap(height/30);
		categoryPane.setAlignment(Pos.CENTER);
		
		// create category pane components.
		title = new Label("Practice View");
		categoryLabel = new Label("Select category: ");
		categoryOptions = FXCollections.observableArrayList();
		controller.updateCategoryOptions(categoryOptions);
		categoryCB = new ComboBox<String>(categoryOptions);
		showQuestionButton = new Button("Show Question");
		returnToMenuButton = new Button("Return to menu");
		
		// set the size and alignment of components.
		title.setTextAlignment(TextAlignment.CENTER);
		title.setAlignment(Pos.CENTER);
		title.setFont(new Font(height/9));
		title.setPadding(new Insets(0, width/20, 0, width/20));
		categoryLabel.setTextAlignment(TextAlignment.CENTER);
		categoryLabel.setAlignment(Pos.CENTER);
		categoryLabel.setFont(new Font(height/18));
		categoryLabel.setPadding(new Insets(height/6, 0, height/6, 0));
		categoryCB.setPrefHeight(height/9);
		categoryCB.setPrefWidth(width/3);
		showQuestionButton.setPrefHeight(height/9);
		showQuestionButton.setPrefWidth(width/3);
		returnToMenuButton.setPrefHeight(height/9);
		returnToMenuButton.setPrefWidth(width/3);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(categoryLabel, HPos.CENTER);
		GridPane.setHalignment(categoryCB, HPos.CENTER);
		GridPane.setHalignment(showQuestionButton, HPos.CENTER);
		GridPane.setHalignment(returnToMenuButton, HPos.CENTER);
		returnToMenuButton.setFocusTraversable(false);
		showQuestionButton.setFocusTraversable(false);
		
		// add components to category pane.
		categoryPane.add(title, 0, 0, 2, 1);
		categoryPane.add(categoryLabel, 0, 1);
		categoryPane.add(categoryCB, 1, 1);
		categoryPane.add(showQuestionButton, 0, 2, 2, 1);
		categoryPane.add(returnToMenuButton, 0, 3, 2, 1);
		
		// Setup answer pane.
		GridPane answerPane = new GridPane();
		answerPane.setVgap(height/30);
		answerPane.setAlignment(Pos.CENTER);
		
		// create answer pane components.
		answerLabel = new Label("Question");
		hintLabel = new Label();
		answerTextBox = new TextArea();
		checkAnswerButton = new Button("Check Answer");
		
		// set the size and alignment of components.
		answerLabel.setTextAlignment(TextAlignment.CENTER);
		answerLabel.setAlignment(Pos.CENTER);
		answerLabel.setFont(new Font(height/25));
		hintLabel.setTextAlignment(TextAlignment.CENTER);
		hintLabel.setAlignment(Pos.CENTER);
		hintLabel.setFont(new Font(height/15));
		answerTextBox.setPrefHeight(height/9);
		answerTextBox.setPrefWidth(width/3);
		checkAnswerButton.setPrefHeight(height/9);
		checkAnswerButton.setPrefWidth(width/3);
		GridPane.setHalignment(answerLabel, HPos.CENTER);
		GridPane.setHalignment(hintLabel, HPos.CENTER);
		GridPane.setHalignment(checkAnswerButton, HPos.CENTER);
		checkAnswerButton.setFocusTraversable(false);
		
		// add components to answer pane.
		answerPane.add(answerLabel, 0, 0);
		answerPane.add(hintLabel, 0, 1);
		answerPane.add(answerTextBox, 0, 2);
		answerPane.add(checkAnswerButton, 0, 3);
		
		// start at category pane.
		main = new Scene(categoryPane, width, height);
		
		// Makes everything look prettier.
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		// Button functionality.
		showQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.showQuestion(controller, main, answerPane, categoryCB, answerLabel,
						answerTextBox, hintLabel, checkAnswerButton);
			}
		});
		
		checkAnswerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.submitAnswer(controller, main, categoryPane,
						checkAnswerButton, hintLabel, answerTextBox, categoryCB);
			}
		});
		
		main.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		    public void handle(KeyEvent ke) {
		        if (ke.getCode() == KeyCode.ENTER) {
		        	controller.submitAnswer(controller, main, categoryPane,
							checkAnswerButton, hintLabel, answerTextBox, categoryCB);
		        }
		    }
		});
		
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
