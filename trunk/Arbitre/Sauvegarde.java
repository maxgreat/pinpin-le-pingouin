package Arbitre;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
 
import org.w3c.dom.*;

import java.util.*;
import java.io.*;

public class Sauvegarde
{

    protected int largeur;
    protected int hauteur;
    protected String joueur1;
    protected String joueur2;
    protected int joueurEnCours;

    protected ListIterator<Configuration> it;

    /**
     * Retour une chaine contenant le xml
     * Prend un iterateur sur une liste de configuration
     **/
    public String getXml()
    {
	ListIterator<Configuration> list = getIterateur();

        if (!list.hasNext())
            return null;

        try 
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    
            // Element racine
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("sauvegarde");
            doc.appendChild(rootElement);

	    // Type du joueur 1
            Element joueur1Elem = doc.createElement("joueur1");
            joueur1Elem.appendChild(doc.createTextNode(joueur1));
            rootElement.appendChild(joueur1Elem);
 
	    // Type du joueur 2
            Element joueur2Elem = doc.createElement("joueur2");
            joueur2Elem.appendChild(doc.createTextNode(joueur2));
            rootElement.appendChild(joueur2Elem);

	    // Joueur en cours
            Element joueurEnCoursElem = doc.createElement("joueurEnCours");
	    joueurEnCoursElem.appendChild(doc.createTextNode(String.valueOf(joueurEnCours)));
            rootElement.appendChild(joueurEnCoursElem);
	    
            // Largeur du terrain
            Element largeur = doc.createElement("largeur");
            largeur.appendChild(doc.createTextNode(String.valueOf(getLargeur())));
            rootElement.appendChild(largeur);
 
            // Hauteur du terrain
            Element hauteur = doc.createElement("hauteur");
            hauteur.appendChild(doc.createTextNode(String.valueOf(getHauteur())));
            rootElement.appendChild(hauteur);

            Element listeConfig = doc.createElement("configurations");

	    Configuration c = null;
	    Element configuration = null;
            while (list.hasNext())
            {
                c = list.next();
                configuration = doc.createElement("data");
                configuration.appendChild(doc.createTextNode(c.getSerialize()));
                listeConfig.appendChild(configuration);	    
            }
	    
            // Ajoute la configuration
            rootElement.appendChild(listeConfig);

            // Récupère la chaine et la renvoit
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
 
            // Récupère dans la writer
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
 
            transformer.transform(source, result);
 
            return writer.getBuffer().toString();

        } 
        catch (ParserConfigurationException pce) 
        {
            return null;
        }
        catch (TransformerException tfe) 
        {
            return null;
        }
    }
    
    /**
     * Lis une chaine et retourne un iterateur 
     * Sur une liste de configuration
     **/
    public ListIterator<Configuration> readXml(String xml)
    {
        Pile<Configuration> pile = new Pile<Configuration>();

        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
	    
            doc.getDocumentElement().normalize();
	    
            setHauteur(Integer.valueOf(doc.getElementsByTagName("hauteur").item(0).getTextContent()));
            setLargeur(Integer.valueOf(doc.getElementsByTagName("largeur").item(0).getTextContent()));
	    setJoueur1(doc.getElementsByTagName("joueur1").item(0).getTextContent());
	    setJoueur2(doc.getElementsByTagName("joueur2").item(0).getTextContent());
	    setJoueurEnCours(Integer.valueOf(doc.getElementsByTagName("joueurEnCours").item(0).getTextContent()));

            NodeList nList = doc.getElementsByTagName("data");
            for (int temp = 0; temp < nList.getLength(); temp++) 
            {
                Node nNode = nList.item(temp);
                Configuration c = new Configuration(largeur, hauteur, null);
                c.setSerialized(nNode.getTextContent());
                pile.push(c);
            }
	    
        }
        catch(Exception e)
        {
            System.err.println("Erreur lors de l'ouverture de la sauvegarde.");
            return null;
        }

	setIterateur(pile.iterator());
	    
        return pile.iterator();
    }

    /**
     * Getter/Setter
     **/
    public void setLargeur(int largeur)
    {
	this.largeur = largeur;
    }

    public int getLargeur()
    {
	return largeur;
    }

    public void setHauteur(int hauteur)
    {
	this.hauteur = hauteur;
    }

    public int getHauteur()
    {
	return hauteur;
    }

    public void setJoueur1(String joueur1)
    {
	this.joueur1 = joueur1;
    }

    public String getJoueur1()
    {
	return joueur1;
    }

    public void setJoueur2(String joueur2)
    {
	this.joueur2 = joueur2;
    }

    public String getJoueur2()
    {
	return joueur2;
    }

    public void setJoueurEnCours(int joueurEnCours)
    {
	this.joueurEnCours = joueurEnCours;
    }

    public int getJoueurEnCours()
    {
	return joueurEnCours;
    }

    public void setIterateur(ListIterator<Configuration> it)
    {
	this.it = it;
    }

    public ListIterator<Configuration> getIterateur()
    {
	return it;
    }
}
