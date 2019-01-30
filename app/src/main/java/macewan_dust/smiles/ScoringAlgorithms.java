package macewan_dust.smiles;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import macewan_dust.smiles.database.SMILES_DatabaseSchema.ScoreTable;
import macewan_dust.smiles.database.SMILES_DatabaseHelper;

/**
 * ScoringAlgorithms class includes all methods for converting user input values into score values
 *
 * Also contains database methods to store and update scores
 *
 *
 * /////////////// getting scores will likely be done with a list and is not yet implemented -----------------
 *
 * Please use constants when calling functions to support maintainability
 * WARNING! Changing the text on buttons may require an update to the scoring rules.
 *
 * Note: booleans are not checked or reported in range checking
 */

public class ScoringAlgorithms {

    public static final String TAG = "ScoringAlgorithms";

    public static final int SCORE_LOW = 0;
    public static final int SCORE_BALANCED = 1;
    public static final int SCORE_HIGH = 2;
    public static final int SCORE_OFF = 3; // off balance input includes both high and low input
    public static final int SCORE_ERROR = 10000;
    public static final int SCORE_NONE = 8;

    // input_x corresponds to the answer selected for all number range questions
    public static final int INPUT_a = 0;
    public static final int INPUT_b = 1;
    public static final int INPUT_c = 2;
    public static final int INPUT_d = 3;

    /**
     * Question A
     * sleepButton: button string
     * INPUT_a: < 4 hours
     * INPUT_b: 4-5 hours
     * INPUT_c: 6-9 hours
     * INPUT_d: > 9 hours
     *
     * @param sleepInput      integer corresponding to sleep amount as above. Use constants.
     * @param hinderanceCount integer corresponding to number of sleep interuptions as above
     * @return score integer corresponding to score constant
     */
    public int scoreSleep(int sleepInput, int hinderanceCount) {

        // range checking. Note: hinderanceCount should be a max of 5 but could be increased
        // // without changing the scoring behavior, so has a higher range check
        if (sleepInput < INPUT_a || sleepInput > INPUT_d
                || hinderanceCount < 0 || hinderanceCount > 10){
            Log.e(TAG, "Error: invalid input, sleepInput: " + sleepInput +
                    " and hinderanceCount: " + hinderanceCount);
            return SCORE_ERROR;
        }

        // scoring
        if (sleepInput < INPUT_c || (sleepInput < INPUT_d && hinderanceCount > 1)) {
            return SCORE_LOW;
        } else if (sleepInput == INPUT_c && hinderanceCount <= 1) {
            return SCORE_BALANCED;
        } else if (sleepInput > INPUT_c) {
            return SCORE_HIGH;
        } else {
            Log.e(TAG, "Error: score for sleepInput: " + sleepInput +
                    " and hinderanceCount: " + hinderanceCount + " has no rules");
            return SCORE_ERROR;
        }
    }

    /**
     *
     * @param exerciseIndex 0 is low, 3 is high. all else are balanced
     * @param boneAndMuscle true is balanced
     * @param relaxationIndex 0 is low, 3 is high. all else are balanced
     * @return
     */
    public int scoreMovement(int exerciseIndex, boolean boneAndMuscle, int relaxationIndex) {

        int balancedRule = 3; // the number of questions that must be balanced for an overall balanced score.
        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (exerciseIndex < INPUT_a || exerciseIndex > INPUT_d
                || relaxationIndex < INPUT_a || relaxationIndex > INPUT_d){
            Log.e(TAG, "Error: invalid input, exerciseIndex: " + exerciseIndex +
                    " and relaxationIndex: " + relaxationIndex);
            return SCORE_ERROR;
        }

        // count
        switch (exerciseIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_d:
                highCounter++;
                break;
            default:
                balancedCounter++;
                break;
        }

        if (boneAndMuscle)
            balancedCounter++;
        else
            lowCounter++;

        switch (relaxationIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_d:
                highCounter++;
                break;
            default:
                balancedCounter++;
                break;
        }

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for movement has no rule");
    }

