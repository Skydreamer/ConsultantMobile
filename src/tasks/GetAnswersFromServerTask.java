package tasks;


import items.AnswerItem;
import items.Consultant;
import items.MessageItem;
import items.QuestionItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;
import data.MasterData;
import data.MasterDataController;

public class GetAnswersFromServerTask extends AsyncTask<String, Void, JSONArray> {
	QuestionItem question;
	MasterDataController masterDataController;

	public GetAnswersFromServerTask(QuestionItem question)
	{
		this.question = question;
	}

	@Override
	protected void onPreExecute() {
		masterDataController = MasterDataController.getInstance();
		super.onPreExecute();
	}

	@Override
	protected JSONArray doInBackground(String... params) {
		String oldName = Thread.currentThread().getName();
		Thread.currentThread().setName("GetAnswersFromServerTask");
		JSONArray temp = loadJSON(params[0], params[1]);
		Thread.currentThread().setName(oldName);
		return temp;
	}

	public JSONArray loadJSON(String url, String parameter)
	{
		InputStream inputStream = null;
		String json = "";
		JSONArray jsonArray = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("question", parameter));

		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new UrlEncodedFormEntity(params));
			httpPost.addHeader("Cookie", question.getCookie());

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();				
			inputStream = httpEntity.getContent();

			httpClient = null;
			httpPost = null;
			httpResponse = null;
			httpEntity = null;
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
				stringBuider.append(line + "\n");

			inputStream.close();
			json = stringBuider.toString();

			reader.close();
			stringBuider = null;
		}
		catch(Exception e)
		{
			Log.d("Catch", e.toString());
		}

		try
		{
			jsonArray = new JSONArray(json);
		}
		catch(Exception e)
		{
			Log.d("Catch", e.toString());
		}

		return jsonArray;
	}

	@Override
	protected void onPostExecute(JSONArray jsonData) {
		super.onPostExecute(jsonData);

		if(jsonData != null)
		{
			parseJSONAnswers(jsonData);
		}
	}
	// TODO оптимизация всей этой херни
	private void parseJSONAnswers(JSONArray jsonData) 
	{
		String text;
		String time;
		String object;
		long consultantId;
		Date date;
		
		boolean isAnswerFind;
		boolean isConsultantFind;
		
		Consultant consultant = null;
		MessageItem message = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.UK);

		try
		{
			for(int i = 0; i < jsonData.length(); ++i)
			{
				isAnswerFind = false;
				isConsultantFind = false;
				
				object = jsonData.get(i).toString();
				time = object.substring(object.indexOf("[") + 1, object.lastIndexOf("]"));
				time = time.replace("MSD", "+04:00");
				text = object.substring(object.indexOf("]") + 2);
				date = sdf.parse(time);
				// парс консультанта и запись в нужный answeritem
				consultantId = Long.parseLong(text.replaceAll("[^0-9]", "")) % 30;
				
				for(Consultant cons : MasterData.consultants)
				{
					if(consultantId == cons.getPublicId())
					{
						isConsultantFind = true;
						consultant = cons;
						break;
					}
				}
				
				if(isConsultantFind == false)
				{
					consultant = new Consultant(consultantId, "Консультант номер " + consultantId);
					masterDataController.saveConsultantToDatabase(consultant);
					MasterData.consultants.add(consultant);
				}
				
				for(AnswerItem answer : question.getAnswers())
				{
					if(consultantId == answer.getConsultantId())
					{
						message = new MessageItem(answer, text, date.getTime() / 1000L, false);
						masterDataController.saveMessageToDatabase(message);
						answer.getMessages().add(message);
						MasterData.messages.add(message);
						isAnswerFind = true;
						break;
					}
				}
				
				if(isAnswerFind == false)
				{
					AnswerItem answer = new AnswerItem(question, consultant);
					masterDataController.saveAnswerToDatabase(answer);
					
					message = new MessageItem(answer, text, date.getTime() / 1000L, false);
					masterDataController.saveMessageToDatabase(message);
					
					answer.getMessages().add(message);
					MasterData.answers.add(answer);
					MasterData.messages.add(message);
					
					question.addAnswer(answer);
				}			
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}


}
