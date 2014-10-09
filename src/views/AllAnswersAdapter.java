package views;

import items.AnswerItem;

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

public class AllAnswersAdapter extends ArrayAdapter<AnswerItem> {
	Context context;
	ArrayList<AnswerItem> answers;
	
	Animation introAnimation;
	
	int maxShowItem;
	
	public AllAnswersAdapter(Context context, int resource, ArrayList<AnswerItem> answers) {
		super(context, resource, answers);
		this.context = context;
		this.answers = answers;
		
		maxShowItem = 0;
	}
	
	public void swapItems(ArrayList<AnswerItem> answers)
	{
		this.answers = answers;
		// TODO TODO TODO
		if(maxShowItem > answers.size())
			maxShowItem = 0;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		AnswerItem rowItem = getItem(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.fragment_answer_view_item, null);
			holder = new ViewHolder();
			holder.answerImage = (ImageView) convertView.findViewById(R.id.answerImage);
			holder.answerConsultantName = (TextView) convertView.findViewById(R.id.answerConsultantName);
			holder.answerMessagesCount = (TextView) convertView.findViewById(R.id.answerMessagesCount);
			holder.answerTitleMessage = (TextView) convertView.findViewById(R.id.answerTitleMessage);
			holder.answerTitleTime = (TextView) convertView.findViewById(R.id.answerTitleTime);
			convertView.setTag(holder);	
		}
	
		holder = (ViewHolder) convertView.getTag();
		
		holder.answerConsultantName.setText(rowItem.getConsultant().getName());
		holder.answerTitleMessage.setText(rowItem.getMessages().get(0).getText());
		holder.answerTitleTime.setText(rowItem.getMessages().get(0).getDateString());
		holder.answerMessagesCount.setText("" + rowItem.getMessages().size());
		
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
		ImageView answerImage;
		TextView answerConsultantName;
		TextView answerMessagesCount;
		TextView answerTitleMessage;
		TextView answerTitleTime;
	}
}
