package mergerobotics.memo.db;

import android.provider.BaseColumns;

public final class EventsContract implements BaseColumns {
        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private EventsContract() {}

        /* Inner class that defines the table contents, from mergserv:
        *
        * "CREATE TABLE events (
        * sync_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
        * type VARCHAR(5) NOT NULL, ***
        * team_number SMALLINT UNSIGNED NOT NULL, ***
        * match_number TINYINT UNSIGNED NOT NULL,  ***
        * competition VARCHAR(16) NOT NULL,
        * success TINYINT NOT NULL,
        * start_time TINYINT UNSIGNED NOT NULL,
        * end_time TINYINT UNSIGNED NOT NULL,
        * extra VARCHAR(64) NOT NULL,
        * scout_name VARCHAR(16) NOT NULL,
        * scout_team SMALLINT UNSIGNED NOT NULL,
        * signature TEXT NOT NULL)",
         */
        public static class EventsEntry implements BaseColumns {
            public static final String TABLE_NAME = "events";
            public static final String COLUMN_NAME_SYNC_TIME = "sync_time";
            public static final String COLUMN_NAME_TYPE = "type";
            public static final String COLUMN_NAME_TEAM = "team";
            public static final String COLUMN_NAME_MATCH = "match_number";
            public static final String COLUMN_NAME_COMPETITION = "competition";
            public static final String COLUMN_NAME_SUCCESS = "success";
            public static final String COLUMN_NAME_START_TIME = "start_time";
            public static final String COLUMN_NAME_END_TIME = "end_time";
            public static final String COLUMN_NAME_EXTRA= "extra";
            public static final String COLUMN_NAME_SCOUT_NAME = "scout_name";
            public static final String COLUMN_NAME_SCOUT_TEAM = "scout_team";
            public static final String COLUMN_NAME_SIGNATURE = "signature";
        }

        public static final String DATABASE_NAME = "mergdb";    // Database Name
        public static final int DATABASE_VERSION = 1;           // Database Version
    }
