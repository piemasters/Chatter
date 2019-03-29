package app.davidnorton.chatter.ui.chatscreen;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.davidnorton.chatter.R;
import app.davidnorton.chatter.ui.models.Chat;
import app.davidnorton.chatter.ui.models.Message;
import app.davidnorton.chatter.ui.models.User;

import java.util.List;

import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {

    private List<Chat> mChats;

    public ChatsAdapter(List<Chat> chats) {
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
            holder.unreadMessageCountTV.setText(chat.getMessage().getUnreadMessageCount());
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userIV;
        TextView nameTV;
        TextView messageTV;
        TextView lastMessageTimeTV;
        TextView unreadMessageCountTV;

        public ChatViewHolder(View v) {
            super(v);
            userIV = (CircleImageView) v.findViewById(R.id.userIV);
            nameTV = (TextView) v.findViewById(R.id.nameTV);
            messageTV = (TextView) v.findViewById(R.id.messageTV);
            lastMessageTimeTV = (TextView) v.findViewById(R.id.lastMessageTimeTV);
            unreadMessageCountTV= (TextView) v.findViewById(R.id.unreadMessageCountTV);
        }
    }

}