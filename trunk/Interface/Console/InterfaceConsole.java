package Interface.Console;

import Interface.*;

public class InterfaceConsole implements Interface
{
    /**
     * Lance l'interface graphique
     **/
    public void run(String [] args)
    {
        if (args.length > 0)
        {
            System.out.println("Arguments :");
            for (int i = 0; i < args.length; i++)
                System.out.println(args[i]);
        }
    }

    /**
     * Doit réafficher la configuration à l'écran
     **/
    public void repaint()
    {
        System.out.println("Affiche la configuration à l'écran");
    }
}
