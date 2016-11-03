package com.example.kantai.bundledemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class BundleActivity extends AppCompatActivity {

    protected String bundleKeyArray[] = {"username", "phone", "mobile", "email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);

        TextView textView = (TextView) findViewById(R.id.activity_bundle_textView);
        Bundle bundle = this.getIntent().getExtras();
        for (String key : this.bundleKeyArray)
            if (!bundle.getString(key).equals(null))
                textView.setText(textView.getText() + key + " = " + bundle.getString(key) + "\n");
            else
                textView.setText(textView.getText() + key + " = " + getResources().getString(R.string.bundle_value_is_null) + "\n");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
