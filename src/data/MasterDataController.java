package data;


import items.AnswerItem;
import items.CategoryItem;
import items.Consultant;
import items.MessageItem;
import items.QuestionItem;

import java.io.File;
import java.util.ArrayList;

import utils.Constants;
import utils.DatabaseController;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import enums.ApplicationState;
import enums.ConnectionState;

public class MasterDataController extends MasterData{
	private static MasterDataController masterDataController;

	private Context context;
	private static DatabaseController databaseHelper;
	public SQLiteDatabase database;

	public int unreadAnswersCount;
	public String lastUnreadAnswer;
	public boolean isNotificate;

	private MasterDataController()
	{
		this.context = CustomApplication.getAppContext();
		databaseHelper = new DatabaseController();
		database = databaseHelper.getWritableDatabase();

		unreadAnswersCount = 0;
		lastUnreadAnswer = "";
		isNotificate = false;
	}

	public static MasterDataController getInstance()
	{
		if(masterDataController == null)
		{
			if(instance == null)
				instance = new MasterData();
			masterDataController = new MasterDataController();
		}
		return masterDataController;
	}

	public void readQuestionsFromDatabase()
	{
		Cursor cursor = database.query(DatabaseController.TABLE_NAME_QUESTIONS, 
				null, 
				null, 
				null, 
				null, 
				null, 
				null);

		questions.clear();

		long start_time = System.currentTimeMillis();
		try
		{
			int indexColumnID = cursor.getColumnIndex(DatabaseController.QUESTIONS_COLUMN_ID);
			int questionColumnID = cursor.getColumnIndex(DatabaseController.QUESTIONS_COLUMN_NAME);
			int cookieColumnID = cursor.getColumnIndex(DatabaseController.QUESTIONS_COLUMN_COOKIE);
			int timeColumnID = cursor.getColumnIndex(DatabaseController.QUESTIONS_COLUMN_TIME);
			int categoryColumnID = cursor.getColumnIndex(DatabaseController.QUESTIONS_COLUMN_ID_CATEGORY);

			if(cursor.moveToFirst())
			{
				do {
					//////////////
					QuestionItem question = new QuestionItem(cursor.getString(questionColumnID),
							cursor.getString(categoryColumnID), 
							cursor.getLong(timeColumnID),
							0, 
							cursor.getString(cookieColumnID),
							cursor.getLong(indexColumnID));
					
					questions.add(question);

					if(question.getCookie() == null)
						questionsUnsent.add(question);

					readAnswersFromDatabase(question);
				}
				while(cursor.moveToNext());
			}
		}
		finally
		{
			cursor.close();
		}

		Log.d(Constants.TAG_MESSAGE, "Время чтения вопросов и ответов с бд - " + (System.currentTimeMillis() - start_time));
	}

	public void readCategoriesFromDatabase()
	{
		Cursor cursor = database.query(DatabaseController.TABLE_NAME_CATEGORIES, 
				null, 
				null, 
				null, 
				null, 
				null, 
				null);

		categories.clear();

		try
		{
			int indexColumnID = cursor.getColumnIndex(DatabaseController.CATEGORIES_COLUMN_ID);
			int nameColumnID = cursor.getColumnIndex(DatabaseController.CATEGORIES_COLUMN_NAME);
			int countColumnID = cursor.getColumnIndex(DatabaseController.CATEGORIES_COLUMN_COUNT);

			if(cursor.moveToFirst())
				do
				{
					categories.add(new CategoryItem(cursor.getString(nameColumnID),
							cursor.getInt(countColumnID)));
				} while(cursor.moveToNext());
		}
		finally
		{
			cursor.close();
		}
	}

	public void readAnswersFromDatabase(QuestionItem question)
	{
		Cursor cursor = database.query(DatabaseController.TABLE_NAME_ANSWERS, 
				null, 
				DatabaseController.ANSWERS_COLUMN_ID_QUESTION + "=" + question.getTableId(), 
				null, 
				null, 
				null, 
				null);

		question.getAnswers().clear();

		try
		{
			int indexColumnID = cursor.getColumnIndex(DatabaseController.ANSWERS_COLUMN_ID);
			int questionIndexColumnID = cursor.getColumnIndex(DatabaseController.ANSWERS_COLUMN_ID_QUESTION);
			int consultantColumnID = cursor.getColumnIndex(DatabaseController.ANSWERS_COLUMN_ID_CONSULTANT);


			if(cursor.moveToFirst())
				do
				{
					// ERROR
					AnswerItem answer = new AnswerItem(question, 
							findConsultantById(cursor.getInt(consultantColumnID)),
							cursor.getLong(indexColumnID));
					
					question.addAnswer(answer);
					answers.add(answer);

					readMessegesFromDatabase(answer);
				} while(cursor.moveToNext());
		}
		finally
		{
			cursor.close();
		}
	}

