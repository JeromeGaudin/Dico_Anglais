// tout ce qui est utile pour modifier le fichier

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.StringJoiner;


public class FichierModifier {
  private String nom;

  public FichierModifier(String nom) {
    this.nom = nom;
  }

  // format ajouter : add motAnglais motFrancais [motFrancais] [motFrancais] ...
  public void ajouterMot(String motAnglais, String[] motFrancais) {
    StringJoiner sj = new StringJoiner(Character.toString(References.getSeparateurFichier()));
    for(String s:motFrancais) sj.add(s);
    String ligneAEcrire = motAnglais+References.getTraductionFichier()+sj;
      try {
      BufferedWriter fd = new BufferedWriter( new FileWriter(this.nom, true));
      fd.write(ligneAEcrire);//, 0, 0 + ligneAEc;
      fd.newLine();
      fd.close();
    } catch(IOException e) {
      System.err.println("Erreur le fichier "+this.nom+" n'a pas les droits pour être ouvert en écriture");
    }
  }

  public boolean motAnglaisExiste(String mot) {
    boolean trouve = false;
    try {
      BufferedReader flux = new BufferedReader( new FileReader(this.nom));
      String ligne = flux.readLine();
      while( ligne != null && !trouve) {
        ligne = ligne.substring(0, ligne.indexOf(References.getTraductionFichier()));
        if(ligne.equals(mot))
          trouve = true;
        ligne = flux.readLine();
      }
      flux.close();
    } catch (IOException e) {
      System.err.println("Erreur le fichier "+this.nom+" n'a pas pus être lu");
    }
    return trouve;
  }

  public void effacerDefinition(String motASupp) {
    if(!motAnglaisExiste(motASupp)) {
      System.out.println("Le mot "+motASupp+" n'existe pas");
      return;
    }
    String l;
    File tempFile = new File("tempFile.txt");

    try {
      BufferedReader lire = new BufferedReader( new FileReader(this.nom));
      BufferedWriter ecrire = new BufferedWriter(new FileWriter(tempFile));
      while((l = lire.readLine()) != null) {
        String mot = l.substring(0,l.indexOf(References.getTraductionFichier()));
        if(!mot.equals(motASupp)) {
          ecrire.write(l + System.getProperty("line.separator")); // a modifier si bug
        }
      }
      ecrire.close();
      lire.close();

      File fileDico = new File(this.nom);
      tempFile.renameTo(fileDico);
    } catch(IOException e) {
      System.err.println("Erreur le fichier "+this.nom+" n'a pas les droits pour être ouvert en écriture");
    }
  }
}
