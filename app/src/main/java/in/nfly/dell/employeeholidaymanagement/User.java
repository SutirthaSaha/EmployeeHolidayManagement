package in.nfly.dell.employeeholidaymanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;
    SharedPreferences sharedPreferences;
    private Float H;
    private String appName;

    public Float getH() {
        H=sharedPreferences.getFloat("H",0);
        return H;
    }

    public void setH(Float H) {
        this.H = H;
        sharedPreferences.edit().putFloat("H",H);
        sharedPreferences.edit().apply();
    }

    public String getAppName() {
        appName=sharedPreferences.getString("appName","HR Manager");
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
        sharedPreferences.edit().putString("appName",appName);
        sharedPreferences.edit().apply();
    }

    public User(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("App Details",Context.MODE_PRIVATE);

    }
    public void logOut(){
        sharedPreferences.edit().clear().commit();
    }
}

