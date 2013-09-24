package com.madhavan93.androidmouse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PushService extends Service{

	public static String EXTRA_GESTURE_JSON = "com.madhavan93.androidmouse.gestureJSON";
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String gesture = intent.getStringExtra("GESTURE");
		PushImpl pusher = new PushImpl();
		String[] params = {gesture};
		pusher.execute(params);
		
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	  public void onCreate() {
		super.onCreate();
	}
}


//DatagramSocket socket = new DatagramSocket();
//byte[] buf;
//if(!input.getText().toString().isEmpty())
//{
//buf=input.getText().toString().getBytes();
//}
//else
//{
//buf = ("Default message").getBytes();
//}
//DatagramPacket packet = new DatagramPacket(buf,
//
//              buf.length, serverAddr, SERVERPORT);
//updatetrack("Client: Sending ‘" + new String(buf) + "’n");
//socket.send(packet);
//updatetrack("Client: Message sentn");
//updatetrack("Client: Succeed!n");
//} catch (Exception e) {
//updatetrack("Client: Error!n");
//}
