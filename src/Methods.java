import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Methods {

    private static final int HTTP_OK = 200;

    public static String makeRESTCall(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        StringBuilder output = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));) {
            int response_code = connection.getResponseCode();
            if (response_code != HTTP_OK) { throw new IOException("Error code : " + response_code); }

            String line;
            while ((line = br.readLine()) != null) {
                output.append(line);
            }
        }
        finally {
            connection.disconnect();
        }

        return output.toString();
    }
}
