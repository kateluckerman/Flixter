package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    String videoId;
    ImageView ivBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        ivBackdrop = findViewById(R.id.ivBackdrop);
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        String placeholder = "flicks_backdrop_placeholder.gif";
        Glide.with(getApplicationContext()).load(movie.getBackdropPath()).placeholder(Drawable.createFromPath(placeholder)).into(ivBackdrop);

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        // calls to videos endpoint to find video id
        final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/"+ movie.getId()+ "/videos?api_key=" + getString(R.string.YT_api_key);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEOS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    // get the first result
                    if (results.length() > 0) {
                        JSONObject result = results.getJSONObject(0);
                        videoId = result.getString("key");
                    }

                    if (videoId != null) {
                        // add a click listener to the backdrop image (first add the backdrop image)

                        // use an intent and pass the video id as an extra string
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MovieDetailsActivity", "onFailure " + response);
            }
        });
    }
}