package com.example.mealmanagement.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.mealmanagement.model.UserInfo;

public class PreferenceConnector {
    public static final String PREF_NAME = "CHAT_PREF";
    public static final int MODE = Context.MODE_PRIVATE;


    private long Id;
    private String status;
    private String creationDate;
    private String name;
    private String emailID;
    private String socialID;
    private String gcmRegID;
    private String profileImage;
    private String examDate;
    private int expBandScore;
    private int englishLevel;
    private int lookingForEnLevel;
    private int examModule;


    public static void saveUser(Context context, UserInfo userInfo) {

        writeLong(context, "id", userInfo.getId());
        writeString(context, "name", userInfo.getName());
        writeString(context, "mail", userInfo.getMail());
        writeString(context, "password", userInfo.getPassword());
        writeString(context, "mess_name", userInfo.getMess_name());
        writeInteger(context, "manager", userInfo.getManager());
        writeInteger(context, "approve", userInfo.getApprove());



    }


    public static boolean isIDExits(Context context) {


        long id = readLong(context, "Id", 0);

        if (id > 0) {
            return true;
        }


        return false;
    }


    public static String getStatus(Context context) {


        String status = readString(context, "status", "");


        return status;
    }


    public static UserInfo getUser(Context context) {

        UserInfo userInfo1 = new UserInfo();

        userInfo1.setId(readLong(context, "id", 0));
        userInfo1.setName(readString(context, "name", ""));
        userInfo1.setMail(readString(context, "mail", ""));
        userInfo1.setName(readString(context, "name", ""));
        userInfo1.setPassword(readString(context, "password", ""));
        userInfo1.setMess_name(readString(context, "mess_name", ""));
        userInfo1.setManager(readInteger(context, "manager", 0));
        userInfo1.setApprove(readInteger(context, "approve", 0));

        return userInfo1;


    }



    public static void setID(Context context,int examDate) {


        writeLong(context, "Id", examDate);


    }


    public static void setExamDate(Context context, String examDate) {


        writeString(context, "examDate", examDate);


    }


    public static void setExpBandScore(Context context, int examDate) {


        writeInteger(context, "expBandScore", examDate);


    }


    public static void setEnglishLevel(Context context, int examDate) {


        writeInteger(context, "englishLevel", examDate);


    }


    public static void setExamModule(Context context, int examDate) {


        writeInteger(context, "examModule", examDate);


    }


    public static void setLookingForEngLevel(Context context, int examDate) {


        writeInteger(context, "lookingForEnLevel", examDate);


    }


    public static long getID(Context context) {


        long status = readLong(context, "Id", 0);


        return status;
    }


    public static String getCreationdate(Context context) {


        String status = readString(context, "creationDate", "");


        return status;
    }

    public static String getName(Context context) {


        String status = readString(context, "name", "");


        return status;
    }

    public static String getemailID(Context context) {


        String status = readString(context, "emailID", "");


        return status;
    }

    public static String getSocialID(Context context) {


        String status = readString(context, "socialID", "");


        return status;
    }

    public static String getGCMID(Context context) {


        String status = readString(context, "gcmRegID", "");


        return status;
    }

    public static String getProfileImage(Context context) {


        String status = readString(context, "profileImage", "");


        return status;
    }


    public static String getExamDate(Context context) {


        String status = readString(context, "examDate", "");


        return status;
    }


    public static int getExpBandScore(Context context) {


        int status = readInteger(context, "expBandScore", 0);


        return status;
    }


    public static int getEnglishLevel(Context context) {


        int status = readInteger(context, "englishLevel", 0);


        return status;
    }

    public static int getLookingForEngLevel(Context context) {


        int status = readInteger(context, "lookingForEnLevel", 0);


        return status;
    }

    public static int getExamModule(Context context) {


        int status = readInteger(context, "examModule", 0);


        return status;
    }


    //public static final String ISLOGIN = "ISLOGIN";
    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }
}
