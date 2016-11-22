package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class EditTextHelveticaNeueThin extends EditText {

	public EditTextHelveticaNeueThin(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public EditTextHelveticaNeueThin(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EditTextHelveticaNeueThin(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "HelveticaNeue-Thin.otf");
		setTypeface(tf ,1);

	}
}
