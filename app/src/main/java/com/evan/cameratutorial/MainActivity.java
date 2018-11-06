package com.evan.cameratutorial;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    protected static int REQUEST_IMAGE_CAPTURE = 1;
    protected String absoluteImagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTakePictureButtonPressed(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(takePictureIntent,0);

        File picturesFolder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File tempFile;
        try {
            tempFile = File.createTempFile("CameraTutorial", ".jpg", picturesFolder);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Uri fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",tempFile);

        this.absoluteImagePath = tempFile.getAbsolutePath();
        Log.d("Path", absoluteImagePath);

        TextView text = findViewById(R.id.textView);
        text.setText(this.absoluteImagePath);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_IMAGE_CAPTURE)
            return;
        ImageView image = findViewById(R.id.imageView);

        Bitmap thumbnail = BitmapFactory.decodeFile(this.absoluteImagePath);
        image.setImageBitmap(thumbnail);
    }
}
