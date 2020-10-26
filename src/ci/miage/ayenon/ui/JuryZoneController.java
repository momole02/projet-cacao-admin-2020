package ci.miage.ayenon.ui;

import java.util.List;
import java.util.Optional;

import ci.miage.ayenon.model.Criteria;
import ci.miage.ayenon.model.Farmer;
import ci.miage.ayenon.model.FarmerMark;
import ci.miage.ayenon.model.JuryMember;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class JuryZoneController {
	
	private JuryMember juryMember;
	private Farmer lastSelected;

	
	@FXML TableView<FarmerMark> markTView; 
	@FXML TextField searchTxField;
	@FXML Label welcomeLabel; 
	@FXML ListView<Farmer> farmerLView;
	@FXML HBox setupPendingVBox; 
	@FXML Button searchButton; 
	
	public void initialize() {
		
		markTView.setEditable(true);
		TableColumn<FarmerMark, String> criteriaNoColumn = new TableColumn<>("N. critère");
		criteriaNoColumn.setCellValueFactory(new Callback<CellDataFeatures<FarmerMark,String>, ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<FarmerMark, String> p) {
				// TODO Auto-generated method stub
				return new SimpleStringProperty("<" + "No. " + p.getValue().getCriteriaCode() + ">");
			}
			
		});
		criteriaNoColumn.setPrefWidth(150);
		criteriaNoColumn.setEditable(false);
		
		TableColumn<FarmerMark , String> criteriaColumn = new TableColumn<>("Critère d'évaluation"); 
		criteriaColumn.setCellValueFactory(new Callback<CellDataFeatures<FarmerMark,String>, ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<FarmerMark, String> p) {
				// TODO Auto-generated method stub
				return p.getValue().getCriteriaNameProperty();
			}
			
		});

		criteriaColumn.setPrefWidth(300);
		TableColumn<FarmerMark, Number> markColumn = new TableColumn<>("Note");
		markColumn.setCellValueFactory(new Callback<CellDataFeatures<FarmerMark,Number>, ObservableValue<Number>>(){
			@Override
			public ObservableValue<Number> call(CellDataFeatures<FarmerMark, Number> p) {
				// TODO Auto-generated method stub
				return p.getValue().getMarkProperty();
			}

		});
		markColumn.setPrefWidth(100);
		markTView.getColumns().addAll(criteriaNoColumn, criteriaColumn , markColumn);
		markTView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				FarmerMark mk = markTView.getSelectionModel().getSelectedItem();
				if(mk != null) {
					mk.loadExtraData(true);
					if(event.getClickCount() == 2) {
						TextInputDialog dialog = new TextInputDialog(String.valueOf(mk.getMark()));
						dialog.setTitle("Attribution de note");
						dialog.setHeaderText("Attribuer note à : "+mk.getFarmer().getFirstName() + " "+mk.getFarmer().getLastName());
						dialog.setContentText("Note du critère "+mk.getCriteria().getLabel());
						Optional<String> result = dialog.showAndWait();
						if(result.isPresent()) {
							try {
								double mark = Double.valueOf(result.get());
								mk.setMark(mark);
								mk.update();
								reloadMarks();
							}catch(NumberFormatException e) {
								Alert al = new Alert(AlertType.ERROR , "Format de la note incorrecte");
								al.setTitle("Erreur");
								al.setHeaderText(null);
								al.show();
							}
						}
					}
				}
			}
		});
		farmerLView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Farmer>() {

			@Override
			public void changed(ObservableValue<? extends Farmer> observable, Farmer oldValue, Farmer newValue) {
				// TODO Auto-generated method stub
				if(null != newValue)
					onListItemSelect(newValue);
				
			}
			
		});
		setupPendingVBox.setVisible(false); // Hide the pending message
		setupMarks();
	}
	
	
	private void setupMarks() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				

				setupPendingVBox.setVisible(true);
				//Disable sensible controls
				searchTxField.setDisable(true);
				searchButton.setDisable(true);
				int nbEntries = 0;
				
				//Setup marks for candidates and jury
				List<Farmer> candidates = new Farmer().selectAll(-1);
				List<JuryMember> juryMembers = new JuryMember().selectAll(-1);
				List<Criteria> criteria = new Criteria().selectAll(-1);

				int i,j,k;
				for(i=0; i < candidates.size() ; ++i) {
					for(j=0; j < juryMembers.size() ; ++j) {
						for(k=0; k < criteria.size() ; ++k) {
							Farmer farmer = candidates.get(i);
							JuryMember jury = juryMembers.get(j);
							Criteria cri = criteria.get(k);
							FarmerMark mark = new FarmerMark(farmer.getIdCode(), jury.getIdCode(), cri.getIdCode(), 0);
							mark.retrieve();
							if(mark.getMark() == -1) {
								mark.setMark(0);
								mark.insert();
								++nbEntries;
							}
						}
					}
				}
				setupPendingVBox.setVisible(false);
				//Re-enable sensible controls
				searchTxField.setDisable(false);
				searchButton.setDisable(false);
				
				if(nbEntries > 0) {
					System.out.println(nbEntries + " entrée(s) de notes crée(s)");
				}
			}
			
		}).start();;
	}
	
	public void setJuryMember(JuryMember jury) {
		this.juryMember = jury;
		
		String str = welcomeLabel.getText();
		welcomeLabel.setText(str + " " + jury.getFirstName() + " "+jury.getLastName());
	}
	
	public void onSearch() {
		markTView.getItems().clear();
		farmerLView.getItems().clear();
		String filter = searchTxField.getText();
		List<Farmer> candidates = new Farmer().search(filter);
		for(Farmer cdt : candidates)
			farmerLView.getItems().add(cdt);
	}
	
	public void onListItemSelect(Farmer selected) {
		lastSelected = selected;
		markTView.getItems().clear();
		List<FarmerMark> marks = FarmerMark.getFarmerMarks(selected.getIdCode(), juryMember.getIdCode());
		for(FarmerMark fm : marks) {
			markTView.getItems().add(fm);
		}
	}
	
	private void reloadMarks() {

		markTView.getItems().clear();
		List<FarmerMark> marks = FarmerMark.getFarmerMarks(lastSelected.getIdCode(), juryMember.getIdCode());
		for(FarmerMark fm : marks) {
			markTView.getItems().add(fm);
		}
	}
	
	

}
