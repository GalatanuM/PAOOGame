package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class WaterTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip apa.
 */public class WaterTile extends Tile
{
    /*! \fn public WaterTile(int id)
       \brief Constructorul de initializare al clasei

       \param id Id-ul dalei util in desenarea hartii.
    */
    public WaterTile(int id)
    {
        super(Assets.water, id);
        solid=true;
    }
}
