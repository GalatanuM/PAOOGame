package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class SeedTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip samanta.
 */
public class SeedTileSolid extends Tile
{
    /*! \fn public SeedTile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */
    public SeedTileSolid(int id)
    {
        super(Assets.seed, id);
        solid=true;
    }
}