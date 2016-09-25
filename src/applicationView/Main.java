package applicationView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {
        createScene cScene = new createScene();
        primaryStage.setTitle("Cell Society Group 5");
        Scene myScene = cScene.initScene();
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

}
