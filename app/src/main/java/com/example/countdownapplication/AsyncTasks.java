package com.example.countdownapplication;

import android.database.Cursor;
import android.os.AsyncTask;

public class AsyncTasks {

    public static class InsertionAsyncTask extends AsyncTask<String, Void, Boolean> {

        public InsertionAsyncTask(){
            super();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(strings.length == 7){
                DatabaseHelper db = new DatabaseHelper(null);
                return db.insertData(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]),
                        Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5]),
                        Integer.parseInt(strings[6]));

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

    }

    public static class CursorAsyncTask extends AsyncTask<String, Void, Integer>{

        public CursorAsyncTask(){
            super();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String task = strings[0];
            DatabaseHelper db = new DatabaseHelper(null);
            Cursor cursor = db.getAllData();

            while(cursor.moveToNext()){
                if(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TASK))
                    .equals(task)){
                    return cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                }
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
