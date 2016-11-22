package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextView300Light extends TextView {

	public TextView300Light(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextView300Light(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextView300Light(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-300Light.ttf");
		setTypeface(tf ,1);

	}
}
