package edu.trackme.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zehcnas
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
        
	private String             driverName,
				   url,
				   database,
				   username,
				   password;
	private Connection con;
        
	public  DBConnection()
	{
		driverName = "com.mysql.jdbc.Driver";
		url        = "jdbc:mysql://localhost:3306/";
		database   = "trackme";
		username   = "root";
		password   = "root";
	}
	
	public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(getUrl() + getDatabase(), getUsername(), getPassword());
			return con;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		return null;
	}

	public String getDriverName()
	{
		return driverName;
	}

	public void setDriverName(String driverName) 
	{
		this.driverName = driverName;
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getDatabase() 
	{
		return database;
	}

	public void setDatabase(String database)
	{
		this.database = database;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public String getPassword()
{
		return password;
	}

	public void setPassowrd(String password) 
	{
		this.password = password;
	}

	
	public void closeCon(){
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
        

        
        
  

   
