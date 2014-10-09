package tasks;


import items.QuestionItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import utils.Constants;

import android.os.AsyncTask;
import android.util.Log;
import data.MasterDataController;

public class SendQuestionToServerTask extends AsyncTask<String, Void, Void> {
	MasterDataController masterDataController;
	QuestionItem question;

	public SendQuestionToServerTask(QuestionItem question)
	{
		Log.d(Constants.TAG_CALL, "SendQuestionToServerTask - SendQuestionToServerTask()");
		Log.d(Constants.TAG_MESSAGE, "Отправляю вопрос на сервер\n" + question.getText());
		masterDataController = MasterDataController.getInstance();
		this.question = question;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... params) {
		Thread.currentThread().setName("SendQuestionToServerTask");
		loadJSON(params[0], params[1]);
		return null;
	}

	public JSONArray loadJSON(String url, String parameter)
	{
		InputStream inputStream = null;
		JSONArray jsonArray = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("question", parameter));

		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();				
			inputStream = httpEntity.getContent();
			
			String cookie = httpResponse.getHeaders("Set-Cookie")[0].getValue();
			question.setCookie(cookie);
			Log.d("ResponseHeader - Cookie", cookie);
		}
		catch (Exception e) {
			Log.d("Catch", e.toString());
		}

		try
		{
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(inputStream, "utf-8"), 8);

			StringBuilder stringBuider = new StringBuilder();
			String line = null;

			while((line = reader.readLine()) != null)
			{
				Log.d("BufferedReader", line);
				stringBuider.append(line + "\n");
			}

			inputStream.close();
			//json = stringBuider.toString();
		}
		catch(Exception e)
		{
			Log.d("Catch", e.toString());
		}
		return jsonArray;
	}

	@Override
	protected void onPostExecute(Void result) {
		Log.d(Constants.TAG_MESSAGE, "Вопрос отправлен");
		super.onPostExecute(result);
		//new SaveQuestionTask(context, question).execute(); TODO
		masterDataController.updateQuestionCookieInDatabase(question);
	}
}
