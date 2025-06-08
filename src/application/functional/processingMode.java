package application.functional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class processingMode
{
	public static File schema;
	public static File directory;
	public static File instLoc;
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
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> findTypes() throws FileNotFoundException
	{
		Yaml yaml = new Yaml();
		InputStream input = new FileInputStream(instLoc.toString() + "\\config.yml");
		
		Map<String, Object> data = yaml.load(input);
		
		ArrayList<String> fileTypes;
		if (mediaType)
		{
			fileTypes = (ArrayList<String>) data.get("image_extensions");
		}
		else
		{
			fileTypes = (ArrayList<String>) data.get("video_extensions");
		}
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		ArrayList<String> pairs = new ArrayList<String>();
		
		for (String fileType : fileTypes)
		{
			String[] matches = directory.list((dir, name) -> name.endsWith(fileType));
			String str = "*." + fileType + " (" + matches.length + ")";
			pairs.add(str);
		}
		
		return pairs;
	}
	
	public static boolean testInst()
	{
		if (!instLoc.isDirectory())
		{
			return false;
		}
		return true;
	}
}
