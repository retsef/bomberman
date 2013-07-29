package Engine;

import Utils.SpriteException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * La classe grafica gli elementi attivi del gioco
 * E' adoperato come un server grafico addetto ad renderizzare ogni elemento che gli viene dato in pasto
 * @author roberto
 */
public class Draw{
   private JFrame frame; //finestra
   private Canvas canvas; //tavolozza per graficare l'area di gioco
   private JPanel panel; // pannello da ancorare alla finestra

   private BufferStrategy bufferStrategy; //Questo è una classe in grado di gestire più immagini in contemporanea
   private Image Background; //Sfondo
   private Image Scope_img; //mirino
   private Font myFont; //Opzionale: se si vuole impostare un stile per i caratteri
   
   //Cursore invisibile
   private Cursor HIDDEN_CURSOR;
   private Rectangle Scope;
   /**
    * @param HEIGHT Altezza finestra
    * @param WIDTH Larghezza della finestra
    */
   public int WIDTH; // dimensioni finestra
   public int HEIGHT;
   
   /**
    * Inizializza la finestra di gioco
    * @param pwidth Larghezza della finestra
    * @param pheight Altezza della finestra
    * @param pString Titolo della finestra
    */
   public Draw(int pwidth,int pheight,String pString){
      this.WIDTH = pwidth;
      this.HEIGHT = pheight;
      //Definiamo la finestra ed il pannello e la relativa dimensione
      this.frame = new JFrame(pString); //inizializziamo la finestra
      this.panel = (JPanel) this.frame.getContentPane(); // inizializziamo il pannello 
      this.panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      this.panel.setLayout(null);
      //aggiungiamoci il pannello canvas
      this.canvas = new Canvas();
      this.canvas.setBounds(0, 0, WIDTH, HEIGHT);
      this.canvas.setIgnoreRepaint(true);
      this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.frame.pack();
      //ancoriamo la finestra
      this.frame.setResizable(false);
      this.frame.setVisible(false);
      this.frame.setLocationRelativeTo(null);
                             //this will add the canvas to our frame
      this.panel.add(this.canvas);
      this.canvas.createBufferStrategy(3);
      this.bufferStrategy = this.canvas.getBufferStrategy();
                            //This will make sure the canvas has focus, so that it can take input from mouse/keyboard
      this.canvas.requestFocus();
                             //this will set the background
      this.canvas.setBackground(null);
       try {
           this.Background = ImageIO.read(getClass().getResource("/images/Background.png"));
       } catch (IOException ex) {
           Logger.getLogger(Draw.class.getName()).log(Level.SEVERE, null, ex);
       }
      // This will add our command listner to our program
      this.canvas.addKeyListener(new ButtonHandler());
      this.canvas.addMouseListener(new MouseHandler()); //this will get the button of mouse
      this.canvas.addMouseMotionListener(new MouseHandler()); // this will get the movement of mouse
      //miscelatous
      this.myFont = new Font("Arial", Font.BOLD, 12);
      this.HIDDEN_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(), "null");
      
      //Nascondo il cursore
      this.canvas.setCursor(this.HIDDEN_CURSOR);
   
      this.Scope = new Rectangle((int)Game.get_player().get_Glove().getTarget().getX(),(int)Game.get_player().get_Glove().getTarget().getY(),28,28);
       try {
           this.Scope_img = Game.get_player().get_Glove().Fireball(23);
       } catch (SpriteException ex) {
           Logger.getLogger(Draw.class.getName()).log(Level.SEVERE, null, ex);
       }
      }
   
   /**
    * Il costutto renderizza tutti gli elementi del gioco
    * Puo' essere visto come un grosso secchio contenente tutto quello che viene visto su schermo
    * @throws IOException Restituisce un Exception nel caso le immagini sono errate
    */
   public void render() throws IOException{
      Graphics2D g = (Graphics2D) this.bufferStrategy.getDrawGraphics();
      g.clearRect(0, 0, this.WIDTH, this.HEIGHT);
      //render of background
      g.drawImage(this.Background, 0, 0, null);
        //render life and score status
        g.drawImage(Game.get_player().Heart(),this.WIDTH-40,5,30,30,null);
        g.setFont(this.myFont);
        g.drawString(Game.get_player().getLife(),this.WIDTH-33,22);
        g.drawString(Game.get_player().getScore(),this.WIDTH-50,this.HEIGHT-30);
       try {
           this.renderPlayer(g);
           this.renderMob(g);
           this.renderScope(g);
       } catch (SpriteException ex) {
           Logger.getLogger(Draw.class.getName()).log(Level.SEVERE, null, ex);
       }
      g.dispose();
      this.bufferStrategy.show();
   }
   /**
    * Grafica gli elementi di tipo Player
    * @param g Oggetto che contiene i metodi grafici
    * @throws SpriteException Restituisce un Exception nel caso gli Sprite sono errati
    */
   protected void renderPlayer(Graphics2D g) throws SpriteException {
       Game.get_player().Draw(g);
       Game.get_player().get_Glove().Draw(g);
       /*
       for(int k = 0; k < Game.get_player().BundleGlove.size(); k++)
        { 
            Game.get_player().BundleGlove.get(k).Draw(g); 
        }*/
    }
   /**
    * Grafica gli elementi del puntatore
    * @param g Oggetto che contiene i metodi grafici
    * @throws SpriteException Restituisce un Exception nel caso gli Sprite sono errati
    */ 
   protected void renderScope(Graphics2D g) throws SpriteException {
       g.drawImage(this.Scope_img,(int)Game.get_player().get_Glove().getTarget().getX(),(int)Game.get_player().get_Glove().getTarget().getY(),28,28,null);
   }
   /**
    * Grafica gli elementi di tipo Mob
    * @param g Oggetto che contiene i metodi grafici
    * @throws SpriteException Restituisce un Exception nel caso gli Sprite sono errati
    */
   protected void renderMob(Graphics2D g) throws SpriteException {
       // Here we draw all the Mob.
        for(int l = 0; l < Game.get_Bundle_Mob().size(); l++)
        {
            Game.get_Bundle_Mob().get(l).Draw(g);
        }
    }
   /**
    * Impone la decisione di mostrare la finestra o no
    * @param b Boolena che impone l'esito
    */
   public void setFrameVisible(boolean b){
       if (b==true)
           this.frame.setVisible(true);
       else
           this.frame.setVisible(false);
   }
   
   }
