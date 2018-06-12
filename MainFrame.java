import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainFrame extends JFrame {
	private static final int WIDTH  = 400;
	private static final int HEIGHT = 400;
	private static final int LEVEL1 = 100;
	private static final int LEVEL2  = 380;
	private static final int LEVEL3  = 770;
	private static final int LEVEL4  = 1300;
	private static final int LEVEL5  = 2150;
	private static final int LEVEL6  = 3300;
	private static final int LEVEL7  = 4800;
	private static final int LEVEL8  = 6900;
	private static final int LEVEL9  = 10000;
	private static final int LEVEL10 = 15000;
	private static final int[] EXP_REQUIRED = {LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5, LEVEL6, LEVEL7, LEVEL8, LEVEL9, LEVEL10};

	JPanel pane;
	File file;
	FileInputStream is;
	BufferedReader reader;	

	public MainFrame(String p) {
		setLayout(new FlowLayout());
		//Container pane = getContentPane();
		//pane.setLayout(new GridLayout(5, 4, 5, 5));
		pane = new JPanel(new GridLayout(6, 4, 5, 5));
		setTitle("Experience Visualizer");
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		file = new File(p);

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateDisplay();
			}
		});
		add(update);
		updateDisplay();
		pack();
	}

	private void updateDisplay() {
		pane.removeAll();
		try {
			is = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(is));

			String[] header = {"Skill", "Level", "EXP", "Progress"};
			String[] skills = {"Farming", "Fishing", "Foraging", "Mining", "Combat"};
			int[] levels = new int[5];
			int[] experience = new int[5];
			Double[] percents = new Double[5];

			String text = reader.readLine();
			is.close();
			reader.close();
			int start = text.indexOf("<experiencePoints>");
			int end = text.indexOf("</experiencePoints>");
			text = text.substring(start+23, end);
			String[] values = text.split("</int><int>");
			for (int i = 0; i < 5; i++) {
				experience[i] = Integer.parseInt(values[i]);
			}

			// calculate levels
			for (int i = 0; i < 5; i++) {
				int n = experience[i];
				if (n < LEVEL1) {
					levels[i] = 0;
				} else if (n < LEVEL2) {
					levels[i] = 1;
				} else if (n < LEVEL3) {
					levels[i] = 2;
				} else if (n < LEVEL4) {
					levels[i] = 3;
				} else if (n < LEVEL5) {
					levels[i] = 4;
				} else if (n < LEVEL6) {
					levels[i] = 5;
				} else if (n < LEVEL7) {
					levels[i] = 6;
				} else if (n < LEVEL8) {
					levels[i] = 7;
				} else if (n < LEVEL9) {
					levels[i] = 8;
				} else if (n < LEVEL10) {
					levels[i] = 9;
				} else {
					levels[i] = 10;
				}
			}

			// calculate percents to next level
			for (int i = 0; i < 5; i++) {
				int currentLevel = levels[i];
				int expNeeded = EXP_REQUIRED[currentLevel] - EXP_REQUIRED[currentLevel-1];
				int expProgress =  experience[i] - EXP_REQUIRED[currentLevel-1];
				percents[i] = 1.0*expProgress / expNeeded;
			}

			// add header to grid
			for (int i = 0; i < 4; i++) {
				JLabel l = new JLabel(header[i]);
				pane.add(l);
			}

			// add all skills to grid
			for (int i = 0; i < 5; i++) {
				JLabel skill = new JLabel(skills[i]);
				pane.add(skill);
				JLabel level = new JLabel(Integer.toString(levels[i]));
				pane.add(level);
				JLabel exp = new JLabel(String.format("%d / %d", experience[i], EXP_REQUIRED[levels[i]]));
				pane.add(exp);
				JLabel percent = new JLabel(String.format("%2.2f%%", 100*percents[i]));
				pane.add(percent);
			}

			add(pane);
			revalidate();
			repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
