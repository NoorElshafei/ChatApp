package com.example.chatapp1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp1.Model.Chat;
import com.example.chatapp1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int MSG_TYPE_RIGHT = 1;
    private static final int MSG_TYPE_LEFT = 0;
    private Context context;
    private List<Chat> mChat;
    private FirebaseUser firebaseUser;
    private String imageURL;


    public MessageAdapter(Context context, List<Chat> mChat,String imageURL) {
        this.context = context;
        this.mChat = mChat;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Chat chat = mChat.get(position);

        holder.message.setText(chat.getMessage());
        if (imageURL.equals("default")) {
            holder.imageProfile.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(imageURL).into(holder.imageProfile);
        }


        if (position == mChat.size()-1){
            if(chat.isIsseen()){
                holder.txt_seen.setText("seen");
            }else {
                holder.txt_seen.setText("delivered");
            }
        }else{
            holder.txt_seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView message;
        private ImageView imageProfile;
        private TextView txt_seen;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.show_message);
            imageProfile = itemView.findViewById(R.id.profile_image_chat);
            txt_seen = itemView.findViewById(R.id.is_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;

        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
