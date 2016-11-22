package com.dts.classes;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.dts.utils.Constants;
import com.ssl.MySSLSocketFactory;

public class AsyncTaskMain extends AsyncTask<String, Long, String> {

	AsyncTaskListener listener;
	HashMap<String, PostObject> map;
	private DefaultHttpClient mHttpClient;
	private String _data = "";
	private Activity _activity;
	private boolean useArrayList = false;
	private ArrayList<String> keys;
	private ArrayList<String> values;

	CommonFunction _commonFunction;
	boolean exception = false;

	public AsyncTaskMain(AsyncTaskListener listener,
			HashMap<String, PostObject> map, Activity _activity) {
		this.listener = listener;
		this.map = map;
		serverCommunication();
		this._activity = _activity;
		_commonFunction = new CommonFunction(_activity);
	}

	public AsyncTaskMain(AsyncTaskListener listener, ArrayList<String> keys,
			ArrayList<String> values, Activity _activity) {
		this.listener = listener;
		this.keys = keys;
		this.values = values;
		serverCommunication();
		this._activity = _activity;
		useArrayList = true;
		_commonFunction = new CommonFunction(_activity);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		CommonFunction _commFunction = new CommonFunction(_activity);
		if (!JSONParser.haveInternet(_activity)) {
			_commFunction.showToastMessage(
					"Oops! Network Unavailable. Try again later!",
					_commFunction.TOAST_LENGTH_SHORT);
		}

		if (listener != null)
			listener.onTaskPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		if (listener != null)
			listener.onTaskCompleted(this._data);
		Log.e("RESPONSE", this._data);
		if (exception) {
			CommonFunction _commFunction = new CommonFunction(_activity);
			// _commFunction
			// .showToastMessageShort("Something went wrong! Please try again!");
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub

		String baseUrl = JSONParser.webservicePostURL;
		if (map.containsKey("resource")) {
			baseUrl = baseUrl
					+ ((PostObject) map.get("resource")).getString_value();
		}
		
		
		Log.e("POSTEDURL", baseUrl);
		HttpPost _httpPost = new HttpPost(baseUrl);
		_httpPost.setHeader(HttpHeaders.AUTHORIZATION, "dts_android_request");
		MultipartEntity multipartEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		if (!useArrayList) {

			Log.e("POSTED", map.toString());

			@SuppressWarnings("rawtypes")
			Iterator it = map.entrySet().iterator();
			// Log.e("COUNT", map.entrySet().size() + "");
			while (it.hasNext()) {
				Map.Entry<String, PostObject> eachPair = (Entry<String, PostObject>) it
						.next();
				String key = eachPair.getKey();
				Log.e("VALUES", key + " : "
						+ eachPair.getValue().getString_value());
				try {
					if (eachPair.getValue().isFileAvailable()) {
						File _file = new File(eachPair.getValue().getFile_url());
						if (!_file.exists()){
							Log.e("File ","Not exists");
						}else{
							Log.e("File ","Exists");
						}
						multipartEntity.addPart(key, new FileBody(_file));
					} else {
						multipartEntity.addPart(key, new StringBody(eachPair
								.getValue().getString_value()));
					}
					_httpPost.setEntity(multipartEntity);

				} catch (Exception e) {

				}
			}
		} else {
			try {
				for (int i = 0; i < keys.size(); i++) {
					String eachkey = keys.get(i);
					String eachVal = values.get(i);
					multipartEntity.addPart(eachkey, new StringBody(eachVal));
				}
				_httpPost.setEntity(multipartEntity);
			} catch (Exception e) {

			}
		}
		try {

			mHttpClient.execute(_httpPost, new WebserviceResponseHandler());
			map.clear();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exception = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exception = true;
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	private class WebserviceResponseHandler implements ResponseHandler {

		@Override
		public Object handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			// TODO Auto-generated method stub
			HttpEntity r_entity = response.getEntity();
			String responseString = EntityUtils.toString(r_entity);
			_data = responseString;
			return null;
		}

	}

	@SuppressWarnings("deprecation")
	public void serverCommunication() {
		
		/*KeyStore trustStore;
		try {
			
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			
			trustStore.load(null, null);

	        MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			
			SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));
	        
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		
		
		/*HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
				HttpVersion.HTTP_1_1);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				Constants.CONNECTION_TIMED_OUT);
		mHttpClient = new DefaultHttpClient(params);*/
		
		mHttpClient = getNewHttpClient();
	}
	
	
	public DefaultHttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					Constants.CONNECTION_TIMED_OUT);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}

}
