
//package trash;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.util.Log;
//
//public class BackgroundTask extends AsyncTask<Void, Integer, Boolean> {
//	public static final int ACTIVE = 1;
//	public static final int CLOSED = 0;
//
//	Handler handler;
//	Context context;
//	int activeDelay = 1 * 1000;
//	int closedDelay = 30 * 1000;
//	int mode;
//	
//	public BackgroundTask(Context context, Handler handler)
//	{
//		this.context = context;
//		this.handler = handler;
//	}
//	
//	public BackgroundTask(Context context, Handler handler, int activeDelay, int closedDelay)
//	{
//		this.context = context;
//		this.handler = handler;
//	}
//
//	@Override
//	protected Boolean doInBackground(Void... params) {
//		handler.sendEmptyMessage(0);
//		return handler.postDelayed(connectChecker, activeDelay);
//	}
//
//	@Override
//	protected void onPostExecute(Boolean result) {
//		// TODO Auto-generated method stub
//		super.onPostExecute(result);
//	}
//
//	@Override
//	protected void onPreExecute() {
//		// TODO Auto-generated method stub
//		super.onPreExecute();
//	}
//
//	@Override
//	protected void onProgressUpdate(Integer... values) {
//		// TODO Auto-generated method stub
//		super.onProgressUpdate(values);
//	}
//
//	private Runnable connectChecker = new Runnable() {
//		
//		@Override
//		public void run() {
//			Log.d("BackgroundTask", "connectChecker()");
//			
//			ConnectivityManager connectivityManager = 
//					(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//
//			if(networkInfo != null && networkInfo.isConnected())
//				handler.sendEmptyMessage(1);
//			else 
//				handler.sendEmptyMessage(2);
//			
//			if(mode == ACTIVE)
//				handler.postDelayed(connectChecker, activeDelay);
//			else if (mode == CLOSED)
//				handler.postDelayed(connectChecker, closedDelay);
//		}
//	};
//
//	public void cancel()
//	{
//		handler.removeCallbacks(connectChecker);
//	}
//	
//	public void setMode(int mode)
//	{
//		this.mode = mode;
//	}
//	
//}
