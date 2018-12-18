package hiaccounts.in.restopos.application.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder()
                        .name("realm-hyva-india.db")
                        .schemaVersion(1)
                        .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
