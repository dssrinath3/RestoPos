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
import hiaccounts.in.restopos.application.realm_model.data.Realm_Discount;

public class DiscountListAdapter extends RecyclerView.Adapter<DiscountListAdapter.DiscountViewHolder> implements Filterable {
    private List<Realm_Discount> itemLists;
    private List<Realm_Discount> currentList;
    private LayoutInflater inflater;
    private Context context;


    public DiscountListAdapter(Activity activity, List<Realm_Discount> itemLists) {
        this.context = activity;
        this.itemLists = itemLists;
        this.currentList=itemLists;
    }

    @Override
    public DiscountListAdapter.DiscountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_discountlist_orders_view,parent,false);
        return new DiscountListAdapter.DiscountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DiscountListAdapter.DiscountViewHolder holder, int position) {

        holder.nametxt.setText(itemLists.get(position).getDiscountName());
        holder.idtxt.setText(""+itemLists.get(position).getDiscountId());
        holder.valtxt.setText(""+itemLists.get(position).getDiscountAmount()+"%");


    }
    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void setItems(List<Realm_Discount> filteredGalaxies)
    {
        this.itemLists=filteredGalaxies;
    }

    @Override
    public Filter getFilter() {
        return DiscountListAdapter.FilterHelper.newInstance(currentList,this);
    }


    class DiscountViewHolder extends RecyclerView.ViewHolder {

        TextView nametxt,idtxt,valtxt;
        public DiscountViewHolder(View itemView) {
            super(itemView);
            nametxt= itemView.findViewById(R.id.discount_name);
            idtxt= itemView.findViewById(R.id.discount_id);
            valtxt= itemView.findViewById(R.id.discount_amount);
        }
    }



    public static class FilterHelper extends Filter {
        static List<Realm_Discount> currentList;
        static DiscountListAdapter adapter;

        public static DiscountListAdapter.FilterHelper newInstance(List<Realm_Discount> currentList, DiscountListAdapter adapter) {
            DiscountListAdapter.FilterHelper.adapter=adapter;
            DiscountListAdapter.FilterHelper.currentList=currentList;
            return new DiscountListAdapter.FilterHelper();
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
                List<Realm_Discount> foundFilters=new ArrayList<>();

                String discountName;
                Long discountId;
                Double discountAmt = 0.00;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    discountName= currentList.get(i).getDiscountName();
                    discountId = currentList.get(i).getDiscountId();
                    //SEARCH
                    if(discountName.toUpperCase().contains(constraint) || String.valueOf(discountId).toUpperCase().contains(constraint))
                    {
                        Realm_Discount dataModel = new Realm_Discount();
                        dataModel.setDiscountId(discountId);
                        dataModel.setDiscountName(discountName);
                        dataModel.setDiscountAmount(discountAmt);
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

            adapter.setItems((List<Realm_Discount>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }


}
