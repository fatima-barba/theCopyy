package com.example.thecopy.data.model;

public class Teams {

    public static final String TABLE = "Teams";
    public static final String KEY_TeamNum = "TeamNumber";
    public static final String KEY_TeamName = "TeamName";

    private int teamNum;
    private String teamName;

    public Teams(){
        teamName = "Default Team";
        teamNum = 0;
    }

    public int getTeamNum(){
        return teamNum;
    }

    public void setTeamNum(int i){
        teamNum = i;
    }

    public String getTeamName(){
        return teamName;
    }

    public void setTeamName(String s){
        teamName = s;
    }
}
