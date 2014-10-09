package utils;

import data.CustomApplication;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseController extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "database.db";
	public static final int DATABASE_VERSION = 5;

	public static final String TABLE_NAME_CATEGORIES = "categories";
	public static final String CATEGORIES_COLUMN_ID = "_id";
	public static final String CATEGORIES_COLUMN_NAME = "name";
	public static final String CATEGORIES_COLUMN_COUNT = "count";
		
	public static final String TABLE_NAME_QUESTIONS = "questions";
	public static final String QUESTIONS_COLUMN_ID = "_id";
	public static final String QUESTIONS_COLUMN_ID_CATEGORY = "id_category";
	public static final String QUESTIONS_COLUMN_NAME = "name";
	public static final String QUESTIONS_COLUMN_TIME = "time";
	public static final String QUESTIONS_COLUMN_COOKIE = "cookie";

	public static final String TABLE_NAME_ANSWERS = "answers";
	public static final String ANSWERS_COLUMN_ID = "_id";
	public static final String ANSWERS_COLUMN_ID_QUESTION = "id_question";
	public static final String ANSWERS_COLUMN_ID_CONSULTANT = "id_consultant";
	
	public static final String TABLE_NAME_CONSULTANTS = "consultants";
	public static final String CONSULTANTS_COLUMN_ID = "_id";
	public static final String CONSULTANTS_COLUMN_PUBLIC_ID = "id_public";
	public static final String CONSULTANTS_COLUMN_NAME = "name";
	public static final String CONSULTANTS_COLUMN_WEBSITE = "website";
	public static final String CONSULTANTS_COLUMN_INFO = "info";
	
	public static final String TABLE_NAME_MESSAGES = "messages";
	public static final String MESSAGES_COLUMN_ID = "_id";
	public static final String MESSAGES_COLUMN_ID_ANSWER = "id_answer";
	public static final String MESSAGES_COLUMN_TEXT = "text";
	public static final String MESSAGES_COLUMN_TIME = "time";
	public static final String MESSAGES_COLUMN_SIDE = "side";
	
	public DatabaseController() {
		super(CustomApplication.getAppContext(), DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(Constants.TAG_CALL, "DatabaseController - DatabaseController()");
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.d(Constants.TAG_CALL, "DatabaseController - onCreate()");
		String query;

		database.beginTransaction();
		try
		{
			query = "CREATE TABLE " 
					+ TABLE_NAME_CATEGORIES + " (" 
					+ CATEGORIES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ CATEGORIES_COLUMN_NAME + " TEXT NOT NULL, " 
					+ CATEGORIES_COLUMN_COUNT + " INTEGER NOT NULL);";
			Log.d("Query", query);
			database.execSQL(query);

			query = "CREATE TABLE " 
					+ TABLE_NAME_QUESTIONS + " (" 
					+ QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ QUESTIONS_COLUMN_ID_CATEGORY + " INTEGER NOT NULL, " 
					+ QUESTIONS_COLUMN_COOKIE + " TEXT,"
					+ QUESTIONS_COLUMN_NAME + " TEXT NOT NULL, "
					+ QUESTIONS_COLUMN_TIME + " LONG NOT NULL);";
			Log.d("Query", query);
			database.execSQL(query);
			
			query = "CREATE TABLE " 
					+ TABLE_NAME_ANSWERS + " (" 
					+ ANSWERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ ANSWERS_COLUMN_ID_QUESTION + " INTEGER NOT NULL, " 
					+ ANSWERS_COLUMN_ID_CONSULTANT + " INTEGER NOT NULL);";
			Log.d("Query", query);
			database.execSQL(query);
			
			query = "CREATE TABLE " 
					+ TABLE_NAME_CONSULTANTS + " (" 
					+ CONSULTANTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ CONSULTANTS_COLUMN_PUBLIC_ID + " INTEGER NOT NULL, " 
					+ CONSULTANTS_COLUMN_NAME + " TEXT NOT NULL, "
					+ CONSULTANTS_COLUMN_WEBSITE + " TEXT, "
					+ CONSULTANTS_COLUMN_INFO + " TEXT);";
			Log.d("Query", query);
			database.execSQL(query);
			
			query = "CREATE TABLE " 
					+ TABLE_NAME_MESSAGES + " (" 
					+ MESSAGES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ MESSAGES_COLUMN_ID_ANSWER + " INTEGER NOT NULL, " 
					+ MESSAGES_COLUMN_TEXT + " TEXT NOT NULL, "
					+ MESSAGES_COLUMN_TIME + " LONG NOT NULL, "
					+ MESSAGES_COLUMN_SIDE + " TEXT NOT NULL);";
			Log.d("Query", query);
			database.execSQL(query);
			
			database.setTransactionSuccessful();
		}
		finally
		{
			database.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.d(Constants.TAG_CALL, "DatabaseController - onUpgrade()");
		Log.d(Constants.TAG_MESSAGE, "Обновление базы данных с версии " 
				+ oldVersion + " до версии " + newVersion);

		database.beginTransaction();
		try
		{
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORIES);
			Log.d(Constants.TAG_MESSAGE, "Таблица " + TABLE_NAME_CATEGORIES
					+ " удалена");

			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUESTIONS);
			Log.d(Constants.TAG_MESSAGE, "Таблица " + TABLE_NAME_QUESTIONS
					+ " удалена");
			
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ANSWERS);
			Log.d(Constants.TAG_MESSAGE, "Таблица " + TABLE_NAME_ANSWERS
					+ " удалена");
			
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONSULTANTS);
			Log.d(Constants.TAG_MESSAGE, "Таблица " + TABLE_NAME_CONSULTANTS
					+ " удалена");
			
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MESSAGES);
			Log.d(Constants.TAG_MESSAGE, "Таблица " + TABLE_NAME_MESSAGES
					+ " удалена");
			
			database.setTransactionSuccessful();
		}
		finally
		{
			database.endTransaction();
		}

		onCreate(database);
	}

}
