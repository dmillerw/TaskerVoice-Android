package me.dmillerw.taskervoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import me.dmillerw.taskervoice.utils.Constants;

public class EditActivity extends AppCompatActivity {

    private EditText mEditText;
    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEditText = (EditText) findViewById(R.id.message);
        mToggleButton = (ToggleButton) findViewById(R.id.exact);
        mToggleButton.setTextOff("Wild");
        mToggleButton.setTextOn("Exact");
        mToggleButton.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        if (!mToggleButton.isChecked() && mEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Must enter target message", Toast.LENGTH_LONG).show();
            return;
        }
        finish();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();

        Bundle result = new Bundle();

        result.putString(Constants.MSG, mEditText.getText().toString().trim());
        result.putBoolean(Constants.EXACT, mToggleButton.isChecked());

        intent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, result);
        intent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, "On " + (mToggleButton.isChecked() ? "Exact" : "Similar") + " Message: " + mEditText.getText().toString());

        setResult(RESULT_OK, intent);

        super.finish();
    }
}
