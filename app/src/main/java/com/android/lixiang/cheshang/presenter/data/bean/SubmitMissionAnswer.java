package com.android.lixiang.cheshang.presenter.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SubmitMissionAnswer {

    private String answer;
    private String hrAccount;
    private String quesId;
    private String shopId;

    public static SubmitMissionAnswer objectFromData(String str) {

        return new Gson().fromJson(str, SubmitMissionAnswer.class);
    }

    public static SubmitMissionAnswer objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), SubmitMissionAnswer.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SubmitMissionAnswer> arraySubmitMissionAnswerFromData(String str) {

        Type listType = new TypeToken<ArrayList<SubmitMissionAnswer>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SubmitMissionAnswer> arraySubmitMissionAnswerFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SubmitMissionAnswer>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHrAccount() {
        return hrAccount;
    }

    public void setHrAccount(String hrAccount) {
        this.hrAccount = hrAccount;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
