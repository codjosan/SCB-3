import com.opencsv.exceptions.CsvException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.route.Routes;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;
public class TestPostRoute {
    @Before
    public void setUp() throws Exception {
        String [] args = {};
        Servlet.main(args);
        awaitInitialization();
    }

    @After
    public void shutDown() throws Exception {
        stop();
    }

    @Test
    public void testModePOST() throws IOException, CsvException {

        String testUrl = "/api/v1/enrich";
      //Servlet res = res.request("POST", testUrl, null);

     //   assertEquals(csvResult, res.status);
    }
}
