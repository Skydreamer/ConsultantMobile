//package trash;
//
//import java.util.ArrayList;
//
//
//import ru.romangolovan.consultantmobile.R;
//import utils.Constants;
//import activities.AnswersActivity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class QuestionsAdapter extends ArrayAdapter<QuestionsListItem> 
//			 implements OnItemClickListener {
//	private Context context;
//	private LayoutInflater inflater;
//	
//	private ArrayList<QuestionsListItem> items;
//	
//	public QuestionsAdapter(Context context, int resource,
//			ArrayList<QuestionsListItem> items) {
//		super(context, resource, items);
//		Log.d(Constants.TAG_CALL, "QuestionsListViewAdapter()");
//		
//		this.context = context;
//		this.items = items;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		//Log.d("QuestionsListViewAdapter", "getView()");
//		if(convertView == null)
//		{
//			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = inflater.inflate(R.layout.questions_listview, parent, false);
//		}
//		
//		TextView questionText = (TextView) convertView.findViewById(R.id.questionTextView);
//		TextView questionCategory = (TextView) convertView.findViewById(R.id.questionCategoryTextView);
//		TextView questionDate = (TextView) convertView.findViewById(R.id.questionDateTextView);
//		EditText questionAnswersCount = (EditText) convertView.findViewById(R.id.questionAnswersCountEditText);
//		
//		questionText.setText(items.get(position).getItem().getText());
//		questionCategory.setText(items.get(position).getItem().getCategory());
//		questionDate.setText(items.get(position).getItem().getDateString());
//		questionAnswersCount.setText(String.valueOf(items.get(position).getItem().getAnswers().size()));
//		
//		if(!items.get(position).isLooked())
//			questionAnswersCount.setTypeface(null, Typeface.BOLD);
//		else
//			questionAnswersCount.setTypeface(null, Typeface.NORMAL);
//		
//		return convertView;
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		Intent intent = new Intent(context, AnswersActivity.class);
//		intent.putExtra("QuestionPosition", position);
//		items.get(position).look();
//		context.startActivity(intent);
//	}
//
//	
//
//}
