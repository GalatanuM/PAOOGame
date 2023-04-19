package PaooGame.Items;

import java.awt.*;
import java.awt.image.BufferedImage;

import PaooGame.RefLinks;
import PaooGame.Graphics.Assets;

import static java.lang.Math.sqrt;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    private BufferedImage image;    /*!< Referinta catre imaginea curenta a eroului.*/

    private int animatie;

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y)
    {
            ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
            ///Seteaza imaginea de start a eroului
        image = Assets.heroDown;
        animatie=0;
            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 0;
        normalBounds.y = 0;
        normalBounds.width = 48;
        normalBounds.height = 48;

            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.x = 10;
        attackBounds.y = 10;
        attackBounds.width = 38;
        attackBounds.height = 38;
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
            ///Actualizeaza imaginea

        if(refLink.GetKeyManager().left)
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
        if(refLink.GetKeyManager().right)
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
        if(refLink.GetKeyManager().up)
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
        if(refLink.GetKeyManager().down)
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
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput()
    {
            ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
            ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up && !refLink.GetKeyManager().left && !refLink.GetKeyManager().right)
        {
            yMove = -speed;
        }

            ///Verificare apasare tasta "jos"
        if(refLink.GetKeyManager().down && !refLink.GetKeyManager().left && !refLink.GetKeyManager().right)
        {
            yMove = speed;
        }

            ///Verificare apasare tasta "stanga"
        if(refLink.GetKeyManager().left && !refLink.GetKeyManager().up && !refLink.GetKeyManager().down)
        {
            xMove = -speed;
        }

            ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right && !refLink.GetKeyManager().up && !refLink.GetKeyManager().down)
        {
            xMove = speed;
        }

            ///Verificare apasare taste "stanga-sus"
        if(refLink.GetKeyManager().left && refLink.GetKeyManager().up)
        {
            xMove = -(float)sqrt(2*speed*speed)/2;
            yMove = -(float)sqrt(2*speed*speed)/2;
        }

            ///Verificare apasare taste "stanga-jos"
        if(refLink.GetKeyManager().left && refLink.GetKeyManager().down)
        {
            xMove = -(float)sqrt(2*speed*speed)/2;
            yMove = (float)sqrt(2*speed*speed)/2;
        }

            ///Verificare apasare taste "dreapta-sus"
        if(refLink.GetKeyManager().right && refLink.GetKeyManager().up)
        {
            xMove = (float)sqrt(2*speed*speed)/2;
            yMove = -(float)sqrt(2*speed*speed)/2;
        }

            ///Verificare apasare taste "dreapta-jos"
        if(refLink.GetKeyManager().right && refLink.GetKeyManager().down)
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
        ///g.setColor(Color.blue);
        ///g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }
}
