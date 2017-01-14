package jialu.cmu.edu.leonard.cohen.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import lip.cmu.com.witnessjayz.R;

/**
 * PictureActivity that shows music.
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class PictureActivity extends Activity {


    private static int count = 1;
    private ImageSwitcher switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Toast.makeText(PictureActivity.this, R.string.touch_image, Toast.LENGTH_SHORT).show();
        switcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                return new ImageView(PictureActivity.this);
            }
        });
        switcher.setInAnimation(AnimationUtils.loadAnimation(PictureActivity.this, android.R.anim.slide_in_left));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(PictureActivity.this, android.R.anim.slide_out_right));
        switcher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showCurrentImage();
            }
        });

        showCurrentImage();

    }

    private void showCurrentImage() {

        switch (count) {
            case 1:
                switcher.setImageResource(R.drawable.image1);
                break;
            case 2:
                switcher.setImageResource(R.drawable.image2);
                break;
            case 3:
                switcher.setImageResource(R.drawable.image3);
                break;
            case 4:
                switcher.setImageResource(R.drawable.image4);
                break;
            case 5:
                switcher.setImageResource(R.drawable.image5);
                count = 1;
                return;
            default:
                break;
        }
        count++;

    }
}