package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectedService extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_service);

        // Go back to User search list (temporarily going back to home)
        Button backButton = findViewById(R.id.btn_back_from_user_item_page);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

    }

    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("tab",2);
        startActivity(openScreen);
    }
}