package ci.miage.ayenon.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ci.miage.ayenon.db.DBA;
import ci.miage.ayenon.db.query.DeleteQuery;
import ci.miage.ayenon.db.query.InsertQuery;
import ci.miage.ayenon.db.query.SelectQuery;

public class Criteria implements CRUDInterface{

	private String idCode;
	private String label ; 
	
	public Criteria() {
		idCode = "";
		label = "";
	}

	public Criteria(String idCode, String label) {
		super();
		this.idCode = idCode;
		this.label = label;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public static String generateCode() {
		
		Connection conn = DBA.getInstance().getConnection();
		String code = null;
		try {
			Statement stat = conn.createStatement();
			ResultSet rs ; 
			do {
			code = String.valueOf(Math.floor(Math.random() * 100 + 10));
			rs = stat.executeQuery(new SelectQuery("CRITERE_CANDIDAT")
					.where("NUM_CRI", code , false)
					.toString());
			}while(rs.next());
			
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			stat.execute(new InsertQuery("CRITERE_CANDIDAT")
					.field("NUM_CRI", idCode, false)
					.field("LIBELLE_CRI", label).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Criteria> selectAll(int limit) {
		
		ArrayList<Criteria> list = new ArrayList<>();
		
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("CRITERE_CANDIDAT").toString());
			while(rs.next()) {
				list.add(new Criteria(rs.getString("NUM_CRI"), rs.getString("LIBELLE_CRI")));
			}
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
		Connection conn = DBA.getInstance().getConnection();
		try {
			Statement stat = conn.createStatement();
			stat.executeQuery(new DeleteQuery("CRITERE_CANDIDAT")
					.where("NUM_CRI", idCode, false)
					.toString());
			
			stat.executeQuery(
					new DeleteQuery("NOTE_CANDIDAT")
					.where("NUM_CRI" , idCode, false).toString());
			stat.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<? extends CRUDInterface> search(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve() {
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("CRITERE_CANDIDAT")
					.where("NUM_CRI", idCode )
					.toString());
			if(rs.next()) {
				this.label = rs.getString("LIBELLE_CRI");
			}else {
				this.label = null;
			}
			
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String toString() {
		return "<No. " +this.idCode + ">  " + this.label;
	}
	
	
}
