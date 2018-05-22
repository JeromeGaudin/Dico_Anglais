/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

/**
 * Classe abstraite pour crée une base identique pour chaque ligne de commande
 */
public abstract class CommandLine {

  /**
   * Classe Option pour avoir les paramètres
   */
  protected Option option;

  /**
   * Constructeur
   */
  public CommandLine() {
    option = Option.getInstance();
  }

  /**
   * fonction abstraite qui doit normalement afficher l'aide de la commande 
   */
  public abstract void help(AttributCommandLine attr);

  /**
   * fonction abstraite qui doit normalement lancer la commande en prenant en
   * compte les attributs
   */
  public abstract void run(AttributCommandLine attr);

  /**
   * Doit normalement retourner une phrase pour résumé la fonction, cette
   * phrase sera affichée avec l'objet commandLineHelp
   */
  public abstract String sentenceResume();
}