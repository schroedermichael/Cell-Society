package applicationView;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import controller.ApplicationController;

public class Toolbar {
    
    private int toolbarSize;
    private final ResourceBundle GUIResources;
    private ApplicationController applicationController;
    Button pause;
    Button step;
    Button loadXMLbutton;
    
    public Toolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    public Node initToolbar(int height, int width, Scene myScene) {
        //myScene.setRoot(root);
        double toolbarDistance = 8.0;
        HBox myToolbar = new HBox(toolbarDistance);
        myToolbar.setPrefWidth(width);
        Slider slider = new Slider(0, 1, 0.5);
        pause = makeButton(GUIResources.getString("PauseCommand"), event -> pauseSimulation());
        step = makeButton(GUIResources.getString("StepCommand"), event -> stepSimulation());
        //TODO: Get ArrayList for ChoiceBox to grab from resource bundle
        ComboBox<String> cb = new ComboBox<String>(FXCollections.observableArrayList(
                                                                       "Fire", "Game of Life", "Predator-Prey", "Segregation")
                                                                   );
        loadXMLbutton = makeButton(GUIResources.getString("LoadXML"), event -> getXMLFile());
        myToolbar.getChildren().addAll(slider, pause, step, cb, loadXMLbutton);
        return myToolbar;
    }
    
    
    private Object getXMLFile () {
        // TODO Allow user to upload their own XML file
        return null;
    }

    private Object stepSimulation () {
        return null;
        // TODO create method to step through the simulation
    }


    private void pauseSimulation() {
        //TODO Create method to pause the simulation
        //applicationController.getSimulationController().getSimulation().step();
    }
    
    private Button makeButton(String name, EventHandler<ActionEvent> handleAction) {
        Button myButton = new Button();
        myButton.setText(name);
        myButton.setOnAction(handleAction);
        return myButton;
        
    }
    
    
}
