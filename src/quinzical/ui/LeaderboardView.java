package quinzical.ui;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import quinzical.controller.LeaderboardController;

public class LeaderboardView {
	private Scene main;
	
	public LeaderboardView(LeaderboardController controller, int width, int height) {
		GridPane mainPane = new GridPane();
		main = new Scene(mainPane, width, height);
		File styleFile = new File("./src/quinzical/style.css");
		main.getStylesheets().clear();
		main.getStylesheets().add("file:///" + styleFile.getAbsolutePath().replace("\\", "/"));
	}
	
	public Scene getScene() {
		return main;
	}
}
