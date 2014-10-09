package tasks;

import utils.Constants;
import data.MasterDataController;
import activities.MainActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ReadQuestionsTask extends AsyncTask<Void, Integer, Void> {
	private MasterDataController dataController;
	private Context context;

	public ReadQuestionsTask(Context context)
	{
		Log.d(Constants.TAG_CALL, "ReadQuestionsTask - ReadQuestionsTask()");
		Log.d(Constants.TAG_MESSAGE, "Загружаю список вопросов с базы данных");
		this.context = context;
		this.dataController = MasterDataController.getInstance();
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		Thread.currentThread().setName("ReadQuestionsTask");
		dataController.readQuestionsFromDatabase();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		Log.d(Constants.TAG_MESSAGE, "Список вопросов загружен");
		super.onPostExecute(result);

		//((MainActivity) context).updateQuestionsList();
	}
}
