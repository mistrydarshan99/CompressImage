package com.example.ti.cameraimagedemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {

    private static final String TAG = "Demo";
    ImageView imageViewMain , imageViewNew;

    ImageBean imageBean;

    TextView txtMain, txtNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewMain = (ImageView) findViewById(R.id.img1);
        imageViewNew = (ImageView) findViewById(R.id.img2);
        txtMain = (TextView) findViewById(R.id.txtMain);
        txtNew = (TextView) findViewById(R.id.txtNew);

        imageViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(MainActivity.this);
                startActivityForResult(chooseImageIntent, 112);
            }
        });

        Button btnOk = (Button) findViewById(R.id.btn1);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bm = ImagePicker.getImageResized(MainActivity.this, imageBean.getImageUri());
                String filePath = AppUtil.setBitmapToFile(bm, 100);
                File newfile = new File(filePath);
                Log.e(TAG, "onClick: old size " + imageBean.getImageLength() + " new size  :: " + newfile.length());
                Glide.with(MainActivity.this)
                        .load(newfile)
                        .into(imageViewNew);
                txtNew.setText("Compressed Size - "+ newfile.length());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 112:
                    ImageBean ib = null;
                    try {
                        ib = ImagePicker.getImageFromResult(MainActivity.this, resultCode, data);

                        Log.e(TAG, "Choosen file size: " + ib.getImageLength() + " :: ");
                        Glide.with(this)
                                .load(ib.getImageUri())
                                .into(imageViewMain);
                        txtMain.setText("Original Size - "+ ib.getImageLength());
                        imageBean = ib;
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