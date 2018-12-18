package hiaccounts.in.restopos.application.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.model.ConfigSplash;
import hiaccounts.in.restopos.utility.SharedPreference;

public class Activity_Splash extends AwesomeSplash  {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreference sharedPreference;


    @Override
    public void initSplash(ConfigSplash configSplash) {
    }

    @Override
    public void animationsFinished() {
        //wait 5 sec and then go back to MainActivity
        final Activity a = this;
        animationsFinishedData();
    }

    private void animationsFinishedData() {

        sharedPreference = SharedPreference.getInstance(Activity_Splash.this);

        if (sharedPreference.isFirstTimeLaunch()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Activity_Splash.this, Activity_Login.class);
                    startActivity(mainIntent);


                    //sharedPreference.setFirstTimeLaunch(false);
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }
}
