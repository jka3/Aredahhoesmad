package hoes.mad.application;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
//import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.support.v7.app.AppCompatActivity;
import hoes.mad.application.AnalyzeText;
import hoes.mad.application.Documents;

/**
 * main activity.
 */
public final class MainActivity extends AppCompatActivity {

    /** Request queue for our network requests. */
    private RequestQueue requestQueue;

    private EditText getInput;

    private Button analyzeText;
    private  static boolean areFinish = false;
    private static String mad;
    String userText;
    public static Documents doc;
    /**
     * Run when our activity comes into view.
     *
     * @param savedInstanceState state that was saved by the activity last time it was paused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);

        // Load the main layout for our activity
        setContentView(R.layout.activity_main);
        getInput = findViewById(R.id.putText);
        /*
         * Set up handlers for each button in our UI. These run when the buttons are clicked.
         */
        //final ImageButton checkCat = findViewById(R.id.isACat)
        analyzeText = findViewById(R.id.analyzeText);

        analyzeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userText = getInput.getText().toString();
                doc.add(userText);
                areFinish = true;
                openResults();
                ResultsPage.textView.setText(getMad());
            }
        });

    }

    /**
     * g.
     */

    public void openResults() {
        Intent intent = new Intent(this, ResultsPage.class);
        startActivity(intent);
    }
    public static String getMad() {
        try {
            String response = AnalyzeText.getTheSentiment(doc);
            mad = AnalyzeText.getSentiment(response);
        } catch (Exception e) {
            System.out.println(e);
        }

        return mad;
    }
}
