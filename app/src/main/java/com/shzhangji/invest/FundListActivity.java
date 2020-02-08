package com.shzhangji.invest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

public class FundListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_list);

        getSupportActionBar().setTitle(R.string.fund_list_title);

        List<FundItem> fundList = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            fundList.add(new FundItem("博时标普500ETF联接", "8,601.27", "+702.05", "+8.89%"));
            fundList.add(new FundItem("大成纳斯达克100指数", "11,130.81", "+1,457.15", "+15.06%"));
            fundList.add(new FundItem("大成中证红利指数", "11,536.57", "-504.86", "-4.19%"));
        }

        RecyclerView recyclerView = findViewById(R.id.fund_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FundAdapter(fundList));
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

    @AllArgsConstructor
    public static class FundItem {
        public String title;
        public String asset;
        public String profit;
        public String rate;
    }

    public static class FundViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView asset;
        public TextView profit;
        public TextView rate;

        public FundViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            asset = itemView.findViewById(R.id.asset);
            profit = itemView.findViewById(R.id.profit);
            rate = itemView.findViewById(R.id.rate);
        }
    }

    public static class FundAdapter extends RecyclerView.Adapter<FundViewHolder> {
        private List<FundItem> items;

        public FundAdapter(List<FundItem> items) {
            this.items = items;
        }

        @Override @NonNull
        public FundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_fund_list_item, parent, false);
            return new FundViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FundViewHolder holder, int position) {
            FundItem item = items.get(position);
            holder.title.setText(item.title);
            holder.asset.setText(item.asset);
            holder.profit.setText(item.profit);
            holder.rate.setText(item.rate);

            Resources resources = holder.itemView.getResources();
            int textUp = resources.getColor(R.color.textUp, null);
            int textDown = resources.getColor(R.color.textDown, null);
            int textColor = item.profit.startsWith("-") ? textDown : textUp;
            holder.profit.setTextColor(textColor);
            holder.rate.setTextColor(textColor);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
