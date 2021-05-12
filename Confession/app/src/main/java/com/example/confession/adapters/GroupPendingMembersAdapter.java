package com.example.confession.adapters;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confession.R;
import com.example.confession.binders.group.ManagePendingMembersBinder;
import com.example.confession.models.behaviors.ConfessionGroup;
import com.example.confession.models.data.BasicUserInfo;
import com.example.confession.models.data.ConfessionGroupInfo;
import com.example.confession.models.data.UserInfo;
import com.example.confession.presenters.group.ManagePendingMembersPresenter;

import java.util.ArrayList;

public class GroupPendingMembersAdapter extends RecyclerView.Adapter<GroupPendingMembersAdapter.ViewHolder>
    implements ManagePendingMembersBinder.View {

    Context context;
    ArrayList<BasicUserInfo> groupPendingUser;
    ConfessionGroupInfo group_info;
    ManagePendingMembersBinder.Presenter presenter;
    int removePosition = -1;

    public GroupPendingMembersAdapter(Context context, ArrayList<BasicUserInfo> groupPending, ConfessionGroupInfo group_info){
        this.context = context;
        this.groupPendingUser = groupPending;
        this.group_info = group_info;

        presenter = new ManagePendingMembersPresenter(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pending_members, parent, false);
        return new GroupPendingMembersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.InitData(position);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getItemCount() {
        return groupPendingUser.size() ;
    }

    @Override
    public void OnAcceptAllPendingMembersSuccess() {

    }

    @Override
    public void OnAcceptPendingMembersSuccess() {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Accept Successfully", Toast.LENGTH_SHORT).show();

                groupPendingUser.remove(removePosition);
                groupPendingUser.notifyAll();
            }
        });
    }

    @Override
    public void OnAcceptPendingMembersFailure(String error) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removePosition = -1;
                Toast.makeText(context, "Failed to accpect", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_group_pending_members;
        private ImageView ava_user_pending, accept_pending_member, reject_pending_members;
        private TextView pending_member_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            InitView(itemView);
            InitListener();
        }



        public void InitView(View view) {
            ll_group_pending_members = view.findViewById(R.id.ll_group_pending_members);
            ava_user_pending = view.findViewById(R.id.ava_user_pending);
            accept_pending_member = view.findViewById(R.id.accept_pending_member);
            reject_pending_members = view.findViewById(R.id.reject_pending_members);
            pending_member_name = view.findViewById(R.id.pending_member_name);
        }


        public void InitData(int i) {
            BasicUserInfo user_info = groupPendingUser.get(i);
            //ava_user_pending.setImageResource((Integer) user_info.avatar);
            pending_member_name.setText(user_info.name);

        //check and reject chua lafm
        }


        public void InitListener() {
            accept_pending_member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            presenter.HandleAcceptPendingMembers(
                                    groupPendingUser.get(getLayoutPosition()),
                                    group_info
                            );
                        }
                    }).start();

                    removePosition = getLayoutPosition();
                }
            });
            reject_pending_members.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }





    }


}
