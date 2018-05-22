/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.util.List;

/**
 * Classe pour ajouter une traduction
 */
public class CommandLineAddTranslate extends CommandLine {

  /**
   * Objet WriteFunction pour écrire dans le dictionnaire
   */
  WriteFunction write;

  /**
   * Objet ReadFunction pour lire dans le dictionnaire
   */
  ReadFunction read;


  /**
   * Constructeur
   */
  public CommandLineAddTranslate() {
    super();
    write = WriteFunction.getInstance();
    read = ReadFunction.getInstance();
  }

  /**
   * L'aide de la commande
   */ 
  @Override
  public void help(AttributCommandLine attr) {
    System.out.println("Permet d'ajouter une traduction, un mot Anglais peut corresponde à plusieurs");
    System.out.println("mots Français. Pour ajouter un espace il faut taper : \"\\ \"");
    System.out.println();
    System.out.println(attr.getCommand()+" <mot Angais> <equivalent en Français> ...");
  }

  /**
   * Phrase pour résumé l'objet
   * @return String : retourne une phrase pour résumé l'objet
   */
  @Override
  public final String sentenceResume() {
    return "Permet d'ajouter une traduction";
  }


  /**
   * Ajoute une traduction
   * @param attr : un objet attribut
   */
  @Override
  public void run(AttributCommandLine attr) {
    
    List<String> words = attr.getWordAttribut();

    if(words.size() < 2) {
      System.err.println("Il faut au moins deux arguments");
      return;
    }

    String englishWord = words.get(0);

    //verifie que le mot n'est pas déjà présent dans le dictionnaire
    if(read.englishWordExist(englishWord) != -1) {
      System.err.println("Le mot \""+englishWord+"\" est déjà présent dans le dictionnaire.");
      return;
    }

    words.remove(0);
    String[] translate = words.toArray(new String[words.size()]);

    write.addTranslation(englishWord, translate);
  }
}
