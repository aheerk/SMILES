package macewan_dust.smiles;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScoreLabTest  extends InstrumentationTestCase {

    private static final String TAG = "score lab test";

    ScoreLab mScoreLab;

    @Before
    public void setUp() throws Exception {
        Log.e(TAG, "setup");
        Context appContext = getInstrumentation().getContext();
        mScoreLab = new ScoreLab(appContext); // context for testing only

    }

    @Test
    public void testTest(){
        assertEquals(1, 1);
    }


    @Test
    public void scoreSleep() {

        int i = ScoreLab.INPUT_d;

        // Input_d is >9 hours of sleep
        assertEquals(ScoreLab.SCORE_HIGH, mScoreLab.scoreSleep(ScoreLab.INPUT_d, 0));
        assertEquals(ScoreLab.SCORE_HIGH, mScoreLab.scoreSleep(ScoreLab.INPUT_d, 5));

        // Input_c is 6-9 hours of sleep. balanced unless hindered > 1
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreSleep(ScoreLab.INPUT_c, 0));
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreSleep(ScoreLab.INPUT_c, 1));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreSleep(ScoreLab.INPUT_c, 2));

        // Input_b and a are low in all cases
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreSleep(ScoreLab.INPUT_b, 5));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreSleep(ScoreLab.INPUT_b, 0));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreSleep(ScoreLab.INPUT_a, 0));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreSleep(ScoreLab.INPUT_a, 5));

        // error cases. hinderance should be 0-5. input index is 0-3
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreSleep(ScoreLab.INPUT_a, -1));
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreSleep(ScoreLab.INPUT_a, 100));
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreSleep(4, 0));
    }

    @Test
    public void scoreMovement() {

        // input a is low
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreMovement(
                ScoreLab.INPUT_a, true, ScoreLab.INPUT_a));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreMovement(
                ScoreLab.INPUT_a, false, ScoreLab.INPUT_a));

        // input b and c are balanced if boneAndMuscle is true
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreMovement(
                ScoreLab.INPUT_b, true, ScoreLab.INPUT_b));
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreMovement(
                ScoreLab.INPUT_c, true, ScoreLab.INPUT_c));
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreMovement(
                ScoreLab.INPUT_b, true, ScoreLab.INPUT_c));
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreMovement(
                ScoreLab.INPUT_c, true, ScoreLab.INPUT_b));

        // input b and c and boneAndMuscle is false are all low
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreMovement(
                ScoreLab.INPUT_b, true, ScoreLab.INPUT_b));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreMovement(
                ScoreLab.INPUT_c, true, ScoreLab.INPUT_c));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreMovement(
                ScoreLab.INPUT_b, true, ScoreLab.INPUT_c));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreMovement(
                ScoreLab.INPUT_c, true, ScoreLab.INPUT_b));

        // input d is high
        assertEquals(ScoreLab.SCORE_HIGH, mScoreLab.scoreMovement(
                ScoreLab.INPUT_d, true, ScoreLab.INPUT_d));

        // this high is actually unbalanced because of the lack of bone and muscle ----------------------
        assertEquals(ScoreLab.SCORE_OFF, mScoreLab.scoreMovement(
                ScoreLab.INPUT_d, false, ScoreLab.INPUT_d));

        // unbalanced inputs
        assertEquals(ScoreLab.SCORE_OFF, mScoreLab.scoreMovement(
                ScoreLab.INPUT_a, true, ScoreLab.INPUT_d));
        assertEquals(ScoreLab.SCORE_OFF, mScoreLab.scoreMovement(
                ScoreLab.INPUT_d, true, ScoreLab.INPUT_a));
        assertEquals(ScoreLab.SCORE_OFF, mScoreLab.scoreMovement(
                ScoreLab.INPUT_a, false, ScoreLab.INPUT_d));
        assertEquals(ScoreLab.SCORE_OFF, mScoreLab.scoreMovement(
                ScoreLab.INPUT_d, false, ScoreLab.INPUT_a));

        // input range is 0-3
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreMovement(
                4, false, ScoreLab.INPUT_a));
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreMovement(
                4, false, 4));
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreMovement(
                -1, false, ScoreLab.INPUT_a));
    }

    @Test
    public void scoreImagination() {

        // in all categories a is low, b is balanced, c is high.
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreImagination(
                ScoreLab.INPUT_a, ScoreLab.INPUT_a, ScoreLab.INPUT_a));
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreImagination(
                ScoreLab.INPUT_b, ScoreLab.INPUT_b, ScoreLab.INPUT_b));
        assertEquals(ScoreLab.SCORE_HIGH, mScoreLab.scoreImagination(
                ScoreLab.INPUT_c, ScoreLab.INPUT_c, ScoreLab.INPUT_c));


        // off

        // ~high ~ low



    }

    @Test
    public void scoreLaughter() {
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreLaughter(1));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreLaughter(2));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreLaughter(3));
        assertEquals(ScoreLab.SCORE_LOW, mScoreLab.scoreLaughter(4));
        assertEquals(ScoreLab.SCORE_BALANCED, mScoreLab.scoreLaughter(5));
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreLaughter(100));
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreLaughter(-100));
        assertEquals(ScoreLab.SCORE_ERROR, mScoreLab.scoreLaughter(0));
    }



    @Test
    public void scoreEating() {
    }

    @Test
    public void scoreSpeaking() {
    }

    @Test
    public void addScore() {
    }

    @Test
    public void updateScore() {
    }

    @After
    public void tearDown() throws Exception {

    }
}