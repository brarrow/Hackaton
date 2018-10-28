package androidapp.hackaton.hackaton;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static androidapp.hackaton.hackaton.RealPathUtils.getFilePath;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private ServerCommunication communication = new ServerCommunication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);
        imageView = findViewById(R.id.ImageView);

        imageView.setImageResource(R.drawable.ytka);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File image = null;
        Bitmap bitmap;
        if (requestCode == 1) {
            final Uri imageUri = data.getData();

            try {
                image = new File(getFilePath(this, imageUri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            imageView.setImageURI(imageUri);
        } else {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            try {
                image = File.createTempFile("tmpFile", ".png", getCacheDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (FileOutputStream fos = new FileOutputStream(image)) {
                fos.write(bitmapdata);
                fos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }


        //write the bytes in file
        final PostPhotoTask postPhotoTask = new PostPhotoTask(communication, this);
        postPhotoTask.execute(image);

    }

}
