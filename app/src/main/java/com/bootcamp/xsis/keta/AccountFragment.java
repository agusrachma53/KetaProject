package com.bootcamp.xsis.keta;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bootcamp.xsis.keta.DatabaseHelper.QueryHelper;
import com.bootcamp.xsis.keta.DatabaseHelper.SQLiteDbHelper;

import static java.lang.Integer.parseInt;


public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    SessionManager session;
    SQLiteDbHelper dbHelper;
    QueryHelper queryHelper;
    Context context;
    Cursor cursor , cursorId;
    TextView a_nama, a_phone, a_pass, a_IdUser;
    Button b_update;
    private int id_user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        session = new SessionManager(getContext());
        dbHelper = new SQLiteDbHelper(getContext());
        queryHelper = new QueryHelper(dbHelper);

        String phone = session.phone();
        String pass = session.pass();

        cursor = queryHelper.login(phone, pass);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            String Id = cursor.getString(0);
            int IdUser = parseInt(Id);
            cursorId = queryHelper.loginId(IdUser);

            a_nama = (TextView) view.findViewById(R.id.a_nama);
            a_phone = (TextView) view.findViewById(R.id.a_phone);
            a_pass = (TextView) view.findViewById(R.id.a_pass);
            a_IdUser = (TextView) view.findViewById(R.id.a_IdUser);
            a_IdUser.setVisibility(View.GONE);

            if (cursorId.getCount() >0) {
                cursorId.moveToPosition(0);
                a_IdUser.setText(cursorId.getString(0));
                a_nama.setText(cursorId.getString(1));
                a_phone.setText(cursorId.getString(2));
                a_pass.setText(cursorId.getString(3));
            }
            String idUser = a_IdUser.getText().toString();
            id_user = parseInt(idUser);
        }

        b_update = (Button)view.findViewById(R.id.b_update);
        b_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AccountUpdate.class);
                //send id to update
                i.putExtra("id", id_user);
                startActivity(i);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
