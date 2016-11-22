package com.fitmi.activitys;

import java.io.IOException;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.OnClick;

import com.db.DatabaseHelper;
import com.db.modules.SignUpModule;
import com.fitmi.R;
import com.fitmi.activitys.BaseActivity;

public class ChangePasswordActivity extends BaseActivity {


	DatabaseHelper databaseObject;

	String validPass = "";

	@InjectView(R.id.OldPassword)
	EditText OldPassword;

	@InjectView(R.id.NewPassword)
	EditText NewPassword;

	@InjectView(R.id.ConfirmPassword)
	EditText ConfirmPassword;

	@InjectView(R.id.Save_ChangePasswordActivity)
	Button Save_ChangePasswordActivity;

	String oldPass="" , newPass="", conPass="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		databaseObject = new DatabaseHelper(ChangePasswordActivity.this);
		try {

			databaseObject.createDatabase();

			databaseObject.openDatabase();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		prepareKnives();

		validPass = SignUpModule.getPassword(databaseObject);
		//hidden by avinash
	//	OldPassword.setText(validPass);

		Save_ChangePasswordActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				TextView[] views = { OldPassword, NewPassword, ConfirmPassword };
				String[] msg = { "Old password cannot be blank!", "New password cannot be blank!", "Confirm password cannot be blank!" };

				if (!mCommonFunction.validateAllFields(views, msg)) {
					return;
				}

				oldPass = OldPassword.getText().toString();
				newPass = NewPassword.getText().toString();
				conPass = ConfirmPassword.getText().toString();

				if(!oldPass.equalsIgnoreCase(validPass))
				{
					Toast.makeText(ChangePasswordActivity.this, "Old password is wrong", Toast.LENGTH_LONG).show();
					return;
				}
				else if(!newPass.equalsIgnoreCase(conPass))
				{
					Toast.makeText(ChangePasswordActivity.this, "Confirm password is not match with new password", Toast.LENGTH_LONG).show();
					return;
				}

				SignUpModule.changePassword(newPass,databaseObject);

				validPass = SignUpModule.getPassword(databaseObject);

				if(newPass.equalsIgnoreCase(validPass))
				{
					Toast.makeText(ChangePasswordActivity.this, "Password change success", Toast.LENGTH_LONG).show();

				}

				finish();
			}
		});


	}
	
	@OnClick(R.id.Save_ChangePasswordCancel)
	public void cliclCancel()
	{
		finish();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
