package com.android.lixiang.cheshang.presenter.data.greenDao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by vslimit on 17/1/21.
 */
@Entity
public class Record {
    @Id
    private Long id;
    private String detail;
    private String time;
    private String remind;
    private String realTime;
    private String isToday;
    @Generated(hash = 659469020)
    public Record(Long id, String detail, String time, String remind,
            String realTime, String isToday) {
        this.id = id;
        this.detail = detail;
        this.time = time;
        this.remind = remind;
        this.realTime = realTime;
        this.isToday = isToday;
    }
    @Generated(hash = 477726293)
    public Record() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDetail() {
        return this.detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getRemind() {
        return this.remind;
    }
    public void setRemind(String remind) {
        this.remind = remind;
    }
    public String getRealTime() {
        return this.realTime;
    }
    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }
    public String getIsToday() {
        return this.isToday;
    }
    public void setIsToday(String isToday) {
        this.isToday = isToday;
    }

}
