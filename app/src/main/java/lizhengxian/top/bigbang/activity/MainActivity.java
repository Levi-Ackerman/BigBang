package lizhengxian.top.bigbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import lizhengxian.top.bigbang.HTTPRequest;
import lizhengxian.top.bigbang.IResponse;
import lizhengxian.top.bigbang.R;

public class MainActivity extends Activity {
    String TAG = getClass().getName();

    ViewGroup mRootView ;
    ViewGroup mAutoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mAutoLayout = (ViewGroup) findViewById(R.id.auto_layout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = new Button(this);
        button.setText("最后一个镜头");
        mAutoLayout.addView(button,params);

        findViewById(R.id.test_ws).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HTTPRequest.getSplitChar("测试一下讯飞分词云的效果如何呢?", new IResponse() {
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
                                    Toast.makeText(getApplication(), builder.toString(), Toast.LENGTH_SHORT).show();
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
        });
    }
}
