package hiaccounts.in.restopos.application.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.model.table.TableModel;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {
    private List<TableModel> tableModelList = new ArrayList<>();
    private Context context;

    public TableAdapter(List<TableModel> horizontalList, Context context) {
        this.tableModelList = horizontalList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return tableModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final TableViewHolder holder, final int position) {
        final TableModel model = tableModelList.get(position);
        TableViewHolder tableViewHolder = (TableViewHolder) holder;

        tableViewHolder.tvTableName.setText(model.getTableName());
        tableViewHolder.tvTableNo.setText(""+model.getTableNo());

        tableViewHolder.tvTableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, model.getTableName()+" - "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {

        TextView tvTableName,tvTableNo;

        private TableViewHolder(View view) {
            super(view);
            tvTableName =  view.findViewById(R.id.table_name);
            tvTableNo =  view.findViewById(R.id.table_no);


        }
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row_view, parent, false);
        return new TableViewHolder(itemView);
    }
}
