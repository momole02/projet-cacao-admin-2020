package ci.miage.ayenon.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Uploader {
	
	public final static String PHOTO_FOLDER = "photo/";
	public final static String BIRTH_ACT_FOLDER = "birth_act/";
	
	public static File upload(String destFolder , String destNamePrefix , File srcFile)
	{
		File dstFile = new File(destFolder+destNamePrefix);
		
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(srcFile));
			
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(dstFile));
			
			int readLength = -1;
			byte buffer[] = new byte[1024]; // 1 Ko buffer 
			while((readLength = in.read(buffer)) > 0) {
				out.write(buffer, 0 , readLength);
				out.flush();
			}
			in.close();
			out.close();
			
			return dstFile;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ; 
		}
	}
	
}
