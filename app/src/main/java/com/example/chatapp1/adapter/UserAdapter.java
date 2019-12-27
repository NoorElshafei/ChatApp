package com.example.chatapp1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp1.Model.Chat;
import com.example.chatapp1.Model.User;
import com.example.chatapp1.R;
import com.example.chatapp1.message.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<User> mUsers;
    private boolean isChat=false;
    private String theLastMessage;

    public UserAdapter(Context context, List<User> mUsers,boolean isChat) {
        this.context = context;
        this.mUsers = mUsers;
        this.isChat=isChat;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        final User user=mUsers.get(position);
        holder.userName.setText(user.getUsername());
        if(user.getImageURL().equals("default")){
        holder.imageProfile.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(user.getImageURL()).into(holder.imageProfile);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid",user.getId());
                context.startActivity(intent);
            }
        });

        if(isChat){
            lastMessage(user.getId(),holder.lastChat);
        }else{
            holder.lastChat.setVisibility(View.GONE);
        }

        if(isChat){

            if (user.getStatus().equals("online")){
                holder.imageOff.setVisibility(View.GONE);
                holder.imageOn.setVisibility(View.VISIBLE);
            }else{
                holder.imageOff.setVisibility(View.VISIBLE);
                holder.imageOn.setVisibility(View.GONE);
            }
        }else {
            holder.imageOff.setVisibility(View.GONE);
            holder.imageOn.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private ImageView imageProfile;
        private ImageView imageOn;
        private ImageView imageOff;
        private TextView lastChat;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.user_name_user);
            imageProfile= itemView.findViewById(R.id.profile_image_user);
            imageOn= itemView.findViewById(R.id.img_on);
            imageOff= itemView.findViewById(R.id.img_off);
            lastChat=itemView.findViewById(R.id.last_msg);

        }
    }

    public void lastMessage(final String userId, final TextView lastMessage){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    if(firebaseUser!=null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId)
                                || chat.getReceiver().equals(userId) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                        }
                    }
                }
                switch (theLastMessage){
                    case "default":
                        lastMessage.setText("No Message");
                        break;
                    default:
                        lastMessage.setText(theLastMessage);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
