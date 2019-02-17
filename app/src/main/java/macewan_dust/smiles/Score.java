package macewan_dust.smiles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
        mSleepScore = ScoringAlgorithms.SCORE_NONE;
        mMovementScore = ScoringAlgorithms.SCORE_NONE;
        mImaginationScore = ScoringAlgorithms.SCORE_NONE;
        mLaughterScore = ScoringAlgorithms.SCORE_NONE;
        mEatingScore = ScoringAlgorithms.SCORE_NONE;
        mSpeakingScore = ScoringAlgorithms.SCORE_NONE;
    }

    /**
     * //Will need to load the rest of the data from the database
     *
     * @param id
     */
    public Score(UUID id) {
        mID = id;
        mSleepScore = ScoringAlgorithms.SCORE_NONE;
        mMovementScore = ScoringAlgorithms.SCORE_NONE;
        mImaginationScore = ScoringAlgorithms.SCORE_NONE;
        mLaughterScore = ScoringAlgorithms.SCORE_NONE;
        mEatingScore = ScoringAlgorithms.SCORE_NONE;
        mSpeakingScore = ScoringAlgorithms.SCORE_NONE;
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

    public static int getBackgroundID(int score){

        switch (score) {
            case ScoringAlgorithms.SCORE_LOW:
                return R.drawable.border_image_low;
            case ScoringAlgorithms.SCORE_HIGH:
                return R.drawable.border_image_high;
            case ScoringAlgorithms.SCORE_BALANCED:
                return R.drawable.border_image_balanced;
            case ScoringAlgorithms.SCORE_OFF:
                return R.drawable.border_image_unbalanced;
            default:
                return R.drawable.border_image_no_data;
        }
    }

    public static String timelessDate(Date dateIn){
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
