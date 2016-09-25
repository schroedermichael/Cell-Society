package applicationView;
import controller.ApplicationController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.*;
import javafx.stage.Stage;
import javafx.util.Duration;
<<<<<<< aa3401ee54ff90d6076e92e11fc5b19277a7832c
public class createScene extends Application {
=======

public class createScene {
>>>>>>> Added main class to run the entire program, coordinated the two applicationView classes to work with main, created make button method in toolbar to allow easy addition of button
    
    public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int SCREEN_SIZE = 400;
    private final ResourceBundle GUIResources;
    private Toolbar myToolbar;
    private Group root = new Group();
    
    public createScene() {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }
    
<<<<<<< aa3401ee54ff90d6076e92e11fc5b19277a7832c
<<<<<<< bc358349355cbd382f852ba29dffec22282c4e03
    @Override
=======
    protected Scene initScene() {
        myToolbar = new Toolbar();
        Scene scene = new Scene(root);
        myToolbar.initToolbar(SCREEN_SIZE, SCREEN_SIZE, scene, root);
        return scene;
    }
    
  /*  @Override
>>>>>>> Added main class to run the entire program, coordinated the two applicationView classes to work with main, created make button method in toolbar to allow easy addition of button
    public void start(Stage s) {
        myToolbar = new Toolbar();
        Scene scene = new Scene(root);
        //myToolbar.initToolbar(SCREEN_SIZE, SCREEN_SIZE, scene, root);
        s.setScene(scene);
        s.show();
    }*/
    
   /* public static void main (String[] args) {
        launch(args);
    }*/
    
}
=======
    protected Scene initScene() {
        myToolbar = new Toolbar();
        Scene scene = new Scene(root);
        myToolbar.initToolbar(SCREEN_SIZE, SCREEN_SIZE, scene, root);
        return scene;
    }
}
>>>>>>> Created methods to be implemented when buttons are pressed
