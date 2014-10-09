package activities;

import utils.Preferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new fragments.PreferencesFragment()).commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Preferences.updatePreference();
	}

}
