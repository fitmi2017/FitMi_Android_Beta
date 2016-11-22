package com.fitmi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HandelOutfemoryException {
/*	
	public static Bitmap convertBitmap(String path)   {

		Bitmap bitmap=null;
		BitmapFactory.Options bfOptions=new BitmapFactory.Options();
		bfOptions.inDither=false;                     //Disable Dithering mode
		bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
		bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
		bfOptions.inTempStorage=new byte[32 * 1024];


		File file=new File(path);
		FileInputStream fs=null;
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if(fs!=null)
			{
				bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally{
			if(fs!=null) {
				try {
					fs.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		
		

		return bitmap;
	}*/
	
	public static Bitmap convertBitmap(String filePath)   {

		  int reqHeight = 120;
		    int reqWidth = 120;
		    BitmapFactory.Options options = new BitmapFactory.Options();

		    // First decode with inJustDecodeBounds=true to check dimensions
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(filePath, options);

		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, reqWidth,
		            reqHeight);

		    // Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;

		    return BitmapFactory.decodeFile(filePath, options);
	}
	
	
	
	
	
	
/*	BitmapFactory.Options o = new BitmapFactory.Options();
    o.inJustDecodeBounds = true;
    o.inSampleSize = 6;
    // factor of downsizing the image

    FileInputStream inputStream = new FileInputStream(file);
    //Bitmap selectedBitmap = null;
    BitmapFactory.decodeStream(inputStream, null, o);
    inputStream.close();

    // The new size we want to scale to
    final int REQUIRED_SIZE=75;

    // Find the correct scale value. It should be the power of 2.
    int scale = 1;
    while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
        scale *= 2;
    }

    BitmapFactory.Options o2 = new BitmapFactory.Options();
    o2.inSampleSize = scale;
    inputStream = new FileInputStream(file);

    Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
    inputStream.close();

    // here i override the original image file
    file.createNewFile();
    FileOutputStream outputStream = new FileOutputStream(file);

    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);*/
	private static int calculateInSampleSize(BitmapFactory.Options options,
	        int reqWidth, int reqHeight) {

	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        // Calculate ratios of height and width to requested height and
	        // width
	        final int heightRatio = Math.round((float) height
	                / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);

	        // Choose the smallest ratio as inSampleSize value, this will
	        // guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	    return inSampleSize;
	}

}
