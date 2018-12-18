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
import hiaccounts.in.restopos.application.realm_model.data.Realm_TableName;

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.TableViewHolder> implements Filterable {

    private List<Realm_TableName> itemLists;
    private List<Realm_TableName> currentList;
    private LayoutInflater inflater;
    private Context context;
    private final TableListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Realm_TableName item);
    }

    public TableListAdapter(Activity activity, List<Realm_TableName> itemLists, OnItemClickListener listener) {
        this.context = activity;
        this.itemLists = itemLists;
        this.currentList=itemLists;
        this.listener = listener;
    }

    @Override
    public TableListAdapter.TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_tablelist_orders_view,parent,false);
        return new TableListAdapter.TableViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TableListAdapter.TableViewHolder holder, int position) {
        holder.bind(itemLists.get(position), listener);
        holder.nametxt.setText(itemLists.get(position).getTableName());
        holder.idtxt.setText(""+itemLists.get(position).getId());


    }
    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void setItems(List<Realm_TableName> filteredGalaxies)
    {
        this.itemLists=filteredGalaxies;
    }

    @Override
    public Filter getFilter() {
        return TableListAdapter.FilterHelper.newInstance(currentList,this);
    }


    class TableViewHolder extends RecyclerView.ViewHolder {

        TextView nametxt,idtxt;
        public TableViewHolder(View itemView) {
            super(itemView);
            nametxt= itemView.findViewById(R.id.table_name);
            idtxt= itemView.findViewById(R.id.table_id);
        }

        public void bind(final Realm_TableName item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    public static class FilterHelper extends Filter {
        static List<Realm_TableName> currentList;
        static TableListAdapter adapter;

        public static TableListAdapter.FilterHelper newInstance(List<Realm_TableName> currentList, TableListAdapter adapter) {
            TableListAdapter.FilterHelper.adapter=adapter;
            TableListAdapter.FilterHelper.currentList=currentList;
            return new TableListAdapter.FilterHelper();
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
                List<Realm_TableName> foundFilters=new ArrayList<>();

                String tableName;
                Long tableId;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    tableName= currentList.get(i).getTableName();
                    tableId = currentList.get(i).getId();
                    //SEARCH
                    if(tableName.toUpperCase().contains(constraint) || String.valueOf(tableId).toUpperCase().contains(constraint))
                    {
                        Realm_TableName dataModel = new Realm_TableName();
                        dataModel.setTableName(tableName);
                        dataModel.setId(tableId);
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

            adapter.setItems((List<Realm_TableName>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }


}
