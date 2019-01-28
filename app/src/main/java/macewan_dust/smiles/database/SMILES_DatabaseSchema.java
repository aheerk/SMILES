package macewan_dust.smiles.database;

public class SMILES_DatabaseSchema {

    /**
     * Table class
     */
    public static final class ScoreTable {
        public static final String NAME = "questions";

        /**
         * Column class
         */
        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String SLEEP = "sleep";
            public static final String MOVEMENT = "movement";
            public static final String IMAGINATION = "imagination";
            public static final String LAUGHTER = "laughter";
            public static final String EATING = "eating";
            public static final String SPEAKING = "speaking";
            public static final String DATE = "date";
        }
    }
}
