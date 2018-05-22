/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringJoiner;


/**
 * Classe pour modifier le dictionnaire
 */
public class WriteFunction extends ReadFunction {

  /**
   * Instance de l'objet ReadFunction
   */
  private static WriteFunction instance = null;

  /**
   * Constructeur
   */
  private WriteFunction() {
    super();
  }

  /**
   * Permet d'obtenir une instance de l'objet WriteFunction
   * @return WriteFunction : retourne une instance de WriteFunction
   */
  public static WriteFunction getInstance() {
    if(instance == null) {
      instance = new WriteFunction();
    }
    return instance;
  }

  /**
   * Permet d'ajouter un mot dans le dictionnaire
   *  format pour ajouter un mot : add motAnglais motFrancais [motFrancais] [motFrancais] ...
   * @param englishWord : le mot anglais à ajouter
   * @param translate : les traductions de ce mot anglais
   */
  public void addTranslation(String englishWord, String[] translate) {
    
    // StringJoiner permet de faire qu'une seul concaténation d'un coup
    StringJoiner sj = new StringJoiner(option.getDicoSeparator());

    for(String s:translate) sj.add(s);
    String line = englishWord + option.getDicoEqual() + sj;
    
    File file = new File(option.getFileNameDictionary());

    try {
      BufferedWriter fd = new BufferedWriter( new FileWriter(file, true));
      
      if(file.length() != 0)
        fd.newLine();

      fd.write(line);
      fd.close();

    } catch(IOException e) {
      System.err.println("Erreur le fichier "+option.getFileNameDictionary()+" n'a pas les droits pour être ouvert en écriture");
    }
  }
}