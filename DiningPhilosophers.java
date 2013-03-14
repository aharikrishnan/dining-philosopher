/**
 * @(#)DiningPhilosophers.java
 *
 *
 * @author Harikrishnan A
 * @version 1.0
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

public class DiningPhilosophers {

    /**
     * Creates a new instance of <code>DiningPhilosophers</code>.
     */
    public DiningPhilosophers() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable()
    	{
    		public void run()
    		{
    			Dimension	screenDimension;
    			Toolkit		tk = Toolkit.getDefaultToolkit();

    			screenDimension 	=	tk.getScreenSize();
				DPFrame f 			=	new DPFrame();

        		f.setBounds(50,50,screenDimension.width-100,screenDimension.height-100);
        		f.setVisible(true);
        		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    		}
    	});
        }
}

	class PhilosopherThread implements Runnable
{
	final int	millis = 1000;
	String		philosopher="";
	boolean 	eating;
	boolean 	hungry;
	DPPanel 	screen;

	static JTextArea	out;
	PhilosopherThread left,right;

	PhilosopherThread(DPPanel screen,String name)
	{
		this.screen 		=	screen;
		this.philosopher	=	name;
		eating				=	false;
		hungry 				=	false;
	}

	public void addAdjacentPhilosophers(PhilosopherThread left,PhilosopherThread right)
	{
		this.left	=	left;
		this.right	=	right;
	}

	static void addOutputArea(JTextArea output)
	{
		out = output;
	}
	public void run()
	{
		try
			{
			for(;;){
//			out.append("\nrunning@["+philosopher+"]");
			think();
			takeForks();
			eat();
			putForks();
			}
			}
			catch(InterruptedException ie)
			{
				System.out.println("->"+ie);
				System.exit(0);
			}
	}

	synchronized void think()
		throws InterruptedException
	{
//		out.append("\nthinking@["+philosopher+"]");
		Thread.sleep(millis);
	}

	synchronized void takeForks()
		throws InterruptedException
	{
			test();
	}
	synchronized void putForks()
		throws InterruptedException
	{
		if(eating){
//		out.append("\nputting forks@["+philosopher+"]");
		screen.repaint();
		eating = false;
		hungry = false;
		if(this.philosopher.equals("philosopher1"))
			{
				screen.forkStatus5=Color.RED;
				screen.forkStatus1=Color.RED;
				screen.ps1="THINKING.";
			}
			if(this.philosopher.equals("philosopher2"))
			{
				screen.forkStatus1=Color.RED;
				screen.forkStatus2=Color.RED;
				screen.ps2="THINKING.";
			}
			if(this.philosopher.equals("philosopher3"))
			{
				screen.forkStatus2=Color.RED;
				screen.forkStatus3=Color.RED;
				screen.ps3="THINKING.";
			}
			if(this.philosopher.equals("philosopher4"))
			{
				screen.forkStatus3=Color.RED;
				screen.forkStatus4=Color.RED;
				screen.ps4="THINKING.";
			}
			if(this.philosopher.equals("philosopher5"))
			{
				screen.forkStatus4=Color.RED;
				screen.forkStatus5=Color.RED;
				screen.ps5="THINKING.";
			}
		screen.repaint();
		notifyAll();
		}
	}
	synchronized void test()
		throws InterruptedException
	{

//		out.append("\ntesting@["+philosopher+"]");
		if(left.eating == false && right.eating == false )
		{
			out.append("\n_____________________________");
			out.append("\n|EATING@["+philosopher+"]|");
			out.append("\n_____________________________");
			this.eating = true;
			if(this.philosopher.equals("philosopher1"))
			{
				screen.forkStatus5=Color.GREEN;
				screen.forkStatus1=Color.GREEN;
				screen.ps1="EATING.";
			}
			if(this.philosopher.equals("philosopher2"))
			{
				screen.forkStatus1=Color.GREEN;
				screen.forkStatus2=Color.GREEN;
				screen.ps2="EATING.";
			}
			if(this.philosopher.equals("philosopher3"))
			{
				screen.forkStatus2=Color.GREEN;
				screen.forkStatus3=Color.GREEN;
				screen.ps3="EATING.";
			}
			if(this.philosopher.equals("philosopher4"))
			{
				screen.forkStatus3=Color.GREEN;
				screen.forkStatus4=Color.GREEN;
				screen.ps4="EATING.";
			}
			if(this.philosopher.equals("philosopher5"))
			{
				screen.forkStatus4=Color.GREEN;
				screen.forkStatus5=Color.GREEN;
				screen.ps5="EATING.";
			}
		}
		else
		{
			hungry = true;
			if(this.philosopher.equals("philosopher1"))
			{
				screen.ps1="HUNGRY!.";
			}
			if(this.philosopher.equals("philosopher2"))
			{
				screen.ps2="HUNGRY!.";
			}
			if(this.philosopher.equals("philosopher3"))
			{
				screen.ps3="HUNGRY!.";
			}
			if(this.philosopher.equals("philosopher4"))
			{
				screen.ps4="HUNGRY!.";
			}
			if(this.philosopher.equals("philosopher5"))
			{
			 	screen.ps5="HUNGRY!.";
			}
		}
		screen.repaint();
		wait(millis);
	}
	synchronized void  eat()
		throws InterruptedException
	{
		if(eating){
			Thread.sleep(millis);
			hungry = false;
		}
	}
}

