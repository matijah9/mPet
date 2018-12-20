package mpet.project2018.air.mpet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import mpet.project2018.air.mpet.Config;
import mpet.project2018.air.mpet.R;

import static mpet.project2018.air.mpet.Config.SHARED_PREF_NAME;

public class KorisnikUredivanje extends Fragment {
    private KorisnikUredivanje.OnFragmentInteractionListener mListener;

    public KorisnikUredivanje() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.update, container, false);

        if (mListener != null) {
            mListener.onFragmentInteraction("Korisnički podaci");
        }


        //buttonSpremi.setOnClickListener(new View.OnClickListener() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, 0);
        String id1 = sharedPreferences.getString(Config.ID_SHARED_PREF, "").toString();
        Toast.makeText(getActivity(),"Vas id je"+id1, Toast.LENGTH_SHORT).show();



        EditText korImeS = (EditText) view.findViewById(R.id.unosKorImeU);
        korImeS.setText(id1);
        //String  korImeU= korImeS.getText().toString();

        /*if(TextUtils.isEmpty(ime)||TextUtils.isEmpty(prezime)||TextUtils.isEmpty(mail)||lozinka.length()<3||korIme.length()<3){
            Toast.makeText(getActivity(), "Provjerite polja!",
                    Toast.LENGTH_LONG).show();
        }
        else{
            UpdateMethod.Upload(ime, prezime, korIme, adresa, mail, mobitel, telefon, lozinka);

        }*/






        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof KorisnikUredivanje.OnFragmentInteractionListener) {
            mListener = (KorisnikUredivanje.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String title);
    }
    private class ArticleFragment {
    }
}