package com.example.thecopy.app;

import android.app.Application;
import android.content.Context;

import com.example.thecopy.data.DataBaseHelper;
import com.example.thecopy.data.DatabaseManager;

public class App extends Application {
    private static Context context;
    private static DataBaseHelper dataBaseHelper;

    @Override
    public void onCreate(){
        super.onCreate();
        context = this.getApplicationContext();
        dataBaseHelper = new DataBaseHelper();
        DatabaseManager.initializedInstance(dataBaseHelper);
    }

    public static Context getContext(){
        return context;
    }
}
