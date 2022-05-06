package skripsi.android.andrian.Adapter.model;

public class productCategory {

    Integer productId;
    String productName;

    public productCategory(Integer productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }


    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
