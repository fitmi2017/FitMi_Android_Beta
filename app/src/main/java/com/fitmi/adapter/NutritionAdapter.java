package com.fitmi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.fitmi.R;
import com.fitmi.dao.NutritionTypeSetget;


public class NutritionAdapter extends BaseAdapter {

	Context context;
	ArrayList<NutritionTypeSetget> dataList;

	NutritionTypeSetget nutritionTypeDAO;
	

	public NutritionAdapter(Context context, ArrayList<NutritionTypeSetget>  dataList) {
		// TODO Auto-generated constructor stub

		this.context = context;
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

/*		@InjectView(R.id.FoodName_RecentFoodListItem)
		TextView foodName;

		@InjectView(R.id.description)
		TextView description;

		@InjectView(R.id.recentfoodListcalory)
		TextView recentfoodListcalory;

		@InjectView(R.id.recentfoodListGram)
		TextView recentfoodListGram;

		@InjectView(R.id.imageViewFav)
		ImageView imageViewFav;
*/
		@InjectView(R.id.foodItemname)
		TextView foodItemname;

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
			view = inflater.inflate(R.layout.itemlistnutrition,arg2, false);

			holder = new ViewHolder(view);

			view.setTag(holder);			


		} else {

			holder = (ViewHolder) view.getTag();

		}

		NutritionTypeSetget nutritionTypeSetget= new NutritionTypeSetget();
		nutritionTypeSetget=dataList.get(arg0);
		String name,value,unit;
		if(!nutritionTypeSetget.getName().equalsIgnoreCase("null"))
		{
			name=nutritionTypeSetget.getName();
		}else{
			name="Not Specified";
		}
		
		if(!nutritionTypeSetget.getValue().equalsIgnoreCase("null"))
		{
			value=nutritionTypeSetget.getValue();
		}
		else{
			value="";
		}
		
		if(!nutritionTypeSetget.getUnit().equalsIgnoreCase("null"))
		{
			unit=nutritionTypeSetget.getUnit();
			
		}else{
			unit="";
		}
		
		holder.foodItemname.setText(name+"    "+value+unit);

		return view;
	}


	class TagSet
	{
		ImageView favImg;
		String foodId = "";
		int imgId = 0;
	}
	

	

}