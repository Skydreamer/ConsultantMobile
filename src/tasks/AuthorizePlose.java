package tasks;

import java.io.IOException;

import utils.Constants;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.Scopes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AuthorizePlose extends AsyncTask<Void, Void, Void> {
	Context context;
	
	public AuthorizePlose(Context context)
	{
		this.context = context;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String token = null;
		AccountManager accountManager = AccountManager.get(context);
		Account[] accounts = accountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		String[] names = new String[accounts.length];
		for(int i = 0; i < accounts.length; i++)
		{
			names[i] = accounts[i].name;
		}
		
		//Toast.makeText(context, names.toString(), Toast.LENGTH_SHORT).show();
		Log.d(Constants.TAG_MESSAGE, names.toString());
		
		try {
			token = GoogleAuthUtil.getToken(context, names[0], "oauth2:" + Scopes.PLUS_LOGIN);
		} catch (UserRecoverableAuthException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GoogleAuthException e) {
			e.printStackTrace();
		}
		Log.d(Constants.TAG_MESSAGE, token);
		return null;
	}

}
