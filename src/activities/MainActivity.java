package activities;


import items.CategoryItem;
import items.QuestionItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import ru.romangolovan.consultantmobile.R;
import services.ServiceAdapter;
import tasks.GetCategoriesFromServerTask;
import tasks.ReadCategoriesTask;
import tasks.SaveQuestionTask;
import tasks.SendQuestionToServerTask;
import utils.Constants;
import utils.Preferences;
import views.CategorySpinnerAdapter;
import views.CategorySpinnerItem;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import comparators.CategoryComparator;

import data.MasterData;
import data.MasterDataController;
import enums.ApplicationState;
import enums.SortType;
import fragments.AddQuestionDialogFragment;
import fragments.QuestionsListFragment;

public class MainActivity extends FragmentActivity implements OnClickListener {
	ImageButton enterButton;
	Button historyButton;
	EditText queryEditText;

	Spinner categorySpinner;
	CategorySpinnerAdapter spinnerAdapter;
	
	QuestionsListFragment questionsListFragment;

	ArrayList<CategorySpinnerItem> spinnerItems;

	MasterDataController masterDataController;
	ServiceAdapter serviceAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(Constants.TAG_CALL, "MainActivity - onCreate()");
		setContentView(R.layout.activity_main);
		//setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		masterDataController = MasterDataController.getInstance();
		masterDataController.clean();
		
		questionsListFragment = (QuestionsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentQuestionsList);

		serviceAdapter = ServiceAdapter.newInstance();
		spinnerItems = new ArrayList<CategorySpinnerItem>();
		initiateViews();

