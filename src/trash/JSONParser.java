
//package utils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URLEncodedUtils;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//
//import android.util.Log;
//
//public class JSONParser {
//	static InputStream inputStream = null;
//	static JSONArray jsonArray = null;
//	static String json = "";
//
//	public JSONParser()
//	{
//		Log.d("JSONParser", "JSONParser");
//	}
//
//	// url - адрес method - GET or POST params - передаваемые параметры
//	public JSONArray makeHttpRequest(String url, 
//			String method, List<NameValuePair> params)
//	{
//		Log.d("JSONParser", "makeHttpRequest - " + method);
//
//		try
//		{
//			if(method == "POST")
//			{
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				HttpPost httpPost = new HttpPost(url);
//				
//				httpPost.setEntity(new UrlEncodedFormEntity(params));
//				//HttpEntity entity = new UrlEncodedFormEntity(params);
//				//httpPost.addHeader(entity.getContentType());
//				//httpPost.setEntity(entity);
//				httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//			//	httpPost.setHeader("", value)
//				//httpPost.setHeader("Content-type", "application/json");
//				//httpPost.setHeader("Accept", "*/*");
//				//httpPost.setHeader("Accept-Encoding", "gzip,deflate,sdch");
//				//httpPost.setHeader("Accept-Charset", "utf-8");
//				//httpPost.setHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
//				
//				HttpResponse httpResponse = httpClient.execute(httpPost);
//				
//				HttpEntity httpEntity = httpResponse.getEntity();
//				
//				String responseBody = httpEntity.toString();
//				Log.d("Response", responseBody);
//				
//				
//				inputStream = httpEntity.getContent();
//				Log.d("InputStream", inputStream.toString());
//			}
//			else if (method == "GET")
//			{
//				DefaultHttpClient httpClient = new DefaultHttpClient();
//				String paramString = URLEncodedUtils.format(params, "utf-8");
//				url += "?" + paramString;
//				HttpGet httpGet = new HttpGet(url);
//
//				HttpResponse httpResponse = httpClient.execute(httpGet);
//				HttpEntity httpEntity = httpResponse.getEntity();
//				inputStream = httpEntity.getContent();
//				Log.d("InputStream", inputStream.toString());
//			}
//		}
//		catch(UnsupportedEncodingException e)
//		{
//			Log.d("Catch", e.toString());
//		}
//		catch (ClientProtocolException e) {
//
//			Log.d("Catch", e.toString());
//
//		} catch (IOException e) {
//
//			Log.d("Catch", e.toString());
//		}
//
//		try
//		{
//			BufferedReader reader = new BufferedReader(
//					new InputStreamReader(inputStream, "utf-8"), 8);
//
//			StringBuilder stringBuider = new StringBuilder();
//			String line = null;
//
//			while((line = reader.readLine()) != null)
//			{
//				Log.d("BufferedReader", line);
//				stringBuider.append(line + "\n");
//			}
//
//			inputStream.close();
//			///////////////////////////////////
//			json = stringBuider.toString();
//		}
//		catch(Exception e)
//		{
//			Log.d("Catch", e.toString());
//		}
//
//		try
//		{
//			jsonArray = new JSONArray(json);
//			//jsonObject = jsonArray.toJSONObject(jsonArray);
//		}
//		catch(Exception e)
//		{
//			Log.d("Catch", e.toString());
//		}
//
//		return jsonArray;
//	}
//}
