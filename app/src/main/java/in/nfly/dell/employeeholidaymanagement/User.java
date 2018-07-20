package in.nfly.dell.employeeholidaymanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;
    SharedPreferences sharedPreferences;
    private Integer H;
    private String appName;

    public Integer getH() {
        H=sharedPreferences.getInt("H",0);
        return H;
    }

    public void setH(Integer H) {
        this.H = H;
        sharedPreferences.edit().putInt("H",H);
        sharedPreferences.edit().apply();
    }

    public String getAppName() {
        appName=sharedPreferences.getString("appName","");
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
        sharedPreferences.edit().putString("appName",appName);
        sharedPreferences.edit().apply();
    }

    public User(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("Login Details",Context.MODE_PRIVATE);

    }
    public void logOut(){
        sharedPreferences.edit().clear().commit();
    }
}

