package simulation;

import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import grid.Neighbor;
import javafx.scene.paint.Color;


public class FireSimulation extends Simulation {
    private double probCatch;
    private int burnTime;

    public FireSimulation (Map<String, Map<String, String>> simulationConfig) {
        super(simulationConfig);
    }

    @Override
    public void step () {
        getGrid().applyFuncToCell(p -> setNextState(p));
        updateGrid();
        countCellsinGrid();
    }

    public boolean hasBurningNeighbor (Cell cell) {
        for (Cell neighborCell : getNeighborsHandler()
                .getOrthogonalNeighbors(cell.getMyGridCoordinate())) {
            if (neighborCell.getMyCurrentState().equals(FireState.BURNING)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void countCellsinGrid () {
        stepNum = getStepNum();
        System.out.println("Num of steps: " + stepNum);
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
        System.out.println("Burning:" + burningCount);
        System.out.println("Tree: " + treeCount);
        System.out.println("Empty: " + emptyCount);
        stepNum++;
    }

    // is switching on cell state bad?
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
        /*
         * if (r == 0 || c == 0 || r == (getGrid().getNumRows() - 1) ||
         * c == (getGrid().getNumColumns() - 1)) {
         * cell.setMyCurrentState(State.EMPTY);
         * }
         */
        cell.setBurnTimer(burnTime);
        return cell;
    }

//    private State randomGeneration() {
//        Random rn = new Random();
//        double spawnRandomNumber = rn.nextDouble() * 100;
//        double currentProbability = 0;
//        for(FireState state : FireState.values()) {
//            currentProbability += state.getProbability();
//            if(spawnRandomNumber < currentProbability) {
//                return state;
//            }
//        }
//        return FireState.valueOf(getDefaultState());
//    }
    
    public enum FireState implements State {

                                            EMPTY(Color.YELLOW),
                                            TREE(Color.GREEN),
                                            BURNING(Color.RED);

        private final Color myColor;
        private double myProbability;

        FireState (Color color) {
            myColor = color;
            myProbability = 0;
        }

        public Color getColor () {
            return myColor;
        }
        
        public double getProbability () {
            return myProbability;
        }
        
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
    public double getSpawnProbability (double currentProbability) {
        // TODO Auto-generated method stub
        return 0;
    }

}
