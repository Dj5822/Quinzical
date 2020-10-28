package quinzical.ui;

import java.io.File;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import quinzical.controller.DatabaseController;

/**
 * This class manages the view of question database of the app.
 * @author se2062020
 *
 */
public class DatabaseView {
	
	private Scene main;
	
	private Label mainTitle;
	private Label sectionLabel;
	private Label categoryLabel;
	private ObservableList<String> sectionOptions;
	private ObservableList<String> categoryOptions;
	private ComboBox<String> sectionCB;
 	private ComboBox<String> categoryCB;
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
		mainPane.setVgap(height/20);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color: #edf4fc");
		mainPane.setId("grassbackground");
		main = new Scene(mainPane, width, height);
		
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		mainTitle = new Label("Select the section and category below to modify questions");
		sectionLabel = new Label("Section:");
		sectionOptions = FXCollections.observableArrayList();
		controller.updateSectionOptions(sectionOptions);
		sectionCB = new ComboBox<String>(sectionOptions);
		categoryLabel = new Label("Category:");
		categoryOptions = FXCollections.observableArrayList();
		categoryCB = new ComboBox<String>(categoryOptions);
		listView = new ListView();
		addButton = new Button("add");
		modifyButton = new Button("modify");
		deleteButton = new Button("delete");
		returnButton = new Button("Return");
		
		mainTitle.setTextAlignment(TextAlignment.CENTER);
		mainTitle.setAlignment(Pos.CENTER);
		mainTitle.setFont(new Font(height/20));
		mainTitle.setPadding(new Insets(0, width/20, 0, width/20));
		sectionLabel.setTextAlignment(TextAlignment.CENTER);
		sectionLabel.setAlignment(Pos.CENTER);
		sectionLabel.setFont(new Font(height/20));
		sectionLabel.setPadding(new Insets(0, width/20, 0, width/20));
		categoryLabel.setTextAlignment(TextAlignment.CENTER);
		categoryLabel.setAlignment(Pos.CENTER);
		categoryLabel.setFont(new Font(height/20));
		categoryLabel.setPadding(new Insets(0, width/20, 0, width/20));
		sectionCB.setPrefHeight(height/20);
		sectionCB.setPrefWidth(width/4);
		categoryCB.setPrefHeight(height/20);
		categoryCB.setPrefWidth(width/4);
		listView.setPrefHeight(height/2);
		listView.setPrefWidth(width);
		addButton.setPrefHeight(height/20);
		addButton.setPrefWidth(width/4);
		modifyButton.setPrefHeight(height/20);
		modifyButton.setPrefWidth(width/4);
		deleteButton.setPrefHeight(height/20);
		deleteButton.setPrefWidth(width/4);
		returnButton.setPrefHeight(height/10);
		returnButton.setPrefWidth(width/2);
		
		GridPane.setHalignment(mainTitle, HPos.CENTER);
		GridPane.setHalignment(sectionLabel, HPos.CENTER);
		GridPane.setHalignment(categoryLabel, HPos.CENTER);
		GridPane.setHalignment(sectionCB, HPos.CENTER);
		GridPane.setHalignment(categoryCB, HPos.CENTER);
		GridPane.setHalignment(listView, HPos.CENTER);
		GridPane.setHalignment(addButton, HPos.CENTER);
		GridPane.setHalignment(modifyButton, HPos.CENTER);
		GridPane.setHalignment(deleteButton, HPos.CENTER);
		GridPane.setHalignment(returnButton, HPos.CENTER);
		
		addButton.setDisable(true);
		modifyButton.setDisable(true);
		deleteButton.setDisable(true);
		
		mainPane.add(mainTitle, 0, 0, 3, 1);
		mainPane.add(sectionLabel, 0, 1);
		mainPane.add(categoryLabel, 2, 1);
		mainPane.add(sectionCB, 0, 2);
		mainPane.add(categoryCB, 2, 2);
		mainPane.add(listView, 0, 3, 3, 1);
		mainPane.add(addButton, 0, 4);
		mainPane.add(modifyButton, 1, 4);
		mainPane.add(deleteButton, 2, 4);
		mainPane.add(returnButton, 0, 5, 3, 1);
		
