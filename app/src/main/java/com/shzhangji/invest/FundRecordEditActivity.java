package com.shzhangji.invest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FundRecordEditActivity extends AppCompatActivity {
    public static final int SELECT_FUND_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_record_edit);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_FUND_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            EditText input = findViewById(R.id.fund_record_edit_input_fund_code);
            input.setText(getString(R.string.fund_record_edit_template_fund_code,
                    data.getStringExtra("code"),
                    data.getStringExtra("title")));
        }
    }

    public void selectFund(View view) {
        Intent intent = new Intent(this, FundSearchActivity.class);
        startActivityForResult(intent, SELECT_FUND_REQUEST);
    }

    public void showConfirmDatePicker(View view) {
        EditText editText = findViewById(R.id.fund_record_edit_input_confirm_date);
        String dateString = editText.getText().toString();
        Calendar current = Calendar.getInstance();
        if (!TextUtils.isEmpty(dateString)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                current.setTime(date);
            } catch (Exception e) {
                // noop
            }
        }

        DialogFragment newFragment = new DatePickerFragment(current, calendar -> {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            editText.setText(date);
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private Calendar calendar;
        private Callback callback;

        public DatePickerFragment(Calendar calendar, Callback callback) {
            this.calendar = calendar;
            this.callback = callback;
        }

        @Override @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, dayOfMonth);
            callback.call(c);
        }

        public interface Callback {
            void call(Calendar calendar);
        }
    }
}
