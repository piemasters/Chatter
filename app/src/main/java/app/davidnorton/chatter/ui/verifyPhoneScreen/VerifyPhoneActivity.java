package app.davidnorton.chatter.ui.verifyPhoneScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.createUserScreen.CreateUserActivity;


public class VerifyPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mNextButton;
    private EditText countryCodeET;
    private EditText phoneNumberET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mNextButton = (Button) findViewById(R.id.nextBtn);
        countryCodeET = (EditText) findViewById(R.id.countryCodeET);
        phoneNumberET = (EditText) findViewById(R.id.phoneNumberET);

        phoneNumberET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    mNextButton.performClick();
                }
                return false;
            }
        });

        findViewById(R.id.nextBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String countryCode = countryCodeET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();
        if (!TextUtils.isEmpty(countryCode) & !TextUtils.isEmpty(phoneNumber)) {
            Intent intent = new Intent(this, CreateUserActivity.class);
            intent.putExtra("PHONE_NUMBER", countryCode + phoneNumber);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Phone number can not be blank", Toast.LENGTH_LONG).show();
        }

    }

}


