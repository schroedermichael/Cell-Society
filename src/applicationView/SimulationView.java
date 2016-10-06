// This entire file is part of my masterpiece.
// Michael Schroeder

/**
 * This class is to replace all of the UI logic that used to be in Simulation. I have included the
 * changed Simulation class in this commit so that you can see the amount of code that was removed
 * and refactored, although the Simulation class is not a part of my masterpiece. Only the
 * code in this class is my masterpiece. The purpose of this class is to more distinctly separate
 * our view from our model/controller. Before, many lines of code in the Simulation class, which
 * isn't actually directly tied to the view, were used on creating and setting up the visualization
 * part of the project. However, now that I have made a SimulationView class, many things are
 * better. First of all, the code in Simulation is cleaner, and serves a more precise purpose.
 * Secondly, it is now much easier to add the extension of having multiple simulations displayed at
 * one time, because every simulation has its own distinct SimulationView, which can be added to the
 * application view root. This allows for much more customization in terms of overall layout of the
 * UI, because the application view isn't anchored to being a BorderPane with sections for the grid,
 * toolbar, and graph, but it can now be any shape, as long as there is a place to put the
 * SimulationView, which now operates as a single unit (as it should). I believe these
 * considerations and advantages speak to the good design of this decision.
 */
package applicationView;

import java.util.List;
import java.util.Map;
import grid.Grid;
import grid.GridView;
import grid.HexagonGridView;
import grid.RectangleGridView;
import grid.TriangleGridView;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Michael Schroeder
 *
 */
public class SimulationView {
    private GridView myGridView;
    private SimulationToolbar mySimulationToolbar;
    private SimulationGraph mySimulationGraph;
    private BorderPane myView;
    private Dimension2D myGridSize;

    /**
     * Constructs a new SimulationView based on the XML parameters in the map
     * 
     * @param simulationConfig - Map of parameters specifying how to set up the view
     * @param grid - grid used to initialize the GridView
     */
    public SimulationView (Map<String, Map<String, String>> simulationConfig, Grid grid) {

        Map<String, String> generalConfig = simulationConfig.get("GeneralConfig");
        setGridSize(new Dimension2D(Double.parseDouble(generalConfig.get("gridWidth")),
                                    Double.parseDouble(generalConfig.get("gridHeight"))));
        handleCellShape(generalConfig.get("cellShape"), grid);

        initializeView();
    }

    /**
     * Update the gridView and the graph
     * 
     * @param counts - the new data points to be added to the graph
     */
    public void update (List<Integer> counts) {
        myGridView.updateView();
        mySimulationGraph.updateGraph(counts);
    }

    /**
     * Sets the gridSize of this view
     * 
     * @param gridSize - the gridSize to use
     */
    private void setGridSize (Dimension2D gridSize) {
        this.myGridSize = gridSize;
    }

    /**
     * Return this view
     * 
     * @return - the node that represents the BorderPane
     */
    public Node view () {
        return this.myView;
    }

    /**
     * Adds the labels to the legends for this graph
     * 
     * @param legend
     */
    public void addToLegend (List<String> legend) {
        mySimulationGraph.addToLegend(legend);
    }

    /**
     * Setup the view
     */
    private void initializeView () {
        mySimulationGraph = new SimulationGraph();
        mySimulationToolbar = new SimulationToolbar();
        myView = new BorderPane();
        myView.setCenter(myGridView.getRoot());
        myView.setRight(mySimulationToolbar.getRoot());
        myView.setBottom(mySimulationGraph.createGraph());
    }

    /**
     * Clear the simulation view so the next one can be added
     */
    public void clear () {
        myView.getChildren().clear();
    }

    /**
     * Returns the toolbar for this view
     * 
     * @return - the simulationToolbar for this view
     */
    public SimulationToolbar getToolbar () {
        return mySimulationToolbar;
    }

    /**
     * Determines what type of GridView to make based on the XML parameters
     * 
     * @param cellShape - shape of the cells of the grid
     * @param grid - the Grid of cells
     */
    private void handleCellShape (String cellShape, Grid grid) {
        if (cellShape.equals("Rectangle")) {
            myGridView = new RectangleGridView(myGridSize, grid);
        }
        else if (cellShape.equals("Triangle")) {
            myGridView = new TriangleGridView(myGridSize, grid);
        }
        else if (cellShape.equals("Hexagon")) {
            myGridView = new HexagonGridView(myGridSize, grid);
        }
    }
}
