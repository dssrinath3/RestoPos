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
import hiaccounts.in.restopos.application.realm_model.data.Realm_Tax;

public class TaxListAdapter extends RecyclerView.Adapter<TaxListAdapter.TaxViewHolder> implements Filterable {
    private List<Realm_Tax> itemLists;
    private List<Realm_Tax> currentList;
    private LayoutInflater inflater;
    private Context context;
    private final TaxListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Realm_Tax item);
    }

    public TaxListAdapter(Activity activity, List<Realm_Tax> itemLists, OnItemClickListener listener) {
        this.context = activity;
        this.itemLists = itemLists;
        this.currentList=itemLists;
        this.listener = listener;
    }

    @Override
    public TaxListAdapter.TaxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_taxlist_orders_view,parent,false);
        return new TaxListAdapter.TaxViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaxListAdapter.TaxViewHolder holder, int position) {
        holder.bind(itemLists.get(position), listener);
        holder.nametxt.setText(itemLists.get(position).getTaxName()+"("+itemLists.get(position).getTaxAmount()+"%)");
        holder.idtxt.setText(""+itemLists.get(position).getTaxId());


    }
    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void setItems(List<Realm_Tax> filteredGalaxies)
    {
        this.itemLists=filteredGalaxies;
    }

    @Override
    public Filter getFilter() {
        return TaxListAdapter.FilterHelper.newInstance(currentList,this);
    }


    class TaxViewHolder extends RecyclerView.ViewHolder {

        TextView nametxt,idtxt;
        public TaxViewHolder(View itemView) {
            super(itemView);
            nametxt= itemView.findViewById(R.id.item_Tax);
            idtxt= itemView.findViewById(R.id.item_tax_id);

        }

        public void bind(final Realm_Tax item, final OnItemClickListener listener) {    itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
        }
    }



    public static class FilterHelper extends Filter {
        static List<Realm_Tax> currentList;
        static TaxListAdapter adapter;

        public static TaxListAdapter.FilterHelper newInstance(List<Realm_Tax> currentList, TaxListAdapter adapter) {
            TaxListAdapter.FilterHelper.adapter=adapter;
            TaxListAdapter.FilterHelper.currentList=currentList;
            return new TaxListAdapter.FilterHelper();
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
                List<Realm_Tax> foundFilters=new ArrayList<>();

                String taxName;
                Long taxId;
                Double taxAmont = 0.00;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    taxName= currentList.get(i).getTaxName();
                    taxId = currentList.get(i).getTaxId();
                    //SEARCH
                    if(taxName.toUpperCase().contains(constraint) || String.valueOf(taxId).toUpperCase().contains(constraint))
                    {
                        Realm_Tax dataModel = new Realm_Tax(1l, "gst", 10l, false);
                        dataModel.setTaxName(taxName);
                        dataModel.setTaxId(taxId);
                        dataModel.setTaxAmount(taxAmont);
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

            adapter.setItems((List<Realm_Tax>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }



}
