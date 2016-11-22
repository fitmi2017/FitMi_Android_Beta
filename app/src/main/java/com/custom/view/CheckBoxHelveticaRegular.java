package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

public class CheckBoxHelveticaRegular extends CheckBox {

	public CheckBoxHelveticaRegular(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CheckBoxHelveticaRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CheckBoxHelveticaRegular(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "helvetica-regular.ttf");
		setTypeface(tf ,1);

	}
}
