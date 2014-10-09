package views;

import ru.romangolovan.consultantmobile.R;
import utils.DatabaseController;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChatCursorAdapter extends CursorAdapter {
	Context context;
	
	public ChatCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);
		this.context = context;
		
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.message_view_item, parent, false);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView messageText = (TextView) view.findViewById(R.id.messageText);
		TextView messageDate = (TextView) view.findViewById(R.id.messageDate);

		messageText.setText(cursor.getString(cursor.getColumnIndex(DatabaseController.MESSAGES_COLUMN_TEXT)));
		messageDate.setText(cursor.getString(cursor.getColumnIndex(DatabaseController.MESSAGES_COLUMN_TIME)));
	}



}
