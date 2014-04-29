package biobook.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnection  {
	
	private static SimpleConnection simpleConnection=null;
	
	private SimpleConnection()
	{
		String nomDriver="com.mysql.jdbc.Driver";
		try
		{
			Class.forName(nomDriver);
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println("Probl�me driver :"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static synchronized SimpleConnection getInstance()
	{
		if(simpleConnection==null)
		{
			simpleConnection=new SimpleConnection();
		}
		return simpleConnection;
	}
	
	public Connection getConnection() 
	{
		Connection co=null;
		
                String url="jdbc:mysql://localhost:3306/biobook";
		String login="root";
		String pwd="admin";
		
		try
		{
			co=DriverManager.getConnection(url ,login ,pwd );
			co.setAutoCommit(false);
		}
		catch (SQLException sqle )
		{
			System.out.println("Probl�me de connexion � la base :"+sqle.getMessage());
			sqle.printStackTrace();
		}
	   
	    return co;
		
	}
	
	public static void main(String[] args)
	{
		Connection co1=null;
	
			co1=SimpleConnection.getInstance().getConnection();
		
		System.out.println("--->Simple Connection: "+co1.toString());
	}

}
