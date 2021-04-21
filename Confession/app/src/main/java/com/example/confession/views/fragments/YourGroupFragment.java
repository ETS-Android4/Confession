package com.example.confession.views.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.confession.R;
import com.example.confession.adapters.GroupListAdapter;
import com.example.confession.adapters.MyJoinedGroupAdapter;
import com.example.confession.adapters.MyOwnGroupAdapter;
import com.example.confession.binders.JoinedGroupsTabBinder;
import com.example.confession.models.data.ConfessionGroupInfo;
import com.example.confession.presenters.JoinedGroupsTabPresenter;


import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YourGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourGroupFragment extends Fragment implements JoinedGroupsTabBinder.View{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mPage";
    private static final String ARG_PARAM2 = "mTitle";

    // TODO: Rename and change types of parameters
    private int mPage;
    private String mTitle;
    private Thread newThread;
    private JoinedGroupsTabBinder.Presenter presenter;
    private MyOwnGroupAdapter mAdapter;


    androidx.appcompat.widget.SearchView your_gr_search;
    ListView lv_your_group_item;
    LinearLayout ll_your_group_progress;



    public YourGroupFragment() {
        // Required empty public constructor
        presenter = new JoinedGroupsTabPresenter(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page page number.
     * @param title title of page.
     * @return A new instance of fragment YourGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YourGroupFragment newInstance(int page, String title) {
        YourGroupFragment fragment = new YourGroupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, page);
        args.putString(ARG_PARAM2, title);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PARAM1);
            mTitle = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_your_group, container, false);

        your_gr_search = view.findViewById(R.id.your_gr_search);
        lv_your_group_item = view.findViewById(R.id.lv_your_group_item);
        ll_your_group_progress = view.findViewById(R.id.ll_your_group_progress);

        Toast.makeText(getContext(), " Your Group onCreateView", Toast.LENGTH_LONG).show();

        InitListenser();
        CallPresenter();
        //new CallAPI().execute();


        return view;
    }

    private void CallPresenter(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                presenter.HandleGetCreatedGroups();
            }
        }).start();

        Toast.makeText(getContext(), " Your Group on call presenter", Toast.LENGTH_LONG).show();
    }

    public void InitListenser(){
        your_gr_search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void UpdateListView() {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                lv_your_group_item.invalidateViews();
                lv_your_group_item.refreshDrawableState();
            }
        };
        run.run();
    }



    @Override
    public void OnGetFollowedGroupsSuccess(ArrayList<ConfessionGroupInfo> groups) {
        //Not in use
    }

    @Override
    public void OnGetFollowedGroupsFailure(String error) {
        //Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnGetCreatedGroupsSuccess(ArrayList<ConfessionGroupInfo> groups) {
        //newThread.interrupt();
        Log.e("Successssssssss YG", "Check YYG");
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Set visibility
                ll_your_group_progress.setVisibility(View.GONE);
                lv_your_group_item.setVisibility(View.VISIBLE);

                //set adapter
                mAdapter = new MyOwnGroupAdapter(getContext(), groups);
                lv_your_group_item.setAdapter(mAdapter);
            }
        });

    }

    @Override
    public void OnGetCreatedGroupsFailure(String error) {
        //newThread.interrupt();

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                //Set visibility
                ll_your_group_progress.setVisibility(View.GONE);
                lv_your_group_item.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public void OnLeaveGroupSuccess(ConfessionGroupInfo group) {
        //Not in use
    }

    @Override
    public void OnLeaveGroupFailure(String error) {
        //Not in use
    }
}