	public void readMessegesFromDatabase(AnswerItem answer)
	{
		Cursor cursor = database.query(DatabaseController.TABLE_NAME_MESSAGES, 
				null, 
				DatabaseController.MESSAGES_COLUMN_ID_ANSWER + "=" + answer.getTableId(), 
				null, 
				null, 
				null, 
				null);

		answer.getMessages().clear();

		try
		{
			int indexColumnID = cursor.getColumnIndex(DatabaseController.ANSWERS_COLUMN_ID);
			int questionIndexColumnID = cursor.getColumnIndex(DatabaseController.ANSWERS_COLUMN_ID_QUESTION);
			int textColumnId = cursor.getColumnIndex(DatabaseController.MESSAGES_COLUMN_TEXT);
			int timeColumnId = cursor.getColumnIndex(DatabaseController.MESSAGES_COLUMN_TIME);
			int sideColumnId = cursor.getColumnIndex(DatabaseController.MESSAGES_COLUMN_SIDE);

			if(cursor.moveToFirst())
				do
				{
					MessageItem message = new MessageItem(answer,
							cursor.getString(textColumnId),
							cursor.getLong(timeColumnId),
							Boolean.parseBoolean(cursor.getString(sideColumnId)));
					
					messages.add(message);
					answer.getMessages().add(message);
				} while(cursor.moveToNext());
		}
		finally
		{
			cursor.close();
		}
	}

	public Consultant findConsultantById(long id)
	{
		if(consultants != null)
		{
			for(Consultant consultant : consultants)
			{
				if(id == consultant.getPublicId())
					return consultant;
			}
		}

		return null;
	}

	public void readConsultantsFromDatabase()
	{
		Cursor cursor = database.query(DatabaseController.TABLE_NAME_CONSULTANTS, 
				null, 
				null, 
				null, 
				null, 
				null, 
				null);

		consultants.clear();

		try
		{
			int publicIdColumnID = cursor.getColumnIndex(DatabaseController.CONSULTANTS_COLUMN_PUBLIC_ID);
			int nameColumnID = cursor.getColumnIndex(DatabaseController.CONSULTANTS_COLUMN_NAME);
			int websiteColumnID = cursor.getColumnIndex(DatabaseController.CONSULTANTS_COLUMN_WEBSITE);
			int infoColumnID = cursor.getColumnIndex(DatabaseController.CONSULTANTS_COLUMN_INFO);

			if(cursor.moveToFirst())
				do
				{
					consultants.add(new Consultant(cursor.getInt(publicIdColumnID), 
							cursor.getString(nameColumnID),
							cursor.getString(websiteColumnID),
							cursor.getString(infoColumnID)));
				} while(cursor.moveToNext());
		}
		finally
		{
			cursor.close();
		}
	}

	public void saveCategoriesToDatabase()
	{
		Log.d(Constants.TAG_CALL, "MasterDataController - saveCategoriesToDatabase()");
		ContentValues contentValue = new ContentValues();
		database.beginTransaction();
		clearTable(DatabaseController.TABLE_NAME_CATEGORIES);

		for(CategoryItem category : categories)
		{
			contentValue.put(DatabaseController.CATEGORIES_COLUMN_NAME, category.getName());
			contentValue.put(DatabaseController.CATEGORIES_COLUMN_COUNT, category.getCount());
			database.insert(DatabaseController.TABLE_NAME_CATEGORIES, null, contentValue);
			contentValue.clear();
		}
		database.setTransactionSuccessful();
		database.endTransaction();
	}

//	public void saveConsultantsToDatabase()
//	{
//		Log.d(Constants.TAG_CALL, "MasterDataController - saveConsultantsToDatabase()");
//		ContentValues contentValue = new ContentValues();
//		database.beginTransaction();
//		clearTable(DatabaseController.TABLE_NAME_CONSULTANTS);
//
//		for(Consultant consultant : consultants)
//		{
//			contentValue.put(DatabaseController.CONSULTANTS_COLUMN_PUBLIC_ID, consultant.getPublicId());
//			contentValue.put(DatabaseController.CONSULTANTS_COLUMN_NAME, consultant.getName());
//			contentValue.put(DatabaseController.CONSULTANTS_COLUMN_WEBSITE, consultant.getWebsite());
//			contentValue.put(DatabaseController.CONSULTANTS_COLUMN_INFO, consultant.getInfo());
//			database.insert(DatabaseController.TABLE_NAME_CONSULTANTS, null, contentValue);
//			contentValue.clear();
//		}
//		database.setTransactionSuccessful();
//		database.endTransaction();
//	}

