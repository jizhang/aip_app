package com.shzhangji.invest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.shzhangji.invest.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void editFundRecord(View view) {
        Intent intent = new Intent(this, FundRecordEditActivity.class);
        startActivity(intent);
    }

    public void listFunds(View view) {
        Intent intent = new Intent(this, FundListActivity.class);
        startActivity(intent);
    }
}
