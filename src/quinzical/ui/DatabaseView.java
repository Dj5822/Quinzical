package quinzical.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import quinzical.controller.DatabaseController;
import quinzical.controller.LeaderboardItem;

public class DatabaseView {
	
	private Scene main;
	
	private Label CBLabel;
	private ObservableList<String> categoryOptions;
	private ComboBox<String> categoryCB;
	private Button showButton;
	private ListView listView;
	private Button addButton,modifyButton,deleteButton;
	private Button returnButton;
	
	private Label title;
	private Label typeLabel;
	private Label typeInput;
	private Label clueLabel;
	private TextField clueInput;
	private Label answerFrontLabel;
	private TextField answerFrontInput;
	private Label answerBackLabel;
	private TextField answerBackInput;
	private Button confirmButton;
	private Button cancelButton;

	public DatabaseView(DatabaseController controller, int width, int height) {
		GridPane mainPane = new GridPane();
		mainPane.setVgap(height/15);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color: #edf4fc");
		main = new Scene(mainPane, width, height);
		
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		CBLabel = new Label("Select the category you want to modify and click show.");
		categoryOptions = FXCollections.observableArrayList();
		controller.updateCategoryOptions(categoryOptions);
		categoryCB = new ComboBox<String>(categoryOptions);
		showButton = new Button ("Show data");
		listView = new ListView();
		addButton = new Button("add");
		modifyButton = new Button("modify");
		deleteButton = new Button("delete");
		returnButton = new Button("Return");
		
		CBLabel.setTextAlignment(TextAlignment.CENTER);
		CBLabel.setAlignment(Pos.CENTER);
		CBLabel.setFont(new Font(height/20));
		CBLabel.setPadding(new Insets(0, width/20, 0, width/20));
		categoryCB.setPrefHeight(height/20);
		categoryCB.setPrefWidth(width/4);
		showButton.setPrefHeight(height/20);
		showButton.setPrefWidth(width/4);
		listView.setPrefHeight(height/2);
		listView.setPrefWidth(width);
		addButton.setPrefHeight(height/20);
		addButton.setPrefWidth(width/4);
		modifyButton.setPrefHeight(height/20);
		modifyButton.setPrefWidth(width/4);
		deleteButton.setPrefHeight(height/20);
		deleteButton.setPrefWidth(width/4);
		returnButton.setPrefHeight(height/20);
		returnButton.setPrefWidth(width/4);
		
		GridPane.setHalignment(CBLabel, HPos.CENTER);
		GridPane.setHalignment(categoryCB, HPos.CENTER);
		GridPane.setHalignment(showButton, HPos.CENTER);
		GridPane.setHalignment(listView, HPos.CENTER);
		GridPane.setHalignment(addButton, HPos.CENTER);
		GridPane.setHalignment(modifyButton, HPos.CENTER);
		GridPane.setHalignment(deleteButton, HPos.CENTER);
		GridPane.setHalignment(returnButton, HPos.CENTER);
		
		addButton.setVisible(false);
		modifyButton.setVisible(false);
		deleteButton.setVisible(false);
		
		mainPane.add(CBLabel, 0, 0, 3, 1);
		mainPane.add(categoryCB, 0, 1);
		mainPane.add(showButton, 1, 1);	
		mainPane.add(listView, 0, 2, 3, 1);
		mainPane.add(addButton, 0, 3);
		mainPane.add(modifyButton, 1, 3);
		mainPane.add(deleteButton, 2, 3);
		mainPane.add(returnButton, 0, 4, 3, 1);
		
		GridPane modifyPane = new GridPane();
		modifyPane.setVgap(height/15);
		modifyPane.setAlignment(Pos.CENTER);
		modifyPane.setStyle("-fx-background-color: #edf4fc");
		
		title = new Label("Add / Modify a question.");
		typeLabel = new Label("Operation type: ");
		typeInput = new Label("");
		clueLabel = new Label("Clue: ");
		clueInput = new TextField("");
		answerFrontLabel = new Label("Front part of answer: ");
		answerFrontInput = new TextField("");
		answerBackLabel = new Label("Back part of answer: ");
		answerBackInput = new TextField("");
		confirmButton = new Button ("Confirm");
		cancelButton = new Button ("Cancel");
		
		typeInput.setDisable(false);
			
		modifyPane.add(title, 0, 0, 2, 1);
		modifyPane.add(typeLabel, 0, 1);
		modifyPane.add(typeInput, 1, 1);
		modifyPane.add(clueLabel, 0, 2);
		modifyPane.add(clueInput, 1, 2);
		modifyPane.add(answerFrontLabel, 0, 3);
		modifyPane.add(answerFrontInput, 1, 3);
		modifyPane.add(answerBackLabel, 0, 4);
		modifyPane.add(answerBackInput, 1, 4);
		modifyPane.add(confirmButton, 0, 5);
		modifyPane.add(cancelButton, 1, 5);
		
		controller.setup(main, mainPane, modifyPane, categoryCB, listView, typeInput, 
				clueInput, answerFrontInput, answerBackInput);
		showButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				addButton.setVisible(true);
				modifyButton.setVisible(true);
				deleteButton.setVisible(true);
				controller.updateListView(categoryCB.getValue().toString());
			}
		});
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.addQuestion();
			}
		});
		modifyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.modifyQuestion();
			}
		});
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.deleteQuestion();
			}
		});
		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.returnToLastScene();
			}
		});
		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.confirmModification();
			}
		});
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.cancelModification();
			}
		});
	}
	public Scene getScene() {
		return main;
	}
}