class DPFrame extends JFrame
{

	JButton start;
	static DPPanel canvas;
	static ButtonPanel bp;
	JTextArea out;
	PhilosopherThread p1,p2,p3,p4,p5;
	Thread s1,s2,s3,s4,s5;

	public DPFrame()
	{
		super("Dining philosopher problem.");
		canvas = new DPPanel();
		bp = new ButtonPanel();
		out = new JTextArea(20,20);
		out.setText("DINING PHILOSOPHER'S PROBLEM!.");
		canvas.setBackground(Color.WHITE);
		canvas.setForeground(Color.RED);
		add(canvas,BorderLayout.CENTER);
		add(new JScrollPane(out),BorderLayout.WEST);

		add(bp,BorderLayout.SOUTH);

		PhilosopherThread.addOutputArea(out);

		bp.start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				p1=(new PhilosopherThread(canvas,"philosopher1"));
				p2=(new PhilosopherThread(canvas,"philosopher2"));
				p3=(new PhilosopherThread(canvas,"philosopher3"));
				p4=(new PhilosopherThread(canvas,"philosopher4"));
				p5=(new PhilosopherThread(canvas,"philosopher5"));

				p1.addAdjacentPhilosophers(p2,p5);
				p2.addAdjacentPhilosophers(p1,p3);
				p3.addAdjacentPhilosophers(p2,p4);
				p4.addAdjacentPhilosophers(p3,p5);
				p5.addAdjacentPhilosophers(p4,p1);

				s1= new Thread(p1);
				s2= new Thread(p2);
				s3= new Thread(p3);
				s4= new Thread(p4);
				s5= new Thread(p5);

				s1.start();
				s2.start();
				s3.start();
				s4.start();
				s5.start();
			}
		});

		bp.stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
					s1.interrupt();
					s2.interrupt();
					s3.interrupt();
					s4.interrupt();
					s5.interrupt();
			}
		});
	}
}
class ButtonPanel extends JPanel
{
	JButton start;
	JButton stop;
	ButtonPanel()
	{
		super();
		start = new JButton("START THE DINING PHILOSOPHERS PROBLEM.");
		stop = new JButton("STOP THE DINING PHILOSOPHERS PROBLEM.");
		add(start);
		add(stop);
	}
}

class DPPanel extends JPanel
{
	Graphics graphics;
	Graphics2D g2;
	GeneralPath gp;
	String ps1,ps2,ps3,ps4,ps5;
	static Color forkStatus1,forkStatus2,forkStatus3,forkStatus4,forkStatus5;
	Ellipse2D p1,p2,p3,p4,p5;
	Ellipse2D diningTable;
	Ellipse2D fork1,fork2,fork3,fork4,fork5;
	String name1,name2,name3,name4,name5;
	final int SIZE=50;
	final int BORDER_SIZE=50;
	int screenWidth, screenHeight;
	int rowPartition,colPartition;
	int adjust = 20;
	public DPPanel()
	{
		super();
		ps1 = "";
		ps2 = "";
		ps3 = "";
		ps4 = "";
		ps5 = "";
		name1 = "PHILOSOPHER-01.";
		name2 = "PHILOSOPHER-02.";
		name3 = "PHILOSOPHER-03.";
		name4 = "PHILOSOPHER-04.";
		name5 = "PHILOSOPHER-05.";
		gp=new GeneralPath();

	}

