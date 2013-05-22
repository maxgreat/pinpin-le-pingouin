package Interface;

/**
 * Décommentez celle que vous voulez utiliser
 **/
import Interface.Graphique.InterfaceGraphique;
import Interface.Console.InterfaceConsole;

public class InterfaceFabrique
{
    /**
     * Retourne l'interface en cours d'utilisation
     * Arguments : les arguments de la ligne de commande
     **/
    public static Interface createInterface(boolean console)
    {
        if (console)
        {
            // Interface console
            return (Interface)(new InterfaceConsole());
        }
        else
        {
            // Interface graphique
            return (Interface)(new InterfaceGraphique());
        }        
    }
}
    
            