	public long saveConsultantToDatabase(Consultant consultant)
	{
		//Log.d(Constants.TAG_CALL, "MasterDataController - saveConsultantToDatabase()");
		ContentValues contentValue = new ContentValues();
		database.beginTransaction();

		contentValue.put(DatabaseController.CONSULTANTS_COLUMN_PUBLIC_ID, consultant.getPublicId());
		contentValue.put(DatabaseController.CONSULTANTS_COLUMN_NAME, consultant.getName());
		contentValue.put(DatabaseController.CONSULTANTS_COLUMN_WEBSITE, consultant.getWebsite());
		contentValue.put(DatabaseController.CONSULTANTS_COLUMN_INFO, consultant.getInfo());
		long id = database.insert(DatabaseController.TABLE_NAME_CONSULTANTS, null, contentValue);
		contentValue.clear();

		database.setTransactionSuccessful();
		database.endTransaction();

		return id;
	}

	public long saveMessageToDatabase(MessageItem message)
	{
		//Log.d(Constants.TAG_CALL, "MasterDataController - saveMessageToDatabase()");
		ContentValues contentValue = new ContentValues();
		database.beginTransaction();

		contentValue.put(DatabaseController.MESSAGES_COLUMN_ID_ANSWER, message.getParentAnswer().getTableId());
		contentValue.put(DatabaseController.MESSAGES_COLUMN_TEXT, message.getText());
		contentValue.put(DatabaseController.MESSAGES_COLUMN_TIME, message.getTime());
		contentValue.put(DatabaseController.MESSAGES_COLUMN_SIDE, message.isMine());

		long id = database.insert(DatabaseController.TABLE_NAME_MESSAGES, null, contentValue);
		contentValue.clear();

		database.setTransactionSuccessful();
		database.endTransaction();

		return id;
	}

//	public void saveMessagesToDatabase(ArrayList<MessageItem> messeges)
//	{
//		Log.d(Constants.TAG_CALL, "MasterDataController - saveMessagesToDatabase()");
//		ContentValues contentValue = new ContentValues();
//		database.beginTransaction();
//
//		for(MessageItem message : messeges)
//		{
//			contentValue.put(DatabaseController.MESSAGES_COLUMN_ID_ANSWER, message.getParentAnswer().getTableId());
//			contentValue.put(DatabaseController.MESSAGES_COLUMN_TEXT, message.getText());
//			contentValue.put(DatabaseController.MESSAGES_COLUMN_TIME, message.getTime());
//			contentValue.put(DatabaseController.MESSAGES_COLUMN_SIDE, message.isMine());
//
//			database.insert(DatabaseController.TABLE_NAME_MESSAGES, null, contentValue);
//			contentValue.clear();
//		}
//
//		database.setTransactionSuccessful();
//		database.endTransaction();
//	}

	public long saveQuestionToDatabase(QuestionItem question)
	{
		Log.d(Constants.TAG_CALL, "MasterDataController - saveQuestionToDatabase()");
		ContentValues contentValue = new ContentValues();

		database.beginTransaction();

		contentValue.put(DatabaseController.QUESTIONS_COLUMN_ID_CATEGORY, question.getCategory());
		contentValue.put(DatabaseController.QUESTIONS_COLUMN_NAME, question.getText());
		contentValue.put(DatabaseController.QUESTIONS_COLUMN_TIME, question.getTime());
		contentValue.put(DatabaseController.QUESTIONS_COLUMN_COOKIE, question.getCookie());

		long id = database.insert(DatabaseController.TABLE_NAME_QUESTIONS, null, contentValue);
		question.setTableId(id);

		database.setTransactionSuccessful();
		database.endTransaction();

		return id;
	}

	public void updateQuestionCookieInDatabase(QuestionItem question)
	{
		Log.d(Constants.TAG_CALL, "MasterDataController - updateQuestionCookieInDatabase()");
		ContentValues contentValue = new ContentValues();

		contentValue.put(DatabaseController.QUESTIONS_COLUMN_COOKIE, question.getCookie());

		database.update(DatabaseController.TABLE_NAME_QUESTIONS, 
				contentValue, 
				DatabaseController.QUESTIONS_COLUMN_ID + " = " + question.getTableId(), 
				null);
	}

//	public void saveAnswersToDatabase(QuestionItem question)
//	{
//		Log.d(Constants.TAG_CALL, "MasterDataController - saveAnswersToDatabase()");
//		ContentValues contentValue = new ContentValues();
//
//		database.beginTransaction();
//
//		for(AnswerItem answer : question.getAnswers())
//		{
//			contentValue.put(DatabaseController.ANSWERS_COLUMN_ID_QUESTION, answer.getParent().getTableId());
//			contentValue.put(DatabaseController.ANSWERS_COLUMN_ID_CONSULTANT, answer.getConsultantId());
//			long id = database.insert(DatabaseController.TABLE_NAME_ANSWERS, null, contentValue);
//			Log.d("Insert answer id - ", String.valueOf(id));
//			contentValue.clear();
//		}
//
//		database.setTransactionSuccessful();
//		database.endTransaction();
//	}

//	public void saveAnswersToDatabase(ArrayList<AnswerItem> answers)
//	{
//		Log.d(Constants.TAG_CALL, "MasterDataController - saveAnswersToDatabase()");
//		ContentValues contentValue = new ContentValues();
//
//		database.beginTransaction();
//
//		for(AnswerItem answer : answers)
//		{
//			contentValue.put(DatabaseController.ANSWERS_COLUMN_ID_QUESTION, answer.getParent().getTableId());
//			contentValue.put(DatabaseController.ANSWERS_COLUMN_ID_CONSULTANT, answer.getConsultantId());
//			long id = database.insert(DatabaseController.TABLE_NAME_ANSWERS, null, contentValue);
//			answer.setTableId(id);
//			contentValue.clear();
//		}
//
//		if(MasterData.applicationState == ApplicationState.PASSIVE)
//		{
//			unreadAnswersCount += answers.size();
//			//lastUnreadAnswer = answers.get(answers.size() - 1)
//			isNotificate = false;
//		}
//
//		database.setTransactionSuccessful();
//		database.endTransaction();
//	}

