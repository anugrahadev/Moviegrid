package com.anugraha.project.moviegrid;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_MOVIESPOT = "spMovieSpot";
    public static final String SP_USERNAME = "spUSERNAME";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    public static final String SP_Region ="region";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_MOVIESPOT, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPSession(String session){
        spEditor.putString("session", session);
        spEditor.commit();
    }

    public String getSPSession(){
        return sp.getString("session", null);
    }

    public void setSpUsername(String username){
        spEditor.putString("username", username);
        spEditor.commit();
    }
    public String getSPUsername(){
        return sp.getString("username", null);
    }


    public void setSpName(String name){
        spEditor.putString("name", name);
        spEditor.commit();
    }
    public String getSPname(){
        return sp.getString("name", null);
    }

    public void setSpAccountID(int accountID){
        spEditor.putInt("accountID", accountID);
        spEditor.commit();
    }

    public int getSpAccountID(){
        return sp.getInt("accountID", 0);
    }

    public void setSpTVID(int tvid){
        spEditor.putInt("tvid", tvid);
        spEditor.commit();
    }

    public int getSpTVID(){
        return sp.getInt("tvid", 0);
    }


    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_USERNAME, "");
    }


    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public void setSpRegion(String region){
        spEditor.putString("region", region);
        spEditor.commit();
    }

    public String getSpRegion(){
        return sp.getString(SP_Region, "US");
    }

    public void setSpLang(String region){
        spEditor.putString("language", region);
        spEditor.commit();
    }

    public String getSpLang(){
        return sp.getString("language", "EN");
    }

    public void setSpImgQuality(String ImgQuality){
        spEditor.putString("imgquality", ImgQuality);
        spEditor.commit();
    }

    public String getImgQuality(){
        return sp.getString("imgquality", "Medium");
    }
}
