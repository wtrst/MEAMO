package ca.ciccc.android.wataru.meamo;

import java.util.Date;
import java.util.UUID;

/**
 * Created by satouwataru on 2018/01/31.
 */

public class Meamo {
    private UUID mId;
    private String mName;
    private String mCategory;
    private float mWholeRating;
    private int mFoodRating;
    private int mDrinkRating;
    private int mCpRating;
    private int mServRating;
    private int mAtmRating;
    private String mAddress;
    private String mMemo;
    private Date mDate;

    public Meamo() {
        this(UUID.randomUUID());
    }

    public Meamo(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }


    public float getWholeRating() {
        return mWholeRating;
    }

    public void setWholeRating(int food, int drink, int cp, int service, int atmosphere) {

        this.mWholeRating = ((float)food + (float)drink + (float)cp + (float)service + (float)atmosphere) / 5;
    }

    public int getFoodRating() {
        return mFoodRating;
    }

    public void setFoodRating(int mFoodRating) {
        this.mFoodRating = mFoodRating;
    }

    public int getDrinkRating() {
        return mDrinkRating;
    }

    public void setDrinkRating(int mDrinkRating) {
        this.mDrinkRating = mDrinkRating;
    }

    public int getCpRating() {
        return mCpRating;
    }

    public void setCpRating(int mCpRating) {
        this.mCpRating = mCpRating;
    }

    public int getServRating() {
        return mServRating;
    }

    public void setServRating(int mServRating) {
        this.mServRating = mServRating;
    }

    public int getAtmRating() {
        return mAtmRating;
    }

    public void setAtmRating(int mAtmRating) {
        this.mAtmRating = mAtmRating;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getMemo() {
        return mMemo;
    }

    public void setMemo(String mMemo) {
        this.mMemo = mMemo;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }


}
