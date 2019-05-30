package com.android.lixiang.cheshang.presenter.data.greenDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Answer {
    @Id
    private String id;
    private String questionId;
    private String answer;

//    @Generated(hash = 1036185905)
//    public Answer(String id, String questionId, String answer) {
//        this.id = id;
//        this.questionId = questionId;
//        this.answer = answer;
//    }
//    @Generated(hash = 53889439)
//    public Answer() {
//    }
//    public Long getId() {
//        return this.id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public String getQuestionId() {
//        return this.questionId;
//    }
//    public void setQuestionId(String questionId) {
//        this.questionId = questionId;
//    }
//    public String getAnswer() {
//        return this.answer;
//    }
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }
    @Generated(hash = 1653679156)
    public Answer(String id, String questionId, String answer) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
    }
    @Generated(hash = 53889439)
    public Answer() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getQuestionId() {
        return this.questionId;
    }
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
