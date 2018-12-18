package hiaccounts.in.restopos.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Prateek on 21/12/16.
 */
public class SharedPreference {

    private static SharedPreference sharedPreference;
    private SharedPreferences sharedPreferences;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public static SharedPreference getInstance(Context context) {
        if (sharedPreference == null) {
            sharedPreference = new SharedPreference(context);
        }
        return sharedPreference;
    }


    public SharedPreference(Context context) {

        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);


        prefsEditor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        prefsEditor.commit();
    }

    public boolean isFirstTimeLaunch() {
        if (sharedPreferences!= null) {
            return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
        }
        return false;

    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void setEmptyPrefrence() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public void setRemovePrefrence(String key) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.remove(key);
        prefsEditor.remove("");
        prefsEditor.commit();
    }


    private static final int MAX_SIZE = 3;


    public SharedPreference() {
        super();
    }




    public static void storeList(Context context,String pref_name, String key, List countries) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(countries);
        editor.putString(key, jsonFavorites);
        editor.apply();
    }

    public static ArrayList<String> loadList(Context context, String pref_name, String key) {

        SharedPreferences settings;
        List<String> favorites;
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        if (settings.contains(key)) {
            String jsonFavorites = settings.getString(key, null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;
        return (ArrayList<String>) favorites;
    }

    public static void addList(Context context, String pref_name, String key,String country) {
        List<String> favorites = loadList(context, pref_name, key);
        if (favorites == null)
            favorites = new ArrayList<>();

        if(favorites.size() > MAX_SIZE) {
            favorites.clear();
            deleteList(context, pref_name);
        }

        if(favorites.contains(country)){

            favorites.remove(country);

        }
        favorites.add(country);

        storeList(context, pref_name, key, favorites);

    }

//    public static void removeList(Context context,String pref_name, String key, String country) {
//        ArrayList favorites = loadList(context, pref_name,key);
//        if (favorites != null) {
//            favorites.remove(country);
//            storeList(context, pref_name, key, favorites);
//        }
//    }


    public static void deleteList(Context context, String pref_name){

        SharedPreferences myPrefs = context.getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
    }






}