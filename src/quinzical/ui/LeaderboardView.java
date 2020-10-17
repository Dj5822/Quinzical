package quinzical.ui;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import quinzical.controller.LeaderboardController;
import quinzical.controller.LeaderboardItem;

public class LeaderboardView {
	private Scene main;
	
	private Label title;
	private TableView<LeaderboardItem> leaderboard;
	private Button returnToMenuButton;
	
	public LeaderboardView(LeaderboardController controller, int width, int height) {
		
		// create main pane.
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		main = new Scene(mainPane, width, height);
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
		
		// generate leaderboard components.
		title = new Label("Leaderboard");
		returnToMenuButton = new Button("Return to Menu");
		
		leaderboard = new TableView<LeaderboardItem>();
		TableColumn<LeaderboardItem, String> nameColumn = new TableColumn<LeaderboardItem, String>("Username");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		TableColumn<LeaderboardItem, Integer> scoreColumn = new TableColumn<LeaderboardItem, Integer>("Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
		leaderboard.getSortOrder().add(scoreColumn);
		leaderboard.getColumns().addAll(nameColumn, scoreColumn);
		
		controller.setupLeaderboard(leaderboard);
		controller.initialiseLeaderboard();
		
		// add components to the pane.
		mainPane.add(title, 0, 0);
		mainPane.add(leaderboard, 0, 1);
		mainPane.add(returnToMenuButton, 0, 2);
		
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
