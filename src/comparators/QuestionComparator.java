package comparators;

import items.QuestionItem;

import java.util.Comparator;

import enums.SortType;

public class QuestionComparator implements Comparator<QuestionItem> {
	SortType type;
	
	public QuestionComparator(SortType type)
	{
		this.type = type;
	}
	
	@Override
	public int compare(QuestionItem lhs, QuestionItem rhs) {
		switch(type)
		{
		case ASC:
			return (int) (lhs.getTime() - rhs.getTime());

		case DESK:
			return (int) (rhs.getTime() - lhs.getTime());
			
		default:
			return 0;
		}
	}
}
