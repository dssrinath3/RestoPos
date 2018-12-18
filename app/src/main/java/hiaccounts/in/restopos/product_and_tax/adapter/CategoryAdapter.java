package hiaccounts.in.restopos.product_and_tax.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.model.pageloaddata.ItemCategory;

public class CategoryAdapter extends ArrayAdapter<ItemCategory> {
    private List<ItemCategory> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
    }

    public CategoryAdapter(List<ItemCategory> data, Context context) {
        super(context, R.layout.category_list_items, data);
        this.dataSet = data;
        this.mContext=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemCategory dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.category_list_items, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.product_name);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.txtName.setText(dataModel.getItemCategoryName());
        // Return the completed view to render on screen
        return convertView;
    }
}
