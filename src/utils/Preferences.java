package utils;

import ru.romangolovan.consultantmobile.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import data.CustomApplication;

public class Preferences {
	private static Preferences preferences;
	private static Context context;
	
	public static boolean backgroundMode;
	public static long questionExpirationTime;
	public static int numberOfActiveQuestions;
	public static long delayActive;
	public static long delayInactive;
	
	public static int initItemsAmount = 20;
	public static int addItemsAmount = 20;
	
	private static String backgroundModeString;
	private static String questionExpirationTimeString;
	private static String numberOfActiveQuestionsString;
	private static String delayActiveString;
	private static String delayInactiveString;

	private Preferences() {
		context = CustomApplication.getAppContext();
		
		backgroundModeString = 			context.getString(R.string.prefKeyBackgroundMode);
		questionExpirationTimeString = 	context.getString(R.string.prefKeyQuestionExpiration);
		numberOfActiveQuestionsString = context.getString(R.string.prefKeyNumberOfQuestions);
		delayActiveString = 			context.getString(R.string.prefKeyActiveDelay);
		delayInactiveString = 			context.getString(R.string.prefKeyInactiveDelay);
		
		updatePreference();
	}

	public static Preferences getInstance()
	{
		if(preferences == null)
			preferences = new Preferences();
		return preferences;
	}

	public static void updatePreference()
	{
		PreferenceManager.setDefaultValues(context, R.xml.fragment_preferences, false);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CustomApplication.getAppContext());
		
		backgroundMode = sharedPreferences.getBoolean(backgroundModeString, true);
		questionExpirationTime = 60 * 60 * Long.parseLong(sharedPreferences.getString(questionExpirationTimeString, "72"));
		numberOfActiveQuestions = Integer.parseInt(sharedPreferences.getString(numberOfActiveQuestionsString, "10"));
		delayActive = 1000 * Long.parseLong(sharedPreferences.getString(delayActiveString, "5"));
		delayInactive = 1000 * Long.parseLong(sharedPreferences.getString(delayInactiveString, "30"));

		Log.d(Constants.TAG_MESSAGE, "Active hours - " + questionExpirationTime
				+ " active count - " + numberOfActiveQuestions
				+ " delay active - " + delayActive 
				+ " delay nonactive - " + delayInactive
				+ " background mode - " + backgroundMode);
	}

}
