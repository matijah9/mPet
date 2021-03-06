package Retrofit.DataPost;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Retrofit.RemotePost.StatusListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.RemotePost.KorisnikService;
public class RegistracijaMethod {

    public static StatusListener listener;

    public RegistracijaMethod(StatusListener statusListener){
        this.listener=statusListener;
    }

    /**
     * kreiranje novog korisnika na poslužitelju
     * @param ime
     * @param prezime
     * @param korIme
     * @param adresa
     * @param email
     * @param mobitel
     * @param telefon
     * @param lozinka
     * @param slika
     */
    public static void Upload(String ime, String prezime, String korIme, String adresa, String email, String mobitel, String telefon, String lozinka, String slika) {

        final RegistracijaResponse body = new RegistracijaResponse();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://airprojekt.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        KorisnikService api = retrofit.create(KorisnikService.class);

        RegistracijaRequest request = new RegistracijaRequest();

        request.setAdresa(adresa);

        request.setBroj_mobitela(mobitel);

        request.setEmail(email);

        request.setIme(ime);

        request.setPrezime(prezime);

        request.setKorisnicko_ime(korIme);

        request.setBroj_telefona(telefon);

        request.setUrl_profilna("/");

        request.setLozinka(lozinka);

        request.setSlika(slika);

        Call<RegistracijaResponse> KorisnikResponseCall = api.createKorisnik(request,"https://airprojekt.000webhostapp.com/registracija.php");

        KorisnikResponseCall.enqueue(new Callback<RegistracijaResponse>() {
            @Override
            public void onResponse(Call<RegistracijaResponse> call, Response<RegistracijaResponse> response) {

                RegistracijaResponse resp=response.body();
                body.id= resp.id;
                listener.onStatusChanged(body.id);

            }

            @Override
            public void onFailure(Call<RegistracijaResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    /**
     * ažuriranje korisničkog računa
     * @param id
     * @param ime
     * @param prezime
     * @param korIme
     * @param adresa
     * @param email
     * @param mobitel
     * @param telefon
     * @param slika
     */
    public static void Update(String id, String ime, String prezime, String korIme, String adresa, String email, String mobitel, String telefon, String slika) {

        final RegistracijaResponse body = new RegistracijaResponse();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://airprojekt.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        KorisnikService api = retrofit.create(KorisnikService.class);

        RegistracijaRequest request = new RegistracijaRequest();

        request.setId(id);
        request.setAdresa(adresa);
        request.setBroj_mobitela(mobitel);
        request.setEmail(email);
        request.setIme(ime);
        request.setPrezime(prezime);
        request.setKorisnicko_ime(korIme);
        request.setBroj_telefona(telefon);
        request.setUrl_profilna("/");
        request.setSlika(slika);

        Call<RegistracijaResponse> KorisnikResponseCall = api.createKorisnik(request,"https://airprojekt.000webhostapp.com/updateKorisnik.php");

        KorisnikResponseCall.enqueue(new Callback<RegistracijaResponse>() {
            @Override
            public void onResponse(Call<RegistracijaResponse> call, Response<RegistracijaResponse> response) {

                RegistracijaResponse resp=response.body();
                body.id= resp.id;
                listener.onStatusChanged(body.id);

            }

            @Override
            public void onFailure(Call<RegistracijaResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}

