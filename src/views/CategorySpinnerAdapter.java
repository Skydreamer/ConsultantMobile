package views;

import java.util.List;


import ru.romangolovan.consultantmobile.R;
import utils.Constants;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class CategorySpinnerAdapter extends ArrayAdapter<CategorySpinnerItem> implements SpinnerAdapter{
	Context context;
	int layoutResourceId;
	int textViewResourceId;
	List<CategorySpinnerItem> categoriesList;

	@Override
	public void setDropDownViewResource(int resource) {
		super.setDropDownViewResource(resource);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.spinner_dropdown_item, null);
		}

		TextView categoryName = (TextView) convertView.findViewById(R.id.categoryNameDropDown);
		TextView categoryCount = (TextView) convertView.findViewById(R.id.categoryCountDropDown);

		if(!categoriesList.get(position).isHint())
		{
			categoryName.setText(categoriesList.get(position).getItem().getName());
			categoryCount.setText(String.valueOf(categoriesList.get(position).getItem().getCount()));
		}
			
		return convertView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.spinner_selected_item, null);
		}

		TextView categoryName = (TextView) convertView.findViewById(R.id.categoryNameSelect);
		TextView categoryCount = (TextView) convertView.findViewById(R.id.categoryCountSelect);
		
		if(!categoriesList.get(position).isHint())
		{
			categoryName.setText(categoriesList.get(position).getItem().getName());
			categoryCount.setText(String.valueOf(categoriesList.get(position).getItem().getCount()));
		}

		return convertView;
	}

	public CategorySpinnerAdapter(Context context, int resource, List<CategorySpinnerItem> objects) {
		super(context, resource, objects);
		Log.d(Constants.TAG_CALL, "CustomSpinnerAdapter()");

		this.context = context;
		this.layoutResourceId = resource;
		this.categoriesList = objects;
	}

	public CategorySpinnerAdapter(Context context, int layout_resource, int resource, List<CategorySpinnerItem> objects) {
		super(context, layout_resource, resource, objects);
		Log.d(Constants.TAG_CALL, "CustomSpinnerAdapter()");

		this.context = context;
		this.layoutResourceId = layout_resource;
		this.textViewResourceId = resource;
		this.categoriesList = objects;
	}

	@Override
	public int getCount() {
		if(categoriesList.size() == 0)
			return 0;
		return categoriesList.size() - 1;
	}

	@Override
	public CategorySpinnerItem getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}
	
	public List<CategorySpinnerItem> getCategoriesList()
	{
		return categoriesList;
	}
}
