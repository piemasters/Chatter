package app.davidnorton.chatter.ui.welcomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.verifyPhoneScreen.VerifyPhoneActivity;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.agreeNContinueTVBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agreeNContinueTVBtn:
                Intent intent = new Intent(this, VerifyPhoneActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}