package hiaccounts.in.restopos.product_and_tax.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> implements Filterable {
    private List<Realm_ItemList> itemLists;
    private final List<Realm_ItemList> currentList;
    private LayoutInflater inflater;
    private Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Realm_ItemList item);
    }



    public ItemListAdapter(Activity activity, List<Realm_ItemList> itemLists, OnItemClickListener listener) {
        this.context = activity;
        this.itemLists = itemLists;
        this.currentList=itemLists;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_itemlist_orders_view,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(itemLists.get(position), listener);
        holder.nametxt.setText(itemLists.get(position).getItemName());
        holder.qtytxt.setText(""+itemLists.get(position).getQty());
        holder.pricetxt.setText(""+itemLists.get(position).getUnitPrice());
        holder.itemcodetxt.setText(itemLists.get(position).getItemCode());
    }
    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void setItems(List<Realm_ItemList> filteredGalaxies)
    {
        this.itemLists=filteredGalaxies;
    }

    @Override
    public Filter getFilter() {
        return FilterHelper.newInstance(currentList,this);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView nametxt,qtytxt,pricetxt,itemcodetxt;
        public ItemViewHolder(View itemView) {
            super(itemView);
            nametxt= itemView.findViewById(R.id.item_name);
            qtytxt= itemView.findViewById(R.id.item_qty);
            pricetxt= itemView.findViewById(R.id.item_total_price);
            itemcodetxt= itemView.findViewById(R.id.item_code);

        }

        public void bind(final Realm_ItemList item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    public static class FilterHelper extends Filter {
        static List<Realm_ItemList> currentList;
        static ItemListAdapter adapter;

        public static FilterHelper newInstance(List<Realm_ItemList> currentList, ItemListAdapter adapter) {
            FilterHelper.adapter=adapter;
            FilterHelper.currentList=currentList;
            return new FilterHelper();
        }

        /*
        - Perform actual filtering.
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                //CHANGE TO UPPER
                constraint=constraint.toString().toUpperCase();

                //HOLD FILTERS WE FIND
                List<Realm_ItemList> foundFilters=new ArrayList<>();

                String itemName,itemCode;
                Double qty,price;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    itemName= currentList.get(i).getItemName();
                    itemCode = currentList.get(i).getItemCode();
                    qty = currentList.get(i).getQty();
                    price = currentList.get(i).getUnitPrice();
                    //SEARCH
                    if(itemName.toUpperCase().contains(constraint) || itemCode.toUpperCase().contains(constraint))
                    {
                        Realm_ItemList dataModel = new Realm_ItemList();
                        dataModel.setItemName(itemName);
                        dataModel.setItemCode(itemCode);
                        dataModel.setQty(qty);
                        dataModel.setUnitPrice(price);
                        foundFilters.add(dataModel);
                    }
                }

                //SET RESULTS TO FILTER LIST
                filterResults.count=foundFilters.size();
                filterResults.values=foundFilters;
            }else
            {
                //NO ITEM FOUND.LIST REMAINS INTACT
                filterResults.count=currentList.size();
                filterResults.values=currentList;
            }

            //RETURN RESULTS
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            adapter.setItems((List<Realm_ItemList>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }




}
