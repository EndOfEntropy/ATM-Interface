package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Keypad {
	
	/* The JButtons that create the keypad and are used to perform operations */
	public JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0;
	public JButton d1, bDel, bDot, bCancel, bClear, bEnter;
	
	/**
	 * initialize the keypad resources
	 */
	Keypad(){
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		b5 = new JButton("5");
		b6 = new JButton("6");
		b7 = new JButton("7");
		b8 = new JButton("8");
		b9 = new JButton("9");
		b0 = new JButton("0");
		bDot = new JButton(".");
		d1 = new JButton("");
		bDel = new JButton("Delete");
		bCancel = new JButton("Cancel");
		bClear = new JButton("Clear");
		bEnter = new JButton("Enter");
	}
	
	/**	create a keypad for the user to interact with and provide input
	 *  GridLayout preferred over a FlowLayout to draw keypads/calculators
	 * @return		the JPanel object
	 */
	public JPanel addKeypad() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 120));
		panel.setBackground(Color.gray);
		panel.setLayout(new GridLayout(4, 4));
		   
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		panel.add(bCancel);
		panel.add(b4);
		panel.add(b5);
		panel.add(b6);
		panel.add(bClear);
		panel.add(b7);
		panel.add(b8);
		panel.add(b9);
		panel.add(bDel);
		panel.add(d1);
		panel.add(b0);
		panel.add(bDot);
		panel.add(bEnter);
		
		return panel;
	}
}
