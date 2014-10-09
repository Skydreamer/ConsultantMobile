package views;

import items.AnswerItem;

import java.util.ArrayList;

import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import activities.ChatActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AnswersAdapter extends ArrayAdapter<AnswerItem> implements
OnItemClickListener {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<AnswerItem> items;

	public AnswersAdapter(Context context, int resource,
			ArrayList<AnswerItem> items) {
		super(context, resource, items);
		Log.d(Constants.TAG_CALL, "AnswersListViewAdapter()");

		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_answer_view_item, parent, false);
		}

		//ImageView answerImage = (ImageView) convertView.findViewById(R.id.answerImage);
		TextView answerText = (TextView) convertView.findViewById(R.id.answerConsultantName);
		TextView answerDate = (TextView) convertView.findViewById(R.id.answerTitleTime);

		if(items.get(position).getMessages().size() > 0)
		{
			answerText.setText(items.get(position).getConsultant().getName());
			answerDate.setText(items.get(position).getMessages().get(0).getDateString());

		}

		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Intent intent = new Intent(context, ChatActivity.class);
		intent.putExtra("AnswerID", items.get(position).getTableId());
		context.startActivity(intent);
	}

}
