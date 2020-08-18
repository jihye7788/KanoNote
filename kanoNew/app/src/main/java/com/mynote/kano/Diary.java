package com.mynote.kano;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Diary {
    public String userId;
    public String diaryDate;
    public String dContent;

    public Diary(){

    }

    public Diary(String userId, String diaryDate, String dContent){
        this.userId=userId;
        this.diaryDate=diaryDate;
        this.dContent=dContent;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId",userId);
        result.put("diaryDate",diaryDate);
        result.put("dContent",dContent);
        return result;
    }

    public String getdContent() {
        return dContent;
    }

    public void setdContent(String dContent) {
        this.dContent = dContent;
    }

    public String getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(String diaryDate) {
        this.diaryDate = diaryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "userId='" + userId + '\'' +
                ", diaryDate='" + diaryDate + '\'' +
                ", dContent='" + dContent + '\'' +
                '}';
    }
}
