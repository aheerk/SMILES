package macewan_dust.smiles;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Raw implements Comparable {

    private UUID mRawID;
    private Date mDate;

    private int mSleep1;
    private int mSleep2;

    private int mMovement1;
    private int mMovement2;
    private int mMovement3;

    private int mImagination1;
    private int mImagination2;
    private int mImagination3;

    private int mLifeSatisfaction1;
    private int mLifeSatisfaction2;
    private int mLifeSatisfaction3;
    private int mLifeSatisfaction4;
    private int mLifeSatisfaction5;
    private int mLifeSatisfaction6;
    private int mLifeSatisfaction7;
    private int mLifeSatisfaction8;
    private int mLifeSatisfaction9;
    private int mLifeSatisfaction10;
    private int mLifeSatisfaction11;

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

    /**
     * this constructor is for data from the database
     * @param date
     * @param id
     */
    public Raw(Date date, UUID id) {
        this.mDate = date;
        mRawID = id;
    }

    public void setSleep(int q1, int q2) {
        mSleep1 = q1;
        mSleep2 = q2;
    }

    public void setMovement(int q1, int q2, int q3) {
        mMovement1 = q1;
        mMovement2 = q2;
        mMovement3 = q3;
    }

    public void setImagination(int q1, int q2, int q3){
        mImagination1 = q1;
        mImagination2 = q2;
        mImagination3 = q3;
    }

    public void setLifeSatisfaction(int q1, int q2, int q3, int q4, int q5, int q6, int q7, int q8, int q9, int q10, int q11) {
        mLifeSatisfaction1 = q1;
        mLifeSatisfaction2 = q2;
        mLifeSatisfaction3 = q3;
        mLifeSatisfaction4 = q4;
        mLifeSatisfaction5 = q5;
        mLifeSatisfaction6 = q6;
        mLifeSatisfaction7 = q7;
        mLifeSatisfaction8 = q8;
        mLifeSatisfaction9 = q9;
        mLifeSatisfaction10 = q10;
        mLifeSatisfaction11 = q11;
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

    public String getDateString() {
        return Score.timelessDate(mDate);
    }

    public UUID getRawID() {
        return mRawID;
    }

    public Date getDate() {
        return mDate;
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

    public int getMovement2() {
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

    public int getLifeSatisfaction1() {
        return mLifeSatisfaction1;
    }
    public int getLifeSatisfaction2() {
        return mLifeSatisfaction2;
    }
    public int getLifeSatisfaction3() {
        return mLifeSatisfaction3;
    }
    public int getLifeSatisfaction4() {
        return mLifeSatisfaction4;
    }
    public int getLifeSatisfaction5() {
        return mLifeSatisfaction5;
    }
    public int getLifeSatisfaction6() {
        return mLifeSatisfaction6;
    }
    public int getLifeSatisfaction7() {
        return mLifeSatisfaction7;
    }
    public int getLifeSatisfaction8() {
        return mLifeSatisfaction8;
    }
    public int getLifeSatisfaction9() {
        return mLifeSatisfaction9;
    }
    public int getLifeSatisfaction10() {
        return mLifeSatisfaction10;
    }
    public int getLifeSatisfaction11() {
        return mLifeSatisfaction11;
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

    public String rawCSVFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(",");
        sb.append(mSleep1);
        sb.append(",");
        sb.append(mSleep2);
        sb.append(",");
        sb.append(mMovement1);
        sb.append(",");
        sb.append(mMovement2);
        sb.append(",");
        sb.append(mMovement3);
        sb.append(",");
        sb.append(mImagination1);
        sb.append(",");
        sb.append(mImagination2);
        sb.append(",");
        sb.append(mImagination3);
        sb.append(",");
        sb.append(mLifeSatisfaction1);
        sb.append(",");
        sb.append(mLifeSatisfaction2);
        sb.append(",");
        sb.append(mLifeSatisfaction3);
        sb.append(",");
        sb.append(mLifeSatisfaction4);
        sb.append(",");
        sb.append(mLifeSatisfaction5);
        sb.append(",");
        sb.append(mLifeSatisfaction6);
        sb.append(",");
        sb.append(mLifeSatisfaction7);
        sb.append(",");
        sb.append(mLifeSatisfaction8);
        sb.append(",");
        sb.append(mLifeSatisfaction9);
        sb.append(",");
        sb.append(mLifeSatisfaction10);
        sb.append(",");
        sb.append(mLifeSatisfaction11);
        sb.append(",");
        sb.append(mEating1);
        sb.append(",");
        sb.append(mEating2);
        sb.append(",");
        sb.append(mEating3);
        sb.append(",");
        sb.append(mEating4);
        sb.append(",");
        sb.append(mEating5);
        sb.append(",");
        sb.append(mEating6);
        sb.append(",");
        sb.append(mEating7);
        sb.append(",");
        sb.append(mSpeaking1);
        sb.append(",");
        sb.append(mSpeaking2);
        sb.append(",");
        sb.append(mSpeaking3);
        sb.append(",");
        sb.append(mSpeaking4);
        sb.append(",");
        sb.append(mSpeaking5);

        return sb.toString();
    }

    @Override
    public String toString() {
        return  "mRawID=" + mRawID +
                ", mDate=" + mDate + "\n" +
                " mSleep1=" + mSleep1 +
                ", mSleep2=" + mSleep2 +"\n" +
                " mMovement1=" + mMovement1 +
                ", mMovement2=" + mMovement2 +
                ", mMovement3=" + mMovement3 + "\n" +
                " mImagination1=" + mImagination1 +
                ", mImagination2=" + mImagination2 +
                ", mImagination3=" + mImagination3 + "\n" +
                " mLifeSatisfaction1=" + mLifeSatisfaction1 + "\n" +
                " mLifeSatisfaction2=" + mLifeSatisfaction2 + "\n" +
                " mLifeSatisfaction3=" + mLifeSatisfaction3 + "\n" +
                " mLifeSatisfaction4=" + mLifeSatisfaction4 + "\n" +
                " mLifeSatisfaction5=" + mLifeSatisfaction5 + "\n" +
                " mLifeSatisfaction6=" + mLifeSatisfaction6 + "\n" +
                " mLifeSatisfaction7=" + mLifeSatisfaction7 + "\n" +
                " mLifeSatisfaction8=" + mLifeSatisfaction8 + "\n" +
                " mLifeSatisfaction9=" + mLifeSatisfaction9 + "\n" +
                " mLifeSatisfaction10=" + mLifeSatisfaction10 + "\n" +
                " mLifeSatisfaction11=" + mLifeSatisfaction11 + "\n" +
                " mEating1=" + mEating1 +
                ", mEating2=" + mEating2 +
                ", mEating3=" + mEating3 +
                ", mEating4=" + mEating4 +
                ", mEating5=" + mEating5 +
                ", mEating6=" + mEating6 +
                ", mEating7=" + mEating7 + "\n" +
                " mSpeaking1=" + mSpeaking1 +
                ", mSpeaking2=" + mSpeaking2 +
                ", mSpeaking3=" + mSpeaking3 +
                ", mSpeaking4=" + mSpeaking4 +
                ", mSpeaking5=" + mSpeaking5 + "\n";
    }

    /**
     * Allows for raw objects to be compared for sorting purposes
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NonNull Object o) {
        Raw other = (Raw)o;
        return (other.getDate()).compareTo(mDate);
    }
}
