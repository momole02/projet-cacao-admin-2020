package ci.miage.ayenon.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.sun.org.apache.xerces.internal.impl.dv.xs.FloatDV;

import ci.miage.ayenon.db.DBA;
import ci.miage.ayenon.db.query.DeleteQuery;
import ci.miage.ayenon.db.query.InsertQuery;
import ci.miage.ayenon.db.query.SelectQuery;
import ci.miage.ayenon.db.query.UpdateQuery;

public final class Seeding implements CRUDInterface{

	private String idCode;
	private String locality ; 
	private float area; 
	
	public Seeding() {
		this.idCode = "";
		this.locality = "";
		this.area = 0;
	}

	public Seeding(String idCode, String locality, float area, String plantTypeCode) {
		this.idCode = idCode;
		this.locality = locality;
		this.area = area;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}


	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public static String generateCode()
	{
		Connection conn = DBA.getInstance().getConnection();
		Statement statement;
		String code = "";
		try {
			
			statement = conn.createStatement();
			ResultSet rs ; 
			do {
				code = "PLAN-"+(int)Math.floor(Math.random() * 1000000);
				rs = statement.executeQuery(new SelectQuery("PLANTATION" , 1)
						.where("NUM_PLAN" , code ).toString());
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
			stat.execute(new InsertQuery("PLANTATION")
					.field("NUM_PLAN", idCode)
					.field("LOCALISATION_PLAN", locality)
					.field("SUPERFICIE_PLAN", String.valueOf(area) , false).toString());

			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.print(e);
		}
		
	}

	@Override
	public void update() {
		Connection conn = DBA.getInstance().getConnection();
		try {
			Statement stat = conn.createStatement();
			stat.execute(new UpdateQuery("PLANTATION")
					.set("LOCALISATION_PLAN", locality)
					.set("SUPERFICIE_PLAN", String.valueOf(area) , false)
					.where("NUM_PLAN" , idCode )
					.toString());
			
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.print(e);
		}
	}

	@Override
	public List<Seeding> selectAll(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			stat.execute(new DeleteQuery("PLANTATION")
					.where("NUM_PLAN", this.idCode)
					.toString());
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
		// TODO Auto-generated method stub
		Connection conn = DBA.getInstance().getConnection();
		
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(new SelectQuery("PLANTATION")
					.where("NUM_PLAN", this.idCode)
					.toString());
			if(rs.next()) {
				this.area = Float.valueOf(rs.getString("SUPERFICIE_PLAN"));
				this.locality = rs.getString("LOCALISATION_PLAN");
			}
			
			rs.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
