package mpet.project2018.air.mpet.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import Retrofit.DataGetListenersAndLoaders.DataLoadedListeners.KarticaDataLoadedListener;
import Retrofit.DataGetListenersAndLoaders.DataLoadedListeners.KorisnikDataLoadedListener;
import Retrofit.DataGetListenersAndLoaders.DataLoadedListeners.LjubimacDataLoadedListener;
import Retrofit.DataGetListenersAndLoaders.DataLoadedListeners.SkeniranjeDataLoadedListener;
import Retrofit.DataGetListenersAndLoaders.DataLoaders.KarticaDataLoader;
import Retrofit.DataGetListenersAndLoaders.DataLoaders.KorisnikDataLoader;
import Retrofit.DataGetListenersAndLoaders.DataLoaders.LjubimacDataLoader;
import Retrofit.DataGetListenersAndLoaders.DataLoaders.SkeniranjeDataLoader;
import Retrofit.DataPost.PrijavaMethod;
import Retrofit.Model.Kartica;
import Retrofit.Model.Korisnik;
import Retrofit.Model.Ljubimac;
import Retrofit.Model.Skeniranje;
import Retrofit.RemotePost.onLoginValidation;
import mpet.project2018.air.core.InternetConnectionHandler;
import mpet.project2018.air.core.OnFragmentInteractionListener;
import mpet.project2018.air.mpet.Config;
import mpet.project2018.air.mpet.MainActivity;
import mpet.project2018.air.mpet.R;

import static android.content.Context.MODE_PRIVATE;
import static mpet.project2018.air.mpet.Config.SHARED_PREF_NAME;

public class Login extends Fragment implements onLoginValidation, KorisnikDataLoadedListener, KarticaDataLoadedListener, LjubimacDataLoadedListener, SkeniranjeDataLoadedListener {
    private OnFragmentInteractionListener mListener;
    public Login() {}

    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    Button btnPrijavaOdustani;

    private String globalId;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_login, container, false);
        if (mListener != null) {
            mListener.onFragmentInteraction("Prijava");
        }
        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnPrijavaOdustani = view.findViewById(R.id.btnPrijavaOdustani);

        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("MyPref", 0);
        if ( Objects.requireNonNull(sharedPreferences.getString("ulogiraniKorisnikId", "")).equals("ulogiraniKorisnikId")) { //getString
            HomeLoggedIn frag;
            frag = new HomeLoggedIn();
            mListener.swapFragment(false,frag);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if ( InternetConnectionHandler.isOnline(getActivity()) ) {

                                                String username = edtUsername.getText().toString();
                                                String password = edtPassword.getText().toString();
                                                if ( validateLogin(username, password) ) {
                                                    doLogin(username, password);
                                                    showLoadingDialog();
                                                }
                                            }
                                            else
                                                Toast.makeText(getActivity(), mpet.project2018.air.core.R.string.internetNotAvailable, Toast.LENGTH_SHORT).show();

                                        }
                                    }
        );
        btnPrijavaOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeLoggedOut frag;
                frag = new HomeLoggedOut();
                mListener.swapFragment(true,frag);
            }
            }
        );
        return view;
    }
       public void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(getActivity());
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setProgressNumberFormat(null);
            progress.setProgressPercentFormat(null);
            progress.setMessage("Molimo pričekajte...");
            progress.setCancelable(false);
            progress.setButton("Odustani",(DialogInterface.OnClickListener)null);
        }
        progress.show();
    }

    public void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
    /**/
    private boolean validateLogin (String username, String password){
        if (username == null || username.trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.potrebno_korime, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            Toast.makeText(getActivity(), R.string.potrebna_lozinka, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void doLogin ( final String username, final String password){
        String username1 = edtUsername.getText().toString();
        String password1 = edtPassword.getText().toString();
        PrijavaMethod postMetodaZaPrijavu = new PrijavaMethod(this);
        String id = "";
        String response = "";
        postMetodaZaPrijavu.Upload(username1, password1);
    }
    @Override
    public void onDataLoaded (String id){
        globalId=id;
        if (Integer.parseInt(id) != 0) {
            downloadDatabase(id);
            /*zamjena izbornika*/
            NavigationView navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.nav_view);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            navigationView.getHeaderView(0);
            navigationView.removeHeaderView(navigationView.getHeaderView(0));
            navigationView.inflateHeaderView(R.layout.nav_header);

            /**/
            progressDialogEdit(15,"Vaši korisnički podaci su u redu!");
        } else {
            Toast.makeText(getActivity(), R.string.kor_podaci_krivi, Toast.LENGTH_SHORT).show();
            dismissLoadingDialog();
        }
    }
    private void downloadDatabase(String id){
        KorisnikDataLoader kor=new KorisnikDataLoader(this);
        kor.loadUsersByUserId(id);
        //nastavak skidanja baze u loaderima zbog zavisnosti
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
   @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void KarticaOnDataLoaded(List<Kartica> listaKartica) {
        LjubimacDataLoader ljub=new LjubimacDataLoader(this);
        ljub.loadDataByUserId(globalId);
        progressDialogEdit(65,"Svi vaši NFC tagovi upravo su skinuti!");
    }
    @Override
    public void KorisnikOnDataLoaded(List<Korisnik> listaKorisnika) {
        KarticaDataLoader kar=new KarticaDataLoader(this);
        kar.loadDataByuserId(globalId);
        progressDialogEdit(45,"Vaši podaci su na sigurnome!");
    }
    @Override
    public void LjubimacOnDataLoaded(List<Ljubimac> listaLjubimaca) {
        SkeniranjeDataLoader sken=new SkeniranjeDataLoader(this);
        sken.loadDataByUserId(globalId);
        progressDialogEdit(90,"Ljubimci su u kućicama!");
    }
    @Override
    public void SkeniranjeOnDataLoaded(List<Skeniranje> listaSkeniranja) {
        Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE)
                .edit()
                .putString(Config.ID_SHARED_PREF,globalId)
                .apply();
        HomeLoggedIn frag;
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        frag = new HomeLoggedIn();

        ((MainActivity)getActivity()).changeHeaderData();
        ((MainActivity)getActivity()).startService();

        mListener.swapFragment(false,frag);

        progressDialogEdit(100,"Pozdrav!");
        dismissLoadingDialog();
    }

    private void progressDialogEdit(int progressNum, String message)
    {
        progress.setProgress(progressNum);
        progress.setMessage(message);
    }
}
