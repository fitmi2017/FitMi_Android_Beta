package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button {

	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyButton(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-200XLight.ttf");
/*
		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-600SemiBold.ttf");*/
		setTypeface(tf ,1);

	}

}
