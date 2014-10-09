
//package utils;
//
//import items.CategoryItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import ru.romangolovan.consultantmobile.MainActivity;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//public class OnlineTask extends AsyncTask<String, Void, JSONArray> {
//	Context context;
//
//	public static final int GET_CATEGORIES = 0;
//	public static final int SEND_QUESTION = 1;
//
//	int taskMode;
//
//
//	public OnlineTask(Context context, int mode)
//	{
//		Log.d("OnlineTask", "OnlineTask()");
//		this.context = context;
//		this.taskMode = mode;
//	}
//
//	@Override
//	protected JSONArray doInBackground(String... params) {
//		Log.d("OnlineTask", "doInBackground()");
//
//		switch(taskMode)
//		{
//		case GET_CATEGORIES:
//			return loadJSON(params[0], null);
//
//		case SEND_QUESTION:
//			return loadJSON(params[0], params[1]);
//		}
//		return null;
//	}
//
//	public JSONArray loadJSON(String url, String par)
//	{
//		Log.d("OnlineTask", "loadJSON()");
//		JSONParser jsonParser;
//		JSONArray json = null;
//		List<NameValuePair> params;
//
//		switch (taskMode) 
//		{
//		case GET_CATEGORIES:
//			jsonParser = new JSONParser();
//			params = new ArrayList<NameValuePair>();
//			json = jsonParser.makeHttpRequest(url, "GET", params);
//			break;
//
//		case SEND_QUESTION:
//			jsonParser = new JSONParser();
//			// добавление параметров
//			params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("String", par));
//			json = jsonParser.makeHttpRequest(url, "POST", params);
//			break;
//		}
//		return json;
//	}
//
//	@Override
//	protected void onPostExecute(JSONArray jsonData) {
//		Log.d("OnlineTask", "onPostExecute()");
//		super.onPostExecute(jsonData);
//
//		switch(taskMode)
//		{
//		case GET_CATEGORIES:
//			parseJSONCategory(jsonData);
//			break;
//			
//		case SEND_QUESTION:
//			break;
//		}	
//	}
//
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//		Log.d("OnlineTask", "onPreExecute()");
//	}
//
//	private void parseJSONCategory(JSONArray jsonData)
//	{
//		Log.d("OnlineTask", "parseJSONCategory()");
//		Log.d("OnlineTask", "JSON - " + jsonData.toString());
//		ArrayList<CategoryItem> items = new ArrayList<CategoryItem>();
//		String count, name;
//		int version = 1;
//
//		if(jsonData != null)
//		{
//			try
//			{
//				for(int i = 0; i < jsonData.length(); ++i)
//				{
//					count = jsonData.getJSONObject(i).getString("count");
//					name = jsonData.getJSONObject(i).getString("name");
//					items.add(new CategoryItem(name, Integer.parseInt(count)));
//				}
//
//				((MainActivity) context).setCategoriesFromMagic(items, version);
//			}
//			catch(JSONException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		else
//			((MainActivity) context).setCategoriesFromDatabase();
//	}
//}
