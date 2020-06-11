package com.example.submission5.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.submission5.BuildConfig;
import com.example.submission5.MainActivity;
import com.example.submission5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MovieDailyRemainder extends BroadcastReceiver {
    public static final String TYPE_DAILY = "DailyNotification";
    public static final String TYPE_RELEASE = "TodayRelease";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "extra_type";
    private static final int MAX_NOTIF = 2;

    private final int ID_DAILY = 100;
    private final int ID_RELEASE = 102;
    private final List<NotifItem> stackNotif = new ArrayList<>();
    AlarmManager alarmManager;
    String DATE_FORMAT = "yyyy-MM-dd";
    String TIME_FORMAT = "HH:mm";
    String message,type;
    private int idNotif = 0;



    public void setAlarm(Context context,String type,String message) {


        Calendar calendar = Calendar.getInstance();


        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, MovieDailyRemainder.class);
            String sTime;
            PendingIntent pendingIntent;
            if (type.equals(TYPE_DAILY)) {
                intent.putExtra(EXTRA_MESSAGE, message);
                intent.putExtra(EXTRA_TYPE, TYPE_DAILY);
                sTime = context.getString(R.string.timeDaily);
                pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);
            } else {
                intent.putExtra(EXTRA_MESSAGE, message);
                intent.putExtra(EXTRA_TYPE, TYPE_RELEASE);
                sTime = context.getString(R.string.timeRilis);
                pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
            }
            if (isDateInvalid(sTime, TIME_FORMAT)) return;
            String[] split = sTime.split(":");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(split[1]));
            calendar.set(Calendar.SECOND, 0);
            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                Toast.makeText(context, "Notification aktif " + type + " at " + calendar.getTime(), Toast.LENGTH_SHORT).show();
            }





    }
    public void setCancel(Context context,String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieDailyRemainder.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, context.getString(R.string.messageNotif)+ type, Toast.LENGTH_SHORT).show();
        }

    }
    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String CHANNEL_ID;
        String CHANNEL_NAME;
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent;
        if (notifId == ID_DAILY) {
            CHANNEL_ID = "Channel_1";
            CHANNEL_NAME = "Daily channel";
            pendingIntent = PendingIntent.getActivities(context, ID_DAILY, new Intent[]{intent}, 0);
        } else {
            CHANNEL_ID = "Channel_2";
            CHANNEL_NAME = "Release channel";
            pendingIntent = PendingIntent.getActivities(context, ID_RELEASE, new Intent[]{intent}, 0);
        }

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder;
        if (notifId == ID_DAILY) {
            // id daily

            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound);

        } else {

            if (idNotif < MAX_NOTIF) {
                builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound);
            } else {
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                for (int line=notifId;line>0;line--){
                    inboxStyle.addLine(stackNotif.get(line-1).getMesssage());
                }
                inboxStyle
                        .setBigContentTitle(title)
                        .setSummaryText(title);
                builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                        .setStyle(inboxStyle)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound);

                Log.i("where Notiif", "at else");
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);

        }
    }

    @Override
    public void onReceive(Context context, Intent mIntent) {
        /*sendNotification(context, context.getString(R.string.judul_daily),
                context.getString(R.string.desk_daily),ID_REPEATING);*/

        type = mIntent.getStringExtra(EXTRA_TYPE);
        message = mIntent.getStringExtra(EXTRA_MESSAGE);

        String title;
        assert type != null;
        int notifId = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;
        if (type.equalsIgnoreCase(TYPE_DAILY)){
            title = context.getString(R.string.daily_reminder);
            showAlarmNotification(context,title,message,notifId);

        }else {
            title = context.getString(R.string.message_rilis_new);
            getData(context,title);
        }
    }
    private void getData(final Context context, final String title){
        message = "Nothing new movies availble today";
        String API_KEY = BuildConfig.TheMovieDBAPI;
        Log.d("e", "getData: ");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String date = dateFormat.format(calendar.getTime());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte=" + date + "&primary_release_date.lte=" + date;

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray data;
                try {
                    data = response.getJSONArray("results");
                    for (int i = 0;i<data.length();i++){
                        JSONObject jsonObject = data.getJSONObject(i);
                        String name =  jsonObject.getString("title");

                        NotifItem notifItem = new NotifItem(idNotif,context.getString(R.string.judul_daily),name);
                        stackNotif.add(notifItem);
                        idNotif++;
                        Log.d("e","yan");
                    }
                    message = context.getString(R.string.desk_daily);
                    showAlarmNotification(context,title,message,idNotif);
                    idNotif = 0;
                    stackNotif.clear();
                }catch (JSONException e){
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Release Notif", "" + error);


            }
        });
        queue.add(json);

    }
}
