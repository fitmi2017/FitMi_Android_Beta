package com.dts.classes;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

public class SimpleYouTubeHelper {

	public static String getImageUrlQuietly(String youtubeUrl) {
		try {
			if (youtubeUrl != null) {
				return String.format("http://img.youtube.com/vi/%s/1.jpg", Uri
						.parse(youtubeUrl).getQueryParameter("v"));
			}
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTitleQuietly(String youtubeUrl) {
		try {
			if (youtubeUrl != null) {
				URL embededURL = new URL("http://www.youtube.com/oembed?url="
						+ youtubeUrl + "&format=json");
				return new JSONObject(IOUtils.toString(embededURL))
						.getString("title");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
