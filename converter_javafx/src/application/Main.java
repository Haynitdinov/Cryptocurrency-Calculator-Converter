package application;

/**
 * This program implements an application that upload data like current price and balance of your account from exchange "exmo.com".
 * Then view in javafx window. Also we can calculate profit from the deal. 
 * We can create sell/buy order.   
 * 
 * @author Artur Haynitdinov
 * @version 1.0
 * @since 02-12-2018
 * @see Documentation of <a href="https://exmo.com/en/api">
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
			// read .fxml file and draw interface
			Parent root = FXMLLoader.load(getClass().getResource("window.fxml"));
			primaryStage.setTitle("cryptocurrency calculator & converter v1.0 Lite");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
	}

	/**
	 * This method start program
	 * @param args
	 */
	public static void main(String[] args) {

		launch(args);
		
	}
}
