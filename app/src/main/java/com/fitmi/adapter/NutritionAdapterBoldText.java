package com.fitmi.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.text.Html;
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


public class NutritionAdapterBoldText extends BaseAdapter {

	Context context;
	ArrayList<NutritionTypeSetget> dataList;

	 HashMap<String, String> datamap;
	NutritionTypeSetget nutritionTypeDAO;
	

	public NutritionAdapterBoldText(Context context, HashMap<String, String> datamap) {
		// TODO Auto-generated constructor stub

		this.context = context;
		this.datamap = datamap;
		
		

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datamap.size();
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
		@InjectView(R.id.fooditemName)
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
			view = inflater.inflate(R.layout.item_nutrilist_boldtext,arg2, false);

			holder = new ViewHolder(view);

			view.setTag(holder);			


		} else {

			holder = (ViewHolder) view.getTag();

		}

		switch(arg0){
		
			case 0:
				holder.foodItemname.setText(Html.fromHtml("<big><b>Total Fat  </b></big><small>"+datamap.get("_nf_total_fat")+"</small>"));
			break;
				
			case 1:
				holder.foodItemname.setText( Html.fromHtml("<big><b>Saturated Fat  </b></big><small>"+datamap.get("_nf_saturated_fat")+"</small>"));
			break;
			
			case 2:
				holder.foodItemname.setText( Html.fromHtml("<big><b>Cholesterol  </b></big><small>"+datamap.get("_nf_cholesterol")+"</small>"));
			
			break;
			
			case 3:
				holder.foodItemname.setText( Html.fromHtml("<big><b>Sodium  </b></big><small>"+datamap.get("_nf_sodium")+"</small>"));
			break;
			
			case 4:
				holder.foodItemname.setText( Html.fromHtml("<big><b>Total Carbohydrate  </b></big><small>"+datamap.get("_nf_total_carbohydrate")+"</small>"));
			break;
			
			case 5:
				holder.foodItemname.setText( Html.fromHtml("<big><b>Dietry Fiber  </b></big><small>"+datamap.get("_nf_dietary_fiber")+"</small>"));
			
			break;
			
			case 6:
				holder.foodItemname.setText(Html.fromHtml("<big><b>Sugar  </b></big><small>"+datamap.get("_nf_sugars")+"</small>"));
			break;
			
			case 7:
				holder.foodItemname.setText(Html.fromHtml("<big><b>Protein  </b></big><small>"+datamap.get("_nf_protein")+"</small>"));
			break;
			
			case 8:
				holder.foodItemname.setText(Html.fromHtml("<big><b>Potassium  </b></big><small>"+datamap.get("_nf_potassium")+"</small>"));
			break;
			
			case 9:
				holder.foodItemname.setText(Html.fromHtml("<big><b>P Value  </b></big><small>"+datamap.get("_nf_p")+"</small>"));
				
			break;
		}
	

		return view;
	}


	class TagSet
	{
		ImageView favImg;
		String foodId = "";
		int imgId = 0;
	}
	

	

}