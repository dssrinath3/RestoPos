package hiaccounts.in.restopos.customer.adapter;

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
import hiaccounts.in.restopos.application.realm_model.data.Realm_Customer;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> implements Filterable {
    private List<Realm_Customer> customerList;
    private List<Realm_Customer> currentList;
    private LayoutInflater inflater;
    private Context context;
    protected ItemListener mListener;
    public interface ItemListener {
        void onItemClick(Realm_Customer realmCustomer, View view);
    }
    public CustomerListAdapter(Activity activity, List<Realm_Customer> customerList, ItemListener itemListener) {
        this.context = activity;
        this.customerList = customerList;
        this.currentList=customerList;
        mListener=itemListener;
    }

    @Override
    public CustomerListAdapter.CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_view,parent,false);
        return new CustomerListAdapter.CustomerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomerListAdapter.CustomerViewHolder holder, int position) {
        holder.setData(customerList.get(position));
        holder.nametxt.setText(customerList.get(position).getCustomerName());
        holder.idtxt.setText(""+customerList.get(position).getCustomerId());
        holder.phonetxt.setText(""+customerList.get(position).getPhoneNumber());
        holder.addresstxt.setText(customerList.get(position).getCustomerAddress());
    }
    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void setItems(List<Realm_Customer> filteredGalaxies)
    {
        this.customerList=filteredGalaxies;
    }

    @Override
    public Filter getFilter() {
        return CustomerListAdapter.FilterHelper.newInstance(currentList,this);
    }


    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nametxt,idtxt,phonetxt,addresstxt;
        Realm_Customer customer;
        public CustomerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nametxt= itemView.findViewById(R.id.customer_name);
            idtxt= itemView.findViewById(R.id.customer_id);
            phonetxt= itemView.findViewById(R.id.phone_no);
            addresstxt= itemView.findViewById(R.id.address);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(customer,v);
            }
        }

        public void setData(Realm_Customer realmCustomer) {
            this.customer = realmCustomer;
        }
    }



    public static class FilterHelper extends Filter {
        static List<Realm_Customer> currentList;
        static CustomerListAdapter adapter;

        public static CustomerListAdapter.FilterHelper newInstance(List<Realm_Customer> currentList, CustomerListAdapter adapter) {
            CustomerListAdapter.FilterHelper.adapter=adapter;
            CustomerListAdapter.FilterHelper.currentList=currentList;
            return new CustomerListAdapter.FilterHelper();
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
                List<Realm_Customer> foundFilters=new ArrayList<>();

                String customerName,address;
                Long customerId,phoneNumber;

                //ITERATE CURRENT LIST
                for (int i=0;i<currentList.size();i++)
                {
                    customerName= currentList.get(i).getCustomerName();
                    address= currentList.get(i).getCustomerAddress();
                    phoneNumber = currentList.get(i).getPhoneNumber();
                    customerId = currentList.get(i).getCustomerId();
                    //SEARCH
                    if(customerName.toUpperCase().contains(constraint) || String.valueOf(customerId).toUpperCase().contains(constraint))
                    {
                        Realm_Customer dataModel = new Realm_Customer();
                        dataModel.setCustomerId(customerId);
                        dataModel.setCustomerName(customerName);
                        dataModel.setPhoneNumber(phoneNumber);
                        dataModel.setCustomerAddress(address);
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

            adapter.setItems((List<Realm_Customer>) filterResults.values);
            adapter.notifyDataSetChanged();
        }
    }


}
