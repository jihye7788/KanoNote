package com.mynote.kano;

import android.os.Parcel;
import android.os.Parcelable;

public class diaryDate implements Parcelable {
    String diaryDate;

    public diaryDate(String pickedDate){
        diaryDate = pickedDate;
    }

    //Parcel 객체에서 읽기
    public diaryDate(Parcel src){
        diaryDate=src.readString();
    }

    //Creator 상수 정의
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        //diaryDate 생성자를 호출해 Parcel객체에서 읽기
        public diaryDate createFromParcel(Parcel in){
            return new diaryDate(in);
        }
        public diaryDate[] newArray(int size){
        return new diaryDate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //Parcel  객체로 쓰기
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(diaryDate);
    }
}