    /**
     *
     * @param mindfulnessIndex index of button pushed
     * @param meditationIndex index of button pushed
     * @param imaginationIndex index of button pushed
     * @return score
     */
    public int scoreImagination(int mindfulnessIndex, int meditationIndex, int imaginationIndex) {

        int balancedRule = 3; // the number of questions that must be balanced for an overall balanced score.
        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (mindfulnessIndex < INPUT_a || mindfulnessIndex > INPUT_c
                || meditationIndex < INPUT_a || meditationIndex > INPUT_c
                || imaginationIndex < INPUT_a || imaginationIndex > INPUT_c){
            Log.e(TAG, "Error: invalid input, mindfulnessIndex: " + mindfulnessIndex +
                    ", meditationIndex: " + meditationIndex +
                    " and imaginationIndex: " + imaginationIndex);
            return SCORE_ERROR;
        }

        // count
        switch (mindfulnessIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        // count
        switch (meditationIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        // count
        switch (imaginationIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for imagination has no rule");
    }

    /**
     * @param laughterInput is the same number as the user inputs from 1 - 5
     * @return balanced or low score constant
     */
    public int scoreLaughter(int laughterInput) {

        // range checking
        if (laughterInput <= 0 || laughterInput > 5){
            Log.e(TAG, "Error: invalid input, laughterInput: " + laughterInput);
            return SCORE_ERROR;
        }

        if (laughterInput < 5) {
            return SCORE_LOW;
        } else if (laughterInput == 5) {
            return SCORE_BALANCED;
        } else {
            Log.e(TAG, "Error: score for laughterInput: " + laughterInput + "  has no rule");
            return SCORE_ERROR;
        }
    }

    /**
     * @param vegIndex 0-3, 2 is balanced
     * @param meatIndex 0-2, 1 is balanced
     * @param milkIndex 0-2, 1 is balanced
     * @param grainIndex 0-3, 2 is balanced
     * @param fatIndex 0-2, 1 is balanced
     * @param unsaturated boolean
     * @return integer code for eating score
     */
    public int scoreEating(int vegIndex, int meatIndex, int milkIndex, int grainIndex,
                           int fatIndex, boolean unsaturated) {

        int balancedRule = 6; // the number of questions that must be balanced for an overall balanced score.
        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (vegIndex < INPUT_a || vegIndex > INPUT_d
                || meatIndex < INPUT_a || meatIndex > INPUT_c
                || milkIndex < INPUT_a || milkIndex > INPUT_c
                || grainIndex < INPUT_a || grainIndex > INPUT_d
                || fatIndex < INPUT_a || fatIndex > INPUT_c){
            Log.e(TAG, "Error: invalid input, vegIndex: " + vegIndex +
                    ", meatIndex: " + meatIndex +
                    " and milkIndex: " + milkIndex +
                    " and grainIndex: " + grainIndex +
                    " and fatIndex: " + fatIndex);
            return SCORE_ERROR;
        }

        // count
        switch (vegIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                lowCounter++;
                break;
            case INPUT_c:
                balancedCounter++;
                break;
            case INPUT_d:
                highCounter++;
                break;
        }

        switch (meatIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        switch (milkIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        switch (grainIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                lowCounter++;
                break;
            case INPUT_c:
                balancedCounter++;
                break;
            case INPUT_d:
                highCounter++;
                break;
        }

        switch (fatIndex) {
            case INPUT_a:
                lowCounter++;
                break;
            case INPUT_b:
                balancedCounter++;
                break;
            case INPUT_c:
                highCounter++;
                break;
        }

        // unsaturated count as balanced only if the fat amount is balanced.
        if (fatIndex == 1 && unsaturated)
            balancedCounter++;

        // if fat amount is not low, then eating the right amount of the wrong fat is high
        if (fatIndex >= 1 && !unsaturated)
            highCounter++;

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for speaking has no rule");
    }

    /**
     * Note: all booleans in the count code are adjusted to be true in the if statement.
     * For example, prevent is inverted with !
     *
     * @param speakingRating integer from 1 to 5 as entered by the user
     * @param debrief        true or false
     * @param prevented      true or false
     * @param socialMedia    true or false
     * @param impactHealth   true or false
     * @return high, low, balanced or unbalanced score constant
     */
    public int scoreSpeaking(int speakingRating, boolean debrief, boolean prevented, boolean socialMedia, boolean impactHealth) {

        int balancedRule = 5; // the number of questions that must be balanced for an overall balanced score.

        int highCounter = 0;
        int balancedCounter = 0;
        int lowCounter = 0;

        // range checking
        if (speakingRating < 1 || speakingRating > 5){
            Log.e(TAG, "Error: invalid input, speakingRating: " + speakingRating);
            return SCORE_ERROR;
        }

        // counting
        if (speakingRating == 5)
            balancedCounter++;
        else
            lowCounter++;

        if (debrief)
            balancedCounter++;
        else
            lowCounter++;

        if (!prevented)
            balancedCounter++;
        else
            lowCounter++;

        if (!socialMedia)
            balancedCounter++;
        else
            highCounter++;

        if (!impactHealth)
            balancedCounter++;
        else
            highCounter++;

        return scoreHelper(balancedRule, highCounter, balancedCounter, lowCounter,
                "Error: score for speaking has no rule");
    }

    /**
     * Helper function for repeating code
     * @param balancedRule number of balanced answers to give a balanced score
     * @param highCounter number of high questions
     * @param balancedCounter number of high balanced
     * @param lowCounter number of high low
     * @param errorMessage text to customize error message.
     * @return score
     */
    private int scoreHelper(int balancedRule, int highCounter,
                            int balancedCounter,int lowCounter, String errorMessage) {

        // returning score based on rules
        if (balancedCounter == balancedRule) {
            return SCORE_BALANCED;
        } else if (lowCounter == 0 && highCounter > 0) {
            return SCORE_HIGH;
        } else if (highCounter == 0 && lowCounter > 0) {
            return SCORE_LOW;
        } else if (highCounter > 0 && lowCounter > 0) {
            return SCORE_OFF;
        } else {
            Log.e(TAG, errorMessage);
            return SCORE_ERROR;
        }
    }
}
