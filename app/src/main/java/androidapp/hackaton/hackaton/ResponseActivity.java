package androidapp.hackaton.hackaton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ResponseActivity extends AppCompatActivity {

    private ListView listView;
    private TextView labelTextView;

    private String label = "default-label";
    private List<String> urls = Arrays.asList("www.default-urls.ru", "apmath.spbu.ru", "abcd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        listView = findViewById(R.id.list_view);
        labelTextView = findViewById(R.id.label_text_view);

        Intent intent = getIntent();

        label = intent.getStringExtra("label");
        urls = intent.getStringArrayListExtra("urls");

        this.labelTextView.setText(label);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, urls);

        this.listView.setAdapter(adapter);
    }
}
