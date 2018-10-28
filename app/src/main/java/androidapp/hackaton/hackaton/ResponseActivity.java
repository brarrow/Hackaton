package androidapp.hackaton.hackaton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponseActivity extends AppCompatActivity {

    private ListView listView;
    private TextView labelTextView;
    private ImageView imageView;

    private String label = "default-label";
    private List<String> urls = Arrays.asList("www.default-urls.ru", "apmath.spbu.ru", "abcd");
    private List<String> names = Arrays.asList("default-url1", "default-url2", "def3");
    private List<String> prices = Arrays.asList("100 000", "322", "228");
    private File originFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        listView = findViewById(R.id.list_view);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(2, Color.BLACK);
        listView.setBackground(drawable);

        labelTextView = findViewById(R.id.label_text_view);
        imageView = findViewById(R.id.imageView);

        //передний план текстовой вьюхи
        labelTextView.bringToFront();

        Intent intent = getIntent();


        label = intent.getStringExtra("label");
        urls = intent.getStringArrayListExtra("urls");
        names = intent.getStringArrayListExtra("titles");
        prices = intent.getStringArrayListExtra("prices");
        originFile = (File) intent.getSerializableExtra("originFile");
        imageView.setImageURI(Uri.fromFile(originFile));

        this.labelTextView.setText(label);

        List<String> listForAdapter = new ArrayList<>();

        for (int i = 0; i < urls.size(); ++i) {
            StringBuilder builder = new StringBuilder("");
            builder.append("Title: ").append(names.get(i)).append("\nPrice: ").append(prices.get(i));

            listForAdapter.add(builder.toString());
        }

        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listForAdapter);
        this.listView.setAdapter(sAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urls.get(position)));
                startActivity(i);
            }
        });

    }
}
