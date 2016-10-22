package lizhengxian.top.bigbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import lizhengxian.top.bigbang.BigBangService;
import lizhengxian.top.bigbang.tool.HTTPRequest;
import lizhengxian.top.bigbang.tool.IResponse;
import lizhengxian.top.bigbang.R;

public class SettingActivity extends Activity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener {
    String TAG = getClass().getName();

    ViewGroup mRootView ;
    Switch mRunSwitch;
    Button mTestBtn;
    EditText mTestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mRunSwitch = (Switch) findViewById(R.id.sw_run);
        mRunSwitch.setOnCheckedChangeListener(this);
        mRunSwitch.setChecked(true);
        mTestBtn = (Button)findViewById(R.id.test_ws);
        mTestBtn.setOnClickListener(this);
        mTestText = (EditText) findViewById(R.id.test_text);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b){
            startService(new Intent(this,BigBangService.class));
        }else{
            stopService(new Intent(this,BigBangService.class));
        }
    }

    @Override
    public void onClick(View view) {
        String text = mTestText.getText().toString();
        HTTPRequest.getSplitChar(text, new IResponse() {
            @Override
            public void finish(String[] words) {
                if (words != null) {
                    final StringBuilder builder = new StringBuilder("分词结果: ");
                    for (String word : words) {
                        builder.append(word).append(" | ");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplication(), builder.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void failure(final String errorMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
