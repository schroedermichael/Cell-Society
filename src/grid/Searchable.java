// This entire file is part of my masterpiece.
// Sean Hudson

/**
 * This Interface is part of my masterpiece because it shows a good use of interfaces
 * as defining the public API for a class. In this case, it gives only immutable access
 * to the Grid data
 */

package grid;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Interface for establishing the public contract for an immutable query of the data structure
 * 
 * @author Sean Hudson
 *
 * @param <E>
 */
public interface Searchable<E> {
    /**
     * gets the number of rows of a data structure
     * 
     * @return number of rows
     */
    public int getNumRows ();

    
    /**
     * gets the number of columns of a data structure
     * 
     * @return number of columns
     */
    public int getNumColumns ();
    
    /**
     * applies a function to every cell in the grid
     * 
     * @param func
     */
    public void applyFuncToCell (Consumer<E> func);
    
    /**
     * gets the value associated with a coordinate key
     * 
     * @param coordinate
     * @return cell value
     */
    public E getCell (Coordinate coordinate);
    
    /**
     * gets an immutable copy of the grid
     * 
     * @return immutable version of the grid
     */
    Map<Coordinate, E> getImmutableCellGrid (); 
}
