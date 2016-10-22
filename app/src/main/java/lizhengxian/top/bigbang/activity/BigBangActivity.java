package lizhengxian.top.bigbang.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import lizhengxian.top.bigbang.R;
import lizhengxian.top.bigbang.tool.Constant;
import lizhengxian.top.bigbang.tool.HTTPRequest;
import lizhengxian.top.bigbang.tool.IResponse;
import lizhengxian.top.bigbang.widget.AutoExpandLinearLayout;
import lizhengxian.top.bigbang.widget.BangWordView;

/**
 * Created by lizhengxian on 2016/10/23.
 * 浮窗Activity,用来展示BigBang结果
 */

public class BigBangActivity extends Activity implements View.OnClickListener {
    AutoExpandLinearLayout mAutoLayout;
    Button mCopyBtn;
    Button mCancelBtn;
    public static boolean isShowing = false;

    @Override
    protected void onStart() {
        super.onStart();
        isShowing = true;
    }

    @Override
    protected void onStop() {
        isShowing = false;
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigbang);
        registerReceiver(receiver,new IntentFilter(Constant.FINISH_BIGBANG_ACTIVITY));
        mAutoLayout = (AutoExpandLinearLayout) findViewById(R.id.auto_layout);
        mCopyBtn = (Button) findViewById(R.id.btn_copy);
        mCancelBtn = (Button)findViewById(R.id.btn_cancel);
        mCopyBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        String text = getIntent().getStringExtra(Constant.CLIPBOARD_TEXT);
        requestServe(text);
    }

    protected void requestServe(String text) {
        HTTPRequest.getSplitChar(text, new IResponse() {
            @Override
            public void finish(final String[] words) {
                if (words != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (String word : words) {
                                BangWordView bangWordView = new BangWordView(getApplication(),word);
                                mAutoLayout.addView(bangWordView);
                            }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_copy:
                int count = mAutoLayout.getChildCount();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < count; i++) {
                    CheckBox checkBox = (CheckBox) mAutoLayout.getChildAt(i);
                    if (checkBox.isChecked()){
                        builder.append(checkBox.getText());
                    }
                }
                String str = builder.toString();
                if(TextUtils.isEmpty(str)){
                    Toast.makeText(getApplicationContext(),"没有选中词组",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"["+str+"] 已经复制到剪切板",Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboardManager.setText(str);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
}
