package PaooGame.Maps;

import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map
{
    static protected int width;          /*!< Latimea hartii in numar de dale.*/
    static protected int height;         /*!< Inaltimea hartii in numar de dale.*/
    private final RefLinks refLink;
    private int [][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    public static int level = 0;

    /*! \fn public Map(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map(RefLinks refLink)
    {
            /// Retine referinta "shortcut".
        this.refLink = refLink;
        //width=40;
        //height=40;
    }


    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    public void Update()
    {

    }

    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.

        \param g Contextl grafi in care se realizeaza desenarea.
     */
    public void Draw(Graphics g)
    {

    }

    /*! \fn public Tile GetTile(int x, int y)
        \brief Intoarce o referinta catre dala aferenta codului din matrice de dale.

        In situatia in care dala nu este gasita datorita unei erori ce tine de cod dala, coordonate gresite etc se
        intoarce o dala predefinita (ex. grassTile, mountainTile)
     */
    public Tile GetTile(int x, int y)
    {
        if(x < 0 || y < 0 || x >= width || y >= height)
        {
            return Tile.grassTile;
        }
        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null)
        {
            return Tile.mountainTile;
        }
        return t;
    }

    /*! \fn public void SetTile(int x, int y, Tile a)
        \brief Seteaza in matricea de dale codul unei alte dale

        In situatia in care dala nu este gasita datorita unei erori ce tine de cod dala, nu se va produce nici o schimbare
     */
    public void SetTile(int x, int y, Tile a)
    {
        if(a != null)
            tiles[x][y]=a.GetId();
    }

    public int getLevel()
    {
        return level;
    }
    public void setLevel(int l)
    {
        level=l;
    }

    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
}