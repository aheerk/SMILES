package macewan_dust.smiles;


import android.util.Log;

/**
 * Score class includes all methods for converting user input values into score values
 */

public class Score {

    public static final String TAG = "Score";

    public static final int SCORE_LOW = 0;
    public static final int SCORE_BALANCED = 1;
    public static final int SCORE_HIGH = 2;
    public static final int SCORE_ERROR = 100000;


    /**
     * Question A
     * sleepButton: button string
     * 0: 4 hours
     * 1: 4-5 hours
     * 2: 6-9 hours
     * 3: 10+ hours
     * <p>
     * Question B
     * Count the number of buttons selected and pass the count into hinderanceCount
     * <p>
     * Rules:
     * SCORE_LOW
     * sleepString < 6-9 hours and hinderanceCount > 2
     * sleepButton < 2 && hinderanceCount > 2
     * <p>
     * SCORE_BALANCED
     * sleepString == 6-9 hours and hinderanceCount < 1
     * sleepButton == 2 && hinderanceCount < 1
     * <p>
     * SCORE_HIGH
     * sleepString == 10+ hours and hinderanceCount > 3-4                  ////////////////// Note: > 3-4 is just > 3
     * sleepButton == 3 && hinderanceCount > 3
     * <p>
     * !!!!!!!!!!!!!!!! ERROR !!!!!!!!!!!!!!!!!
     * The following combinations of input will give no score.
     * Need rules for:
     * sleep < 6-9 with hinderances <= 2
     * sleep == 6-9 with hinderance >= 1
     * sleep == 10+ with hinderance <= 3
     *
     * @param sleepCode       integer corresponding to sleep amount as above
     * @param hinderanceCount integer corresponding to number of sleep interuptions as above
     * @return score integer corresponding to score constant
     */
    public int scoreSleep(int sleepCode, int hinderanceCount) {

        if (sleepCode < 2 && hinderanceCount > 2) {
            return SCORE_LOW;
        } else if (sleepCode == 2 && hinderanceCount < 1) {
            return SCORE_BALANCED;
        } else if (sleepCode == 3 && hinderanceCount > 3) {
            return SCORE_HIGH;
        } else {
            Log.e(TAG, "Error: score for sleepCode: " + sleepCode + " and hinderanceCount: " + hinderanceCount + " has no rules");
            return SCORE_ERROR;
        }
    }


    /**
     * Question A - type of exercise
     * 0: moderate aerobic
     * 1: vigorous aerobic
     * 2: none
     * <p>
     * simplification - exercised
     * true
     * false
     * <p>
     * Note: 0 and 1 are the same in the score algorithm
     * This entire question could be removed. If duration > 0 then exercise, else none.    //////////////////////
     * <p>
     * Question B - length of exercise
     * 0: < 10 min
     * 1: 10 min
     * 2: 20 min
     * 3: 30-45 min
     * 4: 1+ hour
     * <p>
     * Question C - bone and muscle strengthening?
     * true
     * false
     * <p>
     * Question D - relaxation techniques duration
     * 0: < 10 min
     * 1: 10-20 min
     * 2: 20-45 min
     * 3: 1+ hour                                                                          ///// string inconsistency   10-20 here but 10, 20 in B ????
     * <p>
     * Rules:
     * <p>
     * SCORE_LOW
     * no exercise, exercise duration > 10, bone strengthening, relaxing > 10 min
     * <p>
     * <p>
     * !!!!!!!!! ERROR !!!!!!!!
     * ??? impossible to have no exercise and exercise longer than 10 minutes.               /////
     * ??? need to clarify these rules.
     * ??? some rules seem to require all conditions but others are impossible with all conditions.     //////
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * score 1, balanced
     * exercised, 10 - 45 minutes, bone&muscle, relaxed 10-45 min
     * <p>
     * exercised && (exerciseDurationCode == 1 ||  exerciseDurationCode == 2 ||
     * exerciseDurationCode == 3) &&
     * boneAndMuscle && (relaxationDurationCode == 1 || relaxationDurationCode == 2)
     * <p>
     * score 2, high
     * exercised, 1+ hour, bone&muscle, 1+ hour
     * <p>
     * <p>
     * exercised && exerciseDurationCode == 4 &&
     * boneAndMuscle && relaxationDurationCode == 3
     * <p>
     * <p>
     * !!!!!!!!! ERROR !!!!!!!!
     * many possibilities unaccounted for.
     * <p>
     * suggest simplifiying low and high conditions to be simpler and use or statements.           !!!!!!!
     * <p>
     * <p>
     * ////                 Note to self. Consider using a Venn diagram to show the problem to the client.
     *
     * @param exercised              true or false for any exercise type
     * @param exerciseDurationCode   see above for code
     * @param boneAndMuscle          true or false
     * @param relaxationDurationCode see above for code
     * @return score integer corresponding to score constant
     */
    public int scoreMovement(boolean exercised, int exerciseDurationCode,
                             boolean boneAndMuscle, int relaxationDurationCode) {


        /*
        if () {
            return SCORE_LOW;
        } else if () {
            return SCORE_BALANCED;
        } else if () {
            return SCORE_HIGH;
        } else {
             Log.e(TAG, "Error: score for  ...");

            return SCORE_ERROR;
        }
        */
        return 8; // placeholder. delete me.
    }

    public int scoreImagination() {

         /*
        if () {
            return SCORE_LOW;
        } else if () {
            return SCORE_BALANCED;
        } else if () {
            return SCORE_HIGH;
        } else {
             Log.e(TAG, "Error: score for  ...");

            return SCORE_ERROR;
        }
        */

        return 8; // placeholder. delete me.

    }

    public int scoreLaughter() {

        /*
        if () {
            return SCORE_LOW;
        } else if () {
            return SCORE_BALANCED;
        } else if () {
            return SCORE_HIGH;
        } else {
             Log.e(TAG, "Error: score for  ...");

            return SCORE_ERROR;
        }
        */

        return 8; // placeholder. delete me.
    }

    public int scoreEating() {

        /*
        if () {
            return SCORE_LOW;
        } else if () {
            return SCORE_BALANCED;
        } else if () {
            return SCORE_HIGH;
        } else {
             Log.e(TAG, "Error: score for  ...");

            return SCORE_ERROR;
        }
        */

        return 8; // placeholder. delete me.
    }

    public int scoreSpeaking() {

        /*
        if () {
            return SCORE_LOW;
        } else if () {
            return SCORE_BALANCED;
        } else if () {
            return SCORE_HIGH;
        } else {
             Log.e(TAG, "Error: score for  ...");

            return SCORE_ERROR;
        }
        */

        return 8; // placeholder. delete me.
    }


}
