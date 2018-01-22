package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

        FXMLLoader loader = new FXMLLoader(getClass().getResource("vue/Synchro1.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,400,400);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
