package services;

import items.AnswerItem;
import items.CategoryItem;
import items.Consultant;
import items.MessageItem;
import items.QuestionItem;

import java.util.Date;

import tasks.GetAnswersFromServerTask;
import tasks.SendQuestionToServerTask;
import utils.Constants;
import utils.Preferences;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import data.MasterDataController;
import enums.ApplicationState;

public class RecieveAnswersService extends Service{
	Handler handler;
	MasterDataController masterDataController;
	Date date;

	public RecieveAnswersService()
	{
		Log.d(Constants.TAG_CALL, "RecieveAnswersService - RecieveAnswersService()");
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("Constants.TAG_CALL", "RecieveAnswersService - onBind()");
		masterDataController = MasterDataController.getInstance();
		date = new Date();

		handler.removeCallbacks(recieveAnswers);
		handler.postDelayed(recieveAnswers, 1000);

		return new Binder();
	}

	@Override
	public void onCreate() {
		Log.d(Constants.TAG_CALL, "RecieveAnswersService - onCreate()");
		handler = new Handler();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Log.d(Constants.TAG_CALL, "RecieveAnswersService - onDestroy()");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(Constants.TAG_CALL, "RecieveAnswersService - onStartCommand()");
		return START_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(Constants.TAG_CALL, "RecieveAnswersService - onUnbind()");
		handler.removeCallbacks(recieveAnswers);
		handler.postDelayed(recieveAnswers, Preferences.delayInactive);
		return super.onUnbind(intent);
	}

	private Runnable recieveAnswers = new Runnable() {

		@Override
		public void run() {
			Log.d(Constants.TAG_CALL, "RecieveAnswersService - recieveAnswers()");

			Log.d(Constants.TAG_MESSAGE, "Answers - " + AnswerItem.amount);
			Log.d(Constants.TAG_MESSAGE, "Categories - " + CategoryItem.amount);
			Log.d(Constants.TAG_MESSAGE, "Questions - " + QuestionItem.amount);
			Log.d(Constants.TAG_MESSAGE, "Consultants - " + Consultant.amount);
			Log.d(Constants.TAG_MESSAGE, "Messages - " + MessageItem.amount);

			int count = 0;

			// TODO переделать все к ебеням

			if(masterDataController.isOnline())
			{
				if(masterDataController.getQuestionsUnsent().size() > 0)
				{
					for(QuestionItem question : masterDataController.getQuestionsUnsent())
						new SendQuestionToServerTask(question).execute(Constants.ASK_URL, question.getText());
					masterDataController.getQuestionsUnsent().clear();
				}

				for(QuestionItem question : masterDataController.getQuestions())
				{
					if(question.getTime() - (date.getTime() / 1000L) < Preferences.questionExpirationTime)
					{
						count++;
						//Log.d(Constants.TAG_MESSAGE, "Запрос ответов на вопрос #" + count + " : " + question.getText());
						new GetAnswersFromServerTask(question).execute(Constants.ANSWERS_URL, question.getText());
						if(Preferences.numberOfActiveQuestions <= count)
							break;
					}
				}

				//отправка notification
				// TODO TODO TODO TODO
				if(masterDataController.getApplicationState() == ApplicationState.PASSIVE)
					if(!masterDataController.isNotificate)
						if(masterDataController.unreadAnswersCount > 0)
						{
							masterDataController.isNotificate = true;
							Intent intent = new Intent(Constants.BROADCAST_ACTION_NOTIFICATION);
							intent.putExtra(Constants.NUMBER_OF_ANSWERS, masterDataController.unreadAnswersCount);
							intent.putExtra(Constants.LAST_ANSWER, masterDataController.lastUnreadAnswer);
							sendBroadcast(intent);
						}
			}
			else 
				Log.d(Constants.TAG_MESSAGE, "Offline");

			if(masterDataController.getApplicationState() == ApplicationState.ACTIVE)
				handler.postDelayed(recieveAnswers, Preferences.delayActive);
			else if (masterDataController.getApplicationState() == ApplicationState.PASSIVE)
				handler.postDelayed(recieveAnswers, Preferences.delayInactive);

		}
	};

}
