package lizhengxian.top.bigbang;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    String TAG = getClass().getName();

    ViewGroup mRootView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        findViewById(R.id.test_ws).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HTTPRequest.getSplitChar("测试一下讯飞分词云的效果如何呢?", new IResponse() {
                    @Override
                    public void finish(String[] words) {
                        if (words != null) {
                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
