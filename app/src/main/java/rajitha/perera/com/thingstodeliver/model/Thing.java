package rajitha.perera.com.thingstodeliver.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rajitha on 9/15/2018.
 */

public class Thing implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("description")
    private String description;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("location")
    private Location location;

    protected Thing(Parcel in) {
        id = in.readInt();
        description = in.readString();
        imageUrl = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<Thing> CREATOR = new Creator<Thing>() {
        @Override
        public Thing createFromParcel(Parcel in) {
            return new Thing(in);
        }

        @Override
        public Thing[] newArray(int size) {
            return new Thing[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeParcelable(location, flags);
    }
}