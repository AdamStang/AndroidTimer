package com.example.timer_skuska;

import android.provider.BaseColumns;

public class Workouts {

    private Workouts() {}

    public static final class WorkoutsEntry implements BaseColumns {
        public static final String TABLE_NAME = "workouts";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_WORK = "WORK";
        public static final String COLUMN_REST = "REST";
        public static final String COLUMN_ROUNDS = "ROUNDS";
    }
}
