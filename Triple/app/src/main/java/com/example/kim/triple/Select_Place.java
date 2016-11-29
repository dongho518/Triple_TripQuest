package com.example.kim.triple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class Select_Place extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listview;
    String Res_name;

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

        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res1),
                "족발보쌈있는집", ContextCompat.getDrawable(getActivity(),R.drawable.res1_1));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res2),
                "머꼬또떡볶이-신암점", ContextCompat.getDrawable(getActivity(),R.drawable.res2_2));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res3),
                "파스토보이-경대점",  ContextCompat.getDrawable(getActivity(),R.drawable.res3_3));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res4),
                "깻잎두마리칩킨-산격점", ContextCompat.getDrawable(getActivity(),R.drawable.res4_4));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res1),
                "족발보쌈있는집", ContextCompat.getDrawable(getActivity(),R.drawable.res1_1));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res2),
                "머꼬또떡볶이-신암점",ContextCompat.getDrawable(getActivity(),R.drawable.res2_2));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res3),
                "파스토보이-경대점", ContextCompat.getDrawable(getActivity(),R.drawable.res3_3));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res4),
                "깻잎두마리칩킨-산격점", ContextCompat.getDrawable(getActivity(),R.drawable.res4_4));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res1),
                "족발보쌈있는집", ContextCompat.getDrawable(getActivity(),R.drawable.res1_1));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res2),
                "머꼬또떡볶이-신암점",ContextCompat.getDrawable(getActivity(),R.drawable.res2_2));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res3),
                "파스토보이-경대점", ContextCompat.getDrawable(getActivity(),R.drawable.res3_3));
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.res4),
                "깻잎두마리칩킨-산격점", ContextCompat.getDrawable(getActivity(),R.drawable.res4_4));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                Res_name = item.getTitle() ;

                //Drawable iconDrawable = item.getIcon() ;

                /*
                Intent intent = new Intent(getContext(), Res_Information.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                intent.putExtra("ResName", Res_name);

                //intent.putParcelableArrayListExtra("ResMenu",Res_menu);

                startActivityForResult(intent, 1030);

                */
                // TODO : use item data.
            }
        }) ;

        return view;
    }
}