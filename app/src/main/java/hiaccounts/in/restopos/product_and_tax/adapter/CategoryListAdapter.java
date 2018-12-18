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
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemCategoryList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> implements Filterable {
    private List<Realm_ItemCategoryList> itemLists;
    private List<Realm_ItemCategoryList> currentList;
    private LayoutInflater inflater;
    private Context context;
    private final CategoryListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Realm_ItemCategoryList item);
    }


    public CategoryListAdapter(Activity activity, List<Realm_ItemCategoryList> itemLists, OnItemClickListener listener) {
        this.context = activity;
        this.itemLists = itemLists;
        this.currentList=itemLists;
        this.listener = listener;
    }

    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_categorylist_orders_view,parent,false);
        return new CategoryListAdapter.CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        holder.bind(itemLists.get(position), listener);
        holder.nametxt.setText(itemLists.get(position).getItemCategoryName());
        holder.idtxt.setText(""+itemLists.get(position).getItemCategoryId());

        if (itemLists.get(position).getPriority()!=null)
        holder.prioritytxt.setText(""+itemLists.get(position).getPriority());
    }
    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public void setItems(List<Realm_ItemCategoryList> filteredGalaxies)
    {
        this.itemLists=filteredGalaxies;
    }

    @Override
    public Filter getFilter() {
        return CategoryListAdapter.FilterHelper.newInstance(currentList,this);
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView nametxt,idtxt,prioritytxt;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            nametxt= itemView.findViewById(R.id.item_category);
            idtxt= itemView.findViewById(R.id.item_category_code);
            prioritytxt= itemView.findViewById(R.id.item_priority);

        }

        public void bind(final Realm_ItemCategoryList item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }



    public static class FilterHelper extends Filter {
        static List<Realm_ItemCategoryList> currentList;
        static CategoryListAdapter adapter;

        public static CategoryListAdapter.FilterHelper newInstance(List<Realm_ItemCategoryList> currentList, CategoryListAdapter adapter) {
            CategoryListAdapter.FilterHelper.adapter=adapter;
            CategoryListAdapter.FilterHelper.currentList=currentList;
            return new CategoryListAdapter.FilterHelper();
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
                List<Realm_ItemCategoryList> foundFilters=new ArrayList<>();

                String categoryName;
                Long categoryId,priority;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    categoryName= currentList.get(i).getItemCategoryName();
                    categoryId = currentList.get(i).getItemCategoryId();
                    priority = currentList.get(i).getPriority();
                    //SEARCH
                    if(categoryName.toUpperCase().contains(constraint) || String.valueOf(categoryId).toUpperCase().contains(constraint) || String.valueOf(priority).toUpperCase().contains(constraint))
                    {
                        Realm_ItemCategoryList dataModel = new Realm_ItemCategoryList();
                        dataModel.setItemCategoryId(categoryId);
                        dataModel.setItemCategoryName(categoryName);
                        dataModel.setPriority(priority);
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

            adapter.setItems((List<Realm_ItemCategoryList>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }




}
