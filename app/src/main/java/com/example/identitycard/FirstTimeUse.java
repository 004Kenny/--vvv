package com.example.identitycard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FirstTimeUse extends AppCompatActivity {

    Button sign_in;
    EditText user_name, emailAlias;;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String WEB = "https://ncair.nitda.gov.ng/wp-content/uploads/2022/08/";
    public static final String FORMAT = ".jpeg";
    public static  String userAlias;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_use);
        sign_in = findViewById(R.id.btn_sign_in);
        user_name = findViewById(R.id.name_edit_tv);
        emailAlias = findViewById(R.id.email_edit_tv);


        Objects.requireNonNull(getSupportActionBar()).hide();


        sign_in.setOnClickListener(v -> {
            Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show();
            Intent userHomepageIntent = new Intent(FirstTimeUse.this, user_homepage.class);
            userAlias = emailAlias.getText().toString();
            userHomepageIntent.putExtra("User Alias", userAlias);
            saveData(WEB+userAlias+FORMAT);
            startActivity(userHomepageIntent);

        });

        }

        public void saveData(String URL){
            SharedPreferences sp1 = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putString(TEXT, URL);
            editor.apply();
        }


}
