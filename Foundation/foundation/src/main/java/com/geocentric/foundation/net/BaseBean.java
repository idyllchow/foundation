package com.geocentric.foundation.net;

import android.os.Parcel;
import android.os.Parcelable;

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
        @Override
        public BaseBean createFromParcel(Parcel source) {
            return new BaseBean(source);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }
    };
}