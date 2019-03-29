package app.davidnorton.chatter.ui.chatscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.models.Chat;

import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;


public class ChatActivity extends AppCompatActivity {

    public static final String EXTRAS_CHAT = "chat";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        setArguments();
        initView();
    }

    private void initView() {
        initToolbar();
    }

    private Chat mChat;
    private void setArguments() {
        Bundle bundle = getIntent().getExtras();
        mChat = bundle.getParcelable(EXTRAS_CHAT);
        if(mChat != null) {
            setTitle(mChat.getUser().getName());
        }
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView nameTV = (TextView) findViewById(R.id.nameTV);
        nameTV.setText(mChat.getUser().getName());


        CircleImageView userIV = (CircleImageView) findViewById(R.id.userIV);
        Picasso.with(this).load(mChat.getUser().getProfilePicUrl()).into(userIV);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Toolbar","Clicked");
            }
        });

    }
}