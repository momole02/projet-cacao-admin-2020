package ci.miage.ayenon.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ci.miage.ayenon.db.DBA;
import ci.miage.ayenon.db.query.DeleteQuery;
import ci.miage.ayenon.db.query.InsertQuery;
import ci.miage.ayenon.db.query.SelectQuery;
import ci.miage.ayenon.db.query.UpdateQuery;
import ci.miage.ayenon.db.query.WhereClause;
import ci.miage.ayenon.utils.DateUtils;
import ci.miage.ayenon.utils.Utils;

public class Farmer implements CRUDInterface{
	
	private String idCode;
	private String lastName ; 
	private String firstName ; 
	private String birthDateISO;
	private LocalDate birthDate;
	private String birthPlace;
	private String gender;
	private String birthActPath ; 
	private String facePath;
	private String phone ; 
	private String seedingCode;
	private String email ; 
	private String password ;  
	private String productType;
	
	
	public Farmer(){
		this.idCode = "";
		this.lastName = "";
		this.firstName = "";
		this.birthDateISO = "";
		this.gender = "";
		this.birthActPath = "";
		this.facePath = "";
		this.seedingCode = "";
		this.email = "";
		this.password = "";
	}


	
	public Farmer(String idCode, String lastName, String firstName, String birthDateISO, LocalDate birthDate,
			String birthPlace, String gender, String birthActPath, String facePath, String phone, String seedingCode,
			String email, String password, String productType) {
		super();
		this.idCode = idCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDateISO = birthDateISO;
		this.birthDate = birthDate;
		this.birthPlace = birthPlace;
		this.gender = gender;
		this.birthActPath = birthActPath;
		this.facePath = facePath;
		this.phone = phone;
		this.seedingCode = seedingCode;
		this.email = email;
		this.password = password;
		this.productType = productType;
	}



	public String getIdCode() {
		return idCode;
	}


	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getBirthDateISO() {
		return birthDateISO;
	}


