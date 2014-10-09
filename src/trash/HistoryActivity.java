
//package trash;
//
//import java.util.ArrayList;
//
//import ru.romangolovan.consultantmobile.R;
//import utils.DatabaseController;
//import android.app.Activity;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnGroupClickListener;
//import android.widget.ExpandableListView.OnGroupCollapseListener;
//import android.widget.ExpandableListView.OnGroupExpandListener;
//import android.widget.Toast;
//
//public class HistoryActivity extends Activity implements OnGroupClickListener, 
//OnGroupExpandListener, OnGroupCollapseListener, OnClickListener {
//
//	ExpandableListView expandableListView;
//
//	ArrayList<ExpandableListParent> arrayParents;
//	ExpandableListAdapter expandableListAdapter;
//
//	DatabaseController databaseHelper;
//
//	Intent intent;
//	int requestCode;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//setContentView(R.layout.activity_history);
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onCreate()");
//
//		intent = getIntent();
//		requestCode = intent.getExtras().getInt(getResources().getString(R.string.transferRequestCode));
//		databaseHelper = new DatabaseController(this);
//
//		if(requestCode == 1)
//		{
//			String query = intent.getExtras().getString(getString(R.string.transferQuery));
//			String category = intent.getExtras().getString(getString(R.string.transferCategory));
//			String date = intent.getExtras().getString(getString(R.string.transferDate));
//
//			SQLiteDatabase database = databaseHelper.getWritableDatabase();
//			ContentValues cv = new ContentValues();
//			cv.put(DatabaseController.QUESTIONS_COLUMN_ID_CATEGORY, category);
//			cv.put(DatabaseController.QUESTIONS_COLUMN_NAME, query);
//			cv.put(DatabaseController.QUESTIONS_COLUMN_DATE, date);
//
//			int insertCount = (int) database.insert(DatabaseController.TABLE_NAME_QUESTIONS, null, cv);
//
//			Log.d(getString(R.string.logMessage), "Вставлена строка " + insertCount);
//			cv.clear();	
//			Toast.makeText(this, query + " " + category + " " + date + " " + requestCode, Toast.LENGTH_SHORT).show();
//		}
//
//		//expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
//
//		arrayParents = getArrayParent();
//		expandableListAdapter = new ExpandableListAdapter(this, arrayParents, expandableListView);
//		expandableListView.setAdapter(expandableListAdapter);
//		
//		/*addAnswer(0, new ExpandableListChildren("Text 1", "123"));
//		addAnswer(0, new ExpandableListChildren("Text 2", "123"));
//		addAnswer(1, new ExpandableListChildren("Text 123", "123"));
//		addAnswer(1, new ExpandableListChildren("Text 1234", "123"));
//		addAnswer(1, new ExpandableListChildren("Text 12345", "123"));
//		addAnswer(2, new ExpandableListChildren("Text asdf", "123"));*/
//		
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onCreateOptionsMenu()");
//	//	getMenuInflater().inflate(R.menu.history, menu);
//		setTitle(getString(R.string.history));
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onActivityResult()");
//
//		this.requestCode = requestCode;
//	}
//
//	public Cursor getSearchesFromDatabase()
//	{
//		Log.d(getString(R.string.logCalls), "HistoryActivity - getSearchesFromDatabase()");
//		SQLiteDatabase database = databaseHelper.getWritableDatabase();
//		Cursor cursor = database.query(DatabaseController.TABLE_NAME_QUESTIONS, null, null, null, null, null, null);
//		return cursor;
//	}
//
//	public Cursor getAnswersFromDatabase(int searchID)
//	{
//		Log.d(getString(R.string.logCalls), "HistoryActivity - getAnswersFromDatabase()");
//		SQLiteDatabase database = databaseHelper.getWritableDatabase();
//		Cursor cursor = database.query(DatabaseController.TABLE_NAME_DIALOGS, null, 
//				DatabaseController.DIALOGS_COLUMN_ID_SEARCH + " = " + searchID, 
//				null, null, null, null);
//		return cursor;
//	}
//
//	public ArrayList<ExpandableListParent> getArrayParent()
//	{
//		Log.d(getString(R.string.logCalls), "HistoryActivity - getArrayParent()");
//
//		Cursor cursorSearches = getSearchesFromDatabase();
////		Cursor cursorAnswers;
////
////		ArrayList<ExpandableListParent> groupValues = new ArrayList<ExpandableListParent>();
////		ArrayList<ExpandableListChildren> childValues = new ArrayList<ExpandableListChildren>();
//
//		if(cursorSearches.moveToFirst())
//		{
////			int idColumnID = cursorSearches.getColumnIndex(DatabaseHelper.SEARCHES_COLUMN_ID);
////			int searchColumnID = cursorSearches.getColumnIndex(DatabaseHelper.SEARCHES_COLUMN_NAME);
////			int dateColumnID = cursorSearches.getColumnIndex(DatabaseHelper.SEARCHES_COLUMN_DATE);
////			int categoryColumnID = cursorSearches.getColumnIndex(DatabaseHelper.SEARCHES_COLUMN_ID_CATEGORY);
//
//			do {
//				/*cursorAnswers = getAnswersFromDatabase(cursorSearches.getInt(idColumnID));
//
//				if(cursorAnswers.moveToFirst())
//				{
//					do
//					{
//						
//					} while (cursorAnswers.moveToNext());
//				}*/
//
//				/*groupValues.add(new ExpandableListParent(
//						cursorSearches.getString(searchColumnID), 
//						cursorSearches.getString(dateColumnID),
//						cursorSearches.getString(categoryColumnID),	
//						childValues));*/
//			}
//			while(cursorSearches.moveToNext());
//		}
//
//		//return groupValues;
//		return null;
//	}
//	
//	public void addAnswer(int searchID, ExpandableListChildren answer)
//	{
//		Log.d(getString(R.string.logCalls), "HistoryActivity - addAnswer()");
//		((ExpandableListParent) expandableListAdapter.getMParent().get(searchID)).getParentChildren().add(answer);
//		
//		expandableListAdapter.notifyDataSetChanged();
//		expandableListAdapter.notifyDataSetInvalidated();
//	}
//	
//	//	public ArrayList<String> getSearches()
//	//	{
//	//		Cursor cursor = getFullSearches();
//	//		Log.d(getString(R.string.logCalls), "HistoryActivity - getSearches()");
//	//		ArrayList<String> values = new ArrayList<String>();
//	//
//	//		if(cursor.moveToFirst())
//	//		{
//	//			int queryColumnID = cursor.getColumnIndex(DatabaseHelper.SEARCHES_COLUMN_NAME);
//	//
//	//			do
//	//			{
//	//				values.add(cursor.getString(queryColumnID));
//	//			} while(cursor.moveToNext());
//	//		}
//	//
//	//		return values;
//	//	}
//
//	@Override
//	public void onGroupCollapse(int groupPosition) {
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onGroupCollapse()");
//
//	}
//
//	@Override
//	public void onGroupExpand(int groupPosition) {
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onGroupExpand()");
//
//	}
//
//	@Override
//	public boolean onGroupClick(ExpandableListView parent, View v,
//			int groupPosition, long id) {
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onGroupClick()");
//		parent.expandGroup(groupPosition);
//		return false;
//	}
//
//	@Override
//	public void onClick(View v) {
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onClick()");	
//
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		Log.d(getString(R.string.logCalls), "HistoryActivity - onOptionsItemSelected()");	
//	//	switch (item.getItemId()) 
//		//{
//		//case R.id.action_settings_history:
//		//	Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
//		//	break;
//		//}
//		return super.onOptionsItemSelected(item);
//	}
//
//}
