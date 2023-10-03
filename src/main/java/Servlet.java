import static spark.Spark.*;
import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.output.StringBuilderWriter;
import spark.resource.ClassPathResource;
import org.apache.logging.log4j.* ;

public class Servlet {
    private static List<TradeData> tradeList  = new ArrayList<TradeData>() ;

    private static List<TradeDataEnrich> tradeEnrichList = new ArrayList<TradeDataEnrich>() ;

    private static HashMap<Integer,String> productMap = new HashMap<Integer,String>() ;

    private static final Logger log = LogManager.getLogger(Servlet.class);
    public static void main(String[] args) {
        try {
            port(8080);
            loadMap();
            postRequest();
        }
        catch (Exception e) {
        }
    }
    public static void postRequest() throws IOException, CsvException {
        try {
            post("api/v1/enrich", (request, response) -> {
                tradeList = new CsvToBeanBuilder(new StringReader(request.body().toString()))
                        .withType(TradeData.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSkipLines(1)
                        .withIgnoreEmptyLine(true)
                        .build()
                        .parse();

                tradeList.forEach(trade -> {
                    if (valDate(trade.getDate())) {
                        tradeEnrichList.add(enrichData(trade));
                    } else {
                        log.error("Entry not processed productID :" + trade.getProductId());
                    }
                });
                Writer writer = new StringBuilderWriter();

                // mapping of columns with positions
                ColumnPositionMappingStrategy<TradeDataEnrich> mappingStrategy = new ColumnPositionMappingStrategy<TradeDataEnrich>();
                // Set mappingStrategy type to tradeEnrich
                mappingStrategy.setType(TradeDataEnrich.class);
                // Fields in TradeEnrich Bean
                String[] columns = new String[]{"Date", "ProductName", "Currency", "Price"};
                // Setting colums for mappingStrat
                mappingStrategy.setColumnMapping(columns);

                StatefulBeanToCsvBuilder<TradeDataEnrich> builder = new StatefulBeanToCsvBuilder<TradeDataEnrich>(writer);
                StatefulBeanToCsv<TradeDataEnrich> beanWriter = builder.withMappingStrategy(mappingStrategy).build();
                // Write Header
                writer.append("Date,ProductName,Currency,Price" + System.getProperty("line.separator"));

                beanWriter.write(tradeEnrichList);
                writer.close();
                tradeEnrichList.clear();
                //  response.type("text/csv");
                response.body(writer.toString().replaceAll("\"", ""));
                //  Return enrich data to response
                return response.body();

            });
        }
        catch (Exception e ) {
            log.error( e.getMessage());
        }
    }

    private static Boolean valDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMdd");
        try {
            dtf.parse(date) ;
            return Boolean.TRUE;
        }
        catch (Exception ex) {
            log.error( "Cannot Parse Date wrong format :" + date );
            return Boolean.FALSE;
        }
    }

    private static TradeDataEnrich enrichData(TradeData trade) {

        return new TradeDataEnrich (trade.getDate(), productMap(trade.getProductId()), trade.getCurrency(), trade.getPrice()) ;
    }

    private static String productMap(int prodId) {

        if (productMap.get(prodId) != null ) {
            return productMap.get(prodId) ;
        }
        else return "Missing Product Name" ;
    }

    private static void loadMap () throws IOException, CsvException {

        ClassPathResource resource = new ClassPathResource("product.csv");
        InputStream inputStream = resource.getInputStream();
        CSVReader reader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(inputStream)))
                .withSkipLines(1)
                .build();

        List<String[]> r = reader.readAll();
        r.forEach( x ->{ productMap.put( Integer.valueOf(x[0]), x[1] ) ;
        });
    } ;
}