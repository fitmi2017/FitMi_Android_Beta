package com.fitmi.activitys;//package com.fitmi.activitys;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.WindowManager;
//import android.widget.ImageView;
//
//import com.capricorn.ArcMenu;
//import com.fitmi.R;
//
//public class DashBoardActivity extends BaseActivity {
//
//	private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera,
//			R.drawable.composer_music, R.drawable.composer_place,
//			R.drawable.composer_sleep, R.drawable.composer_thought,
//			R.drawable.composer_with };
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_dashboard);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//		ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2);
//		initArcMenu(arcMenu2, ITEM_DRAWABLES);
//
//	}
//
//	private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
//		final int itemCount = itemDrawables.length;
//		for (int i = 0; i < itemCount; i++) {
//			ImageView item = new ImageView(this);
//			item.setImageResource(itemDrawables[i]);
//
//			final int position = i;
//			menu.addItem(item, new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					// Toast.makeText(DashBoardActivity.this, "position:" +
//					// position,
//					// Toast.LENGTH_SHORT).show();
//
//				}
//			});
//		}
//	}
//
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		super.onBackPressed();
//	}
//
// }
