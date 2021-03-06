package com.prueba.myapplication.controller.activities.main;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.tech.NfcBarcode;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prueba.myapplication.baseballOdds;
import com.prueba.myapplication.controller.activities.login.LoginActivity;
import com.prueba.myapplication.controller.activities.master_detail.Baseball_Bets;
import com.prueba.myapplication.controller.activities.master_detail.Hockey_Bets;
import com.prueba.myapplication.hockeyOdds;
import com.prueba.myapplication.topApuestaDetail;
import com.prueba.myapplication.userAccount;
import com.prueba.myapplication.soccerOdds;
import com.prueba.myapplication.R;
import com.prueba.myapplication.basketOdds;
import com.prueba.myapplication.controller.activities.master_detail.Basket_Bets;
import com.prueba.myapplication.controller.activities.master_detail.PlayerCallback;
import com.prueba.myapplication.controller.activities.master_detail.Soccer_Bets;
import com.prueba.myapplication.controller.activities.master_detail.Tennis_Bets;
import com.prueba.myapplication.controller.activities.master_detail.UserCallBack;
import com.prueba.myapplication.controller.managers.PlayerManager;
import com.prueba.myapplication.controller.managers.UserLoginManager;
import com.prueba.myapplication.controller.managers.UserManager;
import com.prueba.myapplication.model.Apuesta;
import com.prueba.myapplication.model.ApuestaRealizada;
import com.prueba.myapplication.model.User;
import com.prueba.myapplication.model.UserToken;
import com.prueba.myapplication.tennisOdds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, userAccount.OnFragmentInteractionListener, soccerOdds.OnFragmentInteractionListener, UserCallBack, Soccer_Bets.OnFragmentInteractionListener, Basket_Bets.OnFragmentInteractionListener,
        basketOdds.OnFragmentInteractionListener, PlayerCallback, Tennis_Bets.OnFragmentInteractionListener,
        tennisOdds.OnFragmentInteractionListener, Hockey_Bets.OnFragmentInteractionListener,
        hockeyOdds.OnFragmentInteractionListener, Baseball_Bets.OnFragmentInteractionListener, baseballOdds.OnFragmentInteractionListener,
        topApuestaDetail.OnFragmentInteractionListener{

    public static User userInfos;
    private TextView totalOdds;
    private TextView balanceU;
    private TextView totalWinning;
    private TextView refreshToken;
    private TextView expiresIn;
    private TextView scope;
    private Button placeBetButton;
    private EditText pastaEdit;
    private ListView topApuestasLista,ticketLista;
    private String username;
    private LinearLayout butonees;
    private ImageButton b1, b2,closeDialogTicket,deleteEvent;
    private List<ApuestaRealizada> topApuestas;
    private Integer topPosicion = 1;
    public static Menu menu;
    private static String ticket = "";
    private ArrayList<Apuesta> apuestaTicket = new ArrayList<>();
    private double totalCuota=1;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topPosicion = 1;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PlayerManager.getInstance(this).getTopApuestas(MainActivity.this);

        topApuestasLista = (ListView) findViewById(R.id.topGamesList);

        topApuestasLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(userInfos != null) {
                    ApuestaRealizada apSelected;
                    apSelected = topApuestas.get(position);


                    Fragment fragment = new topApuestaDetail();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("apuesta", apSelected);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_main, fragment)
                            .commit();

                }
                else{
                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "LogIn please", Toast.LENGTH_SHORT);

                    toast2.show();
                }
            }
        });
        if (userInfos != null) {
            UserManager.getInstance(MainActivity.this).getUserInfo(MainActivity.this, MainActivity.userInfos.getLogin().toString());
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butonees = (LinearLayout) findViewById(R.id.butones);

                if (butonees.getVisibility() == View.VISIBLE) {
                    butonees.setVisibility(View.INVISIBLE);
                } else {
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
                        if(userInfos!=null) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_main, new userAccount())
                                    .commit();

                        }else{

                            Toast toast2 =
                                    Toast.makeText(getApplicationContext(),
                                            "LogIn please", Toast.LENGTH_SHORT);

                            toast2.show();

                        }

                    }
                });
                b2 = (ImageButton) findViewById(R.id.imageButton2);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setBackgroundColor(Color.rgb(245, 245, 245));
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        topPosicion = 1;
        username = getIntent().getStringExtra("userName");
        UserManager.getInstance(this.getApplicationContext()).getUserInfo(MainActivity.this, username);
        UserToken userToken = UserLoginManager.getInstance(this.getApplicationContext()).getUserToken();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        this.menu = menu;
        MenuItem cupon = menu.findItem(R.id.cupon);
        String[] apuestaSel = ticket.split(",");
        if (apuestaSel != null) {
            cupon.setTitle("Ticket (" + (apuestaSel.length / 3) + ")");


        } else {
            cupon.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       /* if (id == R.id.login) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }
        else if (id == R.id.logout) {
            finish();
            return true;
        }
        else*/ if (id == R.id.cupon) {



            final String[] apuestasSelec = ticket.split(",");
            if(apuestasSelec.length!=1) {
                apuestaTicket.clear();
                totalCuota=1;
                for (int i = 0; i < apuestasSelec.length; i = i + 3) {

                    Apuesta apuestaSekected = new Apuesta();
                    apuestaSekected.setApuestaName(apuestasSelec[i]);
                    apuestaSekected.setaApostarName(apuestasSelec[i + 1]);
                    apuestaSekected.setaApostarOdd(Double.parseDouble(apuestasSelec[i + 2]));
                    apuestaTicket.add(apuestaSekected);

                    totalCuota = totalCuota*Double.parseDouble(apuestasSelec[i+2]);
                    if (i + 2 == apuestasSelec.length) {
                        break;
                    }

                }

                dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Ticket");
                dialog.setContentView(R.layout.ticket_layout);


                dialog.show();

                ticketLista = (ListView) dialog.findViewById(R.id.ticketLista);
                ticketLista.setAdapter(new ContactAdapter1(getApplicationContext(), apuestaTicket));
                totalOdds = (TextView) dialog.findViewById(R.id.totalOdd);
                totalWinning = (TextView) dialog.findViewById(R.id.gananciaMoneyXodD);
                balanceU = (TextView) dialog.findViewById(R.id.balanceU);
                pastaEdit = (EditText) dialog.findViewById(R.id.amountToBet);
                balanceU.setText(userInfos.getSaldo().toString());
                totalOdds.setText("Total Odd : " + odd2Decimals(totalCuota));

                pastaEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!pastaEdit.getText().toString().matches("")) {
                            double totalOddBet = odd2Decimals(totalCuota) * Double.parseDouble(String.valueOf(pastaEdit.getText()));
                            totalWinning.setText("To win : " + String.valueOf(totalOddBet));
                        } else {

                            totalWinning.setText("To win : 0.0");

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                placeBetButton = (Button) dialog.findViewById(R.id.placeBet);
                placeBetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pastaEdit.getText().equals("")){
                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            "No bets without iCoins!", Toast.LENGTH_SHORT);

                            toast1.show();
                        }
                        else {
                            ticket = "";
                            for (int i = 0; i < apuestasSelec.length - 1; i = i + 3) {
                                ticket = ticket + apuestasSelec[i] + ",";
                                ticket = ticket + apuestasSelec[i + 1] + ",";
                                if (i + 1 == apuestasSelec.length) {
                                    break;
                                }
                            }

                            Double saldoActual = userInfos.getSaldo().intValue() - Double.valueOf(String.valueOf(pastaEdit.getText()));
                            UserManager.getInstance(v.getContext()).modificarSaldoUser(MainActivity.this, saldoActual);
                            ApuestaRealizada apuestaRealizada = new ApuestaRealizada();
                            apuestaRealizada.setCantidadApostada(Double.valueOf(String.valueOf(pastaEdit.getText())));
                            apuestaRealizada.setCuota(odd2Decimals(totalCuota));
                            if (ticket.split(",").length == 2){
                                apuestaRealizada.setEventoApostado(ticket.split(",")[0]);
                            }else{
                                apuestaRealizada.setEventoApostado("Ticket with " +ticket.split(",").length/2 +" bets.");
                            }

                            apuestaRealizada.setGanadorApuesta(ticket);
                            apuestaRealizada.setEstado(false);
                            PlayerManager.getInstance(v.getContext()).createApuesta(MainActivity.this, apuestaRealizada);
                            ticket = "";
                            MenuItem cupon = menu.findItem(R.id.cupon);
                            cupon.setTitle("Ticket (0)");
                            dialog.dismiss();

                        }
                    }
                });
                closeDialogTicket = (ImageButton) dialog.findViewById(R.id.closeTicket);
                closeDialogTicket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }else{

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "No bets on the ticket!", Toast.LENGTH_SHORT);

                toast1.show();

            }














            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        boolean FragmentTransaction = false;
        int id = item.getItemId();

        if (id == R.id.soccer) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new Soccer_Bets())
                    .commit();
        } else if (id == R.id.basketball) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new Basket_Bets())
                    .commit();

        } else if (id == R.id.tenis) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new Tennis_Bets())
                    .commit();

        } else if (id == R.id.hokey) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new Hockey_Bets())
                    .commit();
        } else if (id == R.id.baseball) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, new Baseball_Bets())
                    .commit();

        } else if (id == R.id.footballA) {

        }

        //Logica para remplazar el fragment
        if (FragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .addToBackStack("tag")

                    .commit();
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSuccess(User userInfo) {
        userInfos = userInfo;
        if (userInfos.getSaldo() == null) {
            UserManager.getInstance(MainActivity.this).modificarSaldoUser(MainActivity.this, 200.0);

        }

    }

    @Override
    public void onSuccess(List<Apuesta> apuestaList) {

    }

    @Override
    public void onSuccessTop(List<ApuestaRealizada> apuestaList) {

        topApuestas = apuestaList;
        topPosicion = 1;
        topApuestasLista.setAdapter(new ContactAdapter(this, topApuestas));

    }

    @Override
    public void onSuccess1(List<Apuesta> apuestaList) {

    }

    @Override
    public void onFailure(Throwable t) {

    }



    private class ViewInfo {
        TextView nombreApuesta, topNumber;
        ApuestaRealizada contact;

        public ViewInfo(View view) {
            nombreApuesta = (TextView) view.findViewById(R.id.topGame);
            topNumber = (TextView) view.findViewById(R.id.top);

        }

        public void setContact(ApuestaRealizada contact) {
            this.contact = contact;
            nombreApuesta.setText(contact.getEventoApostado());

            topNumber.setText("Top" + topPosicion);
            topPosicion++;

        }
    }

    private class ContactAdapter extends BaseAdapter {
        private Context context;
        private List<ApuestaRealizada> contacts;

        public ContactAdapter(Context context, List<ApuestaRealizada> contacts) {
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
                view = inflater.inflate(R.layout.top_10_apuestas, parent, false);
                ViewInfo viewInfo = new ViewInfo(view);
                view.setTag(viewInfo);
            }
            ViewInfo viewInfo = (ViewInfo) view.getTag();
            ApuestaRealizada contact = contacts.get(position);
            viewInfo.setContact(contact);

            return view;
        }


    }


    private class ViewInfo1 {
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
            cuota.setText(apuesta.getaApostarOdd().toString());



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
                deleteEvent = (ImageButton) view.findViewById(R.id.removeEvent);

            }
            ViewInfo1 viewInfo = (ViewInfo1) view.getTag();
            Apuesta contact = contacts.get(position);
            viewInfo.setContact(contact);
            deleteEvent.setTag(contact);
            deleteEvent.setOnClickListener(this);

            return view;
        }


        @Override
        public void onClick(View v) {
            Apuesta ap = (Apuesta) v.getTag();
            totalCuota = totalCuota / ap.getaApostarOdd();
            apuestaTicket.remove(v.getTag());
            ticket = "";
            for (int i = 0; i<apuestaTicket.size();i++){
                ticket=ticket+apuestaTicket.get(i).getApuestaName()+",";
                ticket=ticket+apuestaTicket.get(i).getaApostarName()+",";
                ticket=ticket+apuestaTicket.get(i).getaApostarOdd()+",";
            }if (totalCuota<=1.0){
               dialog.dismiss();
            }
            MenuItem cupon = menu.findItem(R.id.cupon);
            cupon.setTitle("Ticket (" + (apuestaTicket.size()) + ")");

            totalOdds.setText("Total Odd : "+ odd2Decimals(totalCuota));
            ticketLista.setAdapter(new ContactAdapter1(getApplicationContext(), apuestaTicket));
        }
    }



    public static void setTicket(String bet) {
        ticket = ticket + bet;
    }

    public static String getTicket() {
        return ticket;
    }

    public static double odd2Decimals(Double totalOdds){
        totalOdds = totalOdds*100;
        int integer = totalOdds.intValue();
        totalOdds = (double) integer/100;
        return totalOdds;

    }






    /*@Override
    public void onClick(View v) {


       ApuestaRealizada apSelected;
        apSelected = topApuestas.get(topApuestasLista.getSelectedItemPosition());

        Bundle bundle = new Bundle();
        bundle.putSerializable("apuestaSelected", apSelected);
        Fragment fragment = new topApuestaDetail();
        fragment.setArguments(bundle);


    }*/



}
