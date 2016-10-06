package applicationView;

import java.util.List;
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
            new LineChart<Number, Number>(new NumberAxis(), new NumberAxis());;

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
        for (int i = 0; i < myNamesForLegend.size(); i++) {
            String checkName = myNamesForLegend.iterator().next();
            if (checkName.equals("Step")) {
            }
            else if (myFirstSeries == null) 
                myFirstSeries.setName(checkName);
            else if (mySecondSeries == null) {
                mySecondSeries.setName(checkName);
                myLineChart.getData().add(mySecondSeries);
            }
            else if (myThirdSeries == null) {
                myThirdSeries.setName(checkName);
                myLineChart.getData().add(myThirdSeries);
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
    public void updateGraph (Map<String,Integer> myData) {
        Set<String> myLegendNames = myData.keySet();
        addToLegend(myLegendNames);
        myFirstSeries.getData().add(new Data<Number, Number>(myData.get(0), myData.get(1)));
        if (myData.size() > 2) {
            mySecondSeries.getData()
                    .add(new Data<Number, Number>(myData.get(0), myData.get(2)));
        }
        if (myData.size() > 3) {
            myThirdSeries.getData().add(new Data<Number, Number>(myData.get(0), myData.get(3)));
        }
    }

}
