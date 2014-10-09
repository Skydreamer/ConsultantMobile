package views;

import items.MessageItem;

import java.util.ArrayList;

import ru.romangolovan.consultantmobile.R;
import ru.romangolovan.consultantmobile.R.drawable;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ChatAdapter extends ArrayAdapter<MessageItem> {
	Context context;
	ArrayList<MessageItem> messages;
	
	public ChatAdapter(Context context, int resource, ArrayList<MessageItem> messages) {
		super(context, resource, messages);
		
		this.context = context;
		this.messages = messages;
	}

	@Override
	public void add(MessageItem object) {
		super.add(object);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		MessageItem rowItem = getItem(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.message_view_item, null);
			holder = new ViewHolder();
			holder.messageText = (TextView) convertView.findViewById(R.id.messageText);
			holder.messageDate = (TextView) convertView.findViewById(R.id.messageDate);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder) convertView.getTag();
		holder.messageText.setText(rowItem.getText());
		holder.messageDate.setText(rowItem.getDateString());
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		if(rowItem.isMine())
		{
			layoutParams.gravity = Gravity.LEFT;
			layoutParams.setMargins(5, 0, 30, 0);
			holder.messageText.setBackgroundResource(drawable.me_bubble_rot);
			holder.messageText.setTextAlignment(Gravity.LEFT);
			holder.messageDate.setTextAlignment(Gravity.LEFT);
			
		}
		else
		{
			layoutParams.gravity = Gravity.RIGHT;
			layoutParams.setMargins(30, 0, 5, 0);
			holder.messageText.setBackgroundResource(drawable.consul_bubble_rot);
			holder.messageText.setTextAlignment(Gravity.RIGHT);
			holder.messageDate.setTextAlignment(Gravity.RIGHT);
		}
		
		holder.messageText.setLayoutParams(layoutParams);
		holder.messageDate.setLayoutParams(layoutParams);
		
		return convertView;
	}


	@Override
	public boolean isEnabled(int position) {
		return false;
	}


	private class ViewHolder
	{
		TextView messageText;
		TextView messageDate;
	}
}
