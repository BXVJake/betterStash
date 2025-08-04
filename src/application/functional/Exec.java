package application.functional;

import java.io.File;
import java.util.ArrayList;

public class Exec extends processingMode
{
	public static ArrayList<Filetype> typeToggle = new ArrayList<Filetype>();
	public static ArrayList<Media> media;
	
	public static void setList()
	{
		File[] files = processingMode.directory.listFiles();
		media = new ArrayList<>();
		
		for (File file : files)
		{
			switch(processingMode.mediaType)
			{
				case SCENE:
					media.add(new Scene(file));
				default:
					media.add(new Media(file));
			}
		}
	}
	
	public static void refreshList()
	{
		for (Media item : media)
		{
			if(!(Exec.typeToggle.contains(item.getExt())))
			{
				media.remove(item);
			}
		}
	}
}
