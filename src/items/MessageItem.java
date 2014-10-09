package items;

import java.text.SimpleDateFormat;
import java.util.Date;

import utils.Constants;


public class MessageItem {
	public static long amount;
	
	private AnswerItem parentAnswer;
	private String text;
	private String dateString;
	private Date date;
	private long time;
	
	private boolean isMine;
	
	public MessageItem(AnswerItem parentAnswer, String text, long time, boolean isMine)
	{
		this.text = text;
		this.time = time;
		this.isMine = isMine;
		this.parentAnswer = parentAnswer;

		parseDate();
		amount++;
	}
	
	public void parseDate()
	{
		
		this.date = new Date(time * 1000L);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		this.dateString = formatter.format(this.date);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public AnswerItem getParentAnswer() {
		return parentAnswer;
	}

	public void setParentAnswer(AnswerItem parentAnswer) {
		this.parentAnswer = parentAnswer;
	}


}
