import com.sun.source.tree.Tree;

import java.util.*;

public class KsiazkaTelefoniczna {
    public static void main(String[] args) {
        TreeMap<NrTelefoniczny, Wpis> ksiazka = new TreeMap<>();
        ksiazka.put(new NrTelefoniczny("+48", "123456789"), new Osoba("Jan", "Kowalski", "ul. Warszawska 1", new NrTelefoniczny("+48", "123456789")));
        ksiazka.put(new NrTelefoniczny("+48", "987654321"), new Firma("ABC", "ul. Krakowska 2", new NrTelefoniczny("+48", "987654321")));
        ksiazka.put(new NrTelefoniczny("+48", "111222333"), new Osoba("Marek", "Nowak", "ul. Słoneczna 3", new NrTelefoniczny("+48", "111222333")));
        ksiazka.put(new NrTelefoniczny("+48", "444555666"), new Firma("XYZ Industries", "ul. Przemysłowa 4", new NrTelefoniczny("+48", "444555666")));
        ksiazka.put(new NrTelefoniczny("+48", "444515666"), new Firma("XYZ Industries", "ul. Przemysłowa 4", new NrTelefoniczny("+48", "444515666")));
        ksiazka.put(new NrTelefoniczny("+48", "876382173"), new Firma("XYZ1 Industries", "ul. Słoneczna 3", new NrTelefoniczny("+48", "876382173")));
        System.out.println("Przed modyfikacjami: ");
        for (Map.Entry<NrTelefoniczny, Wpis> wpis : ksiazka.entrySet()) {
            wpis.getValue().opis();
        }
        checkadress(ksiazka);
        System.out.println("\nPo modyfikacjach:");
        for (Map.Entry<NrTelefoniczny, Wpis> wpis : ksiazka.entrySet()) {
            wpis.getValue().opis();
        }
    }

    private static void checkadress(TreeMap<NrTelefoniczny, Wpis> ksiazka) {
        HashMap<String, NrTelefoniczny> unikalneAdresy = new HashMap<>();
        Set<NrTelefoniczny> doUsuniecia = new HashSet<>();

        for (Map.Entry<NrTelefoniczny, Wpis> entry : ksiazka.entrySet()) {
            String adres = "";
            if (entry.getValue() instanceof Osoba) {
                adres = ((Osoba) entry.getValue()).getAdres();
            } else if (entry.getValue() instanceof Firma) {
                adres = ((Firma) entry.getValue()).getAdres();
            }
            if (unikalneAdresy.containsKey(adres)) {
                doUsuniecia.add(entry.getKey());
            } else {
                unikalneAdresy.put(adres, entry.getKey());
            }
        }

        for (NrTelefoniczny nr : doUsuniecia) {
            ksiazka.remove(nr);
        }
    }
}


class NrTelefoniczny implements Comparable<NrTelefoniczny>{
    private String nrKier;
    private String nrTel;

    public NrTelefoniczny(String kier, String tel){
        this.nrTel = tel;
        this.nrKier = kier;
    }
    @Override
    public int compareTo(NrTelefoniczny o){
        int kier = nrKier.compareTo(o.nrKier);
        if(kier != 0) return kier;
        return nrTel.compareTo(o.nrTel);
    }
    @Override
    public String toString(){
        return nrKier + " " + nrTel;
    }
}
abstract class Wpis{
    public abstract void opis();
}

class Osoba extends Wpis{
    private String imie, nazwisko, adres;
    private NrTelefoniczny nrtel;
    public Osoba(String imie, String nazwisko, String adres, NrTelefoniczny nrtel){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adres = adres;
        this.nrtel = nrtel;
    }
    @Override
    public void opis(){
        System.out.println(imie + " " + nazwisko + " " + adres + ", tel:" + nrtel);
    }
    public String getAdres(){
        return adres;
    }
}
class Firma extends Wpis{
    private String nazwa, adres;
    private NrTelefoniczny nrtel;
    public Firma(String nazwa, String adres, NrTelefoniczny nrtel){
        this.nazwa = nazwa;
        this.adres = adres;
        this.nrtel = nrtel;
    }
    @Override
    public void opis(){
        System.out.println(nazwa+ " " + adres + ", tel:" + nrtel);
    }
    public String getAdres(){
        return adres;
    }

}
