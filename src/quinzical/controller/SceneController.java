package quinzical.controller;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The SceneController contains methods that allow
 * the stage to switch to a different scene.
 */
public class SceneController {
	private HashMap<String, Scene> stageMap = new HashMap<>();
    private Stage mainStage;
    
    private String currentScene;
    private String previousScene;
    
    public SceneController(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    /**
     * Adds a new scene to the hashmap.
     * Then scene can then be accessed using changeScene(<key>).
     */
    public void addScene(String name, Scene pane){
         stageMap.put(name, pane);
    }
    
    /**
     * Removes the scene from the hashmap.
     * Removed scenes can not be accessed.
     */
    public void removeScene(String name){
        stageMap.remove(name);
    }
    
    /**
     * Changes the current scene to a scene corresponding to the key.
     * Also sets the saves the key of the current and previous scene.
     */
    public void changeScene(String name){
        mainStage.setScene(stageMap.get(name));
        
        if (currentScene == null) {
        	currentScene = name;
        }
        else {
        	previousScene = currentScene;
        	currentScene = name;
        }
    }
    
    /**
     * Return to the previous scene that the user was in prior to the current scene.
     * Switches the the previous and current Scene around.
     */
    public void returnToPreviousScene() {
    	mainStage.setScene(stageMap.get(previousScene));
    	String temp = previousScene;
    	previousScene = currentScene;
    	currentScene = temp;
    }
    
    /**
     * Terminates the stage.
     */
    public void quitGame() {
    	mainStage.close();
    }
}
