package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.HexagonGridView;
import grid.Neighbor;
import grid.NeighborsHandler;
import grid.NormalEdgeNeighborsHandler;
import grid.RectangleGridView;
import grid.ToroidalEdgeNeighborsHandler;
import grid.TriangleGridView;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.shape.Shape;


public abstract class Simulation {
    private String myCellShape = "Square";

    /// these 3 fields could be put in a gridViewController
    private Group myRoot;
    private Grid myGrid;
    private GridView myGridView;
    private NeighborsHandler myNeighborsHandler;
    private State myDefaultState;
    private String myNeighborsToConsider;
    private String myEdgeType;


    private Dimension2D myGridSize;
    protected int stepNum;

    public int getStepNum () {
        return stepNum;
    }

    Simulation (Map<String, Map<String, String>> simulationConfig) {
        initializeSimulation(simulationConfig);
    }

    public abstract void countCellsinGrid ();

    public Grid getGrid () {
        return myGrid;
    }

    public void setGrid (Grid grid) {
        this.myGrid = grid;
    }

    public String getCellShape () {
        return myCellShape;
    }

    public void setCellShape (String CellShape) {
        this.myCellShape = CellShape;
        handleCellShape();
    }

    public abstract void step ();

    public void initializeSimulation (Map<String, Map<String, String>> simulationConfig) {
        initializeGrid(simulationConfig);
        initializeGeneralDetails(simulationConfig.get("GeneralConfig"));
        initializeSimulationDetails(simulationConfig.get("SimulationConfig"));
        //generateMap();
        /*
         * setGridView(new RectangleGridView(new Dimension2D(Double
         * .parseDouble(simulationConfig.get("GeneralConfig").get("gridWidth")), Double
         * .parseDouble(simulationConfig.get("GeneralConfig").get("gridHeight"))),
         * getGrid()));
         */
        /*setGridView(new RectangleGridView(new Dimension2D(Double
                .parseDouble(simulationConfig.get("GeneralConfig").get("gridWidth")), Double
                        .parseDouble(simulationConfig.get("GeneralConfig").get("gridHeight"))),
                                          getGrid()));*/
        //setNeighborsHandler(new NormalEdgeNeighborsHandler(myCellShape, myGrid));
    }

    public void initializeGrid (Map<String, Map<String, String>> simulationConfig) {
        Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();
        setGrid(new Grid(Integer
                .parseInt(simulationConfig.get("GeneralConfig").get("numberOfRows")), Integer
                        .parseInt(simulationConfig.get("GeneralConfig").get("numberOfRows")),
                         cellGrid));
        setDefaultState(getSimulationState(simulationConfig.get("GeneralConfig").get("defaultState")));
        populateGridWithSpecificValues(simulationConfig.get("Cells"));
        handleMapGeneration(simulationConfig.get("GeneralConfig"));
        generateMap();
        
    //    setDefaultState(  simulationConfig.get("GeneralConfig").get("defaultState"));
    }
    
    public void populateGridWithSpecificValues(Map<String, String> cells) {
        for (Map.Entry<String, String> entry : cells.entrySet()) {
            String[] coordinateStrings = entry.getKey().split("_");
            Cell cell =
                    createCell(new Coordinate(Integer.parseInt(coordinateStrings[1]),
                                              Integer.parseInt(coordinateStrings[2])),
                               getSimulationState(entry.getValue()));
            myGrid.addCell(cell);
        }
    }

    private void initializeGeneralDetails (Map<String, String> generalConfig) {
        setGridSize(new Dimension2D(Double.parseDouble(generalConfig.get("gridWidth")),
                                    Double.parseDouble(generalConfig.get("gridHeight")))); //TEMPORARY - NEEDS TO BE SET BY VIEW CONTROLLER
        setCellShape(generalConfig.get("cellShape"));
        //myCellShape = generalConfig.get("cellShape");
        //myDefaultState = getSimulationState( generalConfig.get("defaultState"));
        //setDefaultState(getSimulationState(generalConfig.get("defaultState")));
        myNeighborsToConsider = generalConfig.get("neighborsToConsider");//link to neighborHandler
        setEdgeType(generalConfig.get("edgeType"));
        //myEdgeType = generalConfig.get("edgeType");
    }

    private void setDefaultState (State state) {
        myDefaultState = state;
    }
    
    public State getDefaultState () {
        return myDefaultState;
    }

