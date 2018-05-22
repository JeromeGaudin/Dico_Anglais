/**
 * @author: Jérôme Gaudin https://github.com/JeromeGaudin
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui permet d'appeler les bonnes classes en fonction de la commande
 * taper sur le terminal
 */
public class Console {

  /**
   * Dictionnaire qui associe des nom de commande à des objets CommandLine
   */
  public static final Map<String, CommandLine> COMMAND;

  /**
   * Crée le dictionnaire qui ne sera pas modifiable
   */
  static {
    Map<String, CommandLine> map = new HashMap<String, CommandLine>();
    
    CommandLine translate = new CommandLineTranslate();
    CommandLine end = new CommandLineEnd();
    CommandLine help = new CommandLineHelp();
    CommandLine add = new CommandLineAddTranslate();

    map.put("quit", end);
    map.put("trans", translate);
    map.put("help", help);
    map.put("add", add);

    COMMAND = Collections.unmodifiableMap(map);
  }
  
  /**
   * Un objet option pour récupéré les options
   */
  private Option option;

  /**
   * Indique si on doit quitter le programme ou non
   */
  private boolean end;

  /**
   * Instance de la classe Console
   */
  private static Console instance = null;

  /**
   * Constructeur
   */
  private Console() {
    option = Option.getInstance();
    end = false;
  }

   /**
   * Permet d'obtenir l'instance de la classe Console
   * @return une instance de la classe Console
   */
  public static Console getInstance() {
    if(instance == null) {
      instance = new Console();
    }
    return instance;
  }

  /**
   * Lit une ligne de la console
   */
  public void readLine() {
    
    String line;
    BufferedReader console = new BufferedReader( new InputStreamReader(System.in));
    
    try {
      line = console.readLine().trim();

      if(line != null) {
        runCommandLine(line);
      }

    } catch(IOException exc) {
      System.err.println("Erreur le programme n'a pas pus lire sur la console");
    }
  }

  /**
   * Boucle pour lire charque ligne de commande
   */
  public void loopRead() {
    while( !end) {
      System.out.print(option.getPreStringConsole());
      readLine();
    }
  }

  /**
   * Appelle la fonction qui correspond à la ligne de commande taper et sépart
   * les arguments, tous les argument doivent commencer par - ou -- sinon il
   * ne sont pas pris en compte
   * @param commandLine : une ligne de commande
   */
  public void runCommandLine(String commandLine) {

    AttributCommandLine attr = separatesAttributs(commandLine);

    CommandLine com = SelectCommandLine(attr);

    if(com == null) {
      System.out.println("La commande "+attr.getCommand()+" n'existe pas taper la commande : help pour connaire toutes les commandes");
    } else {

      // si la commande à été appelé avec l'option help
      if(attr.needHelp()) {
        com.help(attr);
      } else {
        // sinon on exécute la commande normalement
        com.run(attr);
      }
    }
  }

  /**
   * Sépart les attribut de la ligne de commande et les renvoie
   * Si il y a un "mot\ mot" alors compte les deux mots comme un seul
   * @param commandLine : ligne de commande
   * @return AttributCommandLine : un objet qui correspond aux attributs de la
   * ligne de commande
   */
  private AttributCommandLine separatesAttributs(String commandLine) {


    int end = commandLine.indexOf(' ');
    if(end == -1) {
      end = commandLine.length();
    }

    // La commande appelée
    AttributCommandLine result = new AttributCommandLine(commandLine.substring(0, end));

    int start;
    String word = null;

    while(end != commandLine.length()) {

      // redélimite un mot
      start = ++end;
      end = commandLine.indexOf(' ', end);
      if(end == -1) {
      	end = commandLine.length();
      }

      //si il y a un \ en fin de mot
      while(commandLine.charAt(end-1) == '\\') {
        
        // enlève le \
        commandLine = commandLine.substring(0, end-1) + commandLine.substring(end);

        // si c'est pas la fin du groupe de mot
        end = commandLine.indexOf(' ', end);
        if(end == -1) {
          end = commandLine.length();
        }
      }
      
      // ajoute le mot
      word = commandLine.substring(start, end);
      result.addAttribut(word.trim());
    }
    return result;
  }

  /**
   * Selectionne la commande en fonction du dictionnaire de commandes qui 
   * associe un mot à une méthode
   * @param attr : un objet AttributCommandLine qui contient une commande
   * @return CommandLine : retourne un l'objet command line qui correspond à
   * la commande dans l'objet AttributCommandLine, peut retourner null si la
   * commande n'existe pas
   */
  private CommandLine SelectCommandLine(AttributCommandLine attr) {
    CommandLine com = null;
    com = COMMAND.get(attr.getCommand());
    return com;
  }

  /**
   * Change l'attribut end, indique que le programme doit se finir
   */
  public void ending() {
  	end = true;
  }
}