package com.fitmi.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.fitmi.R;
import com.fitmi.dao.UserInfoDAO;
import com.fitmi.fragments.MyActivityFragment;
import com.fitmi.utils.Constants;
import com.fitmi.utils.HandelOutfemoryException;
import com.squareup.picasso.Picasso;

public class UserListAdapter extends BaseAdapter {

	Context context;
	ArrayList<UserInfoDAO> dataList;
	Bitmap myBitmap;

	public UserListAdapter(Context context, ArrayList<UserInfoDAO> dataList) {
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

		@InjectView(R.id.UserName_ItemUser)
		TextView userName;

		@InjectView(R.id.UserFrame_ItemUser)
		RelativeLayout userFrameItemUser;

		//@InjectView(R.id.imageView1)
		ImageView profilePic;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}

	}

	ViewHolder holder;

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		View view = arg1;

		if (view == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_user, null);

			holder = new ViewHolder(view);

			holder.profilePic = (ImageView)view.findViewById(R.id.imageView1);

			view.setTag(holder);

		} else {

			holder = (ViewHolder) view.getTag();

		}

		if (Constants.PROFILE_ID.equalsIgnoreCase(dataList.get(position).getProfileId())) {

			holder.userFrameItemUser
			.setBackgroundResource(R.color.userBg);

		} else {

			holder.userFrameItemUser
			.setBackgroundResource(R.color.white);

		}

		holder.userFrameItemUser.setTag(dataList.get(position));
		holder.userName.setText(dataList.get(position).getFirstName()+" "+dataList.get(position).getLastName());

		String imagePath = dataList.get(position).getPicturePath();

	/*	if(imagePath !=null && !imagePath.equalsIgnoreCase(""))	{	
			//setFullImageFromFilePath(imagePath,holder.profilePic);

			if(myBitmap !=null)
				myBitmap.recycle();

			myBitmap = BitmapFactory.decodeFile(dataList.get(position).getPicturePath());
			holder.profilePic.setImageBitmap(myBitmap);

			myBitmap = HandelOutfemoryException.convertBitmap(imagePath);
			holder.profilePic.setImageBitmap(myBitmap);
		}	*/
		
		
		if(imagePath !=null && !imagePath.equalsIgnoreCase(""))	{	
			   //setFullImageFromFilePath(dataList.get(position).getPicturePath(),holder.profilePic);
				//Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
				//profileImage.setImageBitmap(myBitmap);
				
				//setFullImageFromFilePath(imagePath, profileImage);
			myBitmap = HandelOutfemoryException.convertBitmap(imagePath);
			//holder.profilePic.setImageBitmap(myBitmap);
			Picasso.with(context).load("file:" + imagePath).noFade().resize(80, 80).centerCrop().into(holder.profilePic);
			}else{
				
				if (dataList.get(position).getGender().equalsIgnoreCase("Male")) {	
					
					holder.profilePic.setImageResource(R.drawable.user_male);

				} else {
					
					holder.profilePic.setImageResource(R.drawable.user_female);
				}
			}

		return view;
	}


	public Bitmap convertBitmap(String path)   {

		Bitmap bitmap=null;
		BitmapFactory.Options bfOptions=new BitmapFactory.Options();
		bfOptions.inDither=false;                     //Disable Dithering mode
		bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
		bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
		bfOptions.inTempStorage=new byte[32 * 1024];


		File file=new File(path);
		FileInputStream fs=null;
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if(fs!=null)
			{
				bitmap=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally{
			if(fs!=null) {
				try {
					fs.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		return bitmap;
	}
}
