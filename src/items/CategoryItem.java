package items;

public class CategoryItem {
	public static long amount;
	
	private String name;
	private int count;
	
	public CategoryItem(String name, int count)
	{
		this.name = name;
		this.count = count;
		
		++amount;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		--amount;
	}
}
