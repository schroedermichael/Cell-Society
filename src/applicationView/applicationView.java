package applicationView;

import grid.GridView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import simulation.Simulation;

public class applicationView {
    
    private GridView myGridView;
    private Simulation simulation;

    public Scene initBorderPane(int height, int width) {
        BorderPane myPane = new BorderPane();
        Toolbar myToolbar = new Toolbar();
        simulationGraph myGraph = new simulationGraph();
        myPane.setTop(myToolbar.initToolbar(height, width));
        myPane.setBottom(myGraph.createGraph());
        myGridView = simulation.getGridView();
        myPane.setCenter(myGridView.getRoot());
        
        Scene scene = new Scene(myPane);
        return scene;
    }
    
}
