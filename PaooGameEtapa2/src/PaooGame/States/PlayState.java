package PaooGame.States;

import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Tiles.Tile;

import java.awt.*;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private static PlayState playState = null;
    private Map map;    /*!< Referinta catre harta curenta.*/

    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    PlayState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza
        super(refLink);
        /*    ///Construieste harta jocului
        map = new Map(refLink);
            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
            ///Construieste eroul
        hero = Hero.getInstance(refLink,0, 0);
        hero.SetX(map.spawnX()*Tile.TILE_WIDTH);
        hero.SetY(map.spawnY()*Tile.TILE_HEIGHT);*/
    }

    public static synchronized PlayState getInstance(RefLinks refLink)
    {
        if(playState == null)
        {
            playState = new PlayState(refLink);
        }
        return playState;
    }


    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
       /* map.Update();
        hero.Update();*/
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        /*map.Draw(g);
        hero.Draw(g);*/
    }
}
