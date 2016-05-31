package com.prueba.myapplication.controller.activities.main;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.tech.NfcBarcode;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.prueba.myapplication.baseballOdds;
import com.prueba.myapplication.controller.activities.master_detail.Baseball_Bets;
import com.prueba.myapplication.controller.activities.master_detail.Hockey_Bets;
import com.prueba.myapplication.hockeyOdds;
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
        hockeyOdds.OnFragmentInteractionListener, Baseball_Bets.OnFragmentInteractionListener, baseballOdds.OnFragmentInteractionListener {

    public static User userInfos;
    private TextView accessToken;
    private TextView saldo;
    private TextView grantType;
    private TextView refreshToken;
    private TextView expiresIn;
    private TextView scope;
    private Button button;
    private ListView topApuestasLista,ticketLista;
    private String username;
    private LinearLayout butonees;
    private ImageButton b1, b2;
    private List<ApuestaRealizada> topApuestas;
    private Integer topPosicion = 1;
    public static Menu menu;
    private static String ticket = "";
    ArrayList<Apuesta> apuestaTicket = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PlayerManager.getInstance(this).getTopApuestas(MainActivity.this);

        topApuestasLista = (ListView) findViewById(R.id.topGamesList);
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
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_main, new userAccount())
                                .commit();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.cupon) {



            String[] apuestasSelec = ticket.split(",");

            for(int i = 0;i<apuestasSelec.length; i=i+3){

                Apuesta apuestaSekected = new Apuesta();
                apuestaSekected.setaApostarName(apuestasSelec[i]);
                apuestaSekected.setApuestaName(apuestasSelec[i + 1]);
                apuestaSekected.setaApostarOdd(Double.parseDouble(apuestasSelec[i+2]));
                apuestaTicket.add(apuestaSekected);
                if (i+2==apuestasSelec.length){
                    break;
                }

            }







            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setTitle("Ticket");
            dialog.setContentView(R.layout.ticket_layout);


            dialog.show();

            ticketLista = (ListView) dialog.findViewById(R.id.ticketLista);
            ticketLista.setAdapter(new ContactAdapter1(getApplicationContext(), apuestaTicket));










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
        topApuestasLista.setAdapter(new ContactAdapter(this, topApuestas));
        topPosicion = 1;
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

    private class ContactAdapter1 extends BaseAdapter {
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


    }



    public static void setTicket(String bet) {
        ticket = ticket + bet;
    }

    public static String getTicket() {
        return ticket;
    }
}
