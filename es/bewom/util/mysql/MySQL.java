package es.bewom.util.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	
	public final String DB = "jdbc:mysql://bewom.es/bewom.es";
	public final String user = "plugin";
	public final String password = "UzjnGApn8bD3TWUV";
	
	public Object executeQuery(String e, String c){
		
		System.out.println("String e: "+e);
		System.out.println("String c: "+c);
		Object o = null;
		
		Connection conexion;
		try {
			conexion = DriverManager.getConnection (DB, user, password);
			Statement s = conexion.createStatement();
			
			ResultSet rs = s.executeQuery(e);
			
			if(c != null){
				
				while (rs.next()) {
					
					o = rs.getObject(c);
					
				}
			}
			
			conexion.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return o;
	}

}
