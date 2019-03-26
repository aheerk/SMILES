package macewan_dust.smiles;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import static macewan_dust.smiles.ScoringAlgorithms.*;

/**
 * Note there is no setter for RAW_ID
 */
public class Score implements Comparable{

//    private String mDateString; // using a string date due to all the date issues. deprecated, etc.
    private static final String TAG = "Score";

    private Date mDate;
    private String mDateString;
    private UUID mScoreID;
    private int mSleepScore;
    private int mMovementScore;
    private int mImaginationScore;
    private int mLaughterScore;
    private int mEatingScore;
    private int mSpeakingScore;

    public Score() {
        mDate =  new Date();
    //    long currentTime = mDate.getTime();
    //    long dateOnly = currentTime + TimeZone.getDefault().getOffset(currentTime);
    //    mDate = new Date(dateOnly);
        dateShiftHelper();
        mScoreID = UUID.randomUUID();
        mSleepScore = SCORE_NO_DATA;
        mMovementScore = SCORE_NO_DATA;
        mImaginationScore = SCORE_NO_DATA;
        mLaughterScore = SCORE_NO_DATA;
        mEatingScore = SCORE_NO_DATA;
        mSpeakingScore = SCORE_NO_DATA;

        mDateString = timelessDate(mDate);
    }

    public Score(Date date) {
        mDate = date;
    //    long currentTime = mDate.getTime();
    //    long dateOnly = currentTime + TimeZone.getDefault().getOffset(currentTime);
    //    mDate = new Date(dateOnly);
        dateShiftHelper();
        mScoreID = UUID.randomUUID();
        mSleepScore = SCORE_NO_DATA;
        mMovementScore = SCORE_NO_DATA;
        mImaginationScore = SCORE_NO_DATA;
        mLaughterScore = SCORE_NO_DATA;
        mEatingScore = SCORE_NO_DATA;
        mSpeakingScore = SCORE_NO_DATA;

        mDateString = timelessDate(mDate);
    }

    /**
     * //Will need to load the rest of the data from the database
     *
     * @param id
     */
    public Score(UUID id) {
        // no date shift here! this is for databases
        mScoreID = id;
        mSleepScore = SCORE_NO_DATA;
        mMovementScore = SCORE_NO_DATA;
        mImaginationScore = SCORE_NO_DATA;
        mLaughterScore = SCORE_NO_DATA;
        mEatingScore = SCORE_NO_DATA;
        mSpeakingScore = SCORE_NO_DATA;

    }

    /**
     * Shifts a new date forward by 2 hours if doing so doesnt change the day. This is to avoid
     * daylight savings time issues with the calendar which takes the end of the day date.
     * This should only be used on new dates that have not yet been saved into the database.
     * Dates from the database will have the date overwritten and should stay consistent.
     */
    private void dateShiftHelper(){
        if (timelessDate(mDate).equals(timelessDate(new Date(mDate.getTime() - 60*2*1000)))) {
            mDate = new Date(mDate.getTime() - 60*2*1000);
        }
    }

    /**
     * returns true if all categories have been scored.
     * @return
     */
    public boolean isAllScored() {
        if (mSleepScore == SCORE_NO_DATA)
            return false;
        if (mMovementScore == SCORE_NO_DATA)
            return false;
        if (mImaginationScore == SCORE_NO_DATA)
            return false;
        if (mLaughterScore == SCORE_NO_DATA)
            return false;
        if (mEatingScore == SCORE_NO_DATA)
            return false;
        if (mSpeakingScore == SCORE_NO_DATA)
            return false;
        return true;
    }

    public String getDateString() {
        return mDateString;
    }

    public UUID getScoreID() {
        return mScoreID;
    }

    public int getSleepScore() {
        return mSleepScore;
    }

    public void setSleepScore(int sleepScore) {
        this.mSleepScore = sleepScore;
    }

    public int getMovementScore() {
        return mMovementScore;
    }

    public void setMovementScore(int movementScore) {
        this.mMovementScore = movementScore;
    }