	public long saveAnswerToDatabase(AnswerItem answer)
	{
		//Log.d(Constants.TAG_CALL, "MasterDataController - saveAnswerToDatabase()");
		ContentValues contentValue = new ContentValues();

		database.beginTransaction();

		contentValue.put(DatabaseController.ANSWERS_COLUMN_ID_QUESTION, answer.getParentQuestion().getTableId());
		contentValue.put(DatabaseController.ANSWERS_COLUMN_ID_CONSULTANT, answer.getConsultantId());
		long id = database.insert(DatabaseController.TABLE_NAME_ANSWERS, null, contentValue);
		answer.setTableId(id);
		contentValue.clear();

		database.setTransactionSuccessful();
		database.endTransaction();
		
		return id;
	}

	public boolean deleteDatabase()
	{
		Log.d(Constants.TAG_CALL, "MasterDataController - deleteDatabase()");
		File databaseFile = context.getDatabasePath(DatabaseController.DATABASE_NAME);
		return databaseFile.delete();
	}

	public void clearTable(String tableName)
	{
		database.delete(tableName, null, null);
	}

	public void clean()
	{
		Log.d(Constants.TAG_CALL, "MasterDataController - clean()");
		categories.clear();
		questions.clear();
		answers.clear();
		questionsQuery.clear();
		questionsUnsent.clear();
	}

	public void removeQuestion(QuestionItem question)
	{
		database.beginTransaction();
		questions.remove(question);

		// TODO удаление отовсюду

		long id = database.delete(DatabaseController.TABLE_NAME_QUESTIONS, 
				DatabaseController.QUESTIONS_COLUMN_ID + " = " + question.getTableId(), 
				null);
		database.delete(DatabaseController.TABLE_NAME_ANSWERS, 
				DatabaseController.ANSWERS_COLUMN_ID_QUESTION + " = " + question.getTableId(), 
				null);
		database.setTransactionSuccessful();
		database.endTransaction();
		Log.d("Delete answer id - ", String.valueOf(id));
	}

	public void removeCategory(int position)
	{
		// TODO
		categories.remove(position);
	}

	public void removeConsultant(Consultant consultant)
	{
		// TODO
	}

	public boolean isOnline()
	{
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if(networkInfo != null && networkInfo.isConnected())
			return true;
		return false;
	}

	public ConnectionState getConnectionState() {
		return connectionState;
	}

	public void setConnectionState(ConnectionState connectionState) {
		MasterData.connectionState = connectionState;
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(ApplicationState applicationState) {
		MasterData.applicationState = applicationState;

		if(MasterData.applicationState == ApplicationState.PASSIVE)
		{
			unreadAnswersCount = 0;
			lastUnreadAnswer = "";
			isNotificate = false;
		}
	}

	public ArrayList<CategoryItem> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<CategoryItem> categories) {
		MasterData.categories = categories;
	}

	public ArrayList<QuestionItem> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<QuestionItem> questions) {
		MasterData.questions = questions;
	}

	public ArrayList<QuestionItem> getQuestionsQuery() {
		return questionsQuery;
	}

	public void setQuestionsQuery(ArrayList<QuestionItem> questionsQuery) {
		MasterData.questionsQuery = questionsQuery;
	}

	public ArrayList<QuestionItem> getQuestionsUnsent() {
		return questionsUnsent;
	}

	public void setQuestionsUnsent(ArrayList<QuestionItem> questionsUnsent) {
		MasterData.questionsUnsent = questionsUnsent;
	}	

	public void addQuestion(QuestionItem question)
	{
		MasterData.questions.add(question);
	}
}
