package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.QBase;
import model.SuperBase;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private SuperBase superBase;
	private JFileChooser fc;
	private File currFile;
	JSpinner spinner_groups = new JSpinner();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void newBase(){
		this.superBase = new SuperBase();
	
		this.currFile = null;
	//	this.newBase = true;
	//	notSaved();
	//	renderAuthorName();
	//	setQBaseSize();
		renderTable();
	}
	
	public void saveAs(){				//zapisz jako
		try
	      {
			int returnVal = fc.showSaveDialog(GUI.this);
			
			 File file;
			 
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
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
	         //this.saved();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	
	
	public void save(){					//zapis pliku
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
	        // this.saved();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public void open(){				//ladowanie pliku
	      try
	      {
	    	 int returnVal = fc.showOpenDialog(GUI.this);
	    	 
	    	 File file;
	    	 
	    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
	                this.currFile = file;
	                //This is where a real application would open the file.
	                //log.append("Opening: " + file.getName() + "." + newline);
	            } else {
	                //log.append("Open command cancelled by user." + newline);
	            	return;
	         }
	    	  
	    	 FileInputStream fileIn = new FileInputStream(file);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.superBase = (SuperBase) in.readObject();
	         in.close();
	         fileIn.close();
	        // this.superBase();
	        // setQBaseSize();
		    // currQ = 0;
		    // renderQuestion();
		    // renderAuthorName();
		    // this.saved();
	         renderTable();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Niepoprawny format");
	         c.printStackTrace();
	         return;
	      }
	      
	      
	      
	}
	
	public void addBase(){
		try
	      {
	    	 
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
	         
	         renderTable();
		   
		     //this.saved();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Niepoprawny format");
	         c.printStackTrace();
	         return;
	      }
	}
	
	public void renderTable(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for(QBase qb: superBase.getQbcoll()){
			
			model.addRow(new Object[]{qb.getName(), qb.getAuthor(), qb.getQuestions().size(), 0, new Boolean(false)});
		}
	}
	
	public void deleteSelected(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();		
		for(int i = 0; i < superBase.getQbcoll().size(); i++){
			if((boolean)model.getValueAt(i, 4)){
				superBase.getQbcoll().get(i).setToDelete(true);				
			}
		}
		
		for(int i = 0; i < superBase.getQbcoll().size(); i++){
			if(superBase.getQbcoll().get(i).isToDelete()){
				superBase.getQbcoll().remove((int)i);
				i--;
			}
		}
		
		renderTable();
	}
	
	public void setAmounts(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();	
		for(int i = 0; i < superBase.getQbcoll().size(); i++){
			
				superBase.getQbcoll().get(i).setAmountToTest((int)model.getValueAt(i,3));;				
			
		}
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		superBase = new SuperBase();
		fc = new JFileChooser("C:\\Users\\Miszelek\\Desktop");
		
		setTitle("Integrator pyta� egzaminacyjnych");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1016, 664);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		
		mntmNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newBase();
			}
			
		});
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File...");
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
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		mntmSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				save();
			}
			
		});
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
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
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAddBase = new JButton("Add Base");
		btnAddBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//DefaultTableModel model = (DefaultTableModel) table.getModel();
				//model.addRow(new Object[]{"a", "b", "c", "d"});
				
				addBase();
				
				
			}
		});
		btnAddBase.setBounds(10, 11, 175, 23);
		contentPane.add(btnAddBase);
		
		JLabel lblNumberOfGroups = new JLabel("Amount of groups");
		lblNumberOfGroups.setBounds(10, 579, 140, 14);
		contentPane.add(lblNumberOfGroups);
		
		
		spinner_groups.setModel(new SpinnerNumberModel(1, 1, 4, 1));
		spinner_groups.setBounds(156, 576, 29, 20);
		contentPane.add(spinner_groups);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 734, 523);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Title", "Author", "# of Questions","# to Test", "Delete"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Integer.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		JButton btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteSelected();
			}
		});
		btnDeleteSelected.setBounds(195, 11, 175, 23);
		contentPane.add(btnDeleteSelected);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setAmounts();
				superBase.createTest((Integer)spinner_groups.getValue());
			}
		});
		btnGenerate.setBounds(380, 11, 89, 23);
		contentPane.add(btnGenerate);
		
		JLabel lblStartWithLetter = new JLabel("Start with letter");
		lblStartWithLetter.setBounds(250, 579, 120, 14);
		contentPane.add(lblStartWithLetter);
		
		textField = new JTextField();
		textField.setBounds(380, 576, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		/*final JCheckBox checkBox = new JCheckBox();
		table.getColumn("Delete").setCellRenderer(new DefaultTableCellRenderer() {
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		      checkBox.setSelected(((Boolean)value).booleanValue()) ;
		      return checkBox;
		    }
		});*/
		
		
	}
}
