package ci.miage.ayenon.app;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bootstraper {

	public void init(Stage primaryStage) throws IOException
	{
		
		///TODO check saved session 
		Parent root = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
		
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("CNIC - Connexion");
		primaryStage.setResizable(false);
		primaryStage.show();

	}
}
