package com.example.identitycard;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import static com.example.identitycard.FirstTimeUse.SHARED_PREFS;
import static com.example.identitycard.FirstTimeUse.TEXT;
import static com.example.identitycard.FirstTimeUse.userAlias;
import static com.example.identitycard.FirstTimeUse.WEB;
import static com.example.identitycard.FirstTimeUse.FORMAT;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import java.io.InputStream;

public class user_homepage extends AppCompatActivity {

    MaterialToolbar toolbar1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;


    String URL;
    ImageView qrCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        URL = sp.getString(TEXT, "");

        toolbar1 = findViewById(R.id.toolbar);
        //getSupportActionBar().hide();

        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        frameLayout = findViewById(R.id.main_framelayout);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigation);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //SharedPreferences sp1 = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        //qrCode.setImageBitmap();

        new DownloadImageTask((ImageView) findViewById(R.id.businessCard_iv))
                .execute(WEB+userAlias+FORMAT);



        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(URL, BarcodeFormat.QR_CODE, 270,250);
            int height = bitMatrix.getHeight();
            int width = bitMatrix.getWidth();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            qrCode = findViewById(R.id.qrCode_iv);
            qrCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
