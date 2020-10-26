package ci.miage.ayenon.db;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class DBA {
	
	private static DBA instance; 
	private Connection sqlConnection; 
	
	private DBA()
	{
		try {
			DatabaseConfig config = DatabaseConfig.getInstance();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
				String connString = 
						"jdbc:oracle:thin:@(description=(address_list="+
						"(address=(protocol="+config.getProtocol()+")(port="+config.getPort()+")(host="+config.getHost()+")))"+
						"(connect_data=(SERVICE_NAME="+config.getServiceName()+")))";
					OracleDataSource ods = new OracleDataSource();

					ods.setURL(connString);
					ods.setUser(config.getUser());
					ods.setPassword(config.getPassword());
					sqlConnection = ods.getConnection();
					
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		return sqlConnection;
	}
	
	public static DBA getInstance()
	{
		if(null == instance)
			instance = new DBA();
		return instance; 
	}
	

}
