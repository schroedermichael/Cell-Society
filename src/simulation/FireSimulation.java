package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import applicationView.SimulationToolbar;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;


/**
 * 
 * @author Sean Hudson
 *
 */
public class FireSimulation extends Simulation {

    private double probCatch;
    private int burnTime;
    private Map<String, Integer> myOutput = new HashMap<String, Integer>();

    public FireSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {
        getGrid().applyFuncToCell(p -> setNextState(p));
        updateGrid();
    }

    public boolean hasBurningNeighbor (Cell cell) {
        for (Cell neighborCell : getNeighborsHandler()
                .getSurroundingNeighbors(cell.getMyGridCoordinate())) {
            if (neighborCell.getMyCurrentState().equals(FireState.BURNING)) {
                System.out.println(cell.getMyGridCoordinate());
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Integer> countCellsinGrid () {
        stepNum = getStepNum();
        stepNum++;
        int burningCount = 0;
        int treeCount = 0;
        int emptyCount = 0;
        for (Cell cell : getGrid().getImmutableCellGrid().values()) {

            if (cell.getMyCurrentState().equals(FireState.BURNING)) {
                burningCount++;
            }
            if (cell.getMyCurrentState().equals(FireState.TREE)) {
                treeCount++;
            }
            if (cell.getMyCurrentState().equals(FireState.EMPTY)) {
                emptyCount++;
            }
        }
        List<String> myNames = getSimulationNames();
        myOutput.put("Step", stepNum);
        for (String name : myNames) {
            if (myOutput.containsKey(name)) {
                if (name.equals("Step")) {
                }
                if (name.equals("BURNING")) {
                    myOutput.put(name, burningCount);
                }
                if (name.equals("TREE"))
                    myOutput.put(name, treeCount);
                if (name.equals("EMPTY"))
                    myOutput.put(name, emptyCount);
            }
            else {
                myOutput.put(name, 0);
            }
        }

        return myOutput;
    }

    public void setNextState (Cell cell) {
        if (cell.getMyCurrentState().equals(FireState.TREE)) {
            cell.setMyNextState(FireState.TREE);
            Random rn = new Random();
            if (hasBurningNeighbor(cell) && rn.nextInt(100) < probCatch) {
                cell.setMyNextState(FireState.BURNING);
                ((FireCell) cell).setBurnTimer(burnTime);
            }
        }
        else if (cell.getMyCurrentState().equals(FireState.BURNING)) {
            ((FireCell) cell).decrementBurnTimer();
            cell.setMyNextState(FireState.BURNING);
            if (((FireCell) cell).getBurnTimer() == 0) {
                cell.setMyNextState(FireState.EMPTY);
            }
        }
        else {
            cell.setMyNextState(FireState.EMPTY);
        }
    }

    @Override
    public void initializeSimulationDetails (Map<String, String> simulationConfig) {
        this.probCatch = Double.parseDouble(simulationConfig.get("probCatch"));
        this.burnTime = Integer.parseInt(simulationConfig.get("burnTime"));
    }

    @Override
    public Cell createCell (Coordinate coordinate, State currentState) {
        FireCell cell = new FireCell(currentState, coordinate);
        int r = (int) coordinate.getX();
        int c = (int) coordinate.getY();

        if (getEdgeType().equals("Normal") && r == 0 || c == 0 ||
            r == (getGrid().getNumRows() - 1) ||
            c == (getGrid().getNumColumns() - 1)) {
            cell.setMyCurrentState(FireState.EMPTY);
        }
        cell.setBurnTimer(burnTime);
        return cell;
    }

    @Override
    public void initializeSimulationToolbar (SimulationToolbar toolbar) {
        Slider fireSlider = new Slider(0, 100, probCatch);
        fireSlider.valueProperty().addListener(e -> this.probCatch = fireSlider.getValue());
        toolbar.addSlider(fireSlider, "probCatch");
    }

    private enum FireState implements State {

                                             EMPTY(Color.YELLOW),
                                             TREE(Color.GREEN),
                                             BURNING(Color.RED);

        private final Color myColor;
        private double myProbability;

        FireState (Color color) {
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
    public State[] getSimulationStates () {
        return FireState.values();
    }

    @Override
    public State getSimulationState (String simulationState) {
        return FireState.valueOf(simulationState.toUpperCase());
    }

    @Override
    public List<String> getSimulationNames () {
        List<String> myList = new ArrayList<String>();
        for (State n : getSimulationStates()) {
            myList.add(n.name());
        }
        return myList;
    }

}
