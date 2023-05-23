package PaooGame.States;

import PaooGame.RefLinks;

import java.awt.*;
import java.util.Vector;

public class MenuState extends State
{
    static int pressed = 0, hold = 0;
    int t = 0;
    private static int currentOption = 0;

    private static MenuState menu = null;
    Font menuFont;
    Vector<String> options;
    private MenuState(RefLinks refLink)
    {
        super(refLink);
        menuFont = new Font("Arial", Font.PLAIN,40);
        options = new Vector<>();
        options.add("Resume");
        options.add("Restart");
        options.add("Save game");
        options.add("Settings");
        options.add("Exit");
    }

    public static synchronized MenuState getInstance(RefLinks refLink)
    {
        if(menu == null)
        {
           menu = new MenuState(refLink);
        }
        return menu;
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
        g.setColor(Color.BLACK);
        g.fillRect(0,0, refLink.GetWidth(), refLink.GetHeight());

        g.setFont(menuFont);
        g.setColor(Color.white);
        g.drawString("Option = " + currentOption,100,100);

        for(int i = 0; i < options.size(); ++i)
        {
            int textwidht=(int)g.getFontMetrics().getStringBounds(options.get(i),g).getWidth();
            int textheight=(int)g.getFontMetrics().getStringBounds(options.get(0),g).getHeight();
            if(currentOption == i)
                g.setColor(Color.blue);
            else
                g.setColor(Color.white);
            g.drawString(options.get(i), refLink.GetWidth()/2 - 100,refLink.GetHeight()/2+i*40);
        }
    }
    public int midScreen(String s)
    {
        return refLink.GetWidth()/2 - menuFont.getSize() * s.length()/2;
    }
    public static int getCurrentOption()
    {
        return currentOption;
    }
}