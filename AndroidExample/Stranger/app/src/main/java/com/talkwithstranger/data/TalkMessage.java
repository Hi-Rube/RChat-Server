package com.talkwithstranger.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rube on 14-8-17.
 */
public class TalkMessage {
    private int type;
    private String content;
    private String time;

    public TalkMessage(int type, String content){
        this.type = type;
        this.content = content;
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        this.time = df.format(new Date());
    }

    public int getType(){
        return this.type;
    }

    public String getContent(){
        return this.content;
    }

    public String getTime(){
        return this.time;
    }
}