	public void setBirthDateISO(String birthDateISO) {
		this.birthDateISO = birthDateISO;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getBirthActPath() {
		return birthActPath;
	}


	public void setBirthActPath(String birthActPath) {
		this.birthActPath = birthActPath;
	}


	public String getFacePath() {
		return facePath;
	}


	public void setFacePath(String facePath) {
		this.facePath = facePath;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getSeedingCode() {
		return seedingCode;
	}


	public void setSeedingCode(String seedingCode) {
		this.seedingCode = seedingCode;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}


	public String getBirthPlace() {
		return birthPlace;
	}


	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	
	public static String generateCode(Farmer f)
	{
		Connection conn = DBA.getInstance().getConnection();
		Statement statement;
		String code = "";
		try {
			statement = conn.createStatement();
			ResultSet rs ; 
			do {
				code = "CD-"
						+Utils.getInitials(f.getFirstName(), f.getLastName())
						+"-"+(int)Math.floor(100 + Math.random() * 900);
				rs = statement.executeQuery(new SelectQuery("CANDIDAT" , 1)
						.where("NUM_CAND" , code ).toString());
			}while(rs.next());
			
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.print(e.getMessage());
		} 
		return code;
	}


	@Override
	public void insert() {
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		try {
			
			Statement stat = conn.createStatement();
			stat.execute(new InsertQuery("CANDIDAT")
					.field("NUM_CAND", idCode)
					.field("NUM_PL", seedingCode)
					.field("PHOTO_CAND", facePath)
					.field("EXTRAIT_CAND", birthActPath)
					.field("NOM_CAND", lastName)
					.field("PRENOM_CAND", firstName)
					.field("SEXE_CAND", gender)
					.field("DATENAISSANCE_CAND", "TO_DATE( '"+DateUtils.toISODate(birthDate)+"','YYYY-MM-DD' )", false)
					.field("LIEUDN_CAND", birthPlace)
					.field("CONT_CAND", phone)
					.field("EMAIL_CAND", email)
					.field("MDP_CAND", password)
					.field("NUM_PROD", productType)
					.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.print(e);
		}
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		Connection conn = DBA.getInstance().getConnection();
		try {
			Statement stat = conn.createStatement();
			stat.execute(new UpdateQuery("CANDIDAT")
					.set("NUM_PL", seedingCode)
					.set("PHOTO_CAND", facePath)
					.set("EXTRAIT_CAND", birthActPath)
					.set("NOM_CAND", lastName)
					.set("PRENOM_CAND", firstName)
					.set("SEXE_CAND", gender)
					.set("DATENAISSANCE_CAND", "TO_DATE( '"+DateUtils.toISODate(birthDate)+"','YYYY-MM-DD' )", false)
					.set("LIEUDN_CAND", birthPlace)
					.set("CONT_CAND", phone)
					.set("EMAIL_CAND", email)
					.set("MDP_CAND", password)
					.set("NUM_PROD", productType)
					.where("NUM_CAND", idCode ).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public List<Farmer> selectAll(int limit) {
		
		ArrayList<Farmer> list = new ArrayList<>();
		
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(
					new SelectQuery("CANDIDAT" , limit).toString());

			while(rs.next()) {
				list.add(new Farmer(
						rs.getString("NUM_CAND"),
						rs.getString("NOM_CAND"), 
						rs.getString("PRENOM_CAND"), 
						rs.getString("DATENAISSANCE_CAND"), 
						null, 
						rs.getString("LIEUDN_CAND"), 
						rs.getString("SEXE_CAND"), 
						rs.getString("EXTRAIT_CAND"),
						rs.getString("PHOTO_CAND"), 
						rs.getString("CONT_CAND"), 
						rs.getString("NUM_PL"), 
						rs.getString("EMAIL_CAND"), 
						rs.getString("MDP_CAND") , 
						rs.getString("NUM_PROD")
					));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return list;
	}


	@Override
	public void delete() {
		Connection conn = DBA.getInstance().getConnection();
		try {
			Statement stat = conn.createStatement();
			stat.execute(new DeleteQuery("CANDIDAT")
					.where("NUM_CAND", idCode ).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public List<Farmer> search(String filter) {
		// TODO Auto-generated method stub
		ArrayList<Farmer> list = new ArrayList<Farmer>();
		
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("CANDIDAT")
					.where("NUM_CAND", filter)
					.orWhere(new WhereClause("LOWER(NOM_CAND)" , " LIKE " , "%"+filter.toLowerCase()+"%"))
					.orWhere(new WhereClause("LOWER(PRENOM_CAND)" , " LIKE " , "%"+filter.toLowerCase()+"%"))
					.toString());

			while(rs.next()) {
				list.add(new Farmer(
						rs.getString("NUM_CAND"),
						rs.getString("NOM_CAND"), 
						rs.getString("PRENOM_CAND"), 
						rs.getString("DATENAISSANCE_CAND"), 
						null, 
						rs.getString("LIEUDN_CAND"), 
						rs.getString("SEXE_CAND"), 
						rs.getString("EXTRAIT_CAND"),
						rs.getString("PHOTO_CAND"), 
						rs.getString("CONT_CAND"), 
						rs.getString("NUM_PL"), 
						rs.getString("EMAIL_CAND"), 
						rs.getString("MDP_CAND") , 
						rs.getString("NUM_PROD")
					));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}



	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
			Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("CANDIDAT")
					.where("NUM_CAND", idCode)
					.toString());
			if(rs.next()) {
				idCode = rs.getString("NUM_CAND");
				lastName = rs.getString("NOM_CAND");
				firstName = rs.getString("PRENOM_CAND");
				birthDateISO = rs.getString("DATENAISSANCE_CAND");
				birthPlace = rs.getString("LIEUDN_CAND");
				gender = rs.getString("SEXE_CAND");
				birthActPath = rs.getString("EXTRAIT_CAND");
				facePath = rs.getString("PHOTO_CAND");
				phone = rs.getString("CONT_CAND"); 
				seedingCode = rs.getString("NUM_PL");
				email = rs.getString("EMAIL_CAND");
				password = rs.getString("MDP_CAND"); 
				productType = rs.getString("NUM_PROD");
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "<No. " + idCode+ "> " + firstName +" "+lastName;
	}
	
	
}
