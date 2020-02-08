package com.shzhangji.invest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.shzhangji.invest.fundview.FundViewActivity;

public class FundListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_list);
        getSupportActionBar().setTitle(R.string.fund_list_title);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.fund_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fund_list_action_add) {
            Intent intent = new Intent(this, FundEditActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFund(View view) {
        Intent intent = new Intent(this, FundViewActivity.class);
        startActivity(intent);
    }
}
