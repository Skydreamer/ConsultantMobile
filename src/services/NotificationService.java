package services;

import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import activities.MainActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class NotificationService extends Service {
	NotificationManager notificationManager;	
	BroadcastReceiver broadcastReceiver;
	
	String notificationTitle;
	String notificationSummary;
	String notificationStatusBarText;
	
	int numberOfAnswers;
	String lastAnswer;

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
		broadcastReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				numberOfAnswers = intent.getIntExtra(Constants.NUMBER_OF_ANSWERS, 0);
				lastAnswer = intent.getStringExtra(Constants.LAST_ANSWER);
				
				notificationStatusBarText = "Получено " + numberOfAnswers + " ответов";
				notificationTitle = "Получено " + numberOfAnswers + " ответов"; 
				notificationSummary = lastAnswer;
				
				showNotification();
			}
		};
		
		IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION_NOTIFICATION);
		registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
	
	public void showNotification()
	{
		// показ в скрытом action bar'e
		Notification notification = new Notification(R.drawable.ic_launcher, 
									notificationStatusBarText, 
									System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_ALL;
		
		// действие на нажатие в раскрытом баре
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		
		// показ уведомления
		notification.setLatestEventInfo(this, notificationTitle, notificationSummary, pendingIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(Constants.NOTIFICATION_ID, notification);
	}

}
