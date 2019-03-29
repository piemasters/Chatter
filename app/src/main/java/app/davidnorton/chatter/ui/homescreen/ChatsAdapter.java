package app.davidnorton.chatter.ui.homescreen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.chatscreen.ChatActivity;
import app.davidnorton.chatter.ui.models.Chat;
import app.davidnorton.chatter.ui.models.Message;
import app.davidnorton.chatter.ui.models.User;

import java.util.List;

import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {

    private List<Chat> mChats;
    private Context mContext;

    public ChatsAdapter(Context context, List<Chat> chats) {
        mContext = context;
        mChats = chats;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        Chat chat = mChats.get(position);
        User user = chat.getUser();
        Message message = chat.getMessage();

        if(!TextUtils.isEmpty(user.getProfilePicUrl())) {
            Picasso.with(holder.userIV.getContext()).load(user.getProfilePicUrl()).into(holder.userIV);
        }

        holder.nameTV.setText(user.getName());
        holder.messageTV.setText(message.getLastMessage());
        holder.lastMessageTimeTV.setText(message.getLastMessageTime());

        if(TextUtils.isEmpty(message.getUnreadMessageCount())) {
            holder.unreadMessageCountTV.setVisibility(View.GONE);
        } else {
            holder.unreadMessageCountTV.setVisibility(View.VISIBLE);
            holder.unreadMessageCountTV.setText(chat.getMessage().getUnreadMessageCount());
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout chatRL;
        CircleImageView userIV;
        TextView nameTV;
        TextView messageTV;
        TextView lastMessageTimeTV;
        TextView unreadMessageCountTV;

        public ChatViewHolder(View v) {
            super(v);
            chatRL = (RelativeLayout) v.findViewById(R.id.chatRL);
            userIV = (CircleImageView) v.findViewById(R.id.userIV);
            nameTV = (TextView) v.findViewById(R.id.nameTV);
            messageTV = (TextView) v.findViewById(R.id.messageTV);
            lastMessageTimeTV = (TextView) v.findViewById(R.id.lastMessageTimeTV);
            unreadMessageCountTV= (TextView) v.findViewById(R.id.unreadMessageCountTV);
            chatRL.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Chat chat = mChats.get(position);
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra(ChatActivity.EXTRAS_CHAT,chat);
                mContext.startActivity(intent);
            }
        }
    }

}