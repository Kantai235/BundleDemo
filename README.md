# Android 利用 Bundle 在 Activity 換頁時傳值

首先我們拉一下兩個畫面，一個是 Main，一個是 Bundle 的目標地：

![Main 首頁](/images/main_view.png "Main 首頁")

![Bundle View](/images/bundle_view.png "Bundle View")

首先我們要在 onCreate 當中把 EditText 給全部定義好，以方便待會取值：

```Java
this.bootstrapEditTexts = new BootstrapEditText[]{
    (BootstrapEditText) findViewById(R.id.bootstrapEditTextUsername),
    (BootstrapEditText) findViewById(R.id.bootstrapEditTextPhone),
    (BootstrapEditText) findViewById(R.id.bootstrapEditTextMobile),
    (BootstrapEditText) findViewById(R.id.bootstrapEditTextEmail)
};
```

然後定義 Button 被點擊後的事件：

```Java
public void mainButtonClick(View view) {
    // Code ...
}
```

再來要想到，如果使用者亂輸入或者沒有輸入，這些例外錯誤的處理方式：

```Java
// 判斷是否為 Email 格式
private boolean isVaildEmailFormat(EditText _edittext) {
    if (android.util.Patterns.EMAIL_ADDRESS.matcher(_edittext.getText().toString()).matches()) {
        return true;
    } else {
        _edittext.setError(getResources().getString(R.string.edittext_error_message));
        return false;
    }
}

// 判斷是否為 Phone 格式
private boolean isVaildPhoneFormat(EditText _edittext) {
    if (Patterns.PHONE.matcher(_edittext.getText().toString()).matches()) {
        return true;
    } else {
        _edittext.setError(getResources().getString(R.string.edittext_error_message));
        return false;
    }
}
```

接著我們回到 mainButtonClick() 當中，如果這個按鈕被點擊了，那我們要開始對 EditText 的內容進行判斷：

```Java
boolean checkValue = true;
// 利用 foreach 的方式，去把每個 EditText 都檢查
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
            // 單純判斷是否為空
            if (bootstrapEditText.getText().toString().equals("")) {
                bootstrapEditText.setError(getResources().getString(R.string.edittext_error_message));
                checkValue = false;
            }
            break;
    }
}
```

如果剛剛的步驟都沒問題了，那我們開始把資料包在 Bundle 裡頭，並塞到 Intent(應用程式的意圖或使用者的意圖) 當中，並開始執行換頁的活動。

```Java
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
```

接著我們到 Bundle Activity 當中，我們要想到，如果這個活動一執行(onCreate)，那就要從 Bundle  把接收到的資訊給擷取出來，並顯示出來：

```Java
TextView textView = (TextView) findViewById(R.id.activity_bundle_textView);
Bundle bundle = this.getIntent().getExtras();
for (String key : this.bundleKeyArray)
    textView.setText(textView.getText() + key + " = " + bundle.getString(key) + "\n");
```

但這樣還是有個問題，如果用上面的方法，但 Bundle 傳過來的值是 null 或空的，那我們保險起見，最好還是再加個排除例外的防範措施：

```Java
TextView textView = (TextView) findViewById(R.id.activity_bundle_textView);
Bundle bundle = this.getIntent().getExtras();
for (String key : this.bundleKeyArray)
    if (!bundle.getString(key).equals(null))
        textView.setText(textView.getText() + key + " = " + bundle.getString(key) + "\n");
    else
        textView.setText(textView.getText() + key + " = " + getResources().getString(R.string.bundle_value_is_null) + "\n");
```