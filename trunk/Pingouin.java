import Interface.*;
import Arbitre.*;
import Joueurs.*;

class Pingouin
{
    public static void main(String [] args)
    {
        // Ajoute un hook pour fermer proprement si Ctrl+C la partie
        Runtime.getRuntime().addShutdownHook(new Thread() 
            {
                public void run() 
                {
                    ArbitreManager.stopperPartie();
                }
            });

        // Création de l'interface via la fabrique (Si au moins un argument on lance la console)
        Interface inter = InterfaceFabrique.createInterface(args.length > 0);

        // Lance l'interface
        inter.run(args);
    }
}
