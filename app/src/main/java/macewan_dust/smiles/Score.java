package macewan_dust.smiles;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import static macewan_dust.smiles.ScoringAlgorithms.*;

/**
 * Note there is no setter for UUID
 */
public class Score {

    private String mDate; // using a string date due to all the date issues. deprecated, etc.
    private UUID mID;
    private int mSleepScore;
    private int mMovementScore;
    private int mImaginationScore;
    private int mLaughterScore;
    private int mEatingScore;
    private int mSpeakingScore;

    public Score() {
        Date tempDate = new Date();
        //    mDate = timelessDate(mDate);

        //     mDate.setHours(); // gives the wrong date

        mDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(tempDate);
        //      mDate = new Date(tempStringDate);

        mID = UUID.randomUUID();
        mSleepScore = SCORE_NO_DATA;
        mMovementScore = SCORE_NO_DATA;
        mImaginationScore = SCORE_NO_DATA;
        mLaughterScore = SCORE_NO_DATA;
        mEatingScore = SCORE_NO_DATA;
        mSpeakingScore = SCORE_NO_DATA;
    }

    /**
     * //Will need to load the rest of the data from the database
     *
     * @param id
     */
    public Score(UUID id) {
        mID = id;
        mSleepScore = SCORE_NO_DATA;
        mMovementScore = SCORE_NO_DATA;
        mImaginationScore = SCORE_NO_DATA;
        mLaughterScore = SCORE_NO_DATA;
        mEatingScore = SCORE_NO_DATA;
        mSpeakingScore = SCORE_NO_DATA;
    }

    public String getDate() {
        return mDate;
    }

    public UUID getID() {
        return mID;
    }

    public void setDate(String date) {
        mDate = date;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("mID: " + mID);
        sb.append("\n");
        sb.append("Date: " + mDate);
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
     * scoreCSVFormat - creates a comma separated string.                                   /// ----- beware of commas in string!!!
     * @return
     */
    public String scoreCSVFormat() {
        StringBuilder sb = new StringBuilder();

        //    sb.append("mID: " + mID);
        //    sb.append(",");
        sb.append(mDate);
        sb.append(",");
        sb.append(getScoreName(mSleepScore));
        sb.append(",");
        sb.append(getScoreName(mMovementScore));
        sb.append(",");
        sb.append(getScoreName(mImaginationScore));
        sb.append(",");
        sb.append(getScoreName(mLaughterScore));
        sb.append(",");
        sb.append(getScoreName(mEatingScore));
        sb.append(",");
        sb.append(getScoreName(mSpeakingScore));
        //  sb.append("\n");

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

    /**
     * getScoreName - used to make the CSV output human readable
     * @param score
     * @return
     */
    public String getScoreName(int score) {                                             ///// ------ should return string but need to find out why getResource and getString are not working here. imports most likely
        switch (score) {
            case SCORE_UNDER:
                return "low";
            case SCORE_BALANCED:
                return "balanced";
            case SCORE_OVER:
                return "over";
            case SCORE_UNBALANCED:
                return "unbalanced";
            case SCORE_ERROR:
                return "error";
            default:
                return "no data";
        }
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
/*
    public static Date timelessDate(Date dateIn){
        // removing time from date.
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateIn = formatter.parse(formatter.format(dateIn));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateIn;
    }*/
}
