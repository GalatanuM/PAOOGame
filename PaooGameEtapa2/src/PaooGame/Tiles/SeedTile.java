package PaooGame.Tiles;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;

/*! \class public class SeedTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip samanta.
 */
public class SeedTile extends Tile
{
    private boolean in;

    private boolean parcurs;

    /*! \fn public SeedTile(int id)
        \brief Constructorul de initializare al clasei

        \param id Id-ul dalei util in desenarea hartii.
     */
    public SeedTile(int id)
    {
        super(Assets.seed, id);
    }
}