package macewan_dust.smiles;

import java.util.Date;
import java.util.UUID;

public class Raw {

    private UUID mRawID;
    private Date mDate;

    private int mSleep1;
    private int mSleep2;

    private int mMovement1;
    private boolean mMovement2;
    private int mMovement3;

    private int mImagination1;
    private int mImagination2;
    private int mImagination3;

    private int mLaughter1;

    private int mEating1;
    private int mEating2;
    private int mEating3;
    private boolean mEating4;
    private boolean mEating5;
    private boolean mEating6;
    private boolean mEating7;

    private int mSpeaking1;
    private boolean mSpeaking2;
    private boolean mSpeaking3;
    private boolean mSpeaking4;
    private boolean mSpeaking5;

    /**
     * constructor - only making raw objects if the date already exists and is being used for
     * score objects
     * @param date
     */
    public Raw(Date date) {
        this.mDate = date;
        mRawID = UUID.randomUUID();
    }

    public void setSleep(int q1, int q2) {
        mSleep1 = q1;
        mSleep2 = q2;
    }

    public void setMovement(int q1, boolean q2, int q3) {
        mMovement1 = q1;
        mMovement2 = q2;
        mMovement3 = q3;
    }

    public void setImagination(int q1, int q2, int q3){
        mImagination1 = q1;
        mImagination2 = q2;
        mImagination3 = q3;
    }

    public void setLaughter(int q1) {
        mLaughter1 = q1;
    }

    public void setEating(int q1, int q2, int q3, boolean q4, boolean q5, boolean q6, boolean q7) {
        mEating1 = q1;
        mEating2 = q2;
        mEating3 = q3;
        mEating4 = q4;
        mEating5 = q5;
        mEating6 = q6;
        mEating7 = q7;
    }

    public  void setSpeaking(int q1, boolean q2, boolean q3, boolean q4, boolean q5) {
        mSpeaking1 = q1;
        mSpeaking2 = q2;
        mSpeaking3 = q3;
        mSpeaking4 = q4;
        mSpeaking5 = q5;
    }

    public int getSleep1() {
        return mSleep1;
    }

    public int getSleep2() {
        return mSleep2;
    }

    public int getMovement1() {
        return mMovement1;
    }

    public boolean isMovement2() {
        return mMovement2;
    }

    public int getMovement3() {
        return mMovement3;
    }

    public int getImagination1() {
        return mImagination1;
    }

    public int getImagination2() {
        return mImagination2;
    }

    public int getImagination3() {
        return mImagination3;
    }

    public int getLaughter1() {
        return mLaughter1;
    }

    public int getEating1() {
        return mEating1;
    }

    public int getEating2() {
        return mEating2;
    }

    public int getEating3() {
        return mEating3;
    }

    public boolean isEating4() {
        return mEating4;
    }

    public boolean isEating5() {
        return mEating5;
    }

    public boolean isEating6() {
        return mEating6;
    }

    public boolean isEating7() {
        return mEating7;
    }

    public int getSpeaking1() {
        return mSpeaking1;
    }

    public boolean isSpeaking2() {
        return mSpeaking2;
    }

    public boolean isSpeaking3() {
        return mSpeaking3;
    }

    public boolean isSpeaking4() {
        return mSpeaking4;
    }

    public boolean isSpeaking5() {
        return mSpeaking5;
    }
}
