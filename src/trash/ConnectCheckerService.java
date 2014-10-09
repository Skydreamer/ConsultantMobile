package trash;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import data.MasterDataController;
import enums.ApplicationState;
import enums.ConnectionState;

public class ConnectCheckerService extends Service {
	ConnectionState connectState;

	private int activeDelay;
	private int closedDelay;

	Handler handler;
	MasterDataController masterDataController;

	@Override
	public void onCreate() {
		Log.d("ConnectCheckerService", "onCreate()");
		super.onCreate();
		handler = new Handler();
		connectState = ConnectionState.OFFLINE;
	}

	@Override
	public void onDestroy() {
		Log.d("ConnectCheckerService", "onDestroy()");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("ConnectCheckerService", "onStartCommand() - " + startId);

		return START_STICKY;
	}

	private Runnable connectChecker = new Runnable() {

		@Override
		public void run() {
			Log.d("ConnectCheckerService", "connectChecker()");
			ConnectivityManager connectivityManager = 
					(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

			if(masterDataController != null)
			{
				if(networkInfo != null && networkInfo.isConnected())
				{
					if(connectState == ConnectionState.OFFLINE)
					{
						Log.d("Message", "ONLINE");
						connectState = ConnectionState.ONLINE;
						masterDataController.setConnectionState(connectState);
					}
				}
				else 
				{
					if(connectState == ConnectionState.ONLINE)
					{
						Log.d("Message", "OFFLINE");
						connectState = ConnectionState.OFFLINE;
						masterDataController.setConnectionState(connectState);
					}
				}

				if(masterDataController.getApplicationState() == ApplicationState.ACTIVE)
					handler.postDelayed(connectChecker, activeDelay);
				else if (masterDataController.getApplicationState() == ApplicationState.PASSIVE)
					handler.postDelayed(connectChecker, closedDelay);
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("ConnectCheckerService", "onBind()");
		activeDelay = intent.getIntExtra("activeDelay", 5000);
		closedDelay = intent.getIntExtra("closedDelay", 30 * 1000);
		masterDataController = intent.getParcelableExtra("masterDataController");
		masterDataController.setApplicationState(ApplicationState.ACTIVE);

		handler.postDelayed(connectChecker, activeDelay);

		return new Binder();
	}

	@Override
	public void onRebind(Intent intent) {
		Log.d("ConnectCheckerService", "onRebind()");
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("ConnectCheckerService", "onUnbind()");

		if(masterDataController != null)
			masterDataController.setApplicationState(ApplicationState.PASSIVE);
		//handler.removeCallbacks(connectChecker);
		return super.onUnbind(intent);
	}

}
