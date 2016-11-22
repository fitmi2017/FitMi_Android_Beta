package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButtonSinkinSansNineHXBlack extends Button {

	public MyButtonSinkinSansNineHXBlack(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyButtonSinkinSansNineHXBlack(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyButtonSinkinSansNineHXBlack(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-900XBlack.ttf");
/*
		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-600SemiBold.ttf");*/
		setTypeface(tf ,1);

	}

}
