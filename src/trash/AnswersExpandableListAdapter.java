/*package views;

import java.util.ArrayList;

import ru.romangolovan.consultantmobile.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AnswersExpandableListAdapter extends BaseExpandableListAdapter 
implements OnClickListener, OnChildClickListener {

	LayoutInflater layoutInflater;
	ArrayList<AnswersExpandableListItem> answers;
	ArrayList<String> actions;
	Context context;

	public AnswersExpandableListAdapter(Context context, ArrayList<AnswersExpandableListItem> answers)
	{
		this.context = context;
		this.answers = answers;
		this.layoutInflater = LayoutInflater.from(context);
		
		actions = new ArrayList<String>();
		actions.add("Перейти на сайт");
		actions.add("Инфо о консультанте");
		actions.add("Удалить ответ");
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return actions.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.answers_list_actions, parent, false);
		}

		TextView actionName = (TextView) convertView.findViewById(R.id.actionTextView);
		actionName.setText(actions.get(childPosition));

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return actions.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return answers.get(groupPosition).getAnswer().getText();
	}

	@Override
	public int getGroupCount() {
		return answers.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			convertView = layoutInflater.inflate(R.layout.answers_listview, parent, false);
		}

		TextView answerText = (TextView) convertView.findViewById(R.id.answerText);
		TextView answerDate = (TextView) convertView.findViewById(R.id.answerDate);

		answerText.setText(answers.get(groupPosition).getAnswer().getText());
		answerDate.setText(answers.get(groupPosition).getAnswer().getDateString());

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(context, v.toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Toast.makeText(context, "Ответ - " + groupPosition + " действие - " + childPosition, Toast.LENGTH_SHORT).show();
		return false;
	}

}
*/