package Snake;
import java.awt.BasicStroke;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.JPanel;
	
public class Boards extends JPanel implements Runnable, KeyListener {
	
	 private static final long serialVersionUID = 1L;
	 public static final int GENISLIK = 700, YUKSEKLIK = 750;
	 
	 public Thread thread;
	 public boolean running;
	 public boolean sag=true, sol=false, yukari=false, assagi=false;
	 
	 public Vucud v;
	 public ArrayList<Vucud> snake;
	 
	 public int xCoor= GENISLIK/20 , yCoor=YUKSEKLIK/20, size=5;
	 public int ticks=0;
	 
	 public Yem yem;
	 public ArrayList<Yem> yemler;
	 
	 public Random r;
	 public int sans, score=0, can=3, speed=500000;
	
	 
	 public Boards()
	 {
		 setFocusable(true);
		 setPreferredSize(new Dimension(GENISLIK,YUKSEKLIK));
		 
		 addKeyListener(this);
		 
		 snake = new ArrayList<Vucud>();
		 yemler = new ArrayList<Yem>();
		 r= new Random();
		 
		 baslat();
	 }
	 
	 public void baslat()
	 {
		 running = true;
		 thread = new Thread(this);
		 thread.start();
	 }
	 	 
	 public void durdur() 
	 {
		 running = false;
		 can--;
		 if(can>0)
		 {
			 xCoor=GENISLIK/20;
			 yCoor=YUKSEKLIK/20;
			 pause();
			 running =true;
		 }
		 else
		 {
		 try
		 {
			 System.out.flush();
			 System.out.println("Kaybettin..!");
			 System.out.println("Puanýn:"+score);
			 running = false;
			 thread.join();
		 }
		 catch(InterruptedException e)
		 {
			 e.printStackTrace();		 
		 }
		 }
	  }
	 
