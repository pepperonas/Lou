/*
 * Copyright (c) 2017 Martin Pfeffer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.celox.app.lou;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import io.celox.app.lou.custom.BottomBarAdapter;
import io.celox.app.lou.fragments.Fragment1;
import io.celox.app.lou.fragments.Fragment2;
import io.celox.app.lou.fragments.Fragment3;
import io.celox.app.lou.fragments.Fragment4;
import io.celox.app.lou.fragments.Fragment5;

/**
 * The type Main activity.
 *
 * @author Martin Pfeffer
 *         <a href="mailto:martin.pfeffer@celox.io">martin.pfeffer@celox.io</a>
 * @see <a href="https://celox.io">https://celox.io</a>
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnBottomNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_nav_profile:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.bottom_nav_search:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.bottom_nav_play:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.bottom_nav_favorite:
                    mViewPager.setCurrentItem(3);
                    return true;
                case R.id.bottom_nav_gallery:
                    mViewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };

    private NavigationView.OnNavigationItemSelectedListener
            mOnNavigationViewItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setChecked(true);
            mDrawerLayout.closeDrawers();

            switch (item.getItemId()) {
                case R.id.drawer_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.drawer_favorite:
                    mViewPager.setCurrentItem(3);
                    return true;
                case R.id.drawer_settings:
                    // TODO: create preference fragment
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnBottomNavigationItemSelectedListener);
        final BottomBarAdapter bottomBarAdapter = new BottomBarAdapter(getSupportFragmentManager());
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();
        Fragment5 fragment5 = new Fragment5();
        bottomBarAdapter.addFragments(fragment1);
        bottomBarAdapter.addFragments(fragment2);
        bottomBarAdapter.addFragments(fragment3);
        bottomBarAdapter.addFragments(fragment4);
        bottomBarAdapter.addFragments(fragment5);

        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(bottomBarAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // the guidelines say that we don't want to implement to same navigation twice, so this code should be replaced.
            // See it as a showcase how the selection of the bottom navigation can be kept synchronized.
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_profile);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_search);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_play);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_favorite);
                        break;
                    case 4:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_gallery);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close
        );
        mDrawerLayout.addDrawerListener(drawerToggle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        } else {
            Log.w(TAG, "onCreate: Missing ActionBar...");
        }

        drawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(mOnNavigationViewItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

}
