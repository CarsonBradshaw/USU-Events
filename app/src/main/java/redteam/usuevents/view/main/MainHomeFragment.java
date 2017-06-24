package redteam.usuevents.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import redteam.usuevents.R;
import redteam.usuevents.model.Event;

/**
 * Created by Admin on 6/12/2017.
 */

public class MainHomeFragment extends Fragment {

    private static View sView;

    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;


    public static MainHomeFragment getInstance() {
        return new MainHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(sView==null){
            sView = inflater.inflate(R.layout.fragment_main_home, container, false);
        }

        bindViews(container);

        return sView;
    }

    private void bindViews(ViewGroup container){
        mRecyclerView = (RecyclerView)container.findViewById(R.id.fragment_main_home_recycler_view);
    }






    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public void bind(Event event) {

        }

        public EventHolder(LayoutInflater inflater, ViewGroup parent, int layoutResourceId) {
            super(inflater.inflate(layoutResourceId, parent, false));

        }

        @Override
        public void onClick(View v) {

        }
    }




    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
