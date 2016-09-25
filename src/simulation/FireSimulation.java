package simulation;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import grid.Grid;
import grid.GridView;
import grid.Neighbor;
import grid.Neighbors;
import grid.NormalEdgeNeighbors;
import grid.RectangleGridView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class FireSimulation extends Simulation {
    private double probCatch;
    private int burnTime;

    public FireSimulation (Element rootElement) {
        initializeSimulation(rootElement);
    }

    /*
     * public void printMap() {
     * for(Cell cell : input.values()) {
     * System.out.println(cell.getMyGridCoordinate().getX() + " " +
     * cell.getMyGridCoordinate().getY());
     * }
     * }
     */

    private Cell parseCells (String cellString) {
        String[] cellData = cellString.split("_");
        FireCell cell = new FireCell(State.valueOf(cellData[0].toUpperCase()),
                                     new Coordinate(Double.parseDouble(cellData[1]),
                                                    Double.parseDouble(cellData[2])));
        if(cell.getMyCurrentState().equals(State.BURNING)) {
            cell.setBurnTimer(burnTime);
        }
        return cell;

    }

    private String getStringValue (Element rootElement, String name) {
        return rootElement.getElementsByTagName(name).item(0).getFirstChild().getNodeValue();
    }

    // TODO-put config factory stuff in another class
    private void initializeSimulation (Element rootElement) {
        this.probCatch = Double.parseDouble(getStringValue(rootElement, "probCatch"));
        this.burnTime = Integer.parseInt(getStringValue(rootElement, "burnTime"));
        Map<Coordinate, Cell> cellGrid = new HashMap<Coordinate, Cell>();
        NodeList cells = rootElement.getElementsByTagName("cell");
        for (int i = 0; i < cells.getLength(); i++) {
            Cell cell = parseCells(cells.item(i).getFirstChild().getNodeValue());
            cellGrid.put(cell.getMyGridCoordinate(), cell);
        }
        int numRows = Integer.parseInt(getStringValue(rootElement, "numberOfRows"));
        int numCols = Integer.parseInt(getStringValue(rootElement, "numberOfColumns"));
        generateMap(numRows, numCols, cellGrid);
        setGrid(new Grid(numRows, numCols, cellGrid));
        double gridWidth = Double.parseDouble(getStringValue(rootElement, "gridWidth"));
        double gridHeight = Double.parseDouble(getStringValue(rootElement, "gridHeight"));
        // TODO-grid dimensions should come from SimulationController, type of grid will be
        // determined by input as well as edge type
        setGridView(new RectangleGridView(new Dimension2D(gridWidth, gridHeight), getGrid()));
        setNeighbors(new NormalEdgeNeighbors(getGrid()));
    }

    // Allows them to specify what coordinates they went, the rest are set to default by a function,
    // gets bounds and starter map from config
    public void generateMap (int numberOfRows,
                             int numberOfColumns,
                             Map<Coordinate, Cell> cellGrid) {
        for (int r = 0; r < numberOfRows; r++) {
            for (int c = 0; c < numberOfColumns; c++) {
                Coordinate coordinate = new Coordinate(r, c);
                if (!cellGrid.containsKey(coordinate)) {
                    FireCell cell = new FireCell(State.TREE, coordinate);
                    if (r == 0 || c == 0 || r == (numberOfRows - 1) || c == (numberOfColumns - 1)) {
                        cell.setMyCurrentState(State.EMPTY);
                    }
                    cellGrid.put(coordinate, cell);
                }

            }
        }
    }

    // very temporary code, just here to make it work
    @Override
    public void step () {
        // getGrid().applyFuncToCell(FireSimulation::setNextState); HOW DO I DO THIS?
        for (Cell cell : getGrid().getImmutableCellGrid().values()) {
            setNextState(cell);
        }
        updateGrid();

    }

    public boolean hasBurningNeighbor (Cell cell) {
        for (Cell neighborCell : getNeighbors().getNeighbors(Neighbor.SQUARE.getNeighbors(),
                                                             cell.getMyGridCoordinate())) {
            if (neighborCell.getMyCurrentState().equals(State.BURNING)) {
                return true;
            }
        }
        return false;
    }

    // is switching on cell state bad?
    @Override
    public void setNextState (Cell cell) {
        // System.out.println(cell.getMyCurrentState());
        if (cell.getMyCurrentState().equals(State.TREE)) {
            cell.setMyNextState(State.TREE);
            Random rn = new Random();
            if (hasBurningNeighbor(cell) && rn.nextInt(100) < probCatch) {
                cell.setMyNextState(State.BURNING);
                ((FireCell) cell).setBurnTimer(burnTime);
            }
        }
        else if (cell.getMyCurrentState().equals(State.BURNING)) {
            ((FireCell) cell).decrementBurnTimer();
            cell.setMyNextState(State.BURNING);
            if (((FireCell) cell).getBurnTimer() == 0) {
                cell.setMyNextState(State.EMPTY);
            }
        }
        else {
            cell.setMyNextState(State.EMPTY);
        }
    }

    @Override
    public void start () {
        // TODO Auto-generated method stub

    }

    @Override
    public void init () {
        // TODO Auto-generated method stub

    }

}
