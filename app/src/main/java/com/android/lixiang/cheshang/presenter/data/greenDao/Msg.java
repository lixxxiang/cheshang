package com.android.lixiang.cheshang.presenter.data.greenDao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by vslimit on 17/1/21.
 */
@Entity
public class Msg {
    @Id
    private Long id;
    private String name;
    private String detail;
    private String time;
    private String isRead;
    private String msgId;
    @Generated(hash = 2001981746)
    public Msg(Long id, String name, String detail, String time, String isRead,
            String msgId) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.time = time;
        this.isRead = isRead;
        this.msgId = msgId;
    }
    @Generated(hash = 23037457)
    public Msg() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getIsRead() {
        return this.isRead;
    }
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
    public String getMsgId() {
        return this.msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
