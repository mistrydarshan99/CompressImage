package com.example.ti.cameraimagedemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = "Demo";
    ImageView imageView;
    private EditText etQty;
    File file;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.img1);
        etQty = (EditText) findViewById(R.id.etQty);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(MainActivity.this);
                startActivityForResult(chooseImageIntent, 112);
            }
        });

        Button btnOk = (Button)findViewById(R.id.btn1);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tmpFile = new File(Environment.getExternalStorageDirectory(),"CrashLog");
                File tmpName = new File(tmpFile, "tmp.jpeg");

//                String filePath = SiliCompressor.with(MainActivity.this).compress(uri.toString());
                String filePath = AppUtil.compressImage(uri.toString(), MainActivity.this, Integer.parseInt(etQty.getText().toString().trim()));
                Log.e(TAG, "onClick: "+filePath );
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 112:
                    try {
                        file = ImagePicker.getImageFileFromResult(MainActivity.this, resultCode, data);
                        uri =ImagePicker.getImageFromResult(MainActivity.this, resultCode, data);

                        imageView.setImageURI(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;
            }
        }
    }
}