package macewan_dust.smiles;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class ScoringAlgorithmsTest {

    ScoringAlgorithms mScoringAlgorithms;


    @Before
    public void setUp() throws Exception {
        mScoringAlgorithms = new ScoringAlgorithms();

    }

    @Test
    public void scoreSleep() {


        assertEquals(2, 2);


        // Input_d is >9 hours of sleep
        assertEquals(ScoringAlgorithms.SCORE_HIGH, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_d, 0));
        assertEquals(ScoringAlgorithms.SCORE_HIGH, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_d, 5));

        // Input_c is 6-9 hours of sleep. balanced unless hindered > 1
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_c, 0));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_c, 1));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_c, 2));

        // Input_b and a are low in all cases
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_b, 5));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_b, 0));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, 0));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, 5));

        // error cases. hinderance should be 0-5. input index is 0-3
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, -1));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, 100));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreSleep(4, 0));
    }

    @Test
    public void scoreMovement() {

        // input a is low
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, true, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, false, ScoringAlgorithms.INPUT_a));

        // input b and c are balanced if boneAndMuscle is true
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, true, ScoringAlgorithms.INPUT_b));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, true, ScoringAlgorithms.INPUT_c));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, true, ScoringAlgorithms.INPUT_c));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, true, ScoringAlgorithms.INPUT_b));

        // input b and c and boneAndMuscle is false are all low
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, false, ScoringAlgorithms.INPUT_b));

        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, false, ScoringAlgorithms.INPUT_c));

        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, false, ScoringAlgorithms.INPUT_c));

        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, false, ScoringAlgorithms.INPUT_b));

        // input d is high
        assertEquals(ScoringAlgorithms.SCORE_HIGH, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, true, ScoringAlgorithms.INPUT_d));

        // this high is actually unbalanced because of the lack of bone and muscle ----------------------
        assertEquals(ScoringAlgorithms.SCORE_OFF, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, false, ScoringAlgorithms.INPUT_d));

        // unbalanced inputs
        assertEquals(ScoringAlgorithms.SCORE_OFF, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, true, ScoringAlgorithms.INPUT_d));
        assertEquals(ScoringAlgorithms.SCORE_OFF, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, true, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_OFF, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, false, ScoringAlgorithms.INPUT_d));
        assertEquals(ScoringAlgorithms.SCORE_OFF, mScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, false, ScoringAlgorithms.INPUT_a));

        // input range is 0-3
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreMovement(
                4, false, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreMovement(
                4, false, 4));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreMovement(
                -1, false, ScoringAlgorithms.INPUT_a));
    }

    @Test
    public void scoreImagination() {

        // in all categories a is low, b is balanced, c is high.
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b));
        assertEquals(ScoringAlgorithms.SCORE_HIGH, mScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_c));


        // off

        // ~high ~ low



    }

    @Test
    public void scoreLaughter() {
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreLaughter(1));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreLaughter(2));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreLaughter(3));
        assertEquals(ScoringAlgorithms.SCORE_LOW, mScoringAlgorithms.scoreLaughter(4));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, mScoringAlgorithms.scoreLaughter(5));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreLaughter(100));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreLaughter(-100));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, mScoringAlgorithms.scoreLaughter(0));
    }



    @Test
    public void scoreEating() {
    }

    @Test
    public void scoreSpeaking() {
    }
}