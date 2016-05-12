package com.prueba.myapplication.controller.activities.master_detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prueba.myapplication.BlankFragment;
import com.prueba.myapplication.BlankFragment1;
import com.prueba.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Soccer_Bets.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Soccer_Bets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Soccer_Bets extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Leagues> listLeage = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Soccer_Bets.
     */
    // TODO: Rename and change types and number of parameters
    public static Soccer_Bets newInstance(String param1, String param2) {
        Soccer_Bets fragment = new Soccer_Bets();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Soccer_Bets() {
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
        rootView = inflater.inflate(R.layout.fragment_soccer__bets, container, false);

        ListView leagues = (ListView) rootView.findViewById(R.id.listView);

            Leagues spain = new Leagues("Spanish La Liga Primera", "es");
            Leagues france = new Leagues("French Ligue 1", "fr");
            Leagues england = new Leagues("English Premier League", "england");
            Leagues german = new Leagues("German Bundesliga", "de");
            Leagues italy = new Leagues("Italian Serie A", "it");
            Leagues holand = new Leagues("Dutch Eredivisie", "nl");
            Leagues bulgaria = new Leagues("Bulgarian APFG", "bg");
            Leagues championsLigaEuropa = new Leagues("UEFA Champions League", "euro");
            Leagues euroLigaEuropa = new Leagues("UEFA Europa League", "euro");
            listLeage.add(spain);
            listLeage.add(england);
            listLeage.add(france);
            listLeage.add(german);
            listLeage.add(italy);
            listLeage.add(holand);
            listLeage.add(bulgaria);
            listLeage.add(championsLigaEuropa);
            listLeage.add(euroLigaEuropa);
        leagues.setAdapter(new ContactAdapter(rootView.getContext(), listLeage));
        leagues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("liga", listLeage.get(position).nameLeage.toString());
                Fragment fragment = new BlankFragment1();
                fragment.setArguments(bundle);

                listLeage.clear();

                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, fragment)
                        .addToBackStack("tag")
                        .commit();
            }
        });


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
    public void onResume() {
        super.onResume();


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


    private class Leagues {
        public String nameLeage;
        public String img;

        public Leagues(String nameLeage, String img) {
            this.nameLeage = nameLeage;
            this.img = img;
        }
    }


    private class ViewInfo {
        TextView nombreLeage;
        ImageView img;
        Leagues contact;

        public ViewInfo(View view) {
            nombreLeage = (TextView) view.findViewById(R.id.leageName);
            img = (ImageView) view.findViewById(R.id.banderaLeage);

        }

        public void setContact(Leagues contact) {
            this.contact = contact;
            nombreLeage.setText(contact.nameLeage);
            img.setImageResource(getResources().getIdentifier(contact.img, "drawable", rootView.getContext().getPackageName()));

        }
    }

    private class ContactAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Leagues> contacts;

        public ContactAdapter(Context context, ArrayList<Leagues> contacts) {
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
                view = inflater.inflate(R.layout.leagues, parent, false);
                ViewInfo viewInfo = new ViewInfo(view);
                view.setTag(viewInfo);
            }
            ViewInfo viewInfo = (ViewInfo) view.getTag();
            Leagues contact = contacts.get(position);
            viewInfo.setContact(contact);

            return view;
        }


    }

}
