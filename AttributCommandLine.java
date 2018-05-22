/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Classe pous stocker les attributs et la commande d'une ligne de commande
 */
public class AttributCommandLine {

  /**
   * considère les options suivantes pour activer l'aide de la commande
   */
  private static final String HELP = "--help";
  private static final char H = 'h';

  /**
   * Si la varibale est égale à true alors la ligne de commande est demandé
   * avec l'option help
   */
  private boolean help;

  /**
   * La commande
   */
  private String command;

  /**
   * La liste des mots
   */
  private List<String> attribut;
  
  /**
   * Constructeur
   * @param com : la commande
   */
  public AttributCommandLine(String com) {
    command = com;
    attribut = new ArrayList<String>();
    help = false;
  }

  /**
   * Ajoute un attribut, n'ajoute pas les attributs null
   * @param attr : attribut à plusieurs lettres
   */
  public void addAttribut(String attr) {
    if(attr.length() > 0) {
      if(attr.length() > 1 && attr.charAt(0) == '-' && attr.charAt(1) != '-') {
        for(int i=1; i<attr.length(); i++){
          if(attr.charAt(i) == H) {
            help = true;
          }
        }
      } else if(attr.equals(HELP)) {
        help = true;
      }
      attribut.add(attr);
    }
  }

  /**
   * Getter
   * @return la commande
   */
  public String getCommand() {
    return command;
  }

  /**
   * Permet d'extraire les options à une seul lettre de la liste des attributs
   * @return la liste des attributs à une seul lettre
   */
  public List<Character> getSimpleAttribut() {

    List<Character> result = new ArrayList<Character>();

    if(attribut != null) {
      for(String word : attribut) {
        if(word.length() >= 2 && word.charAt(0) == '-') {
          
          int i=1;
          // si il y a plusieurs lettres (ex : -xvf)
          while(i < word.length()) {
            result.add(word.charAt(i++));
          }
        }
      }
    }
    return result;
  }

  /**
   * Permet d'extraire les options à plusieurs lettres de la liste des attributs
   * @return la liste des mots attribus avec plusieurs lettres
   */
  public List<String> getComplexAttribut() {
    
    List<String> result = new ArrayList<String>();

    if(attribut != null) {
      for(String word : attribut) {
        if(word.length() >= 3 && word.charAt(0) == '-' && word.charAt(1) == '-') {
          result.add(word.substring(2, word.length()));
        }
      }
    }
    return result;
  }

  /**
   * Getter
   * @return la liste des attribus
   */
  public List<String> getAttribut() {
    return attribut;
  }

  /**
   * Permet d'extraire les mot qui ne sont pas des options de la liste des attributs
   * @return la liste des attribus
   */
  public List<String> getWordAttribut() {

    List<String> result = new ArrayList<String>();

    if(attribut != null) {
      for(String word : attribut) {
        if(word.length() >= 2 && word.charAt(0) != '-') {
          result.add(word);
        } else if(word.length() < 2) {
          result.add(word);
        }
      }
    }
    return result;
  }

  /**
   * pour convertire l'objet en chaine de caractères
   * @return string : retourle l'objet en chaines de caractères
   */
  public String toString() {
    return command+" "+attribut;
  }

  /**
   * Détermine si l'utilisateur à demander de l'aide sur cette commande, le
   * détermine grâce aux attributs simpleHelp et complexHelp
   * @return boolean : ture si il a demander de l'aide, flase sinon
   */
    public boolean needHelp() {
      return help;
    }
}