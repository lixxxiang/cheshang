package com.android.lixiang.cheshang.presenter.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetMissionListBean implements Serializable, Parcelable {

    private String message;
    private int status;
    private ArrayList<DataBean> data;

    protected GetMissionListBean(Parcel in) {
        message = in.readString();
        status = in.readInt();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<GetMissionListBean> CREATOR = new Creator<GetMissionListBean>() {
        @Override
        public GetMissionListBean createFromParcel(Parcel in) {
            return new GetMissionListBean(in);
        }

        @Override
        public GetMissionListBean[] newArray(int size) {
            return new GetMissionListBean[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(status);
        parcel.writeString(message);
        parcel.writeList(data);
    }

    public static GetMissionListBean objectFromData(String str) {

        return new Gson().fromJson(str, GetMissionListBean.class);
    }

    public static GetMissionListBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), GetMissionListBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GetMissionListBean> arrayGetMissionListBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<GetMissionListBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<GetMissionListBean> arrayGetMissionListBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GetMissionListBean>>() {
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

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public static class DataBean implements Serializable, Parcelable {
        private int finish;
        private String missionId;
        private String name;
        private int quesAmount;
        private ArrayList<QuestionListBean> questionList;

        protected DataBean(Parcel in) {
            finish = in.readInt();
            missionId = in.readString();
            name = in.readString();
            quesAmount = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(missionId);
            dest.writeString(name);
            dest.writeInt(finish);
            dest.writeInt(quesAmount);
            dest.writeList(questionList);
        }
        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DataBean> arrayDataBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        public String getMissionId() {
            return missionId;
        }

        public void setMissionId(String missionId) {
            this.missionId = missionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuesAmount() {
            return quesAmount;
        }

        public void setQuesAmount(int quesAmount) {
            this.quesAmount = quesAmount;
        }

        public ArrayList<QuestionListBean> getQuestionList() {
            return questionList;
        }

        public void setQuestionList(ArrayList<QuestionListBean> questionList) {
            this.questionList = questionList;
        }



        public static class QuestionListBean implements Serializable, Parcelable{
            private int id;
            private String option;
            private String question;
            private String quesId;

            private int type;
            private int required;

            protected QuestionListBean(Parcel in) {
                id = in.readInt();
                option = in.readString();
                question = in.readString();
                type = in.readInt();
                quesId = in.readString();
                required = in.readInt();
            }

            public static final Creator<QuestionListBean> CREATOR = new Creator<QuestionListBean>() {
                @Override
                public QuestionListBean createFromParcel(Parcel in) {
                    return new QuestionListBean(in);
                }

                @Override
                public QuestionListBean[] newArray(int size) {
                    return new QuestionListBean[size];
                }
            };

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(question);
                parcel.writeString(option);
                parcel.writeInt(id);
                parcel.writeInt(type);
                parcel.writeInt(required);
                parcel.writeString(quesId);

            }
            public static QuestionListBean objectFromData(String str) {

                return new Gson().fromJson(str, QuestionListBean.class);
            }

            public static QuestionListBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), QuestionListBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<QuestionListBean> arrayQuestionListBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<QuestionListBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<QuestionListBean> arrayQuestionListBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<QuestionListBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getquesId() {
                return quesId;
            }

            public void setquesId(String quesId) {
                this.quesId = quesId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOption() {
                return option;
            }

            public void setOption(String option) {
                this.option = option;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public int getRequired() {
                return required;
            }

            public void setRequired(int required) {
                this.required = required;
            }
        }
    }
}
