package hiaccounts.in.restopos.application.model.pageloaddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubRow {
    @SerializedName("tableName")
    @Expose
    public String tableName;
    @SerializedName("tableValue")
    @Expose
    public String tableValue;
    @SerializedName("occupied")
    @Expose
    public Boolean occupied;
    @SerializedName("floorId")
    @Expose
    public Object floorId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableValue() {
        return tableValue;
    }

    public void setTableValue(String tableValue) {
        this.tableValue = tableValue;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Object getFloorId() {
        return floorId;
    }

    public void setFloorId(Object floorId) {
        this.floorId = floorId;
    }
}
