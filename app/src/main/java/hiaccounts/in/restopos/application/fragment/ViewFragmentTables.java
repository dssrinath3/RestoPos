package hiaccounts.in.restopos.application.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hiaccounts.in.restopos.R;
import hiaccounts.in.restopos.application.ViewFragment;
import hiaccounts.in.restopos.application.adapter.TableAdapter;
import hiaccounts.in.restopos.application.model.table.TableModel;

/**
 * srinath.
 */
public class ViewFragmentTables extends Fragment {


    @BindView(R.id.recyclerviewTables)
    RecyclerView recyclerviewTables;
    Unbinder unbinder;
    private TableAdapter tableAdapter;
    private LinearLayoutManager horizontalLayoutManager;
    private Activity activity;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tables, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        callTableView();
    }

    private void callTableView() {
        tableAdapter = new TableAdapter(fillWithTableData(), activity);
        horizontalLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewTables.setLayoutManager(horizontalLayoutManager);
        recyclerviewTables.setAdapter(tableAdapter);
    }

    private List<TableModel> fillWithTableData() {
        List<TableModel> tableModelsList = new ArrayList<>();
        TableModel tableModel = new TableModel();
        tableModel.setTableName("Tab1");
        tableModel.setTableNo(1L);
        tableModelsList.add(tableModel);

        TableModel tableModel2 = new TableModel();
        tableModel2.setTableName("Tab2");
        tableModel.setTableNo(2L);
        tableModelsList.add(tableModel2);

        TableModel tableModel3 = new TableModel();
        tableModel3.setTableName("Tab3");
        tableModel.setTableNo(3L);
        tableModelsList.add(tableModel3);

        TableModel tableModel4 = new TableModel();
        tableModel4.setTableName("Tab4");
        tableModel.setTableNo(4L);
        tableModelsList.add(tableModel4);

        TableModel tableModel5 = new TableModel();
        tableModel5.setTableName("Tab5");
        tableModel.setTableNo(5L);
        tableModelsList.add(tableModel5);

        TableModel tableModel6 = new TableModel();
        tableModel6.setTableName("Tab6");
        tableModel.setTableNo(6L);
        tableModelsList.add(tableModel6);

        TableModel tableModel7 = new TableModel();
        tableModel7.setTableName("Tab7");
        tableModel.setTableNo(7L);
        tableModelsList.add(tableModel7);

        TableModel tableModel8 = new TableModel();
        tableModel8.setTableName("Tab8");
        tableModel.setTableNo(8L);
        tableModelsList.add(tableModel8);

        return tableModelsList;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
