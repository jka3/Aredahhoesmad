package hoes.mad.application;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.Gson;

class Document {
    public String id, language, text;

    public Document(String text){

        this.text = text;
    }
}

class Documents {
    public List<Document> documents;

    public Documents() {
        this.documents = new ArrayList<Document>();
    }
    public void add(String text) {
        this.documents.add (new Document (text));
    }
}


public class AnalyzeText {

    //access key to us API
    static String accessKey = "1a5b5eb42cc6432db09adbc05cd6ae2b";

// Replace or verify  region.

// "westcentralus" in the URI below with "westus".

    // NOTE: Free trial access keys are generated in the westcentralus region, so if you are using
// a free trial access key, you should not need to change this region.
    static String host = "https://westcentralus.api.cognitive.microsoft.com";

    static String path = "/text/analytics/v2.1/sentiment";

    public static String getTheSentiment(Documents documents) throws Exception  {
        try {
            String text = new Gson().toJson(documents);
            byte[] encoded_text = text.getBytes("UTF-8");
            //Builds url to send to server
            URL url = new URL(host + path);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(encoded_text, 0, encoded_text.length);
            wr.flush();
            wr.close();



            StringBuilder response = new StringBuilder();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            return response.toString();
        } catch (FileNotFoundException e) { //can not find file of server for some reason :/ prevent app from crashing
                System.out.println("a");

                String text = new Gson().toJson(documents);
                byte[] encoded_text = text.getBytes("UTF-8");

                URL url = new URL("https://westcentralus.api.cognitive.microsoft.com/text/analytics/v2.1/sentiment[?showStats]");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "text/json");
                connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
                connection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.write(encoded_text, 0, encoded_text.length);
                wr.flush();
                wr.close();

                StringBuilder response = new StringBuilder();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                return response.toString();
        }
    }

    public static String getSentiment(final java.lang.String json) {
        double madValue;
        if (json != null) {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject().getAsJsonObject("sentiment");
            JsonArray sentiment = result.getAsJsonArray("documents");
            madValue = sentiment.get(0).getAsJsonObject().get("sentiment").getAsInt();
            if (madValue <= 0.25) {
                return "They are MAD";
            } else if (madValue <= 0.50) {
                return "They are Moderately mad";
            } else if (madValue <= 0.75) {
                return "They are not very mad but be careful";
            } else {
                return "They are not mad";
            }
        }
        return "oops";
    }
}
