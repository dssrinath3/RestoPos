package hiaccounts.in.restopos.jobschedular.schedulertask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Srinath on 6/11/2018.
 */
public class SchedulerGetTask extends AsyncTask<String, Void, String> {

    public static String accessToken = "";

    private AsyncTaskCompleteListener<String> listener;

    public SchedulerGetTask(AsyncTaskCompleteListener<String> listener) {
        this.listener = listener;

    }


    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JsonStr = null;
        try {
            String url = strings[0];

            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Cookie", "accessToken=" + accessToken + ";");
            urlConnection.setUseCaches(true);
            urlConnection.connect();

            try {
                int responseCode = urlConnection.getResponseCode();
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
                        return "No Response Body";
                    }
                    JsonStr = buffer.toString();
                    return JsonStr;
                } else {
                    JsonStr = null;
                    return JsonStr;
                }
            } catch (NullPointerException e) {
                JsonStr = null;
                return JsonStr;

            }

        } catch (Exception e) {
            JsonStr = null;
            return JsonStr;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


        if (result!=null){
            Log.e("@Flow", "Response Code " + result);
            listener.onTaskComplete(result);
        }



    }

}