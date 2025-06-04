package application.functional;

import java.io.*;
import java.util.Properties;

public class AppData
{
	private static final String FILE_NAME = System.getProperty("user.home") + File.separator + ".betterStash" + File.separator + "config.properties";
	private static final Properties props = new Properties();

	static
	{
		try
		{
			File file = new File(FILE_NAME);
			File parent = file.getParentFile();
			if (!parent.exists())
			{
				parent.mkdirs();
			}
			if (file.exists())
			{
				try (FileInputStream in = new FileInputStream(file))
				{
					props.load(in);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void saveSetting(String key, String value)
	{
		props.setProperty(key, value);
		try (FileOutputStream out = new FileOutputStream(FILE_NAME))
		{
			props.store(out, "betterStash Config");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String getSetting(String key)
	{
		return props.getProperty(key, "");
	}
}
