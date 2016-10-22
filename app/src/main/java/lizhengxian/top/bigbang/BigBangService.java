package lizhengxian.top.bigbang;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import lizhengxian.top.bigbang.activity.BigBangActivity;
import lizhengxian.top.bigbang.activity.SettingActivity;
import lizhengxian.top.bigbang.tool.Constant;

import static android.app.PendingIntent.getActivity;

/**
 * 后台监听剪切板事件,有复制就BigBang一下
 */
public class BigBangService extends Service {

    public static final int NOTIFICATION_ID = 110;

    public BigBangService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeService();
        listenClipboard();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 通知栏常驻,提醒用户BigBang正在后台提供服务
     */
    private void startForeService() {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, SettingActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, PendingIntent.FLAG_UPDATE_CURRENT)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("BigBang正在后台运行") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("复制想要bang的内容到剪切板, BigBang帮你处理") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        Notification notification = builder.build(); // 获取构建好的Notification

        startForeground(NOTIFICATION_ID, notification);// 开始前台服务
    }

    /**
     * 监听剪切板
     */
    private void listenClipboard() {
        final ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (BigBangActivity.isShowing) {
                    //是BigBang复制的,复制结束了就发广播关闭
                    sendBroadcast(new Intent(Constant.FINISH_BIGBANG_ACTIVITY));
                } else {
                    Intent intent = new Intent(getApplication(), BigBangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constant.CLIPBOARD_TEXT, clipboard.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        super.onDestroy();
    }
}
