package Snake;

import java.awt.Color;
import java.awt.Graphics;

public class Yem {

    public int xCoor, yCoor, genislik, yukseklik, puan, renk;


    public Yem (int xCoor, int yCoor, int tileSize, int puan, int renk )
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        genislik = tileSize;
        yukseklik = tileSize;
        this.puan =puan;
        this.renk = renk;
    }

    public void tick()
    {

    }

    public void draw(Graphics g)
    {
        
        if(renk==0)
        {
        	g.setColor(Color.RED);
        }
        
        else if(renk==1)
        {
        	g.setColor(Color.GREEN);
        }
        
        else if(renk==2)
        {
        	g.setColor(Color.BLUE);
        }
        g.fillRect(xCoor*genislik,yCoor*yukseklik,genislik,yukseklik);
    }

    public int getxCoor() {
		return xCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public int getyCoor() {
        return yCoor;
    }

    public void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }

}