		GridPane modifyPane = new GridPane();
		modifyPane.setVgap(height/10);
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
		
		title.setTextAlignment(TextAlignment.CENTER);
		title.setAlignment(Pos.CENTER);
		title.setFont(new Font(height/20));
		title.setPadding(new Insets(0, width/20, 0, width/20));
		typeLabel.setTextAlignment(TextAlignment.CENTER);
		typeLabel.setAlignment(Pos.CENTER);
		typeLabel.setFont(new Font(height/20));
		typeLabel.setPadding(new Insets(0, width/20, 0, width/20));
		typeInput.setTextAlignment(TextAlignment.CENTER);
		typeInput.setAlignment(Pos.CENTER);
		typeInput.setFont(new Font(height/20));
		typeInput.setPadding(new Insets(0, width/20, 0, width/20));
		clueLabel.setTextAlignment(TextAlignment.CENTER);
		clueLabel.setAlignment(Pos.CENTER);
		clueLabel.setFont(new Font(height/20));
		clueLabel.setPadding(new Insets(0, width/20, 0, width/20));
		clueInput.setAlignment(Pos.CENTER);
		clueInput.setFont(new Font(height/20));
		clueInput.setPadding(new Insets(0, width/20, 0, width/20));
		answerFrontLabel.setTextAlignment(TextAlignment.CENTER);
		answerFrontLabel.setAlignment(Pos.CENTER);
		answerFrontLabel.setFont(new Font(height/20));
		answerFrontLabel.setPadding(new Insets(0, width/20, 0, width/20));
		answerFrontInput.setAlignment(Pos.CENTER);
		answerFrontInput.setFont(new Font(height/20));
		answerFrontInput.setPadding(new Insets(0, width/20, 0, width/20));
		answerBackLabel.setTextAlignment(TextAlignment.CENTER);
		answerBackLabel.setAlignment(Pos.CENTER);
		answerBackLabel.setFont(new Font(height/20));
		answerBackLabel.setPadding(new Insets(0, width/20, 0, width/20));
		answerBackInput.setAlignment(Pos.CENTER);
		answerBackInput.setFont(new Font(height/20));
		answerBackInput.setPadding(new Insets(0, width/20, 0, width/20));
		confirmButton.setPrefHeight(height/10);
		confirmButton.setPrefWidth(width/4);
		cancelButton.setPrefHeight(height/10);
		cancelButton.setPrefWidth(width/4);
		
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(typeLabel, HPos.CENTER);
		GridPane.setHalignment(typeInput, HPos.CENTER);
		GridPane.setHalignment(clueLabel, HPos.CENTER);
		GridPane.setHalignment(clueInput, HPos.CENTER);
		GridPane.setHalignment(answerFrontLabel, HPos.CENTER);
		GridPane.setHalignment(answerFrontInput, HPos.CENTER);
		GridPane.setHalignment(answerBackLabel, HPos.CENTER);
		GridPane.setHalignment(answerBackInput, HPos.CENTER);
		GridPane.setHalignment(confirmButton, HPos.CENTER);
		GridPane.setHalignment(cancelButton, HPos.CENTER);
		
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
		
		controller.setup(main, mainPane, modifyPane, sectionCB, categoryCB, listView, addButton,
				modifyButton, deleteButton,typeInput, clueInput, answerFrontInput, answerBackInput);
		
		sectionCB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.updateCategoryOptions(categoryOptions, sectionCB.getValue());
				controller.clearListView();
				addButton.setDisable(true);
				modifyButton.setDisable(true);
				deleteButton.setDisable(true);
			}
		});
		categoryCB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.clearListView();
				if(addButton.isDisabled()) {
					addButton.setDisable(false);
				}else if(!modifyButton.isDisabled()) {
					modifyButton.setDisable(true);
					deleteButton.setDisable(true);
				}		
				controller.updateListView();
			}
		});
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.questionSelected();
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
				Optional<ButtonType> result = controller.showConfirmationDialog("Deleting a question!","Are you sure to do this?");
				if(result.get() == ButtonType.OK) {
					controller.deleteQuestion();
				}
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
