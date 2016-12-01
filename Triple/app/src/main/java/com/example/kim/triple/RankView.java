package com.example.kim.triple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;


public class RankView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FRAGMENT1.
     */
    // TODO: Rename and change types and number of parameters
    public static RankView newInstance(String param1, String param2) {
        RankView fragment = new RankView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank_view,null);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);
        ctl.setTitle("미션지: 퍼시픽랜드");

        ImageView cur_missionView = (ImageView)view.findViewById(R.id.backdrop);
        cur_missionView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place3));

       /* ArrayList<String> arrName = new ArrayList<String>();
        ArrayAdapter<String> adapName = new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.name,arrName);*/
        RankViewAdapter adapter = new RankViewAdapter();
        ListView listview = (ListView) view.findViewById(R.id.RankView);
        listview.setAdapter(adapter);

        //버전 체크필요
        listview.setNestedScrollingEnabled(true);


        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "01","오시훈","");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "02","김현욱","" );
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.porfil1),
                "03","김동호","");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "04","안성훈","" );
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "05","강승규","");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "06","김동욱","" );
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "07","이호승","");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "08","김성현","" );
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "09","김미수","");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "10","이환일","" );
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "11","이홍주","");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.null_photo),
                "12","이효인","" );

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                RankViewItem item = (RankViewItem) parent.getItemAtPosition(position) ;

                String place_name = item.getTitle();
                String place_info1 = item.getDesc();
                String place_info2 = item.getDesc2();
                Bitmap place_bitmap = item.getIcon() ;

                Intent intent = new Intent(getContext(), Place_MissionInfo.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                intent.putExtra("place_name", place_name);
                intent.putExtra("place_tag",place_info1);
                intent.putExtra("place_address",place_info2);
                /////이미지 넘기기
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                place_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("place_image",byteArray);
                startActivity(intent);

                //intent.putParcelableArrayListExtra("ResMenu",Res_menu);

                // TODO : use item data.
            }
        }) ;

        return view;
    }
}