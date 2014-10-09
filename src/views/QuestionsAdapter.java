package views;

import items.QuestionItem;

import java.util.ArrayList;

import ru.romangolovan.consultantmobile.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionsAdapter extends ArrayAdapter<QuestionItem> {
	Context context;
	ArrayList<QuestionItem> questions;
	
	Animation introAnimation;

	int maxShowItem;

	public QuestionsAdapter(Context context, int resource, ArrayList<QuestionItem> questions) {
		super(context, resource, questions);
		this.context = context;
		this.questions = questions;

		maxShowItem = 0;
	}
	
	public void swapItems(ArrayList<QuestionItem> questions)
	{
		this.questions = questions;
		// TODO TODO TODO
		if(maxShowItem > questions.size())
			maxShowItem = 0;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		QuestionItem rowItem = getItem(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.fragment_question_view_item, null);
			holder = new ViewHolder();
			holder.questionAnswersCount = (TextView) convertView.findViewById(R.id.questionAnswersCount);
			holder.questionText = (TextView) convertView.findViewById(R.id.questionText);
			holder.questionCategory = (TextView) convertView.findViewById(R.id.questionCategory);
			holder.questionDate = (TextView) convertView.findViewById(R.id.questionDate);
			holder.questionState = (ImageView) convertView.findViewById(R.id.questionState);

			convertView.setTag(holder);	
		}

		holder = (ViewHolder) convertView.getTag();
		holder.questionAnswersCount.setText("" + rowItem.getAnswers().size());
		holder.questionText.setText(rowItem.getText());
		holder.questionCategory.setText(rowItem.getCategory());
		holder.questionDate.setText(rowItem.getDateString());

		if(position > maxShowItem && position > utils.Preferences.initItemsAmount)
		{
			introAnimation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
			introAnimation.setDuration(200);
			convertView.startAnimation(introAnimation);		
			maxShowItem = position;
		}

		return convertView;
	}

	private class ViewHolder
	{
		TextView questionAnswersCount;
		TextView questionText;
		TextView questionCategory;
		TextView questionDate;
		ImageView questionState;
	}
}
