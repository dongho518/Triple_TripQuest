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

import com.example.kim.triple.data.dao.MissionCartDao;
import com.example.kim.triple.data.dao.MissionDao;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.MissionCart;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class MissionCartList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int UserId = 1010;

    ListView listview;

    public MissionCartList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MissionCartList.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionCartList newInstance(String param1, String param2) {
        MissionCartList fragment = new MissionCartList();
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
        View view = inflater.inflate(R.layout.fragment_mission_cart_list,null);


        MissionCartDao missionCartDao = new MissionCartDao(this.getContext());
        List <MissionCart> missionlist = missionCartDao.selectFromUserId(1010);
        MissionDao missionDao = new MissionDao(this.getContext());

        MissionCartAdapter adapter = new MissionCartAdapter();
        ListView listview = (ListView) view.findViewById(R.id.missionCart_imageview);
        listview.setAdapter(adapter);
        //버전 체크필요
        listview.setNestedScrollingEnabled(true);

        Log.i("missionCartlist",""+missionlist.size());
        for(MissionCart elem: missionlist){
            Log.i("missionCart",""+elem.getMissionId());
            int missionId = elem.getMissionId();
            Mission mission = missionDao.selectFromId(missionId);
            adapter.addItem(mission.getId(), BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mission.getImageUrl(), "drawable","com.example.kim.triple")),
                    mission.getName(), mission.getExplan(), mission.getExplan());
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                int missionId = item.getId();

                Intent intent = new Intent(getContext(), MissionPlay.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                intent.putExtra("mission_id",missionId);
                // Toast.makeText(getApplicationContext(),place_name+" 미션이 선택 되었습니다.", Toast.LENGTH_SHORT).show();
                // setupLocation();
                // TODO : use item data.
                startActivity(intent);
                //intent.putParcelableArrayListExtra("ResMenu",Res_menu);

                // TODO : use item data.
            }
        }) ;

        return view;
    }

}
