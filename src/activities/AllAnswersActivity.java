package activities;

import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import fragments.AllAnswersListFragment;

public class AllAnswersActivity extends FragmentActivity {
	AllAnswersListFragment allAnswersListFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(Constants.TAG_CALL, "AllAnswersActivity - onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_messages);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		Log.d(Constants.TAG_CALL, "AllAnswersActivity - onAttachFragment()");
		allAnswersListFragment = (AllAnswersListFragment) fragment;
		super.onAttachFragment(fragment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d(Constants.TAG_CALL, "AllAnswersActivity - onCreateOptionsMenu()");
		getMenuInflater().inflate(R.menu.menu_all_answers, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.menuAllAnswersUpdate:
			allAnswersListFragment.updateItems();
			break;
			
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
