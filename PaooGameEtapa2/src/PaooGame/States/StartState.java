package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.Vector;

public class StartState extends State
{
    static int pressed = 0, hold = 0;
    int t = 0;
    private static int currentOption = 0;

    private static Color brown;

    private static StartState start = null;
    Font startFont;
    Font startFont1;
    Font startFont2;
    Vector<String> options;
    private StartState(RefLinks refLink)
    {
        super(refLink);
        startFont = new Font("", Font.PLAIN,40);
        startFont1 = new Font("", Font.PLAIN,60);
        startFont2 = new Font("", Font.BOLD,60);
        options = new Vector<>();
        brown= new Color(139,69,19);
        options.add("Start game");
        options.add("Highscores");
        options.add("Load game");
        options.add("Difficulty");
        options.add("About");
        options.add("Exit");
    }

    public static synchronized StartState getInstance(RefLinks refLink)
    {
        if(start == null)
        {
            start = new StartState(refLink);
        }
        return start;
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

        /*g.setColor(Color.BLACK);
        g.fillRect(0,0, refLink.GetWidth(), refLink.GetHeight());*/

        g.setFont(startFont1);
        g.setColor(Color.black);
        g.drawString("Aranara Flower Garden", refLink.GetWidth()/2 - 320+5,refLink.GetHeight()/2-265+5);
        g.setFont(startFont1);
        g.setColor(new Color(210,105,30));
        g.drawString("Aranara Flower Garden", refLink.GetWidth()/2 - 320,refLink.GetHeight()/2-265);
        g.setFont(startFont);
        for(int i = 0; i < options.size(); ++i)
        {
            int textwidht=(int)g.getFontMetrics().getStringBounds(options.get(i),g).getWidth();
            int textheight=(int)g.getFontMetrics().getStringBounds(options.get(0),g).getHeight();
            if(currentOption == i)
                g.setColor(brown);
            else
                g.setColor(Color.orange);

            g.drawString(options.get(i), refLink.GetWidth()/2 - textwidht/2,refLink.GetHeight()/2-textheight*options.size()/2+(i+1)*textheight);
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