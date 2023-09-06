package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
        /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage heroLeft;
    public static BufferedImage heroLeft1;
    public static BufferedImage heroLeft2;
    public static BufferedImage heroLeft3;
    public static BufferedImage heroRight;
    public static BufferedImage heroRight1;
    public static BufferedImage heroRight2;
    public static BufferedImage heroRight3;
    public static BufferedImage soil;
    public static BufferedImage seed;
    public static BufferedImage grass;
    public static BufferedImage mountain;
    public static BufferedImage water;
    public static BufferedImage heroUp;
    public static BufferedImage heroUp1;
    public static BufferedImage heroUp2;
    public static BufferedImage heroUp3;
    public static BufferedImage heroDown;
    public static BufferedImage heroDown1;
    public static BufferedImage heroDown2;
    public static BufferedImage heroDown3;
    public static BufferedImage tree;
    public static BufferedImage finish;
    public static BufferedImage menuBackground;

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        SpriteSheet menu = new SpriteSheet(ImageLoader.LoadImage("/textures/menubackground.png"));
            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        grass = sheet.crop(0, 0);
        soil = sheet.crop(1, 0);
        mountain = sheet.crop(3, 0);
        water = sheet.crop(4, 0);
        tree = sheet.crop(5,0);
        seed = sheet.crop (0,1);
        heroLeft = sheet.crop(0, 2);
        heroLeft1 = sheet.crop(0, 3);
        heroLeft2 = sheet.crop(0, 4);
        heroLeft3 = sheet.crop(0, 5);
        heroRight = sheet.crop(1, 2);
        heroRight1 = sheet.crop(1, 3);
        heroRight2 = sheet.crop(1, 4);
        heroRight3 = sheet.crop(1, 5);
        heroDown = sheet.crop(2, 2);
        heroDown1 = sheet.crop(2, 3);
        heroDown2 = sheet.crop(2, 4);
        heroDown3 = sheet.crop(2, 5);
        heroUp = sheet.crop(3, 2);
        heroUp1 = sheet.crop(3, 3);
        heroUp2 = sheet.crop(3, 4);
        heroUp3 = sheet.crop(3, 5);
        finish = sheet.crop(1,1);
        menuBackground = menu.crop(0,2,791,573);
    }
}
