package application.functional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.yaml.snakeyaml.Yaml;

public class processingMode
{
	public static File schema = new File(AppData.getSetting("schemaPath"));
	public static File directory;
	public static File instLoc = new File(AppData.getSetting("instLoc"));
	public static boolean mediaType; //true = images, false = videos.
	public static String port = AppData.getSetting("port");
	public static String apiKey = AppData.getSetting("apiKey");
	
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
	
	public static void runScan()
	{
		try
		{
			URI uri = URI.create("http://localhost:" + port + "/graphql");
			URL url = uri.toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("ApiKey", apiKey);
            conn.setDoOutput(true);

            String jsonInputString = "{\"query\":\"mutation { metadataScan(input: { rescan: true }) }\"}";;
;
            
            try (OutputStream os = conn.getOutputStream()) 
            {
            	byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            	os.write(input);
            }
            
            int status = conn.getResponseCode();
            System.out.println("GraphQL request sent. HTTP status code: " + status);
            
            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) 
            {
                String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                System.out.println("Response:\n" + response);
            }
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static boolean testCnxn(int timeout)
	{
		try (Socket socket = new Socket())
		{
			socket.connect(new InetSocketAddress("localhost", Integer.valueOf(port)), timeout);
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}
	
	public static boolean testAuth(int timeout)
	{
		try
		{
			URI uri = URI.create("http://localhost:" + port + "/graphql");
			URL url = uri.toURL();

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("ApiKey", apiKey);
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			conn.setDoOutput(true);

			String testQuery = """
				{
					"query": "{ allTags { id } }"
				}
			""";

			try (OutputStream os = conn.getOutputStream())
			{
				byte[] input = testQuery.getBytes(StandardCharsets.UTF_8);
				os.write(input);
			}

			int responseCode = conn.getResponseCode();

			if (responseCode >= 200 && responseCode < 300)
			{
				try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8))
				{
					String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
					return response.contains("\"data\"");
				}
			}
		}
		catch (Exception e)
		{
			// Silent fail
		}

		return false;
	}

}
