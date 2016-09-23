package main;

import applicationView.Toolbar;
import controller.ApplicationController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Main class for creating an animated scene.
 * 
 */
public class Main extends Application {
    public static final int SIZE = 500;
    public static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    //private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 2.0;


    private ApplicationController applicationController;

    /**
     * Sets the Game up at the beginning.
     * 
     * @return nothing
     */
    @Override
    public void start (Stage s) {
        applicationController = new ApplicationController();
        s.setTitle(applicationController.getTitle());

        Scene scene = applicationController.init(SIZE, SIZE);
        //TODO: Get toolbar to not interfere with the simulation, want it to be its own section
        Toolbar myToolbar = new Toolbar();
        myToolbar.initToolbar(SIZE,SIZE, scene, (Group) scene.getRoot());
        s.setScene(scene);
        s.show();

        KeyFrame frame = new KeyFrame(Duration.millis(SECOND_DELAY),
                                      e -> applicationController.getSimulationController().getSimulation().step());
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    public static void main (String[] args) {
        launch(args);
    }
}
