/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * classe pour lire dans le dictionnaire
 */
public class ReadFunction {

  /**
   * Objet option pour avoir le nom du fichier qui correspond au dictionnaire
   */
  protected Option option;

  /**
   * Instance de l'objet ReadFunction
   */
  private static ReadFunction instance = null;

  /**
   * Constructeur
   */
  protected ReadFunction() {
    option = Option.getInstance();
  }

  /**
   * Permet d'obtenir une instance de l'objet ReadFunction
   * @return ReadFunction : retourne une instance de ReadFunction
   */
  public static ReadFunction getInstance() {
    if(instance == null) {
      instance = new ReadFunction();
    }
    return instance;
  }

  /**
   * Cherche une traduction en anglais ou en français (si on met des mots
   * anglais et français dans le dictionnaire)
   * Trouve plus rappidement la traduction Anglais->Français que 
   * Français->Anglais
   * @param searchWord : mot à chercher dans le dictionnaire
   * @param englishWord : true si englishWord est un mot anglais
   * @return List<string> : une liste de mots qui correspond à la traduction
   */
  public List<String> searchTranslate(String searchWord, boolean englishWord) {
    List<String> result = new ArrayList<String>();
    String fileNameDictionary = option.getFileNameDictionary();

    boolean notFinish = true;
    int searchWordSize = searchWord.length();

    char equal = option.getDicoEqual().charAt(0);
    char separator = option.getDicoSeparator().charAt(0);

    try {
      BufferedReader read = new BufferedReader(new FileReader( fileNameDictionary));
      
      try {
        String line = read.readLine();
        
        while(line != null && notFinish) {

          // mot à traduire en français
          if(englishWord) {

            try{
              String dicoWord = line.substring(0, searchWordSize);

              // si le cherché correspond au mot trouvé dans le dictionnaire
              if(dicoWord != null && searchWord.equals(dicoWord)) {

                // prend tous les mots qui sont sur la ligne après le symbole de
                // la variable equal
                String transWord = line.substring(line.indexOf(equal) + 1, line.length());
                result = Arrays.asList(transWord.split(Character.toString(separator)));
              }
            } catch(IndexOutOfBoundsException exc) {}
          
          } else {// mot à traduire en anglais  

            String dicoWord = line.substring(line.indexOf(equal) + 1);
            String[] transWord = dicoWord.split(Character.toString(separator));

            // pour chaque mot de la ligne
            for(String s : transWord) {
              if(searchWord.equals(s)) {
                result.add(line.substring(0, line.indexOf(equal)));
              }
            }
          }

          line = read.readLine();
        }
      } catch(IOException exc) {
        System.err.println("Le fichier \"dictionnaire\" ("+fileNameDictionary+") n'a pas les droits pour être lu");
      }
    } catch(IOException exc) {
      System.err.println("Le fichier \"dictionnaire\" ("+fileNameDictionary+") n'existe pas.");
    }
    return result;
  }

  /**
   * Verifie si le mot exite ou non dans le dictionnaire et retourne sa position
   * @param word : mot Angalais à trouver
   * @return long : retourne la ligne dans le fichier si le mot à été trouvé sinon retourne -1
   */
  protected long englishWordExist(String word) {
    
    boolean found = false;

    char equal = option.getDicoEqual().charAt(0);
    String fileNameDictionary = option.getFileNameDictionary();
    long index=0;

    try {
      BufferedReader read = new BufferedReader( new FileReader(fileNameDictionary));
      String line = read.readLine();
      while( line != null && !found) {
        
        line = line.substring(0, line.indexOf(equal));
        if(line.equals(word))
          found = true;

        line = read.readLine();
        index++;
      }
      read.close();

    } catch (IOException e) {
      System.err.println("Erreur le fichier "+fileNameDictionary+" n'a pas pus être lu");
    }

    if(found)
      return index;
    else
      return -1;
  }
}