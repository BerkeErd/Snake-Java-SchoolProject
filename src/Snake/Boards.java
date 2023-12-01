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
			 System.out.println("Puan�n:"+score);
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
		 
		 if (yemler.size()==0) // Yem olu�umu
		 {
			 sans = r.nextInt(100);
			 if (sans<=30) // Ye�il yem
			 {
			 int xCoor = r.nextInt((GENISLIK/10)-3);
			 int yCoor = r.nextInt(((YUKSEKLIK-200)/10)-3);
			 
			 yem =new Yem(xCoor , yCoor, 10, 20 , 1); // (kordinatX,kordinatY,Boyut,Puan,Renk) �eklinde
			 yemler.add(yem);
			 }
			 
			 else if(sans>=50) // K�rm�z� Yem
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
		 String[] options = new String[] {"H�z� 5 d���r", "Boyu 3 d���r", "Can� 1 artt�r", "Puan� 50 artt�r fakat...(bir zorla�t�ran �zellik se�men gerekir)"};
		 
		 int response = JOptionPane.showOptionDialog(null, "Bir �zellik se�iniz", "Evrim",
		 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
		 null, options, options[0]);
		 
		 switch(response) {
		 
		 
		 case 0: // H�z� d���rme se�ene�i
		 {
			
				 speed +=50000;
				 pause();
				 running=true;
				 break; 
			 
		 }
		 case 1: // Boyu d���rme se�ene�i
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
		 case 2: // Can� 1 artt�rma se�ene�i
		 {
			 can += 1;
			 pause();
			 running=true;
			 break;
			
		 }
		 case 3: // Puan� 100 artt�r fakat...(bir k�t� �zellik se�men gerekir) se�ene�i
		 {
             String[] options2 = new String[] {"H�z� ekstra 10 artt�r", "Boyu ekstra 2 artt�r", "1 Can�n� feda et", "Vazge�"};
			 
			 int response2 = JOptionPane.showOptionDialog(null, "Bir �zellik se�iniz", "Evrim",
			 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
			 null, options2, options2[0]);
			 
			 switch(response2)
			 {
			 
			 case 0: // H�z� 10 artt�r
				 speed -=200000;
				 pause();
				 running=true;
				 score+=50;
				 break;
			 case 1: // Boyu 2 artt�r
				 
				 size +=2;
				 score+=50;
				 break;
			 case 2: // 1 Can�n� feda et
				 
				 if(can>1) // Can� yoksa en ba�a geri d�n�yor
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
					 
				 
			 case 3: //Vazge�
				 SecimYap();
				 break;
				 
				 default: //ESC tu�u da Vazge�
					 SecimYap();
					 break;
			 }
		 }
		 default: // ESC tu�una bas�l�p ��k�l�rsa h�z� d���rme se�ene�i se�ilmi� say�l�yor
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
		g.drawString("H�z : "+Hiz, 400, 730);
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
