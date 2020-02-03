package com.shzhangji.invest.fundview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shzhangji.invest.R;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

public class RecordListFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fund_view_record_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<RecordItem> recordList = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            recordList.add(new RecordItem("买入", "2020-01-21", "257.55元"));
            recordList.add(new RecordItem("买入", "2020-01-14", "262.40元"));
            recordList.add(new RecordItem("买入", "2020-01-07", "265.34元"));
        }

        RecyclerView recyclerView = view.findViewById(R.id.fund_view_record_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecordAdapter(recordList));
    }

    @AllArgsConstructor
    public static class RecordItem {
        public String recordType;
        public String confirmDate;
        public String amount;
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        public TextView recordType;
        public TextView confirmDate;
        public TextView amount;

        public RecordViewHolder(View itemView) {
            super(itemView);
            recordType = itemView.findViewById(R.id.record_type);
            confirmDate = itemView.findViewById(R.id.confirm_date);
            amount = itemView.findViewById(R.id.amount);
        }
    }

    public static class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {
        private List<RecordItem> items;

        public RecordAdapter(List<RecordItem> items) {
            this.items = items;
        }

        @Override @NonNull
        public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_fund_view_record_item, parent, false);
            return new RecordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
            RecordItem item = items.get(position);
            holder.recordType.setText(item.recordType);
            holder.confirmDate.setText(item.confirmDate);
            holder.amount.setText(item.amount);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
