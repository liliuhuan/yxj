package com.bolooo.studyhomeparents.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ChatService extends Service {
    private static final String TAG = "DEBUG-WCL: " + ChatService.class.getSimpleName();

    private boolean mIsServiceDestroyed = false;
    private Socket mClientSocket;
    private PrintWriter mPrintWriter;
    private NotificationManager notificationManager;

    public ChatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override public void run() {
                connectTCPServer();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();

    }

    private void connectTCPServer() {
        Socket socket = null;
        // 不停重试直到连接成功为止
        while (socket == null) {
            try {
                socket = new Socket("localhost",12345);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);
                Log.e(TAG, "服务器连接成功");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.e(TAG, "连接TCP服务失败, 重试...");
            }
        }

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (!mIsServiceDestroyed) {
                String msg = br.readLine();
                Log.e(TAG, "收到信息: " + msg);
                if (msg != null) {
                    String time = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(System.currentTimeMillis());
                    String showedMsg = "server " + time + ":" + msg + "\n";
                    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(this);
                    notificationCompatBuilder.setAutoCancel(true).
                            setContentTitle("消息标题").
                            setContentText("").
                            setWhen(System.currentTimeMillis()).
                            setSmallIcon(R.drawable.noavatar).build();

                    Intent intent = new Intent(this, MainActivity.class);  //需要跳转指定的页面
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos",1);
                    intent.putExtras(bundle);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    notificationCompatBuilder.setContentIntent(pendingIntent);

                    Notification notification = notificationCompatBuilder.build();
                    notificationManager.notify(1,notification);
                }
            }
            Log.e(TAG, "退出");
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
