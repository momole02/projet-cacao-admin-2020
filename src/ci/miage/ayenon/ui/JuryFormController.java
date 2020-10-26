package ci.miage.ayenon.ui;

import java.time.LocalDate;

import ci.miage.ayenon.model.JuryMember;
import ci.miage.ayenon.utils.DateUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JuryFormController {
	
	
	@FXML Label titleLabel; 
	@FXML TextField firstNameTxField; 
	@FXML TextField lastNameTxField; 
	@FXML DatePicker birthDatePicker;
	@FXML TextField localityTxField; 
	
	@FXML Button validateButton ;
	
	private JuryMember jury ;
	
	public void initialize() {
		setToAddMode();
	}
	
	private void setToAddMode() {
		jury = new JuryMember();
		validateButton.setText("Ajouter un membre du jury");
		titleLabel.setText("AJOUTER UN MEMBRE DU JURY");
		validateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				addJury(event);
			}
		});
	}
	
	private void setToEditMode() {
		//Fill data 
		firstNameTxField.setText(jury.getFirstName());
		lastNameTxField.setText(jury.getLastName());
		birthDatePicker.setValue(DateUtils.fromISODate(jury.getBirthDateISO()));
		localityTxField.setText(jury.getLocality());
		titleLabel.setText("MODIFIER UN MEMBRE DU JURY");
		validateButton.setText("Modifier un membre du jury");
		validateButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				editJury(event);
			}
			
		});
	}
	
	public boolean validateForm() {
		
		String firstName = firstNameTxField.getText().trim();
		if(firstName.length() < 3) {
			Alert alert = new Alert(AlertType.ERROR , "Le prénom doit avoir au moins 3 caractères");
			alert.setHeaderText("Echec de la validation du formulaire");
			alert.setTitle("Erreur");
			alert.showAndWait();
			return false;
		}
		
		String lastName = lastNameTxField.getText().trim();
		if(lastName.length() < 3) {
			Alert alert = new Alert(AlertType.ERROR, "Le nom doit avoir au moins 3 caractères");
			alert.setHeaderText("Echec de la validation du formulaire");
			alert.setTitle("Erreur");
			alert.showAndWait();
			return false;
		}
		
		LocalDate now = LocalDate.now();
		LocalDate birthDate = birthDatePicker.getValue();
		if(now.getYear() - birthDate.getYear() < 18) {
			Alert alert = new Alert(AlertType.ERROR, "Le juré doit avoir au moins 18 ans");
			alert.setHeaderText("Echec de la validation du formulaire");
			alert.setTitle("Erreur");
			alert.showAndWait();
			return false;
		}
		
		String locality = localityTxField.getText().trim();
		if(locality.length() < 3) {
			Alert alert = new Alert(AlertType.ERROR , "La localité doit avoir au moins 3 caractères");
			alert.setHeaderText("Echec de la validation du formulaire");
			alert.setTitle("Erreur");
			alert.showAndWait();
			return false;
		}
		
		jury.setFirstName(firstName);
		jury.setLastName(lastName);
		jury.setBirthDateISO(DateUtils.toISODate(birthDate));
		jury.setLocality(locality);
		return true;
	}
	
	public void addJury(ActionEvent e) {
		if(validateForm()) {
			jury.setIdCode(JuryMember.generateCode());
			jury.insert();
			Alert alert = new Alert(AlertType.INFORMATION, "Membre du jury ajouté avec succès");
			alert.setHeaderText("Membre du jury ajouté");
			alert.setTitle("Ajout du membre du jury");
			alert.showAndWait();
			((Stage)((Button)e.getTarget()).getParent().getScene().getWindow()).close();
		}
	}
	
	public void onClose(ActionEvent e) {
		((Stage)((Button)e.getTarget()).getParent().getScene().getWindow()).close();
	}
	
	public void editJury(ActionEvent e) {
		if(validateForm()) {
			jury.update();
			Alert alert = new Alert(AlertType.INFORMATION, "Membre du jury modifié avec succès");
			alert.setHeaderText("Membre du jury modifié");
			alert.setTitle("Edition du membre du jury");
			alert.showAndWait();
			((Stage)((Button)e.getTarget()).getParent().getScene().getWindow()).close();
		}
	}
	
	public void setJuryMember(JuryMember member) {
		jury = member;
		setToEditMode();
	}
	

}
