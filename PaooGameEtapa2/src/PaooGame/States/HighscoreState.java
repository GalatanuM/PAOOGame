package PaooGame.States;
import PaooGame.Database.Database;
import PaooGame.RefLinks;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.List;

public class HighscoreState extends State
{
    private static int currentOption = 0;

    private List<String> highscores;
    private static HighscoreState highscore = null;
    Font saveFont;
    private HighscoreState(RefLinks refLink)
    {
        super(refLink);
        saveFont = new Font("Arial", Font.PLAIN,40);
    }

    public static synchronized HighscoreState getInstance(RefLinks refLink)
    {
        if(highscore == null)
        {
            highscore = new HighscoreState(refLink);
        }
        return highscore;
    }
    @Override
    public void Update()
    {
    }

    @Override
    public void Draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, refLink.GetWidth(), refLink.GetHeight());
        g.setFont(saveFont);
        g.setColor(Color.white);
        highscores=Database.loadHighscores();

        if(highscores!=null)
        {
            for (int i = 0; i < highscores.size(); ++i)
            {
                int textwidht=(int)g.getFontMetrics().getStringBounds(highscores.get(i),g).getWidth();
                int textheight=(int)g.getFontMetrics().getStringBounds(highscores.get(0),g).getHeight();
                g.drawString((i+1)+". "+highscores.get(i), refLink.GetWidth() / 2 - textwidht/2, refLink.GetHeight()/2 - textheight*highscores.size()/2 + (i+1)*textheight);
            }
        }
    }
}