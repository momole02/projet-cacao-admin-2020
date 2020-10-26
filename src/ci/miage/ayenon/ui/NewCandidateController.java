package ci.miage.ayenon.ui;

import java.io.File;
import java.time.LocalDate;

import ci.miage.ayenon.model.Farmer;
import ci.miage.ayenon.model.Seeding;
import ci.miage.ayenon.utils.Uploader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class NewCandidateController {

	@FXML public TextField lastNameTxField ; 
	@FXML public TextField firstNameTxField;
	@FXML public RadioButton femaleRd;
	@FXML public RadioButton maleRd; 
	@FXML public TextField photoFileTxField;
	@FXML public TextField birthActFileTxField;
	@FXML public DatePicker birthDatePicker;
	@FXML public TextField birthPlaceTxField;
	@FXML public TextField phoneNumberTxField;
	
	@FXML public TextField localityTxField; 
	@FXML public TextField areaTxField;
	@FXML public RadioButton cocoaRd;
	@FXML public RadioButton coffeeRd;
	
	@FXML public TextField emailTxField; 
	@FXML public PasswordField passwordTxField ;
	@FXML public PasswordField confirmPasswordTxField; 
	
	@FXML public Button saveButton ;
	
	private File photoFile ; 
	private File birthActFile;

	private Farmer farmerData; 
	private Seeding seedingData; 
	
	public void initialize() 
	{
		maleRd.selectedProperty().set(true);
		cocoaRd.selectedProperty().set(true);
		
		farmerData = new Farmer();
		seedingData = new Seeding();
	}
	
	
	
	public void onCancel(ActionEvent e)
	{

		Stage stage = (Stage)((Button)e.getTarget()).getScene().getWindow();
		stage.close();
	}
	
	public File choosePicture() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une image");
		fileChooser.getExtensionFilters().add(
				new ExtensionFilter("Image JPEG", "*.jpg", "*.jpeg" , "*.jfif"));
		fileChooser.getExtensionFilters().add(
				new ExtensionFilter("Image PNG" , "*.png"));
		fileChooser.getExtensionFilters().add(
				new ExtensionFilter("Tous les fichiers" , "*.*"));
		return fileChooser.showOpenDialog(null);
	}
	
	public void choosePhoto() {
		File localPhotoFile = choosePicture();
		if(localPhotoFile == null)
			return ; 
		int rand = (int)Math.floor(Math.random() * 10000);
		File uploadedPhotoFile = Uploader.upload(Uploader.PHOTO_FOLDER, "_"+rand+localPhotoFile.getName(), localPhotoFile);
		photoFile = uploadedPhotoFile; 
		photoFileTxField.setText(photoFile.getAbsolutePath());
	}
	
	public void chooseBirthAct() {
		File localFile = choosePicture();
		if(localFile == null)
			return ; 
		
		int rand = (int)Math.floor(Math.random() * 10000);
		File uploadedFile = Uploader.upload(Uploader.BIRTH_ACT_FOLDER, "_"+rand+localFile.getName(), localFile);
		birthActFile = uploadedFile;
		birthActFileTxField.setText(birthActFile.getAbsolutePath());
	}
	
	private boolean validateIdentityInfo()
	{
		String lastName = lastNameTxField.getText().trim();
		if(lastName.length() < 3) {
			Alert alert = new Alert(AlertType.ERROR, "Le nom doit comporter au moins 3 caractères", ButtonType.OK );
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Nom invalide");
			alert.showAndWait();
			return false; 
		}
		
		
		String firstName = firstNameTxField.getText().trim();
		if(firstName.length() < 3) {
			Alert alert = new Alert(AlertType.ERROR, "Le prenom doit comporter au moins 3 caractères", ButtonType.OK );
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Prenom invalide");
			alert.showAndWait();
			return false; 
		}
		
		
		
		LocalDate birthDate = birthDatePicker.getValue();
		if(null == birthDate ) {
			Alert alert = new Alert(AlertType.ERROR, "Entrez une date de naissance" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Date de naissance invalide");
			alert.showAndWait();
			return false;
		}
		if( LocalDate.now().getYear() -  birthDate.getYear() < 18) {
			Alert alert = new Alert(AlertType.ERROR, "Le candidat doit avoir au moins 18 ans" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Date de naissance invalide");
			alert.showAndWait();
			return false;
		}
		
		String phoneNumber = phoneNumberTxField.getText().trim();
		if(!phoneNumber.matches("^\\+?[0-9]{4,}")) {
			Alert alert = new Alert(AlertType.ERROR, "Le numéro de téléphone doit au moins 4 chiffres(indicatif inclus)" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Téléphone invalide");
			alert.showAndWait();
			return false;
		}
		
		
		if(null == photoFile || !photoFile.exists()) {
			Alert alert = new Alert(AlertType.ERROR , "Choisissez une photo" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Photo invalide");
			alert.showAndWait();
			return false;
		}
		
		if(null == birthActFile || !birthActFile.exists()) {
			Alert alert = new Alert(AlertType.ERROR , "Choisissez un extrait de naissance" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Extrait de naissance invalide");
			alert.showAndWait();
			return false;
		}
		
		farmerData.setFirstName(firstName);
		farmerData.setLastName(lastName);
		farmerData.setBirthDate(birthDate);
		if(maleRd.isSelected())
			farmerData.setGender("M");
		else 
			farmerData.setGender("F");
		farmerData.setPhone(phoneNumber);
		farmerData.setFacePath(photoFileTxField.getText());
		farmerData.setBirthActPath(birthActFileTxField.getText());
		farmerData.setBirthPlace(birthPlaceTxField.getText());
		return true;
	}
	
	private boolean validateSeedingInfo()
	{
		String locality = localityTxField.getText().trim();
		if(locality.length() < 3) {
			Alert alert = new Alert(AlertType.ERROR, "La localité doit comporter au moins 3 caractères" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Localité invalide");
			alert.showAndWait();
			return false;
		}
		
		String area = areaTxField.getText().trim();
		if(!area.matches("[+-]?+(?>\\d++\\.?+\\d*+|\\d*+\\.?+\\d++)")) {
			Alert alert = new Alert(AlertType.ERROR, "Superficie invalide" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Superficie invalide");
			alert.showAndWait();
			return false;
		}
		
		seedingData.setLocality(locality);
		seedingData.setArea(Float.valueOf(area));
		if(cocoaRd.isSelected())
			farmerData.setProductType("CACAO");
		else 
			farmerData.setProductType("CAFE");
		return true;
	}
	
	private boolean validateAccountAccess() {
		String email = emailTxField.getText().trim();
		if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			Alert alert = new Alert(AlertType.ERROR, "Format de l'email invalide" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Email invalide");
			alert.showAndWait();
			return false;
			
		}
		
		String password = passwordTxField.getText();
		if(password.length() < 6) {
			Alert alert = new Alert(AlertType.ERROR, "Le mot de passe doit contenir au moins 6 caractères" , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Mot de passe trop faible");
			alert.showAndWait();
			return false;
		}
		
		String confirm = confirmPasswordTxField.getText();
		if(!confirm.equals(password)) {
			Alert alert = new Alert(AlertType.ERROR, "Les mots de passes ne correspondent pas " , ButtonType.OK);
			alert.setTitle("Formulaire invalide");
			alert.setHeaderText("Mot de passe != confirmation ");
			alert.showAndWait();
			return false;
		}
		farmerData.setEmail(email);
		farmerData.setPassword(password);
		return true; 
	}
	
	private boolean validateForm()
	{		
		return validateIdentityInfo() && validateSeedingInfo() && validateAccountAccess();
	}
	
	public void onSave(ActionEvent e) {
		if(validateForm()) { // is form valid
			seedingData.setIdCode(Seeding.generateCode());
			seedingData.insert();
			farmerData.setIdCode(Farmer.generateCode(farmerData));
			farmerData.setSeedingCode(seedingData.getIdCode());
			farmerData.insert();
			
			Alert alert = new Alert(AlertType.INFORMATION , "Candidat inscrit avec succès" , ButtonType.OK);
			alert.setTitle("Enregistrement reussi");
			alert.setHeaderText("Candidat enregistré");
			alert.showAndWait();
			
			Stage stage = (Stage)((Button)e.getTarget()).getScene().getWindow();
			stage.close();
			
		}
	}
	
	
	
	
}
