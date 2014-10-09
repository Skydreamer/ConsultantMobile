package trash;

import items.QuestionItem;


public class QuestionsListItem {
	private QuestionItem item;
	private boolean isLooked;
	
	public QuestionsListItem(QuestionItem item)
	{
		this.item = item;
		isLooked = false;
	}
	
	public void look()
	{
		this.isLooked = true;
	}

	public QuestionItem getItem() {
		return item;
	}

	public boolean isLooked() {
		return isLooked;
	}

	public void setItem(QuestionItem item) {
		this.item = item;
	}

	public void setLooked(boolean isLooked) {
		this.isLooked = isLooked;
	}
}
