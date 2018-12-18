package hiaccounts.in.restopos.product_and_tax.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableOrders;

public class OrdersAdapter extends BaseAdapter {

    Context context;
    List<Realm_TableOrders> ordersList;

    public OrdersAdapter(Context context, List<Realm_TableOrders> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tv_item_qty;
        TextView tv_item_name;
        TextView tv_item_price;
        TextView tv_item_total_price;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.table_item_orders_view, null);
            holder = new ViewHolder();
            holder.tv_item_qty = (TextView) convertView.findViewById(R.id.item_qty);
            holder.tv_item_name = (TextView) convertView.findViewById(R.id.item_name);
            holder.tv_item_price = (TextView) convertView.findViewById(R.id.item_price);
            holder.tv_item_total_price = (TextView) convertView.findViewById(R.id.item_total_price);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Realm_TableOrders orders = (Realm_TableOrders) getItem(position);

        if (orders!=null){
            if (orders.getQty()!=null){
                holder.tv_item_qty.setText(""+orders.getQty());
            }
            if (orders.getItemName()!=null){
                holder.tv_item_name.setText(orders.getItemName());
            }
            if (orders.getUnitPrice()!=null){
                holder.tv_item_price.setText(""+orders.getUnitPrice());
            }

            if (orders.getTotalPrice()!=null){
                holder.tv_item_total_price.setText(""+orders.getTotalPrice());
            }

        }



        return convertView;
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ordersList.indexOf(getItem(position));
    }
}
