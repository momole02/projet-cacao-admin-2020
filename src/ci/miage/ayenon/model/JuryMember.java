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
import ci.miage.ayenon.db.query.UpdateQuery;

public class JuryMember implements CRUDInterface{

	private String idCode ; 
	private String firstName ; 
	private String lastName ; 
	private String locality;
	private String birthDateISO; 
	
	public JuryMember() {
		this.idCode = "";
		this.firstName = "";
		this.lastName = "";
		this.locality = "";
		this.birthDateISO = "";
	}

	public JuryMember(String idCode, String firstName, String lastName, String locality, String birthDateISO) {
		super();
		this.idCode = idCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.locality = locality;
		this.birthDateISO = birthDateISO;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getBirthDateISO() {
		return birthDateISO;
	}

	public void setBirthDateISO(String birthDateISO) {
		this.birthDateISO = birthDateISO;
	}
	
	public static String generateCode() {
		Connection conn = DBA.getInstance().getConnection();
		String code = null; 
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs ; 
			do {
				
				code = String.valueOf(Math.floor(100 + Math.random() * 900));
				rs = stat.executeQuery(new SelectQuery("MEMBRE_JURY")
						.where("NUM_JURY", code, false)
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
			stat.execute(new InsertQuery("MEMBRE_JURY")
					.field("NUM_JURY", idCode , false)
					.field("NOM_JURY", lastName)
					.field("PRENOM_JURY", firstName)
					.field("LOCALITE_JURY", locality)
					.field("DATE_NAISS_JURY", "TO_DATE( '"+birthDateISO+"','YYYY-MM-DD' )", false)
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
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			stat.execute(new UpdateQuery("MEMBRE_JURY")
					.set("NOM_JURY", lastName)
					.set("PRENOM_JURY", firstName)
					.set("LOCALITE_JURY", locality)
					.set("DATE_NAISS_JURY", "TO_DATE( '"+birthDateISO+"','YYYY-MM-DD' )", false)
					.where("NUM_JURY", idCode , false)
					.toString());
			
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<JuryMember> selectAll(int limit) {
		// TODO Auto-generated method stub
		

		ArrayList<JuryMember> members = new ArrayList<>();
		Connection conn = DBA.getInstance().getConnection();
		
		try {
		
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("MEMBRE_JURY")
					.toString());
			while(rs.next()) {
				members.add(
						new JuryMember(rs.getString("NUM_JURY"), rs.getString("NOM_JURY"), 
						rs.getString("PRENOM_JURY"), rs.getString("LOCALITE_JURY"), 
						rs.getString("DATE_NAISS_JURY")));
			}
			
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
		return members;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			stat.execute(new DeleteQuery("MEMBRE_JURY")
					.where("NUM_JURY", idCode, false)
					.toString());
			
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			ResultSet rs = stat.executeQuery(new SelectQuery("MEMBRE_JURY")
					.where("NUM_JURY", idCode, false)
					.toString());
			if(rs.next()) {
				firstName = rs.getString("PRENOM_JURY");
				lastName = rs.getString("NOM_JURY");
				birthDateISO = rs.getString("DATE_NAISS_JURY");
			}else {
				birthDateISO = firstName = lastName = null ; 
				
			}
			rs.close();
			stat.close();
		}catch(SQLException e) {
			System.err.println(e);
		}
	}
	
	
}
