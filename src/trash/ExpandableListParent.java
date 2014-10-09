//
//
//import java.util.ArrayList;
//
//
//public class ExpandableListParent {
//	private Object parent;
//	private Object date;
//	private Object category;
//	private long createTime;
//	private boolean isLooked = false;
//	
//	private ArrayList<ExpandableListChildren> parentChildren;
//
//	public ExpandableListParent(Object parent, 
//								ArrayList<ExpandableListChildren> parentChildren)
//	{
//		this.parent = parent;
//		this.date = null;
//		this.createTime = 0;
//		this.category = "Null";
//		this.parentChildren = parentChildren;
//	}
//	
//	public ExpandableListParent(Object parent, Object date, 
//								long createTime, Object category,
//								ArrayList<ExpandableListChildren> parentChildren)
//	{
//		this.parent = parent;
//		this.date = date;
//		this.createTime = createTime;
//		this.category = category;
//		this.parentChildren = parentChildren;
//	}
//	
//	public Object getParent()
//	{
//		return parent;
//	}
//	public Object getDate()
//	{
//		return date;
//	}
//	public long getCreateTime()
//	{
//		return createTime;
//	}
//	public Object getCategory()
//	{
//		return category;
//	}
//	public ArrayList<ExpandableListChildren> getParentChildren()
//	{
//		return parentChildren;
//	}
//	public boolean getIsLooked()
//	{
//		return isLooked;
//	}
//	
//////////////////////////////////////////////////////////////////////
//	
//	public void setParent(Object parent)
//	{
//		this.parent = parent;
//	}
//
//	public void setData(Object date)
//	{
//		this.date = date;
//	}
//	
//	public void setCreateTime(long createTime)
//	{
//		this.createTime = createTime;
//	}
//	
//	public void setCategory(Object category)
//	{
//		this.category = category;
//	}
//		
//	public void setParentChildren(ArrayList<ExpandableListChildren> parentChildren)
//	{
//		this.parentChildren = parentChildren;
//	}
//	
//	public void setIsLooked(boolean isLooked)
//	{
//		this.isLooked = isLooked;
//	}
//}
