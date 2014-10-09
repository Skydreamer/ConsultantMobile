package services;

import utils.Constants;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import data.CustomApplication;

public class ServiceAdapter {
	public Context context;
	public Intent connectCheckerService;
	public Intent notificationService;
	public ServiceConnection connectCheckerConnection;
	public ServiceConnection notificationConnection;
	
	public boolean boundWithRecieveAnswers;
	public boolean boundWithNotifications;
	
	private static ServiceAdapter serviceAdapter;
	
	public static ServiceAdapter newInstance()
	{
		Log.d(Constants.TAG_CALL, "ServiceAdapter - newInstance()");
		if(serviceAdapter == null)
			serviceAdapter = new ServiceAdapter();
		return serviceAdapter;
	}
	
	private ServiceAdapter()
	{
		Log.d(Constants.TAG_CALL, "ServiceAdapter - ServiceAdapter()");
		this.context = CustomApplication.getAppContext();
		
		connectCheckerConnection = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(Constants.TAG_MESSAGE, "Отключились от сервиса RecieveAnswers");
				boundWithRecieveAnswers = false;
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(Constants.TAG_MESSAGE, "Подключились к сервису RecieveAnswers");
				boundWithRecieveAnswers = true;				
			}
		};
		
		notificationConnection = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(Constants.TAG_MESSAGE, "Отключились от сервиса Notification");
				boundWithNotifications = false;
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(Constants.TAG_MESSAGE, "Подключились к сервису Notification");
				boundWithNotifications = true;
			}
		};
	}
	
	public void bindRecieveAnswersService()
	{
		Log.d(Constants.TAG_CALL, "ServiceAdapter - bindRecieveAnswersService()");
		connectCheckerService = new Intent(context, RecieveAnswersService.class)
						.putExtra("startDelay", 0)
						.putExtra("activeDelay", 5000)
						.putExtra("closedDelay", 30000)
						.putExtra("activeAnswersCount", 2);
		
		context.bindService(connectCheckerService, connectCheckerConnection, Context.BIND_AUTO_CREATE); 
	}
	
	public void bindNotificationService()
	{
		Log.d(Constants.TAG_CALL, "ServiceAdapter - bindNotificationService()");
		notificationService = new Intent(context, NotificationService.class);
		
		//context.bindService(notificationService, notificationConnection, Context.BIND_AUTO_CREATE);
		context.startService(notificationService);
	}
	
	public void unbindRecieveAnswers()
	{
		Log.d(Constants.TAG_CALL, "ServiceAdapter - unbindRecieveAnswers()");
		context.unbindService(connectCheckerConnection);
	}
	
	public void unbindNotifications()
	{
		Log.d(Constants.TAG_CALL, "ServiceAdapter - unbindNotifications()");
		context.unbindService(notificationConnection);
	}

}
