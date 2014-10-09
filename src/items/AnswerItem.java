package items;

import java.util.ArrayList;

public class AnswerItem {
	private ArrayList<MessageItem> messages;
	public static long amount;

	private long consultantId;
	private Consultant consultant;
	private QuestionItem parentQuestion;
	
	private long tableId;
	
	public AnswerItem(QuestionItem parent, Consultant consultant)
	{
		messages = new ArrayList<MessageItem>();
		
		this.parentQuestion = parent;
		this.consultant = consultant;
		this.consultantId = consultant.getPublicId();

		++amount;
	}
	
	public AnswerItem(QuestionItem parent, Consultant consultant, long tableId)
	{
		messages = new ArrayList<MessageItem>();
		
		this.parentQuestion = parent;
		this.consultant = consultant;
		this.consultantId = consultant.getPublicId();
		this.tableId = tableId;

		++amount;
	}


	public AnswerItem(QuestionItem parent, Consultant consultant, ArrayList<MessageItem> messages, long tableId)
	{
		this.parentQuestion = parent;
		this.consultant = consultant;
		this.consultantId = consultant.getPublicId();
		this.messages = messages;
		this.tableId = tableId;

		++amount;
	}


	public long getConsultantId() {
		return consultantId;
	}


	public void setConsultantId(long consultantId) {
		this.consultantId = consultantId;
	}


	public ArrayList<MessageItem> getMessages() {
		return messages;
	}


	public void setMessages(ArrayList<MessageItem> messages) {
		this.messages = messages;
	}


	public Consultant getConsultant() {
		return consultant;
	}


	public void setConsultant(Consultant consultant) {
		this.consultant = consultant;
	}


	public QuestionItem getParentQuestion() {
		return parentQuestion;
	}


	public void setParentQuestion(QuestionItem parent) {
		this.parentQuestion = parent;
	}


	public long getTableId() {
		return tableId;
	}


	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

}
