package jialu.cmu.edu.leonard.cohen.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import jialu.cmu.edu.leonard.cohen.constant.Constant;
import jialu.cmu.edu.leonard.cohen.exception.AutoException;
import jialu.cmu.edu.leonard.cohen.util.AppUtil;
import lip.cmu.com.witnessjayz.R;

/**
 * MailListActivity that sends mail
 * <p>
 * author : jialu chen
 * andrew id: jialuc
 */
public class MailListActivity extends Activity implements View.OnClickListener {

    private EditText etNickname;
    private EditText etEmailAddress;
    private EditText etSubject;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        etNickname = (EditText) findViewById(R.id.et_nickname);
        etEmailAddress = (EditText) findViewById(R.id.et_email_address);
        etSubject = (EditText) findViewById(R.id.et_subject);
        etContent = (EditText) findViewById(R.id.et_content);

        // Set the Listener
        findViewById(R.id.btn_send_email).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String nickname = etNickname.getText().toString();
        String email = etEmailAddress.getText().toString();
        String subject = etSubject.getText().toString();
        String content = etContent.getText().toString();
        if (checkInput(nickname, email, subject, content)) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, Constant.aEmailList);
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Email : " + email + " \n  Content: \n" +
                    content + " || \n" + "    from : " + nickname + "--");
            startActivity(emailIntent);
        }
    }


    private boolean checkInput(String nickname, String email, String subject, String content) {

        if (nickname.length() == 0 || email.length() == 0 || subject.length() == 0 || content.length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MailListActivity.this);
            new AutoException(getString(R.string.empty_input), builder);
            builder.setPositiveButton(R.string.errorButton, null);
            builder.show();
            return false;
        }

        if (AppUtil.isEmailValid(email)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MailListActivity.this);
            new AutoException(getString(R.string.invalid_email), builder);
            builder.setPositiveButton(R.string.errorButton, null);
            builder.show();
            return false;
        }

        return true;
    }

}
