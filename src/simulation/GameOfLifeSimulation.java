package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import applicationView.SimulationToolbar;
import cell.Cell;
import cell.GameOfLifeCell;
import cell.State;
import grid.Coordinate;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


/**
 * 
 * @author Sean Hudson
 *
 */
public class GameOfLifeSimulation extends Simulation {

    private int myNumToReproduce;
    private Map<String, Integer> myOutput = new HashMap<String, Integer>();

    public GameOfLifeSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {
        getGrid().applyFuncToCell(p -> setNextState(p));
        updateGrid();
    }

    private int livingNeighbors (List<Cell> neighbors) {
        int count = 0;
        for (Cell cell : neighbors) {
            if (cell.getMyCurrentState().equals(GameOfLifeState.LIVING)) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected List<String> getSimulationNames () {
        List<String> myList = new ArrayList<String>();
        for (State n : getSimulationStates()) {
            myList.add(n.name());
        }
        return myList;
    }

    public void setNextState (Cell cell) {
        int numberOfLivingNeighbors =
                livingNeighbors(getNeighborsHandler()
                        .getSurroundingNeighbors(cell.getMyGridCoordinate()));
        if (numberOfLivingNeighbors == myNumToReproduce) {
            cell.setMyNextState(GameOfLifeState.LIVING);
        }
        else if (numberOfLivingNeighbors < myNumToReproduce - 1 ||
                 numberOfLivingNeighbors > myNumToReproduce) {
            cell.setMyNextState(GameOfLifeState.EMPTY);
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        myNumToReproduce = Integer.parseInt(simulationConfig.get("numToReproduce"));
    }

    @Override
    public Map<String, Integer> countCellsinGrid () {
        stepNum = getStepNum();
        stepNum++;
        int livingCount = 0;
        int emptyCount = 0;
        List<String> myNames = getSimulationNames();
        myOutput.put("Step", stepNum);
        for (Cell cell : getGrid().getImmutableCellGrid().values()) {
            if (cell.getMyCurrentState().equals(GameOfLifeState.LIVING)) {
                livingCount++;
            }

        }
        emptyCount = getGrid().getNumRows() * (getGrid().getNumColumns()) - livingCount;
        for (String name : myNames) {
            if (myOutput.containsKey(name)) {
                if (name.equals("Step")) {
                }
                else if (name.equals("EMPTY")) {
                    myOutput.put(name, emptyCount);
                }
                else if (name.equals("LIVING")) {
                    myOutput.put(name, livingCount);
                }
            }
            else {
                myOutput.put(name, 0);
            }
        }
        return myOutput;

    }

    @Override
    public void initializeSimulationToolbar (SimulationToolbar toolbar) {
        Slider myReproduceSlider = new Slider(1, 6, 3);
        myReproduceSlider.valueProperty()
                .addListener(e -> myNumToReproduce = (int) myReproduceSlider.getValue());
        toolbar.addSlider(myReproduceSlider, "neighborsToReproduce");
    }

    private enum GameOfLifeState implements State {

                                                   EMPTY(Color.GHOSTWHITE),
                                                   LIVING(Color.DARKGREEN);

        private final Color myColor;
        private double myProbability;

        GameOfLifeState (Color color) {
            myColor = color;
            myProbability = 0;
        }

        @Override
        public Color getColor () {
            return myColor;
        }

        @Override
        public double getProbability () {
            return myProbability;
        }

        @Override
        public void setProbability (double probability) {
            myProbability = probability;
        }
    }

    @Override
    public Cell createCell (Coordinate coordinate, State currentState) {
        GameOfLifeCell cell = new GameOfLifeCell(currentState, coordinate);
        return cell;
    }

    @Override
    public State[] getSimulationStates () {
        return GameOfLifeState.values();
    }

    @Override
    public State getSimulationState (String simulationState) {
        return GameOfLifeState.valueOf(simulationState.toUpperCase());

    }

}
