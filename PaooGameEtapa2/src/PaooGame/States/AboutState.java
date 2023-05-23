package PaooGame.States;

import PaooGame.Database.Database;
import PaooGame.RefLinks;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*! \class public class AboutState extends State
    \brief Implementeaza notiunea de credentiale (about)
 */
public class AboutState extends State
{
    private static AboutState aboutState = null;

    Font aboutFont;

    /*! \fn public AboutState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    private AboutState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza.
        super(refLink);
        aboutFont = new Font("Arial", Font.PLAIN,30);
    }

    public static synchronized AboutState getInstance(RefLinks refLink)
    {
        if(aboutState == null)
        {
            aboutState = new AboutState(refLink);
        }
        return aboutState;
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniu about.
     */
    @Override
    public void Update()
    {

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniu about.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, refLink.GetWidth(), refLink.GetHeight());
        g.setFont(aboutFont);
        g.setColor(Color.white);

        List<String> text = new ArrayList<>();

        text.add("\"Aranara Flower Garden\" este un joc");
        text.add("distractiv și educativ, care pune la încercare ");
        text.add("abilitățile de rezolvare a puzzle-urilor ale ");
        text.add("jucatorilor, dar și creativitatea si imaginatia ");
        text.add("lor in crearea unor gradini virtuale unice.");
        for (int i = 0; i < text.size(); ++i)
        {
            int textwidht=(int)g.getFontMetrics().getStringBounds(text.get(i),g).getWidth();
            int textheight=(int)g.getFontMetrics().getStringBounds(text.get(0),g).getHeight();
            g.drawString(text.get(i), refLink.GetWidth() / 2 - textwidht/2, refLink.GetHeight()/2 - textheight*text.size()/2 + (i+1)*textheight);
        }
    }
}
