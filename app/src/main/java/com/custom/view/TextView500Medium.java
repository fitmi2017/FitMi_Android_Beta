package com.custom.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextView500Medium extends TextView {
	
	
	public TextView500Medium(Context context) {
		super(context);
		this.setTypeface(Typefaces.get(context, "SinkinSans-500Medium"));
		// TODO Auto-generated constructor stub
	}

	public TextView500Medium(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setTypeface(Typefaces.get(context, "SinkinSans-500Medium"));
	}

	public TextView500Medium(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTypeface(Typefaces.get(context, "SinkinSans-500Medium"));
	}
	
	

	/*public TextView500Medium(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TextView500Medium(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextView500Medium(Context context) {
		super(context);
		init();
	}

	public void init() {

		Typeface tf = Typeface.createFromAsset
				(getContext().getAssets(), "SinkinSans-500Medium.ttf");
		setTypeface(tf ,1);

	}*/
	
	
	
}
