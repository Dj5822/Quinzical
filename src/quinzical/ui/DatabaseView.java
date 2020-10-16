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
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import quinzical.controller.DatabaseController;

public class DatabaseView {
	
	private Scene main;
	
	private Label CBLabel;
	private ObservableList<String> categoryOptions;
	private ComboBox<String> categoryCB;
	private Button showButton;
	private ListView listView;
	private Button addButton,modifyButton,deleteButton;
	private Button returnButton;

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
		
		mainPane.add(CBLabel, 0, 0, 3, 1);
		mainPane.add(categoryCB, 0, 1);
		mainPane.add(showButton, 1, 1);	
		mainPane.add(listView, 0, 2, 3, 1);
		mainPane.add(addButton, 0, 3);
		mainPane.add(modifyButton, 1, 3);
		mainPane.add(deleteButton, 2, 3);
		mainPane.add(returnButton, 0, 4, 3, 1);
		
		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.returnToLastScene();
			}
		});		
	}
	
	public Scene getScene() {
		return main;
	}
}
