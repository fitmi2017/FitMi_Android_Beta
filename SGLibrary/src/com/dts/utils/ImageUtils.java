package com.dts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.os.Environment;

public class ImageUtils {

	public static boolean copyFile(String from, File dir, String fileName) {
		FileChannel src = null;
		FileChannel dst = null;
		try {
			File sd = Environment.getExternalStorageDirectory();

			/*if (fileName.contains("mp4")) {

				

			}*/
			
			if (!dir.exists()) {
				dir.mkdirs();
				File file = new File(dir + "/" + fileName);
				file.createNewFile();
			}

			if (sd.canWrite()) {
				File source = new File(from);
				File destination = new File(dir, fileName);
				if (source.exists()) {
					src = new FileInputStream(source).getChannel();
					dst = new FileOutputStream(destination).getChannel();
					dst.transferFrom(src, 0, src.size());
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				src.close();
				dst.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}