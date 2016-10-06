package grid;
//This entire file is part of my masterpiece.
//Sean Hudson

/**
 * This class is part of my master piece because it shows the subclass of 
 * a good example of inheritance to promote extensibility and to reduce
 * code duplication
 */

import cell.Cell;
import javafx.geometry.Dimension2D;

/**
 * Class for handling the creation of a hexagon tiled grid.
 * 
 * @author Sean Hudson
 */
public class HexagonGridView extends GridView {
    private static final double DEFAULT_HEIGHT_OFFSET = 0.5;
    
    public HexagonGridView (Dimension2D gridSize, Grid<Cell> grid) {
        super(gridSize, grid);
    }

    /**
     * Draws a hexagon tiled grid.
     * 
     */
    @Override
    public void displayGrid () {
        double cellHeight = myGridSize.getHeight() / (myGrid.getNumRows() + DEFAULT_HEIGHT_OFFSET);
        for (int r = 0; r < myGrid.getNumRows(); r++) {
            for (int c = 0; c < myGrid.getNumColumns(); c++) {
                Hexagon gridCellDisplay =
                        new Hexagon(cellHeight, myGridSize.getWidth(), myGrid.getNumColumns());
                double xView =
                        c * gridCellDisplay.getStretchedEffectiveWidth() +
                               gridCellDisplay.getStretchedWidth() / 2;
                double yView = r * cellHeight - ((cellHeight / 2) * (c % 2)) + cellHeight / 2;
                gridCellDisplay.setLayoutX(xView);
                gridCellDisplay.setLayoutY(yView);
                configureShape(gridCellDisplay, new Coordinate(c, r));
                addCellToRoot(gridCellDisplay);
            }
        }
    }

}
