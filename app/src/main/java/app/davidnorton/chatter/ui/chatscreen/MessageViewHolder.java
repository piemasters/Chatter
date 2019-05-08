package app.davidnorton.chatter.ui.chatscreen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.models.Message;


public class MessageViewHolder extends RecyclerView.ViewHolder {

    public ImageView chatIV;
    public TextView chatTV;
    public TextView timeTV;

    public MessageViewHolder(View itemView) {
        super(itemView);

        chatIV = (ImageView)itemView.findViewById( R.id.chatIV );
        chatTV = (TextView)itemView.findViewById( R.id.chatTV );
        timeTV = (TextView)itemView.findViewById( R.id.timeTV );
    }

    public void bindToMessage(Message message, View.OnClickListener starClickListener) {
        chatTV.setText(message.getData());
    }

}