	 public void tick()
	 {
		 if(snake.size() == 0)
		 {
			 v = new Vucud(xCoor, yCoor, 10);
			 snake.add(v);
		 }
		 
		 ticks++;
		 
		 if(ticks>speed)
		 {
			 if(sag)
			 {
				 xCoor++;
			 }
			 
			 if(sol)
			 {
				 xCoor--;
			 }
			 
			 if(yukari)
			 {
				 yCoor--;
			 }
			 
			 if(assagi)
			 {
				 yCoor++;
			 }
			 
			 ticks=0;
			 
			 v=new Vucud(xCoor, yCoor, 10);
			 snake.add(v);
			 
			 if (snake.size()>size)
			 {
				 snake.remove(0);
			 }
		 }
		 
		 for (int i = 0 ; i<yemler.size() ;i++)
		 {
			 if(xCoor == yemler.get(i).getxCoor() && yCoor == yemler.get(i).getyCoor())
			 {
				 size++;
				 yemler.remove(i);
				 i++;
				 score+=yem.puan;
				 speed-=10000;
				 if(yem.renk==2)
				 {
					 SecimYap();
				 }
			 }
		 }
		 
		 if (yemler.size()==0) // Yem oluþumu
		 {
			 sans = r.nextInt(100);
			 if (sans<=30) // Yeþil yem
			 {
			 int xCoor = r.nextInt((GENISLIK/10)-3);
			 int yCoor = r.nextInt(((YUKSEKLIK-200)/10)-3);
			 
			 yem =new Yem(xCoor , yCoor, 10, 20 , 1); // (kordinatX,kordinatY,Boyut,Puan,Renk) þeklinde
			 yemler.add(yem);
			 }
			 
			 else if(sans>=50) // Kýrmýzý Yem
			 {
				 int xCoor = r.nextInt((GENISLIK/10)-2);
				 int yCoor = r.nextInt(((YUKSEKLIK-200)/10)-2);
				 
				 yem =new Yem(xCoor , yCoor, 10, 10, 0);
				 yemler.add(yem);		 
			 }
			 
			 else  // Mavi Yem
			 {
				 int xCoor = r.nextInt((GENISLIK/10)-2);
				 int yCoor = r.nextInt(((YUKSEKLIK-200)/10)-2);
				 
				 yem =new Yem(xCoor , yCoor, 10, 0, 2);
				 yemler.add(yem);			 
			 }
			 
		 }

		 
		 for (int i = 0 ; i<snake.size() ;i++)
		 {
			 if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor())
			 {
				 if (i != snake.size()- 1 )
				 {		 
					 durdur();
				 }
			 }
			 
		 }
		 
		 
		  if(xCoor < 0 || xCoor> (GENISLIK/10)-2 || yCoor < 0 || yCoor>((YUKSEKLIK-50)/10)-2 )
		  {
			  durdur();
		  }
	 }
	 
	 public void paint(Graphics g)
	 {		 
		 for(int i=0 ; i<GENISLIK/10 ; i++)
		 {
			 g.drawLine(i*10 , 0 , i*10 , YUKSEKLIK);
		 }
		 
		 for(int i=0 ; i<YUKSEKLIK/10 ; i++)
		 {
			 g.drawLine(i*10 , 0 , i*10 , GENISLIK);
		 }
		 
		 super.paint(g);
		 Graphics2D g2 = (Graphics2D)g;
		 g.clearRect(0, 0, GENISLIK, YUKSEKLIK);
		 g.setColor(Color.BLACK);
		 g.fillRect(0, 0, GENISLIK, YUKSEKLIK);
		 Rectangle rect =new Rectangle(0,0,GENISLIK,YUKSEKLIK-50);
		 g2.setColor(Color.LIGHT_GRAY);
		 g2.setStroke(new BasicStroke(10));
		 g2.draw(rect);
		 BilgileriYaz(g);
		 
		 for(int i=0 ; i<snake.size() ; i++)
		 {
			 snake.get(i).Ciz(g);
		 }
		 
		 for(int i=0 ; i<yemler.size() ; i++)
		 {
			 yemler.get(i).draw(g);
		 }
		 

	 }
 
	@Override
	public void run() 
	{
		while (running)
		{
			tick();
			repaint();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key= e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT && !sol)
		{
			sag=true;
			yukari=false;
			assagi=false;		
		}
		
		if(key == KeyEvent.VK_LEFT && !sag)
		{
			sol=true;
			yukari=false;
			assagi=false;		
		}
		
		if(key == KeyEvent.VK_UP && !assagi)
		{
			yukari=true;
			sag=false;
			sol=false;		
		}
		
		if(key == KeyEvent.VK_DOWN && !yukari)
		{
			assagi=true;
			sag=false;
			sol=false;		
		}
		
	}
	
	public void SecimYap()
	{
		 running=false;
		 String[] options = new String[] {"Hýzý 5 düþür", "Boyu 3 düþür", "Caný 1 arttýr", "Puaný 50 arttýr fakat...(bir zorlaþtýran özellik seçmen gerekir)"};
		 
		 int response = JOptionPane.showOptionDialog(null, "Bir özellik seçiniz", "Evrim",
		 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		 null, options, options[0]);
		 
		 switch(response) {
		 
		 
		 case 0: // Hýzý düþürme seçeneði
		 {
			
				 speed +=50000;
				 pause();
				 running=true;
				 break; 
			 
		 }
		 case 1: // Boyu düþürme seçeneði
		 {
			 if(snake.size() <=3)
			 {
				 SecimYap();
				 break;
			 }
			 else {
				 
		for(int i=0;i<=3;i++) {
				size--;
				snake.remove(0);
			                 }
				
			
		     
			 pause(); 
			 running=true;
			 break;
			 }
		 }
		 case 2: // Caný 1 arttýrma seçeneði
		 {
			 can += 1;
			 pause();
			 running=true;
			 break;
			
		 }
		 case 3: // Puaný 100 arttýr fakat...(bir kötü özellik seçmen gerekir) seçeneði
		 {
             String[] options2 = new String[] {"Hýzý ekstra 10 arttýr", "Boyu ekstra 2 arttýr", "1 Canýný feda et", "Vazgeç"};
			 
			 int response2 = JOptionPane.showOptionDialog(null, "Bir özellik seçiniz", "Evrim",
			 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
			 null, options2, options2[0]);
			 
			 switch(response2)
			 {
			 
			 case 0: // Hýzý 10 arttýr
				 speed -=200000;
				 pause();
				 running=true;
				 score+=50;
				 break;
			 case 1: // Boyu 2 arttýr
				 
				 size +=2;
				 score+=50;
				 break;
			 case 2: // 1 Canýný feda et
				 
				 if(can>1) // Caný yoksa en baþa geri dönüyor
				 {
					 can -= 1;
					 pause();
					 running=true;
					 score+=50;
					 break; 
				 }
				 else {
					 SecimYap();
					 break;
				 }
					 
				 
			 case 3: //Vazgeç
				 SecimYap();
				 break;
				 
				 default: //ESC tuþu da Vazgeç
					 SecimYap();
					 break;
			 }
		 }
		 default: // ESC tuþuna basýlýp çýkýlýrsa hýzý düþürme seçeneði seçilmiþ sayýlýyor
		 {
			 speed +=50000;
			 pause();
			 running=true;
			 break;
		 }
		 }
	}
	public void BilgileriYaz(Graphics g)
	{
		int Hiz = (-1 *speed/10000) + 150; 
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Puan : "+score, 0, 730);
		g.drawString("Can : "+can, 600, 730);
		g.drawString("Boy : "+snake.size(), 200, 730);
		g.drawString("Hýz : "+Hiz, 400, 730);
		if (can==0)
		{
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.setColor(Color.RED);
			g.drawString("Kaybettin!!", 260, 300);	
		}
	}
	
	
	
	static void pause(){
	    long Time0 = System.currentTimeMillis();
	    long Time1;
	    long runTime = 0;
	    while(runTime<1000){
	        Time1 = System.currentTimeMillis();
	        runTime = Time1 - Time0;
	    }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
