package PaooGame.States;

import PaooGame.Database.Database;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.Vector;

public class SaveState extends State
{
    static int pressed = 0, hold = 0;
    private static Color brown;
    int t = 0;
    private static int currentOption = 0;

    private static SaveState save = null;
    Font saveFont;
    Vector<String> options;
    private SaveState(RefLinks refLink)
    {
        super(refLink);
        saveFont = new Font("Arial", Font.PLAIN,40);
        brown= new Color(139,69,19);
        options = new Vector<>();
        options.add("Save your score");
        options.add("Exit to main menu");
    }

    public static synchronized SaveState getInstance(RefLinks refLink)
    {
        if(save == null)
        {
            save = new SaveState(refLink);
        }
        return save;
    }
    @Override
    public void Update()
    {
        Select();
    }
    public void Select()
    {
        int nextPos = 0;
        if (refLink.GetKeyManager().up)
        {
            pressed = 1;
            nextPos = -1;
        }
        else
        if (refLink.GetKeyManager().down)
        {
            pressed = 1;
            nextPos = 1;
        }
        else
        {
            pressed = 0;
        }
        if(hold == 0)
        {
            currentOption += nextPos;
            if (currentOption > options.size() - 1) {
                currentOption = 0;
            }
            if (currentOption < 0) {
                currentOption = options.size() - 1;
            }
        }
        hold = pressed;
    }

    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(Assets.menuBackground,0,0,refLink.GetWidth(), refLink.GetHeight(), null);
        g.setFont(saveFont);

        for(int i = 0; i < options.size(); ++i)
        {
            int textwidht=(int)g.getFontMetrics().getStringBounds(options.get(i),g).getWidth();
            int textheight=(int)g.getFontMetrics().getStringBounds(options.get(0),g).getHeight();
            if(currentOption == i)
                g.setColor(brown);
            else
                g.setColor(Color.orange);
            g.drawString(options.get(i), refLink.GetWidth()/2 - textwidht/2, refLink.GetHeight()/2-options.size()/2*textheight+i*textheight);
        }
    }
    public static int getCurrentOption()
    {
        return currentOption;
    }
    public static void resetCurrentOption()
    {
        currentOption=0;
    }
}