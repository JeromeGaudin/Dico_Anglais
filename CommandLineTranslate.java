/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.util.List;

/**
 * Classe pour traduire un mot à partie d'une ligne de commande
 */
public class CommandLineTranslate extends CommandLine {

  /**
   * Objet qui content des méthodes pour lire facilement dans le diciotnnaie
   */
  private ReadFunction readFunction;

  /**
   * Constructeur
   */
  public CommandLineTranslate() {
    super();
    readFunction = ReadFunction.getInstance();
  }

  /**
   * L'aide de la commande
   */ 
  @Override
  public void help(AttributCommandLine attr) {
    System.out.println("Permet de traduire un mot de l'Anglais vers le Français ou du Français vers l'Anglais, sans option la commande traduit de l'Anglais vers le Français.");
    System.out.println();
    System.out.println(attr.getCommand()+" [option] <mot à traduire>");
    System.out.println();
    System.out.println("options :");
    System.out.println("  -e\t--english\tTraduit du Français vers l'Anglais");
    System.out.println("  -f\t--french\tTraduit de l'Anglais vers le Français");
    System.out.println("  -h\t--help\t\tL'aide de la commande");
  }

  /**
   * Phrase pour résumé l'objet
   * @return String : retourne une phrase pour résumé l'objet
   */
  @Override
  public final String sentenceResume() {
    return "Permet de traduire un mot";
  }


  /**
   * Traduit un mot
   * @param attr : un objet attribut
   */
  @Override
  public void run(AttributCommandLine attr) {

    boolean english = false;
    boolean french = false;

    for(char c : attr.getSimpleAttribut()) {
      if(c == 'e') {
        english = true;
      } else if(c == 'f') {
        french = true;
      }
    }

    for(String s : attr.getComplexAttribut()) {
      if(s.equals("english")) {
        english = true;
      } else if(s.equals("french")) {
        french = true;
      }
    }

    List<String> words = attr.getWordAttribut();

    if(words.size() > 0) {
      if(english && !french) {
        transEnglish(words, false);

      } else if(french && !english) {
        transFrench(words, false);

      } else {
        transEnglish(words, true);
        transFrench(words, true);
      }

    } else {
      System.err.println("Il faut ajouter un mot a traduire.");
    }
  }


  /**
   * trouve les à traduire en français
   * @param words : liste de mot à traduire
   * @param printLangue : afficher la langue ou non
   * @return la liste des mots traduit 
   */
  private void transFrench(List<String> words, boolean printLangue) {
    List<String> trans;
    boolean first = true;

    for(String s : words) {
      trans = readFunction.searchTranslate(s, true);
      if(trans.size() != 0) {
        if(printLangue && first) {
          System.out.println("Français :");
          first = false;
        }
        translatePrint(s, trans);
      }
    }
  }

  /**
   * trouve les à traduire en anglais
   * @param words : liste de mot à traduire
   * @param printLangue : afficher la langue ou non
   * @return la liste des mots traduit 
   */
  private void transEnglish(List<String> words, boolean printLangue) {
    List<String> trans;
    boolean first = true;

    for(String s : words) {
      trans = readFunction.searchTranslate(s, false);
      if(trans.size() != 0) {
        if(printLangue && first) {
          System.out.println("Anglais :");
          first = false;
        }
        translatePrint(s, trans);
      }
    }
  }

  /**
   * affiche la traduction
   * @param w : le mot à traduire
   * @param translare : les mots qui correspondent à la traduction
   */
  private void translatePrint(String w, List<String> translate) {
    String equal = option.getConsoleEqual();
    String separator = option.getConsoleSeparator();


    String trans = String.join(separator, translate);

    System.out.println(w+equal+trans);
  }

}