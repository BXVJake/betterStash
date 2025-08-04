package application.functional;

import java.io.File;

public class Media
{
	private String filename;
	private Filetype extension;
	private File file;
	
	public Media(File path)
	{
		this.file = path;
		this.extension = findType();
		this.filename = file.getName();
	}
	
	private Filetype findType()
	{
		String path = file.toString();
		String ext = path.substring(path.lastIndexOf(".") + 1).toUpperCase();

		switch (ext)
		{
			case "M4V": return Filetype.M4V;
			case "MP4": return Filetype.MP4;
			case "MOV": return Filetype.MOV;
			case "WMV": return Filetype.WMV;
			case "AVI": return Filetype.AVI;
			case "MPG": return Filetype.MPG;
			case "MPEG": return Filetype.MPEG;
			case "RMVB": return Filetype.RMVB;
			case "RM": return Filetype.RM;
			case "FLV": return Filetype.FLV;
			case "ASF": return Filetype.ASF;
			case "MKV": return Filetype.MKV;
			case "WEBM": return Filetype.WEBM;
			case "F4V": return Filetype.F4V;
			case "PNG": return Filetype.PNG;
			case "JPG": return Filetype.JPG;
			case "JPEG": return Filetype.JPEG;
			case "GIF": return Filetype.GIF;
			case "WEBP": return Filetype.WEBP;
			default: return Filetype.UNKN;
		}
	}
	
	public Filetype getExt()
	{
		return extension;
	}
}
