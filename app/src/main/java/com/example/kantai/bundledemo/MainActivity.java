package com.example.kantai.bundledemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;

public class MainActivity extends AppCompatActivity {

    protected BootstrapEditText bootstrapEditTexts[];
    protected String bundleKeyArray[] = {"username", "phone", "mobile", "email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bootstrapEditTexts = new BootstrapEditText[]{
                (BootstrapEditText) findViewById(R.id.bootstrapEditTextUsername),
                (BootstrapEditText) findViewById(R.id.bootstrapEditTextPhone),
                (BootstrapEditText) findViewById(R.id.bootstrapEditTextMobile),
                (BootstrapEditText) findViewById(R.id.bootstrapEditTextEmail)
        };
    }

    public void mainButtonClick(View view) {
        boolean checkValue = true;
        for (BootstrapEditText bootstrapEditText : this.bootstrapEditTexts) {
            switch (bootstrapEditText.getId()) {
                case R.id.bootstrapEditTextPhone:
                    if (!this.isVaildPhoneFormat(bootstrapEditText))
                        checkValue = false;
                    break;
                case R.id.bootstrapEditTextMobile:
                    if (!this.isVaildPhoneFormat(bootstrapEditText))
                        checkValue = false;
                    break;
                case R.id.bootstrapEditTextEmail:
                    if (!this.isVaildEmailFormat(bootstrapEditText))
                        checkValue = false;
                    break;
                default:
                    if (bootstrapEditText.getText().toString().equals("")) {
                        bootstrapEditText.setError(getResources().getString(R.string.edittext_error_message));
                        checkValue = false;
                    }
                    break;
            }
        }

        if (checkValue) {
            Intent intent = new Intent();
            intent.setClass(this, BundleActivity.class);
            Bundle bundle = new Bundle();
            for (int i = 0; i < this.bootstrapEditTexts.length; i++)
                bundle.putString(this.bundleKeyArray[i], bootstrapEditTexts[i].getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_value_is_null), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isVaildEmailFormat(EditText _edittext) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(_edittext.getText().toString()).matches()) {
            return true;
        } else {
            _edittext.setError(getResources().getString(R.string.edittext_error_message));
            return false;
        }
    }

    private boolean isVaildPhoneFormat(EditText _edittext) {
        if (Patterns.PHONE.matcher(_edittext.getText().toString()).matches()) {
            return true;
        } else {
            _edittext.setError(getResources().getString(R.string.edittext_error_message));
            return false;
        }
    }

}
