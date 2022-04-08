package com.example.thecopy.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.thecopy.app.App;
import com.example.thecopy.data.model.Competitions;
import com.example.thecopy.data.model.MatchInfo;
import com.example.thecopy.data.model.Matches;
import com.example.thecopy.data.model.PitData;
import com.example.thecopy.data.model.Stats;
import com.example.thecopy.data.model.Teams;
import com.example.thecopy.data.repo.CompetitionsRepo;
import com.example.thecopy.data.repo.MatchInfoRepo;
import com.example.thecopy.data.repo.MatchesRepo;
import com.example.thecopy.data.repo.PitDataRepo;
import com.example.thecopy.data.repo.StatsRepo;
import com.example.thecopy.data.repo.TeamsRepo;

public class DataBaseHelper extends SQLiteOpenHelper {

    //update when making any changes to tables or indexes
    private static final int DATABASE_VERSION = 42;
    private static final String DATABASE_NAME = "ScoutingData.db";
    private static final String TAG = DataBaseHelper.class.getSimpleName();

    public DataBaseHelper(){
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //creates all tables using the string from each of the repo
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CompetitionsRepo.createTable());
        db.execSQL(TeamsRepo.createTable());
        db.execSQL(MatchesRepo.createTable());
        db.execSQL(MatchesRepo.createIndex());
        db.execSQL(StatsRepo.createTable());
        db.execSQL(StatsRepo.createIndex());
        db.execSQL(MatchInfoRepo.createTable());
        db.execSQL(MatchInfoRepo.createIndex());
        db.execSQL(PitDataRepo.createTable());
    }

    @Override
    //drops all tables if there is any change to the database version
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        db.execSQL("Drop Table if Exists "+ Competitions.TABLE);
        db.execSQL("Drop Table if Exists "+ Matches.TABLE);
        db.execSQL("Drop Table if Exists "+ Teams.TABLE);
        db.execSQL("Drop Table if Exists "+ Stats.TABLE);
        db.execSQL("Drop Table if Exists "+ MatchInfo.TABLE);
        db.execSQL("Drop Table if Exists "+ PitData.TABLE );
        onCreate(db);
    }


}
