package applicationView;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Toolbar {
    
    private int toolbarSize;
    private final ResourceBundle GUIResources;
    Button pause;
    Button step;
    Button loadXMLbutton;
    
    public Toolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    protected void initToolbar(int height, int width, Scene myScene, Group root) {
        myScene.setRoot(root);
        double toolbarSpacing = 20.0;
        HBox myToolbar = new HBox(toolbarSpacing);
        Slider slider = new Slider(0, 1, 0.5);
        pause = makeButton(GUIResources.getString("PauseCommand"), event -> pauseSimulation());
        step = makeButton(GUIResources.getString("StepCommand"), event -> stepThroughSimulation());
        //TODO: Get ArrayList for ChoiceBox to grab from resource bundle
        ComboBox<String> cb = new ComboBox<String>(FXCollections.observableArrayList(
                                                                       "Fire", "Game of Life", "Predator-Prey", "Segregation")
                                                                   );
        loadXMLbutton = makeButton(GUIResources.getString("LoadXML"), event -> loadFileOptions());
        myToolbar.getChildren().addAll(slider, pause, step, cb, loadXMLbutton);
        root.getChildren().add(myToolbar);
    }
    
    
    private Object stepThroughSimulation () {
        // TODO Create method to step through simulation
        return null;
    }

    private Object loadFileOptions () {
        // TODO Create file loader
        return null;
    }

    private void pauseSimulation() {
        //TODO Create method to pause the simulation
    }
    
    private Button makeButton(String name, EventHandler<ActionEvent> handleAction) {
        Button myButton = new Button();
        myButton.setText(name);
        myButton.setOnAction(handleAction);
        return myButton;
        
    }
    
}