    public abstract void initializeSimulationDetails (Map<String, String> simulationConfig);

    public abstract Cell createCell (Coordinate coordinate, State currentState);

    /*
     * public abstract void generateMap (int numberOfRows,
     * int numberOfColumns,
     * Grid cellGrid);
     */

    /**
     * Return the grid view
     * 
     * @return
     */
    public Group getSimulationView () {
        return this.myRoot;
    }

    public GridView getGridView () {
        return myGridView;
    }

    public void setGridView (GridView gridView) {
        myRoot = new Group();
        this.myGridView = gridView;
        this.myRoot.getChildren().add(myGridView.getRoot());
    }

    public void addGridViewSceneGraph (Group root) {
        root.getChildren().add(myGridView.getRoot());
    }

    public void removeGridViewSceneGraph (Group root) {
        root.getChildren().clear();
        // myGridView.getRoot().getChildren().clear();
        // root.getChildren().removeAll(myGridView.getRoot().getChildren());
        // root.getChildren().remove(myGridView.getRoot());
    }

    /// if laggy change order
    public void updateGrid () {
        myGrid.updateGrid();
        myGridView.updateView();

    }

    public NeighborsHandler getNeighborsHandler () {
        return myNeighborsHandler;
    }

    public void setNeighborsHandler (NeighborsHandler neighbors) {
        this.myNeighborsHandler = neighbors;
    }

    /**
     * @param cell
     * @return
     */
    public List<Cell> getSquareNeighbors (Cell cell) {
        return getNeighborsHandler().getSurroundingNeighbors(cell.getMyGridCoordinate());
    }

    public void generateMap () {
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumColumns(); c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!myGrid.isCreated(coordinate)) {
                    myGrid.addCell(createCell(coordinate, stateGenerator()));
                }

            }
        }
    }

    public void handleCellShape () {
        if (myCellShape.equals("Rectangle")) {
            setGridView(new RectangleGridView(myGridSize, myGrid));
        }
        else if (myCellShape.equals("Triangle")) {
            setGridView(new TriangleGridView(myGridSize, myGrid));
        }
        else if (myCellShape.equals("Hexagon")) {
            setGridView(new HexagonGridView(myGridSize, myGrid));
        }
    }

    public void handleEdgeType () {
        if (myEdgeType.equals("Normal")) {
            setNeighborsHandler(new NormalEdgeNeighborsHandler(myNeighborsToConsider, myGrid));
        }
        else if (myEdgeType.equals("Toriodal")) {
            setNeighborsHandler(new ToroidalEdgeNeighborsHandler(myNeighborsToConsider, myGrid));
        }
    }

    public void handleNeighborsToConsider (String neighborsToconsider) {
        if (neighborsToconsider.equals("Hexagon")) {

        }
    }

    public abstract State[] getSimulationStates();
    public abstract State getSimulationState(String simulationState);
    
    public abstract double getSpawnProbability(double currentProbability);
    
    private State stateGenerator() {
        Random rn = new Random();
        double spawnRandomNumber = rn.nextDouble() * 100;
        double currentProbability = 0;
        for(State state : getSimulationStates()) {
            currentProbability += state.getProbability();
            if(spawnRandomNumber < currentProbability) {
                return state;
            }
        }
        return getDefaultState();
    }
        
    public void handleMapGeneration ( Map<String, String> generalConfig) {
        String generationType = generalConfig.get("generationType");
        if (generationType.equals("Random")) {
            for(State state : getSimulationStates()) {
                state.setProbability(100.0 / getSimulationStates().length);
            }     
        }
        else if (generationType.equals("Probability")) {
            for(State state : getSimulationStates()) {
                System.out.println(generalConfig.get(state.name() + "_Probability"));
                state.setProbability(Double.parseDouble(generalConfig.get(state.name() + "_Probability")));
            }
        }
        else if (generationType.equals("Specific")) {
            for(State state : getSimulationStates()) {
                state.setProbability(0);
            }
        }

    }

    public Dimension2D getGridSize () {
        return myGridSize;
    }

    public void setGridSize (Dimension2D myGridSize) {
        this.myGridSize = myGridSize;
    }
    
    public String getEdgeType () {
        return myEdgeType;
    }

    public void setEdgeType (String edgeType) {
        this.myEdgeType = edgeType;
        handleEdgeType();
    }

    // How to go from the inputed XML ShapeType to making RectangleGrid()
}
