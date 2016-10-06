package grid;

import cell.Cell;

/**
 * 
 * @author Sean Hudson
 *
 */
public class ToroidalEdgeNeighborsHandler extends NeighborsHandler {

    public ToroidalEdgeNeighborsHandler (String myCellShape, Grid<Cell> grid) {
        super(myCellShape, grid);
    }

    public double wrapCoordinate (double value, int bound) {
        if (value < 0) {
            return value + bound;
        }
        else if (value >= bound) {
            return 0;
        }
        else {
            return value;
        }

    }

    @Override
    public Coordinate handleEdgeCoordinate (Coordinate coordinate,
                                            Coordinate neighborRelativeCoordinate) {
        Coordinate offGridCoordinate = coordinate.add(neighborRelativeCoordinate);
        double x = wrapCoordinate(offGridCoordinate.getX(), getMyGrid().getNumColumns());
        double y = wrapCoordinate(offGridCoordinate.getY(), getMyGrid().getNumRows());
        return new Coordinate(x, y);
    }

}
