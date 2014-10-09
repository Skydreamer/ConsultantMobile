package trash;
//package tasks;
//
//
//import items.AnswerItem;
//
//import java.util.ArrayList;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import data.Constants;
//import data.MasterDataController;
//
//public class SaveAnswersTask extends AsyncTask<Void, Integer, Void> {
//	private MasterDataController dataController;
//	private ArrayList<AnswerItem> answers;
//
//	public SaveAnswersTask(ArrayList<AnswerItem> answers)
//	{
//		Log.d(Constants.TAG_CALL, "SaveAnswersTask()");
//		//Log.d(Constants.TAG_MESSAGE, "Сохраняю ответы в базу данных - " + answers.size());
//		this.dataController = new MasterDataController();
//		this.answers = answers;
//	}
//
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//	}
//
//	@Override
//	protected Void doInBackground(Void... params) {
//		dataController.saveAnswersToDatabase(answers);
//		return null;
//	}
//
//	@Override
//	protected void onPostExecute(Void result) {
//		
//		//Log.d("Message", "Ответы сохранены - " + answers.size());
//		super.onPostExecute(result);
//	}
//}
