package hiaccounts.in.restopos.application.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.model.CompanyData;
import hiaccounts.in.restopos.application.model.LoginDetail;
import hiaccounts.in.restopos.application.model.pageloaddata.ItemCategory;
import hiaccounts.in.restopos.application.model.pageloaddata.RestOnPageLoadData;
import hiaccounts.in.restopos.application.realm_model.utils.DataGenerator;
import hiaccounts.in.restopos.application.realmdbmanager.RealmManager;
import hiaccounts.in.restopos.jobschedular.schedulertask.SchedulerGetTask;
import hiaccounts.in.restopos.jobschedular.schedulertask.SchedulerPostTask;
import hiaccounts.in.restopos.task.AsyncTaskCompleteListener;
import hiaccounts.in.restopos.task.GetDataTask;
import hiaccounts.in.restopos.task.PostDataTask;
import hiaccounts.in.restopos.utility.Constant;
import hiaccounts.in.restopos.utility.ServiceHandler;
import hiaccounts.in.restopos.utility.SharedPreference;
import hiaccounts.in.restopos.utility.UtilView;
import hiaccounts.in.restopos.utility.Validation;

public class Activity_Login extends AppCompatActivity {
    public static String TAG = Activity_Login.class.getSimpleName();
    @BindView(R.id.input_baseUrl)
    EditText inputBaseUrl;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.btn_signup)
    AppCompatButton btnSignup;
    @BindView(R.id.link_forgot_password)
    TextView linkForgotPassword;
    @BindView(R.id.id_rememberme)
    CheckBox idRememberme;
    @BindView(R.id.progress_bar)
    ProgressView progressBar;
    private Activity activity;
    private ServiceHandler serviceHandler;
    private String serverUrl ,companyRegNo="",userId="",accessToken="";
    private SharedPreference sharedPreference;
    private Boolean isInternetPresent = false;
    private String callingFrom = "";
    boolean pageloaddata =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initComponent();
    }

    private void initComponent() {
        activity = this;
        serverUrl = UtilView.getUrl(activity);
        serviceHandler = new ServiceHandler(activity);
        sharedPreference = SharedPreference.getInstance(activity);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        RealmManager.open();

        UtilView.showLogCat("@Flow", "relam " + RealmManager.open().getPath());

        Intent intent = getIntent();

        rememberDetails();


        if (intent != null) {
            callingFrom = intent.getStringExtra("callingFrom");
        }
      /*  inputEmail.setText("shwethabaradwaj1808@gmail.com");
        inputUsername.setText("sri");
        inputPassword.setText("sri");
        inputBaseUrl.setText("http://98c97f62.ngrok.io");*/
      /*  inputEmail.setText("demo@hiaccounts.in");
        inputUsername.setText("sri1");
        inputPassword.setText("sri1");
        inputBaseUrl.setText("http://cloud.hiaccounts.com:8090");*/

    }

    @OnClick({R.id.btn_login, R.id.btn_signup, R.id.link_forgot_password, R.id.id_rememberme})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                callLogin();
                break;
            case R.id.btn_signup:
                callSignup();
                break;
            case R.id.link_forgot_password:
                break;
            case R.id.id_rememberme:
                break;
        }
    }

    private void callSignup() {
        isInternetPresent = serviceHandler.isConnectingToInternet();
        if (isInternetPresent) {
            Intent intent = new Intent(activity, Activity_SignUp.class);
            startActivity(intent);
        } else {
            Toast.makeText(activity, getResources().getString(R.string.intertnet_status), Toast.LENGTH_SHORT).show();

        }
    }

    private void callLogin() {
        isInternetPresent = serviceHandler.isConnectingToInternet();
        String compnayEmail = inputEmail.getText().toString().trim();
        String userName = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String urlPath = inputBaseUrl.getText().toString().trim();

        if (!urlPath.equals("") && !compnayEmail.equals("") && !userName.equals("") && !password.equals("")) {

            if (isInternetPresent) {
                Constant.BASE_URL = urlPath;

                if (Validation.isValidEmail(compnayEmail)) {
                    callLoginUrl(urlPath, compnayEmail, userName, password);
                } else {
                    inputBaseUrl.setError(getResources().getString(R.string.err_msg_email));
                }


            } else {
                Toast.makeText(activity, getResources().getString(R.string.intertnet_status), Toast.LENGTH_SHORT).show();

            }
        } else {
            if (compnayEmail.equals("")) {

                inputEmail.setError(getResources().getString(R.string.err_msg_companyemail));
            }
            if (userName.equals("")) {
                inputUsername.setError(getResources().getString(R.string.err_msg_username));
            }
            if (password.equals("")) {
                inputPassword.setError(getResources().getString(R.string.err_msg_password));
            }
        }
    }

    private void callLoginUrl(String urlPath, String compnayEmail, String userName, String password) {
        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put(Constant.COMPANYEMAIL, compnayEmail);


            requestJson.put(Constant.USERNAME, userName);
            requestJson.put(Constant.PASSWORD, password);
            requestJson.put("urlpath", urlPath);
            sharedPreference.saveData(Constant.USERNAME, userName);
            if (idRememberme.isChecked()) {
                sharedPreference.saveData(Constant.REMEMBERLOGINDETAILS, requestJson.toString());

            } else {
                sharedPreference.setRemovePrefrence(Constant.REMEMBERLOGINDETAILS);
            }


            UtilView.showProgessBar(this, progressBar);
            PostDataTask postDataTask = new PostDataTask(activity, new TaskCompleteListener(), false);
            postDataTask.execute(requestJson.toString(), Constant.BASE_URL + "/hipos/login", Constant.FUNCTION_LOGIN);


        } catch (Exception e) {
            UtilView.showLogCat(TAG, "callLoginUrl exception " + e.toString());

        }
    }


    private class TaskCompleteListener implements AsyncTaskCompleteListener<String> {
        @Override
        public void onTaskComplete(String result) {
            UtilView.hideProgessBar(Activity_Login.this, progressBar);
            try{
                LoginDetail loginDetail = new LoginDetail();
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.has("companyRegNo")){
                    companyRegNo= jsonObject.optString("companyRegNo","companyRegNo");
                }
                accessToken= jsonObject.getString("accessToken");
                userId= jsonObject.getString("userId");
                loginDetail.setAuthToken(accessToken);
                if (companyRegNo!=null && !companyRegNo.equals("")){
                    loginDetail.setCompanyRegNo(companyRegNo);
                }else{
                    loginDetail.setCompanyRegNo("");
                }
                if (userId != null && userId.equals("")) {
                    loginDetail.setUserId(userId);
                }else{
                    loginDetail.setUserId("");
                }

                loginDetail.setRedirecturli(Constant.BASE_URL);
                sharedPreference.saveData(Constant.LOGINDETAIL, new Gson().toJson(loginDetail));
                sharedPreference.saveData(Constant.ACCESSTOKEN, accessToken);
                sharedPreference.saveData(Constant.SERVER_URL, Constant.BASE_URL);

                SchedulerPostTask.accessToken = accessToken;
                SchedulerGetTask.accessToken = accessToken;



                UtilView.showProgessBar(Activity_Login.this, progressBar);

                GetDataTask task = new GetDataTask(activity, new AsyncTaskCompleteListener<String>() {
                    @Override
                    public void onTaskComplete(String result) {
                        UtilView.hideProgessBar(activity, progressBar);
                        if (result != null) {
                            try {


                                JSONObject jsonObject = new JSONObject(result.toString());


                                String pageNumber=jsonObject.getString("pageName");
                                sharedPreference.saveData(Constant.NAVIGATION_REDIRECTPAGE,pageNumber);

                                if (pageNumber!=null){
                                    if (pageNumber.equals(Constant.PAGE_DAHSBOARD)){

                                        GetDataTask task = new GetDataTask(activity, new AsyncTaskCompleteListener<String>() {
                                            @Override
                                            public void onTaskComplete(String result) {
                                                UtilView.hideProgessBar(activity, progressBar);
                                                if (result != null) {
                                                    try {

                                                        JSONObject jsonObject = new JSONObject(result.toString());
                                                        Gson gson = new Gson();
                                                        CompanyData companyData = gson.fromJson(jsonObject.toString(), CompanyData.class);
                                                        if (companyData != null) {
                                                            sharedPreference.saveData(Constant.COMPANYDATA, gson.toJson(companyData));


                                                            getPageLoadData();


                                                        } else {
                                                            Toast.makeText(activity, getResources().getString(R.string.intertnet_status), Toast.LENGTH_SHORT).show();
                                                        }


                                                    } catch (Exception e) {
                                                        Toast.makeText(activity, getResources().getString(R.string.intertnet_status), Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            }
                                        }, false);
                                        task.execute(Constant.BASE_URL + Constant.FUNTION_GETCOMPANYDATA, "");



                                    }
                                }



                            } catch (JSONException e) {
                                Toast.makeText(activity, getResources().getString(R.string.intertnet_status), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }, false);
                task.execute(Constant.BASE_URL+ Constant.FUNTION_GETCOMPANYSTATUS, "");
            }catch (Exception e){

            }


        }
    }

    private void getPageLoadData() {

        isInternetPresent = serviceHandler.isConnectingToInternet();
        if (serverUrl != null) {
            if (isInternetPresent) {
                UtilView.showProgessBar(activity, progressBar);
                GetDataTask getDataTask = new GetDataTask(activity, new AsyncTaskCompleteListener<String>() {
                    @Override
                    public void onTaskComplete(String result) {
                        UtilView.hideProgessBar(activity, progressBar);
                        if (result != null) {
                            if (result.equals("invalid")) {
                                UtilView.showToast(activity, getResources().getString(R.string.accesstoken_error));
                                Intent intent = new Intent(activity, Activity_Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                try {

                                    Gson gson = new Gson();
                                    RestOnPageLoadData pageData = gson.fromJson(result.toString(), RestOnPageLoadData.class);
                                    if (pageData!=null){
                                        for (ItemCategory category : pageData.getItemCategorys()){
                                            RealmManager.createRestaurentDao().saveRestuarentCategoryData(DataGenerator.generateRestaCategoryItem(category),category.getItemCategoryName());

                                        }
                                    }
                                    //sharedPreference.saveData(Constant.HINEXTRESTUARANTDATA_KEY, new Gson().toJson(pageData));

                                        Intent intent = new Intent(activity, Activity_Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                } catch (Exception e) {
                                    Log.e("exception ", e.toString());
                                }
                            }

                        } else {
                            Toast.makeText(activity, ""+getResources().getString(R.string.response_error), Toast.LENGTH_SHORT).show();

                        }
                    }
                }, false);
                getDataTask.execute(Constant.BASE_URL + "/restaurant/" + Constant.FUNTION_RESTRAGETPAGELOADDATA , "");
            } else {
                Toast.makeText(activity, ""+getResources().getString(R.string.intertnet_status), Toast.LENGTH_SHORT).show();

            }
        } else {
            UtilView.gotToLogin(activity);
        }
    }

    private void rememberDetails() {
        if (sharedPreference!=null) {

            String loginDetailData = sharedPreference.getData(Constant.REMEMBERLOGINDETAILS);

            if (loginDetailData != null) {

                try {
                    JSONObject jsonData = new JSONObject(loginDetailData.toString());
                    String url          = jsonData.getString("urlpath");
                    String email        = jsonData.getString("companyEmail");
                    String username     = jsonData.getString("userName");
                    String password     = jsonData.getString("password");

                    if (url !=null && email !=null && username !=null && password !=null )
                    {
                        idRememberme.setChecked(true);
                        inputBaseUrl.setText(url.toString());
                        inputEmail.setText(email.toString());
                        inputUsername.setText(username.toString());
                        inputPassword.setText(password.toString());
                    }
                    else {
                        idRememberme.setChecked(false);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                inputBaseUrl.setText("");
                inputEmail.setText("");
                inputUsername.setText("");
                inputPassword.setText("");
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (callingFrom != null && !callingFrom.equals("")) {
            if (callingFrom.equals("ServerConfig")) {
                finish();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            System.exit(0);
        }

    }
}
