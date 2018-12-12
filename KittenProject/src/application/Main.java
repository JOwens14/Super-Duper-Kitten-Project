/**
 * Main class
 * Authors: Logan Jenny, Rich Williams, Jake Owens, Isaiah Miller
 * version 1.0
 * 12/2018
 */
package application;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class Main extends Application {

	/* Stage field. */
	/**
	 * myStage
	 */
	public Stage myStage;
	

	protected static ExportableDataAggregate myPersistentData;
	
		/**
		 * version
		 */
		public String version = "0.1.9.0";
	    /**
	     * xOffset
	     */
	    private double xOffset = 0;
	    /**
	     * yOffset
	     */
	    private double yOffset = 0;

	    public static Home myMainHome;

	    /**
	     * theHome
	     */
	    public static Home theHome;

	 /* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	    public void start(Stage stage) throws Exception {
	     myStage = stage;
		 final URL rootURL = getClass().getResource("/application/kittenproject.fxml");
		 
	        FXMLLoader loader = new FXMLLoader(rootURL);
//	        loader.setLocation(getClass().getResource("sample.fxml"));
	        loader.setLocation(rootURL);
	        stage.initStyle(StageStyle.UNDECORATED);
		 	stage.setTitle("Kitten-Construction");
		 	stage.getIcons().add(new Image("application/resources/constructlogo.png"));
	        Parent root = (Parent) FXMLLoader.load(getClass().getResource("kittenproject.fxml")); //maybe the issue

	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setResizable(false);
	        
	        stage.show();

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
	 	 * changes the scene on the stage
	 	 * @param A
	 	 * @param event
	 	 * @throws IOException
	 	 */
	 	public void changeScene(String A, MouseEvent event) throws IOException {
	 	    Parent newb = FXMLLoader.load(getClass().getResource(A));
	        Scene newbScene = new Scene(newb);
	        //This line gets the Stage information
	        myStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        //sets new scene
	        myStage.setScene(newbScene);
	        myStage.show();
	        
		      //grab your root here
	        newbScene.setOnMousePressed(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               xOffset = event.getSceneX();
	               yOffset = event.getSceneY();
	           }
	       });

	       //move around here
	        newbScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               myStage.setX(event.getScreenX() - xOffset);
	               myStage.setY(event.getScreenY() - yOffset);
	           }
	       });
	 	}
	 	
	 	public void changeHomeScene(String A, MouseEvent event) throws IOException {
	 	    FXMLLoader loader = new FXMLLoader();
	 	    loader.setLocation(getClass().getResource("edithome.fxml"));
	        loader.setControllerFactory(new Callback<Class<?>, Object>() {
	            @Override
	            public Object call(Class<?> aClass) {
	                return new editHomeController(myPersistentData.getMyHome());
	            }
	        });
	        Parent newb = (Parent) loader.load();
	        Scene newbScene = new Scene(newb);
	        //This line gets the Stage information
	        myStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	        //sets new scene
	        myStage.setScene(newbScene);
	        myStage.show();
	        
		      //grab your root here
	        newbScene.setOnMousePressed(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               xOffset = event.getSceneX();
	               yOffset = event.getSceneY();
	           }
	       });

	       //move around here
	        newbScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
	           @Override
	           public void handle(MouseEvent event) {
	               myStage.setX(event.getScreenX() - xOffset);
	               myStage.setY(event.getScreenY() - yOffset);
	           }
	       });
	 	}
	 	

	 	/**
	 	 * gets version
	 	 * @return
	 	 */
	 	public String getVersion() {
	 		return version;
	 	}
	 	


		/**
		 * Main method that runs the program
		 * @param args
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 */
		public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		    if(myPersistentData == null) {
//		        myMainHome = new Home();
//		    	final Config overallConfig = new Config();
//		    	final Settings overallSettings = new Settings();
//		    	myPersistentData.setMySettings(overallSettings);
//		    	myPersistentData.setMyConfig(overallConfig);
//		    	myPersistentData.setMyHome(myMainHome);
		    	myPersistentData = new ExportableDataAggregate();
		    	myPersistentData.importJSON();
		    	if (myMainHome == null) {
		    		myMainHome = myPersistentData.getMyHome();
		    	}
//		    	overallConfig.initFromConfig(myPersistentData);//myMainHome, overallSettings);
//		    	System.out.println(myMainHome == myPersistentData.getMyHome());
//		    	System.out.println("my Main home is (after import): " + myMainHome);
//		    	System.out.println("Initialized config.");
		    	
		    }

//			final Config template = new Config();
//			System.out.println("construction.");
//			final String confPath = template.retConfigPath();
//			System.out.println("made conf path: " + confPath);
//			template.exportJSON(new File(confPath));
//			System.out.println("finished exporting.");
	    	launch(args);
	    }
}
