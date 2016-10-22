package lizhengxian.top.bigbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import lizhengxian.top.bigbang.R;
import lizhengxian.top.bigbang.tool.Constant;
import lizhengxian.top.bigbang.tool.HTTPRequest;
import lizhengxian.top.bigbang.tool.IResponse;

/**
 * Created by lizhengxian on 2016/10/23.
 * 浮窗Activity,用来展示BigBang结果
 */

public class BigBangActivity extends Activity implements View.OnClickListener {
    ViewGroup mAutoLayout;
    Button mCopyBtn;
    Button mCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigbang);
        mAutoLayout = (ViewGroup) findViewById(R.id.auto_layout);
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
            public void finish(String[] words) {
                if (words != null) {
                    final StringBuilder builder = new StringBuilder("分词结果: ");
                    for (String word : words) {
                        builder.append(word).append(" | ");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),builder.toString(),Toast.LENGTH_SHORT).show();
//                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            Button button = new Button(this);
//                            button.setText("最后一个镜头");
//                            mAutoLayout.addView(button, params);
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
            case R.id.btn_copy:break;
        }
    }
}
