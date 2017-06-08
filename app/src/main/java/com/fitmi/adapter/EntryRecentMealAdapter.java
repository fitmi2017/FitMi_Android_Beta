//package com.fitmi.adapter;
//
//import java.util.ArrayList;
//import com.callback.Item;
//import com.db.modules.FoodLoginModule;
//import com.fitmi.R;
//import com.fitmi.R.color;
//import com.fitmi.dao.FitmiFoodLogDAO;
//import com.fitmi.dao.SectionItemFoodlogin;
//import com.fitmi.fragments.FoodLoggingFragment;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class EntryRecentMealAdapter extends ArrayAdapter<Item> {
//
//	private Context context;
//	private ArrayList<Item> items;
//	private LayoutInflater vi;
//	FoodLoginModule foodLogObj;
//
//	//DatabaseHelper databaseObject;
//
//	FitmiFoodLogDAO ei;
//
//	public EntryRecentMealAdapter(Context context,ArrayList<Item> items) {
//		super(context,0, items);
//
//		this.context = context;
//		this.items = items;
//		foodLogObj = new FoodLoginModule(context);
//
//		vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View v = convertView;
//		//ViewHolder holder = null;
//		final Item i = items.get(position);
//		if (i != null) {
//			if(i.isSection()){
//				SectionItemFoodlogin si = (SectionItemFoodlogin)i;
//				v = vi.inflate(R.layout.blue_header_listitem, null);
//
//				v.setOnClickListener(null);
//				v.setOnLongClickListener(null);
//				v.setLongClickable(false);
//
//				TextView sectionView = (TextView) v.findViewById(R.id.textSeparator);
//				sectionView.setText(si.getTitle());
//
//				TextView headerCalTotal = (TextView) v.findViewById(R.id.headerCalTotal);
//				headerCalTotal.setVisibility(View.GONE);
//				TextView cal = (TextView) v.findViewById(R.id.cal);
//				cal.setVisibility(View.GONE);
//
//
//			}else{
//				ei = (FitmiFoodLogDAO)i;
//				v = vi.inflate(R.layout.recent_meal_listitem, null);
//
//
//				TextView foodName = (TextView)v.findViewById(R.id.FoodName_RecentFoodListItem);
//
//				ImageView imageViewFav= (ImageView)v.findViewById(R.id.imageViewFav);
//
//				foodName.setText(ei.getFoodName());
//
//
//				TagSet tag=new TagSet();
//				tag.favImg = imageViewFav;
//				tag.foodId = ei.getFoodLogId();
//				tag.mealidList = ei.getMealidList();
//
//				if(ei.getMealFavourite().equalsIgnoreCase("0")){
//
//					tag.imgId = R.drawable.green_star;
//					tag.favImg.setImageResource(R.drawable.green_star);
//				}else{
//					tag.imgId = R.drawable.favorites_star;
//					tag.favImg.setImageResource(R.drawable.favorites_star);
//				}
//
//				imageViewFav.setTag(tag);
//
//				imageViewFav.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						TagSet tag = (TagSet) v.getTag();
//
//						switch (tag.imgId) {
//						case R.drawable.green_star:
//							tag.favImg.setImageResource(R.drawable.favorites_star);
//							tag.imgId = R.drawable.favorites_star;
//
//							for(int i=0;i<tag.mealidList.size();i++){
//									foodLogObj.updateFavouriteMeal(tag.mealidList.get(i), "1");
//								}
//
//							break;
//
//						case R.drawable.favorites_star:
//							tag.favImg.setImageResource(R.drawable.green_star);
//							tag.imgId = R.drawable.green_star;
//
//							for(int i=0;i<tag.mealidList.size();i++){
//									foodLogObj.updateFavouriteMeal(tag.mealidList.get(i), "0");
//								}
//
//							break;
//						}
//
//					}
//				});
//
//
//
//				v.setTag(ei.getMealId());
//			}
//		}
//
//		if(FoodLoggingFragment.CLICKRECENTITEM == position){
//
//			//view.setBackgroundColor(context.getResources().getColor(color.home_green_select_dark));
//			v.setBackgroundColor(context.getResources().getColor(color.home_green_select_light));
//			//view.setAlpha(0.8f);
//		}else{
//			v.setBackgroundColor(context.getResources().getColor(color.white));
//			//view.setAlpha(1.0f);
//		}
//
//		return v;
//	}
//
//
//
//
//	class TagSet
//	{
//		ImageView favImg;
//		String foodId = "";
//		int imgId = 0;
//		ArrayList<String> mealidList;
//	}
//
//}
