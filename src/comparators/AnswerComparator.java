package comparators;

import items.AnswerItem;

import java.util.Comparator;

import enums.SortType;

public class AnswerComparator implements Comparator<AnswerItem>{
	SortType type;
	
	public AnswerComparator(SortType type)
	{
		this.type = type;
	}
	
	@Override
	public int compare(AnswerItem lhs, AnswerItem rhs) {
		switch (type) {
		case ASC:
			return (int) (lhs.getMessages().get(0).getTime() - rhs.getMessages().get(0).getTime());

		case DESK:
			return (int) (rhs.getMessages().get(0).getTime() - lhs.getMessages().get(0).getTime());
			
		default:
			return 0;
		}
	}
}
