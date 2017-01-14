package jialu.cmu.edu.leonard.cohen.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lip.cmu.com.witnessjayz.R;

/**
 * MainActivity that shows the main info page.
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init TextView
        TextView tvMainPage = (TextView) findViewById(R.id.tv_main_page);
        TextView tvTwitter = (TextView) findViewById(R.id.tv_twitter);
        TextView tvFacebook = (TextView) findViewById(R.id.tv_facebook);


        tvMainPage.setMovementMethod(LinkMovementMethod.getInstance());
        tvTwitter.setMovementMethod(LinkMovementMethod.getInstance());
        tvFacebook.setMovementMethod(LinkMovementMethod.getInstance());


        Button buttonMusic = (Button) findViewById(R.id.btn_music);
        Button buttonVideo = (Button) findViewById(R.id.btn_video);
        Button buttonPics = (Button) findViewById(R.id.btn_pic);
        Button buttonMailList = (Button) findViewById(R.id.btn_email);

        buttonMusic.setOnClickListener(this);
        buttonVideo.setOnClickListener(this);
        buttonPics.setOnClickListener(this);
        buttonMailList.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.btn_music:
                intent = new Intent(this, MusicActivity.class);
                break;
            case R.id.btn_video:
                intent = new Intent(this, VideoActivity.class);
                break;
            case R.id.btn_pic:
                intent = new Intent(this, PictureActivity.class);
                break;
            case R.id.btn_email:
                intent = new Intent(this, MailListActivity.class);
                break;
            default:
                return;
        }

        startActivity(intent);
    }


}
