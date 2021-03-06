package com.example.fingtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity {
    Button b1;
    ImageView signImage;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        b1 = (Button) findViewById(R.id.getSign);
        signImage = (ImageView) findViewById(R.id.imageView1);
        b1.setOnClickListener(onButtonClick);
    }
 
    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
 
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent i = new Intent(MainActivity.this, CaptureSignature.class);
            startActivityForResult(i, 0);
        }
    };
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 1) {
            Bitmap b = BitmapFactory.decodeByteArray(
                    data.getByteArrayExtra("byteArray"), 0,
                    data.getByteArrayExtra("byteArray").length);
            signImage.setImageBitmap(b);
        }
    }
}