package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableConfigDetail {
    @SerializedName("row")
    @Expose
    public String row;
    @SerializedName("subRow")
    @Expose
    public List<SubRow> subRow = null;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public List<SubRow> getSubRow() {
        return subRow;
    }

    public void setSubRow(List<SubRow> subRow) {
        this.subRow = subRow;
    }
}
