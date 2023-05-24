package PaooGame.Maps;

import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.States.MenuState;
import PaooGame.States.PlayState1;
import PaooGame.States.State;
import PaooGame.Tiles.GrassTile;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*! \class public class Map
    \brief Implementeaza notiunea de harta a jocului.
 */
public class Map4 extends Map
{
    private RefLinks refLink;   /*!< O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.*/
    private int [][] tiles;     /*!< Referinta catre o matrice cu codurile dalelor ce vor construi harta.*/
    private int soilTileContor; /*!< Contor pentru numarul de tile-uri de tip pamant.*/

    private static boolean over=false;
    public Map4 map4ref;
    private final Point spawnPoint = new Point();
    private State menuState;    /*!< Referinta catre menu.*/

    /*! \fn public Map(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinte catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public Map4(RefLinks refLink)
    {
        super(refLink);
        /// Retine referinta "shortcut".
        this.refLink = refLink;
        over=false;
        level=4;
        ///incarca harta de start. Functia poate primi ca argument id-ul hartii ce poate fi incarcat.
        LoadWorld(level);
        map4ref=this;
    }

    public Map4(RefLinks refLink,String path)
    {
        super(refLink);
        /// Retine referinta "shortcut".
        this.refLink = refLink;
        over=false;
        level=4;
        ///incarca harta de start. Functia poate primi ca argument id-ul hartii ce poate fi incarcat.
        LoadWorld(path);
        map4ref=this;
    }

    /*! \fn public  void Update()
        \brief Actualizarea hartii in functie de evenimente (un copac a fost taiat)
     */
    public void Update()
    {
        int heroPosX_stg = (int)(refLink.GetHero().GetX()+ refLink.GetHero().getBoundX())/Tile.TILE_WIDTH;
        int heroPosX_drp = (int)(refLink.GetHero().GetX()+refLink.GetHero().getBoundX()+refLink.GetHero().getBoundWidth())/Tile.TILE_WIDTH;

        int heroPosY_sus = (int)(refLink.GetHero().GetY()+ refLink.GetHero().getBoundY())/Tile.TILE_HEIGHT;
        int heroPosY_jos = (int)(refLink.GetHero().GetY()+ refLink.GetHero().getBoundY()+refLink.GetHero().getBoundHeight())/Tile.TILE_HEIGHT;
        for(int y = 0; y < width; y++)
        {
            for(int x = 0; x < height; x++)
            {
                if(GetTile(x,y).GetId()==5)
                {
                    if(x!=heroPosX_drp && x!=heroPosX_stg || y!=heroPosY_sus && y!=heroPosY_jos)
                    {
                        SetTile(x,y,Tile.seedTileSolid);
                        soilTileContor--;
                    }
                }
            }
        }

        if(soilTileContor==0 &&
                GetTile((int)(refLink.GetHero().GetX()+ refLink.GetHero().getBoundX())/Tile.TILE_WIDTH,(int)(refLink.GetHero().GetY()+ refLink.GetHero().getBoundY())/Tile.TILE_HEIGHT) == Tile.finishTile &&
                GetTile((int)(refLink.GetHero().GetX()+refLink.GetHero().getBoundX()+refLink.GetHero().getBoundWidth())/Tile.TILE_WIDTH,(int)(refLink.GetHero().GetY()+ refLink.GetHero().getBoundY()+refLink.GetHero().getBoundHeight())/Tile.TILE_HEIGHT)==Tile.finishTile)
        {
            over=true;
        }
    }

    public static boolean isOver()
    {
        return over;
    }

    public int getSoilTileContor()
    {
        return soilTileContor;
    }


    /*! \fn public void Draw(Graphics g)
        \brief Functia de desenare a hartii.
        \param g Contextl grafi in care se realizeaza desenarea.
     */
    public void Draw(Graphics g) {
        int offsetX = (int) ((refLink.GetGame().GetWidth()-refLink.GetHero().GetWidth())/2 - refLink.GetHero().GetX());
        int offsetY = (int) ((refLink.GetGame().GetHeight()-refLink.GetHero().GetHeight())/2 - refLink.GetHero().GetY());
        g.translate(offsetX, offsetY);
        for (int y =0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                Tile.grassTile.Draw(g, (int) x * Tile.TILE_HEIGHT, (int) y * Tile.TILE_WIDTH);
                GetTile(x, y).Draw(g, (int) x * Tile.TILE_HEIGHT, (int) y * Tile.TILE_WIDTH);
            }
        }

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

    public void LoadWorld(int level)
    {
        try {
            File inputFile = new File("res/maps/Map"+level+".txt");
            Scanner scanner = new Scanner(inputFile);
            if (scanner.hasNextInt())
            {
                height = scanner.nextInt();
                width = scanner.nextInt();
                tiles = new int[width][height];
                soilTileContor = scanner.nextInt();
                spawnPoint.setLocation(scanner.nextInt(), scanner.nextInt());
                for(int y = 0; y < height; y++)
                {
                    for(int x = 0; x < width; x++)
                    {
                        tiles[x][y] = scanner.nextInt();
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void LoadWorld(String path)
    {
        try {
            File inputFile = new File(path);
            Scanner scanner = new Scanner(inputFile);
            if (scanner.hasNextInt())
            {
                height = scanner.nextInt();
                width = scanner.nextInt();
                tiles = new int[width][height];
                soilTileContor = scanner.nextInt();
                spawnPoint.setLocation(scanner.nextInt(), scanner.nextInt());
                for(int y = 0; y < height; y++)
                {
                    for(int x = 0; x < width; x++)
                    {
                        tiles[x][y] = scanner.nextInt();
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public int spawnX()
    {
        return spawnPoint.x;
    }

    public int spawnY()
    {
        return spawnPoint.y;
    }

    public String mapToString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                sb.append(tiles[j][i]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}