		setCategoriesList();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(Constants.TAG_CALL, "MainActivity - onStart()");
		Preferences.updatePreference();
		masterDataController.setApplicationState(ApplicationState.ACTIVE);
		serviceAdapter.bindRecieveAnswersService();
		serviceAdapter.bindNotificationService();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(Constants.TAG_CALL, "MainActivity - onDestroy()");
		masterDataController.setApplicationState(ApplicationState.PASSIVE);
	}

	public void initiateViews()
	{
		Log.d(Constants.TAG_CALL, "MainActivity - initiateViews()");
		enterButton = (ImageButton) findViewById(R.id.mainEnterQueryButton);
		queryEditText = (EditText) findViewById(R.id.mainQueryTextEdit);
		categorySpinner = (Spinner) findViewById(R.id.mainCategorySpinner);

		enterButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(Constants.TAG_CALL, "MainActivity - onCreateOptionsMenu()");
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Log.d(Constants.TAG_CALL, "MainActivity - onClick()");
		String question, category;
		Animation animation;
		animation = AnimationUtils.loadAnimation(this, R.anim.shakeview);

		switch(v.getId())
		{
		case R.id.mainEnterQueryButton:
			question = queryEditText.getText().toString();
			if(question.equalsIgnoreCase(""))
			{
				Toast.makeText(this, getString(R.string.writeQuery), Toast.LENGTH_SHORT).show();
				queryEditText.startAnimation(animation);
				break;
			}

			if(masterDataController.getCategories().size() == 0)
				category = null;
			else
				category = ((CategorySpinnerItem) categorySpinner.getSelectedItem()).getItem().getName();

			if(category == null || category.equalsIgnoreCase(""))
			{
				Toast.makeText(this, getString(R.string.selectCategory), Toast.LENGTH_SHORT).show();
				categorySpinner.startAnimation(animation);
				break;
			}

			AddQuestionDialogFragment dialogFragment = AddQuestionDialogFragment.newInstance(question, category);
			dialogFragment.show(getSupportFragmentManager(), Constants.DIALOG_ADD_QUESTION_STRING);

			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(Constants.TAG_CALL, "MainActivity - onOptionsItemSelected()");
		Intent intent;
		
		switch(item.getItemId())
		{
		case R.id.menuMainOpenAllAnswers:
			intent = new Intent(this, AllAnswersActivity.class);
			startActivity(intent);
			break;

		case R.id.menuMainActionSettings:
			intent = new Intent(this, PreferencesActivity.class);
			startActivity(intent);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		Log.d(Constants.TAG_CALL, "MainActivity - onCreateContextMenu()");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu_questions, menu);
	}

//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		Log.d(Constants.TAG_CALL, "MainActivity - onContextItemSelected()");
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//
//		String questionText = question.getText();
//
//		switch(item.getItemId())
//		{
//		case R.id.contextMenuQuestionsAskAgain:
//			// TODO доделать вю эту хрень
//			if(masterDataController.isOnline())
//				new SendQuestionToServerTask(question).execute(Constants.ASK_URL, questionText);
//			break;
//
//		case R.id.contextMenuQuestionsGetAnswers:
//			if(masterDataController.isOnline())
//				new GetAnswersFromServerTask(question).execute(Constants.ANSWERS_URL, questionText);
//			break;
//
//		case R.id.contextMenuQuestionsDelete:
//			DeleteQuestionDialogFragment dialogFragment = DeleteQuestionDialogFragment.newInstance(questionText);
//			//dialogFragment.show(getFragmentManager(), Constants.DIALOG_DELETE_QUESTION_STRING);
//			dialogFragment.show(getSupportFragmentManager(), Constants.DIALOG_DELETE_QUESTION_STRING);
//			break;
//		}
//
//		return super.onContextItemSelected(item);
//	}

	public void updateCategoriesList()
	{
		Log.d(Constants.TAG_CALL, "MainActivity - updateCategoriesList()");
		spinnerItems = new ArrayList<CategorySpinnerItem>();
		
		Collections.sort(MasterData.categories, new CategoryComparator(SortType.DESK));

		for(CategoryItem category : masterDataController.getCategories())
			spinnerItems.add(new CategorySpinnerItem(category, false));
		spinnerItems.add(new CategorySpinnerItem("", 0, true));

		spinnerAdapter = new CategorySpinnerAdapter(this, R.layout.spinner_selected_item, spinnerItems);
		spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

		categorySpinner.setAdapter(spinnerAdapter);
		categorySpinner.setSelection(spinnerItems.size() - 1, true);	
	}

	public void setCategoriesList()
	{
		Log.d(Constants.TAG_CALL, "MainActivity - setCategoriesList()");
		if(masterDataController.isOnline())
			new GetCategoriesFromServerTask(this, masterDataController).execute(Constants.CATEGORIES_URL);
		else
		{
			Toast.makeText(this, "Ошибка при получении данных. Загрузка с базы данных", Toast.LENGTH_SHORT).show();
			new ReadCategoriesTask(this).execute();
		}
	}

	public void onDialogPositiveClick(int dialog)
	{
		Log.d(Constants.TAG_CALL, "MainActivity - onDialogPositiveClick");

		switch (dialog) {
		case Constants.DIALOG_ADD_QUESTION:
			QuestionItem question = new QuestionItem(queryEditText.getText().toString(), 
					((CategorySpinnerItem) categorySpinner.getSelectedItem()).getItem().getName(), 
					new Date().getTime() / 1000L, 
					0, 
					null);

			new SaveQuestionTask(question).execute();
			masterDataController.addQuestion(question);
			questionsListFragment.updateList();

			if(masterDataController.isOnline())
				new SendQuestionToServerTask(question).execute(Constants.ASK_URL, queryEditText.getText().toString());
			else
				masterDataController.getQuestionsUnsent().add(question);

			// hide keyboard
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(queryEditText.getWindowToken(), 0);

			// clear components
			queryEditText.setText("");
			categorySpinner.setSelection(spinnerItems.size() - 1, true);	
			break;

		case Constants.DIALOG_DELETE_QUESTION:
			//Log.d(Constants.TAG_MESSAGE, "Удаление вопроса -" + masterDataController.getQuestions().get(selectedQuestion));
			//masterDataController.removeQuestion(masterDataController.getQuestions().get(selectedQuestion));
			//updateQuestionsList();
			break;

		default:
			break;
		}
	}

	public void onDialogNegativeClick(int dialog)
	{
		Log.d(Constants.TAG_CALL, "MainActivity - onDialogNegativeClick");

		switch (dialog) {
		case Constants.DIALOG_ADD_QUESTION:
			break;

		case Constants.DIALOG_DELETE_QUESTION:
			break;

		default:
			break;
		}
	}
}






















