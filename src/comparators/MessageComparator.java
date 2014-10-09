package comparators;

import items.MessageItem;

import java.util.Comparator;

import enums.SortType;

public class MessageComparator implements Comparator<MessageItem>{
	SortType type;

	public MessageComparator(SortType type)
	{
		this.type = type;
	}

	@Override
	public int compare(MessageItem lhs, MessageItem rhs) {
		switch(type)
		{
		case ASC:
			return 1;

		case DESK:
			return -1;

		default:
			return 0;
		}
	}

}
