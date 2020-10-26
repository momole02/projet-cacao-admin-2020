package ci.miage.ayenon.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import ci.miage.ayenon.db.DBA;
import ci.miage.ayenon.db.query.SelectQuery;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;

public class FarmerResult {

	private Farmer farmer;
	private HashMap<String, Property<Number>> marksAvgPerCriteria; 
	private Property<Number> marksAvgSum;
	
	public FarmerResult(){
		farmer = new Farmer();
		marksAvgPerCriteria = new HashMap<>();
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public HashMap<String, Property<Number>> getMarksAvgPerCriteria() {
		return marksAvgPerCriteria;
	}

	public Property<Number> getMarksAvgSum() {
		return marksAvgSum;
	}

	public void loadFarmerResult(Farmer f) {
		this.farmer = f;
		
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("NOTE_CANDIDAT")
					.where("NUM_CAND", f.getIdCode())
					.toString());
			while(rs.next()) { //for all jury members, all criteria
				String criteriaCode = rs.getString("NUM_CRI");
				//Do the sum in place ! 
				double mark = rs.getDouble("NOTE_CAND");
				if(!marksAvgPerCriteria.containsKey(criteriaCode)) {
					marksAvgPerCriteria.put(criteriaCode, new SimpleDoubleProperty(mark));
				}else {
					Double old = marksAvgPerCriteria.get(criteriaCode).getValue().doubleValue();
					marksAvgPerCriteria.get(criteriaCode).setValue(old.doubleValue() + mark);
				}
			}
			
			//Compute the AVG and the AVG sum 
			double avgSum  = 0; 
			for(String criName : marksAvgPerCriteria.keySet()) {
				double sum = marksAvgPerCriteria.get(criName).getValue().doubleValue();
				int nbJury = new JuryMember().selectAll(-1).size();
				double avg = sum / nbJury;
				marksAvgPerCriteria.put(criName , new SimpleDoubleProperty(avg));
				avgSum += avg; 
			}
			marksAvgSum = new SimpleDoubleProperty(avgSum);
	

			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
}
