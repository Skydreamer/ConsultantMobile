package tasks;

import utils.Constants;
import android.os.AsyncTask;
import android.util.Log;
import data.MasterDataController;

public class SaveCategoriesTask extends AsyncTask<Void, Integer, Void> {
	private MasterDataController dataController;

	public SaveCategoriesTask()
	{
		Log.d(Constants.TAG_CALL, "SaveCategoriesTask - SaveCategoriesTask()");
		//Log.d(Constants.TAG_MESSAGE, "Сохраняю список категорий в базу данных");
		this.dataController = MasterDataController.getInstance();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		Thread.currentThread().setName("SaveCategoriesTask");
		dataController.saveCategoriesToDatabase();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		//Log.d(Constants.TAG_MESSAGE, "Список категорий сохранен");
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

}
