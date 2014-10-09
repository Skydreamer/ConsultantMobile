package views;

import items.CategoryItem;


public class CategorySpinnerItem {
	private CategoryItem item;
	private boolean isHint;
	
	public CategorySpinnerItem(CategoryItem item, boolean isHint)
	{
		this.item = item;
		this.isHint = isHint;
	}
	
	public CategorySpinnerItem(String name, int count, boolean isHint)
	{
		item = new CategoryItem(name, count);
		this.isHint = isHint;
	}

	public CategoryItem getItem() {
		return item;
	}

	public void setItem(CategoryItem item) {
		this.item = item;
	}

	public boolean isHint() {
		return isHint;
	}

	public void setHint(boolean isHint) {
		this.isHint = isHint;
	}
	
}
