package macewan_dust.smiles;

import java.util.Date;
import java.util.UUID;

/**
 * Note there is no setter for UUID
 */
public class Score {

    private Date mDate;
    private UUID mID;
    private int sleepScore;
    private int movementScore;
    private int imaginationScore;
    private int laughterScore;
    private int eatingScore;
    private int speakingScore;

    public Score() {
        mDate = new Date();                                                 /// check this is the right date option. there are 2 import options
        mID = UUID.randomUUID();
    }

    /**
     *                                                                      //Will need to load the rest of the data from the database
     * @param id
     */
    public Score(UUID id){
        mID = id;
    }

    public Date getDate() {
        return mDate;
    }

    public UUID getID() {
        return mID;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public int getSleepScore() {
        return sleepScore;
    }

    public void setSleepScore(int sleepScore) {
        this.sleepScore = sleepScore;
    }

    public int getMovementScore() {
        return movementScore;
    }

    public void setMovementScore(int movementScore) {
        this.movementScore = movementScore;
    }

    public int getImaginationScore() {
        return imaginationScore;
    }

    public void setImaginationScore(int imaginationScore) {
        this.imaginationScore = imaginationScore;
    }

    public int getLaughterScore() {
        return laughterScore;
    }

    public void setLaughterScore(int laughterScore) {
        this.laughterScore = laughterScore;
    }

    public int getEatingScore() {
        return eatingScore;
    }

    public void setEatingScore(int eatingScore) {
        this.eatingScore = eatingScore;
    }

    public int getSpeakingScore() {
        return speakingScore;
    }

    public void setSpeakingScore(int speakingScore) {
        this.speakingScore = speakingScore;
    }
}
