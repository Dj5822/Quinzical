package quinzical.ui;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import quinzical.controller.SettingsController;

public class SettingsView {
	
	private Scene main;
	
	private Label title;
	private Label speechSpeedLabel;
	private Slider speechSpeedSlider;
	private Label testLabel;
	private Button testButton;
	private Button databaseButton;
	private Button returnButton;
	
	private Label speechTypeLabel;
	private RadioButton nzMaleRadioButton;
	private RadioButton nzFemaleRadioButton;
	private RadioButton defaultRadioButton;
	private final ToggleGroup voiceTypeToggleGroup;
	private GridPane voiceTypeMenu;
	
	public SettingsView(SettingsController controller, int width, int height) {
		
		GridPane mainPane = new GridPane();
		mainPane.setVgap(height/15);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setStyle("-fx-background-color: #edf4fc");
		main = new Scene(mainPane, width, height);
		
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		voiceTypeToggleGroup = new ToggleGroup();
		
		// Initialize buttons and labels.
		title = new Label("Settings");
		speechSpeedLabel = new Label("Speech speed: ");
		speechSpeedSlider = new Slider(0.1, 3, 1);
		speechSpeedSlider.setShowTickMarks(true);
		speechSpeedSlider.setShowTickLabels(true);
		speechSpeedSlider.setMajorTickUnit(1f);
		speechSpeedSlider.setBlockIncrement(0.1f);
		testLabel = new Label(controller.getTestText());
		testButton = new Button("Test");
		databaseButton = new Button("Manage Database");
		returnButton = new Button("Return");
		
		speechTypeLabel = new Label("Speech type: ");
		defaultRadioButton = new RadioButton("Default voice");
		defaultRadioButton.setToggleGroup(voiceTypeToggleGroup);
		defaultRadioButton.setSelected(true);
		nzMaleRadioButton = new RadioButton("NZ male voice");
		nzMaleRadioButton.setToggleGroup(voiceTypeToggleGroup);
		nzFemaleRadioButton = new RadioButton("NZ female voice");
		nzFemaleRadioButton.setToggleGroup(voiceTypeToggleGroup);
		voiceTypeMenu = new GridPane();
		
		// Set component sizes. and alignment
		title.setTextAlignment(TextAlignment.CENTER);
		title.setAlignment(Pos.CENTER);
		title.setPrefHeight(height/6);
		title.setPrefWidth(width/2);
		title.setFont(new Font(height/9));
		testButton.setPrefHeight(height/9);
		testButton.setPrefWidth(width/3);
		databaseButton.setPrefHeight(height/9);
		databaseButton.setPrefWidth(width/3);
		returnButton.setPrefHeight(height/9);
		returnButton.setPrefWidth(width/3);
		speechSpeedSlider.setPrefWidth(width/2);
		speechSpeedLabel.setFont(new Font(height/18));
		testLabel.setFont(new Font(height/18));
		speechTypeLabel.setFont(new Font(height/18));
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(testLabel, HPos.LEFT);
		GridPane.setHalignment(testButton, HPos.RIGHT);
		GridPane.setHalignment(databaseButton, HPos.CENTER);
		GridPane.setHalignment(returnButton, HPos.CENTER);
		testButton.setFocusTraversable(false);
		databaseButton.setFocusTraversable(false);
		returnButton.setFocusTraversable(false);
		voiceTypeMenu.setHgap(width/15);
		
		// Add buttons and labels to the view.
		voiceTypeMenu.add(speechTypeLabel, 0, 0);
		voiceTypeMenu.add(defaultRadioButton, 1, 0);
		voiceTypeMenu.add(nzMaleRadioButton, 2, 0);
		voiceTypeMenu.add(nzFemaleRadioButton, 3, 0);
		mainPane.add(title, 0, 0, 2, 1);
		mainPane.add(testLabel, 0, 1);
		mainPane.add(testButton, 1, 1);
		mainPane.add(speechSpeedLabel, 0, 2);
		mainPane.add(speechSpeedSlider, 1, 2);
		mainPane.add(voiceTypeMenu, 0, 3, 2, 1);
		mainPane.add(databaseButton, 0, 4, 1, 1);
		mainPane.add(returnButton, 1, 4, 2, 1);
		
		speechSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				controller.setSpeed(arg0.getValue().doubleValue());
			}
			
		});
		
		// Button functionality
		testButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.testSpeech();
			}
		});
		
		nzMaleRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.setVoiceType("nz male");
			}
		});
		
		nzFemaleRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.setVoiceType("nz female");
			}
		});
		
		defaultRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.setVoiceType("default");
			}
		});
		databaseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.switchToDatabase();
			}
		});		
		returnButton.setOnAction(new EventHandler<ActionEvent>() {
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
