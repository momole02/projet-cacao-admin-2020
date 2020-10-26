package ci.miage.ayenon.db;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DatabaseConfig {
	
	private static DatabaseConfig config = new DatabaseConfig();
	
	private String user;
	private String password;
	private String host ; 
	private int port ; 
	private String serviceName ; 
	private String protocol; 
	
	private DatabaseConfig() {
		this.load();
	}
	
	private void load() {
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
					new File("database.conf")));
			
			Scanner scanner = new Scanner(bis);
			while(scanner.hasNextLine()) {
				String buffer = scanner.nextLine().trim();
				if(buffer.isEmpty()) continue ; 
				String data[] = buffer.split("=");
				switch(data[0]) {
					case "USER":
						this.user = data[1]; break ; 
					case "PASSWORD":
						this.password = data[1] ; break ; 
					case "PORT" :
						this.port = Integer.valueOf(data[1]); break ; 
					case "HOST" :
						this.host = data[1]; break ; 
					case "SERVICE_NAME": 
						this.serviceName = data[1];break ; 
					case "PROTOCOL":
						this.protocol = data[1]; break; 
					default : 
						System.err.println("Unknown config line : "+ data[0]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static DatabaseConfig getInstance() {
		return config; 
	}

	public static DatabaseConfig getConfig() {
		return config;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getProtocol() {
		return protocol;
	}
	
	
}
