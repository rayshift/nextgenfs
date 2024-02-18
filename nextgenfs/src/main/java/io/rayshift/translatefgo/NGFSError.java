package io.rayshift.translatefgo;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NGFSError implements Parcelable {
    public static final String TAG = "NGFSError";
    public boolean IsSuccess;
    public String Error;

    public static final Parcelable.Creator<NGFSError> CREATOR = new Parcelable.Creator<NGFSError>() {

        @Override
        public NGFSError createFromParcel(Parcel source) {
            return new NGFSError(source);
        }

        @Override
        public NGFSError[] newArray(int size) {
            return new NGFSError[size];
        }
    };

    public NGFSError(Parcel src) {
        readFromParcel(src);
    }

    public NGFSError() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(IsSuccess ? 1 : 0);
        dest.writeString(Error);
    }

    public void readFromParcel(Parcel src) {
        IsSuccess = src.readInt() == 1;
        Error = src.readString();
    }
}
