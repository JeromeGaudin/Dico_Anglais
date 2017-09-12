//méthode utile pour leire le ficheir

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FichierLire {
  private static String nom; // nom du fichier

  public FichierLire(String nom) {
    this.nom = nom;
  }

  /*public String getNom() {
    return this.nom;
  }*/

  /*public String lireLigne() {
    String r = null;
    try {
      BufferedReader flux = new BufferedReader( new FileReader(this.nom));
      r = flux.readLine();
      flux.close();
    } catch(IOException e) {
      System.err.println("Le fichier "+this.nom+" ne peut pas être lu a cause des droits");
    }
    return r;
  }*/

  public List<String> chercherTraduction(String motAChercher, boolean motBaseAnglais) {
    List<String> r = new ArrayList<String>();
    boolean continu=true;
    try{
      BufferedReader flux = new BufferedReader( new FileReader(this.nom));
      String l = flux.readLine();
      while(l != null && continu) {
        if(motBaseAnglais) { // a traduire en français
          String motAnglais = l.substring(0,l.indexOf(References.getTraductionFichier()));
          if( motAChercher.equals(motAnglais)) {
            String motsTrad = l.substring(1+l.indexOf(References.getTraductionFichier()));
            r = Arrays.asList(motsTrad.split(Character.toString(References.getSeparateurFichier())));
            continu = false;
          }
        } else { // a traduire en anglais
          String motTrad = l.substring(1+l.indexOf(References.getTraductionFichier()));
          String[] motsFrancais = motTrad.split(Character.toString(References.getSeparateurFichier()));
          for(String s:motsFrancais) {
            if(motAChercher.equals(s)) {
              r.add(l.substring(0, l.indexOf(References.getTraductionFichier())));
            }
          }
        }
        l = flux.readLine();
      }
      flux.close();
    } catch(IOException e) {
      System.err.println("Erreur le fichier "+this.nom+" n'a pas pus être lu");
    }
    return r;
  }
}
