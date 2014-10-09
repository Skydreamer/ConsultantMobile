package tasks;


import items.CategoryItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import utils.Constants;

import data.MasterDataController;

import activities.MainActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetCategoriesFromServerTask extends AsyncTask<String, Void, JSONArray> {
	Context context;
	MasterDataController dataController;

	public GetCategoriesFromServerTask(Context context, MasterDataController dataController)
	{
		Log.d(Constants.TAG_CALL, "GetCategoriesFromServerTask - GetCategoriesFromServerTask()");
		Log.d(Constants.TAG_MESSAGE, "Получаю список категорий с сервера");
		this.context = context;
		this.dataController = dataController;
	}

	@Override
	protected JSONArray doInBackground(String... params) {
		String oldName = Thread.currentThread().getName();
		Thread.currentThread().setName("GetCategoriesFromServerTask");
		JSONArray temp = loadJSON(params[0], null);
		Thread.currentThread().setName(oldName);
		return temp;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	public JSONArray loadJSON(String url, String par)
	{		
		InputStream inputStream = null;
		String json = "";
		JSONArray jsonArray = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String paramString = URLEncodedUtils.format(params, "utf-8");
			url += "?" + paramString;
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
		}
		catch(UnsupportedEncodingException e)
		{
			Log.d("Catch", e.toString());
		}
		catch (ClientProtocolException e) {

			Log.d("Catch", e.toString());

		} catch (IOException e) {

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
		Log.d(Constants.TAG_MESSAGE, "Получен список категорий");
		super.onPostExecute(jsonData);

		if(jsonData != null)
		{
			parseJSONCategory(jsonData);	
			((MainActivity) context).updateCategoriesList();
			new SaveCategoriesTask().execute();
		}
	}

	private void parseJSONCategory(JSONArray jsonData)
	{
		String count, name;

		try
		{
			for(int i = 0; i < jsonData.length(); ++i)
			{
				count = jsonData.getJSONObject(i).getString("count");
				name = jsonData.getJSONObject(i).getString("name");

				dataController.getCategories().add(new CategoryItem(name, Integer.parseInt(count)));
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
	}
}
