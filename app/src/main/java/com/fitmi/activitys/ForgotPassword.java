package com.fitmi.activitys;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.dialog.Alert;
import com.dts.classes.JSONParser;
import com.fitmi.R;
import com.ssl.MySSLSocketFactory;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by harmanpreet on 28/3/17.
 */
public class ForgotPassword extends BaseActivity {

    @InjectView(R.id.editText2)
    EditText editText2;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forgot_password);
        prepareKnives();
        pDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.Cancel)
    public void cancel() {
        onBackPressed();
    }

    @OnClick(R.id.Send)
    public void send() {
//        String valemail = editText2.getText().toString();
//        AsyncForgetpassword asyncForgetpassword = new AsyncForgetpassword(JSONParser.HOST_URL_DYNAMIC + "fitmiwebservice/index.php/authentication/forgot_password?", valemail);
//        asyncForgetpassword.execute("exe");
    }

    private class AsyncForgetpassword extends AsyncTask<String, Void, String> {

        String JSON_URL;
        JSONObject holder = new JSONObject();
        String email;

        public AsyncForgetpassword(String url, String email) {
            // TODO Auto-generated constructor stub
            JSON_URL = url;

            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pDialog.setMessage("Requesting...");
            pDialog.show();

            super.onPreExecute();
        }

        @SuppressWarnings({"deprecation", "unchecked"})
        @Override
        protected String doInBackground(String... urls) {
            // placesAutoCompleteAdapter.notifyDataSetInvalidated();

            String SetServerString = "";
            HttpURLConnection conn = null;


            DefaultHttpClient Client = getNewHttpClient();
            HttpPost httpost = new HttpPost(JSON_URL);
            StringEntity se = null;
            try {
                se = new StringEntity(holder.toString());
            } catch (UnsupportedEncodingException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email_address",
                    email));
            try {
                httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            @SuppressWarnings("rawtypes")
            ResponseHandler responseHandler = new BasicResponseHandler();
            try {
                SetServerString = (String) Client.execute(httpost, responseHandler);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            return SetServerString;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            Log.e("AsyncForgetpassword ", result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                String status = jsonObject.optString("status");
                if (status.equalsIgnoreCase("true")) {
                    Alert.showAlertForgotPass(ForgotPassword.this, jsonObject.optString("message"), mCommonFunction);
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public DefaultHttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    com.dts.utils.Constants.CONNECTION_TIMED_OUT);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}
