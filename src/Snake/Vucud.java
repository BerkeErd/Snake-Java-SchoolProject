package Snake;

import java.awt.Color;
import java.awt.Graphics;

public class Vucud {

	private int xCoor , yCoor , genislik , yukseklik;
	 public Vucud(int xCoor, int yCoor, int titleSize)
	 {
		 this.xCoor = xCoor;
		 this.yCoor = yCoor;
		 genislik = titleSize;
		 yukseklik = titleSize;
	 }
	 
	 public void tick()
	 {
		 
	 }
	 
	 public void Ciz (Graphics g)
	 {
		 g.setColor(Color.WHITE);
		 g.fillRect(xCoor*genislik, yCoor*yukseklik, genislik, yukseklik);
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
