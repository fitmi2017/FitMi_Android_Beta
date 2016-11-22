package com.fitmi.fragments;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.fitmi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatFragment extends BaseFragment{

	@InjectView(R.id.webViewChat)
	WebView webViewChat;

	@InjectView(R.id.Heading)
	public TextView heading;

	@InjectView(R.id.Settings)
	public ImageView Settings;

	@InjectView(R.id.Back)
	public ImageView back;
	
	@InjectView(R.id.backLiner)
	LinearLayout backLiner;
	
	private ProgressDialog progressBar;
	AlertDialog alertDialog;
	
	
	Activity activity;
	ProgressDialog pd;
	Dialog progressDialog;

	Button btn_back;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.chat_fragment, container, false);
		ButterKnife.inject(this, view);
		setNullClickListener(view);

		Settings.setVisibility(View.INVISIBLE);
		heading.setText("Chat");
		
		/*progressBar = ProgressDialog.show(getActivity(), "WebView Example", "Loading...");*/
		
		  pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();
		alertDialog = new AlertDialog.Builder(getActivity()).create();

		WebSettings settings = webViewChat.getSettings();
		settings.setJavaScriptEnabled(true);
		webViewChat.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		  
	        if (Build.VERSION.SDK_INT >= 19) {
	        	webViewChat.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	        }

	        webViewChat.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);
				return true;
			}

			public void onPageFinished(WebView view, String url) {

				if (pd.isShowing()) {
					pd.dismiss();
				}
			}

			@SuppressWarnings("deprecation")
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

				Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
				alertDialog.setTitle("Error");
				alertDialog.setMessage(description);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
				alertDialog.show();
			}
		});
	        webViewChat.loadUrl("https://secure.livechatinc.com/licence/3498581/open_chat.cgi?groups=0");

		return view;
	}
	
	@OnClick(R.id.backLiner)
	public void back() {

		getActivity().onBackPressed();

	}
	
/*	 @Override
	    public void onBackPressed() {
	    	// TODO Auto-generated method stub
	    //	super.onBackPressed();
	    	

	    	if (webViewFaq.canGoBack()) {
	        	webViewFaq.goBack();
	        } else {
	            finish();
	        }
	    }*/
	    
}
