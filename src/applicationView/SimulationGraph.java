// This entire file is part of my masterpiece.
// Kayla Schulz

/**
 * This code is a part of my masterpiece because of the significant refactor it
 * needed. When we submitted the code, I was bothered by the obvious design flaw
 * with this class. There was a list of the different states to be input into the
 * graph legend, and there was another list with the corresponding data. This
 * was a huge problem, because if one index changed, suddenly the graph was entirely
 * wrong. So, although this code isn't as neat at the other one (it requires more
 * checks), this one is much more realistic and drastically reduces the risk of
 * another person coming in and ruining the graph for the entire simulation. The
 * one downfall in this piece though, is the need to use booleans to check if a
 * series has been created yet. I was unable to find any boolean operation for a series
 * other than .equals, which did not give me what I needed. I would be interested
 * to talk about alternatives to the boolean. But overall, I am satisfied with the
 * change, knowing that the data is being passed with its name, rather than relying
 * on it being on the same index in two entirely separate lists.
 * 
 * A significant amount of the refactor can also be found in each simulation. Instead
 * of two methods that return two different lists, I coordinated the methods so 
 * only one needs to communicate with other classes. That way, one method changed
 * from public to protected, giving it less communication with outside classes.
 */

package applicationView;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;


/**
 * This class creates the simulation graph by taking in data and placing it into
 * a series that is then shown in a line chart. This line chart will be updated at
 * every step of the simulation.
 * 
 * @author Kayla Schulz
 *
 */
public class SimulationGraph {
    private Series<Number, Number> myFirstSeries = new XYChart.Series<Number, Number>();
    private Series<Number, Number> mySecondSeries = new XYChart.Series<Number, Number>();
    private Series<Number, Number> myThirdSeries = new XYChart.Series<Number, Number>();
    private LineChart<Number, Number> myLineChart =
            new LineChart<Number, Number>(new NumberAxis(), new NumberAxis());
    private boolean firstUsed = false;
    private boolean secondUsed = false;
    private boolean thirdUsed = false;

    /**
     * Creates the graph by setting the preferred height and adding the first
     * series of data. It also sets the legend and the position of the legend
     * 
     * @return myLineChart
     */
    public LineChart<Number, Number> createGraph () {
        myLineChart.setPrefHeight(50);
        myLineChart.getData().add(myFirstSeries);
        myLineChart.setLegendVisible(true);
        myLineChart.setLegendSide(Side.RIGHT);
        myLineChart.setCreateSymbols(false);
        return myLineChart;
    }

    /**
     * Takes in a list of strings that include the names to be included in the
     * legend. Then, the series names are set based on the order they are input
     * into the list
     * 
     * @param myNamesForLegend
     */
    public void addToLegend (Set<String> myNamesForLegend) {
        Iterator<String> myit = myNamesForLegend.iterator();
        while (myit.hasNext()) {
            String checkName = myit.next();
            if (checkName.equals("Step")) {
            }
            else if (checkName.equals(myFirstSeries.getName()) ||
                     checkName.equals(mySecondSeries.getName()) ||
                     checkName.equals(myThirdSeries.getName())) {
            }
            else if (!firstUsed) {
                myFirstSeries.setName(checkName);
                firstUsed = true;
            }
            else if (!secondUsed) {
                mySecondSeries.setName(checkName);
                myLineChart.getData().add(mySecondSeries);
                secondUsed = true;
            }
            else if (!thirdUsed) {
                myThirdSeries.setName(checkName);
                myLineChart.getData().add(myThirdSeries);
                thirdUsed = true;
            }
            else {
            }
        }
    }

    /**
     * Takes an input of the list of data to update the graph. Always uses the first
     * piece of data in the list as the step (for the x-axis) and the rest of the
     * data is for the number of cells in that particular state.
     * 
     * @param myData
     */
    public void updateGraph (Map<String, Integer> myData) {
        Set<String> myLegendNames = myData.keySet();
        addToLegend(myLegendNames);
        myFirstSeries.getData().add(new Data<Number, Number>(myData.get("Step"),
                                                             myData.get(myFirstSeries.getName())));
        if (myData.size() > 2) {
            mySecondSeries.getData()
                    .add(new Data<Number, Number>(myData.get("Step"),
                                                  myData.get(mySecondSeries.getName())));
        }
        if (myData.size() > 3) {
            myThirdSeries.getData()
                    .add(new Data<Number, Number>(myData.get("Step"),
                                                  myData.get(myThirdSeries.getName())));
        }
    }

}