	void drawForks()
	{
		int forkWidth=20,forkHeight=10;
		int forkLength=25;
		int error=5;

		g2.setPaint(forkStatus1);

		gp.moveTo(colPartition,rowPartition);
		gp.lineTo(colPartition+forkLength,rowPartition);
		g2.fill(new Ellipse2D.Double(colPartition+forkLength,rowPartition-error,forkWidth,forkHeight));
		g2.draw(gp);

		g2.setPaint(forkStatus2);
		gp.moveTo(colPartition*5,rowPartition);
		gp.lineTo(colPartition*5-forkLength,rowPartition);
		g2.fill(new Ellipse2D.Double(colPartition*5-forkLength-forkWidth,rowPartition-error,forkWidth,forkHeight));
		g2.draw(gp);

		g2.setPaint(forkStatus3);

		gp.moveTo(colPartition*5,rowPartition*3);
		gp.lineTo(colPartition*5-forkLength,rowPartition*3);
		g2.fill(new Ellipse2D.Double(colPartition*5-forkLength-forkWidth,rowPartition*3-error,forkWidth,forkHeight));
		g2.draw(gp);

		g2.setPaint(forkStatus4);

		gp.moveTo(colPartition*3,rowPartition*3);
		gp.lineTo(colPartition*3,rowPartition*3+forkLength);
		g2.fill(new Ellipse2D.Double(colPartition*3-error,rowPartition*3-forkHeight,forkHeight,forkWidth));
		g2.draw(gp);

		g2.setPaint(forkStatus5);
		gp.moveTo(colPartition,rowPartition*3);
		gp.lineTo(colPartition+forkLength,rowPartition*3);
		g2.fill(new Ellipse2D.Double(colPartition+forkLength,rowPartition*3-error,forkWidth,forkHeight));
		g2.draw(gp);
	}

	void drawPhilosophers()
	{
		int diningWidth = 50;
//adding philosopher's name.
		graphics.drawString(name1,colPartition   - SIZE-adjust,rowPartition*2+adjust);
		graphics.drawString(name2,colPartition*3 - SIZE-adjust,rowPartition+adjust);
		graphics.drawString(name3,colPartition*5 - SIZE-adjust,rowPartition*2+adjust);
		graphics.drawString(name4,colPartition*4 - SIZE-adjust,rowPartition*3+adjust);
		graphics.drawString(name5,colPartition*2 - SIZE-adjust,rowPartition*3+adjust);
// adding the status of the philosophers.

		graphics.drawString(ps1,colPartition   - SIZE-adjust+20,rowPartition*2+adjust+20);
		graphics.drawString(ps2,colPartition*3 - SIZE-adjust+20,rowPartition+adjust+20);
		graphics.drawString(ps3,colPartition*5 - SIZE-adjust+20,rowPartition*2+adjust+20);
		graphics.drawString(ps4,colPartition*4 - SIZE-adjust+20,rowPartition*3+adjust+20);
		graphics.drawString(ps5,colPartition*2 - SIZE-adjust+20,rowPartition*3+adjust+20);

		// addding the philosopher's head.
		p1 = new Ellipse2D.Double(colPartition     - SIZE,rowPartition * 2 - SIZE,SIZE,SIZE);
		p2 = new Ellipse2D.Double(colPartition * 3 - SIZE,rowPartition     - SIZE,SIZE,SIZE);
		p3 = new Ellipse2D.Double(colPartition * 5 - SIZE,rowPartition * 2 - SIZE,SIZE,SIZE);
		p4 = new Ellipse2D.Double(colPartition * 2 - SIZE,rowPartition * 3 - SIZE,SIZE,SIZE);
		p5 = new Ellipse2D.Double(colPartition * 4 - SIZE,rowPartition * 3 - SIZE,SIZE,SIZE);


		diningTable = new Ellipse2D.Double(colPartition*2-diningWidth + adjust,rowPartition*2-diningWidth - adjust,2*colPartition,rowPartition);

		g2.setPaint(Color.GREEN);
		g2.fill(p1);
		g2.fill(p2);
		g2.fill(p3);
		g2.fill(p4);
		g2.fill(p5);
		g2.setPaint(Color.PINK);
		g2.fill(diningTable);

	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		graphics= g;
		g2 = (Graphics2D)g;
		screenWidth  = getWidth();
		screenHeight = getHeight();
		rowPartition = screenHeight/4;
		colPartition = screenWidth/6;

		drawPhilosophers();
		drawForks();

	}
}
