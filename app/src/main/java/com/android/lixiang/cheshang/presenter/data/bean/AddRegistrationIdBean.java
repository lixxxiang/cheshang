package com.android.lixiang.cheshang.presenter.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddRegistrationIdBean {

    /**
     * message : success
     * status : 200
     */

    private String message;
    private int status;

    public static AddRegistrationIdBean objectFromData(String str) {

        return new Gson().fromJson(str, AddRegistrationIdBean.class);
    }

    public static AddRegistrationIdBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), AddRegistrationIdBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<AddRegistrationIdBean> arrayAddRegistrationIdBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AddRegistrationIdBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<AddRegistrationIdBean> arrayAddRegistrationIdBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<AddRegistrationIdBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