    public int getImaginationScore() {
        return mImaginationScore;
    }

    public void setImaginationScore(int imaginationScore) {
        this.mImaginationScore = imaginationScore;
    }

    public int getLaughterScore() {
        return mLaughterScore;
    }

    public void setLaughterScore(int laughterScore) {
        this.mLaughterScore = laughterScore;
    }

    public int getEatingScore() {
        return mEatingScore;
    }

    public void setEatingScore(int eatingScore) {
        this.mEatingScore = eatingScore;
    }

    public int getSpeakingScore() {
        return mSpeakingScore;
    }

    public void setSpeakingScore(int speakingScore) {
        this.mSpeakingScore = speakingScore;
    }

    public Date getDate() {
 //       long currentTime = mDate.getTime();
   //     long dateOnly = currentTime + TimeZone.getDefault().getOffset(currentTime);
     //   mDate = new Date(dateOnly);
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
        mDateString = timelessDate(mDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("mScoreID: " + mScoreID);
        sb.append("\n");
        sb.append("Date long: " + String.valueOf(mDate.getTime()));
        sb.append("\n");
        sb.append("Date string: " + mDateString);
        sb.append("\n");
        sb.append("Sleep: " + mSleepScore);
        sb.append("\n");
        sb.append("Movement: " + mMovementScore);
        sb.append("\n");
        sb.append("Imagination: " + mImaginationScore);
        sb.append("\n");
        sb.append("Laughter: " + mLaughterScore);
        sb.append("\n");
        sb.append("Eating: " + mEatingScore);
        sb.append("\n");
        sb.append("Speaking: " + mSpeakingScore);
        sb.append("\n");

        return sb.toString();
    }

    /**
     * scoreCSVFormat - creates a comma separated string.
     * @return
     */
    public String scoreCSVFormat() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        sb.append("\"");
        sb.append(formatter.format(mDate));
        sb.append("\",");
        sb.append(mSleepScore);
        sb.append(",");
        sb.append(mMovementScore);
        sb.append(",");
        sb.append(mImaginationScore);
        sb.append(",");
        sb.append(mLaughterScore);
        sb.append(",");
        sb.append(mEatingScore);
        sb.append(",");
        sb.append(mSpeakingScore);

        return sb.toString();
    }

    public static int getBackgroundID(int score) {

        switch (score) {
            case SCORE_UNDER:
                return R.drawable.border_image_low;
            case SCORE_OVER:
                return R.drawable.border_image_high;
            case SCORE_BALANCED:
                return R.drawable.border_image_balanced;
            case SCORE_UNBALANCED:
                return R.drawable.border_image_unbalanced;
            default:
                return R.drawable.border_image_no_data;
        }
    }

    /**
     * getDotID - used by weekly graph to find the right dot image for a given score
     * @param score
     * @return
     */
    public static int getDotID(int score) {

        switch (score) {
            case SCORE_UNDER:
                return R.drawable.dot_image_low;
            case SCORE_OVER:
                return R.drawable.dot_image_high;
            case SCORE_BALANCED:
                return R.drawable.dot_image_balanced;
            case SCORE_UNBALANCED:
                return R.drawable.dot_image_unbalanced;
            default:
                return R.drawable.dot_image;
        }
    }

    public static boolean isToday(Date date) {
        Date today = new Date();
        Log.d(TAG, timelessDate(date) + " is today: " + timelessDate(today));
        if (timelessDate(today).equals(timelessDate(date))) {
            Log.d(TAG, "true");
            return true;
        }
        Log.d(TAG, "false");
        return false;
    }

    /**
     * timelessDate - remove hours, minutes, seconds from date. should only be done in this one
     * method for consistency
     * @param dateIn
     * @return
     */
    public static String timelessDate(Date dateIn) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(dateIn);
    }

    /**
     * Allows for scores to be compared for sorting purposes
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NonNull Object o) {
        Score other = (Score)o;
        return (other.getDate()).compareTo(mDate);
    }
}
