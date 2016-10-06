// This entire file is part of my masterpiece.
// Sean Hudson

/**
 * This class is part of my masterpiece because it shows the super class of a good use of 
 * inheritance to promote extensibility and reduction of code duplication
 */

package grid;

import cell.Cell;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

/**
 * An abstract class that gridViews composed of differently shaped tiled grids can inherit from.
 * @author Sean Hudson
 *
 */
public abstract class GridView {
    private static final int DEFAULT_STROKE_WIDTH = 2;
    private static final Paint DEFAULT_STROKE_FILL = Paint.valueOf("BLACK");
    private Group myRoot = new Group();
    protected Searchable<Cell> myGrid;
    protected Dimension2D myGridSize;

    public GridView (Dimension2D gridSize, Grid<Cell> grid) {
        this.myGridSize = gridSize;
        this.myGrid = grid;
        displayGrid();
    }

    /**
     * Display the grid
     */
    public abstract void displayGrid ();

    /**
     * Clear the root and display the grid
     */
    public void updateView () {
        myRoot.getChildren().clear();
        displayGrid();
    }

    /**
     * 
     * @return root
     */
    public Group getRoot () {
        return myRoot;
    }

    /**
     * Set the lines and the colors of the grid
     * @param shape
     * @param coordinate key
     */
    public void configureShape (Shape shape, Coordinate key) {
        shape.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        shape.setStroke(DEFAULT_STROKE_FILL);
        if (myGrid.getImmutableCellGrid().containsKey(key)) {
            shape.setFill(myGrid.getCell(key).getColor());

        } 
    }

    /**
     * Displays the cells on the screen by adding them to the gridView root
     * @param shape
     */
    public void addCellToRoot (Shape shape) {
        myRoot.getChildren().add(shape);
    }
}
