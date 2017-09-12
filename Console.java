//méthodes utiles pour l'entré et la sortie de la console

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Console {
  private static final String ligneDebutConsole = "DicoEnglish> ";
  //private Fichier fichier;
  private static String dicoFile;
  private static FichierModifier fichierEcriture;
  private static FichierLire fichierLecture;

  private boolean exit;

  public Console() {
    Option option = new Option();
    String dico = option.getDicoFile();
    if(dico.equals("") == false) {
      this.dicoFile = option.getDicoFile();
      this.fichierEcriture = new FichierModifier(dico);
      this.fichierLecture = new FichierLire(dico);
      this.exit = false;
    } else {
      System.exit(1);
    }
  }

  public void actualiserCommande() {
    int l;
    String ligneEntre, commande=null;
    List<String> option = new ArrayList<String>();
    while(this.exit == false) {
      String[] argument=null;
      System.out.print(this.ligneDebutConsole);
      BufferedReader console = new BufferedReader( new InputStreamReader(System.in));
      try {
        ligneEntre = console.readLine().trim();

        l = ligneEntre.indexOf(" ");
        if(l != -1) {
          commande = ligneEntre.substring(0, l); // verifier si il faut ajouter trim ou -1 c'est plus efficace
          //sépart les options des arguments
          String[] arguments = ligneEntre.substring(l+1).split(" ");
          int argumentL = 0;
          for(int i=0; i<arguments.length; i++) {
            if(arguments[i].charAt(0) == '-' &&
             argumentL == 0) {
              if(arguments[i].charAt(1) == '-' && arguments[i].length() > 2) { // l'option est un mot
                option.add(arguments[i].substring(2,arguments[i].length()));
              } else if (arguments[i].length() > 1){ // l'option est une lettre
                for(int j=1; j<arguments[i].length(); j++) {
                  option.add(Character.toString(arguments[i].charAt(j)));
                }
              }

            } else {
              if(argumentL == 0) {
                argument = new String[arguments.length - i];
              }
              argument[argumentL] = arguments[i];
              argumentL++;
            }
          }
        } else {
          commande = ligneEntre.substring(0, ligneEntre.length());
        }
      } catch(IOException e) {
        System.err.println("Erreur le programme n'a pas pus lire sur la console");
      }
      /* utile pour le debug */
      /*System.out.println("Commande = "+commande);
      System.out.println("Option = "+option);
      System.out.print("Argument = ");
      if(argument != null)
        for(String n:argument) System.out.print(n+",");
      System.out.println();*/
      if(! commande.equals("")) {
        CommandeConsole commandeConsole = new CommandeConsole(this);
        commandeConsole.determineCommande(commande,option,argument);
      }
      option.removeAll(option);
    }
  }

  public FichierModifier getFichierEcriture() {
    return this.fichierEcriture;
  }

  public FichierLire getFichierLecture() {
    return this.fichierLecture;
  }

  public String getNomDico() {
    return this.dicoFile;
  }

  public void setExit(boolean f) {
    this.exit = f;
  }
}
