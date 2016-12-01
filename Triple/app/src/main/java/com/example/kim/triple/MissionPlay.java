package com.example.kim.triple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MissionPlay extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_play);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("place_image");
        Bitmap image;
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        ImageView detail_ImageView = (ImageView) findViewById(R.id.backdrop);
        detail_ImageView.setImageBitmap(image);
        // detail_ImageView.setImageResource(R.drawable.res1);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(intent.getStringExtra("place_name"));
        final TextView tag = (TextView) findViewById(R.id.detailText1);
        TextView phone = (TextView) findViewById(R.id.detailText2);
        TextView address = (TextView) findViewById(R.id.detailText3);
        tag.setText("미션대기");
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatActionBtn);
        final myToggle mytoggle = new myToggle();
        mytoggle.setToggle(true);
        final Intent serviceintent = new Intent(this, BackgroundSensorService.class);

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mytoggle.getToggle()) {
                    tag.setText("미션시작");
                    startService(serviceintent);
                    mytoggle.setToggle(false);
                } else {
                    tag.setText("미션대기");
                    mytoggle.setToggle(true);

                }

            }
        });

        /*


        tag.setText("Tag : "+intent.getStringExtra("place_tag"));
        address.setText("주소 : "+intent.getStringExtra("place_address"));
        phone.setText("전화번호 : "+"010 - 7272 - 3768");

        ArrayList<String> arrName = new ArrayList<String>();
        ArrayAdapter<String> adapName = new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.name,arrName);*/
        /*
        MissionViewAdapter adapter = new MissionViewAdapter();
        ListView listview = (ListView) findViewById(R.id.MissionList);
        listview.setAdapter(adapter);
        //버전 체크필요
        listview.setNestedScrollingEnabled(true);

        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place1),
                "대유랜드","[레포츠] 제주도서귀포시","제주특별자치도 서귀포시 상예로 381(상예동)");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place2),
                "퍼시픽랜드","[관광지] 퍼시픽랜드","제주특별자치도 서귀포시 중문관광로 154-17(색달동)" );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place3),
                "한라산 트레킹","[레포츠] 제주도제주시","제주특별자치도 제주시 1100로 2070-61(해안동)"  );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place4),
                "한림공원 국화축제2016","[관광지] 제주도 서귀포시","제주특별자치도 제주시 한림읍 한림로 300(한림읍)" );

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                MissionViewItem item = (MissionViewItem) parent.getItemAtPosition(position) ;

                String place_name = item.getTitle();
                String place_info1 = item.getDesc();
                String place_info2 = item.getDesc2();
                Bitmap place_bitmap = item.getIcon() ;

                Toast.makeText(getApplicationContext(),place_name+" 미션이 선택 되었습니다.", Toast.LENGTH_SHORT).show();
                // TODO : use item data.
            }
        }) ;
*/
    }
    private class myToggle{
        private boolean myflag;
        myToggle(){}
        public boolean getToggle(){
            return myflag;
        }
        public void setToggle(boolean arg){
            myflag = arg;
        }
    }

}
