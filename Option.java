/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe qui permet de prendre les options dans le fichier option.conf
 */
public class Option {

  /**
   * Chemin du fichier de configuration
   */
  private static final String optionFile = "option.conf";

  /**
   * Char qui correspond à l'équivalence dans le fichier d'option
   */
  private static final char equal = ':';

  /**
   * Nom des différentes options
   */
  private static final String[] optionsNames = {"dicoFile", "preStringConsole", "consoleSeparator", "consoleEqual", "dicoSeparator", "dicoEqual"};

  /**
   * Valeur des différentes options
   */
  private String[] optionsValues = new String[optionsNames.length];

  /**
   * Instance de la classe Option
   */
  private static Option instance = null;

  /**
   * Constructeur
   */
  private Option() {
    if( !loadData()) {
      System.exit(1);
    }
  }

  /**
   * Permet d'obtenir l'instance de la classe Option
   * @return une instance de la classe Option
   */
  public static Option getInstance() {
    if(instance == null) {
      instance = new Option();
    }
    return instance;
  }

  /**
   * Lit les informations dans le fichier de configuration et les enregistre
   * dans des variables
   * @return boolean : true si tout c'est bien passé, false sinon
   */
  private boolean loadData() {
    boolean result = true;
    try {
      BufferedReader read = new BufferedReader(new FileReader(optionFile));
      try {

        String line = read.readLine();

        // si le fichier est vide
        if(line == null) {
          System.err.println("Le fichier "+optionFile+" est vide il faut ajouter l'option \""+optionsNames[0]+"\".");
          result = false;
        }

        int numberLine = 1;
        boolean notExist = true;

        // pour chaque ligne du fichier
        while(line != null && result) {

          // vérifie que le symbole de la variable equal existe dans la ligne
          int equalIndex = line.indexOf(equal);
          if(equalIndex < 1) {
            if(equalIndex == -1) {
              System.err.println("Il manque une valeur associer avec l'option de la ligne "+numberLine+", ajouter le symbole égale : '"+equal+"' et une valeur pour que le programme fonctionne");
            } else {
              System.err.println("L'option ne peut pas être nul ajouter un nom d'option à la ligne "+numberLine+".");
            }
            result = false;
            break;
          }

          // prend le nom de l'option
          String option = line.substring(0, equalIndex);

          // attribut la valeur à l'option si l'option existe
          notExist = true;
          for(int i=0; i<optionsNames.length && notExist; i++) {
            if(line.startsWith(optionsNames[i])) {
              optionsValues[i] = line.substring(equalIndex+1);
              notExist = false;
            }
          }
          // si l'attribut n'existe pas
          if(notExist) {
            System.err.println("La ligne avec l'option : \""+option+"\" n'existe pas, enlever la du fichier "+optionFile+" pour que le programme fonctionne.");
            result = false;
          }

          line = read.readLine();
          numberLine++;
        }

        // verifie que toutes les options ont bien été remplie
        if(result) {
          for(String option : optionsValues) {
            if(option == null) {
              System.err.println("L'option : \""+option+"\" n'est pas dans le fichier "+optionFile+ " ajouter le dans le fichier pour que le programme fonctionne.");
              result = false;
            }
          }
        }

        if( !checkOption()) {
          result = false;
        }

      } catch(IOException exc) {
        System.err.println("Le fichier de configuration "+optionFile+" n'a pas les droits pour être lu");
        result = false;
      }
    } catch(IOException exc) {
      System.err.println("Le fichier de configuration "+optionFile+" n'existe pas.");
      result = false;
    }
    return result;
  }

  /**
   * Verifie si les options rentré sont correcte
   * @return boolean : true si les options sont bonnes, false sinon
   */
  private boolean checkOption() {

    // dicoFile
    if(optionsValues[0].length() == 0) {
      System.err.println("La valeur de l'option \""+optionsNames[0]+"\" ne doit pas être nul");
      return false;
    }
    File f = new File(optionsValues[0]);
    if( !f.exists() || f.isDirectory()) { 
      System.err.println("La valeur de l'option \""+optionsNames[0]+"\" ("+optionsValues[0]+") doit corresponde à un fichier existant.");
      return false;
    }

    return true;
  }

  /**
   * Permet d'avoir le nom du fichier qui correspond au dictionnaire
   * @return String : le nom du fichier ou il y a le dictionnaire
   */
  public String getFileNameDictionary() {
    return optionsValues[0];
  }

  /**
   * Permet d'avoir le string qui doit être affichier avant que l'on entre une ligne de commande
   * @return String : la chaine de caractère à afficher
   */
  public String getPreStringConsole() {
    return optionsValues[1];
  }

  /**
   * Permet d'avoir le string qui est afficher pour séparé un deux mots traduit
   * @return String : string qui est afficher pour séparé un deux mots traduit
   */
  public String getConsoleSeparator() {
    return optionsValues[2];
  }  

  /**
   * Permet d'avoir le string qui est afficher pour signifier que deux mot de langues différentes correspondent à la même définition
   * @return String : string égale qui est afficher à la console
   */
  public String getConsoleEqual() {
    return optionsValues[3];
  }

  /**
   * Permet d'avoir le string qui correspond au séparateur dans le fichier dictionnaire
   * @return String : string qui correspond au séparateur dans le fichier dictionnaire
   */
  public String getDicoSeparator() {
    return optionsValues[4];
  }  

  /**
   * Permet d'avoir le string qui correspond à l'agal dans le fichier dictionnaire
   * @return String : string qui correspond à l'égal dans le fichier dictionnaire
   */
  public String getDicoEqual() {
    return optionsValues[5];
  }
}