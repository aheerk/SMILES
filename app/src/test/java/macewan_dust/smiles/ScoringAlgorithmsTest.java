package macewan_dust.smiles;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class ScoringAlgorithmsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void scoreSleep() {

        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_d, 0));

        // Input_d is >9 hours of sleep
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_d, 0));
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_d, 5));

        // Input_c is 6-9 hours of sleep. balanced unless hindered > 1
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_c, 0));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_c, 1));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_c, 2));

        // Input_b and a are low in all cases
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_b, 5));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_b, 0));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, 0));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, 5));

        // error cases. hinderance should be 0-5. input index is 0-3
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, -1));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreSleep(ScoringAlgorithms.INPUT_a, 10));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreSleep(4, 0));
    }

    @Test
    public void scoreMovement() {

        // input a is low
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, true, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, false, ScoringAlgorithms.INPUT_a));

        // input b and c are balanced if boneAndMuscle is true
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, true, ScoringAlgorithms.INPUT_b));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, true, ScoringAlgorithms.INPUT_c));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, true, ScoringAlgorithms.INPUT_c));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, true, ScoringAlgorithms.INPUT_b));

        // input b and c and boneAndMuscle is false are all low
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, false, ScoringAlgorithms.INPUT_b));

        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, false, ScoringAlgorithms.INPUT_c));

        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_b, false, ScoringAlgorithms.INPUT_c));

        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_c, false, ScoringAlgorithms.INPUT_b));

        // input d is high
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, true, ScoringAlgorithms.INPUT_d));

        // this high is actually unbalanced because of the lack of bone and muscle ----------------------
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, false, ScoringAlgorithms.INPUT_d));

        // unbalanced inputs
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, true, ScoringAlgorithms.INPUT_d));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, true, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_a, false, ScoringAlgorithms.INPUT_d));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreMovement(
                ScoringAlgorithms.INPUT_d, false, ScoringAlgorithms.INPUT_a));

        // input range is 0-3
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreMovement(
                4, false, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreMovement(
                4, false, 4));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreMovement(
                -1, false, ScoringAlgorithms.INPUT_a));
    }

    @Test
    public void scoreImagination() {

        // in all categories a is low, b is balanced, c is high.
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_a));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b));
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_c));

        // low mixed with balanced is low
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_b));

        // high mixed with balanced is high
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_c));

        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b));

        // off balance
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_c));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_c));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_b));

        // errors - input range is a to c on all fields
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreImagination(
                -1, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_c));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreImagination(
                ScoringAlgorithms.INPUT_d, ScoringAlgorithms.INPUT_d, ScoringAlgorithms.INPUT_d));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreImagination(
                100, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_c));
    }

    @Test
    public void scoreLaughter() {
        // note that laughter has no high or off balanced score
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreLaughter(1));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreLaughter(2));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreLaughter(3));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreLaughter(4));
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreLaughter(5));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreLaughter(100));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreLaughter(-100));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreLaughter(0));
    }

    @Test
    public void scoreEating() {

        // Balance
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b,
                true, true, true, true));

        // Under and balanced produces under
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_a,
                true, true, true, false));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b,
                true, true, true, false));

        // Over and balanced produces over
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreEating(
            ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_c,
                false, false, false, true));
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b,
                false, false, false, true));
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_b,
                true, true, false, true));

        // Unbalanced
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_c,
                false, false, false, false));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_a,
                false, false, true, false));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_b, ScoringAlgorithms.INPUT_c,
                false, false, true, false));

        // Error
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreEating(
                100, ScoringAlgorithms.INPUT_c, ScoringAlgorithms.INPUT_c,
                false, false, false, false));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_a, -5, ScoringAlgorithms.INPUT_a,
                false, false, true, false));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreEating(
                ScoringAlgorithms.INPUT_a, ScoringAlgorithms.INPUT_b, 800,
                false, false, true, false));
    }

    @Test
    public void scoreSpeaking() {

        // balanced
        assertEquals(ScoringAlgorithms.SCORE_BALANCED, ScoringAlgorithms.scoreSpeaking(
                5, true, false, false, false));

        // low for any speakingRating < 5 and any combination of booleans
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSpeaking(
                4, true, false, false, false));
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSpeaking(
                2, true, true, false, false));

        // low if speakingRating 5 but booleans reduce score
        assertEquals(ScoringAlgorithms.SCORE_UNDER, ScoringAlgorithms.scoreSpeaking(
                5, false, false, false, false));

        // high if socialMedia is true and/or impactHealth is true
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreSpeaking(
                5, true, false, true, false));
        assertEquals(ScoringAlgorithms.SCORE_OVER, ScoringAlgorithms.scoreSpeaking(
                5, true, false, false, true));

        // off balanced
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreSpeaking(
                1, true, false, true, false));
        assertEquals(ScoringAlgorithms.SCORE_UNBALANCED, ScoringAlgorithms.scoreSpeaking(
                5, true, true, false, true));

        // error - speaking out of range 1-5
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreSpeaking(
                0, true, true, false, true));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreSpeaking(
                -1, true, true, false, true));
        assertEquals(ScoringAlgorithms.SCORE_ERROR, ScoringAlgorithms.scoreSpeaking(
                6, true, true, false, true));

    }
}