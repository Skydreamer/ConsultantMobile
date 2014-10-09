package data;

import utils.Constants;
import utils.Preferences;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class CustomApplication extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(Constants.TAG_CALL, "CustomApplication - onCreate()");
		CustomApplication.context = getApplicationContext();
		Preferences.getInstance();
	}

	public static Context getAppContext()
	{
		return CustomApplication.context;
	}

	@Override
	public void onTerminate() {
		Log.d(Constants.TAG_CALL, "CustomApplication - onTerminate()");
		super.onTerminate();
	}
}
