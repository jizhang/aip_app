package com.shzhangji.invest.fundview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.shzhangji.invest.R;

public class ProfitChartFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fund_view_profit_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.fund_view_button_periods);
        toggleGroup.addOnButtonCheckedListener(this::togglePeriod);
        toggleGroup.check(R.id.fund_view_button_period_3_months);
    }

    public void togglePeriod(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (!isChecked) {
            return;
        }

        int period;
        switch (checkedId) {
            case R.id.fund_view_button_period_3_months:
                period = R.string.fund_view_button_period_3_months;
                break;
            case R.id.fund_view_button_period_6_months:
                period = R.string.fund_view_button_period_6_months;
                break;
            case R.id.fund_view_button_period_1_year:
                period = R.string.fund_view_button_period_1_year;
                break;
            case R.id.fund_view_button_period_3_years:
                period = R.string.fund_view_button_period_3_years;
                break;
            default:
                throw new IllegalArgumentException("invalid id " + checkedId);
        }

        TextView holder = getView().findViewById(R.id.fund_view_holder_profit_chart);
        holder.setText(getString(R.string.fund_view_text_holder,
                getString(period),
                getString(R.string.fund_view_text_profit_chart)));
    }
}
