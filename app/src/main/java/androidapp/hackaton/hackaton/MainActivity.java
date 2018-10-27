package androidapp.hackaton.hackaton;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCamera = findViewById(R.id.btnCamera);
        imageView = findViewById(R.id.ImageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        //imageView.setImageBitmap(bitmap);

        Intent intent = new Intent(this, ResponseActivity.class);
        intent.putExtra("label", "Some duck or another cool shit");
        ArrayList<String> urlsList = new ArrayList<>();
        urlsList.add("https://stackoverflow.com/questions/18831948/how-parsing-jsonarray-in-java-with-json-simple");
        urlsList.add("https://yandex.ru");
        intent.putStringArrayListExtra("urls", urlsList);
        startActivity(intent);
    }
}
