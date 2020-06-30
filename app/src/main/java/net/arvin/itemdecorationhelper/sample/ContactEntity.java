package net.arvin.itemdecorationhelper.sample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arvinljw on 2018/7/25 13:31
 * Function：
 * Desc：
 */
public class ContactEntity implements Parcelable {
    private String name;
    private String pinyinName;
    private String letter;

    public ContactEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.pinyinName);
        dest.writeString(this.letter);
    }

    protected ContactEntity(Parcel in) {
        this.name = in.readString();
        this.pinyinName = in.readString();
        this.letter = in.readString();
    }

    public static final Creator<ContactEntity> CREATOR = new Creator<ContactEntity>() {
        @Override
        public ContactEntity createFromParcel(Parcel source) {
            return new ContactEntity(source);
        }

        @Override
        public ContactEntity[] newArray(int size) {
            return new ContactEntity[size];
        }
    };
}
