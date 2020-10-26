package ci.miage.ayenon.ui;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ci.miage.ayenon.model.JuryMember;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class JuryMembersController {
	
	@FXML TableView<JuryMember> juryMembersTView;
	
	List<JuryMember> members;
	
	public void initialize() {
		
		TableColumn<JuryMember, String> codeCol= new TableColumn<>("Code jury");
		codeCol.setPrefWidth(150);
		codeCol.setCellValueFactory(new PropertyValueFactory<>("idCode"));
		TableColumn<JuryMember, String> lastNameCol = new TableColumn<>("Nom");
		lastNameCol.setPrefWidth(200);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		TableColumn<JuryMember, String> firstNameCol = new TableColumn<>("Prenom");
		firstNameCol.setPrefWidth(200);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		TableColumn<JuryMember, String> birthDateCol= new TableColumn<>("Date de naissance");
		birthDateCol.setPrefWidth(150);
		birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDateISO"));
		TableColumn<JuryMember, String> localityCol= new TableColumn<>("Localité");
		localityCol.setPrefWidth(150);
		localityCol.setCellValueFactory(new PropertyValueFactory<>("locality"));
		juryMembersTView.getColumns().addAll(codeCol, lastNameCol, firstNameCol, birthDateCol, localityCol);
		
		ContextMenu cm = new ContextMenu();
		MenuItem m1 = new MenuItem("Modifier");
		m1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				JuryMember member = juryMembersTView.getSelectionModel().getSelectedItem();
				if(null != member)
					onEditJuryMember(member);
			}
		});
		MenuItem m2 = new MenuItem("Supprimer");
		m2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				JuryMember member = juryMembersTView.getSelectionModel().getSelectedItem();
				if(null != member)
					onDeleteJuryMember(member);
			}
		});
		cm.getItems().addAll(m1, new SeparatorMenuItem() , m2);
		juryMembersTView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY) {
					cm.show(juryMembersTView, event.getScreenX() , event.getSceneY());
				}else {
					cm.hide();
				}
				// TODO Auto-generated method stub
				
			}
		});
		updateList();
	}
	
	private void updateList() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				 members = new JuryMember().selectAll(-1);
				 juryMembersTView.getItems().clear();
				 for(JuryMember member : members) {
					 juryMembersTView.getItems().add(member);
				 }
			}
		}).start();
	}
	
	public void onNewJuryMember() {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("jury_form.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.setResizable(false);
			stage.setTitle("Ajouter un membre du jury");
			stage.showAndWait();
			updateList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onEditJuryMember(JuryMember member) {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("jury_form.fxml"));
		try {
			Parent parent = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.setResizable(false);
			stage.setTitle("Modifier un membre du jury");
			JuryFormController controller = loader.<JuryFormController>getController();
			controller.setJuryMember(member);
			stage.showAndWait();
			updateList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void onDeleteJuryMember(JuryMember member) {

		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Supprimer un membre du jury");
		confirm.setHeaderText("Supprimer "+member.getFirstName() + " "+member.getLastName());
		confirm.setContentText("Supprimer le membre du jury: "+member.getFirstName() + " "+member.getLastName()+" ?");
		Optional<ButtonType> result = confirm.showAndWait();
		if(result.get() == ButtonType.OK) {
			member.delete();
			updateList();
		}
	}
	
	public void onRefresh() {
		updateList();
	}
	
	public void onManageEvalCriteria() {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("note_criteria.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(parent));
			stage.setResizable(false);
			stage.setTitle("Gestion des critêres d'évaluation");
			stage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
