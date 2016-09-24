package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import cell.Cell;
import cell.FireCell;
import cell.State;
import grid.Coordinate;
import grid.GridView;
import grid.Neighbors;
import grid.RectangleGrid;
import grid.RectangleGridView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class FireSimulation extends Simulation {
    Map<Coordinate, Cell> temp = new HashMap<Coordinate, Cell>(); //temporary solution
    int counter = 0;
    private boolean diagonalNeighbors;
    public FireSimulation () {
        diagonalNeighbors = true;
        ArrayList<Cell> input = new ArrayList<Cell>();

        input.add(new FireCell(State.BURNING,
                               new Coordinate(2, 2)));
        input.add(new FireCell(State.TREE, new Coordinate(3,3)));
        setGrid(new RectangleGrid(5, 5, input));
        setGridView(new RectangleGridView(new Dimension2D(500, 500), getGrid()));
    }

    //very temporary code, just here to make it work
    @Override
    public void step () {
        counter++;
        Iterator<Map.Entry<Coordinate, Cell>> iterCell = getGrid().getCellGridIterator();

        for (Coordinate coordinate : temp.keySet()) {
            getGrid().addCell(temp.get(coordinate));
        }
        temp.clear();
        iterCell = getGrid().getCellGridIterator();
        while (iterCell.hasNext()) {
            Cell cell = iterCell.next().getValue();
            setNextState(cell);
        }
        getGrid().updateGrid();
        System.out.println(counter);
        if(counter % 100 == 0)
            this.getGridView().updateView();
        //this.getGridView().d
        
    }

    @Override
    public void createNeighbors (Cell cell) {
        Neighbors neighbors = getGrid().getNeighbors(cell, diagonalNeighbors);
        Iterator<Coordinate> iterNewCell = neighbors.getUncreatedNeighborCoordinates();
        while (iterNewCell.hasNext()) {
            Coordinate coordinate = iterNewCell.next();
            Cell newCell = new FireCell(State.EMPTY, coordinate);
            //getGrid().addCell(newCell);
            temp.put(coordinate, newCell);
        }
    }

    // test filler code
    @Override
    public void setNextState (Cell cell) {
        System.out.println(cell.getMyCurrentState());
        if (cell.getMyCurrentState() == State.EMPTY) {
            cell.setMyNextState(State.TREE);
        }
        else if (cell.getMyCurrentState() == State.TREE) {
            cell.setMyNextState(State.BURNING);
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
