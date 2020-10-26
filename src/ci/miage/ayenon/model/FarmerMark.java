package ci.miage.ayenon.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ci.miage.ayenon.db.DBA;
import ci.miage.ayenon.db.query.InsertQuery;
import ci.miage.ayenon.db.query.SelectQuery;
import ci.miage.ayenon.db.query.UpdateQuery;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class FarmerMark implements CRUDInterface{
	

	private String farmerCode ; 
	private String juryMemberCode ; 
	private String criteriaCode ; 
	private double mark ; 
	
	private Farmer farmer;
	private Criteria criteria; 
	private JuryMember juryMember;
	
	private SimpleStringProperty criteriaNameProperty = new SimpleStringProperty(); 
	private Property<Number> markProperty = new SimpleDoubleProperty();
	
	public FarmerMark() {
		farmerCode = juryMemberCode = criteriaCode = null ; 
		mark = 0.0 ;

		farmer = new Farmer();
		criteria = new Criteria();
		juryMember = new JuryMember();
	}

	public FarmerMark(String farmerCode, String juryMemberCode, String criteriaCode, double mark) {
		this.farmerCode = farmerCode;
		this.juryMemberCode = juryMemberCode;
		this.criteriaCode = criteriaCode;
		this.mark = mark;

		farmer = new Farmer();
		criteria = new Criteria();
		juryMember = new JuryMember();
		
	}

	public String getFarmerCode() {
		return farmerCode;
	}

	public void setFarmerCode(String farmerCode) {
		this.farmerCode = farmerCode;
	}

	public String getJuryMemberCode() {
		return juryMemberCode;
	}

	public void setJuryMemberCode(String juryMemberCode) {
		this.juryMemberCode = juryMemberCode;
	}

	public String getCriteriaCode() {
		return criteriaCode;
	}

	public void setCriteriaCode(String criteriaCode) {
		this.criteriaCode = criteriaCode;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public JuryMember getJuryMember() {
		return juryMember;
	}

	public void setJuryMember(JuryMember juryMember) {
		this.juryMember = juryMember;
	}
	
	public SimpleStringProperty getCriteriaNameProperty() {
		return criteriaNameProperty;
	}

	public Property<Number> getMarkProperty() {
		return markProperty;
	}

	public void loadExtraData() {
		loadExtraData(false);
	}
	
	
	public void loadExtraData(boolean all) {
		if(all) {
			farmer.setIdCode(farmerCode);
			farmer.retrieve();
			juryMember.setIdCode(juryMemberCode);
			juryMember.retrieve();
		}
		criteria.setIdCode(criteriaCode);
		criteria.retrieve();
		criteriaNameProperty.set(criteria.getLabel());
		markProperty.setValue(mark);
	}
	
	@Override
	public void insert() {
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			stat.execute(new InsertQuery("NOTE_CANDIDAT")
					.field("NUM_CAND", farmerCode)
					.field("NUM_JURY", juryMemberCode , false)
					.field("NUM_CRI", criteriaCode , false)
					.field("NOTE_CAND", String.valueOf(mark), false)
					.toString());
			
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			stat.execute(new UpdateQuery("NOTE_CANDIDAT")
					.set("NOTE_CAND", String.valueOf(mark), false)
					.where("NUM_CAND" , farmerCode)
					.where("NUM_JURY", juryMemberCode , false)
					.where("NUM_CRI", criteriaCode , false)
					.toString());
			
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<? extends CRUDInterface> selectAll(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<? extends CRUDInterface> search(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("NOTE_CANDIDAT")
					.where("NUM_CAND", farmerCode)
					.where("NUM_JURY" , juryMemberCode, false)
					.where("NUM_CRI", criteriaCode, false)
					.toString());
			if(rs.next()) {
				mark = Double.valueOf(rs.getString("NOTE_CAND"));
			}else {
				mark = -1;
			}
			
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<FarmerMark> getFarmerMarks(String farmerCode, String juryMemberCode)
	{
		Connection conn = DBA.getInstance().getConnection();
		ArrayList<FarmerMark> marks = new ArrayList<>();
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("NOTE_CANDIDAT")
					.where("NUM_CAND", farmerCode)
					.where("NUM_JURY" , juryMemberCode , false)
					.toString());
			while(rs.next()) {
				FarmerMark fm =new FarmerMark(rs.getString("NUM_CAND"), 
						rs.getString("NUM_JURY") , 
						rs.getString("NUM_CRI") , 
						rs.getDouble("NOTE_CAND")); 
				fm.loadExtraData();
				marks.add(fm);
			}
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return marks;
	}
	
}
