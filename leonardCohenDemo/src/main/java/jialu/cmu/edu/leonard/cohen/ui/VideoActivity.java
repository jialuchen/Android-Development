package jialu.cmu.edu.leonard.cohen.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import jialu.cmu.edu.leonard.cohen.util.FragmentCallback;
import lip.cmu.com.witnessjayz.R;

/**
 * VideoActivity that plays video.
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class VideoActivity extends Activity implements FragmentCallback {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, "fragment");
        fragmentTransaction.commit();
        videoView = (VideoView) this.findViewById(R.id.videoView);

    }

    public void playVideo(int id) {
        MediaController mc = new MediaController(this);

        if (id == R.id.fragment_right_button) {
            Toast.makeText(VideoActivity.this, R.string.crazy_in_love, Toast.LENGTH_SHORT).show();
            videoView.setMediaController(mc);
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video_demo1);
            videoView.requestFocus();
            videoView.start();
        } else {
            Toast.makeText(VideoActivity.this, R.string.empire_state, Toast.LENGTH_SHORT).show();
            videoView.setMediaController(mc);
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video_demo2);
            videoView.requestFocus();
            videoView.start();
        }

    }

    @Override
    public void onFragmentClicked(View view) {
        playVideo(view.getId());
    }
}
