package com;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.Font;
public class ViewMatrix extends JFrame{
	JTable tab;
	DefaultTableModel dtm;
	JScrollPane jsp;
	
public ViewMatrix(){
	super("View Matrix");
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int row_no,int column_no){
			return false;
		}
	};
	tab = new JTable(dtm);
	tab.getTableHeader().setFont(new Font("Courier New",Font.BOLD,14));
	tab.setFont(new Font("Courier New",Font.BOLD,13));
	tab.setRowHeight(30);
	jsp = new JScrollPane(tab);
	dtm.addColumn("Tweet Text");
	dtm.addColumn("Is Same Source");
	dtm.addColumn("Retweet Count");
	dtm.addColumn("Relative Tag");
	dtm.addColumn("Is Same Time");
	dtm.addColumn("Is Same Blogger");
	dtm.addColumn("Message Influence");
	getContentPane().add(jsp);
	
}

}