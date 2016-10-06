package simulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.NeighborsHandler;
import grid.NormalEdgeNeighborsHandler;
import grid.ToroidalEdgeNeighborsHandler;
import javafx.scene.Node;
import applicationView.SimulationToolbar;
import applicationView.SimulationView;


public abstract class Simulation {
    private static final String GENERAL_CONFIG = "GeneralConfig";
    private String myCellShape = "Square";
    private Grid myGrid;
    private NeighborsHandler myNeighborsHandler;
    private State myDefaultState;
    private String myNeighborsToConsider;
    private String myEdgeType;

    private SimulationView mySimulationView;

    protected int stepNum;

    public Simulation (Map<String, Map<String, String>> simulationConfig) {

        initializeSimulation(simulationConfig);
        getSimulationNames();
    }

    public int getStepNum () {
        return stepNum;
    }

    public abstract List<Integer> countCellsinGrid ();

    public Grid getGrid () {
        return myGrid;
    }

    public void setGrid (Grid grid) {
        this.myGrid = grid;
    }

    public String getCellShape () {
        return myCellShape;
    }

    public abstract void step ();

    public void initializeSimulation (Map<String, Map<String, String>> simulationConfig) {
        initializeSimulationDetails(simulationConfig.get("SimulationConfig"));
        initializeGrid(simulationConfig);

        initializeGeneralDetails(simulationConfig.get(GENERAL_CONFIG));
        initializeView(simulationConfig);

    }

    private void initializeView (Map<String, Map<String, String>> simulationConfig) {
        mySimulationView = new SimulationView(simulationConfig, myGrid);
        initializeSimulationToolbar(mySimulationView.getToolbar());
    }

    public void initializeGrid (Map<String, Map<String, String>> simulationConfig) {
        Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();
        setGrid(new Grid(Integer
                .parseInt(simulationConfig.get(GENERAL_CONFIG).get("numberOfRows")), Integer
                        .parseInt(simulationConfig.get(GENERAL_CONFIG).get("numberOfRows")),
                         cellGrid));
        setDefaultState(getSimulationState(simulationConfig.get(GENERAL_CONFIG)
                .get("defaultState")));
        myEdgeType = simulationConfig.get(GENERAL_CONFIG).get("edgeType");
        populateGridWithSpecificValues(simulationConfig.get("Cells"));
        handleMapGeneration(simulationConfig.get(GENERAL_CONFIG));
        generateMap();
    }

    public void populateGridWithSpecificValues (Map<String, String> cells) {
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
        setNeighborsToConsider(generalConfig.get("neighborsToConsider"));
        setEdgeType(generalConfig.get("edgeType"));
    }

    private void setDefaultState (State state) {
        myDefaultState = state;
    }

    public State getDefaultState () {
        return myDefaultState;
    }

    public abstract void initializeSimulationDetails (Map<String, String> simulationConfig);

    public abstract Cell createCell (Coordinate coordinate, State currentState);

    public abstract void initializeSimulationToolbar (SimulationToolbar toolbar);

    public void updateGrid () {
        myGrid.updateGrid();
        mySimulationView.update(countCellsinGrid());
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

    public void handleEdgeType (String edgeType) {
        if (edgeType.equals("Normal")) {
            setNeighborsHandler(new NormalEdgeNeighborsHandler(myNeighborsToConsider, myGrid));
        }
        else if (edgeType.equals("Toroidal")) {
            setNeighborsHandler(new ToroidalEdgeNeighborsHandler(myNeighborsToConsider, myGrid));
        }
    }

    public abstract State[] getSimulationStates ();

    public abstract State getSimulationState (String simulationState);

    private State stateGenerator () {
        Random rn = new Random();
        double spawnRandomNumber = rn.nextDouble() * 100;
        double currentProbability = 0;
        for (State state : getSimulationStates()) {
            currentProbability += state.getProbability();
            if (spawnRandomNumber < currentProbability) {
                return state;
            }
        }
        return getDefaultState();
    }

    public void handleMapGeneration (Map<String, String> generalConfig) {
        String generationType = generalConfig.get("generationType");
        if (generationType.equals("Random")) {
            for (State state : getSimulationStates()) {
                state.setProbability(100.0 / getSimulationStates().length);
            }
        }
        else if (generationType.equals("Probability")) {
            for (State state : getSimulationStates()) {
                System.out.println(generalConfig.get(state.name() + "_Probability"));
                state.setProbability(Double
                        .parseDouble(generalConfig.get(state.name() + "_Probability")));
            }
        }
        else if (generationType.equals("Specific")) {
            for (State state : getSimulationStates()) {
                state.setProbability(0);
            }
        }

    }

    public String getEdgeType () {
        return myEdgeType;
    }

    public void setEdgeType (String edgeType) {
        handleEdgeType(edgeType);
        this.myEdgeType = edgeType;
    }

    public String getNeighborsToConsider () {
        return myNeighborsToConsider;
    }

    public void setNeighborsToConsider (String neighborsToConsider) {
        myNeighborsToConsider = neighborsToConsider.toUpperCase();
    }

    public Node getView () {
        return this.mySimulationView.view();
    }

    public void clear () {
        mySimulationView.clear();
    }

    public void addToLegend (List<String> legend) {
        mySimulationView.addToLegend(legend);
    }

    public abstract void getSimulationNames ();
}
