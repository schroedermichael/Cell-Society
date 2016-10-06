// This entire file is part of my masterpiece.
// Sean Hudson

/**
 * This class is part of my masterpiece because it exemplifies: 
 * Separation of concerns, re-usability/generic, open-closed principle and interfaces
 * Separations of concerns - Grid class only deals with matters related to accessing and managing the data structure
 * re-usability - I re-factored it to be of a generic type so that it could be re-used in any project
 * open-closed principle - Grid is such a central class to the project that its API shouldn't diminish, but it should be flexible to add more methods
 * interface - implements the Searchable interface which give immutable access to the Grid data. Interface is passed in instead of Grid reference when edits to the grid aren't needed
 */

package grid;

import java.util.*;
import java.util.function.Consumer;

/**
 * Generic grid class that maps a coordinate to a specified type of object
 *
 * 
 * @author Sean Hudson
 *
 */
public class Grid<E> implements Iterable<E>, Searchable<E> {
    private Map<Coordinate, E> myCellGrid = new HashMap<Coordinate, E>();

    private int myNumberOfRows;
    private int myNumberOfColumns;

    public Grid (int numberOfRows, int numberOfColumns, Map<Coordinate, E> initCells) {
        myNumberOfRows = numberOfRows;
        myNumberOfColumns = numberOfColumns;
        myCellGrid = initCells;
    }

    /**
     * gets the raw grid
     * 
     * @return grid
     */
    public Map<Coordinate, E> getCellGrid () {
        return myCellGrid;
    }

    /**
     * gets an immutable version of the grid
     * 
     * @return grid
     */
    public Map<Coordinate, E> getImmutableCellGrid () {
        return Collections.unmodifiableMap(getCellGrid());
    }

    @Override
    public Iterator<E> iterator () {
        return getImmutableCellGrid().values().iterator();
    }

    @Override
    public void applyFuncToCell (Consumer<E> func) {
        getImmutableCellGrid().values().forEach(func);
    }

    /**
     * Checks to see if the cell is in the grid
     * 
     * @param coordinate
     * @return if cell in grid
     */
    public Boolean isCreated (Coordinate coordinate) {
        return myCellGrid.containsKey(coordinate);
    }

    /**
     * Checks to see if the coordinate is inside the bounds of the grid
     * 
     * @param coordinate
     * @return if coordinate in grid bounds
     */
    public Boolean isInGrid (Coordinate coordinate) {
        return coordinate.getX() > -1 && coordinate.getX() < getNumRows() ||
               coordinate.getY() > -1 && coordinate.getY() < getNumColumns();
    }

    @Override
    public E getCell (Coordinate coordinate) {
        return myCellGrid.get(coordinate);
    }

    /**
     * Adds cell entry to the grid
     * 
     * @param coordinate
     * @param cell
     */
    public void addCell (Coordinate coordinate, E cell) {
        myCellGrid.put(coordinate, cell);
    }

    @Override
    public int getNumRows () {
        return myNumberOfRows;
    }

    @Override
    public int getNumColumns () {
        return myNumberOfColumns;
    }

    /**
     * sets the cell grid
     * 
     * @param cellGrid
     */
    public void setCellGrid (Map<Coordinate, E> cellGrid) {
        this.myCellGrid = cellGrid;
    }

}
