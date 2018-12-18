package hiaccounts.in.restopos.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.activity.Activity_Login;



/**
 * Created by Srinath on  22/09/18.
 */
public class UtilView {

    public static final String PREFS_NAME = "ITEMNAMESEARCH";

    public static final String KEY_ITEMNAME = "ItemName";

    public static final int REQUEST_CODE = 1234;

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public static ProgressDialog showProgressDialog(Activity context) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait ...");

        return progressDialog;
    }

    public static void showToast(Activity context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLogCat(String tag, String message) {
        Log.e("@Flow " + tag + ": ", message);
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        Context c = activity.getBaseContext();
        View v = view.findFocus();
        if (v == null)
            return;
        InputMethodManager inputManager = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static void gotToLogin(Activity mActivity) {


        UtilView.showToast(mActivity,"Session Expired. Please Login again.");
        SharedPreference sharedPreference=SharedPreference.getInstance(mActivity);


        sharedPreference.setRemovePrefrence(Constant.LOGINDATA_KEY);
        sharedPreference.setRemovePrefrence(Constant.LOGINDETAIL);
        sharedPreference.setRemovePrefrence(Constant.ACCESSTOKEN);
        sharedPreference.setRemovePrefrence(Constant.USERNAME);
        sharedPreference.setRemovePrefrence(Constant.SERVER_URL);
        Intent intent = new Intent(mActivity, Activity_Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);
        //mActivity.finish();


    }

    public static void showProgessBar(Activity mActivity, ProgressView progressBar) {
        if (progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public static void hideProgessBar(Activity mActivity, ProgressView progressBar) {
        if (progressBar!=null){
            progressBar.setVisibility(View.GONE);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    public static String setCurrentDate(EditText edDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        f.setTimeZone(utc);
        edDate.setText(String.valueOf(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year)));
        return f.format(calendar.getTime());
    }


    public static String getUrl(Activity mActivity){
        SharedPreference sharedPreference=SharedPreference.getInstance(mActivity);
        String base_url= sharedPreference.getData(Constant.SERVER_URL);
        return base_url;
    }






}