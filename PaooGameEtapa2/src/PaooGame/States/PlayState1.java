package PaooGame.States;

import PaooGame.Items.Hero;
import PaooGame.Maps.Map1;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;

/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState1 extends PlayState
{
    private Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/
    private Map1 map;    /*!< Referinta catre harta curenta.*/

    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei
        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState1(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map1(refLink);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap1(map);
        refLink.GetMap1().setLevel(1);
        ///Construieste eroul
        hero = Hero.getInstance(refLink,0, 0);
        hero.SetX(map.spawnX()*Tile.TILE_WIDTH);
        hero.SetY(map.spawnY()*Tile.TILE_HEIGHT);
    }
    public PlayState1(RefLinks refLink,String path)
    {
        ///Apel al constructorului clasei de baza
        super(refLink);
        ///Construieste harta jocului
        map = new Map1(refLink,path);
        ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap1(map);
        refLink.GetMap1().setLevel(1);
        refLink.GetGame().setLevelFinished(0);
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
        g.setColor(Color.orange);
        g.drawString("Target time: " + targetscor/60,(int)refLink.GetHero().GetX()-refLink.GetGame().GetWidth()/2 +50,(int)refLink.GetHero().GetY()-refLink.GetGame().GetHeight()/2+70);
        g.drawString("Initial time: " + lastscor/60,(int)refLink.GetHero().GetX()-refLink.GetGame().GetWidth()/2 +50,(int)refLink.GetHero().GetY()-refLink.GetGame().GetHeight()/2+110);
        g.drawString("Current time: " + scor/60,(int)refLink.GetHero().GetX()-refLink.GetGame().GetWidth()/2 +50,(int)refLink.GetHero().GetY()-refLink.GetGame().GetHeight()/2+150);
        //System.out.println((int)refLink.GetHero().GetX()+ " " +(int)refLink.GetHero().GetY());
    }
}
