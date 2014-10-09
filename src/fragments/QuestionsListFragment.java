package fragments;

import items.QuestionItem;

import java.util.ArrayList;
import java.util.Collections;

import ru.romangolovan.consultantmobile.R;
import utils.Preferences;
import views.QuestionsAdapter;
import activities.AnswersActivity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import comparators.QuestionComparator;

import data.MasterData;
import data.MasterDataController;
import enums.SortType;

public class QuestionsListFragment extends ListFragment {
	View listContainer;
	View progressContainer;
	//View listFooter;
	ListView listView;
	TextView internalEmpty;

	ArrayList<QuestionItem> allQuestions;
	ArrayList<QuestionItem> currentQuestions;

	QuestionsAdapter questionsAdapter;

	boolean shown = false;
	boolean animate = false;
	boolean loading = false;
	int currentAmount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(utils.Constants.TAG_CALL, "QuestionsListFragment - onCreateView()");
		View rootView = inflater.inflate(R.layout.fragment_questions, container, false);		

		listView = (ListView) rootView.findViewById(android.R.id.list);
		listContainer = rootView.findViewById(R.id.questionsListContainer);
		progressContainer = rootView.findViewById(R.id.questionsProgressContainer);
		internalEmpty = (TextView) rootView.findViewById(R.id.questionsInternalEmpty);

		listView.setFriction(3.0f);
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {			
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				//				if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0)
				//					if(loading == false)
				//					{
				//						// TODO
				//						loading = true;
				//						new Handler().postDelayed(new Runnable() {
				//							
				//							@Override
				//							public void run() {
				//								addAnswers(Preferences.addItemsAmount);
				//							}
				//						}, 500);
				//					}

			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				//	selectedInContext = view;
				return false;
			}
		});
		//listView.addFooterView(listFooter);

		setListAdapter(questionsAdapter);

		registerForContextMenu(listView);

		setListShown(false, animate);
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(utils.Constants.TAG_CALL, "QuestionsListFragment - onCreate()");
		allQuestions = new ArrayList<QuestionItem>();
		currentQuestions = new ArrayList<QuestionItem>();

		questionsAdapter = new QuestionsAdapter(getActivity(), android.R.id.list, currentQuestions);
		//	listFooter = getLayoutInflater(null).inflate(R.layout.list_footer_progress, null);

		currentAmount = 0;

		new LoadQuestions().execute();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), AnswersActivity.class);
		intent.putExtra("QuestionPosition", position);
		//items.get(position).look();
		getActivity().startActivity(intent);
	}

	public void setListShown(boolean shown, boolean animate) {
		this.shown = shown;

		if(shown)
		{
			if(animate)
			{
				progressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
				listContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
			}
			progressContainer.setVisibility(View.INVISIBLE);
			listContainer.setVisibility(View.VISIBLE);
		}
		else
		{
			if(animate)
			{
				progressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
				listContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
			}
			progressContainer.setVisibility(View.VISIBLE);
			listContainer.setVisibility(View.INVISIBLE);
		}
	}

	public void addQuestions(int count)
	{
		int top = currentAmount + count;

		if(top > MasterData.questions.size())
			top = MasterData.questions.size();
		//TODO
		for(int i = currentAmount; i < top; ++i, ++currentAmount)
			currentQuestions.add(MasterData.questions.get(i));

		questionsAdapter.swapItems(currentQuestions);
		setListShown(true, animate);

		loading = false;
	}

	public void updateList()
	{
		currentQuestions.clear();
		
		for(int i = 0; i <= currentAmount; ++i)
			currentQuestions.add(MasterData.questions.get(i));
		
		questionsAdapter.swapItems(currentQuestions);
	}

	private class LoadQuestions extends AsyncTask<Integer, Void, Void>
	{		
		@Override
		protected void onPreExecute() {
			loading = true;
		}

		@Override
		protected Void doInBackground(Integer... params) {
			MasterDataController masterDataController = MasterDataController.getInstance();
			masterDataController.readConsultantsFromDatabase();
			masterDataController.readQuestionsFromDatabase();

			Collections.sort(MasterData.questions, new QuestionComparator(SortType.DESK));
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO 
			loading = false;
			if(MasterData.answers.size() != 0)
			{
				addQuestions(Preferences.initItemsAmount);
				internalEmpty.setText("");
			}
			else
			{
				internalEmpty.setText("Вопросов нет");
			}
		}
	}
}
