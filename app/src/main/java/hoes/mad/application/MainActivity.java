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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
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


import edu.illinois.cs.cs125.spring2019.mp3.lib.RecognizePhoto;

/**
 * main activity.
 */
public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "MP3:Main";

    /** Constant to perform a read file request. */
    private static final int READ_REQUEST_CODE = 42;

    /** Constant to request an image capture. */
    private static final int IMAGE_CAPTURE_REQUEST_CODE = 1;

    /** Constant to request permission to write to the external storage device. */
    private static final int REQUEST_WRITE_STORAGE = 112;

    /** Threshold for calling something a dog or cat. */
    private static final double RECOGNITION_THRESHOLD = 0.9;

    /** Request queue for our network requests. */
    private RequestQueue requestQueue;

    /** Whether we can write to public storage. */
    private boolean canWriteToPublicStorage = false;

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

        /*
         * Set up handlers for each button in our UI. These run when the buttons are clicked.
         */
        //final ImageButton checkCat = findViewById(R.id.isACat)
        ImageButton resultsPage = findViewById(R.id.resultsPage);
        resultsPage.setOnClickListener(v -> {
            openResults();
            Volley.newRequestQueue(this);
        });

        ImageButton button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Log.d(TAG, "next");
            openDir();
            Volley.newRequestQueue(this);
        });
        // There are a few button that we disable into an image has been loaded
//        enableOrDisableButtons(false);

        // We also want to make sure that our progress bar isn't spinning, and style it a bit
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources()
                        .getColor(R.color.colorPrimaryDark, getTheme()), PorterDuff.Mode.SRC_IN);

        /*
         * Here we check for permission to write to external storage and request it if necessary.
         * Normally you would not want to do this on ever start, but we want to be persistent
         * since it makes development a lot easier.
         */
        canWriteToPublicStorage = (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        Log.d(TAG, "Do we have permission to write to external storage: "
                + canWriteToPublicStorage);
        if (!canWriteToPublicStorage) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    /**
     * g.
     */
    public void openDir() {
        Intent intent = new Intent(this, actDir.class);
        startActivity(intent);
    }

    public void openResults() {
        Intent intent = new Intent(this, activityResults.class);
        startActivity(intent);
    }
