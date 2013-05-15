import Interface.*;
import Arbitre.*;
import Joueurs.*;

class Gaufre
{
    public static void main(String [] args)
    {
        // Instancie l'arbitre et l'interface
        Interface inter = new Interface();

        // Lance l'interface
        inter.run(args);
    }
}
