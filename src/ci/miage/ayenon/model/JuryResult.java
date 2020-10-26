package ci.miage.ayenon.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ci.miage.ayenon.db.DBA;
import ci.miage.ayenon.db.query.SelectQuery;

public class JuryResult {
	
	private JuryMember member ; 
	private Farmer farmer; 
	private HashMap<String, Double> marks; 
	
	public JuryResult() {
		member = new JuryMember();
		farmer = new Farmer();
		marks = new HashMap<String, Double>();
	}

	public JuryMember getMember() {
		return member;
	}

	public void setMember(JuryMember member) {
		this.member = member;
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}

	public HashMap<String, Double> getMarks() {
		return marks;
	}

	public void setMarks(HashMap<String, Double> marks) {
		this.marks = marks;
	}
	
	public void loadJuryResults(Farmer farmer , JuryMember member ){
		
		this.farmer = farmer; 
		this.member = member; 
		Connection conn = DBA.getInstance().getConnection();
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("NOTE_CANDIDAT")
					.where("NUM_CAND", farmer.getIdCode())
					.where("NUM_JURY" , member.getIdCode()).toString());
			while(rs.next()) {
				String criteriaCode = rs.getString("NUM_CRI");
				marks.put(criteriaCode , rs.getDouble("NOTE_CAND"));
			}
			
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
