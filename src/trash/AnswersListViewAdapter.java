
//package trash;
//
//import java.util.ArrayList;
//
//import ru.romangolovan.consultantmobile.R;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class AnswersListViewAdapter extends ArrayAdapter<AnswersListViewItem> 
//			 implements OnItemClickListener, OnItemLongClickListener, OnClickListener {
//	private Context context;
//	private LayoutInflater layoutInflater;
//
//	private ArrayList<AnswersListViewItem> items;
//
//	public AnswersListViewAdapter(Context context, int resource, 
//			ArrayList<AnswersListViewItem> items) {
//		super(context, resource, items);
//		
//		this.context = context;
//		this.items = items;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		//Log.d("AnswersListViewAdapter", "getView()");
//		if(convertView == null)
//		{
//			layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = layoutInflater.inflate(R.layout.answers_listview, parent, false);
//		}
//		
//		TextView answerText = (TextView) convertView.findViewById(R.id.answerTextView);
//		TextView answerDate = (TextView) convertView.findViewById(R.id.answerDateTextView);
//		Button firstButton = (Button) convertView.findViewById(R.id.firstAnswerButton);
//		Button secondButton = (Button) convertView.findViewById(R.id.secondAnswerButton);
//		
//		answerText.setText(items.get(position).getItem().getText());
//		answerDate.setText(items.get(position).getItem().getDate());
//		
//		firstButton.setOnClickListener(this);
//		secondButton.setOnClickListener(this);
//		
//		return convertView;
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {	
//		// TODO что-нить сделать - открыть сайт или диалог
//	}
//
//	@Override
//	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
//			long arg3) {
//		
//		return true;
//	}
//
//	@Override
//	public void onClick(View v) {
//		Toast.makeText(context, String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();
//	}
//}
