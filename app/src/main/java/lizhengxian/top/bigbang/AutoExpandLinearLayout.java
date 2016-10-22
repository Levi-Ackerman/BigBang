package lizhengxian.top.bigbang;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AutoExpandLinearLayout extends ViewGroup {
    private int maxWidth;// 可使用的最大宽度
    public AutoExpandLinearLayout(Context context) {
        super(context);
    }
    public AutoExpandLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AutoExpandLinearLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int containorHeight = 0;// 容器的高度,也就是本布局的高度。初始化赋值为0.
        int count = getChildCount();// 获取该布局内子组件的个数
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            /**
             * measure(int widthMeasureSpec,int
             * heightMeasureSpec)用于设置子组件显示模式.有三个值：<br/>
             * MeasureSpec.AT_MOST 该组件可以设置自己的大小,但是最大不能超过其父组件的限定<br/>
             * MeasureSpec.EXACTLY 无论该组件设置大小是多少,都只能按照父组件限制的大小来显示<br/>
             * MeasureSpec.UNSPECIFIED 该组件不受父组件的限制,可以设置任意大小
             */
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 把每个子组件的高度相加就是该组件要显示的高度。
            containorHeight += view.getMeasuredHeight();
        }
        setMeasuredDimension(maxWidth, containorHeight);// onMeasure方法的关键代码,该句设置父容器的大小。
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();// 获取子组件数
        int row = 1;// 子组件行数,初始化赋值为1
        int left = 0;// 子组件的左边“坐标”
        int right = 0;// 子组件的右边“坐标”
        int top = 0;// 子组件的顶部“坐标”
        int bottom = 0;// 子组件的底部“坐标”
        int p = getPaddingLeft();// 在父组件中设置的padding属性的值,该值显然也会影响到子组件在屏幕的显示位置
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();// 测量子组件的宽
            int height = view.getMeasuredHeight();// 测量子组件的高
            left = p + right;// ---------------------------------------------------备注1
            right = left + width;// -----------------------------------------------备注2
            top = p * row + height * (row - 1);// ---------------------------------备注3
            bottom = top + height;// ----------------------------------------------备注4
            if (right > maxWidth) {
                row++;
                left = 0;//每次换行后要将子组件左边“坐标”与右边“坐标”重新初始化
                right = 0;
                left = p + right;
                right = left + width;
                top = p * row + height * (row - 1);
                bottom = top + height;
            }
            view.layout(left, top, right, bottom);// 最后按照计算出来的“坐标”将子组件放在父容器内
        }
    }
}
