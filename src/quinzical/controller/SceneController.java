package quinzical.controller;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	private HashMap<String, Scene> stageMap = new HashMap<>();
    private Stage mainStage;

    public SceneController(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void addScene(String name, Scene pane){
         stageMap.put(name, pane);
    }

    public void removeScene(String name){
        stageMap.remove(name);
    }

    public void changeScene(String name){
        mainStage.setScene(stageMap.get(name));
    }
    
    public void quitGame() {
    	mainStage.close();
    }
}
