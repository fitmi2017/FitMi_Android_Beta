package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewHome extends TextView {

	public TextViewHome(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextViewHome(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewHome(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-200XLight.ttf");
		setTypeface(tf ,1);

	}
}
