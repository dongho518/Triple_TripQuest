package com.example.kim.triple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kim.triple.data.dao.TripLocationDao;
import com.example.kim.triple.data.model.TripLocation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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

        TripLocationDao tripLocationDao = new TripLocationDao(this.getContext());
        List<TripLocation> tripLocationList = tripLocationDao.selectAll();
        //Log.i("testse",""+tripLocationList.size());
      //  Log.i("test",""+tripLocationList.size());

        for(TripLocation elem : tripLocationList){
            Log.i("id",""+elem.getId());
            adapter.addItem(elem.getId(),BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(elem.getPicture(), "drawable","com.example.kim.triple")),
                    elem.getName(), elem.getTag(),elem.getAddress());
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;
                /*
                String place_name = item.getTitle();
                String place_info1 = item.getDesc();
                String place_info2 = item.getDesc2();
                Bitmap place_bitmap = item.getIcon() ;
                */
                int tripId = item.getId();
                Intent intent = new Intent(getContext(), PlaceMissionActivity.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                intent.putExtra("place_id",tripId);
                /*
                intent.putExtra("place_name", place_name);
                intent.putExtra("place_tag",place_info1);
                intent.putExtra("place_address",place_info2);

                /////이미지 넘기기
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                place_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("place_image",byteArray);
                */
                startActivity(intent);

                //intent.putParcelableArrayListExtra("ResMenu",Res_menu);

                // TODO : use item data.
            }
        }) ;

        return view;
    }
}