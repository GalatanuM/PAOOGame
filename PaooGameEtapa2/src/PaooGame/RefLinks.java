package PaooGame;

import PaooGame.Database.Database;
import PaooGame.Items.Hero;
import PaooGame.Maps.*;

import PaooGame.Input.KeyManager;
import PaooGame.Tiles.Tile;

import javax.xml.crypto.Data;

/*! \class public class RefLinks
    \brief Clasa ce retine o serie de referinte ale unor elemente pentru a fi usor accesibile.

    Altfel ar trebui ca functiile respective sa aiba o serie intreaga de parametri si ar ingreuna programarea.
 */
public class RefLinks
{
    private Game game;          /*!< Referinta catre obiectul Game.*/

    private Hero hero;          /*!< Referinta catre eroul curent.*/
    private Map map;            /*!< Referinta catre harta curenta.*/
    private Map1 map1;            /*!< Referinta catre harta1 curenta.*/
    private Map2 map2;            /*!< Referinta catre harta2 curenta.*/
    private Map3 map3;            /*!< Referinta catre harta3 curenta.*/
    private Map4 map4;            /*!< Referinta catre harta4 curenta.*/
    private Database database;

    /*! \fn public RefLinks(Game game)
        \brief Constructorul de initializare al clasei.

        \param game Referinta catre obiectul game.
     */
    public RefLinks(Game game)
    {
        this.game = game;
        this.hero = Hero.getInstance(this,0, 0);
        this.database = Database.getInstance(this);
    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza referinta catre managerul evenimentelor de tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return game.GetKeyManager();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei jocului.
     */
    public int GetWidth()
    {
        return game.GetWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei jocului.
     */
    public int GetHeight()
    {
        return game.GetHeight();
    }

    /*! \fn public Game GetGame()
        \brief Intoarce referinta catre obiectul Game.
     */
    public Game GetGame()
    {
        return game;
    }

    /*! \fn public Game GetGame()
        \brief Intoarce referinta catre obiectul Game.
     */
    public Hero GetHero()
    {
        return hero;
    }

    public Database getDatabase(){return database;}

    /*! \fn public void SetGame(Game game)
        \brief Seteaza referinta catre un obiect Game.

        \param game Referinta obiectului Game.
     */
    public void SetGame(Game game)
    {
        this.game = game;
    }

    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map GetMap()
    {
        return map;
    }
    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map1 GetMap1()
    {
        return map1;
    }
    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map2 GetMap2()
    {
        return map2;
    }
    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map3 GetMap3()
    {
        return map3;
    }

    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map4 GetMap4()
    {
        return map4;
    }

    /*! \fn public void SetMap(Map map)
        \brief Seteaza referinta catre harta curenta.

        \param map Referinta catre harta curenta.
     */
    public void SetMap(Map map)
    {
        this.map = map;
    }
    public void SetMap1(Map1 map)
    {
        this.map1 = map;
    }
    public void SetMap2(Map2 map)
    {
        this.map2 = map;
    }
    public void SetMap3(Map3 map)
    {
        this.map3 = map;
    }
    public void SetMap4(Map4 map)
    {
        this.map4 = map;
    }
}
