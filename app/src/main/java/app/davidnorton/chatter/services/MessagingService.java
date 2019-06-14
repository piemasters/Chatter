package app.davidnorton.chatter.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.Database;
import app.davidnorton.chatter.ui.models.Message;
import app.davidnorton.chatter.ui.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        //todo: handle notification
        Log.d("data message", remoteMessage.getData().toString());

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                try {
                    Gson gson = new Gson();
                    String data = remoteMessage.getData().toString();
                    //Toast.makeText(MessagingService.this.getApplicationContext(), data, Toast.LENGTH_LONG).show();
                    JsonElement jsonElement  = gson.toJsonTree(remoteMessage.getData());
                    Message message = gson.fromJson(jsonElement, Message.class);

                    onMessageReceived(message);
                }
                catch(Exception e) {
                   e.printStackTrace();
                }
            }
        });

    }

    private void onMessageReceived(final Message message) {
        String senderUid = message.getSenderUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(Database.NODE_USERS).child(senderUid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {

    @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    User user = dataSnapshot.getValue(User.class);

                    showNotification(MessagingService.this.getApplicationContext(), user, message);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private final int NOTIFICATION_ID = 237;

    private void showNotification(final Context context, final User user, final Message message) {

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                // TODO: Tidy up notification channel and builder
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // The id of the channel.
                String id = "my_channel_01";

                // The user-visible name of the channel.
                //CharSequence name = getString(R.string.channel_name);
                CharSequence name = "Channel";

                // The user-visible description of the channel.
                //String description = getString(R.string.channel_description);
                String description = "Channel Description";

                int importance = NotificationManager.IMPORTANCE_LOW;

                NotificationChannel mChannel = new NotificationChannel(id, name,importance);

                // Configure the notification channel.
                mChannel.setDescription(description);

                mChannel.enableLights(true);
                // Sets the notification light color for notifications posted to this channel, if the device supports this feature.
                mChannel.setLightColor(Color.RED);

                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                mNotificationManager.createNotificationChannel(mChannel);

                mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                // Sets an ID for the notification, so it can be updated.
                int notifyID = 1;

                // The id of the channel.
                String CHANNEL_ID = "my_channel_01";

                // Create a notification and set the notification channel.
                Notification notification = new Notification.Builder(MessagingService.this.getApplicationContext(), "Channel")
                        .setContentTitle(user.getName())
                        .setContentText(message.getData())
                        .setSmallIcon(R.drawable.notifybar)
                        .setLargeIcon(bitmap)
                        .setChannelId(CHANNEL_ID)
                        .build();

                // Issue the notification.
                mNotificationManager.notify(0, notification);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        Picasso.with(this).load(user.getProfilePicUrl()).into(target);
    }

    private void showMultipleMessageNotification() {

    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

}
