package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextView400Regular extends TextView {

	public TextView400Regular(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextView400Regular(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextView400Regular(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-400Regular.ttf");
		setTypeface(tf ,1);

	}
}
