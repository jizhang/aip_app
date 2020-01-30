package com.shzhangji.invest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FundViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_view);

        getSupportActionBar().setTitle("博时标普500ETF联接");
        getSupportActionBar().setSubtitle("050025");
    }
}
