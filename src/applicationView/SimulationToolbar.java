package applicationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import controller.ApplicationController;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;


public class SimulationToolbar {
    private final ResourceBundle GUIResources;
    ApplicationController myAppController = new ApplicationController();
    Group root;
    private Series<Number, Number> firstSeries = new XYChart.Series<Number, Number>();
    private Series<Number, Number> secondSeries = new XYChart.Series<Number, Number>();
    private Series<Number, Number> thirdSeries = new XYChart.Series<Number, Number>();
    private ArrayList<Integer> myGraphValues;

    public SimulationToolbar () {
        GUIResources = ResourceBundle.getBundle("resources/English");
    }

    public void initSimToolbar (int height, int width, Scene myScene) {
        root = (Group) myScene.getRoot();
        VBox mySimToolbar = new VBox();
        mySimToolbar.getChildren().add(createGraph(myScene));
        root.getChildren().add(mySimToolbar);

    }

    private LineChart<Number, Number> myLineChart =
            new LineChart<Number, Number>(new NumberAxis(), new NumberAxis());

    private LineChart<Number, Number> createGraph (Scene myScene) {

        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();
        x_axis.setLabel(GUIResources.getString("XAxis"));
        // myLineChart.setTitle(GUIResources.getString("ChartTitle"));
        myLineChart.setPrefWidth(500);
        myLineChart.setPrefHeight(40);
        myLineChart.setTranslateY(370);
        myLineChart.getData().add(firstSeries);
        myLineChart.getData().add(secondSeries);
        myLineChart.getData().add(thirdSeries);
        myLineChart.setLegendSide(Side.RIGHT);
        myLineChart.setLegendVisible(true);
        // firstSeries.setName("Burning");
        // secondSeries.setName("Empty");
        return myLineChart;
    }

    public void updateGraph (List<Integer> myOutput) {
        firstSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(1)));
        if (myOutput.size() > 2) {
            secondSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(2)));
        }
        if (myOutput.size() > 3) {
            thirdSeries.getData().add(new Data<Number, Number>(myOutput.get(0), myOutput.get(3)));
        }
    }

}
