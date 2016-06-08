package com.prueba.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prueba.myapplication.controller.activities.login.LoginActivity;
import com.prueba.myapplication.controller.activities.main.MainActivity;
import com.prueba.myapplication.controller.activities.master_detail.PlayerCallback;
import com.prueba.myapplication.controller.activities.master_detail.PlayerDetailActivity;
import com.prueba.myapplication.controller.activities.master_detail.PlayerDetailFragment;
import com.prueba.myapplication.controller.activities.master_detail.UserCallBack;
import com.prueba.myapplication.controller.managers.PlayerManager;
import com.prueba.myapplication.controller.managers.UserManager;
import com.prueba.myapplication.model.Apuesta;
import com.prueba.myapplication.model.ApuestaRealizada;
import com.prueba.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link soccerOdds.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link soccerOdds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class topApuestaDetail extends Fragment implements PlayerCallback, UserCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private List<Apuesta> apuestas,bet;
    private List<Apuesta> gameOdds = new ArrayList<Apuesta>();
    private ArrayList<Apuesta> apuestaTicket = new ArrayList<>();
    private double totalCuota=1;
    private ApuestaRealizada a;
    public TextView balanceuser,ticketOdd,ticketOddxMoneyPut;
    public ImageButton placeOnUserBet;
    public EditText money;
    public ListView ticket;
    public Button placeBetButton;
    private ListView topApuestasLista,ticketLista;



    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment soccerOdds.
     */
    // TODO: Rename and change types and number of parameters
    public static soccerOdds newInstance(String param1, String param2) {
        soccerOdds fragment = new soccerOdds();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public topApuestaDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Bundle b = getActivity().getIntent().getExtras();
            a = (ApuestaRealizada) bundle.getSerializable("apuesta");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (MainActivity.userInfos==null){
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);

        }
        final View rootView = inflater.inflate(R.layout.fragment_top_apuesta_detail, container, false);
        balanceuser = (TextView) rootView.findViewById(R.id.balanceU);
        ticketOdd = (TextView) rootView.findViewById(R.id.totalOdd);
        ticketOddxMoneyPut = (TextView) rootView.findViewById(R.id.gananciaMoneyXodD);
        ticket = (ListView) rootView.findViewById(R.id.ticketLista);
        placeOnUserBet = (ImageButton) rootView.findViewById(R.id.betOnTicket);
        money = (EditText) rootView.findViewById(R.id.amountToBet);
        ticketLista = (ListView) rootView.findViewById(R.id.ticketLista);
        final String[] apuestasSelec = a.getGanadorApuesta().split(",");
        if(apuestasSelec.length!=1) {

            apuestaTicket.clear();
            totalCuota = 1;
            for (int i = 0; i < apuestasSelec.length; i = i + 2) {

                Apuesta apuestaSekected = new Apuesta();
                apuestaSekected.setApuestaName(apuestasSelec[i]);
                apuestaSekected.setaApostarName(apuestasSelec[i + 1]);
                apuestaTicket.add(apuestaSekected);


                if (i + 2 == apuestasSelec.length) {
                    break;
                }

            }
            ticketLista.setAdapter(new ContactAdapter1(getContext(), apuestaTicket));
            totalCuota = Double.parseDouble(String.valueOf(a.getCuota()));




            balanceuser.setText(MainActivity.userInfos.getSaldo().toString());
           ticketOdd.setText("Total Odd : " + MainActivity.odd2Decimals(totalCuota));

            money.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!money.getText().toString().matches("")) {
                        double totalOddBet = MainActivity.odd2Decimals(totalCuota) * Double.parseDouble(String.valueOf(money.getText()));
                        ticketOddxMoneyPut.setText("To win : " + String.valueOf(totalOddBet));
                    } else {

                        ticketOddxMoneyPut.setText("To win : 0.0");

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            placeBetButton = (Button) rootView.findViewById(R.id.placeBet);
            placeBetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (money.getText().toString().matches("")){
                        Toast toast1 =
                                Toast.makeText(getActivity(),
                                        "No bets without iCoins!", Toast.LENGTH_SHORT);

                        toast1.show();
                    }
                    else {

                        Double saldoActual = MainActivity.userInfos.getSaldo().intValue() - Double.valueOf(String.valueOf(money.getText()));
                        UserManager.getInstance(v.getContext()).modificarSaldoUser(topApuestaDetail.this, saldoActual);
                        ApuestaRealizada apuestaRealizada = new ApuestaRealizada();
                        apuestaRealizada.setCantidadApostada(Double.valueOf(String.valueOf(money.getText())));
                        apuestaRealizada.setCuota(MainActivity.odd2Decimals(totalCuota));

                        apuestaRealizada.setEventoApostado(a.getEventoApostado());
                        apuestaRealizada.setGanadorApuesta(a.getGanadorApuesta());
                        apuestaRealizada.setEstado(false);
                        PlayerManager.getInstance(v.getContext()).createApuesta(topApuestaDetail.this, apuestaRealizada);
                        Intent i = new Intent(v.getContext(), MainActivity.class);
                        startActivity(i);

                    }
                }
            });
        }


            ImageButton placeOnUserBet = (ImageButton) rootView.findViewById(R.id.betOnTicket);

         placeOnUserBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout ponPastaOnTicket = (RelativeLayout) rootView.findViewById(R.id.betOnUserTicket);



                if(ponPastaOnTicket.getVisibility() == View.VISIBLE){
                    ponPastaOnTicket.setVisibility(View.INVISIBLE);
                }else{
                    ponPastaOnTicket.setVisibility(View.VISIBLE);
                }
            }
        });


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

       // PlayerManager.getInstance(getActivity().getApplicationContext()).getApuestasByleagueName(tennisOdds.this, nombreLeague);
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


        setupRecyclerView(recyclerView);
    }

    @Override
    public void onSuccessTop(List<ApuestaRealizada> apuestaList) {

    }

    @Override
    public void onSuccess1(List<Apuesta> apuestaList) {




    }

    @Override
    public void onSuccess(User userInfo) {

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

            holder.mItem = mValues.get(position);






        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }



        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;


            public Apuesta mItem;

            public ViewHolder(final View view) {
                super(view);
                mView = view;

            }

            @Override
            public String toString() {
                return super.toString() + " '" + balanceuser.getText() + "'";
            }
        }

    }  private class ViewInfo1 {
        TextView ganador,nombreApuesta,cuota;
        ImageButton removeEvent;

        public ViewInfo1(View view) {

            ganador = (TextView) view.findViewById(R.id.selectedWinner);
            nombreApuesta = (TextView) view.findViewById(R.id.eventNameBet);
            cuota = (TextView) view.findViewById(R.id.oddTicket);
            removeEvent = (ImageButton) view.findViewById(R.id.removeEvent);


        }

        public void setContact(Apuesta apuesta) {



            ganador.setText(apuesta.getaApostarName().toString());
            nombreApuesta.setText(apuesta.getApuestaName().toString());

        }
    }

    private class ContactAdapter1 extends BaseAdapter implements View.OnClickListener {
        private Context context;
        private ArrayList<Apuesta> contacts;

        public ContactAdapter1(Context context, ArrayList<Apuesta> contacts) {
            this.context = context;
            this.contacts = contacts;
        }

        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return contacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.ticket_details, parent, false);
                ViewInfo1 viewInfo = new ViewInfo1(view);
                view.setTag(viewInfo);

            }
            ViewInfo1 viewInfo = (ViewInfo1) view.getTag();
            Apuesta contact = contacts.get(position);
            viewInfo.setContact(contact);

            return view;
        }


        @Override
        public void onClick(View v) {

        }
    }


    public static double odd2Decimals(Double totalOdds){
        totalOdds = totalOdds*100;
        int integer = totalOdds.intValue();
        totalOdds = (double) integer/100;
        return totalOdds;

    }}
