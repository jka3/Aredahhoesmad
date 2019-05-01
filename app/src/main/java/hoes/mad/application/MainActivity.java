package hoes.mad.application;

import android.content.Intent;
import android.os.StrictMode;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//mport android.widgi
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.support.v7.app.AppCompatActivity;
import hoes.mad.application.AnalyzeText;
import hoes.mad.application.Documents;

/**
 * main activity.
 */
public final class MainActivity extends AppCompatActivity {

    /** Request queue for our network requests. */
    private RequestQueue requestQueue;
    private final static String TAG = "fuck";
    private EditText getInput;

    private Button analyzeText;
    private  static boolean areFinish = false;
    private static String mad = "blank";
    private String userText;
    public static Documents doc;
    /**
     * Run when our activity comes into view.
     *
     * @param savedInstanceState state that was saved by the activity last time it was paused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestQueue = Volley.newRequestQueue(this);
        userText = "";
        super.onCreate(savedInstanceState);
        //ResultsPage.textView = findViewById(R.id.resultsinfo);
        // Load the main layout for our activity
        setContentView(R.layout.activity_main);
        getInput = findViewById(R.id.putText);
        /*
         * Set up handlers for each button in our UI. These run when the buttons are clicked.
         */
        analyzeText = findViewById(R.id.analyzeText);
        doc = new Documents();
        analyzeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userText = (getInput.getText().toString());
                if (userText.matches("")) {
                    ResultsPage.textView.setText("oops");
                    openResults();
                    return;
                }
                if (userText != null) {
                    doc.add(userText);
                } else {
                    throw new NullPointerException("Try Again!");
                }
                areFinish = true;
                openResults();
            }
        });

    }

    /**
     * g.
     */

    public void openResults() {
        Intent intent = new Intent(MainActivity.this, ResultsPage.class);
        startActivity(intent);
    }
    public static String getMad() {
        try {
            String response = AnalyzeText.getTheSentiment(doc);
            mad = AnalyzeText.getSentiment(response);
            return "hello";
        } catch (Exception e) {
            Log.e(TAG, "getMad: ",e);
            return e.toString();
        }

        //return mad;
    }
}
