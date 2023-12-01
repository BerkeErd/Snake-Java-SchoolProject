package Snake;
import javax.swing.JFrame;

public class Main {
	
	public Main()
	{
		JFrame frame= new JFrame();
		Boards boards = new Boards();
		
		frame.add(boards);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Snake");
		frame.setLocationRelativeTo(null);
		
		frame.pack();
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		new Main();

	}

}
