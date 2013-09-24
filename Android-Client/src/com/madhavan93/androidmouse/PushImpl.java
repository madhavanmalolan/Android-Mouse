package com.madhavan93.androidmouse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class PushImpl extends AsyncTask<String, Void, String> {

	@Override 
	protected String doInBackground(String... params) {
		
		String gesture = params[0];
		InetAddress serverAddress = null;
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			serverAddress = InetAddress.getByName("192.168.1.133");
		} catch (SocketException e) {
			Log.e("GESTURE", "SOCKET ERROR");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			Log.e("GESTURE", "HOST ERROR");
			e.printStackTrace();
		}
		byte[] buf;
		buf = gesture.getBytes();
		
		DatagramPacket packet = new DatagramPacket(buf,  buf.length, serverAddress, 5005);
		try {
			socket.send(packet);
			Log.d("GESTURE", "ASYNC");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
