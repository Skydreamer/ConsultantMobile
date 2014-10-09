package tasks;

import utils.Constants;
import items.QuestionItem;
import android.os.AsyncTask;
import android.util.Log;
import data.MasterDataController;

public class SaveQuestionTask extends AsyncTask<Void, Integer, Void> {
	private MasterDataController dataController;
	private QuestionItem question;

	public SaveQuestionTask(QuestionItem question)
	{
		Log.d(Constants.TAG_CALL, "SaveQuestionTask - SaveCategoriesTask()");
		this.question = question;
		this.dataController = MasterDataController.getInstance();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		Thread.currentThread().setName("SaveQuestionTask");
		dataController.saveQuestionToDatabase(question);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	}
}
