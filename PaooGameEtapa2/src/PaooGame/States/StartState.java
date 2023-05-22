package PaooGame.States;

import PaooGame.RefLinks;

import java.awt.*;
import java.util.Vector;

public class StartState extends State
{
    static int pressed = 0, hold = 0;
    int t = 0;
    private static int currentOption = 0;

    private static StartState start = null;
    Font startFont;
    Vector<String> options;
    private StartState(RefLinks refLink)
    {
        super(refLink);
        startFont = new Font("Arial", Font.PLAIN,40);
        options = new Vector<>();
        options.add("Start");
        options.add("Highscores");
        options.add("Load save");
        options.add("Settings");
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
        g.setColor(Color.BLACK);
        g.fillRect(0,0, refLink.GetWidth(), refLink.GetHeight());

        g.setFont(startFont);
        g.setColor(Color.white);
        g.drawString("Option = " + currentOption,100,100);

        for(int i = 0; i < options.size(); ++i)
        {
            if(currentOption == i)
                g.setColor(Color.blue);
            else
                g.setColor(Color.white);
            g.drawString(options.get(i), refLink.GetWidth()/2 - 100,refLink.GetHeight()/2+i*40);
        }
    }
    public int midScreen(String s)
    {
        return refLink.GetWidth()/2 - startFont.getSize() * s.length()/2;
    }
    public static int getCurrentOption()
    {
        return currentOption;
    }
}