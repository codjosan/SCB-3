import com.opencsv.bean.CsvBindByPosition;

public class TradeDataEnrich {

    @CsvBindByPosition(position = 0)
    private String date;

    @CsvBindByPosition(position = 1)
    private String productName;

    @CsvBindByPosition(position = 2)
    private String currency;

    @CsvBindByPosition(position = 3)
    private double price;

    public TradeDataEnrich(String date, String name, String ccy, double price) {
        this.date = date ;
        this.productName= name;
        this.currency = ccy ;
        this.price = price ;
    }

    public String getDate() {
        return date;
    }

    public String getProductName() {
        return this.productName;
    }

    public double getPrice() {
        return this.price;
    }

}
