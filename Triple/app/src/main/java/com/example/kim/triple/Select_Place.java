package com.example.kim.triple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;

public class Select_Place extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listview;

    public Select_Place() {
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
    public static Select_Place newInstance(String param1, String param2) {
        Select_Place fragment = new Select_Place();
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

        View view = inflater.inflate(R.layout.fragment_select__place,null);


       /* ArrayList<String> arrName = new ArrayList<String>();
        ArrayAdapter<String> adapName = new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.name,arrName);*/
        ListViewAdapter adapter = new ListViewAdapter();
        ListView listview = (ListView) view.findViewById(R.id.listView2);
        listview.setAdapter(adapter);

        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place1),
                "대유랜드","[레포츠] 제주도서귀포시","제주특별자치도 서귀포시 상예로 381(상예동)");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place2),
                "퍼시픽랜드","[관광지] 퍼시픽랜드","제주특별자치도 서귀포시 중문관광로 154-17(색달동)" );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place3),
                "한라산 트레킹","[레포츠] 제주도제주시","제주특별자치도 제주시 1100로 2070-61(해안동)"  );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place4),
                "한림공원 국화축제2016","[관광지] 제주도 서귀포시","제주특별자치도 제주시 한림읍 한림로 300(한림읍)" );
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place1),
                "대유랜드","[레포츠] 제주도서귀포시","제주특별자치도 서귀포시 상예로 381(상예동)");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place2),
                "퍼시픽랜드","[관광지] 퍼시픽랜드","제주특별자치도 서귀포시 중문관광로 154-17(색달동)" );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place3),
                "한라산 트레킹","[레포츠] 제주도제주시","제주특별자치도 제주시 1100로 2070-61(해안동)"  );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place4),
                "한림공원 국화축제2016","[관광지] 제주도 서귀포시","제주특별자치도 제주시 한림읍 한림로 300(한림읍)" );
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
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String place_name = item.getTitle();
                String place_info1 = item.getDesc();
                String place_info2 = item.getDesc2();
                Bitmap place_bitmap = item.getIcon() ;

                Intent intent = new Intent(getContext(), PlaceMissionActivity.class);

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