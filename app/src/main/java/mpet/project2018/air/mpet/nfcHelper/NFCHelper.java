package mpet.project2018.air.mpet.nfcHelper;

import java.util.List;

import Retrofit.DataGet.LjubimacData;
import Retrofit.Model.Ljubimac;

public class NFCHelper
{
    // Metoda koja provjerava da li je uneseni ili skenirani kod u odgovvarajućem formatu
    public static boolean checkFormat(String code )
    {
        String tagContent = code;
        if(tagContent.length()==10 && tagContent.matches(("[A-Za-z0-9]+")))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*public static boolean checkForCodeInDatabase(String code)
    {
        LjubimacData instancaMetodeZaDohvatPodataka=new LjubimacData();
        List<Ljubimac> skeniraniLjubimac=instancaMetodeZaDohvatPodataka.Download(code);

    }*/



}
