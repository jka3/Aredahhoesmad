package hoes.mad.application;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class AnalyzeText extends java.lang.Object {
    /**
     *
     * @param json checks.
     * @return
     */
    public static String getSentiment(final java.lang.String json) {
        double madValue;
        if (json != null) {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject().getAsJsonObject("sentiment");
            JsonArray sentiment = result.getAsJsonArray("documents");
            madValue = sentiment.get(0).getAsJsonObject().get("sentiment").getAsInt();
            if (madValue <= 0.25) {
                return "HOES MAD";
            } else if (madValue <= 0.50) {
                return "Hoes Moderately mad";
            } else if (madValue <= 0.75) {
                return "Hoes not very mad but be careful";
            } else {
                return "Hoes not mad";
            }
        }
        return "oops";
    }
}
}
