import Interface.*;
import Arbitre.*;
import Joueurs.*;

class Pingouin
{
    public static void main(String [] args)
    {
        // Création de l'interface via la fabrique
        Interface inter = InterfaceFabrique.createInterface();

        // Lance l'interface
        inter.run(args);
    }
}
