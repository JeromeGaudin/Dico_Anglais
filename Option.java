// méthodes pour géré le fichier option.conf
// amélioration : regarder la date de changement pout recherger les arguments

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Option {
  private static final String fileOption = "option.conf";
  private static final String equivalent = "=";
  private static final String[] nomDifferentesOptions = {"dicoFile"};
  private String[] differentesOptions; // fichier où sont toutes les traductions


  public Option() {
  this.differentesOptions = new String[nomDifferentesOptions.length];
  }

  private boolean chargerInformation() {
    try {
      BufferedReader lire = new BufferedReader(new FileReader(fileOption));
      try {
        String l = lire.readLine();
        while(l != null) {
          boolean flag = true;
          for(int i=0; i<nomDifferentesOptions.length && flag; i++) {
            if(l.startsWith(nomDifferentesOptions[i])) {
              String tmp = l.substring(1+l.indexOf(this.equivalent));
              this.differentesOptions[i] = tmp.trim();
              flag = false;
            }
          }
          l = lire.readLine();
        }
        int i;
        boolean pasRempli = false;
        for(i=0; i<differentesOptions.length && !pasRempli; i++) { // verfifi si toutes les options sont bien rempli
          if(differentesOptions[i] == null)
            pasRempli = true;
        }
        if(pasRempli) {
          System.err.println("Le fichier de configuration "+fileOption+" il manque l'argument "+nomDifferentesOptions[i]);
          return false;
        }
      } catch(IOException e) {
        System.err.println("Le fichier de configuration "+fileOption+" n'a pas les droits pour être lu");
        return false;
      }
    } catch(IOException e) {
      System.err.println("Le fichier de configuration "+fileOption+" n'existe pas ou n'a pas mes droits pour être lu");
      return false;
    }
    return true;
  }

  public String getDicoFile() {
    if(chargerInformation())
      return differentesOptions[0];
    else
      return "";
  }
}
