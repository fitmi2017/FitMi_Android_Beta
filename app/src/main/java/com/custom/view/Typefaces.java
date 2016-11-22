package com.custom.view;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class Typefaces {

	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	public static Typeface get(Context c, String name) {
		synchronized (cache) {
			if (!cache.containsKey(name)) {

				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(), name
							+ ".ttf");
					cache.put(name, t);

				} catch (Exception e) {
					Log.e("Typeface", "Could not get typeface '" + name
							+ "' because " + e.getMessage());
					return null;
				}
			}
			return cache.get(name);
		}
	}
}