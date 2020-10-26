package ci.miage.ayenon.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DashboardController {

	@FXML private VBox mainVBox ; 
	@FXML private Label candidatesLabel ; 
	@FXML private Label juryMembersLabel ; 
	@FXML private Label resultsLabel ; 
	
	private VBox candidatesVBox ; 
	private VBox juryVBox; 
	private VBox resultsVBox ; 
	
	public void initialize()
	{
		showCandidatesPanel();
	}
	
	private void loadCandidatesPanel() {
		try {
			Parent candidates = FXMLLoader.load(getClass().getResource("candidates.fxml"));
			candidatesVBox = (VBox)candidates;
			candidatesVBox.setMaxWidth(Double.MAX_VALUE);
			VBox.setVgrow(candidatesVBox, Priority.ALWAYS);
			//mainVBox.getChildren().add(candidatesVBox);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showCandidatesPanel() {
		if(candidatesVBox == null)
			loadCandidatesPanel();
		
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(candidatesVBox);
		if(!candidatesLabel.getStyleClass().contains("menu-item-selected"))
			candidatesLabel.getStyleClass().add("menu-item-selected");
		
		juryMembersLabel.getStyleClass().remove("menu-item-selected");
		resultsLabel.getStyleClass().remove("menu-item-selected");

	}
	
	private void loadJuryPanel() {
		Parent jury;
		try {
			jury = FXMLLoader.load(getClass().getResource("jury_members.fxml"));
			juryVBox = (VBox)jury;
			juryVBox.setMaxWidth(Double.MAX_VALUE);
			VBox.setVgrow(juryVBox, Priority.ALWAYS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showJuryPanel() {
		if(null == juryVBox)
			loadJuryPanel();
		
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(juryVBox);
		if(!juryMembersLabel.getStyleClass().contains("menu-item-selected"))
			juryMembersLabel.getStyleClass().add("menu-item-selected");
		
		candidatesLabel.getStyleClass().remove("menu-item-selected");
		resultsLabel.getStyleClass().remove("menu-item-selected");
	}
	
	private void loadResultsPanel() {
		Parent results;
		try {
			results = FXMLLoader.load(getClass().getResource("results.fxml"));
			resultsVBox = (VBox)results;
			resultsVBox.setMaxWidth(Double.MAX_VALUE);
			VBox.setVgrow(resultsVBox, Priority.ALWAYS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showResultsPanel() {
		//if(null == resultsVBox)
		loadResultsPanel();
		
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(resultsVBox);
		if(!resultsLabel.getStyleClass().contains("menu-item-selected"))
			resultsLabel.getStyleClass().add("menu-item-selected");
		
		candidatesLabel.getStyleClass().remove("menu-item-selected");
		juryMembersLabel.getStyleClass().remove("menu-item-selected");
	}
	
}
