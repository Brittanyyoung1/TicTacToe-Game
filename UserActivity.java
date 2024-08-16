package edu.fvtc.tictactoegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    EditText editTextOwnerName;
    Button buttonSave;

    private static final String TAG = "UserActivity";


    private RestClient restClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        restClient = new RestClient(this);
        setContentView(R.layout.activity_user);

        editTextOwnerName = findViewById(R.id.editTextName);

        buttonSave = findViewById(R.id.buttonSave);


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editTextOwnerName.getText().toString().trim();
                if (!userName.isEmpty()) {
                    callApiWithUserName(userName);
                }
            }
        });

    }


    private void saveUserName(String userName) {
        SharedPreferences preferences = getSharedPreferences("UserNamePreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName", userName);
        editor.apply();

    }

    private void callApiWithUserName(String userName) {


        restClient.get("/" + userName + "/true", new RestClient.VolleyCallback<List<Game>>() {
            @Override
            public void onSuccess(List<Game> result) {
                if (result.size() == 0) {

                    Toast.makeText(UserActivity.this, "User Not Found", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(UserActivity.this, "User Found Successfully", Toast.LENGTH_LONG).show();
                    saveUserName(userName);
                    finish();
                }
            }

            @Override
            public void onError(IOException e) {

            }
        });

    }
}
