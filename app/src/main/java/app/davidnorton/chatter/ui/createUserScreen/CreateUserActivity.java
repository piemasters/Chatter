package app.davidnorton.chatter.ui.createUserScreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.davidnorton.chatter.ui.models.User;
import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.Util;
import app.davidnorton.chatter.ui.homescreen.MainActivity;


public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String tag = CreateUserActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        findViewById(R.id.nextBtn).setOnClickListener(this);
        initViews();
    }

    private Button mNextButton;
    private EditText mUsernameET;
    private EditText mExistingUserET, userProfilePicET;

    private void initViews() {
        mNextButton = (Button) findViewById(R.id.nextBtn);
        mNextButton.setOnClickListener(this);
        mUsernameET = (EditText) findViewById(R.id.userNameET);
        mUsernameET = (EditText) findViewById(R.id.userNameET);
        userProfilePicET = (EditText) findViewById(R.id.userProfilePicET);
        mExistingUserET = (EditText) findViewById(R.id.existingUserET);

        userProfilePicET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    mNextButton.performClick();
                }
                return false;
            }
        });

        mExistingUserET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    mNextButton.performClick();
                }
                return false;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                onNextButtonClicked();
                break;
        }
    }

    String mUserName;
    String userProfilePic;

    private void onNextButtonClicked() {

        if(mExistingUserET.getText().toString().length() > 0) {
            loginExistingUser();
            return;
        }

        mUserName = mUsernameET.getText().toString();
        userProfilePic = userProfilePicET.getText().toString();
        if (!TextUtils.isEmpty(mUserName) && !TextUtils.isEmpty(userProfilePic)) {
            createUser();
        } else {
            Toast.makeText(this, "Username & profile picture cannot be blank", Toast.LENGTH_LONG).show();
        }
    }

    private void loginExistingUser() {
        String user = mExistingUserET.getText().toString() + "@gmail.com";
        //String password = mPasswordET.getText().toString();
        String password = getIntent().getStringExtra("PHONE_NUMBER");

        FirebaseAuth.getInstance().signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(tag, "signInUser:oncomplete:" + task.isSuccessful());
                hideProgressDialog();

                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                    Util.updateToken();
                } else {
                    try {
                        Exception e = task.getException();
                        e.printStackTrace();
                        Log.d("exception:", e.getMessage());
                        Toast.makeText(CreateUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("exception:", e.getMessage());
                    }
                }
            }
        });
    }



    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private void createUser() {
        mAuth = FirebaseAuth.getInstance();
        String email = mUserName + "@gmail.com";
        String password = getIntent().getStringExtra("PHONE_NUMBER");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(tag, "createUser:oncomplete:" + task.isSuccessful());

                        hideProgressDialog();


                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            Util.updateToken();
                        } else {
                            try {
                                Exception e = task.getException();
                                e.printStackTrace();
                                Log.d("exception:", e.getMessage());
                                Toast.makeText(CreateUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("exception:", e.getMessage());
                            }
                        }
                    }
                });

    }

    private void onAuthSuccess(FirebaseUser firebaseUser) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User();
        user.setName(usernameFromEmail(firebaseUser.getEmail()));
        user.setUid(firebaseUser.getUid());
        user.setProfilePicUrl(userProfilePic);
        mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


}