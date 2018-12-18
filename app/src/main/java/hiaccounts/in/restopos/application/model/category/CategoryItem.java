package hiaccounts.in.restopos.application.model.category;

public class CategoryItem {
    public String itemName;
    public Double itemPrice;
    public int drawable;
    public int color;

    public CategoryItem(String itemName, int drawable, int color, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.drawable = drawable;
        this.color = color;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
