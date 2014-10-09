package fragments;

import items.AnswerItem;

import java.util.ArrayList;
import java.util.Collections;

import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import utils.Preferences;
import views.AllAnswersAdapter;
import activities.ChatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import comparators.AnswerComparator;

import data.MasterData;
import data.MasterDataController;
import enums.SortType;

public class AllAnswersListFragment extends android.support.v4.app.ListFragment {
	static int currentAmount = 0;

	public ListView listView;
	View listContainer;
	View progressContainer;
	View selectedInContext;
	View listFooter;

	AllAnswersAdapter allAnswersAdapter;
	ArrayList<AnswerItem> allAnswers;
	ArrayList<AnswerItem> currentAnswers;
	MasterDataController masterDataController;

	boolean shown = false;
	boolean loading = true;
	boolean animate = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(Constants.TAG_CALL, "AllAnswersListFragment - onCreate()");
		super.onCreate(savedInstanceState);
		allAnswers = new ArrayList<AnswerItem>();
		currentAnswers = new ArrayList<AnswerItem>();

		allAnswersAdapter = new AllAnswersAdapter(getActivity(), android.R.id.list, currentAnswers);
		listFooter = getLayoutInflater(null).inflate(R.layout.list_footer_progress, null);

		currentAmount = 0;
		
		new BuildAnswerList(0).execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(utils.Constants.TAG_CALL, "AllAnswersListFragment - onCreateView()");
		View rootView = inflater.inflate(R.layout.fragment_all_answers, container, false);		

		listView = (ListView) rootView.findViewById(android.R.id.list);
		listContainer = rootView.findViewById(R.id.allAnswersListContainer);
		progressContainer = rootView.findViewById(R.id.allAnswersProgressContainer);

		listView.setFriction(3.0f);
		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {			
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0)
					if(loading == false)
					{
						// TODO
						loading = true;
						new Handler().postDelayed(new Runnable() {
							
							@Override
							public void run() {
								addAnswer(Preferences.addItemsAmount);
							}
						}, 200);
					}
						
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				selectedInContext = view;
				return false;
			}
		});
		listView.addFooterView(listFooter);
		
		setListAdapter(allAnswersAdapter);

		registerForContextMenu(listView);

		setListShown(false, animate);
		return rootView;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {		
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		AnswerItem answer = currentAnswers.get(info.position);
		
		switch(item.getItemId())
		{
		case R.id.contextMenuAllAnswersConsultantInfo:
			Toast.makeText(getActivity(), "Узнать домашний адрес консультанта", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.contextMenuAllAnswersDeleteAnswers:
			if(selectedInContext != null)
			{
				Animation animatiom = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
				animatiom.setDuration(500);
				selectedInContext.startAnimation(animatiom);
				currentAnswers.remove(answer);				
				animatiom.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						allAnswersAdapter.notifyDataSetChanged();
					}
				});
			}

			break;

		case R.id.contextMenuAllAnswersReportSpam:
			Toast.makeText(getActivity(), "Наябедничать на консультанта", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.contextMenuAllAnswersVisitWebsite:
			Toast.makeText(getActivity(), "Открытие какого-то сайта в браузере", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.context_menu_all_answers, menu);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), ChatActivity.class);
		intent.putExtra("AnswerID", currentAnswers.get(position).getTableId());
		startActivity(intent);
	}

	public void addAnswer(int count)
	{
		int top = currentAmount + count;
		
		if(top > allAnswers.size())
			top = allAnswers.size();
		
		for(int i = currentAmount; i < top; ++i, ++currentAmount)
			currentAnswers.add(allAnswers.get(currentAmount));
		
		allAnswersAdapter.swapItems(currentAnswers);
		setListShown(true, animate);
		
		loading = false;
	}
	
	public void refreshAnswers()
	{
		currentAnswers.clear();
		for(int i = 0; i < currentAmount; ++i)
			currentAnswers.add(allAnswers.get(i));
		allAnswersAdapter.swapItems(currentAnswers);
		setListShown(true, animate);
	}
	
	public void updateItems()
	{
		new BuildAnswerList(1).execute();
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

	// TODO 2 разных запроса, вынести в отдельный файл
	private class BuildAnswerList extends AsyncTask<Integer, Void, Void>
	{
		int mode;
		
		public BuildAnswerList(int mode)
		{
			this.mode = mode;
		}
		
		@Override
		protected void onPreExecute() {
			loading = true;
		}

		@Override
		protected Void doInBackground(Integer... params) {
			masterDataController = MasterDataController.getInstance();

			allAnswers.clear();
			//for(AnswerItem answer : MasterData.answers)
			//	allAnswers.add(answer);
			allAnswers.addAll(MasterData.answers);

			Collections.sort(allAnswers, new AnswerComparator(SortType.DESK));
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			switch (mode) {
			case 0:
				Log.d(utils.Constants.TAG_MESSAGE, "Mode 0");
				addAnswer(Preferences.initItemsAmount);				
				break;
				
			case 1:
				Log.d(utils.Constants.TAG_MESSAGE, "Mode 1");
				refreshAnswers();
				break;
				
			default:
				break;
			}
			loading = false;
		}
	}
}
