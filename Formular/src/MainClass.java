import java.awt.*;
import java.util.*;
import java.util.stream.Stream;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class MainClass {
public static JFrame mainFrame = new JFrame("Frame");
public static JLabel label = new JLabel("Meine Termine");
public static JButton button = new JButton();
public static File folder = new File("E:\\Termine");
public static File[] listOfFiles = folder.listFiles();
public static String terminFileName = "swag.txt";
public static File terminFile = new File(folder + "/" + terminFileName);
public static ArrayList<Termin> termine = new ArrayList<Termin>();
public static Termin[] terminArray = new Termin[termine.size()];
public static DefaultListModel<Termin> list = new DefaultListModel<>();
public static DefaultListModel<String> listStr = new DefaultListModel<>();
public static DefaultListModel<String> allFiles = new DefaultListModel<>();
public static JList<String> fileList = new JList<String>(allFiles);
public static JScrollPane scrollPane = new JScrollPane();
public static ArrayList<String> fileNames = new ArrayList<String>();
public static JList<String> listmodel = new JList<String>(listStr);
public static JTextArea textField = new JTextArea();

	public static void main(String[] args) {
		System.out.println("swag");
		terminArray = termine.toArray(terminArray);
		createList();
		getTermine();
		__initMAINFRAME();
		__initOverlay();
		createTextField();
		crawlFiles();

		mainFrame.repaint();
	}
	public static String path() {

		
		return "swag.txt";
	}

	public static void writeToFile(int line) {
		try {
			FileWriter out = new FileWriter(terminFile,true);
			out.write("swag\r\n");
			System.out.println("succes");
			out.close();

		} catch (IOException ex) {	}

	}
	public static void __initMAINFRAME() {
		
		mainFrame.setSize(500, 500);
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
	}
	public static Dimension listdim() {
		
		int x = 200;
		int y = 50;
		for (Termin t : termine) {
			y += 12;
		}
		return new Dimension(x,y);
	}
	public static void createTextField() {
		textField.setLocation(new Point(300, 20));
		textField.setSize(new Dimension(150,100));
		String text = "swag ";		
		mainFrame.add(textField);
	}
	public static String textFieldText(String name, String date) {
		String line1 = name;
		String line2 = date;
		String cc = "Name: " + line1 + "\r\nDate: " + line2;
		return cc; 
	}
	public static long countLine() throws IOException{
		System.out.println(terminFile.toPath());
		Stream<String> fileLength = Files.lines(terminFile.toPath());
		System.out.println("Counted Lines out of " + terminFileName);
		fileLength.close();
			return fileLength.count();
	}
	public static String getLine(int line) throws IOException {
		System.out.println("Got Line");
		String lineStr = Files.readAllLines(terminFile.toPath()).get(line);
		System.out.println(lineStr);
		System.out.println("Got Line");
		return lineStr;
	}
	public static void getTermine() {
		try {
			for(int i = 0; i <= countLine(); i++) {
				String line = getLine(i);
				System.out.println("asdasdsd");
				String[] sepline = line.split(",");
				String TName = sepline[0];
				String TDate = sepline[1];
				Termin t = new Termin(TName,TDate);
				termine.add(t);
				System.out.println("Termin Erstellt: Name: " + t.TName);
			}
		}catch(Exception e) {  }
	}
	public static void crawlFiles() {
		for(int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				allFiles.addElement(listOfFiles[i].getName());
				fileNames.add(listOfFiles[i].getName());
			}
		}
	}
	public static void createSubList() {
		for (Termin t : termine) {
			System.out.println(t.TName);
			listStr.addElement(t.TName);
			
		}
	}
	public static void createList() {

		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileList.setLocation(20,listmodel.getLocation().y + 200);
		fileList.setSize(new Dimension(200,200));
		fileList.setVisible(true);
		listmodel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listmodel.setVisible(true);
		listmodel.setLocation(new Point(20,20));
		listmodel.setSize(listdim());
		scrollPane.setViewportView(fileList);
		scrollPane.setPreferredSize(new Dimension(40,40));
		fileList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				termine.clear();
				terminFileName = fileNames.get(fileList.getSelectedIndex());
				for (int i = 0; i <= fileNames.size(); i++) {
				}
				getTermine();
				try {
				}catch (Exception s) {}
				System.out.println(terminFileName);
				System.out.println(fileList.getSelectedIndex());
				createSubList();
			}

		});
		listmodel.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				checkTermin(listmodel.getSelectedIndex());
				System.out.println(listmodel.getSelectedIndex());
			}
		});
	}
	public static void checkTermin(int index) {
		textField.setText("0");
		textField.setText(textFieldText(termine.get(index).TName,termine.get(index).TDate));
	}
	public static void __initOverlay() {
		button.setSize(90, 30);
		label.setSize(new Dimension(120,20));
		label.setLocation(new Point(30,2));
		mainFrame.add(label);
		mainFrame.add(listmodel);
		mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(mainFrame, "Are you sure?",
						"Really closing?",JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) 
				{System.exit(0);
				}
			}
		});
		
		mainFrame.add(fileList);
		button.setText("Submit");
	    button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	System.out.println("swag");
	            }});
	            
		button.setLocation(mainFrame.getWidth()/2 - button.getWidth()/2, 400);
		mainFrame.add(button);
	}
}
