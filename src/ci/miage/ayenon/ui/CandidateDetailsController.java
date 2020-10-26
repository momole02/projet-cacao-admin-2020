package ci.miage.ayenon.ui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import ci.miage.ayenon.model.Farmer;
import ci.miage.ayenon.model.Seeding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CandidateDetailsController {

	private @FXML ImageView photoImageView; 
	private @FXML Label codeLabel;
	private @FXML Label lastNameLabel ; 
	private @FXML Label firstNameLabel ; 
	private @FXML Label birthDateLabel ;
	private @FXML Label birthPlaceLabel;
	private @FXML Label genderLabel ; 
	private @FXML Label phoneLabel ; 
	
	private @FXML Label localityLabel ; 
	private @FXML Label areaLabel ; 
	private @FXML Label productLabel ; 
	
	private @FXML Label emailLabel ; 
	private @FXML Label passwordLabel ; 
	
	private Farmer farmer ;
	private Seeding farmerSeeding ; 
	
	public void initialize() {
		
	}
	
	public void set(Label label , String value) {
		String old = label.getText();
		label.setText(old +" "+value);
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer; 
		Seeding seeding = new Seeding();
		seeding.setIdCode(farmer.getSeedingCode());
		seeding.retrieve();
		this.farmerSeeding = seeding ; 
		
		///fill up labels & images
		set(codeLabel , farmer.getIdCode());
		set(firstNameLabel , farmer.getFirstName());
		set(lastNameLabel , farmer.getLastName());
		set(birthDateLabel , farmer.getBirthDateISO());
		set(birthPlaceLabel , farmer.getBirthPlace());
		set(genderLabel , farmer.getGender());
		set(phoneLabel, farmer.getPhone());
		
		set(localityLabel , farmerSeeding.getLocality());
		set(areaLabel, String.valueOf(farmerSeeding.getArea()));
		set(productLabel, farmer.getProductType().toLowerCase());
		
		set(emailLabel, farmer.getEmail() );
		set(passwordLabel, farmer.getPassword());
		
		try {
			File photoFile = new File(farmer.getFacePath());
			this.photoImageView.setImage(new Image(photoFile.toURI().toURL().toExternalForm()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void onViewPhoto() {
		
		File photoFile = new File(farmer.getFacePath());
		try {
			java.awt.Desktop.getDesktop().open(photoFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onViewBirthAct() {
		File birthActFile = new File(farmer.getBirthActPath());
		try {
			java.awt.Desktop.getDesktop().open(birthActFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClose(ActionEvent e) {
		((Stage)((Button)e.getTarget()).getScene().getWindow()).close();
	}

	
	
	
}
