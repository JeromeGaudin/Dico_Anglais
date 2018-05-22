/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

/**
 * Classe pour terminer le programme
 */
public class CommandLineEnd extends CommandLine {

  /**
   * Constructeur
   */
  public CommandLineEnd() {
    super();
  }

  /**
   * L'aide de la commande
   */ 
  @Override
  public void help(AttributCommandLine attr) {
    System.out.println("Permet de quitter le programme");
    System.out.println();
    System.out.println(attr.getCommand());
  }

  /**
   * Phrase pour résumé l'objet
   * @return String : retourne une phrase pour résumé l'objet
   */
  @Override
  public final String sentenceResume() {
    return "Permet de quitter le programme";
  }


  /**
   * Quitte le programme
   * @param attr : un objet attribut
   */
  @Override
  public void run(AttributCommandLine attr) {
    Console.getInstance().ending();
  }

}
