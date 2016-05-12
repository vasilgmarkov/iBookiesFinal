package com.prueba.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prueba.myapplication.controller.activities.login.LoginActivity;
import com.prueba.myapplication.controller.activities.master_detail.PlayerCallback;
import com.prueba.myapplication.controller.activities.master_detail.PlayerDetailActivity;
import com.prueba.myapplication.controller.activities.master_detail.PlayerDetailFragment;
import com.prueba.myapplication.controller.activities.master_detail.PlayerListActivity;
import com.prueba.myapplication.controller.activities.master_detail.UserDetailActivity;
import com.prueba.myapplication.controller.managers.PlayerManager;
import com.prueba.myapplication.model.Apuesta;
import com.prueba.myapplication.model.ApuestaRealizada;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class basket extends Fragment implements PlayerCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton b1,b2;
    FloatingActionButton fab1;
    private LinearLayout butonees;
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private List<Apuesta> apuestas,bet;
    private String nombreLeague;
    private String h,d,a;



    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment1 newInstance(String param1, String param2) {
        BlankFragment1 fragment = new BlankFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public basket() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_basket, container, false);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nombreLeague =  bundle.getString("liga");
        }

        //nombreLeague = "Spanish La Liga Primera";
        // Show the Up button in the action bar.




        recyclerView = (RecyclerView) rootView.findViewById(R.id.player_list);
        assert recyclerView != null;

        if (rootView.findViewById(R.id.player_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }




        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



    @Override
    public void onResume(){
        super.onResume();
        PlayerManager.getInstance(getActivity().getApplicationContext()).getApuestasByleagueNameBasket(basket.this, nombreLeague);
    }

    @Override
    public void onPause(){
        super.onPause();



    }



    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.i("setupRecyclerView", "                     " + apuestas);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(apuestas));
    }






    @Override
    public void onSuccess(List<Apuesta> apuestaList) {
        bet=apuestaList;
        List<Apuesta> apuestaNames = new ArrayList<Apuesta>();
        int pos = 0;
        for (int i = 0; i<apuestaList.size();i=i+2){
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



        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);

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
            String nameGame = "";
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getLigaName().toString());

            for (int w = 0; w < mValues.get(position).getApuestaName().toString().length(); w++) {

                if (mValues.get(position).getApuestaName().toString().charAt(w) == '-') {

                    nameGame = mValues.get(position).getApuestaName().toString().substring(0, w);

                }
            }
            int hTeam = 0, aTeam = 0;
            String aTeamName = "", hTeamName = "",game="";
            if(nombreLeague=="NBA") {

                game = apuestas.get(position).getApuestaName();

                for (int i = 1; i < game.length(); i++) {
                    if (game.charAt(i) == '@' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                        hTeam = i;
                        break;
                    }
                }
                for (int i = 1; i < game.length(); i++) {
                    if (game.charAt(i) == '-' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                        aTeam = i;
                        break;
                    }
                }
            }else{


                game = apuestas.get(position).getApuestaName();

                for (int i = 1; i < game.length(); i++) {
                    if (game.charAt(i) == 'v' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                        hTeam = i;
                        break;
                    }
                }
                for (int i = 1; i < game.length(); i++) {
                    if (game.charAt(i) == '-' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                        aTeam = i;
                        break;
                    }
                }

            }
            hTeamName = game.substring(0, hTeam - 1);
            aTeamName = game.substring(hTeam+2, aTeam-1);
            for (int i = 0; i < bet.size(); i++) {

                if (bet.get(i).getaApostarName().toString().equals(hTeamName)) {
                    //  home.setText(bet.get(i).getaApostarName().toString());
                    holder.aOdd.setText(bet.get(i).getaApostarOdd().toString());
                    //  h = i;
                }
                if (bet.get(i).getaApostarName().toString().equals(aTeamName)) {
                    //   away.setText(bet.get(i).getaApostarName().toString());
                    holder.hOdd.setText(bet.get(i).getaApostarOdd().toString());
                    //   a = i;
                }


            }

            holder.dOdd.setVisibility(View.INVISIBLE);
            holder.htName.setText(hTeamName);
            holder.atName.setText(aTeamName);
            holder.mContentView.setText("VS");

            holder.timeGame.setText("Game Time : "+mValues.get(position).getPartidoTime());
            holder.dateGame.setText(" Game Date :"+mValues.get(position).getPartidoStartDate());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, PlayerDetailActivity.class);

                    intent.putExtra("nombreLeague", holder.mItem.getLigaName().toString());
                    // extra.putString(i);
                    intent.putExtra(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.getId().toString());
                    // intent.putExtra(PlayerDetailFragment.ARG_ITEM_ID, holder.mItem.getApuestaName().toString());
                    context.startActivity(intent);
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
            public TextView timeGame,dateGame,hOdd,dOdd,aOdd,htName,atName;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                timeGame = (TextView) view.findViewById(R.id.timeGame);
                dateGame = (TextView) view.findViewById(R.id.dateGame);
                hOdd = (TextView) view.findViewById(R.id.hodd);
                dOdd = (TextView) view.findViewById(R.id.dodd);
                aOdd = (TextView) view.findViewById(R.id.aodd);
                htName = (TextView) view.findViewById(R.id.hTeamN);
                atName = (TextView) view.findViewById(R.id.awayTeamN);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

    }}
