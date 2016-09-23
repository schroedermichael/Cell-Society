package applicationView;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class Toolbar {
    
    private int toolbarSize;
    private final ResourceBundle GUIResources;
    Button pause;
    Button step;
<<<<<<< Updated upstream
    Button loadXML;
    HBox myToolbar;
=======
    
    public Toolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
>>>>>>> Stashed changes
    
    protected void initToolbar(int height, int width, Scene myScene, Group root) {
        myScene.setRoot(root);
        HBox myToolbar = new HBox(20.0);
        Slider slider = new Slider(0, 1, 0.5);
        pause = new Button(GUIResources.getString("PauseCommand"));
        step = new Button(GUIResources.getString("StepCommand"));
        //TODO: Get ArrayList for ChoiceBox to grab from resource bundle
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                                                                       "Fire", "Game of Life", "Predator-Prey", "Segregation")
                                                                   );
<<<<<<< Updated upstream
        //cb.set
        loadXML = new Button("Load XML File");
        myToolbar.getChildren().addAll(slider, pause, step, cb, loadXML);
        addToRoot(root);
    }
    
    private void addToRoot(Group root) {
=======
        myToolbar.getChildren().addAll(slider, pause, step, cb);
>>>>>>> Stashed changes
        root.getChildren().add(myToolbar);
    }
    
    private void pauseSimulation() {
        
    }
    
}
