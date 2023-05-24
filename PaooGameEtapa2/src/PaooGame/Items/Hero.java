package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;

import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;
import static java.lang.Math.sqrt;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/
    private static Hero hero = null;
    private int animatie;

    private final int screenX;
    private final int screenY;

    /*! \fn private Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    private Hero(RefLinks refLink, float x, float y)
    {
            ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
            ///Seteaza imaginea de start a eroului
        image = Assets.heroDown;
        animatie=0;
            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 16;
        normalBounds.height = 24;

        screenX = (refLink.GetWidth() - Character.DEFAULT_CREATURE_WIDTH)/2;
        screenY = (refLink.GetHeight() - Character.DEFAULT_CREATURE_HEIGHT)/2;
    }

    public int getScreenX()
    {
        return screenX;
    }
    public int getScreenY()
    {
        return screenY;
    }

    public static synchronized Hero getInstance(RefLinks refLink, float x, float y)
    {
        if(hero == null)
        {
            hero = new Hero(refLink,x,y);
        }
        return hero;
    }

    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    @Override
    public void Move()
    {
        ///Modifica pozitia caracterului pe axa X.
        MoveX();
        ///Modifica pozitia caracterului pe axa Y.
        MoveY();
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    @Override
    public void MoveX()
    {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.

        //stanga sus
        //dreapta sus
        //stanga jos
        //dreapta jos
        Map map = null;
        switch (Map.level)
        {
            case 1: map=refLink.GetMap1(); break;
            case 2: map=refLink.GetMap2(); break;
            case 3: map=refLink.GetMap3(); break;
            case 4: map=refLink.GetMap4(); break;
        }
        if(     !map.GetTile((int)(x+normalBounds.x+ xMove )/Tile.TILE_WIDTH                       ,(int)(y+normalBounds.y )/Tile.TILE_HEIGHT).IsSolid() &&
                !map.GetTile((int)(x+normalBounds.x+ normalBounds.width + xMove)/Tile.TILE_WIDTH    ,(int)(y+normalBounds.y )/Tile.TILE_HEIGHT).IsSolid() &&
                !map.GetTile((int)(x+normalBounds.x + xMove)/Tile.TILE_WIDTH                       ,(int)(y+normalBounds.y + normalBounds.height)/Tile.TILE_HEIGHT).IsSolid() &&
                !map.GetTile((int)(x+normalBounds.x+ normalBounds.width + xMove)/Tile.TILE_WIDTH    ,(int)(y+normalBounds.y + normalBounds.height)/Tile.TILE_HEIGHT).IsSolid()
            )
        {
            if (xMove < 0 && x+xMove > -normalBounds.x) //la stanga
                x += xMove;
            if (xMove > 0 && x+xMove < refLink.GetMap().getWidth()*Tile.TILE_WIDTH - normalBounds.width - normalBounds.x) //la dreapta
                x += xMove;
        }
    }

    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    @Override
    public void MoveY()
    {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.

        //stanga sus
        //dreapta sus
        //stanga jos
        //dreapta jos

        Map map = null;
        switch (Map.level)
        {
            case 1: map=refLink.GetMap1(); break;
            case 2: map=refLink.GetMap2(); break;
            case 3: map=refLink.GetMap3(); break;
            case 4: map=refLink.GetMap4(); break;
        }
        //System.out.println(Map.level);
        if(     !map.GetTile((int)(x+normalBounds.x)/Tile.TILE_WIDTH                       ,(int)(y+normalBounds.y+yMove)/Tile.TILE_HEIGHT).IsSolid() &&
                !map.GetTile((int)(x+normalBounds.x+ normalBounds.width)/Tile.TILE_WIDTH    ,(int)(y+normalBounds.y+yMove)/Tile.TILE_HEIGHT).IsSolid() &&
                !map.GetTile((int)(x+normalBounds.x)/Tile.TILE_WIDTH                       ,(int)(y+normalBounds.y + normalBounds.height+yMove)/Tile.TILE_HEIGHT).IsSolid() &&
                !map.GetTile((int)(x+normalBounds.x+ normalBounds.width)/Tile.TILE_WIDTH    ,(int)(y+normalBounds.y + normalBounds.height+yMove)/Tile.TILE_HEIGHT).IsSolid()
        )
        {
            if (yMove < 0 && y+yMove > -normalBounds.y)
                y += yMove;
            if (yMove > 0 && y+yMove < refLink.GetMap().getHeight()*Tile.TILE_HEIGHT - normalBounds.height - normalBounds.y)
                y += yMove;
        }
    }

    public void UpdateTile()
    {
        //stanga sus == dreapta jos (hitbox)
        //stanga sus == soilTile

        Map map = null;
        switch (Map.level)
        {
            case 1: map=refLink.GetMap1(); break;
            case 2: map=refLink.GetMap2(); break;
            case 3: map=refLink.GetMap3(); break;
            case 4: map=refLink.GetMap4(); break;
        }

        if(     (int)(refLink.GetHero().GetX()+ refLink.GetHero().getBoundX())/Tile.TILE_WIDTH ==
                (int)(refLink.GetHero().GetX()+refLink.GetHero().getBoundX()+refLink.GetHero().getBoundWidth())/Tile.TILE_WIDTH &&
                (int)(refLink.GetHero().GetY()+ refLink.GetHero().getBoundY())/Tile.TILE_HEIGHT ==
                        (int)(refLink.GetHero().GetY()+ refLink.GetHero().getBoundY()+refLink.GetHero().getBoundHeight())/Tile.TILE_HEIGHT &&
                map.GetTile((int)(x+normalBounds.x)/Tile.TILE_WIDTH                       ,(int)(y+normalBounds.y)/Tile.TILE_HEIGHT) == Tile.soilTile
        )
        {
            map.SetTile((int)(x+normalBounds.x)/Tile.TILE_WIDTH,(int)(y+normalBounds.y)/Tile.TILE_HEIGHT,Tile.seedTile);
        }
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
            ///Verifica daca a fost apasata o tasta
        GetInput();
            ///Actualizeaza pozitia
        Move();
            ///Actualizeaza harta
        UpdateTile();
            ///Actualizeaza imaginea


        ///Stanga
        if(refLink.GetKeyManager().left && !refLink.GetKeyManager().up && !refLink.GetKeyManager().down && !refLink.GetKeyManager().right)
        {
            if(image == Assets.heroLeft)
            {
                if(animatie==6)
                {
                    image = Assets.heroLeft1;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroLeft1)
            {
                if(animatie==6)
                {
                    image = Assets.heroLeft2;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroLeft2)
            {
                if(animatie==6)
                {
                    image = Assets.heroLeft3;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroLeft3)
            {
                if(animatie==6)
                {
                    image = Assets.heroLeft;
                    animatie=0;
                }
                animatie++;
            }
            else
            {
                image = Assets.heroLeft;
                animatie=0;
            }
        }
        else
        ///Dreapta
        if(refLink.GetKeyManager().right && !refLink.GetKeyManager().up && !refLink.GetKeyManager().down && !refLink.GetKeyManager().left)
        {
            if(image == Assets.heroRight)
            {
                if(animatie==6)
                {
                    image = Assets.heroRight1;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroRight1)
            {
                if(animatie==6)
                {
                    image = Assets.heroRight2;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroRight2)
            {
                if(animatie==6)
                {
                    image = Assets.heroRight3;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroRight3)
            {
                if(animatie==6)
                {
                    image = Assets.heroRight;
                    animatie=0;
                }
                animatie++;
            }
            else
            {
                image = Assets.heroRight;
                animatie=0;
            }
        }
        else
        ///Sus
        if(refLink.GetKeyManager().up && !refLink.GetKeyManager().right && !refLink.GetKeyManager().left && !refLink.GetKeyManager().down)
        {
            if(image == Assets.heroUp)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp1;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroUp1)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp2;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroUp2)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp3;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroUp3)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp;
                    animatie=0;
                }
                animatie++;
            }
            else
            {
                image = Assets.heroUp;
                animatie=0;
            }
        }
        else
        ///Jos
        if(refLink.GetKeyManager().down && !refLink.GetKeyManager().right && !refLink.GetKeyManager().left && !refLink.GetKeyManager().up)
        {
            if(image == Assets.heroDown)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown1;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroDown1)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown2;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroDown2)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown3;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroDown3)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown;
                    animatie=0;
                }
                animatie++;
            }
            else
            {
                image = Assets.heroDown;
                animatie=0;
            }
        }
        else
        ///Stanga-Sus / Dreapta-Sus
        if( ((refLink.GetKeyManager().up && refLink.GetKeyManager().right) ^ (refLink.GetKeyManager().up && refLink.GetKeyManager().left)) && !refLink.GetKeyManager().down)
        {
            if(image == Assets.heroUp)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp1;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroUp1)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp2;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroUp2)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp3;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroUp3)
            {
                if(animatie==6)
                {
                    image = Assets.heroUp;
                    animatie=0;
                }
                animatie++;
            }
            else
            {
                image = Assets.heroUp;
                animatie=0;
            }
        }
        else
        ///Stanga-Jos / Dreapta-Jos
        if( ((refLink.GetKeyManager().down && refLink.GetKeyManager().right) ^ (refLink.GetKeyManager().down && refLink.GetKeyManager().left)) && !refLink.GetKeyManager().up )
        {
            if(image == Assets.heroDown)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown1;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroDown1)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown2;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroDown2)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown3;
                    animatie=0;
                }
                animatie++;
            }
            else if (image == Assets.heroDown3)
            {
                if(animatie==6)
                {
                    image = Assets.heroDown;
                    animatie=0;
                }
                animatie++;
            }
            else
            {
                image = Assets.heroDown;
                animatie=0;
            }
        }
        else
        {
            image = Assets.heroDown;
            animatie=0;
        }
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput()
    {
            ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;

            ///Verificare apasare tasta shift
        if(refLink.GetKeyManager().shift)
        {
            speed=4.0f;
        }
        else speed=3.0f;

            ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up && !refLink.GetKeyManager().left && !refLink.GetKeyManager().right && !refLink.GetKeyManager().down)
        {
            yMove = -speed;
        }

            ///Verificare apasare tasta "jos"
        if(refLink.GetKeyManager().down && !refLink.GetKeyManager().left && !refLink.GetKeyManager().right && !refLink.GetKeyManager().up)
        {
            yMove = speed;
        }

            ///Verificare apasare tasta "stanga"
        if(refLink.GetKeyManager().left && !refLink.GetKeyManager().up && !refLink.GetKeyManager().down && !refLink.GetKeyManager().right)
        {
            xMove = -speed;
        }

            ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right && !refLink.GetKeyManager().up && !refLink.GetKeyManager().down && !refLink.GetKeyManager().left)
        {
            xMove = speed;
        }

            ///Verificare apasare taste "stanga-sus"
        if(refLink.GetKeyManager().left && refLink.GetKeyManager().up && !refLink.GetKeyManager().down && !refLink.GetKeyManager().right)
        {
            xMove = -(float)sqrt(2*speed*speed)/2;
            yMove = -(float)sqrt(2*speed*speed)/2;
        }

            ///Verificare apasare taste "stanga-jos"
        if(refLink.GetKeyManager().left && refLink.GetKeyManager().down && !refLink.GetKeyManager().up && !refLink.GetKeyManager().right)
        {
            xMove = -(float)sqrt(2*speed*speed)/2;
            yMove = (float)sqrt(2*speed*speed)/2;
        }

            ///Verificare apasare taste "dreapta-sus"
        if(refLink.GetKeyManager().right && refLink.GetKeyManager().up && !refLink.GetKeyManager().left && !refLink.GetKeyManager().down)
        {
            xMove = (float)sqrt(2*speed*speed)/2;
            yMove = -(float)sqrt(2*speed*speed)/2;
        }

            ///Verificare apasare taste "dreapta-jos"
        if(refLink.GetKeyManager().right && refLink.GetKeyManager().down && !refLink.GetKeyManager().up && !refLink.GetKeyManager().left)
        {
            xMove = (float)sqrt(2*speed*speed)/2;
            yMove = (float)sqrt(2*speed*speed)/2;
        }
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafic in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(image, (int)x, (int)y, width, height, null);

            ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        g.setColor(Color.blue);
        g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }
}
