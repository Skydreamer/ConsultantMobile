//
//
//import java.util.ArrayList;
//
//import ru.romangolovan.consultantmobile.R;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnGroupClickListener;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.Toast;
//
//public class ExpandableListAdapter extends BaseExpandableListAdapter implements
//		Filterable, OnClickListener, OnGroupClickListener {
//	
//	private LayoutInflater inflater;
//	private ArrayList<ExpandableListParent> mParent;
//	private Context context;
//	
//	public ArrayList<ExpandableListParent> getMParent()
//	{
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getMParent()");
//		return mParent;
//	}
//	
//	public void setMParent(ArrayList<ExpandableListParent> mParent)
//	{
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - setMParent()");
//		this.mParent = mParent;
//	}
//	
//	public ExpandableListAdapter(Context context, 
//			ArrayList<ExpandableListParent> parentList, ExpandableListView expandableListView)
//	{
//		this.mParent = parentList;
//		this.context = context;
//		this.inflater = LayoutInflater.from(context);
//	}
//
//	// gets the name of each item
//	@Override
//	public Object getChild(int groupPosition, int childPosition) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getChild()");
//		return mParent.get(groupPosition).getParentChildren().get(childPosition);
//	}
//
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getChildId()");
//		return childPosition;
//	}
//
//	// in this method you must set the text to see the children on the list
//	@Override
//	public View getChildView(int groupPosition, int childPosition,
//			boolean isLastChild, View convertView, ViewGroup parent) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getChildView()");
//		
//	/*	if(convertView == null)
//		{
//			convertView = inflater.inflate(R.layout.explist_childview, parent, false);
//		}
//		
//		TextView textAnswer = (TextView) convertView.findViewById(R.id.textAnswer);
//		Button infoButton = (Button) convertView.findViewById(R.id.infoButton);
//		Button dialogButton = (Button) convertView.findViewById(R.id.dialogButton);
//		
//		textAnswer.setText(mParent.get(groupPosition).getParentChildren().get(childPosition).getChildren().toString());
//		
//		infoButton.setOnClickListener(this);
//		dialogButton.setOnClickListener(this);*/
//		
//		return convertView;
//	}
//
//	// counts the number of children items so the list knows how many times
//    // calls getChildView() method
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getChildrenCount()");
//		int size = 0;
//		if(mParent.get(groupPosition).getParentChildren() != null)
//			size = mParent.get(groupPosition).getParentChildren().size();
//		return size;
//	}
//
//	// gets the title of each parent/group
//	@Override
//	public Object getGroup(int groupPosition) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getGroup()");
//		return mParent.get(groupPosition).getParent();
//	}
//
//	// counts the number of group/parent items so the list knows how many
//    // times calls getGroupView() method
//	@Override
//	public int getGroupCount() {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getGroupCount()");
//		return mParent.size();
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getGroupId()");
//		return groupPosition;
//	}
//
//	// in this method you must set the text to see the parent/group on the list
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parent) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getGroupView() " + groupPosition);
//	
//		if(convertView == null)
//		{
//			convertView = inflater.inflate(R.layout.questions_listview, parent, false);
//		}
//		
//	/*	TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
//		TextView textCategory = (TextView) convertView.findViewById(R.id.textCategory);
//		TextView textDate = (TextView) convertView.findViewById(R.id.textDate);
//		EditText numberOfAnswers = (EditText) convertView.findViewById(R.id.numberOfAnswers);
//		*///numberOfAnswers.setOnClickListener(this);
///*		
//		textGroup.setText(mParent.get(groupPosition).getParent().toString());
//		textCategory.setText(mParent.get(groupPosition).getCategory().toString());
//		textDate.setText(mParent.get(groupPosition).getDate().toString());
//		numberOfAnswers.setText(String.valueOf(getChildrenCount(groupPosition)));
//		
//		if(!mParent.get(groupPosition).getIsLooked())
//		{
//			numberOfAnswers.setTypeface(null, Typeface.BOLD);
//		}*/
//	
//		return convertView;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - hasStabledIds()");
//		return true;
//	}
//
//	@Override
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - isChildSelectable()");
//		return true;
//	}
//
//	@Override
//	public Filter getFilter() {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - getFilter()");
//		return null;
//	}
//
//	@Override
//	public void onClick(View v) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - onClick()");
//		Toast.makeText(context, v.toString(), Toast.LENGTH_SHORT).show();	
//	}
//
//	@Override
//	public boolean onGroupClick(ExpandableListView parent, View v,
//			int groupPosition, long id) {
//		Log.d(context.getString(R.string.logCalls), "ExpandableListAdapter - onGroupClick()");
//		return true;
//	}
//
//}
