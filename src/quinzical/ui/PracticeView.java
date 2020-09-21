package quinzical.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
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
	private TextArea answerTextBox;
	private Button checkAnswerButton;
	
	public PracticeView(PracticeController controller, int width, int height) {
		
		// Setup category pane.
		GridPane categoryPane = new GridPane();
		title = new Label("Practice View");
		returnToMenuButton = new Button("Return to menu");
		categoryLabel = new Label("Select category: ");
		categoryOptions = FXCollections.observableArrayList();
		controller.updateCategoryOptions(categoryOptions);
		categoryCB = new ComboBox<String>(categoryOptions);
		showQuestionButton = new Button("Show Question");
		categoryPane.add(title, 0, 0, 2, 1);
		categoryPane.add(categoryLabel, 0, 1);
		categoryPane.add(categoryCB, 1, 1);
		categoryPane.add(showQuestionButton, 0, 2, 2, 1);
		categoryPane.add(returnToMenuButton, 0, 3, 2, 1);
		
		// Setup answer pane.
		GridPane answerPane = new GridPane();
		answerLabel = new Label("Question");
		answerTextBox = new TextArea();
		checkAnswerButton = new Button("Check Answer");
		answerPane.add(answerLabel, 0, 0);
		answerPane.add(answerTextBox, 0, 1);
		answerPane.add(checkAnswerButton, 0, 2);
		
		// start at category pane.
		main = new Scene(categoryPane, width, height);
		
		// Button functionality.
		showQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (categoryCB.getValue() == null) {
					controller.showErrorMessage("You must select a category", "Please select a category from the combobox.");
				}
				else {
					answerLabel.setText(controller.getQuestion(categoryCB.getValue()));
					main.setRoot(answerPane);
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
