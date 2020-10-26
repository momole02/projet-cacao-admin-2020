package ci.miage.ayenon.ui;

import java.util.List;

import ci.miage.ayenon.model.Criteria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CriteriaController {

	@FXML ListView<Criteria> criteriaLView;
	@FXML TextField criteriaAddTxCtrl;
	
	public void initialize() {
		updateList();
	}
	
	private void updateList() {
		
		List<Criteria> criteria = new Criteria().selectAll(-1);
		criteriaLView.getItems().clear();
		for(Criteria cri : criteria) {
			criteriaLView.getItems().add(cri);
		}
			
	}
	
	public void onAddNewCriteria(ActionEvent e) {
		String label = criteriaAddTxCtrl.getText().trim();
		if(label.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR , "Entrez un critère valide");
			alert.setHeaderText("Echec de la validation");
			alert.setTitle("Critère invalide");
			alert.showAndWait();
		}else {

			Criteria cri = new Criteria();
			cri.setLabel(label);
			cri.setIdCode(Criteria.generateCode());
			cri.insert();
			
			Alert alert = new Alert(AlertType.INFORMATION , "Critère d'évaluation enregistré");
			alert.setHeaderText("Succès de l'enregistrement");
			alert.setTitle("Enregistrement reussi");
			alert.showAndWait();
			updateList();
			criteriaAddTxCtrl.setText("");
			
		}
	}
	
	public void onDeleteCriteria() {
		Criteria c = criteriaLView.getSelectionModel().getSelectedItem();
		if(null != c) {
			Alert alert = new Alert(AlertType.CONFIRMATION , "Voulez vous vraiment supprimer le critere "+c.getLabel()+" ?");
			alert.setHeaderText("Suppression du critère "+c.getLabel());
			alert.setTitle("Confirmation de suppression");
			alert.showAndWait();
			c.delete();
			updateList();
		}
	}
	
}
