package activities;


import items.AnswerItem;
import items.QuestionItem;

import java.util.ArrayList;
import java.util.Collections;

import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import views.AnswersAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import comparators.AnswerComparator;

import data.MasterData;
import data.MasterDataController;
import enums.SortType;

public class AnswersActivity extends Activity {
	TextView questionText;
	ListView answersListView;

	MasterDataController masterDataController;
	
	AnswersAdapter answersListViewAdapter;
	ArrayList<AnswerItem> answerItems;

	QuestionItem question;
	int questionPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answers);
		Log.d(Constants.TAG_CALL, "AnswersActivity - onCreate()");

		masterDataController = MasterDataController.getInstance();
		answerItems = new ArrayList<AnswerItem>();

		initiateViews();
		updateAnswers();
	}

	public void initiateViews()
	{
		Log.d(Constants.TAG_CALL, "AnswersActivity - initiateViews()");
		questionText = (TextView) findViewById(R.id.answersQuestionTextView);
		answersListView = (ListView) findViewById(R.id.answersAnswersList);

		Intent intent = getIntent();
		questionPosition = intent.getIntExtra("QuestionPosition", 0);
		question = MasterData.questions.get(questionPosition);
		//question = masterDataController.getQuestions().get(questionPosition);
		questionText.setText(question.getText());

		registerForContextMenu(answersListView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		Log.d(Constants.TAG_CALL, "AnswersActivity - onCreateContextMenu()");
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.context_menu_answers, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.contextMenuAnswersConsultantInfo:
			break;
			
		case R.id.contextMenuAnswersDeleteAnswer:
			break;
			
		case R.id.contextMenuAnswersReportSpam:
			break;
			
		case R.id.contextMenuAnswersVisitWebsite:
			break;
			
		default:
			break;
		}
		
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(Constants.TAG_CALL, "AnswersActivity - onCreateOptionsMenu()");
		getMenuInflater().inflate(R.menu.menu_answers, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(Constants.TAG_CALL, "AnswersActivity - onOptionsItemSelected()");

		switch (item.getItemId()) {
		case R.id.menuAnswersGetAnswers:
			answersListViewAdapter.notifyDataSetChanged();
			break;
		}

		return super.onOptionsItemSelected(item);
	}
	
	public void updateAnswers()
	{
		Log.d(Constants.TAG_CALL, "AnswersActivity - updateAnswers()");
		answerItems = new ArrayList<AnswerItem>();
		Collections.sort(question.getAnswers(), new AnswerComparator(SortType.ASC));
		
		for(AnswerItem answer : question.getAnswers())
			answerItems.add(answer);
		
		answersListViewAdapter = new AnswersAdapter(this, R.layout.fragment_answer_view_item, answerItems);
		answersListView.setAdapter(answersListViewAdapter);
		answersListView.setOnItemClickListener(answersListViewAdapter);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.d(Constants.TAG_CALL, "AnswersActivity - onMenuItemSelected()");
		return super.onMenuItemSelected(featureId, item);
	}

}
