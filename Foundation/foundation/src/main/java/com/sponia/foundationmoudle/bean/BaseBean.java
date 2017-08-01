package com.sponia.foundation.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @packageName com.sponia.foundation.bean
 * @description 基础bean
 * @date 15/9/7
 * @auther shibo
 */
public class BaseBean implements Parcelable {
    public String message;
    public int error_code;


    public BaseBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.error_code);
    }

    protected BaseBean(Parcel in) {
        this.message = in.readString();
        this.error_code = in.readInt();
    }

    public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        public BaseBean createFromParcel(Parcel source) {
            return new BaseBean(source);
        }

        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }
    };
}