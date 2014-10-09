package comparators;

import items.CategoryItem;

import java.util.Comparator;

import enums.SortType;

public class CategoryComparator implements Comparator<CategoryItem>{
	SortType type;

	public CategoryComparator(SortType type)
	{
		this.type = type;
	}

	@Override
	public int compare(CategoryItem lhs, CategoryItem rhs) {
		switch(type)
		{
		case ASC:
			return rhs.getName().compareTo(lhs.getName());
			
		case DESK:
			return lhs.getName().compareTo(rhs.getName());
			
		default:
			return 0;
		}
	}


}
