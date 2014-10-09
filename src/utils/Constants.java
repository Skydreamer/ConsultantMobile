package utils;

public class Constants {
	private Constants() {
	}
	
	public static final String DATE_FORMAT = "d.MM HH:mm:ss";
	
	// Адреса запросов
	public static final String CATEGORIES_URL = "http://corinf-dev.com:8080/dummyserver/categories";
	public static final String ASK_URL = "http://corinf-dev.com:8080/dummyserver/ask";
	public static final String ANSWERS_URL = "http://corinf-dev.com:8080/dummyserver/answers";
	
	// Теги логов
	public static final String TAG_MESSAGE = "Message";
	public static final String TAG_CALL = "Call";
	public static final String TAG_ERROR = "Error";
	public static final String TAG_STATE = "State";
	
	public static final String BROADCAST_ACTION_NOTIFICATION = "ru.romangolovan.develop.notification";
	
	public static final String NUMBER_OF_ANSWERS = "numberOfAnswers";
	public static final String LAST_ANSWER = "lastAnswer";
	
	// dialog id's
	public static final int DIALOG_ADD_QUESTION = 1;
	public static final int DIALOG_DELETE_QUESTION = 2;
	public static final int DIALOG_DELETE_ANSWER = 3;
	
	// dialog string id;
	public static final String DIALOG_ADD_QUESTION_STRING = "AddQuestion";
	public static final String DIALOG_DELETE_QUESTION_STRING = "DeleteQuestion";
	public static final String DIALOG_DELETE_ASNWER_STRING = "DeleteAnswer";
	
	// notification 
	public static final int NOTIFICATION_ID = 1;
	
}
