package com.fitmi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.callback.ChangeFoodContents;
import com.callback.MealFavNotify;
import com.db.modules.FoodLoginModule;
import com.fitmi.R;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.utils.Constants;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TotalFoodFooterAdapter extends BaseAdapter {

    Context context;
    String current_activewt;
    String current_activecal;
    String total_calorie;
    FoodLoginModule foodLogObj;
    MealFavNotify buttonListener;
    String isMealFav;
    Activity activity;
    public static String food_content_type = "Cal";
    ChangeFoodContents changeFoodContents;

    public TotalFoodFooterAdapter(Context context,ChangeFoodContents changeFoodContents, String current_activewt, String current_activecal, String total_calorie, String isMealFav) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.changeFoodContents=changeFoodContents;
        this.current_activecal = current_activecal;
        this.current_activewt = current_activewt;
        this.total_calorie = total_calorie;
        this.isMealFav = isMealFav;
        this.activity = (Activity) context;
        foodLogObj = new FoodLoginModule(context);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public class ViewHolder {

        @InjectView(R.id.total__wt)
        TextView total__wt;
        @InjectView(R.id.total_cuurent_active_wt)
        TextView total_cuurent_active_wt;
        @InjectView(R.id.total_current_calorie_wt)
        TextView total_current_calorie_wt;
        @InjectView(R.id.imgcurrent_weight)
        ImageView imgcurrent_weight;
        @InjectView(R.id.food_content_type)
        TextView food_content_type;

	/*	@InjectView(R.id.recentfoodListcalory)
        TextView recentfoodListcalory;

		@InjectView(R.id.recentfoodListGram)
		TextView recentfoodListGram;

		@InjectView(R.id.imageViewFav)
		ImageView imageViewFav;*/

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

    }

    ViewHolder holder;

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub

        View view = arg1;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listtotal_food_detalsitem, arg2, false);

            holder = new ViewHolder(view);

            view.setTag(holder);


        } else {

            holder = (ViewHolder) view.getTag();

        }

        if (Constants.gunitfw == 0) {
            holder.imgcurrent_weight.setImageResource(R.drawable.total_weight);
            holder.total_cuurent_active_wt.setText(current_activewt);
        } else {
            //	oz =g * 0.035274
            float oz;
            int temp_;
            oz = Float.parseFloat(current_activewt);
            oz = (float) (oz * 0.035274);
            temp_ = (int) oz;
            holder.total_cuurent_active_wt.setText(String.valueOf(temp_));
            holder.imgcurrent_weight.setImageResource(R.drawable.total_weight_oz);
        }
        //	setText();
        holder.total__wt.setText(total_calorie);

        holder.total_current_calorie_wt.setText(current_activecal);

        holder.food_content_type.setText(food_content_type.toUpperCase());
        holder.food_content_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFoodContents.changeFoodContentType();
            }
        });

        holder.imgcurrent_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFoodContents.changeFoodUnit();
            }
        });
        return view;
    }


    class TagSet {
        ImageView favImg;
        String foodId = "";
        int imgId = 0;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }


    // RETURN 1 IF FAVORITE
    // RETURN 0 IF NOT FAVORITE
    @Override
    public int getItemViewType(int position) {

        if (isMealFav.equalsIgnoreCase("0")) {

            return 0;
        } else {
            return 1;
        }
    }
}