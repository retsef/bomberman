package GameObject;

import Engine.Game;
import Engine.InputErrorException;
import Engine.SpriteSheetLoader;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Player {
   //variables which we will use 
   private int x, y;
   private int width,height;
   private boolean  left, right, up, down;
   private String Name;
   private int Score,Life,Velocity,Direction;
   private boolean Damaged,isMoving;
   public Weapon Gun;
   private SpriteSheetLoader ActorWalk;
   private Image Heart;
   public Rectangle Body;
   
   
   public Player (String pName){
       this.Name = pName;
       this.Score = 0;
       this.Life = 15;
       this.Velocity = 4;
       this.Damaged = false;
       this.isMoving = false;
       this.height = 40;
       this.width = 40;
       this.spawn();
       this.Body = new Rectangle(this.x, this.y, this.width, this.height);
       
       try {
           //preset the propriety of the weapon
           this.Gun = new Weapon(1);
       } catch (ValueErrorException ex) { }
       
       this.Direction=1;
       try {
           this.ActorWalk = new SpriteSheetLoader(8,12,"/images/Actor.png");
           this.Heart = new ImageIcon(getClass().getResource("/images/Heart.png")).getImage();
       } catch (IOException | InputErrorException ex) {
           Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public Image Heart() throws InputErrorException, IOException{
       return this.Heart;
   }
   
   public Image Shadow() throws InputErrorException, IOException{
       return this.ActorWalk.paint(69);
   }
   
   public Image Walk() throws InputErrorException, IOException{
       while(true){
       this.getDirectionWalk();
       if (this.Damaged==true)
           return this.ActorWalk.paint(73);
       return this.ActorWalk.paint(this.Direction);
       }
   }
   
   //make player rotation animation
   public void getDirectionWalk(){
       if ((this.getDirection()>338 && this.getDirection()<=0) || (this.getDirection()>0 && this.getDirection()<=23) ){
           this.Direction=1;
       } if (this.getDirection()>23 && this.getDirection()<=68){
           this.Direction=28;
       } if (this.getDirection()>68 && this.getDirection()<=113){
           this.Direction=25;
       } if (this.getDirection()>113 && this.getDirection()<=158){
           this.Direction=40;
       } if ((this.getDirection()>158 && this.getDirection()<=180) || (this.getDirection()>180 &&this.getDirection()<=203)){
           this.Direction=37;
       } if (this.getDirection()>203 && this.getDirection()<=248){
           this.Direction=16;
       } if (this.getDirection()>248 && this.getDirection()<=293){
           this.Direction=13;
       } if (this.getDirection()>293 && this.getDirection()<=338){
           this.Direction=4;
       } else {}
   }
   
   public int getDirection(){
       double angle;
       angle = Math.toDegrees(Math.atan2(this.Gun.getX()-(this.x+16),this.Gun.getY()-(this.y+16)));
       if (angle < 0){
           angle += 360;
       }
       return (int)angle;
   }
   
   public void getDamage(Mob pMob) {
       if (pMob.Attack()==true) {
           this.Life--;
           this.bounce();
       }else{ }
   }
   
   public void DamageMob(Mob pMob) throws IOException, InputErrorException {
       System.out.println(this.Gun.getPoint());
       
       if(this.Gun.getAttack()==true && pMob.Body.contains(this.Gun.getPoint())){
           pMob.Damaged();
           this.increaseScore(pMob);
       } else {}
   }
   
   private void increaseScore(Mob pMob) {
       if (pMob.isDead() == true) {
           this.Score += pMob.getPoint();
       }
   }
   
   //In this function we will do the required checking and updates
   public void update() throws MalformedURLException {
      this.move();
      this.Gun.update();
    }
   
   //This function will move the player according to its direction
   private void move() throws MalformedURLException{
      if(left){
         this.x -= this.Velocity;
         this.isMoving=true;
      }if(right){
         this.x += this.Velocity;
         this.isMoving=true;
      }if(up){
         this.y -= this.Velocity;
         this.isMoving=true;
      }if(down){
         this.y += this.Velocity;
         this.isMoving=true;
      }else{
          this.isMoving=false;
      }
   }
   
   public String getLife() {
       String strng;
       strng = "" + this.Life;
       return strng;
   }
   
   public String getScore() {
       String strng;
       strng = "" + this.Score;
       return strng;
   }
   
   //These 4 functions are able to set the direction
   public void setLeft (boolean pLeft  ){
      this.left  = pLeft; 
   }
   public void setUp   (boolean pUp   ){
      this.up    = pUp;   
   }
   public void setDown (boolean pDown ){
      this.down  = pDown; 
   }
   public void setRight(boolean pRight){
      this.right = pRight;
   }
   
   private void spawn(){
       this.x = Game.WIDTH/2; 
       this.y = Game.HEIGTH/2;
   }
   
   private void bounce(){
       this.x += (int)(Math.random()*50);
       this.y += (int)(Math.random()*50);
   }
   
   //This function will return X as an int.
   public int getX(){
      return this.x;
   }
   //And this function will return Y as an int.
   public int getY(){
      return this.y;
   }

    public int getheight() {
        return this.height;
    }

    public int getwidth() {
        return this.width;
    }
   
   
   private class animation {
       
   }
}

