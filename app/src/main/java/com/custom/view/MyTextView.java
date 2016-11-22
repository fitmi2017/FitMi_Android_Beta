package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	
/*	
	public MyTextView(Context context) {
		super(context);
		this.setTypeface(Typefaces.get(context, "SinkinSans-200XLight"));
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setTypeface(Typefaces.get(context, "SinkinSans-200XLight"));
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTypeface(Typefaces.get(context, "SinkinSans-200XLight"));
	}*/
	
	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyTextView(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-200XLight.ttf");
		setTypeface(tf ,1);

	}
}
