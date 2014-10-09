package items;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utils.Constants;

public class QuestionItem {
	private ArrayList<AnswerItem> answers;
	public static long amount;
	
	private String text;
	private String category;
	private Date date;
	private String dateString;
	private String cookie;
	private long time;
	private int count;
	private long tableId;
	
	private boolean isOutdated;

	public QuestionItem(String text, String category, 
			long time, int count, String cookie)
	{
		answers = new ArrayList<AnswerItem>();
		
		this.text = text;
		this.category = category;
		this.time = time;
		this.count = count;
		this.cookie = cookie;
		
		this.isOutdated = false;
		
		parseDate();
		
		++amount;
	}
	
	public QuestionItem(String text, String category, 
			long time, int count, String cookie, long tableId)
	{
		answers = new ArrayList<AnswerItem>();
		
		this.text = text;
		this.category = category;
		this.time = time;
		this.count = count;
		this.cookie = cookie;
		this.tableId = tableId;
		
		this.isOutdated = false;
		
		parseDate();
		
		++amount;
	}
	
	public void parseDate()
	{
		this.date = new Date(time * 1000L);
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		this.dateString = formatter.format(this.date);
	}
	
	public void addAnswer(AnswerItem item)
	{
		answers.add(item);
	}
	
	public void addAnswer(int position, AnswerItem item)
	{
		answers.add(position, item);
	}

	public ArrayList<AnswerItem> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<AnswerItem> answers) {
		this.answers = answers;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public boolean isOutdated() {
		return isOutdated;
	}

	public void setOutdated(boolean isOutdated) {
		this.isOutdated = isOutdated;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		--amount;
	}
}
