package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton600SemiBold extends Button {

	public MyButton600SemiBold(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyButton600SemiBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyButton600SemiBold(Context context) {
		super(context);
		init();
	}

	public void init() {

		/*Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-200XLight.ttf");*/

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-600SemiBold.ttf");
		setTypeface(tf ,1);

	}

}
