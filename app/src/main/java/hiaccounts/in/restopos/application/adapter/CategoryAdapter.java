package hiaccounts.in.restopos.application.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.realm_model.data.Realm_ItemList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Realm_ItemList> mValues;
    private Context mContext;
    protected ItemListener mListener;


    public CategoryAdapter(Context context, List<Realm_ItemList> values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvItemName,tvPrice;
        private ImageView imageView;
        private RelativeLayout relativeLayout;
        private Realm_ItemList item;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvItemName = (TextView) v.findViewById(R.id.textView);
            tvPrice = (TextView) v.findViewById(R.id.price);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
        }

        public void setData(Realm_ItemList item) {
            this.item = item;
            tvItemName.setText(item.getItemName());
            tvPrice.setText(""+item.getUnitPrice());
            if (item.getImageLocation()!=null && !item.getImageLocation().equals("")){
                imageView.setImageBitmap(BitmapFactory.decodeFile(item.getImageLocation()));
            }else {
                imageView.setImageResource(R.drawable.masal_vada);
            }
            relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorBrown));
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item,view);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_row_itemlist_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setData(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(Realm_ItemList item, View view);
    }
}
