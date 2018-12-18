package hiaccounts.in.restopos.utility;


import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;

import hiaccounts.in.restopos.R;


/*
* Created by Srinath on  22/09/18.
* */
public class Validation {

    public static boolean isValidInptLayoutEmail(Activity activity, TextInputLayout inputLayoutEmail, String email) {
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(activity.getString(R.string.err_msg_email));
            return false;
        } else {

           // inputLayoutEmail.setError(null);

        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidInptLayoutPassword(Activity activity, TextInputLayout inputLayoutPassword, String password) {
        if (password.isEmpty()) {
            inputLayoutPassword.setError(activity.getString(R.string.err_msg_password));
            return false;
        } else {

          }
        return true;
    }
    public static boolean isValidInptLayoutUsername(Activity activity, TextInputLayout inputLayoutUsername, String username) {
        if (username.isEmpty()) {
            inputLayoutUsername.setError(activity.getString(R.string.err_msg_username));
            return false;
        } else {

        }
        return true;
    }
    public static boolean isValidInptLayoutNumber(Activity activity, TextInputLayout inputLayoutNumber, String number) {
        if (number.isEmpty() || !isValidNumber(number) || number.length()!=10) {
            inputLayoutNumber.setError(activity.getString(R.string.err_msg_number));
            return false;
        } else {


        }
        return true;

    }

    public static boolean isValidNumber(String number) {
        return !TextUtils.isEmpty(number) && Patterns.PHONE.matcher(number).matches();
    }
    public static boolean isValidInptLayout(Activity activity, TextInputLayout inputLayout, String data) {
        if (data.isEmpty()) {
            inputLayout.setError(activity.getString(R.string.err_msg));
            return false;
        } else {

        }
        return true;
    }
}
