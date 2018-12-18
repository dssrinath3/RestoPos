package hiaccounts.in.restopos.product_and_tax.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_Tax;

public class ItemTaxListAdapter extends ArrayAdapter<Realm_Tax> {
    private List<Realm_Tax> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
    }

    public ItemTaxListAdapter(List<Realm_Tax> data, Context context) {
        super(context, R.layout.item_taxlist_row, data);
        this.dataSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Realm_Tax getItem(int position) {
        return dataSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taxlist_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Realm_Tax item = getItem(position);

        if (item!=null){
            Log.e("taxxxxx",item.getTaxName());
        }

        viewHolder.txtName.setText(item.getTaxName());
        viewHolder.checkBox.setChecked(item.isChecked());


        return result;
    }
}
