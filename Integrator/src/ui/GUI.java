package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import model.QBase;
import model.SuperBase;
import util.Config;

import com.itextpdf.text.DocumentException;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private SuperBase superBase;
	//private JFileChooser fc;
	private File currFile;
	private JSpinner spinner_groups = new JSpinner();
	private JTextField txt_letterofset;
	private JLabel lblTotal;
	
	private boolean newBase = true;
	
	private boolean renderTable = false;
	
	private ResourceBundle messages;
	
	private String defaultPath = "";
	
	private Config config;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void newBase(){
		this.superBase = new SuperBase();
	
		this.currFile = null;
		this.newBase = true;
		notSaved();
		renderTable();
	}
	
	private void saveAs(){				//zapisz jako
		try
	      {
			JFileChooser fc = new JFileChooser(this.defaultPath);
			FileFilter fileFilter = new FileNameExtensionFilter("Integrator file", "integrator");
			fc.setFileFilter(fileFilter);
			int returnVal = fc.showSaveDialog(GUI.this);
			
			 File file;
			 
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
	                file = new File(file.getAbsolutePath()+".integrator");
	                this.currFile = file;
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            	return;
	         }
			
			 FileOutputStream fileOut = new FileOutputStream(file);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.superBase);
	         out.close();
	         fileOut.close();
	         this.saved();
	         //this.saved();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	private void setAmounts(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();	
		for(int i = 0; i < superBase.getQbcoll().size(); i++){
			
				superBase.getQbcoll().get(i).setAmountToTest((int)model.getValueAt(i,5));;				
			
		}
	}
	
	
	private void save(){					//zapis pliku
		try
	      {
			 if(this.currFile == null){				//jesli jeszcze plik nie powstal
				 saveAs();
				 return;
			 }
			
			 FileOutputStream fileOut = new FileOutputStream(this.currFile);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.superBase);
	         out.close();
	         fileOut.close();
	         this.saved();
	        // this.saved();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	private void open(){				//ladowanie pliku
	      try
	      {
	        JFileChooser fc = new JFileChooser(this.defaultPath);
			FileFilter fileFilter = new FileNameExtensionFilter("Integrator file", "integrator");
			fc.setFileFilter(fileFilter);
	    	 int returnVal = fc.showOpenDialog(GUI.this);
	    	 
	    	 File file;
	    	 
	    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
	                this.currFile = file;
	            } else {
	            	return;
	         }
	    	  
	    	 FileInputStream fileIn = new FileInputStream(file);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.superBase = (SuperBase) in.readObject();
	         in.close();
	         fileIn.close();
	         renderTable = true;
	         renderTable();
	         saved();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Incorrect format");
	         c.printStackTrace();
	         return;
	      }
	      
	      
	      
	}
	
	private void notSaved(){
		if(this.newBase == false){
			this.setTitle(currFile.getName() + "* - Integrator pyta\u0144 egzaminacyjnych");
		}else{
			this.setTitle("NewBase* - Integrator pyta\u0144 egzaminacyjnych");
		}
	}
	
	private void saved(){
		this.newBase = false;
		this.setTitle(currFile.getName() + " - Integrator pyta\u0144 egzaminacyjnych");
	}
	
	private void addBase(){
		try
	      {
	    	JFileChooser fc = new JFileChooser(defaultPath);
	    	FileFilter fileFilter = new FileNameExtensionFilter("Creator file", "creator");
	    	fc.setFileFilter(fileFilter);
			fc.setMultiSelectionEnabled(true);
			int returnVal = fc.showOpenDialog(GUI.this);
	    	 
	    	 File files[];
	    	 
	    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                files = fc.getSelectedFiles();
	                
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            	return;
	         }
	    	  
	    	 for(File f: files){
		    	 FileInputStream fileIn = new FileInputStream(f);
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         superBase.add((QBase) in.readObject());
		         in.close();
		         fileIn.close();
	    	 }
	         renderTable = true;
	         renderTable();
		   
		     this.notSaved();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Incorrect format");
	         c.printStackTrace();
	         return;
	      }
	}
	
	private void renderTable(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for(QBase qb: superBase.getQbcoll()){
			renderTable = true;
			model.addRow(new Object[]{qb.getName(), qb.getAuthor(), qb.getProfile(), qb.getSubjectCode(), qb.getQuestions().size(), qb.getAmountToTest(), new Boolean(false)});
		}
		
		setTotal();
	}
	
	private void deleteSelected(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();		
		for(int i = 0; i < superBase.getQbcoll().size(); i++){
			if((boolean)model.getValueAt(i, 6)){
				superBase.getQbcoll().get(i).setToDelete(true);				
			}
		}
		
		for(int i = 0; i < superBase.getQbcoll().size(); i++){
			if(superBase.getQbcoll().get(i).isToDelete()){
				superBase.getQbcoll().remove((int)i);
				i--;
			}
		}
		renderTable = true;
		renderTable();
		this.notSaved();
	}
	
	private int calcTotal(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		int res = 0;
		
		for(int i = 0; i < superBase.getQbcoll().size(); i++){
			
			int size = (int)model.getValueAt(i, 4);
			int val = (int)model.getValueAt(i, 5);
			
			if(val > 0 && val <= size){
				res += val;
			}
				
			//(int)model.getValueAt(i, 5);
			
		}
		
		return res;
	}
	
	private void setTotal(){
		this.lblTotal.setText(messages.getString("total") + ": " + calcTotal());
		
	}
	
	private void loadConfig() throws IOException, ClassNotFoundException{
		//Config config;
		
		File file = new File("config");
		
		if(!file.exists() || file.isDirectory()) {
			/* do something */ 
			config = new Config("en","US");
			FileOutputStream fileOut = new FileOutputStream(file);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(config);
	        out.close();
	        fileOut.close();
		}
		
		FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        config = (Config) in.readObject();
        in.close();
        fileIn.close();
        
        //return config;
	}
	
	private void setLang(String lang, String region) throws IOException{
		if(config.getLang() == lang){
			return;
		}
		
		config = new Config(lang,region);
		File file = new File("config");
		FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(config);
        out.close();
        fileOut.close();
        System.out.println("Lang changed to " + lang.toUpperCase());
        JOptionPane.showMessageDialog(null, messages.getString("changeLang"));
	}
	
	private void exit(){
		 int reply = JOptionPane.showConfirmDialog(this, messages.getString("exitConfirmation"), 
			      null,      
			      JOptionPane.YES_NO_OPTION, 
			      JOptionPane.INFORMATION_MESSAGE);
		 
		 if(reply == JOptionPane.YES_OPTION){
			 System.exit(0);
		 }
		 
		 
		
		
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	private GUI() throws ClassNotFoundException, IOException {
		superBase = new SuperBase();
		
		 Locale currentLocale;
	     
		 loadConfig();
	     currentLocale = new Locale(config.getLang(), config.getCountry());

	     messages = ResourceBundle.getBundle("i18n.MessagesBundle", currentLocale);
		
		setTitle("Integrator pyta\u0144 egzaminacyjnych");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1016, 664);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu(messages.getString("file"));
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem(messages.getString("new"));
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		
		mntmNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newBase();
			}
			
		});
		
		JMenuItem mntmOpenFile = new JMenuItem(messages.getString("openFile"));
		mntmOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpenFile);
		
		mntmOpenFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				open();
			}
			
		});
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmSave = new JMenuItem(messages.getString("save"));
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		mntmSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				save();
			}
			
		});
		
		JMenuItem mntmSaveAs = new JMenuItem(messages.getString("saveAs"));
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmSaveAs);
		
		mntmSaveAs.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveAs();
			}
			
		});
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem(messages.getString("exit"));
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);
		
		JMenu mnLanguage = new JMenu(messages.getString("language"));
		menuBar.add(mnLanguage);
		
		JMenuItem mntmEnglish = new JMenuItem(messages.getString("english"));
		mnLanguage.add(mntmEnglish);
		
		mntmEnglish.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					setLang("en","US");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		JMenuItem mntmPolish = new JMenuItem(messages.getString("polish"));
		mnLanguage.add(mntmPolish);
		mntmPolish.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					setLang("pl","PL");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		mntmExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				exit();
			}
			
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAddBase = new JButton(messages.getString("addBase"));
		btnAddBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//DefaultTableModel model = (DefaultTableModel) table.getModel();
				//model.addRow(new Object[]{"a", "b", "c", "d"});
				
				addBase();
				
				
			}
		});
		btnAddBase.setBounds(10, 11, 175, 23);
		contentPane.add(btnAddBase);
		
		JLabel lblNumberOfGroups = new JLabel(messages.getString("amountOfSets"));
		lblNumberOfGroups.setBounds(10, 579, 140, 14);
		contentPane.add(lblNumberOfGroups);
		
		
		spinner_groups.setModel(new SpinnerNumberModel(1, 1, 99, 1));
		spinner_groups.setBounds(140, 576, 45, 20);
		contentPane.add(spinner_groups);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 980, 523);
		contentPane.add(scrollPane);
		
		table = new JTable(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		        Component comp = super.prepareRenderer(renderer, row, col);
		        Object value = getModel().getValueAt(row, 4);
		        Object value2 = getModel().getValueAt(row, 5);
		        
		        
		            if ((int)value < (int)value2) {
		                comp.setBackground(Color.red);
		            }else{
		            	if((int)value2 <= 0){	
		            		comp.setBackground(Color.orange);
		            	}else{
		            		comp.setBackground(Color.white);
		            	}
		            }
		        
		        return comp;
		    }
		};
		scrollPane.setViewportView(table);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					messages.getString("subject"), messages.getString("teacher"), messages.getString("profile"), messages.getString("code"), messages.getString("amountInBase"), messages.getString("amountToTest"), messages.getString("delete")
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, Integer.class, Integer.class, Boolean.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		JButton btnDeleteSelected = new JButton(messages.getString("deleteSelected"));
		btnDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteSelected();
			}
		});
		btnDeleteSelected.setBounds(815, 11, 175, 23);
		contentPane.add(btnDeleteSelected);
		
		JButton btnGenerate = new JButton(messages.getString("generateSets"));
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setAmounts();

				try {
					char letter = txt_letterofset.getText().charAt(0);
					superBase.createSets(letter, (Integer)spinner_groups.getValue());
				} catch (DocumentException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnGenerate.setBounds(520, 575, 193, 23);
		contentPane.add(btnGenerate);
		
		JLabel lblStartWithLetter = new JLabel(messages.getString("firstLetter"));
		lblStartWithLetter.setBounds(250, 579, 164, 14);
		contentPane.add(lblStartWithLetter);
		
		txt_letterofset = new JTextField();
		txt_letterofset.setText("A");
		txt_letterofset.setBounds(424, 576, 86, 20);
		contentPane.add(txt_letterofset);
		txt_letterofset.setColumns(10);
		
		lblTotal = new JLabel(messages.getString("total") + ": 0");
		lblTotal.setBounds(723, 579, 267, 14);
		contentPane.add(lblTotal);
		
		JButton btnNewButton = new JButton(messages.getString("generateEntireBase"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					superBase.createDocumentOfEntireBase();
				} catch (DocumentException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(380, 11, 221, 23);
		contentPane.add(btnNewButton);
		
		table.getModel().addTableModelListener(new TableModelListener(){

			@Override
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				if(!renderTable){
					for(int i = 0; i < superBase.getQbcoll().size(); i++){					
						superBase.getQbcoll().get(i).setAmountToTest((int)table.getModel().getValueAt(i, 5));
					}
					setTotal();
				}
				
				renderTable = false;
				
				notSaved();
				
			}
		
		});
		
		this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
			     exit();
			   }
		});
		
		this.defaultPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		
		
	}
}
