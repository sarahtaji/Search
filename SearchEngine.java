import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SearchEngine {
    private static List<String> stringsList = new ArrayList<>();

    public static void main(String[] args) {
        Spark.port(4567); // You can choose any port number you prefer
        
        // Path for adding a new string to the list
        Spark.post("/add", new AddStringRoute());
        
        // Path for querying strings with a given substring
        Spark.get("/search", new SearchStringRoute());
    }

    // Handler for adding a new string to the list
    private static class AddStringRoute implements Route {
        @Override
        public Object handle(Request req, Response res) throws Exception {
            String newString = req.queryParams("s");
            stringsList.add(newString);
            return "String added: " + newString;
        }
    }

    // Handler for querying strings with a given substring
    private static class SearchStringRoute implements Route {
        @Override
        public Object handle(Request req, Response res) throws Exception {
            String substring = req.queryParams("s");
            List<String> matchingStrings = new ArrayList<>();
            for (String s : stringsList) {
                if (s.contains(substring)) {
                    matchingStrings.add(s);
                }
            }
            return matchingStrings;
        }
    }
}
