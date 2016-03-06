package net.nonylene.stacker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // View 取得
        final EditText editText = (EditText) findViewById(R.id.edit_text);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editText.getText().toString();
                requestApi(searchText);
            }
        });
    }

    private void requestApi(String text) {
        String encodedText = null;
        try {
            encodedText = URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException ignore) {}

        String url = "https://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle=" +
                encodedText + "&site=stackoverflow&filter=!6JEajsykLFu3W";

        Request request = new Request.Builder()
                .url(url)
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String body = response.body().string();

                try {
                    JSONObject json = new JSONObject(body);

                    JSONArray questionsJson = json.getJSONArray("items");

                    final List<Question> questions = new ArrayList<>();

                    for (int i = 0; i < questionsJson.length(); i++) {
                        questions.add(new Question(questionsJson.getJSONObject(i)));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, questions.get(0).title, Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (JSONException ignore) {}
            }
        });
    }
}
