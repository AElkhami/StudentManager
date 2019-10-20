package com.elkhamitech.studentmanagerr.data.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.elkhamitech.studentmanagerr.data.model.FinalModel;
import com.elkhamitech.studentmanagerr.data.model.MidtermModel;
import com.elkhamitech.studentmanagerr.data.model.OralModel;
import com.elkhamitech.studentmanagerr.data.model.QuizModel;
import com.elkhamitech.studentmanagerr.data.model.SubjectsModel;
import com.elkhamitech.studentmanagerr.data.model.TaskItemModel;
import com.elkhamitech.studentmanagerr.data.model.TaskModel;
import com.elkhamitech.studentmanagerr.data.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ElkhamiTech on 1/21/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    Context mContext;

    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "StudentManager.db";


    //==================================================================
    //TABLES
    //==================================================================
    // Table Name
    private static final String TABLE_USER = "user";

    // Column names
    private static final String ROW_ID = "row_id";
    private static final String USER_NAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String AGE = "age";
    private static final String EMAIL = "email";
    private static final String USER_IMG = "user_img";

    //=================================================================
    // Table Name
    private static final String TABLE_TASK = "task";
    // Column names
    private static final String TASK_ID = "row_id";
    private static final String TASK_NAME = "task_name";
    private static final String TASK_CREATED = "task_created";
    private static final String STUDENT_TASK = "student_task";

    //=================================================================
    // Table Name
    private static final String TABLE_ITEM = "item";
    // Column names
    private static final String ITEM_ID = "row_id";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_DONE = "item_done";
    private static final String TASK_ITEM = "task_item";

    //=================================================================
    // Table Name
    private static final String TABLE_SUBJECT = "subject";
    // Column names
    private static final String SUBJECT_ID = "row_id";
    private static final String SUBJECT_NAME = "subject_name";
    private static final String SUBJECT_LOCATION = "subject_location";
    private static final String SUBJECT_DATE = "subject_date";
    private static final String SUBJECT_TIME = "subject_time";
    private static final String STUDENT_SUBJECT = "student_subject";

    //=================================================================
    // Table Name
    private static final String TABLE_ORAL = "progressoral";
    // Column names
    private static final String PROGRESS_ORAL_ID = "row_id";
    private static final String PROGRESS_ORAL = "progress_oral";
    private static final String PROGRESS_ORAL_FROM = "progress_oral_fom";

    private static final String SUBJECT_ORAL = "subject_oral";

    //=================================================================
    // Table Name
    private static final String TABLE_QUIZ = "progressquiz";
    // Column names
    private static final String PROGRESS_QUIZ_ID = "row_id";
    private static final String PROGRESS_QUIZ = "progress_quiz";
    private static final String PROGRESS_QUIZ_FROM = "progress_quiz_from";

    private static final String SUBJECT_QUIZ = "subject_quiz";

    //=================================================================
    // Table Name
    private static final String TABLE_MIDTERM = "progressmidterm";
    // Column names
    private static final String PROGRESS_MIDTERM_ID = "row_id";
    private static final String PROGRESS_MIDTERM = "progress_midterm";
    private static final String PROGRESS_MIDTERM_FROM = "progress_midterm_from";

    private static final String SUBJECT_MIDTERM = "subject_midterm";

    //=================================================================
    // Table Name
    private static final String TABLE_FINAL = "progressfinal";
    // Column names
    private static final String PROGRESS_FINAL_ID = "row_id";
    private static final String PROGRESS_FINAL = "progress_final";
    private static final String PROGRESS_FINAL_FROM = "progress_final_from";
    private static final String SUBJECT_FINAL = "subject_final";

    //=================================================================


    //==================================================================
    // Create Statements
    //==================================================================
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "("
            + ROW_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + USER_NAME + " TEXT,"
            + PASSWORD + " TEXT,"
            + AGE + " TEXT,"
            + EMAIL + " TEXT,"
            + USER_IMG + " BLOB" + ");";
    //=================================================================
    public String CREATE_TABLE_TASK = "CREATE TABLE "
            + TABLE_TASK + "("
            + TASK_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + TASK_NAME + " TEXT,"
            + TASK_CREATED + " DATETIME,"
            + STUDENT_TASK + " INTEGER" +");";
    //=================================================================
    public String CREATE_TABLE_TASK_ITEM= "CREATE TABLE "
            + TABLE_ITEM + "("
            + ITEM_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + ITEM_NAME + " TEXT,"
            + ITEM_DONE + " TEXT,"
            + TASK_ITEM + " INTEGER" +");";
    //=================================================================
    public String CREATE_TABLE_SUBJECT = "CREATE TABLE "
            + TABLE_SUBJECT + "("
            + SUBJECT_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + SUBJECT_NAME + " TEXT,"
            + SUBJECT_LOCATION + " TEXT,"
            + SUBJECT_DATE + " TEXT,"
            + SUBJECT_TIME + " TEXT,"
            + STUDENT_SUBJECT + " INTEGER" + ");";
    //=================================================================
    public String CREATE_TABLE_ORAL = "CREATE TABLE "
            + TABLE_ORAL + "("
            + PROGRESS_ORAL_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + PROGRESS_ORAL + " INTEGER,"
            + PROGRESS_ORAL_FROM + " INTEGER,"
            + SUBJECT_ORAL + " INTEGER" + ");";
    //=================================================================
    public String CREATE_TABLE_QUIZ = "CREATE TABLE "
            + TABLE_QUIZ + "("
            + PROGRESS_QUIZ_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + PROGRESS_QUIZ + " INTEGER,"
            + PROGRESS_QUIZ_FROM + " INTEGER,"
            + SUBJECT_QUIZ + " INTEGER" + ");";
    //=================================================================
    public String CREATE_TABLE_MIDTERM = "CREATE TABLE "
            + TABLE_MIDTERM + "("
            + PROGRESS_MIDTERM_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + PROGRESS_MIDTERM + " INTEGER,"
            + PROGRESS_MIDTERM_FROM + " INTEGER,"
            + SUBJECT_MIDTERM + " INTEGER" + ");";
    //=================================================================
    public String CREATE_TABLE_FINAL = "CREATE TABLE "
            + TABLE_FINAL + "("
            + PROGRESS_FINAL_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT,"
            + PROGRESS_FINAL + " INTEGER,"
            + PROGRESS_FINAL_FROM + " INTEGER,"
            + SUBJECT_FINAL + " INTEGER" + ");";
    //=================================================================



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("Database Operations", "Database Created");

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK_ITEM);
        sqLiteDatabase.execSQL(CREATE_TABLE_SUBJECT);
        sqLiteDatabase.execSQL(CREATE_TABLE_ORAL);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUIZ);
        sqLiteDatabase.execSQL(CREATE_TABLE_MIDTERM);
        sqLiteDatabase.execSQL(CREATE_TABLE_FINAL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {

            // create new tables
//            onCreate(db);
        }


    }



    //==============================================================================================
    //-----------------------------------CRUD Functions---------------------------------------------
    //==============================================================================================


    //==================================================================
    // Insert Statements
    //==================================================================

    public long createUser(UserModel user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getUser_name());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL, user.getE_mail());
        values.put(AGE, user.getAge());
        values.put(USER_IMG, user.getUserImage());

        user.setRow_id(db.insert(TABLE_USER, null, values));

        return user.getRow_id();
    }


    //-------------------------------------------------------------------

    public long createSubject(SubjectsModel subjectsModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SUBJECT_NAME, subjectsModel.getSubjectName());
        values.put(SUBJECT_LOCATION, subjectsModel.getClassLocation());
        values.put(SUBJECT_DATE, subjectsModel.getDay());
        values.put(SUBJECT_TIME, subjectsModel.getTime());
        values.put(STUDENT_SUBJECT, subjectsModel.getUser_id());

        subjectsModel.setRow_id(db.insert(TABLE_SUBJECT, null, values));

        return subjectsModel.getRow_id();
    }

    //-------------------------------------------------------------------

    public long createTask(TaskModel task){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TASK_NAME,task.getTaskName());
        values.put(TASK_CREATED, getDateTime());
        values.put(STUDENT_TASK, task.getStudent_task());

        task.setRow_id(db.insert(TABLE_TASK, null, values));

        return task.getRow_id();

    }

    //-------------------------------------------------------------------

    public long createItem(TaskItemModel item){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_NAME,item.getItemName());
        values.put(ITEM_DONE, item.getIsDone());
        values.put(TASK_ITEM, item.getTask_item());

        item.setRow_id(db.insert(TABLE_ITEM, null, values));

        return item.getRow_id();

    }


    //-------------------------------------------------------------------

    public long createOral(OralModel oral){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PROGRESS_ORAL,oral.getValue());
        values.put(PROGRESS_ORAL_FROM, oral.getValueFrom());
        values.put(SUBJECT_ORAL, oral.getSubjectOral());

        oral.setRow_id(db.insert(TABLE_ORAL, null, values));

        return oral.getRow_id();

    }

    //-------------------------------------------------------------------

    public long createQuiz(QuizModel quiz){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PROGRESS_QUIZ,quiz.getValue());
        values.put(PROGRESS_QUIZ_FROM, quiz.getValueFrom());
        values.put(SUBJECT_QUIZ, quiz.getSubjectQuiz());

        quiz.setRow_id(db.insert(TABLE_QUIZ, null, values));

        return quiz.getRow_id();

    }

    //-------------------------------------------------------------------

    public long createMidterm(MidtermModel midterm){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PROGRESS_MIDTERM,midterm.getValue());
        values.put(PROGRESS_MIDTERM_FROM, midterm.getValueFrom());
        values.put(SUBJECT_MIDTERM, midterm.getSubjectMidterm());

        midterm.setRow_id(db.insert(TABLE_MIDTERM, null, values));

        return midterm.getRow_id();

    }

    //-------------------------------------------------------------------

    public long createFinal(FinalModel finalModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PROGRESS_FINAL,finalModel.getValue());
        values.put(PROGRESS_FINAL_FROM, finalModel.getValueFrom());
        values.put(SUBJECT_FINAL, finalModel.getSubjectFinal());

        finalModel.setRow_id(db.insert(TABLE_FINAL, null, values));

        return finalModel.getRow_id();

    }


    //==================================================================
    // Select Statements
    //==================================================================


    public UserModel getRegisteredUser(String Email, String Password) {
        SQLiteDatabase db = this.getReadableDatabase();

//        String selectQuery = "SELECT * FROM " + TABLE_USER
//                + " WHERE " + EMAIL + " = '" + Email + "'"
//                + " AND " + PASSWORD + " = '" + Password + "'";
//
//        Cursor cursor = db.rawQuery(selectQuery, null);

        String[] columns = { ROW_ID,USER_NAME, EMAIL, PASSWORD,AGE,USER_IMG };
        Cursor cursor = db.query(TABLE_USER, columns, "Email=? and Password=?", new String[]{Email,Password},
                null, null, null);
        UserModel user = new UserModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    user.setRow_id(cursor.getLong(cursor.getColumnIndex(ROW_ID)));
                    user.setUser_name(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                    user.setE_mail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                    user.setAge(cursor.getString(cursor.getColumnIndex(AGE)));
                    user.setUserImage(cursor.getBlob(cursor.getColumnIndex(USER_IMG)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return user;
    }


    public UserModel getLoggedUser(long row_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { ROW_ID,USER_NAME, EMAIL, PASSWORD,AGE,USER_IMG };
        Cursor cursor = db.query(TABLE_USER, columns, "ROW_ID=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        UserModel user = new UserModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    user.setRow_id(cursor.getLong(cursor.getColumnIndex(ROW_ID)));
                    user.setUser_name(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                    user.setE_mail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                    user.setAge(cursor.getString(cursor.getColumnIndex(AGE)));
                    user.setUserImage(cursor.getBlob(cursor.getColumnIndex(USER_IMG)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return user;
    }

    public SubjectsModel getSubject(long row_id){

        SQLiteDatabase db = this.getReadableDatabase();
        SubjectsModel subject = new SubjectsModel();

        String[] columns = { SUBJECT_ID,SUBJECT_NAME, SUBJECT_LOCATION, SUBJECT_DATE,SUBJECT_TIME,STUDENT_SUBJECT };
        Cursor cursor = db.query(TABLE_SUBJECT, columns, "row_id=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                subject.setRow_id(cursor.getInt((cursor.getColumnIndex(SUBJECT_ID))));
                subject.setSubjectName((cursor.getString(cursor.getColumnIndex(SUBJECT_NAME))));
                subject.setClassLocation(cursor.getString(cursor.getColumnIndex(SUBJECT_LOCATION)));
                subject.setDay(cursor.getString(cursor.getColumnIndex(SUBJECT_DATE)));
                subject.setTime(cursor.getString(cursor.getColumnIndex(SUBJECT_TIME)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return subject;

    }

    public TaskModel getTask(long row_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { TASK_ID,TASK_NAME, TASK_CREATED, STUDENT_TASK};
        Cursor cursor = db.query(TABLE_TASK, columns, "ROW_ID=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        TaskModel task = new TaskModel();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    task.setRow_id(cursor.getLong(cursor.getColumnIndex(TASK_ID)));
                    task.setTaskName(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                    task.setCreated(cursor.getString(cursor.getColumnIndex(TASK_CREATED)));
                    task.setStudent_task(cursor.getLong(cursor.getColumnIndex(STUDENT_TASK)));
                } while (cursor.moveToNext());
            }
        cursor.close();
        return task;

    }


    //==================================================================
    // Select ALL Statements
    //==================================================================
    public List<SubjectsModel> getUserSubject(long row_id){
        List<SubjectsModel> subjects = new ArrayList<SubjectsModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { SUBJECT_ID,SUBJECT_NAME, SUBJECT_LOCATION, SUBJECT_DATE,SUBJECT_TIME,STUDENT_SUBJECT };
        Cursor cursor = db.query(TABLE_SUBJECT, columns, "STUDENT_SUBJECT=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubjectsModel subject = new SubjectsModel();
                subject.setRow_id(cursor.getInt((cursor.getColumnIndex(SUBJECT_ID))));
                subject.setSubjectName((cursor.getString(cursor.getColumnIndex(SUBJECT_NAME))));
                subject.setClassLocation(cursor.getString(cursor.getColumnIndex(SUBJECT_LOCATION)));
                subject.setDay(cursor.getString(cursor.getColumnIndex(SUBJECT_DATE)));
                subject.setTime(cursor.getString(cursor.getColumnIndex(SUBJECT_TIME)));

                // adding to the list
                subjects.add(subject);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return subjects;
    }

    //----------------------------------------------------------------------------------------------
    public List<TaskModel> getUserTasks(long row_id){
        List<TaskModel> tasks = new ArrayList<TaskModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { TASK_ID,TASK_NAME, TASK_CREATED, STUDENT_TASK };
        Cursor cursor = db.query(TABLE_TASK, columns, "STUDENT_TASK=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();
                task.setRow_id(cursor.getInt((cursor.getColumnIndex(TASK_ID))));
                task.setTaskName((cursor.getString(cursor.getColumnIndex(TASK_NAME))));
                task.setCreated(cursor.getString(cursor.getColumnIndex(TASK_CREATED)));
                task.setStudent_task(cursor.getInt(cursor.getColumnIndex(STUDENT_TASK)));

                // adding to the list
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    //----------------------------------------------------------------------------------------------
    public List<TaskItemModel> getTaskItems(long row_id){
        List<TaskItemModel> items = new ArrayList<TaskItemModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { ITEM_ID,ITEM_NAME, ITEM_DONE, TASK_ITEM };
        Cursor cursor = db.query(TABLE_ITEM, columns, "TASK_ITEM=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskItemModel item = new TaskItemModel();
                item.setRow_id(cursor.getInt((cursor.getColumnIndex(ITEM_ID))));
                item.setItemName((cursor.getString(cursor.getColumnIndex(ITEM_NAME))));
                item.setIsDone(cursor.getString(cursor.getColumnIndex(ITEM_DONE)));
                item.setTask_item(cursor.getInt(cursor.getColumnIndex(TASK_ITEM)));

                // adding to the list
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    //----------------------------------------------------------------------------------------------
    public List<OralModel> getOral(long row_id){
        List<OralModel> oralModels = new ArrayList<OralModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { PROGRESS_ORAL_ID,PROGRESS_ORAL,PROGRESS_ORAL_FROM,SUBJECT_ORAL };
        Cursor cursor = db.query(TABLE_ORAL, columns, "SUBJECT_ORAL=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OralModel oral = new OralModel();
                oral.setRow_id(cursor.getInt((cursor.getColumnIndex(PROGRESS_ORAL_ID))));
                oral.setValue((cursor.getString(cursor.getColumnIndex(PROGRESS_ORAL))));
                oral.setValueFrom(cursor.getString(cursor.getColumnIndex(PROGRESS_ORAL_FROM)));
                oral.setSubjectOral(cursor.getLong(cursor.getColumnIndex(SUBJECT_ORAL)));

                // adding to the list
                oralModels.add(oral);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return oralModels;
    }

    //----------------------------------------------------------------------------------------------
    public List<OralModel> getAllOral(){
        List<OralModel> oralModels = new ArrayList<OralModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { PROGRESS_ORAL_ID,PROGRESS_ORAL,PROGRESS_ORAL_FROM,SUBJECT_ORAL };
        Cursor cursor = db.query(TABLE_ORAL, columns, null, null,
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OralModel oral = new OralModel();
                oral.setRow_id(cursor.getInt((cursor.getColumnIndex(PROGRESS_ORAL_ID))));
                oral.setValue((cursor.getString(cursor.getColumnIndex(PROGRESS_ORAL))));
                oral.setValueFrom(cursor.getString(cursor.getColumnIndex(PROGRESS_ORAL_FROM)));
                oral.setSubjectOral(cursor.getLong(cursor.getColumnIndex(SUBJECT_ORAL)));

                // adding to the list
                oralModels.add(oral);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return oralModels;
    }

    //----------------------------------------------------------------------------------------------
    public List<QuizModel> getQuiz(long row_id){
        List<QuizModel> quizes = new ArrayList<QuizModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { PROGRESS_QUIZ_ID,PROGRESS_QUIZ, PROGRESS_QUIZ_FROM,SUBJECT_QUIZ };
        Cursor cursor = db.query(TABLE_QUIZ, columns, "SUBJECT_QUIZ=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuizModel item = new QuizModel();
                item.setRow_id(cursor.getInt((cursor.getColumnIndex(PROGRESS_QUIZ_ID))));
                item.setValue((cursor.getString(cursor.getColumnIndex(PROGRESS_QUIZ))));
                item.setValueFrom(cursor.getString(cursor.getColumnIndex(PROGRESS_QUIZ_FROM)));
                item.setSubjectQuiz(cursor.getLong(cursor.getColumnIndex(SUBJECT_QUIZ)));

                // adding to the list
                quizes.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return quizes;
    }

    //----------------------------------------------------------------------------------------------
    public List<MidtermModel> getMidterm(long row_id){
        List<MidtermModel> midterms = new ArrayList<MidtermModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { PROGRESS_MIDTERM_ID,PROGRESS_MIDTERM, PROGRESS_MIDTERM_FROM,SUBJECT_MIDTERM};
        Cursor cursor = db.query(TABLE_MIDTERM, columns, "SUBJECT_MIDTERM=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MidtermModel midtermModel = new MidtermModel();
                midtermModel.setRow_id(cursor.getInt((cursor.getColumnIndex(PROGRESS_MIDTERM_ID))));
                midtermModel.setValue((cursor.getString(cursor.getColumnIndex(PROGRESS_MIDTERM))));
                midtermModel.setValueFrom(cursor.getString(cursor.getColumnIndex(PROGRESS_MIDTERM_FROM)));
                midtermModel.setSubjectMidterm(cursor.getLong(cursor.getColumnIndex(SUBJECT_MIDTERM)));

                // adding to the list
                midterms.add(midtermModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return midterms;
    }

    //----------------------------------------------------------------------------------------------
    public List<FinalModel> getFinal(long row_id){
        List<FinalModel> finals = new ArrayList<FinalModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { PROGRESS_FINAL_ID,PROGRESS_FINAL, PROGRESS_FINAL_FROM,SUBJECT_FINAL };
        Cursor cursor = db.query(TABLE_FINAL, columns, "SUBJECT_FINAL=?", new String[]{String.valueOf(row_id)},
                null, null, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FinalModel finalModel = new FinalModel();
                finalModel.setRow_id(cursor.getInt((cursor.getColumnIndex(PROGRESS_FINAL_ID))));
                finalModel.setValue((cursor.getString(cursor.getColumnIndex(PROGRESS_FINAL))));
                finalModel.setValueFrom(cursor.getString(cursor.getColumnIndex(PROGRESS_FINAL_FROM)));
                finalModel.setSubjectFinal(cursor.getLong(cursor.getColumnIndex(SUBJECT_FINAL)));

                // adding to the list
                finals.add(finalModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return finals;
    }

    //==================================================================
    // Update Statements
    //==================================================================

    public int updateUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_NAME, userModel.getUser_name());
        values.put(PASSWORD, userModel.getPassword());
        values.put(AGE, userModel.getAge());
        values.put(EMAIL, userModel.getE_mail());

        // updating row
        return db.update(TABLE_USER, values, ROW_ID + " = ?",
                new String[] { String.valueOf(userModel.getRow_id())});
    }

    //------------------------------------------------------------------
    public int updateUserPp(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USER_IMG, userModel.getUserImage());

        // updating row
        return db.update(TABLE_USER, values, ROW_ID + " = ?",
                new String[] { String.valueOf(userModel.getRow_id())});
    }
    //------------------------------------------------------------------
    public int updateSubject(SubjectsModel subjectsModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(SUBJECT_NAME, subjectsModel.getSubjectName());
        values.put(SUBJECT_LOCATION, subjectsModel.getClassLocation());
        values.put(SUBJECT_DATE, subjectsModel.getDay());
        values.put(SUBJECT_TIME, subjectsModel.getTime());

        // updating row
        return db.update(TABLE_SUBJECT, values, SUBJECT_ID + " = ?",
                new String[] { String.valueOf(subjectsModel.getRow_id())});
    }

    //------------------------------------------------------------------
    public int updateTask(TaskModel task, long fk) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TASK_NAME, task.getTaskName());

        // updating row
        return db.update(TABLE_TASK, values, TASK_ID + " = ?",
                new String[] { String.valueOf(fk)});
    }

    //------------------------------------------------------------------
    public int updateItemName(TaskItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_NAME, item.getItemName());
        values.put(ITEM_DONE, item.getIsDone());

        // updating row
        return db.update(TABLE_ITEM, values, ITEM_ID + " = ?",
                new String[] { String.valueOf(item.getRow_id())});
    }

    //------------------------------------------------------------------
    public int updateItemCheck(TaskItemModel item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ITEM_DONE, item.getIsDone());

        // updating row
        return db.update(TABLE_ITEM, values, ITEM_ID + " = ?",
                new String[] { String.valueOf(item.getRow_id())});
    }


    //==================================================================
    // Delete Statements
    //==================================================================
    public long deleteUser(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, ROW_ID + " = ?",
                new String[] { String.valueOf(row_id) });

        return row_id;
    }

    public long deleteSubject(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUBJECT, SUBJECT_ID + " = ?",
                new String[] { String.valueOf(row_id) });

        db.delete(TABLE_ORAL, SUBJECT_ORAL + " = ?",
                new String[] { String.valueOf(row_id) });

        db.delete(TABLE_QUIZ, SUBJECT_QUIZ + " = ?",
                new String[] { String.valueOf(row_id) });

        db.delete(TABLE_MIDTERM, SUBJECT_MIDTERM + " = ?",
                new String[] { String.valueOf(row_id) });

        db.delete(TABLE_FINAL, SUBJECT_FINAL + " = ?",
                new String[] { String.valueOf(row_id) });

        return row_id;
    }

    public long deleteTask(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK, TASK_ID + " = ?",
                new String[] { String.valueOf(row_id) });

        db.delete(TABLE_ITEM, TASK_ITEM + " = ?",
                new String[] { String.valueOf(row_id) });

        return row_id;
    }

    public long deleteItem(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEM, ITEM_ID + " = ?",
                new String[] { String.valueOf(row_id) });
        return row_id;
    }

    public long deleteOral(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORAL, PROGRESS_ORAL_ID + " = ?",
                new String[] { String.valueOf(row_id) });
        return row_id;
    }

    public long deleteQuiz(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUIZ, PROGRESS_QUIZ_ID + " = ?",
                new String[] { String.valueOf(row_id) });
        return row_id;
    }

    public long deleteMidterm(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MIDTERM, PROGRESS_MIDTERM_ID + " = ?",
                new String[] { String.valueOf(row_id) });
        return row_id;
    }

    public long deleteFinal(long row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FINAL, PROGRESS_FINAL_ID + " = ?",
                new String[] { String.valueOf(row_id) });
        return row_id;
    }



    //==================================================================
    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm dd-MMM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
