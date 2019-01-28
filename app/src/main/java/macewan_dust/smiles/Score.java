package macewan_dust.smiles;

import java.util.Date;
import java.util.UUID;

/**
 * Note there is no setter for UUID
 */
public class Score {

    private Date mDate;
    private UUID mID;
    private int mSleepScore;
    private int mMovementScore;
    private int mImaginationScore;
    private int mLaughterScore;
    private int mEatingScore;
    private int mSpeakingScore;

    public Score() {
        mDate = new Date();                                                 /// check this is the right mDate option. there are 2 import options
        mID = UUID.randomUUID();
        mSleepScore = ScoreLab.SCORE_NONE;
        mMovementScore = ScoreLab.SCORE_NONE;
        mImaginationScore = ScoreLab.SCORE_NONE;
        mLaughterScore = ScoreLab.SCORE_NONE;
        mEatingScore = ScoreLab.SCORE_NONE;
        mSpeakingScore = ScoreLab.SCORE_NONE;
    }

    /**
     * //Will need to load the rest of the data from the database
     *
     * @param id
     */
    public Score(UUID id) {
        mDate = new Date();                           // warning. date may be null without this line
        mID = id;
        mSleepScore = ScoreLab.SCORE_NONE;
        mMovementScore = ScoreLab.SCORE_NONE;
        mImaginationScore = ScoreLab.SCORE_NONE;
        mLaughterScore = ScoreLab.SCORE_NONE;
        mEatingScore = ScoreLab.SCORE_NONE;
        mSpeakingScore = ScoreLab.SCORE_NONE;
    }

    public Date getDate() {
        return mDate;
    }

    public UUID getID() {
        return mID;
    }

    public void setDate(Date date) {
        date = date;
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
}
