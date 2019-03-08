package macewan_dust.smiles.database;

public class SMILES_DatabaseSchema {

    /**
     * Score user data table constants
     */
    public static final class ScoreTable {
        public static final String NAME = "scoreTable";

        /**
         * Column class
         */
        public static final class Columns {
            public static final String SCORE_ID = "uuid";
            public static final String SLEEP = "sleep";
            public static final String MOVEMENT = "movement";
            public static final String IMAGINATION = "imagination";
            public static final String LAUGHTER = "laughter";
            public static final String EATING = "eating";
            public static final String SPEAKING = "speaking";
            public static final String DATE = "date";
        }
    }

    /**
     * Raw user data table constants
     */
    public static final class RawTable {
        public static final String NAME = "rawTable";

        /**
         * Column class
         */
        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String SLEEP1 = "sleep1_time";
            public static final String SLEEP2 = "sleep2_interruptions";
            public static final String MOVEMENT1 = "movement1_aerobic";
            public static final String MOVEMENT2 = "movement2_bone_and_muscle";
            public static final String MOVEMENT3 = "movement3_relaxation";

            public static final String IMAGINATION1 = "imagination1_mindfulness";
            public static final String IMAGINATION2 = "imagination2_meditation";
            public static final String IMAGINATION3 = "imagination3_creativity";

            public static final String LAUGHTER1 = "laughter1_rating";
            // eating to be determined
            //public static final String EATING = "eating";
            public static final String SPEAKING1 = "speaking1_rating";
            public static final String SPEAKING2 = "speaking2_debrief";
            public static final String SPEAKING3 = "speaking3_prevented";
            public static final String SPEAKING4 = "speaking4_social_media";
            public static final String SPEAKING5 = "speaking5_negative_impact";

        }
    }
}
