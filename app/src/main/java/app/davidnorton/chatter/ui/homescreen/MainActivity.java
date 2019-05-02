package app.davidnorton.chatter.ui.homescreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.common.adapters.ViewPagerTabAdapter;
import app.davidnorton.chatter.ui.userListScreen.UserListActivity;
import static app.davidnorton.chatter.ui.chatscreen.ChatActivity.tag;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        initToolbar();
        initNewMessageFloatingButton();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void initNewMessageFloatingButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    private ViewPager mViewPager;
    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        List<String> tabNames = new ArrayList<String>();
        tabNames.add("Chats");
        tabNames.add("Status");
        tabNames.add("Calls");
        ViewPagerTabAdapter viewPagerTabAdapter = new ViewPagerTabAdapter(getSupportFragmentManager(), getFragments(), tabNames);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(viewPagerTabAdapter);
    }


    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
    }


    private List<Fragment> mFragments;
    private List<Fragment> getFragments() {

        mFragments = new ArrayList<Fragment>();
        mFragments.add(ChatsFragment.newInstance(""));
        mFragments.add(StatusFragment.newInstance());
        mFragments.add(CallsFragment.newInstance());

        return mFragments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, " onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, " onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, " onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, " onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, " onDestroy()");

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

}
