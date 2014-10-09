package tasks;

import utils.Constants;
import data.MasterDataController;
import activities.MainActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ReadCategoriesTask extends AsyncTask<Void, Integer, Void> {
	private MasterDataController dataController;
	private Context context;

	public ReadCategoriesTask(Context context)
	{
		Log.d(Constants.TAG_CALL, "ReadCategoriesTask - ReadCategoriesTask()");
		Log.d(Constants.TAG_MESSAGE, "Загружаю список категорий с базы данных");
		this.context = context;
		this.dataController = MasterDataController.getInstance();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		String oldName = Thread.currentThread().getName();
		Thread.currentThread().setName("ReadCategoriesTask");
		dataController.readCategoriesFromDatabase();
		Thread.currentThread().setName(oldName);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		Log.d(Constants.TAG_MESSAGE, "Список категорий загружен");
		super.onPostExecute(result);

		((MainActivity) context).updateCategoriesList();
	}
}
