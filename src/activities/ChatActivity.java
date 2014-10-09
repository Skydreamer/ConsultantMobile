package activities;

import items.AnswerItem;
import items.MessageItem;
import ru.romangolovan.consultantmobile.R;
import ru.romangolovan.consultantmobile.R.layout;
import utils.Constants;
import views.ChatAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import data.MasterData;

public class ChatActivity extends Activity implements OnClickListener {
	ListView listView;
	ChatAdapter chatAdapter;
	Button sendButton;
	EditText sendText;

	private AnswerItem parentAnswer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_chat);

		initiateViews();
		
		Intent intent = getIntent();
		long answerID = intent.getLongExtra("AnswerID", -1);

		if(answerID == -1)
			Log.d(Constants.TAG_ERROR, "AnswerID == -1");

		parentAnswer = findAnswerByTableID(answerID);
		
		setTitle(parentAnswer.getConsultant().getName());

		if(parentAnswer != null)
		{
			chatAdapter = new ChatAdapter(this, R.layout.message_view_item, parentAnswer.getMessages());
			listView.setAdapter(chatAdapter);
		}
		else
			Log.d(Constants.TAG_ERROR, "parentAnswer == NULL");		
	}
	
	private void initiateViews()
	{
		listView = (ListView) findViewById(R.id.messagesListView);
		sendButton = (Button) findViewById(R.id.messageSendButton);
		sendText = (EditText) findViewById(R.id.messageSendText);
		
		sendButton.setOnClickListener(this);
	}

	private AnswerItem findAnswerByTableID(long id)
	{
		for(AnswerItem answer: MasterData.answers)
			if(answer.getTableId() == id)
				return answer;
		return null;
	}

	@Override
	public void onClick(View v) {
		String text = sendText.getText().toString();
		if(!text.equalsIgnoreCase(""))
		{
			MessageItem message = new MessageItem(parentAnswer, text, System.currentTimeMillis() / 1000L, true);
			parentAnswer.getMessages().add(message);
			chatAdapter.notifyDataSetChanged();
			sendText.setText("");

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					MessageItem message = new MessageItem(parentAnswer, "Ответ консультанта", System.currentTimeMillis() / 1000L, false);	
					parentAnswer.getMessages().add(message);
					chatAdapter.notifyDataSetChanged();
				}
			}, 1000);
		}
	}

}
