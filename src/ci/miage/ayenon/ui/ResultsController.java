package ci.miage.ayenon.ui;

import java.util.Comparator;
import java.util.List;

import ci.miage.ayenon.model.Criteria;
import ci.miage.ayenon.model.Farmer;
import ci.miage.ayenon.model.FarmerResult;
import ci.miage.ayenon.model.JuryMember;
import ci.miage.ayenon.model.JuryResult;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ResultsController {

	@FXML TableView<FarmerResult> coffeeTView;
	@FXML TableView<FarmerResult> cocoaTView;
	@FXML TableView<JuryResult> juryMarksTView;
	@FXML HBox loadingIndicatorHBox ; 
	
	public void initialize() {
		loadingIndicatorHBox.setVisible(false);
		configureResultTView(cocoaTView);
		configureResultTView(coffeeTView);
		configureJuryMarksTView();
		cocoaTView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				FarmerResult selected = cocoaTView.getSelectionModel().getSelectedItem();
				if(null != selected)
					onResultChoose(selected);
			}
		});
		
		coffeeTView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				FarmerResult selected = coffeeTView.getSelectionModel().getSelectedItem();
				if(null != selected)
					onResultChoose(selected);
			}
		});
		updateList();
	}
	
	private void configureResultTView(TableView<FarmerResult> tview) {
				
		TableColumn<FarmerResult, String> farmerNameColumn = new TableColumn<>("Candidat");
		farmerNameColumn.setPrefWidth(250);
		farmerNameColumn.setCellValueFactory(new Callback<CellDataFeatures<FarmerResult, String>, ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<FarmerResult, String> param) {
				// TODO Auto-generated method stub
				String name = param.getValue().getFarmer().getFirstName() 
						+ " " + param.getValue().getFarmer().getLastName();
				return new SimpleStringProperty(name);
			}
			
		});
		tview.getColumns().add(farmerNameColumn);
		
		List<Criteria> criteriaList = new Criteria().selectAll(-1);
		//ArrayList<TableColumn<FarmerResult, Number>> criteriaColumns = new ArrayList<>();
		for(Criteria cri : criteriaList) {
			TableColumn<FarmerResult, Number> criteriaCol = new TableColumn<>("Moy. "+cri.getLabel());
			criteriaCol.setPrefWidth(120);
			criteriaCol.setCellValueFactory(new Callback<CellDataFeatures<FarmerResult, Number>,ObservableValue<Number>>(){
				@Override
				public ObservableValue<Number> call(CellDataFeatures<FarmerResult, Number> param) {
					// TODO Auto-generated method stub
					return param.getValue()
							.getMarksAvgPerCriteria()
							.get(cri.getIdCode());
				}
			});
			tview.getColumns().add(criteriaCol);
		}
		
		TableColumn<FarmerResult, Number> avgSumColumn= new TableColumn<>("Total");
		avgSumColumn.setPrefWidth(200);
		avgSumColumn.setCellValueFactory(new Callback<CellDataFeatures<FarmerResult,Number>,ObservableValue<Number>>(){

			@Override
			public ObservableValue<Number> call(CellDataFeatures<FarmerResult, Number> param) {
				// TODO Auto-generated method stub
				return param.getValue().getMarksAvgSum();
			}
			
		});
		tview.getColumns().add(avgSumColumn);
		
	}
	
	private void configureJuryMarksTView() {
		TableColumn<JuryResult, String> memberNameCol = new TableColumn<>("Membre du jury");
		memberNameCol.setCellValueFactory(new Callback<CellDataFeatures<JuryResult, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(CellDataFeatures<JuryResult, String> param) {
				// TODO Auto-generated method stub
				String name = param.getValue().getMember().getFirstName() 
						+ " " + param.getValue().getMember().getLastName();
				return new SimpleStringProperty(name);
			}
			
		});
		memberNameCol.setPrefWidth(200);
		juryMarksTView.getColumns().add(memberNameCol);
		
		
		List<Criteria> criteriaList = new Criteria().selectAll(-1);
		//ArrayList<TableColumn<FarmerResult, Number>> criteriaColumns = new ArrayList<>();
		for(Criteria cri : criteriaList) {
			TableColumn<JuryResult, Number> criteriaCol = new TableColumn<>("Note. "+cri.getLabel());
			criteriaCol.setPrefWidth(120);
			criteriaCol.setCellValueFactory(new Callback<CellDataFeatures<JuryResult, Number>,ObservableValue<Number>>(){
				@Override
				public ObservableValue<Number> call(CellDataFeatures<JuryResult, Number> param) {
					// TODO Auto-generated method stub
					return new SimpleDoubleProperty(
							param.getValue().getMarks()
							.get(cri.getIdCode()));
				}
			});
			juryMarksTView.getColumns().add(criteriaCol);
		}
	}
	private void updateList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				
				loadingIndicatorHBox.setVisible(true);
				// TODO Auto-generated method stub
				List<Farmer> allFarmers = new Farmer().selectAll(-1);
				cocoaTView.getItems().clear();
				coffeeTView.getItems().clear();
				for(Farmer f : allFarmers) {
					FarmerResult result = new FarmerResult();
					result.loadFarmerResult(f);
					//System.out.println(f.getProductType().toLowerCase());
					if(f.getProductType().toLowerCase().trim().contains("cafe")) {
						coffeeTView.getItems().add(result);
					}else {
						cocoaTView.getItems().add(result);
					}
				}
				coffeeTView.getItems().sort(new Comparator<FarmerResult>() {
					@Override
					public int compare(FarmerResult arg0, FarmerResult arg1) {
						// TODO Auto-generated method stub
						return 1000 * 
								(int)Math.floor(arg1.getMarksAvgSum().getValue().doubleValue() - 
										arg0.getMarksAvgSum().getValue().doubleValue());
					}
				});
				cocoaTView.getItems().sort(new Comparator<FarmerResult>() {
					@Override
					public int compare(FarmerResult arg0, FarmerResult arg1) {
						// TODO Auto-generated method stub
						return 1000 * 
								(int)Math.floor(arg1.getMarksAvgSum().getValue().doubleValue() - 
										arg0.getMarksAvgSum().getValue().doubleValue());
					}
				});
				
				loadingIndicatorHBox.setVisible(false);
			}
			
		}).start();;
	}
	
	public void onResultChoose(FarmerResult result) {
		juryMarksTView.getItems().clear();
		Farmer farmer = result.getFarmer();
		List<JuryMember> members = new JuryMember().selectAll(-1);
		for(JuryMember jm : members) {
			JuryResult juryResult = new JuryResult();
			juryResult.loadJuryResults(farmer, jm);
			juryMarksTView.getItems().add(juryResult);
		}
	}
	
}
