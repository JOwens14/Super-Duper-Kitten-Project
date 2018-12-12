/**
 * List View Controller
 * Authors: Logan Jenny, Rich Williams, Jake Owens, Isaiah Miller
 * version 1.0
 * 12/2018
 */
package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class listViewController extends Main implements Initializable {
	/**
	 * menuButton
	 */
	@FXML MenuButton menuButton;
	/**
	 * pane
	 */
	@FXML Pane pane;
	/**
	 * listView
	 */
	@FXML ListView<project> listView;
	/**
	 * xOffset
	 */
	private double xOffset = 0;
    /**
     * yOffset
     */
    private double yOffset = 0;

    /**
     * mySettings
     */
    Settings mySettings;
    
    /**
     * selected
     */
    static project selected;
    /**
     * myProjects
     */
    ProjectList myProjects;
    

//    final ExportableDataAggregate myPersistentData;
    
//    public listViewController() {
//    	final Config overallConfig = new Config();
//    	final Settings overallSettings = new Settings();
//    	overallConfig.initFromConfig(myMainHome, overallSettings);
//    	myPersistentData = new ExportableDataAggregate(overallSettings, overallConfig, myMainHome);
//    }
    

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
    public void initialize(URL url, ResourceBundle rb) {
				//populates list
		listView.setEditable(true);
		myProjects = new ProjectList();
		listView.setItems(myProjects.getPros());
		pane.setVisible(false);
//		mySettings = new Settings();
		//end populate
    }
	
	/**
	 * returns selected
	 * @return
	 */
	public static project getSelected() {
	    return selected;
	}
	
	//clicking list item
	/**
	 * opens project view
	 * @param arg0
	 * @throws IOException
	 */
	@FXML public void handleMouseClick(MouseEvent arg0) throws IOException {
	    selected = listView.getSelectionModel().getSelectedItem();                      //remembers what was clicked, needs to be pass on to projectview
	    //System.out.println("clicked on " + selected); //prints what you clicked on
	    if(selected != null) {
	        changeScene("projectView.fxml", arg0);
	    }
	}
	
	
    /**
     * closes program
     * @param event
     */
    @FXML
    void halt(MouseEvent event) {
        Platform.exit();
    }
    
    /**
     * sort by cost
     * @param event
     */
    @FXML
    void costSort(MouseEvent event) {
    	myProjects.sortCost();
       
    }
    
    /**
     * sort by diff
     * @param event
     */
    @FXML
    void difficultySort(MouseEvent event) {
    	myProjects.sortDifficulty();
    	
    }
    
    /**
     * sort by energy savings
     * @param event
     */
    @FXML
    void energySort(MouseEvent event) {
    	myProjects.sortEnergySavings();
       
    }
    
    /**
     * glow
     * @param event
     */
    @FXML
    void hoverGlow(MouseEvent event) {
        ((Node) event.getSource()).setEffect(new Glow(0.8));
    }
    
    /**
     * glow
     * @param event
     */
    @FXML
    void hoverShadow(MouseEvent event) {
        ((Node) event.getSource()).setEffect(new Shadow(0.8, null));
    }
    
    /**
     * unglow
     * @param event
     */
    @FXML
    void unglow(MouseEvent event) {
        ((Node) event.getSource()).setEffect(null);
    }

    /**
     * opens about page
     * @param event
     * @throws IOException
     */
    @FXML
    void about(MouseEvent event) throws IOException {
    	Stage stage = new Stage();
    	stage.initStyle(StageStyle.UNDECORATED);
    	stage.setTitle("About");
	 	stage.getIcons().add(new Image("application/resources/constructlogo.png"));
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("aboutPage.fxml")); //maybe the issue
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        //movability section
	      //grab your root here
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }
    });

    //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    });
    }
    
    /**
     * open menu
     * @param event
     * @throws IOException
     */
    @FXML
    void menu(MouseEvent event) throws IOException {
        pane.setVisible(true);
    }
    
    /**
     * hide menu
     * @param event
     * @throws IOException
     */
    @FXML
    void hideMenu(MouseEvent event) throws IOException {
        pane.setVisible(false);
    }
    
    /**
     * exports file
     * @param event
     */
    @FXML
    void exportFile(final MouseEvent event) {
        // call File chooser's export method pass the stage
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final File exportDest = FileChooserDIY.specSaveFile(stage);
        if (exportDest != null) {
            myPersistentData.exportJSON(exportDest);
        }
    }

    /**
     * import file
     * @param event
     */
    @FXML
    void importFile(MouseEvent event) {
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final File importDest = FileChooserDIY.specOpenFile(stage);
        if (importDest != null) {
            myPersistentData.importJSON(importDest);
        }
    }
    

    /**
     * opens settings menu
     * @param event
     * @throws IOException
     */
    @FXML
    void openSettingsView(MouseEvent event) throws IOException {
        //open settings view, pass settings object
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Settings.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new SettingsController(myPersistentData.getMySettings());
            }
        });
        
        
        
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.getIcons().add(new Image("application/resources/constructlogo.png"));

        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    
    
//    
//    private void changeScene(final String theFXMLName, final Object theControllerParam) {
//    	 FXMLLoader loader = new FXMLLoader();
//         loader.setLocation(getClass().getResource(theFXMLName));
//         loader.setControllerFactory(new Callback<Class<?>, Object>() {
//             @Override
//             public Object call(Class<?> aClass) {
//                 return new SettingsController(myPersistentData.getMySettings());
//             }
//         });
//         
//         
//         
//         Stage stage = new Stage();
//         stage.setTitle("Settings");
//         stage.getIcons().add(new Image("application/resources/constructlogo.png"));
//
//         Parent root = (Parent) loader.load();
//         Scene scene = new Scene(root);
//         stage.setScene(scene);
//         stage.setResizable(false);
//         stage.show();
//    }
    
    
    
    /**
     * changes to home updater scene
     * @param arg0
     * @throws IOException
     */
    @FXML
    void openUpdaterView(MouseEvent arg0) throws IOException {
        //((Node)(arg0.getSource())).getScene().getWindow().hide();
       changeHomeScene("edithome.fxml", arg0);
    	
    }
}
   

