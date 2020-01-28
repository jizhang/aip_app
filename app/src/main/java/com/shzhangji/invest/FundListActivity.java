package com.shzhangji.invest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FundListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_list);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.fund_list, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fund_list_action_add:
                new MessageBox("add").show(getSupportFragmentManager(), "messageBox");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class MessageBox extends DialogFragment {
        private String message;

        public MessageBox(String message) {
            this.message = message;
        }

        @Override @NonNull
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setPositiveButton("OK", (dialog, id) -> {})
                    .create();
        }
    }
}
