package com.prueba.myapplication.controller.activities.master_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prueba.myapplication.R;
import com.prueba.myapplication.controller.activities.login.LoginActivity;
import com.prueba.myapplication.controller.managers.PlayerManager;
import com.prueba.myapplication.model.Apuesta;
import com.prueba.myapplication.model.ApuestaRealizada;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Players. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PlayerDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PlayerListActivity extends AppCompatActivity implements PlayerCallback {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private ImageButton b1,b2;
    FloatingActionButton fab1;
    private LinearLayout butonees;
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private List<Apuesta> apuestas,ap1x2;
    private String nombreLeague;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

          nombreLeague = getIntent().getStringExtra("nombreLeague");
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butonees = (LinearLayout) findViewById(R.id.butones);

               if(butonees.getVisibility() == View.VISIBLE){
                   butonees.setVisibility(View.INVISIBLE);
               }else{
                   butonees.setVisibility(View.VISIBLE);
               }

                butonees.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });



                b1 = (ImageButton) findViewById(R.id.imageButton1);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(PlayerListActivity.this, UserDetailActivity.class);
                        startActivity(i);
                    }
                });
                b2 = (ImageButton) findViewById(R.id.imageButton2);

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.player_list);
        assert recyclerView != null;

        if (findViewById(R.id.player_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (nombreLeague==null) {
            nombreLeague = getIntent().getStringExtra("nombreLeague");
        }

    }

    @Override
    protected void onPause(){
        super.onPause();

            nombreLeague = getIntent().getStringExtra("nombreLeague");


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        PlayerManager.getInstance(this.getApplicationContext()).getApuestasByleagueName(PlayerListActivity.this, nombreLeague);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.i("setupRecyclerView", "                     " + apuestas);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(apuestas));
    }

    @Override
    public void onSuccess(List<Apuesta> apuestaList) {
        List<Apuesta> apuestaNames = new ArrayList<Apuesta>();
        int pos = 0;
        for (int i = 0; i<apuestaList.size();i=i+3){
            apuestaNames.add(pos,apuestaList.get(i));
            pos++;
        }
        apuestas = apuestaNames;
        setupRecyclerView(recyclerView);
    }

    @Override
    public void onSuccessTop(List<ApuestaRealizada> apuestaList) {

    }

    @Override
    public void onSuccess1(List<Apuesta> apuestaList) {

    }

    @Override
    public void onFailure(Throwable t) {

        Intent i = new Intent(PlayerListActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Apuesta> mValues;

        public SimpleItemRecyclerViewAdapter(List<Apuesta> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.player_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
           holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getLigaName().toString());
            holder.mContentView.setText(mValues.get(position).getApuestaName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());
                        PlayerDetailFragment fragment = new PlayerDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.player_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, PlayerDetailActivity.class);

                        intent.putExtra("nombreLeague", holder.mItem.getLigaName().toString());
                        // extra.putString(i);
                        intent.putExtra(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());
                        // intent.putExtra(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.getApuestaName().toString());
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Apuesta mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
