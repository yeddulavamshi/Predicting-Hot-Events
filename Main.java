package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JFileChooser;
import java.awt.Cursor;
import org.jfree.ui.RefineryUtilities;
import java.awt.Dimension;
import java.text.DecimalFormat;
import org.jvnet.substance.SubstanceLookAndFeel;
import java.awt.Cursor;
public class Main extends JFrame{
	JLabel l1;
	JPanel p1,p2,p3;
	Font f1;
	JScrollPane jsp;
	JButton b1,b2,b3,b4,b5,b6;
	JFileChooser chooser;
	DefaultTableModel dtm;
	JTable table;
	File file;
	static int participate,non_participate;
public Main(){
	super("User Behavior Prediction");
	p1 = new JPanel();
	p1.setPreferredSize(new Dimension(600,50));
	p1.setBackground(Color.white);
	l1 = new JLabel("<html><body><center>User Behavior Prediction of Social Hotspots Based on Multimessage<br/>Interaction and Neural Network</center></body></html>".toUpperCase());
	l1.setFont(new Font("Courier New",Font.BOLD,18));
	l1.setForeground(Color.blue);
	p1.add(l1);
	getContentPane().add(p1,BorderLayout.NORTH);

	f1 = new Font("Times New Roman",Font.BOLD,14);

	p2 = new JPanel();
	p2.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	table.setFont(f1);
	table.getTableHeader().setFont(f1);
	jsp = new JScrollPane(table);
	//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	dtm.addColumn("Tweet Text");
	dtm.addColumn("Retweet Count");
	dtm.addColumn("Tweet Source");
	dtm.addColumn("Date");
	dtm.addColumn("Username");
		
	p3 = new JPanel();
	p3.setPreferredSize(new Dimension(150,110));
	chooser = new JFileChooser(new File("."));
	chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY);
	
	b1 = new JButton("Upload Tweets Dataset");
	b1.setFont(f1);
	b1.setPreferredSize(new Dimension(300,30));
	p3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(Main.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				clearTable();
				JOptionPane.showMessageDialog(Main.this,"Dataset Loaded");
			}
		}
	});

	b2 = new JButton("User Personal Characteristics Matrix");
	b2.setFont(f1);
	b2.setPreferredSize(new Dimension(300,30));
	p3.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			OptimizeTrainNeuralNetwork.read(file,dtm,table);
		}
	});

	b3 = new JButton("Optimize Parameters & Train Neural Network");
	b3.setFont(f1);
	b3.setPreferredSize(new Dimension(300,30));
	p3.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			OptimizeTrainNeuralNetwork.optimizeParametersTraining();
			OptimizeTrainNeuralNetwork.showMatrix();
		}
	});

	b4 = new JButton("Run Prediction Algorithm");
	b4.setFont(f1);
	b4.setPreferredSize(new Dimension(300,30));
	p3.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			participate = 0;
			non_participate = 0;
			ParticipationPrediction pp = new ParticipationPrediction();
			for(int i=0;i<OptimizeTrainNeuralNetwork.tweets.size();i++){
				Tweet pc = OptimizeTrainNeuralNetwork.tweets.get(i);
				String hot_topic = "none";
				for(int j=0;j<OptimizeTrainNeuralNetwork.keywords.size();j++) {
					if(pc.getTweet().trim().toLowerCase().indexOf(OptimizeTrainNeuralNetwork.keywords.get(j)) != -1) {
						hot_topic = OptimizeTrainNeuralNetwork.keywords.get(j);
						break;
					}
				}
				if(hot_topic.equals("none")) {
					non_participate = non_participate + 1;
					Object row[] = {pc.getUser(),OptimizeTrainNeuralNetwork.keywords.get(0),"No Participation Predicted"};
					pp.dtm.addRow(row);
				} else {
					participate = participate + 1;
					Object row[] = {pc.getUser(),hot_topic,"Participation Predicted"};
					pp.dtm.addRow(row);
				}
			}
			pp.setSize(800,600);
			pp.setVisible(true);
		}
	});

	b5 = new JButton("Prediction Result Graph");
	b5.setFont(f1);
	p3.add(b5);
	b5.setPreferredSize(new Dimension(300,30));
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("Prediction Result Graph");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
			
		}
	});

	b6 = new JButton("Exit");
	b6.setFont(f1);
	p3.add(b6);
	b6.setPreferredSize(new Dimension(300,30));
	b6.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	});

	getContentPane().add(jsp,BorderLayout.CENTER);
	getContentPane().add(p3,BorderLayout.SOUTH);
}
public void clearTable(){
	for(int i=table.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
public static void main(String a[])throws Exception{
	SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermark");
	SubstanceLookAndFeel.setCurrentTheme("org.jvnet.substance.theme.SubstanceInvertedTheme");
	SubstanceLookAndFeel.setCurrentGradientPainter("org.jvnet.substance.painter.SpecularGradientPainter");
	SubstanceLookAndFeel.setCurrentButtonShaper("org.jvnet.substance.button.ClassicButtonShaper");
	UIManager.setLookAndFeel(new SubstanceLookAndFeel());
	Main ud = new Main();
	ud.setVisible(true);
	ud.setExtendedState(JFrame.MAXIMIZED_BOTH);
}
}
