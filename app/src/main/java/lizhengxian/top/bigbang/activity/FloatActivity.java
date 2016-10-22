package lizhengxian.top.bigbang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import lizhengxian.top.bigbang.R;

/**
 * Created by lizhengxian on 2016/10/23.
 * 浮窗Activity,用来展示BigBang结果
 */

public class FloatActivity extends Activity {
    ViewGroup mAutoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigbang);
        mAutoLayout = (ViewGroup) findViewById(R.id.auto_layout);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = new Button(this);
        button.setText("最后一个镜头");
        mAutoLayout.addView(button,params);
    }
}
