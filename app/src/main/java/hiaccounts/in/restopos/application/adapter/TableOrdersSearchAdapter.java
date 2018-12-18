package hiaccounts.in.restopos.application.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableOrders;

public class TableOrdersSearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<Realm_ItemList> ordersList;
    private LayoutInflater mLayoutInflater;
    private boolean mIsFilterList;

    public TableOrdersSearchAdapter(Context context, List<Realm_ItemList> ordersList, boolean isFilterList) {
        this.mContext = context;
        this.ordersList =ordersList;
        this.mIsFilterList = isFilterList;
    }


    public void updateList(List<Realm_ItemList> filterList, boolean isFilterList) {
        this.ordersList = filterList;
        this.mIsFilterList = isFilterList;
        notifyDataSetChanged ();
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public Realm_ItemList getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if(v==null){

            holder = new ViewHolder();

            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = mLayoutInflater.inflate(R.layout.table_order_list_item_search, parent, false);
            holder.txtItemName = (TextView)v.findViewById(R.id.txt_item_name);
            holder.txtItemPrice = (TextView)v.findViewById(R.id.txt_item_price);

            v.setTag(holder);
        } else{
            holder = (ViewHolder) v.getTag();
        }

        holder.txtItemName.setText(ordersList.get(position).getItemCode()+" | "+ordersList.get(position).getItemName());
        holder.txtItemPrice.setText(""+ordersList.get(position).getUnitPrice());

        Drawable searchDrawable,recentDrawable;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp, null);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp, null);

        } else {
            searchDrawable = mContext.getResources().getDrawable(R.drawable.ic_magnify_grey600_24dp);
            recentDrawable = mContext.getResources().getDrawable(R.drawable.ic_backup_restore_grey600_24dp);
        }
        if(mIsFilterList) {
            holder.txtItemName.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        }else {
            holder.txtItemName.setCompoundDrawablesWithIntrinsicBounds(recentDrawable, null, null, null);

        }
        return v;
    }
}
class ViewHolder{
    TextView txtItemName;
    TextView txtItemPrice;
}