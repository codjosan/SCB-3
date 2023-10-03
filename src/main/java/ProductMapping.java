import com.opencsv.bean.CsvBindByPosition;

public class ProductMapping {

    @CsvBindByPosition(position = 0)
    private int productId;

    @CsvBindByPosition(position = 1)
    private String productName;

    public String getProductName() {
        return this.productName;
    }
    public Integer getProductId() { return this.productId;     }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }
}
