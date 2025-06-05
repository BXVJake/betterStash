package application.functional;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class processingMode
{
	public static File schema;
	public static File directory;
	public static boolean mediaType; //true = images, false = videos.
	
	public static boolean testSchema()
	{
	    String url = "jdbc:sqlite:" + schema.getAbsolutePath();

	    if (!schema.exists() || !schema.isFile())
	    {
	        return false;
	    }

	    try (Connection conn = DriverManager.getConnection(url))
	    {
	        if (conn == null)
	        {
	            return false;
	        }

	        return true;
	    }
	    catch (SQLException e)
	    {
	        return false;
	    }
	}

	
	public static boolean testDirectory()
	{
		if (!directory.isDirectory())
		{
			return false;
		}
		return true;
	}
}
