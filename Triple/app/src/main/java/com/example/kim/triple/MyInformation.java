package com.example.kim.triple;

import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;

import java.io.ByteArrayOutputStream;

/**
 * Created by 안성훈 on 2016-12-01.
 */

public class MyInformation extends SimpleFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PieChart mChart;
    private TextView[] cntOfBadge;
    private ImageView[] bdg;

    public MyInformation() {
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
    public static MyInformation newInstance(String param1, String param2) {
        MyInformation fragment = new MyInformation();
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

        View view = inflater.inflate(R.layout.activity_my_information, container, false);

        ImageView photo = (ImageView) view.findViewById(R.id.photo);
        photo.setImageResource(R.drawable.null_photo);

        generateBadge(view);

        mChart = (PieChart)view.findViewById(R.id.pieChart1);
        mChart.getDescription().setEnabled(false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        mChart.setCenterTextTypeface(tf);
        mChart.setCenterText(generateCenterText());
        mChart.setCenterTextSize(10f);
        mChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        mChart.setData(generatePieData());

        return view;
    }
    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("사용자\n성향");
        s.setSpan(new RelativeSizeSpan(2f), 0, 6, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), s.length() - 2, s.length(), 0);
        return s;
    }
    private void generateBadge(View view) {
        bdg = new ImageView[15];
        cntOfBadge = new TextView[15];

        bdg[0] = (ImageView) view.findViewById(R.id.bdg1);
        bdg[1] = (ImageView) view.findViewById(R.id.bdg2);
        bdg[2] = (ImageView) view.findViewById(R.id.bdg3);
        bdg[3] = (ImageView) view.findViewById(R.id.bdg4);
        bdg[4] = (ImageView) view.findViewById(R.id.bdg5);
        bdg[5] = (ImageView) view.findViewById(R.id.bdg6);
        bdg[6] = (ImageView) view.findViewById(R.id.bdg7);
        bdg[7] = (ImageView) view.findViewById(R.id.bdg8);
        bdg[8] = (ImageView) view.findViewById(R.id.bdg9);
        bdg[9] = (ImageView) view.findViewById(R.id.bdg10);
        bdg[10] = (ImageView) view.findViewById(R.id.bdg11);
        bdg[11] = (ImageView) view.findViewById(R.id.bdg12);
        bdg[12] = (ImageView) view.findViewById(R.id.bdg13);
        bdg[13] = (ImageView) view.findViewById(R.id.bdg14);
        bdg[14] = (ImageView) view.findViewById(R.id.bdg15);

        cntOfBadge[0] = (TextView) view.findViewById(R.id.cntOfBdg1);
        cntOfBadge[1] = (TextView) view.findViewById(R.id.cntOfBdg2);
        cntOfBadge[2] = (TextView) view.findViewById(R.id.cntOfBdg3);
        cntOfBadge[3] = (TextView) view.findViewById(R.id.cntOfBdg4);
        cntOfBadge[4] = (TextView) view.findViewById(R.id.cntOfBdg5);
        cntOfBadge[5] = (TextView) view.findViewById(R.id.cntOfBdg6);
        cntOfBadge[6] = (TextView) view.findViewById(R.id.cntOfBdg7);
        cntOfBadge[7] = (TextView) view.findViewById(R.id.cntOfBdg8);
        cntOfBadge[8] = (TextView) view.findViewById(R.id.cntOfBdg9);
        cntOfBadge[9] = (TextView) view.findViewById(R.id.cntOfBdg10);
        cntOfBadge[10] = (TextView) view.findViewById(R.id.cntOfBdg11);
        cntOfBadge[11] = (TextView) view.findViewById(R.id.cntOfBdg12);
        cntOfBadge[12] = (TextView) view.findViewById(R.id.cntOfBdg13);
        cntOfBadge[13] = (TextView) view.findViewById(R.id.cntOfBdg14);
        cntOfBadge[14] = (TextView) view.findViewById(R.id.cntOfBdg15);

        for (int i = 0; i < bdg.length; i++) {
            bdg[i].setImageResource(R.drawable.empty_badge);
        }

        bdg[0].setImageResource(R.drawable.marathoner);
        cntOfBadge[0].setText("2");

        bdg[8].setImageResource(R.drawable.fire);
        cntOfBadge[8].setText("1");

        bdg[11].setImageResource(R.drawable.walker);
        cntOfBadge[11].setText("4");

        bdg[14].setImageResource(R.drawable.paraglider);
        cntOfBadge[14].setText("2");
    }
}
