package com.elkhamitech.studentmanagerr.businesslogic.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.elkhamitech.studentmanagerr.view.activities.MainTimeManagerActivity;
import com.elkhamitech.studentmanagerr.R;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		buildNotification(context);
		//AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		try {
		    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    Ringtone ring = RingtoneManager.getRingtone(context, notification);
		    ring.play();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	private void buildNotification(Context context){
		  NotificationManager notificationManager
		  = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification.Builder builder = new Notification.Builder(context);
		  
		  Intent intent = new Intent(context, MainTimeManagerActivity.class);
		  PendingIntent pendingIntent
		  = PendingIntent.getActivity(context, 0, intent, 0);
		  
		  builder
		  .setSmallIcon(R.mipmap.ic_launcher)
		  .setContentTitle("Time Manager")
		  .setContentText("Studying time is finished")
		  .setContentInfo("Take a Break")
		  .setTicker("Ticker")
		  .setLights(0xFFFF0000, 500, 500) //setLights (int argb, int onMs, int offMs)
		  .setContentIntent(pendingIntent)
		  .setAutoCancel(true);
		  
		  @SuppressWarnings("deprecation")
          Notification notification = builder.getNotification();
		  
		  notificationManager.notify(R.mipmap.ic_launcher, notification);
		 }
}
