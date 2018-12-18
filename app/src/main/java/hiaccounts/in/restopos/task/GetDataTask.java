package hiaccounts.in.restopos.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.utility.Constant;
import hiaccounts.in.restopos.utility.SharedPreference;
import hiaccounts.in.restopos.utility.UtilView;


/**
 * Created by Srinath on  22/09/18.
 */
public class GetDataTask extends AsyncTask<String, Void, String> {

    boolean dialogE = true;
    Activity activity;
    SharedPreference sharedPreference;
    private Context context;
    private AsyncTaskCompleteListener<String> listener;
    private ProgressDialog Dialog;


    public GetDataTask(Activity activity, AsyncTaskCompleteListener<String> listener, boolean dialog) {
        this.activity = activity;
        dialogE = dialog;
        this.listener = listener;
        sharedPreference = SharedPreference.getInstance(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        if (dialogE) {
            if (Dialog == null) {
                Dialog = new ProgressDialog(activity);
                Dialog.setMessage("Please Wait..");
                Dialog.setCancelable(false);
                Dialog.show();
            }

        }

    }

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JsonStr = null;
        try {

            String url = strings[0];
            String function_call = strings[1];

            UtilView.showLogCat("@Flow","Url Request "+url+" & Function Call : "+function_call);
            urlConnection = (HttpURLConnection) new URL(url).openConnection();


            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");

            if (!function_call.equals(Constant.FUNCTION_LOGIN)) {
                String accessToken=sharedPreference.getData(Constant.ACCESSTOKEN);
                if (accessToken.equals("")){
                    try {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put(Constant.ACCESSTOKEN,"invalid");
                        JsonStr=jsonObject.toString();

                    }catch (Exception e){

                    }
                    return JsonStr;
                }else {
                    urlConnection.setRequestProperty("Cookie", "accessToken="+accessToken+";");
                }
            }
            urlConnection.setUseCaches(true);
            urlConnection.connect();

            try {
                int responseCode = urlConnection.getResponseCode();
                Log.e("@Flow", "Response Code " + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    // Read the input stream into ic_category_leftview String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding ic_category_leftview newline isn't necessary (it won't affect parsing)
                        // But it does make debugging ic_category_leftview *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return activity.getString(R.string.error_rsonsecode204);
                    }
                    JsonStr = buffer.toString();
                    return JsonStr;
                } else {

                    return activity.getString(R.string.error_rsonsecode204);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();

                Log.e("@Flow", "PostDataTask doingBackground NullPointerException " + e.toString());
            }

        } catch (Exception e) {

        }
        return JsonStr;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (Dialog != null && Dialog.isShowing()) {
            Dialog.dismiss();
        }

        Log.e("@Flow ", "GetDataTask onPostExecute call result from server: " + result);
        if (result!=null && result.equals(Constant.SESSION_LOST)){
            UtilView.gotToLogin(activity);
        }else if (result!=null && result.equals(Constant.SESSION_INVALID)){
            UtilView.gotToLogin(activity);
        }else {
            listener.onTaskComplete(result);
        }
    }

}
