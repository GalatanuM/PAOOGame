package PaooGame;

import PaooGame.Database.Database;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Input.KeyManager;
import PaooGame.Maps.*;
import PaooGame.States.*;
import PaooGame.Tiles.Tile;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferStrategy;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private boolean         runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics        g;          /*!< Referinta catre un context grafic.*/

        ///Available states
    private State playState1;            /*!< Referinta catre joc1.*/
    private State playState2;            /*!< Referinta catre joc2.*/
    private State playState3;            /*!< Referinta catre joc3.*/
    private State playState4;            /*!< Referinta catre joc4.*/
    private State menuState;            /*!< Referinta catre menu.*/
    private State saveState;            /*!< Referinta catre meniul de salvare.*/
    private State startState;            /*!< Referinta catre meniul de start.*/
    private State settingsState;        /*!< Referinta catre setari.*/
    private State aboutState;           /*!< Referinta catre about.*/
    private State highscoreState;           /*!< Referinta catre about.*/
    private KeyManager keyManager;      /*!< Referinta catre obiectul care gestioneaza intrarile din partea utilizatorului.*/

    private Map map;
    private RefLinks refLink;            /*!< Referinta catre un obiect a carui sarcina este doar de a retine diverse referinte pentru a fi usor accesibile.*/

    public int levelsFinished=0;

    private Tile tile; /*!< variabila membra temporara. Este folosita in aceasta etapa doar pentru a desena ceva pe ecran.*/

    Database dataBase; /*!< Referinta catre baza de date.*/

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height)
    {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        wnd = new GameWindow(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = false;
            ///Construirea obiectului de gestiune a evenimentelor de tastatura
        keyManager = new KeyManager();
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame()
    {
            /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
            ///Sa ataseaza ferestrei managerul de tastatura pentru a primi evenimentele furnizate de fereastra.
        wnd.GetWndFrame().addKeyListener(keyManager);
            ///Se incarca toate elementele grafice (dale)
        Assets.Init();
            ///Se construieste obiectul de tip shortcut ce va retine o serie de referinte catre elementele importante din program.
        refLink = new RefLinks(this);
            ///Definirea starilor programului
        menuState       = MenuState.getInstance(refLink);
        settingsState   = SettingsState.getInstance(refLink);
        startState   = StartState.getInstance(refLink);
        aboutState      = AboutState.getInstance(refLink);
        saveState       = SaveState.getInstance(refLink);
        dataBase        = Database.getInstance(refLink);
        highscoreState = HighscoreState.getInstance(refLink);
        map             = new Map(refLink);
            ///Seteaza starea implicita cu care va fi lansat programul in executie
        State.SetState(startState);
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()
    {
            /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

            /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (runState == true)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }

    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()
    {
        if(runState == false)
        {
                /// Se actualizeaza flagul de stare a threadului
            runState = true;
                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame()
    {
        if(runState == true)
        {
                /// Actualizare stare thread
            runState = false;
                /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try
            {
                    /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                    /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                gameThread.join();
            }
            catch(InterruptedException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else
        {
                /// Thread-ul este oprit deja.
            return;
        }
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {
        ///Determina starea tastelor
        keyManager.Update();
        ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
        if (State.GetState() != null)
        {
            if(State.GetState()==playState1 || State.GetState()==playState2 || State.GetState()==playState3 || State.GetState()==playState4)
            {
                State.incScor();
            }
            ///Actualizez starea curenta a jocului daca exista.
            State.GetState().Update();
            if (refLink.GetKeyManager().esc && !refLink.GetKeyManager().escPressed)
            {
                System.out.println("esc apasat");
                refLink.GetKeyManager().escPressed = true;
                if (State.GetState() == playState1 || State.GetState() == playState2 || State.GetState() == playState3 || State.GetState()==playState4)
                {
                    State.SetState(menuState);
                }
                else
                if(State.GetState()==menuState || State.GetState()==highscoreState || State.GetState()==aboutState || State.GetState()==settingsState)
                {
                    State.SetState(State.getPreviousState());
                }
            }
            else if (!refLink.GetKeyManager().esc)
            {
                refLink.GetKeyManager().escPressed = false;
            }
            if (refLink.GetKeyManager().enter && !refLink.GetKeyManager().enterPressed)
            {
                System.out.println("enter apasat");
                refLink.GetKeyManager().enterPressed=true;
                if(State.GetState()==startState)
                {
                    if (StartState.getCurrentOption() == 0) {
                        //start
                        // TO BE RE-DESIGNED AFTER ADDING DATABSE
                            State.resetScore();
                            Map.level=0;
                            playState1=null;
                            playState1=new PlayState1(refLink);
                            State.SetState(playState1);
                    }
                    if (StartState.getCurrentOption() == 1) {
                        //highscores
                        State.SetState(highscoreState);
                    }
                    if (StartState.getCurrentOption() == 2) {
                        //load save
                        Database.DatabaseLoadGame();
                        if(Database.isLoaded())
                        {
                            if(levelsFinished==0)
                            {
                                playState1=new PlayState1(refLink,"./src/PaooGame/Database/map.txt");
                                State.SetState(playState1);
                            }
                            if(levelsFinished==1)
                            {
                                playState2=new PlayState2(refLink,"./src/PaooGame/Database/map.txt");
                                State.SetState(playState2);
                            }
                            if(levelsFinished==2)
                            {
                                playState3=new PlayState3(refLink,"./src/PaooGame/Database/map.txt");
                                State.SetState(playState3);
                            }
                            if(levelsFinished==3)
                            {
                                playState4=new PlayState4(refLink,"./src/PaooGame/Database/map.txt");
                                State.SetState(playState4);
                            }
                        }
                        else
                        {
                            playState1=null;
                            playState1=new PlayState1(refLink);
                            State.resetScore();
                            Map.level=1;
                            State.SetState(playState1);
                        }
                    }
                    if (StartState.getCurrentOption() == 3) {
                        //settings
                        State.SetState(settingsState);
                    }
                    if (StartState.getCurrentOption() == 4) {
                        //about
                        State.SetState(aboutState);
                    }
                    if (StartState.getCurrentOption() == 5) {
                        wnd.GetWndFrame().dispose();
                        System.exit(0);
                    }
                }
                else
                if(State.GetState()==saveState)
                {
                    if(SaveState.getCurrentOption()==0)
                    {
                        //save score
                        Database.databaseSaveHighscore();
                        Database.loadHighscores();
                        playState1=null;
                        playState2=null;
                        playState3=null;
                        playState4=null;
                        State.resetScore();
                        refLink.GetKeyManager().enterPressed=true;
                        State.SetState(startState);
                    }
                    if (SaveState.getCurrentOption() == 1) {
                        //exit without save
                        playState1=null;
                        playState2=null;
                        playState3=null;
                        playState4=null;
                        State.resetScore();
                        refLink.GetKeyManager().enterPressed=true;
                        State.SetState(startState);
                    }
                }
                else
                if (State.GetState()==menuState)
                {
                    if (MenuState.getCurrentOption() == 0) {
                        //resume
                        State.SetState(State.getPreviousState());
                    }
                    if (MenuState.getCurrentOption() == 1) {
                        //restart
                        switch (levelsFinished)
                        {
                            case 0:
                                State.setScor(State.getLastscor());
                                playState1=null;
                                playState1=new PlayState1(refLink);
                                State.SetState(playState1);
                                break;
                            case 1:
                                State.setScor(State.getLastscor());
                                playState2=null;
                                playState2=new PlayState2(refLink);
                                State.SetState(playState2);
                                break;
                            case 2:
                                State.setScor(State.getLastscor());
                                playState3=null;
                                playState3=new PlayState3(refLink);
                                State.SetState(playState3);
                                break;
                            case 3:
                                State.setScor(State.getLastscor());
                                playState4=null;
                                playState4=new PlayState4(refLink);
                                State.SetState(playState4);
                                break;
                        }
                    }
                    if (MenuState.getCurrentOption() == 2) {

                        //save game
                        Database.databaseNewGame();
                        Database.DatabaseSaveGame();
                        playState1=null;
                        playState2=null;
                        playState3=null;
                        playState4=null;
                        State.resetScore();
                        Map.level=0;
                        State.SetState(startState);
                    }
                    if (MenuState.getCurrentOption() == 3) {
                        //setari
                        State.SetState(settingsState);
                    }
                    if (MenuState.getCurrentOption() == 4) {
                        //exit to main menu
                        State.SetState(startState);
                    }
                }
            }
            else if (!refLink.GetKeyManager().enter)
            {
                refLink.GetKeyManager().enterPressed = false;
            }
            if(State.GetState() == playState1 && Map1.isOver())
            {
                playState2=null;
                playState2=new PlayState2(refLink);
                levelsFinished=1;
                State.SetState(playState2);
                State.setLastscor(State.getScor());
            }
            if(State.GetState()==playState2 && Map2.isOver())
            {
                playState3=null;
                playState3=new PlayState3(refLink);
                levelsFinished=2;
                State.SetState(playState3);
                State.setLastscor(State.getScor());
            }
            if(State.GetState()==playState3 && Map3.isOver())
            {
                playState4=null;
                playState4=new PlayState4(refLink);
                levelsFinished=3;
                State.SetState(playState4);
                State.setLastscor(State.getScor());
            }
            if(State.GetState()==playState4 && Map4.isOver())
            {
                State.SetState(saveState);
                State.setLastscor(State.getScor());
            }
        }
    }
    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw()
    {
            /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
                /// Se executa doar la primul apel al metodei Draw()
            try
            {
                    /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
            /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        /// operatie de desenare
            ///Trebuie obtinuta starea curenta pentru care urmeaza a se actualiza starea, atentie trebuie sa fie diferita de null.
            if(State.GetState() != null)
            {
                ///Actualizez starea curenta a jocului daca exista.
                State.GetState().Draw(g);
            }
        /// end operatie de desenare

            /// Se afiseaza pe ecran
        bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei
     */
    public int GetWidth()
    {
        return wnd.GetWndWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei
     */
    public int GetHeight()
    {
        return wnd.GetWndHeight();
    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza obiectul care gestioneaza tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return keyManager;
    }

    public int getLevelsFinished()
    {
        return levelsFinished;
    }
    public void setLevelFinished(int levels) {
        this.levelsFinished=levels;
    }
}

