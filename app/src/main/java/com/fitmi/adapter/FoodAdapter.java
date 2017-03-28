package com.fitmi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.callback.MealFavNotify;
import com.db.modules.FoodLoginModule;
import com.fitmi.R;
import com.fitmi.R.color;
import com.fitmi.dao.FitmiFoodLogDAO;
import com.fitmi.fragments.FoodLoggingFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FoodAdapter extends BaseAdapter {

    Context context;
    ArrayList<FitmiFoodLogDAO> dataList;

    FoodLoginModule foodLogObj;
    MealFavNotify buttonListener;

    public FoodAdapter(Context context, ArrayList<FitmiFoodLogDAO> dataList, MealFavNotify buttonListener) {
        // TODO Auto-generated constructor stub

        this.context = context;
        this.dataList = dataList;
        this.buttonListener = buttonListener;

        foodLogObj = new FoodLoginModule(context);

    }

    public ArrayList<FitmiFoodLogDAO> getAllData() {
        return dataList;
    }

    public void setAllData(ArrayList<FitmiFoodLogDAO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
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

        @InjectView(R.id.FoodName_RecentFoodListItem)
        TextView foodName;

        @InjectView(R.id.description)
        TextView description;

        @InjectView(R.id.recentfoodListcalory)
        TextView recentfoodListcalory;

        @InjectView(R.id.recentfoodListGram)
        TextView recentfoodListGram;

        @InjectView(R.id.imageViewFav)
        ImageView imageViewFav;


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
            view = inflater.inflate(R.layout.recent_food_listitem, arg2, false);

            holder = new ViewHolder(view);

            view.setTag(holder);


        } else {

            holder = (ViewHolder) view.getTag();

        }

		/*if(!dataList.get(arg0).getDescription().equalsIgnoreCase("null"))
            holder.description.setText(dataList.get(arg0).getDescription());*/

        if (!dataList.get(arg0).getMealWeight().equalsIgnoreCase("null"))
            if (com.fitmi.utils.Constants.gunitfw == 0) {
                holder.recentfoodListGram.setText(dataList.get(arg0).getMealWeight() + " g");
            } else {
                double wt, f_wt;
                wt = Double.parseDouble(dataList.get(arg0).getMealWeight());
                f_wt = wt / 28.34952;

                Log.e(" Getting value in oz ", String.valueOf(f_wt));

                DecimalFormat formatter = new DecimalFormat("##.##");
                String yourFormattedString = formatter.format(f_wt);
                //	dataList.get(arg0).getMealWeight()
                Log.e(" Getting value in oz ", String.valueOf(yourFormattedString));
                holder.recentfoodListGram.setText(yourFormattedString + " oz");
                ;
            }
            //	holder.recentfoodListGram.setText(dataList.get(arg0).getMealWeight()+" g");
        else {
            if (com.fitmi.utils.Constants.gunitfw == 0) {
                holder.recentfoodListGram.setText("0 g");
            }
            {
                holder.recentfoodListGram.setText("0 oz");
            }
            //holder.recentfoodListGram.setText("0 g");
        }
        double cal = 0, pergram = 0;


        switch (TotalFoodFooterAdapter.food_content_type) {

            case "Cal":
                if (!dataList.get(arg0).getCalory().equalsIgnoreCase("")
                        && !dataList.get(arg0).getServingSize().equalsIgnoreCase("")
                        && !dataList.get(arg0).getMealWeight().equalsIgnoreCase("")) {
                    pergram = Float.parseFloat(dataList.get(arg0).getCalory())
                            / Float.parseFloat(dataList.get(arg0).getServingSize());
                    cal = Float.parseFloat(dataList.get(arg0).getMealWeight())
                            * pergram;
//                    cal = Double.parseDouble(dataList.get(arg0).getCalory());
                }
                holder.recentfoodListcalory.setText((int) cal + " cal");
                break;

            case "Pro":
                if (!dataList.get(arg0).getPro().equalsIgnoreCase("") && !dataList.get(arg0).getServingSize().equalsIgnoreCase("")
                        && !dataList.get(arg0).getMealWeight().equalsIgnoreCase("")) {
                    pergram = Float.parseFloat(dataList.get(arg0).getPro())
                            / Float.parseFloat(dataList.get(arg0).getServingSize());
                    cal = Float.parseFloat(dataList.get(arg0).getMealWeight())
                            * pergram;
                }
//                    cal = Double.parseDouble(dataList.get(arg0).getPro());
                holder.recentfoodListcalory.setText((int) cal + " pro");
                break;

            case "Car":
                if (!dataList.get(arg0).getCar().equalsIgnoreCase("") && !dataList.get(arg0).getServingSize().equalsIgnoreCase("")
                        && !dataList.get(arg0).getMealWeight().equalsIgnoreCase("")) {
                    pergram = Float.parseFloat(dataList.get(arg0).getCar())
                            / Float.parseFloat(dataList.get(arg0).getServingSize());
                    cal = Float.parseFloat(dataList.get(arg0).getMealWeight())
                            * pergram;
//                    cal = Double.parseDouble(dataList.get(arg0).getCar());
                }
                holder.recentfoodListcalory.setText((int) cal + " car");
                break;

            case "Fat":
                if (!dataList.get(arg0).getFat().equalsIgnoreCase("") && !dataList.get(arg0).getServingSize().equalsIgnoreCase("")
                        && !dataList.get(arg0).getMealWeight().equalsIgnoreCase("")) {
                    pergram = Float.parseFloat(dataList.get(arg0).getFat())
                            / Float.parseFloat(dataList.get(arg0).getServingSize());
                    cal = Float.parseFloat(dataList.get(arg0).getMealWeight())
                            * pergram;
//                    cal = Double.parseDouble(dataList.get(arg0).getFat());
                }
                holder.recentfoodListcalory.setText((int) cal + " fat");
                break;

            case "Sod":
                if (!dataList.get(arg0).getSod().equalsIgnoreCase("") && !dataList.get(arg0).getServingSize().equalsIgnoreCase("")
                        && !dataList.get(arg0).getMealWeight().equalsIgnoreCase("")) {
                    pergram = Float.parseFloat(dataList.get(arg0).getSod())
                            / Float.parseFloat(dataList.get(arg0).getServingSize());
                    cal = Float.parseFloat(dataList.get(arg0).getMealWeight())
                            * pergram;
//                    cal = Double.parseDouble(dataList.get(arg0).getSod());
                }
                holder.recentfoodListcalory.setText((int) cal + " sod");
                break;

            case "Cho":
                if (!dataList.get(arg0).getCho().equalsIgnoreCase("") && !dataList.get(arg0).getServingSize().equalsIgnoreCase("")
                        && !dataList.get(arg0).getMealWeight().equalsIgnoreCase("")) {
                    pergram = Float.parseFloat(dataList.get(arg0).getCho())
                            / Float.parseFloat(dataList.get(arg0).getServingSize());
                    cal = Float.parseFloat(dataList.get(arg0).getMealWeight())
                            * pergram;
//                    cal = Double.parseDouble(dataList.get(arg0).getCho());
                }
                holder.recentfoodListcalory.setText((int) cal + " cho");
                break;
        }


        //holder.recentfoodListcalory.setText("0 cal");
        //holder.recentfoodListGram.setText("0 g");


        holder.foodName.setText(dataList.get(arg0).getFoodName());

		/*if(dataList.get(arg0).getBrandName().equalsIgnoreCase(""))		 
            holder.foodName.setText(dataList.get(arg0).getFoodName());
		 else
			 holder.foodName.setText(dataList.get(arg0).getFoodName()+", "+dataList.get(arg0).getBrandName());*/


        TagSet tag = new TagSet();
        tag.favImg = holder.imageViewFav;
        tag.foodId = dataList.get(arg0).getFoodLogId();

        if (dataList.get(arg0).getFavourite().equalsIgnoreCase("0")) {

            tag.imgId = R.drawable.green_star;
            tag.favImg.setImageResource(R.drawable.green_star);
        } else {
            tag.imgId = R.drawable.favorites_star;
            tag.favImg.setImageResource(R.drawable.favorites_star);
        }

        holder.imageViewFav.setTag(tag);

        holder.imageViewFav.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TagSet tag = (TagSet) v.getTag();

                switch (tag.imgId) {
                    case R.drawable.green_star:
                        tag.favImg.setImageResource(R.drawable.favorites_star);
                        tag.imgId = R.drawable.favorites_star;
                        foodLogObj.updateFavourite(tag.foodId, "1");

                        notifyDataSetChanged();
                        break;

                    case R.drawable.favorites_star:
                        tag.favImg.setImageResource(R.drawable.green_star);
                        tag.imgId = R.drawable.green_star;
                        foodLogObj.updateFavourite(tag.foodId, "0");
                        notifyDataSetChanged();
                        break;
                }

                buttonListener.buttonPressed();
            }
        });


        if (FoodLoggingFragment.sFOODLOGGING_POS == arg0) {

            //view.setBackgroundColor(context.getResources().getColor(color.home_green_select_dark));
            view.setBackgroundColor(context.getResources().getColor(color.home_green_select_light));
            //view.setAlpha(0.8f);
        } else {
            view.setBackgroundColor(context.getResources().getColor(color.white));
            //view.setAlpha(1.0f);
        }

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
        // TODO Auto-generated method stub
/*		if (position%2 == 0)
            return 0;
		else 
			return 1;*/
        if (dataList.get(position).getFavourite().equalsIgnoreCase("0")) {

            return 0;
        } else {
            return 1;
        }

        //return super.getItemViewType(position);
    }

}