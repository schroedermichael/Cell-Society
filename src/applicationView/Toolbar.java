package applicationView;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
<<<<<<< aa3401ee54ff90d6076e92e11fc5b19277a7832c
import javafx.scene.input.*;
=======
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

>>>>>>> Added main class to run the entire program, coordinated the two applicationView classes to work with main, created make button method in toolbar to allow easy addition of button
public class Toolbar {
    
    private final ResourceBundle GUIResources;
    Button pause;
    Button step;
    Button loadXMLbutton;
    
    public Toolbar() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
    public void initToolbar(int height, int width, Scene myScene) {
        Group root = (Group)myScene.getRoot();
        myScene.setRoot(root);
<<<<<<< bc358349355cbd382f852ba29dffec22282c4e03
        HBox myToolbar = new HBox(height);
        Slider slider = new Slider(0, 1, 0.5);
        pause = new Button(GUIResources.getString("PauseCommand"));
        loadXMLbutton = new Button(GUIResources.getString("LoadXML"));
        step = new Button(GUIResources.getString("StepCommand"));
        myToolbar.getChildren().addAll(slider, pause, step, loadXMLbutton);
=======
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
>>>>>>> Created methods to be implemented when buttons are pressed
        root.getChildren().add(myToolbar);
    }
    
    public void setPauseButton(EventHandler<MouseEvent> event){
        pause.setOnMouseClicked(event);
      
    }
    
<<<<<<< bc358349355cbd382f852ba29dffec22282c4e03
    public void setStepButton(EventHandler<MouseEvent> event) {
        step.setOnMouseClicked(event);
=======
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
>>>>>>> Created methods to be implemented when buttons are pressed
    }
    
<<<<<<< aa3401ee54ff90d6076e92e11fc5b19277a7832c
    public void setXMLFileButton(EventHandler<MouseEvent> event) {
        loadXMLbutton.setOnMouseClicked(event);
    }

=======
    private Button makeButton(String name, EventHandler<ActionEvent> handleAction) {
        Button myButton = new Button();
        myButton.setText(name);
        myButton.setOnAction(handleAction);
        return myButton;
        
    }
>>>>>>> Added main class to run the entire program, coordinated the two applicationView classes to work with main, created make button method in toolbar to allow easy addition of button
    
}
