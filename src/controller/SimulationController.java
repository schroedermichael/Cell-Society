package controller;

import java.io.File;
import org.w3c.dom.Element;
import applicationView.Toolbar;
import javafx.scene.Group;
import javafx.scene.Scene;
import simulation.Simulation;
import xml.XMLParser;


public class SimulationController {

    private Simulation simulation;
    private XMLParser parser = new XMLParser();
    private Group simulationRoot;

    SimulationController (Group simulationRoot) {
        // File simulationConfig = new File("src/resources/FireSettings.xml");

        this.simulationRoot = simulationRoot;
        File simulationConfig = new File("src/resources/GameOfLife.xml");
        initializeSimulation(simulationConfig.getAbsolutePath());
        // simulation = new PredatorPreySimulation();
    }

    /**
     * TODO-How to make more concise?
     * TODO-add in xml exceptions, might have to refactor into other classes like a factory one
     * 
     * @param xmlFilename
     */
    void initializeSimulation (String xmlFilename) {
        Element rootElement = parser.getRootElement(xmlFilename);
        this.simulation = parser.createSimulation(rootElement);
        simulation.removeGridViewSceneGraph(simulation.getGridView().getRoot());
        simulation.addGridViewSceneGraph(simulationRoot);
    }

    public Simulation getSimulation () {
        return simulation;
    }
}
