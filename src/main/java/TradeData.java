import com.opencsv.bean.CsvBindByPosition;

public class TradeData {

    @CsvBindByPosition(position = 0)
    private String date;

    @CsvBindByPosition(position = 1)
    private int productId;

    @CsvBindByPosition(position = 2)
    private String currency;

    @CsvBindByPosition(position = 3)
    private double price;

    public String getDate() {
        return date;
    }

    public int getProductId() {
        return this.productId;
    }

    public double getPrice() {
        return this.price;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }
}
