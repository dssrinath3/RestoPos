package hiaccounts.in.restopos.jobschedular.schedulertask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import hiaccounts.in.restopos.utility.UtilView;


/**
 * Created by Srinath on 6/11/2018.
 */
public class SchedulerPostTask extends AsyncTask<String, String, String> {

    public static final String TAG = SchedulerPostTask.class.getSimpleName();


    public static String accessToken = "eyJwYXNzd29yZCI6IjBhMjJhYmEzMjAyNmUxODU5NjhlMmRhZmE3NjA5ZjQ5IiwiZGF0YWJhc2UiOiJkZW1vODA5MDE3MTI4IiwiY29tcGFueUVtYWlsIjoiZGVtb0BoaWFjY291bnRzLmluIiwiY3VycmVudFRpbWVNaWxsaXMiOjE1NDE1MDM4OTIxOTEsInVzZXJOYW1lIjoic3JpMSJ9";


    private AsyncTaskCompleteListener<String> listener;

    public SchedulerPostTask(AsyncTaskCompleteListener<String> listener) {
        this.listener = listener;

    }

    @Override
    protected String doInBackground(String... strings) {
        String JsonResponse = null;
        String JsonDATA = strings[0];

        UtilView.showLogCat(TAG, "json data for post " + JsonDATA);

        String uri = strings[1];
        UtilView.showLogCat(TAG, "url for post " + uri);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            //set headers and method

            Log.e(TAG, "Cookie " + accessToken);
            urlConnection.setRequestProperty("Cookie", "accessToken=" + accessToken + ";");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
            writer.close();


            // json data

            try {
                int responseCode = urlConnection.getResponseCode();
                Log.e(TAG, "Response Code " + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    InputStream inputStream = urlConnection.getInputStream();
                    //input stream
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String inputLine;
                    while ((inputLine = reader.readLine()) != null)
                        buffer.append(inputLine + "\n");
                    if (buffer.length() == 0) {
                        // Stream was empty. No point in parsing.

                        return null;
                    }
                    JsonResponse = buffer.toString();
                    return JsonResponse;
                } else {
                    return null;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                Log.e(TAG, "PostDataTask doingBackground NullPointerException " + e.toString());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "PostDataTask doingBackground Exception " + e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "PostDataTask Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        Log.e(TAG, "PostDataTask onPostExecute call result from server: " + result);
        listener.onTaskComplete(result);
    }

}