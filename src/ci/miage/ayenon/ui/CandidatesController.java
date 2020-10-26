package ci.miage.ayenon.ui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ci.miage.ayenon.model.Farmer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CandidatesController {

	@FXML private TableView<Farmer> candidateTView; 
	@FXML private TextField searchFilterTxField;
	
	private String searchFilter = ""; 
	
	private List<Farmer> farmers = null; 
	
	public void initialize() {
		
		TableColumn<Farmer, String> codeCol = new TableColumn<>("Code candidat");
		codeCol.setCellValueFactory(new PropertyValueFactory<>("idCode"));
		codeCol.setPrefWidth(150);
		TableColumn<Farmer, String> lastNameCol = new TableColumn<>("Nom");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		lastNameCol.setPrefWidth(200);
		TableColumn<Farmer, String> firstNameCol = new TableColumn<>("Prenom");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		firstNameCol.setPrefWidth(200);
		TableColumn<Farmer, String> birthDateCol = new TableColumn<>("Date de naissance");
		birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDateISO"));
		birthDateCol.setPrefWidth(150);
		TableColumn<Farmer, String> genderCol = new TableColumn<>("Sexe");
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		genderCol.setPrefWidth(100);
		TableColumn<Farmer, String> emailCol = new TableColumn<>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		TableColumn<Farmer, String> telCol = new TableColumn<>("Telephone");
		telCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
		
		candidateTView.getColumns().addAll(codeCol , lastNameCol , firstNameCol , genderCol, birthDateCol , emailCol , telCol);
		
		ContextMenu cm = new ContextMenu();
		MenuItem m1 = new MenuItem("Details candidat");
		m1.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Farmer f = candidateTView.getSelectionModel().getSelectedItem();
				if(f != null)
					onCandidateDetails(f);
			}
			
		});
	
		MenuItem m2 = new MenuItem("Modifier");
		m2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				Farmer f = candidateTView.getSelectionModel().getSelectedItem();
				if(f != null)
					onEditCandidate(f);
			}
			
		});
		MenuItem m3 = new MenuItem("Supprimer");
		m3.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Farmer f = candidateTView.getSelectionModel().getSelectedItem();
				if(f != null)
					onDeleteCandidate(f);
			}
		});
		cm.getItems().addAll(m1, m2, new SeparatorMenuItem() ,  m3);
		
		candidateTView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY) {
					cm.show(candidateTView , event.getScreenX() , event.getScreenY());
				}else {
					cm.hide();
				}
			}
		});
	
		updateList();
	}
	
	
	public void updateList() {
		new Thread(new Runnable() {				
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(searchFilter.isEmpty())
							farmers = new Farmer().selectAll(-1);
						else 
							farmers = new Farmer().search(searchFilter);
						
						candidateTView.getItems().clear();
						for(Farmer f : farmers) {
							candidateTView.getItems().add(f);	
						}	
					}
				}).start();
				
	}
	public void onNewCandidate()
	{
		try {
			Parent newCandidate = FXMLLoader.load(getClass().getResource("new_candidate.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(newCandidate));
			stage.setResizable(false);
			stage.showAndWait();
			updateList();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onCandidateDetails(Farmer farmer) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("candidate_details.fxml"));
			Parent candidateDetails = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Details candidat");
			stage.setScene(new Scene(candidateDetails));
			stage.setResizable(false);
			CandidateDetailsController controller = loader.<CandidateDetailsController>getController();
			controller.setFarmer(farmer);
			stage.showAndWait();
			updateList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onSearch() {
		searchFilter = searchFilterTxField.getText().trim();
		updateList();
	}
	
	public void onRefresh() {
		searchFilter = "";
		updateList();
	}
	
	public void onEditCandidate(Farmer farmer) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_candidate.fxml"));
		try {
			Parent editCandidate = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Modifier candidat");
			stage.setScene(new Scene(editCandidate));
			stage.setResizable(false);
			EditCandidateController controller = loader.<EditCandidateController>getController();
			controller.setFarmer(farmer);
			stage.showAndWait();
			updateList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onDeleteCandidate(Farmer f) {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Supprimer un candidat");
		confirm.setHeaderText("Supprimer "+f.getFirstName() + " "+f.getLastName());
		confirm.setContentText("Supprimer le candidat : "+f.getFirstName() + " "+f.getLastName()+" ?");
		Optional<ButtonType> result = confirm.showAndWait();
		if(result.get() == ButtonType.OK) {
			f.delete();
			updateList();
		}
	}
}
