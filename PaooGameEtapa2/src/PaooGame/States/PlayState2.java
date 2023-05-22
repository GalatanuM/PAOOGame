package PaooGame.States;

import PaooGame.Items.Hero;
import PaooGame.Maps.Map1;
import PaooGame.Maps.Map2;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Tiles.Tile;

import java.awt.*;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState2 extends PlayState
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map2 map;    /*!< Referinta catre harta curenta.*/

    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei
        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState2(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map2(refLink);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap2(map);
        refLink.GetMap2().setLevel(2);
        ///Construieste eroul
        hero = Hero.getInstance(refLink,0, 0);
        hero.SetX(map.spawnX()*Tile.TILE_WIDTH);
        hero.SetY(map.spawnY()*Tile.TILE_HEIGHT);
    }

    public PlayState2(RefLinks refLink,String path)
    {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map2(refLink,path);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap2(map);
        refLink.GetMap2().setLevel(2);
        ///Construieste eroul
        hero = Hero.getInstance(refLink,0, 0);
        hero.SetX(map.spawnX()*Tile.TILE_WIDTH);
        hero.SetY(map.spawnY()*Tile.TILE_HEIGHT);
    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
        map.Update();
        hero.Update();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        map.Draw(g);
        hero.Draw(g);
        g.setFont(new Font("Arial", Font.PLAIN,40));
        g.setColor(Color.white);
        g.drawString("" + scor/60,(int)refLink.GetHero().GetX(),(int)refLink.GetHero().GetY());
    }
}