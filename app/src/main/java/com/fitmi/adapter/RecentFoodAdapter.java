//package com.fitmi.adapter;
//
//import java.util.ArrayList;
//
//import org.apache.http.annotation.Immutable;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import butterknife.ButterKnife;
//import butterknife.InjectView;
//
//import com.db.modules.FoodLoginModule;
//import com.fitmi.R;
//import com.fitmi.R.color;
//import com.fitmi.dao.FitmiFoodLogDAO;
//import com.fitmi.fragments.FoodLoggingFragment;
//
//public class RecentFoodAdapter extends BaseAdapter {
//
//	Context context;
//	ArrayList<FitmiFoodLogDAO> dataList;
//
//	FoodLoginModule foodLogObj;
//
//	public RecentFoodAdapter(Context context, ArrayList<FitmiFoodLogDAO>  dataList) {
//		// TODO Auto-generated constructor stub
//
//		this.context = context;
//		this.dataList = dataList;
//
//		foodLogObj = new FoodLoginModule(context);
//
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return dataList.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public class ViewHolder {
//
//		@InjectView(R.id.FoodName_RecentFoodListItem)
//		TextView foodName;
//
//		@InjectView(R.id.description)
//		TextView description;
//
//		@InjectView(R.id.recentfoodListcalory)
//		TextView recentfoodListcalory;
//
//		@InjectView(R.id.recentfoodListGram)
//		TextView recentfoodListGram;
//
//		@InjectView(R.id.imageViewFav)
//		ImageView imageViewFav;
//
//
//		public ViewHolder(View view) {
//			ButterKnife.inject(this, view);
//		}
//
//	}
//
//	ViewHolder holder;
//
//	@Override
//	public View getView(int arg0, View arg1, ViewGroup arg2) {
//		// TODO Auto-generated method stub
//
//		View view = arg1;
//
//		if (view == null) {
//
//			LayoutInflater inflater = (LayoutInflater) context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			view = inflater.inflate(R.layout.recent_food_listitem, null);
//
//			holder = new ViewHolder(view);
//
//			view.setTag(holder);
//
//		} else {
//
//			holder = (ViewHolder) view.getTag();
//
//		}
//
//		 if(!dataList.get(arg0).getDescription().equalsIgnoreCase("null"))
//			 holder.description.setText(dataList.get(arg0).getDescription());
//
//		/* if(!dataList.get(arg0).getMealWeight().equalsIgnoreCase("null"))
//			   holder.recentfoodListGram.setText(dataList.get(arg0).getMealWeight()+" g");
//			else
//				holder.recentfoodListGram.setText("0 g");
//		 double cal = 0;
//		 if(!dataList.get(arg0).getCalory().equalsIgnoreCase(""))
//		  cal = Double.parseDouble(dataList.get(arg0).getCalory());
//
//		 holder.recentfoodListcalory.setText((int)cal+" cal");*/
//
//		 holder.recentfoodListGram.setVisibility(View.GONE);
//		 holder.recentfoodListcalory.setVisibility(View.GONE);
//
//
//		holder.foodName.setText(dataList.get(arg0).getFoodName());
//
//
//		TagSet tag=new TagSet();
//		tag.favImg = holder.imageViewFav;
//		tag.foodId = dataList.get(arg0).getFoodLogId();
//
//		if(dataList.get(arg0).getFavourite().equalsIgnoreCase("0")){
//
//			tag.imgId = R.drawable.green_star;
//			tag.favImg.setImageResource(R.drawable.green_star);
//		}else{
//			tag.imgId = R.drawable.favorites_star;
//			tag.favImg.setImageResource(R.drawable.favorites_star);
//		}
//
//		holder.imageViewFav.setTag(tag);
//
//		holder.imageViewFav.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				TagSet tag = (TagSet) v.getTag();
//
//
//				switch (tag.imgId) {
//				case R.drawable.green_star:
//					tag.favImg.setImageResource(R.drawable.favorites_star);
//					tag.imgId = R.drawable.favorites_star;
//					foodLogObj.updateFavourite(tag.foodId, "1");
//					break;
//
//				case R.drawable.favorites_star:
//					tag.favImg.setImageResource(R.drawable.green_star);
//					tag.imgId = R.drawable.green_star;
//					foodLogObj.updateFavourite(tag.foodId, "0");
//					break;
//				}
//
//			}
//		});
//
//		if(FoodLoggingFragment.CLICKRECENTITEM == arg0){
//
//			view.setBackgroundColor(context.getResources().getColor(color.home_green_select_light));
//			//view.setAlpha(0.6f);
//		}else{
//			view.setBackgroundColor(context.getResources().getColor(color.white));
//			//view.setAlpha(1.0f);
//		}
//
//
//		return view;
//	}
//
//
//	class TagSet
//	{
//		ImageView favImg;
//		String foodId = "";
//		int imgId = 0;
//	}
//
//}