package ci.miage.ayenon.app;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

	private static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;
		// TODO Auto-generated method stub		
		new Bootstraper().init(primaryStage);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}
	
	public static Stage getPrimaryStage()
	{
		return primaryStage;
	}

}
 