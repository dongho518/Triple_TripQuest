package com.example.kim.triple;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class BedgePopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bedge_popup);
        Intent intent = getIntent();
        int result = intent.getIntExtra("result",0);
        int missionId = intent.getIntExtra("missionId",0);
        String name = intent.getStringExtra("missionName");
        String name2 = intent.getStringExtra("missionBedge");
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView4.setText(name);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if(result==1){
            textView5.setText(name2 +"뱃지를 획득하였습니다!");


            if(missionId == 1){ //마라톤
                imageView.setImageResource(R.drawable.marathoner);
            }else if(missionId==2){ //페러글라이딩
                imageView.setImageResource(R.drawable.paraglider);
            }else if(missionId==3){ //온도
                imageView.setImageResource(R.drawable.heatingroom);
            }else if(missionId==4){ //만보기
                imageView.setImageResource(R.drawable.walker);
            }
        }else{
            textView5.setText(name2 +"뱃지 획득에 실패하였습니다!");
            imageView.setImageResource(R.drawable.fail);
        }



    }

}
