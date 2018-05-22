/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.util.Map;

/**
 * Classe pour avoir de l'aide sur toutes les commandes
 */
public class CommandLineHelp extends CommandLine {

  /**
   * Constructeur
   */
  public CommandLineHelp() {
    super();
  }

  /**
   * L'aide de la commande
   */ 
  @Override
  public void help(AttributCommandLine attr) {
    System.out.println("Permet de lister toutes les commandes");
    System.out.println();
    System.out.println(attr.getCommand());
  }

  /**
   * Phrase pour résumé l'objet
   * @return String : retourne une phrase pour résumé l'objet
   */
  @Override
  public final String sentenceResume() {
    return "Permet de lister toutes les commandes";
  }


  /**
   * Liste toutes les commandes
   * @param attr : un objet attribut
   */
  @Override
  public void run(AttributCommandLine attr) {
    System.out.println("Ce programme permet de traduire les mots français en mot anglais et vis versa,");
    System.out.println("pour cela il utilise un fichier dictionnaire où les traductions sont");
    System.out.println("enregistrées");
    System.out.println();
    System.out.println("COMMANDES :");

    for(Map.Entry<String, CommandLine> elem : Console.getInstance().COMMAND.entrySet()) {
      System.out.println(elem.getKey()+"\t"+elem.getValue().sentenceResume());
    }
  }

}
