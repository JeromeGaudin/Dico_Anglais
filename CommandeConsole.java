// toutes les commandes de la console sont dans cette fonction

// bug quand on tape trad -f

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommandeConsole{
  private Console console;

  public CommandeConsole(Console console) {
    this.console = console;
  }

  public boolean determineCommande(String commande, List<String> option, String[] argument) {
    if(commande.equals(null)) {
      return false;
    }
    switch(commande) {
      case "add":
        ajouterDefinition(option, argument);
        break;
      case "exit":
        cExit();
        break;
      case "help":
        help();
        break;
      case "trad":
        traduction(option, argument);
        break;
      case "del":
        supprimerDefinition(option, argument);
        break;
      /*case "modif":
        modifierDefinition(option, argument);
        break;*/
      default:
        System.out.println("Cette commande n'existe pas taper \"help\" pour avoir la liste des commandes");
        return false;
    }
    return true;
  }

  private void help() {
    System.out.println("DicoEnglish permet d'enregistrer et de retrouver des traductions de mots anglais");
    System.out.println("Si vous voulez en savoir plus sur une commande faire \"<commmande> --help\"");
    System.out.println();
    System.out.println("COMMANDES :");
    System.out.println("add\t\t permet d'ajouter une nouvelle traduction");
    System.out.println("del\t\t supprime une traduction Anglaise");
    System.out.println("exit\t\t quit le programme");
    //System.out.println("modif\t\t modifie une traduction existante en ajoutant un mot");
    System.out.println("trad\t\t traduit un mot en Anglais ou en Français");
  }

  private void ajouterDefinition(List<String> option, String[] argument) {
    boolean help = false;
    if(!option.isEmpty()) {
      for(String s:option) {
        if(s.equals("help")) {
          help = true;
        }
      }
    }
    if(help) {
      System.out.println("add motAnglais traductionFrançais [traductionFrançais] [traductionFrançais] ...");
      System.out.println();
      System.out.println("add permet d'ajouter une nouvelle traduction");
      System.out.println();
      System.out.println("OPTIONS :");
      System.out.println("\t--help\t\tdescription de la commande");
    } else if((help == false && option.size() > 1) || argument == null || argument.length < 2) {
      System.out.println("La commande n'est pas valide faire \"add --help\" pour connaitre s on utilisation");
    } else {
      FichierModifier ecriture = this.console.getFichierEcriture();
      if(! ecriture.motAnglaisExiste(argument[0])) {
        String[] motFrancais = Arrays.copyOfRange(argument, 1, argument.length);
        ecriture.ajouterMot(argument[0], motFrancais);
      } else {
        System.out.println(argument[0]+" existe est déjà présent dans le dictionnaire");
      }
    }
  }
  private void cExit() {
    console.setExit(true);
  }

  private void traduction(List<String> option, String[] argument) {
    boolean help = false;
    boolean execute = false; // si la commande a été executé et donc il ne faut plus rien faire
    String langue = null;
    boolean optionNonValide = false;
    if(!option.isEmpty()) {
      for(String s:option) {
        if(s.equals("help")) {
          help = true;
        } else if(s.equals("e") || s.equals("f") || s.equals("english") || s.equals("francais")) {
          if(langue == null) {
            langue = s;
          } else if(!help){
            System.out.println("l'option e et l'option f ne sont pas compatible ensemble");
            System.out.println("On ne peut pas traduire un mot en Anglais et en français en même temps");
            execute = true;
          }
        } else {
          optionNonValide = true;
        }
      }
    }
    if(!execute) {
      if(help) {
        System.out.println("trad [option] motATraduire");
        System.out.println();
        System.out.println("trad permet de traduire un mot en anglais ou en francais");
        System.out.println();
        System.out.println("OPTIONS :");
        System.out.println("-e\t--english\t\tRecherche la traduction du mot en anglais");
        System.out.println("-f\t--francais\t\tRecherche la traduction du mot en francais");
        System.out.println("\t--help\t\t\tdescription de la commande");
      } else if(langue == null || argument.length != 1 || optionNonValide) {
        System.out.println("La commande n'est pas valide faire \"trad --help\" pour connaitre son utilisation");
      } else {
        boolean motBaseAnglais = (langue.equals("e") || langue.equals("english"))? false: true;
        FichierLire lecture = this.console.getFichierLecture();
        List<String> traduction = lecture.chercherTraduction(argument[0], motBaseAnglais);
        if(!traduction.isEmpty()) {
          System.out.print(argument[0]+" = ");
          for(int i=0; i<traduction.size(); i++) {
            System.out.print(traduction.get(i));
            if(i != traduction.size()-1)
              System.out.print(", ");
          }
          System.out.println();
        } else {
          String afficherLangue = (motBaseAnglais)? "Anglais": "Français";
          System.out.println("Le mot "+argument[0]+" n'existe pas dans le dictionnaire en "+afficherLangue);
        }
      }
    }
  }

  private void supprimerDefinition(List<String> option, String[] argument) {
    boolean help = false;
    String langue = null;
    boolean execute = false;
    String mauvaiseOption = null;
    if(!option.isEmpty()) {
      for(String s:option) {
        if(s.equals("help")) {
          help = true;
        } else if(!help && mauvaiseOption == null) { // option non valide
          mauvaiseOption = s;
        }
      }
    }
    if(!execute) {
      if(help) {
        System.out.println("del [option] motASuppEnglais");
        System.out.println("supprime une traduction");
        System.out.println();
        System.out.println("OPTIONS :");
        System.out.println("\t--help\t\tdescription de la commande");
      } else if(argument == null || argument.length != 1 || mauvaiseOption != null){
        System.out.println("La commande n'est pas valide faire \"del --help\" pour connaitre son utilisation");
      } else {
        FichierModifier ecrire = this.console.getFichierEcriture();
        ecrire.effacerDefinition(argument[0]);
      }
    }
  }

  /* fonctionne pas à retravailler */
  /*private void modifierDefinition(List<String> option, String[] argument) {
    boolean help = false;
    for(String s:option) {
      if(s.equals("help"))
        help = true;
    }
    if(help) {
      System.out.println("modif motAnglais motFrancaisAAjouter [motFrancaisAAjouter] ...");
      System.out.println("modifie une traduction existante en ajoutant un mot");
      System.out.println();
      System.out.println("OPTIONS :");
      System.out.println("\t--help\t\tdescription de la commande");
    } else if(!option.isEmpty() || argument.length < 2) {
      System.out.println("La commande n'est pas valide faire \"modif --help\" pour connaitre son utilisation");
    } else {
      String l;
      boolean trouve = false;
      try {
        BufferedReader lire  = new BufferedReader(new FileReader(console.getNomDico()));
        while((l = lire.readLine()) != null && trouve == false) {
          String motAnglais = l.substring(0,l.indexOf(References.getTraductionFichier()));
          if(argument[0].equals(motAnglais)) {
            trouve = true;
          }
        }
        lire.close();
        if(trouve) {
          List<String> optionVide= new ArrayList<String>();
          String[] argumentSupp = {argument[0]};
          supprimerDefinition(optionVide, argumentSupp);
          String[] motFrancais = (l.substring(1+l.indexOf(References.getTraductionFichier()))).split(Character.toString(References.getSeparateurFichier()));
          String[] motAAjouter = Arrays.copyOfRange(argument, 1, argument.length);
          String[] argumentAdd = new String[motFrancais.length + motAAjouter.length];
          System.arraycopy(motFrancais, 0, argumentAdd, 0, motFrancais.length);
          System.arraycopy(motAAjouter, 0, argumentAdd, motFrancais.length, motAAjouter.length);
          ajouterDefinition(optionVide, motFrancais);
        } else {
          System.out.println("la traduction "+argument[0]+" n'existe pas");
        }
      } catch(IOException e) {
        System.err.println("Erreur le fichier "+console.getNomDico()+" n'a pas pus être lu");
      }
    }
  }*/
}
