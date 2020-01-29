package com.shzhangji.invest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

public class FundEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_edit);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.fragment_fund_edit_search);

        EditText searchInput = findViewById(R.id.fund_edit_input_search);
        searchInput.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch();
                return true;
            }
            return false;
        });

        List<FundItem> fundList = new ArrayList<>();
        fundList.add(new FundItem("050025", "博时标普500ETF联接"));
        fundList.add(new FundItem("000834", "大成纳斯达克100指数"));
        fundList.add(new FundItem("090010", "大成中证红利指数A"));

        RecyclerView recyclerView = findViewById(R.id.fund_edit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FundAdapter(fundList));
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.fund_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.fund_edit_action_search) {
            doSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doSearch() {
        EditText searchInput = findViewById(R.id.fund_edit_input_search);
        new MessageBox(searchInput.getText().toString()).show(getSupportFragmentManager(), "search");
    }

    public static class FundViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView code;

        public FundViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fund_edit_item_title);
            code = itemView.findViewById(R.id.fund_edit_item_code);
        }
    }

    public static class FundAdapter extends RecyclerView.Adapter<FundViewHolder> {
        private List<FundItem> fundList;

        public FundAdapter(List<FundItem> fundList) {
            this.fundList = fundList;
        }

        @Override @NonNull
        public FundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_fund_edit_item, parent, false);
            return new FundViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FundViewHolder holder, int position) {
            FundItem item = fundList.get(position);
            holder.title.setText(item.title);
            holder.code.setText(item.code);
        }

        @Override
        public int getItemCount() {
            return fundList.size();
        }
    }

    @AllArgsConstructor
    public static class FundItem {
        public String code;
        public String title;
    }
}
