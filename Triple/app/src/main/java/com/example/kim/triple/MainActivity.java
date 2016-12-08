package com.example.kim.triple;

import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.kim.triple.data.dao.ClearDao;
import com.example.kim.triple.data.dao.MissionCartDao;
import com.example.kim.triple.data.dao.MissionDao;
import com.example.kim.triple.data.dao.TripLocationDao;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.MissionCart;
import com.example.kim.triple.data.model.TripLocation;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public int[] tabIcons = {
            R.drawable.search,
            R.drawable.mission,
            R.drawable.rank,
            R.drawable.user};
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);


        tabLayout.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#1976D2"));
        tabLayout.setupWithViewPager(mViewPager);



         setupTabIcons();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ClearDao  cleardao = new ClearDao(this);
        cleardao.clear();
        initTriptable();
        initMission();

    }


    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Bundle args = null;
            switch(position){
                case 0:
                    //////////넣을 프레그먼트
                    fragment = new Select_Place();
                    args = new Bundle();
                    break;
                case 1:
                    fragment = new MissionCartList();
                    args = new Bundle();
                    break;
                case 2:
                    fragment = new RankView();
                    args = new Bundle();
                    break;
                case 3:
                    fragment = new MyInformation();
                    args = new Bundle();
                    break;

            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "관광지 선택";
                case 1:
                    return "수행 미션";
                case 2:
                    return "랭킹 페이지";
                case 3:
                    return "내 정보";
            }
            return null;
        }

        @Override
        public int getItemPosition(Object item) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mSectionsPagerAdapter.notifyDataSetChanged();
        setupTabIcons();
    }



    private void initTriptable(){

        TripLocationDao tripLocationDao = new TripLocationDao(this);
        TripLocation tripLocation = new TripLocation();
        tripLocation.setAddress("제주특별자치도 서귀포시 상예로 381(상예동)")
                .setName("대유랜드").setPhoneNumber("010-0000-0000").setTag("[레포츠] 제주도서귀포시").setPicture("jeju_place1");
        tripLocationDao.insert(tripLocation);
        tripLocation.setAddress("제주특별자치도 서귀포시 중문관광로 154-17(색달동)")
                .setName("퍼시픽랜드").setPhoneNumber("010-0000-0000").setTag("[관광지] 퍼시픽랜드").setPicture("jeju_place2");
        tripLocationDao.insert(tripLocation);
        tripLocation.setAddress("제주특별자치도 제주시 1100로 2070-61(해안동)")
                .setName("한라산 트레킹").setPhoneNumber("010-0000-0000").setTag("[레포츠] 제주도제주시").setPicture("jeju_place3");
        tripLocationDao.insert(tripLocation);
        tripLocation.setAddress("제주특별자치도 제주시 한림읍 한림로 300(한림읍)")
                .setName("한림공원 국화축제2016").setPhoneNumber("010-0000-0000").setTag("[관광지] 제주도 서귀포시").setPicture("jeju_place4");
        tripLocationDao.insert(tripLocation);

    }

    private void initMission(){

        MissionDao missionDao = new MissionDao(this);
        Mission mission = new Mission();
        mission.setName("마라톤미션").setExplan("1. 제한 시간 내에 미션시작 장소로 이동한다.\n\n" +
                "2. 미션 시작 시 목표지점으로 이동한다.\n\n" +
                "3. 초속 4M 이상의 속도를 유지한다.").setImageUrl("maraton")
                .setTripLocationId(1)
                .setClassification("달리기")
                .setEndTime(7)
                .setLatitude("123")
                .setLongitude("32");
        missionDao.insert(mission);
        mission.setName("페러글라이딩미션").setExplan("1. 제한 시간 내에 미션시작 장소로 이동한다.\n\n" +
                "2. 미션시작 장소 반경 100M 안에서 상공 20M 지점으로 이동한다.").setImageUrl("fly")
                .setTripLocationId(1)
                .setClassification("비행")
                .setEndTime(7)
                .setLatitude("123")
                .setLongitude("32");
        missionDao.insert(mission);
        mission.setName("고온미션").setExplan("1. 제한 시간 내에 미션시작 장소로 이동한다.\n\n" +
                "2. 미션시작 시 제한 시간 동안 50도를 유지한다.").setImageUrl("hot")
                .setTripLocationId(1)
                .setClassification("열정")
                .setEndTime(7)
                .setLatitude("123")
                .setLongitude("32");
        missionDao.insert(mission);
        mission.setName("만보미션").setExplan("1. 제한 시간 내에 미션시작 장소로 이동한다.\n\n" +
                "2. 화이팅").setImageUrl("walk")
                .setTripLocationId(2)
                .setClassification("걷기")
                .setEndTime(7)
                .setLatitude("123")
                .setLongitude("32");
        missionDao.insert(mission);


    }
}
