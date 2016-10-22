package lizhengxian.top.bigbang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import lizhengxian.top.bigbang.BigBangService;
import lizhengxian.top.bigbang.tool.HTTPRequest;
import lizhengxian.top.bigbang.tool.IResponse;
import lizhengxian.top.bigbang.R;

public class SettingActivity extends Activity {
    String TAG = getClass().getName();

    ViewGroup mRootView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        startService(new Intent(getApplicationContext(), BigBangService.class));

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
