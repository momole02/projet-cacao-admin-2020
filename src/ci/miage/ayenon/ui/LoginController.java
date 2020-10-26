package ci.miage.ayenon.ui;

import java.io.IOException;

import ci.miage.ayenon.app.App;
import ci.miage.ayenon.model.JuryMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	
	@FXML private Button loginButton;
	@FXML private TextField juryNumberTxCtrl;
	
	@FXML private TextField loginTxField;
	@FXML private PasswordField passwordTxField;
	
	public void initialize() {
		
	}
	public void onLogin(ActionEvent e)
	{
		
		String login = loginTxField.getText();
		String password = passwordTxField.getText();
		if(!login.equals("root") || !password.equals("123456789")) {
			Alert al = new Alert(AlertType.ERROR , "Login et/ou mot de passe invalide");
			al.setHeaderText(null);
			al.setTitle("Accès refusé");
			al.showAndWait();
			return ; 
		}
		App.getPrimaryStage().hide();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
			Stage dashboard = new Stage();
			dashboard.setScene(new Scene(root));
			dashboard.setTitle("CNIC - Espace administrateur");
			dashboard.setHeight(800);
			dashboard.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void onJuryLogin() {
		
		String numJury = juryNumberTxCtrl.getText().trim();
		if(numJury.isEmpty()) {

			Alert alert = new Alert(AlertType.ERROR , "Numéro de jury invalide");
			alert.setHeaderText(null);
			alert.setTitle("Accès refusé");
			alert.show();
			return ; 
		}
		
		JuryMember member = new JuryMember();
		member.setIdCode(numJury);
		member.retrieve();
		if(member.getFirstName() != null && !member.getFirstName().isEmpty()) {
			App.getPrimaryStage().hide();
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("jury_space.fxml"));
				Parent root = loader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("CNIC - Espace jury");
				stage.setHeight(800);
				JuryZoneController controller = loader.<JuryZoneController>getController();
				controller.setJuryMember(member);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Alert alert = new Alert(AlertType.ERROR , "Numéro de jury non trouvé");
			alert.setHeaderText(null);
			alert.setTitle("Accès refusé");
			alert.show();
		}
	}
	
	public void onClose() {
		App.getPrimaryStage().close();
	}
}
