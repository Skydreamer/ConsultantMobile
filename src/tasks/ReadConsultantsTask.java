//package tasks;
//
//import utils.Constants;
//import android.os.AsyncTask;
//import android.util.Log;
//import data.MasterDataController;
//
//public class ReadConsultantsTask extends AsyncTask<Void, Integer, Void> {
//	private MasterDataController dataController;
//
//	public ReadConsultantsTask()
//	{
//		Log.d(Constants.TAG_CALL, "ReadConsultantsTask - ReadConsultantsTask()");
//		Log.d(Constants.TAG_MESSAGE, "Загружаю список консультантов с базы данных");
//		this.dataController = MasterDataController.getInstance();
//	}
//
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//	}
//
//	@Override
//	protected Void doInBackground(Void... params) {
//		String oldName = Thread.currentThread().getName();
//		Thread.currentThread().setName("ReadCategoriesTask");
//		dataController.readConsultantsFromDatabase();
//		Thread.currentThread().setName(oldName);
//		return null;
//	}
//
//	@Override
//	protected void onPostExecute(Void result) {
//		Log.d(Constants.TAG_MESSAGE, "Список консультантов загружен");
//		super.onPostExecute(result);
//	}
//}
