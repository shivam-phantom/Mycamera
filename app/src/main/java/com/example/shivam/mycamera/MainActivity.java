package com.example.shivam.mycamera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button button,invert;
    ImageView imageView;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button =(Button)findViewById(R.id.button);
        imageView =(ImageView)findViewById(R.id.imageView);
        invert = (Button)findViewById(R.id.invert);
        invert.setVisibility(View.INVISIBLE);
        if(!hasCamera()){
            button.setEnabled(false);
        }
        Drawable dhoni = getResources().getDrawable(R.drawable.dhoni);
        Bitmap image = ((BitmapDrawable)dhoni).getBitmap();
    }
    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void launchCamera(View view){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK ){
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
            invert.setVisibility(View.VISIBLE);
        }
    }
    public void invertImage(View v){
       // Drawable dhoni = getResources().getDrawable(R.drawable.dhoni);
       // Bitmap image = ((BitmapDrawable)dhoni).getBitmap();
        Bitmap inverted= Bitmap.createBitmap(photo.getWidth(),photo.getHeight(),photo.getConfig());
        int pixelcolor;
        int A,R,B,G;
        for(int y=0;y<photo.getHeight();y++){
            for(int x=0;x<photo.getWidth();x++){
                pixelcolor=photo.getPixel(x,y);
                A= Color.alpha(pixelcolor);
                R=255 - Color.red(pixelcolor);
                B=255 - Color.blue(pixelcolor);
                G=255 - Color.green(pixelcolor);
                inverted.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        imageView.setImageBitmap(inverted);
    }
}
