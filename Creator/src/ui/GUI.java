package ui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.QBase;
import model.Question;
import util.Config;
import util.DeepCopy;
import util.OutputDocument;

import com.itextpdf.text.DocumentException;





public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_author;
	private JTextField textField_profile;
	private JTextField textField_subjectcode;
	private final JLabel lblqsize = new JLabel("/ 1");
	
	private String defaultPath = "";
	private FileFilter fileFilter = new FileNameExtensionFilter("Creator file", "creator");
	
	
	private QBase qBase;
	private int currQ;
	
	private JTextArea textArea_content = new JTextArea();
	private JTextArea textArea_varA = new JTextArea();
	private JTextArea textArea_varB = new JTextArea();
	private JTextArea textArea_varC = new JTextArea();
	
	private ImagePanel imagePanel;
	private ImagePanel imagePanelA;
	private ImagePanel imagePanelB;
	private ImagePanel imagePanelC;
	
	private File currFile;
	private boolean saved = false;
	private boolean ignoreSave = false;
	private boolean newBase = true;
	
	private JLabel label_current;
	private JSpinner spinner;
	
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	
	private JSpinner spinner_scaleContent = new JSpinner();
	private JSpinner spinner_scaleVarA = new JSpinner();
	private JSpinner spinner_scaleVarB = new JSpinner();
	private JSpinner spinner_scaleVarC = new JSpinner();
	
	private ResourceBundle messages;
	
	private Config config;
	
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
	
	private void renderQuestion(){
		Question q = qBase.getQuestions().get(this.currQ);
		this.scrollPane.setViewportView(textArea_content);
		this.textArea_content.setLineWrap(true);
		this.textArea_content.setText(q.getContent());
		this.scrollPane_1.setViewportView(textArea_varA);
		this.textArea_varA.setLineWrap(true);
		this.textArea_varA.setText(q.getVarA().getContent());
		this.scrollPane_2.setViewportView(textArea_varB);
		this.textArea_varB.setLineWrap(true);
		this.textArea_varB.setText(q.getVarB().getContent());
		this.scrollPane_3.setViewportView(textArea_varC);
		this.textArea_varC.setLineWrap(true);
		this.textArea_varC.setText(q.getVarC().getContent());

		this.label_current.setText(Integer.toString(this.currQ+1));
		
		this.imagePanel.setImage(q.getImg());
		this.imagePanel.resizeImage();
		
		this.imagePanelA.setImage(q.getVarA().getImg());
		this.imagePanelA.resizeImage();
		
		this.imagePanelB.setImage(q.getVarB().getImg());
		this.imagePanelB.resizeImage();
		
		this.imagePanelC.setImage(q.getVarC().getImg());
		this.imagePanelC.resizeImage();
		
		this.spinner_scaleContent.setValue(q.getImgScalePercent());
		this.spinner_scaleVarA.setValue(q.getVarA().getImgScalePercent());
		this.spinner_scaleVarB.setValue(q.getVarB().getImgScalePercent());
		this.spinner_scaleVarC.setValue(q.getVarC().getImgScalePercent());
		
		
		
	}
	
	private void notSaved(){
		this.saved = false;
		if(this.newBase == false){
			this.setTitle(currFile.getName() + "* - Creator pyta\u0144 egzaminacyjnych");
		}else{
			this.setTitle("NewBase* - Creator pyta\u0144 egzaminacyjnych");
		}
	}
	
	private void saved(){
		this.saved = true;
		this.newBase = false;
		this.setTitle(currFile.getName() + " - Creator pyta\u0144 egzaminacyjnych");
	}
	
	private void renderBaseInfo(){
		textField_name.setText(qBase.getName());
		textField_author.setText(qBase.getAuthor());
		textField_profile.setText(qBase.getProfile());
		textField_subjectcode.setText(qBase.getSubjectCode());
		
		
	}
	
	private void nextQuestion(){

		this.ignoreSave = true;
		if(this.currQ == qBase.getQuestions().size()-1){
			return;
		}else{
			this.currQ++;
			renderQuestion();
		}
		this.ignoreSave = false;
		
	}
	
	private void previousQuestion(){
		this.ignoreSave = true;
		if(this.currQ == 0){
			return;
		}else{
			this.currQ--;
			renderQuestion();
		}
		this.ignoreSave = false;
		
	}
	
	private void firstQuestion(){
		this.ignoreSave = true;
		this.currQ = 0;
		renderQuestion();
		this.ignoreSave = false;
		
	}
	
	private void lastQuestion(){
		this.ignoreSave = true;
		this.currQ = qBase.getQuestions().size()-1;
		renderQuestion();
		this.ignoreSave = false;
		
	}
	
	private void jumpTo(int val){
		val--;
		if(val >= qBase.getQuestions().size()){
			return;
		}
		
		this.currQ = val;
		renderQuestion();
		
		if(saved == false){
			notSaved();
		}
	}
	
	private void addQuestion(){
		qBase.addQuestion();
		this.currQ = qBase.getQuestions().size()-1;
		setQBaseSize();
		renderQuestion();
		this.notSaved();
	}
	
	private void removeQuestion(){
		if(qBase.getQuestions().size()-1 > 0){		//jesli mamy wiecej niz 1 pytanie
			if(this.currQ == qBase.getQuestions().size()-1){	//jesli jestesmy na ostatnim pytaniu
				this.currQ--;
				qBase.removeQuestion(this.currQ+1);	
				setQBaseSize();
				renderQuestion();
				this.notSaved();
				return;
			}
			
			qBase.removeQuestion(this.currQ);	
			setQBaseSize();
			renderQuestion();
			this.notSaved();
		}else{
			qBase.removeQuestion(this.currQ);
			qBase.addQuestion();
			setQBaseSize();
			renderQuestion();
			this.notSaved();
		}
	}
	
	private void newBase(){
		this.qBase = new QBase("QuestionBase","Anonymous", "Profile", "ABCD");
		this.currQ = 0;
		this.currFile = null;
		this.newBase = true;
		notSaved();
		renderBaseInfo();
		setQBaseSize();
		renderQuestion();
	}
	
	private void setQBaseSize(){
		String str = "/ " + Integer.toString(qBase.getQuestions().size());
		this.lblqsize.setText(str);
		
	}
	
	private String getFormat(String imageName)
	{
		String extension = "";

		int i = imageName.lastIndexOf('.');
		if (i > 0) {
		    extension = imageName.substring(i+1);
		}
		
		switch(extension)
	    {
	        case "png": return "PNG";
	        case "gif": return "GIF";
	        case "tiff": return "TIFF";
	        case "jpg": return "JPG";
	        case "jpeg": return "JPEG";
	    }

	    return "UNKNOWN";
	}
	
	private void setImg(int choose) throws IOException{		//choose: 0 - content, 1 - varA, 2 - varB, 3 - varC
		JFileChooser fc = new JFileChooser(defaultPath);
		
		int returnVal = fc.showSaveDialog(GUI.this);
		
		 File file;
		 
		 if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();
           } else {
           	return;
        }
		 
		 BufferedImage originalImage = ImageIO.read(file);
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 String format = getFormat(file.getName());
		 ImageIO.write( originalImage, format, baos );
		 baos.flush();
		 byte[] imageInByte = baos.toByteArray();
		 baos.close();
		 
		 switch(choose){
			 case 0:
			 	qBase.getQuestions().get(currQ).setImgInByte(imageInByte);
			 	qBase.getQuestions().get(currQ).setImg(originalImage);
			 	break;
			 
			 case 1:
				qBase.getQuestions().get(currQ).getVarA().setImgInByte(imageInByte);
				qBase.getQuestions().get(currQ).getVarA().setImg(originalImage);
				break;
			
			 case 2:
				qBase.getQuestions().get(currQ).getVarB().setImgInByte(imageInByte);
				qBase.getQuestions().get(currQ).getVarB().setImg(originalImage);
				break;
					
			 case 3:
				qBase.getQuestions().get(currQ).getVarC().setImgInByte(imageInByte);
				qBase.getQuestions().get(currQ).getVarC().setImg(originalImage);
		 }
		 
		 this.saved = false;
		 
		 renderQuestion();
			
	}
	
	private void deleteImg(int choose){		//choose: 0 - content, 1 - varA, 2 - varB, 3 - varC
		switch(choose){
			case 0:
				qBase.getQuestions().get(currQ).deleteImg();
				break;
			case 1:
				qBase.getQuestions().get(currQ).getVarA().deleteImg();
				break;
			case 2:
				qBase.getQuestions().get(currQ).getVarB().deleteImg();
				break;
			case 3:
				qBase.getQuestions().get(currQ).getVarC().deleteImg();
		}
		this.saved = false;
		this.renderQuestion();
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
	         out.writeObject(this.qBase);
	         out.close();
	         fileOut.close();
	         this.saved();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	private void saveAs(){				
		try
	      {
			JFileChooser fc = new JFileChooser(defaultPath);
			fc.setFileFilter(fileFilter);
			int returnVal = fc.showSaveDialog(GUI.this);
			
			 File file;
			 
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
	                file = new File(file.getAbsolutePath()+".creator");
	                this.currFile = file;
	            } else {
	            	return;
	         }
			
			 FileOutputStream fileOut = new FileOutputStream(file);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this.qBase);
	         out.close();
	         fileOut.close();
	         this.saved();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	private void open(){				//ladowanie pliku
	      try
	      {
	    	  
	    	  JFileChooser fc = new JFileChooser(defaultPath);
	    	  fc.setFileFilter(fileFilter);
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
	         this.qBase = (QBase) in.readObject();
	         in.close();
	         fileIn.close();
	         this.qBase.byteArrayToImg();
	         setQBaseSize();
		     currQ = 0;
		     renderQuestion();
		     renderBaseInfo();
		     this.saved();
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
		
		loadConfig();
		
		Locale currentLocale;
	     

	     currentLocale = new Locale(this.config.getLang(), this.config.getCountry());

	     messages = ResourceBundle.getBundle("i18n.MessagesBundle", currentLocale);
		
		setTitle("NewBase - Creator pyta\u0144 egzaminacyjnych");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1101, 720);
		
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
		mntmExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				exit();
			}
			
		});
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(messages.getString("subject"));
		lblNewLabel.setBounds(10, 14, 162, 14);
		contentPane.add(lblNewLabel);
		
		textField_name = new JTextField();
		textField_name.setBounds(182, 11, 332, 20);
		contentPane.add(textField_name);
		textField_name.setColumns(10);
		
		textField_name.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setName(textField_name.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setName(textField_name.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		
		JLabel lblAuthor = new JLabel(messages.getString("teacher"));
		lblAuthor.setBounds(10, 48, 162, 14);
		contentPane.add(lblAuthor);
		
		textField_author = new JTextField();
		textField_author.setColumns(10);
		textField_author.setBounds(182, 42, 332, 20);
		contentPane.add(textField_author);
		
		textField_author.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setAuthor(textField_author.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setAuthor(textField_author.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		
		//textField_profile = new JTextField();
		
		
		
		//textField_subjectcode = new JTextField();
		
		
	
		
		JLabel lblContent = new JLabel(messages.getString("content"));
		lblContent.setBounds(10, 144, 46, 14);
		contentPane.add(lblContent);
		
		JLabel lblA = new JLabel("A");
		lblA.setBounds(34, 260, 12, 14);
		contentPane.add(lblA);
		
		JLabel lblB = new JLabel("B");
		lblB.setBounds(34, 393, 12, 14);
		contentPane.add(lblB);
		
		JLabel lblC = new JLabel("C");
		lblC.setBounds(34, 516, 12, 14);
		contentPane.add(lblC);
		
		JButton btn_first = new JButton("|<");
		btn_first.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstQuestion();
			}
		});
		btn_first.setBounds(58, 592, 89, 23);
		contentPane.add(btn_first);
		
		JButton btn_prev = new JButton("<");
		btn_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousQuestion();
			}
		});
		btn_prev.setBounds(157, 592, 103, 23);
		contentPane.add(btn_prev);
		
		JButton btn_next = new JButton(">");
		btn_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextQuestion();
			}
		});
		btn_next.setBounds(326, 592, 89, 23);
		contentPane.add(btn_next);
		
		JButton btn_last = new JButton(">|");
		btn_last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lastQuestion();
			}
		});
		btn_last.setBounds(425, 592, 89, 23);
		contentPane.add(btn_last);
		
		lblqsize.setBounds(295, 596, 46, 14);
		contentPane.add(lblqsize);
		
		JButton btnAdd = new JButton(messages.getString("addQuestion"));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addQuestion();		
			}
		});
		btnAdd.setBounds(520, 592, 141, 23);
		contentPane.add(btnAdd);
		
		JButton btnRemove = new JButton(messages.getString("removeQuestion"));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeQuestion();
				
			}
		});
		btnRemove.setBounds(671, 592, 141, 23);
		contentPane.add(btnRemove);
		
		JButton btnImage = new JButton(messages.getString("addImage"));
		btnImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setImg(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImage.setBounds(754, 126, 161, 23);
		contentPane.add(btnImage);
		
		JButton btnNewButton = new JButton(messages.getString("addImage"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setImg(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(754, 240, 161, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(messages.getString("addImage"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setImg(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(754, 372, 161, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton(messages.getString("addImage"));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setImg(3);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(754, 492, 161, 23);
		contentPane.add(btnNewButton_2);
		
		imagePanel = new ImagePanel(null);
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanel.setBounds(925, 117, 150, 70);
		contentPane.add(imagePanel);
		
		imagePanelA = new ImagePanel(null);
		imagePanelA.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanelA.setBounds(925, 231, 150, 70);
		contentPane.add(imagePanelA);
		
		imagePanelB = new ImagePanel(null);
		imagePanelB.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanelB.setBounds(925, 363, 150, 70);
		contentPane.add(imagePanelB);
		
		imagePanelC = new ImagePanel(null);
		imagePanelC.setBorder(new LineBorder(new Color(0, 0, 0)));
		imagePanelC.setBounds(925, 483, 150, 70);
		contentPane.add(imagePanelC);
		
		JButton btnDelimage = new JButton(messages.getString("deleteImage"));
		btnDelimage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteImg(0);
			}
		});
		btnDelimage.setBounds(754, 160, 161, 23);
		contentPane.add(btnDelimage);
		
		JButton btnNewButton_3 = new JButton(messages.getString("deleteImage"));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteImg(1);
			}
		});
		btnNewButton_3.setBounds(754, 274, 161, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton(messages.getString("deleteImage"));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteImg(2);
			}
		});
		btnNewButton_4.setBounds(754, 406, 161, 23);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton(messages.getString("deleteImage"));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteImg(3);
			}
		});
		btnNewButton_5.setBounds(754, 526, 161, 23);
		contentPane.add(btnNewButton_5);
		
		label_current = new JLabel("1");
		label_current.setBounds(270, 596, 26, 14);
		contentPane.add(label_current);
		
		JButton btnJumpTo = new JButton(messages.getString("jumpTo"));
		btnJumpTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jumpTo((Integer)spinner.getValue());
			}
		});
		btnJumpTo.setBounds(157, 626, 103, 23);
		contentPane.add(btnJumpTo);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setBounds(270, 627, 40, 20);
		contentPane.add(spinner);
		
		JButton btnGenerate = new JButton(messages.getString("previewEntireBase"));
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
						
						QBase tmpBase =  (QBase) DeepCopy.copy(qBase);
						tmpBase.shuffleVariants();
						try {
							OutputDocument.createDocument(tmpBase);
						} catch (DocumentException | IOException e) {
							e.printStackTrace();
						}
					
			
			}
		});
		btnGenerate.setBounds(822, 592, 253, 23);
		contentPane.add(btnGenerate);
		
		JLabel lblKierunekKsztacenia = new JLabel(messages.getString("profile"));
		lblKierunekKsztacenia.setBounds(589, 14, 184, 15);
		contentPane.add(lblKierunekKsztacenia);
		
		JLabel lblNewLabel_1 = new JLabel(messages.getString("code"));
		lblNewLabel_1.setBounds(589, 48, 161, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblCorrect = new JLabel(messages.getString("correct"));
		lblCorrect.setForeground(new Color(0, 128, 0));
		lblCorrect.setBackground(Color.GREEN);
		lblCorrect.setBounds(619, 260, 89, 14);
		contentPane.add(lblCorrect);
		
		JLabel lblIncorrect = new JLabel(messages.getString("incorrect"));
		lblIncorrect.setForeground(Color.RED);
		lblIncorrect.setBounds(619, 393, 89, 14);
		contentPane.add(lblIncorrect);
		
		JLabel lblIncorrect_1 = new JLabel(messages.getString("incorrect"));
		lblIncorrect_1.setForeground(Color.RED);
		lblIncorrect_1.setBounds(619, 516, 89, 14);
		contentPane.add(lblIncorrect_1);
		
		textField_profile = new JTextField();
		textField_profile.setBounds(783, 11, 292, 20);
		contentPane.add(textField_profile);
		textField_profile.setColumns(10);
		
		textField_profile.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setProfile(textField_profile.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setProfile(textField_profile.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		
		textField_subjectcode = new JTextField();
		textField_subjectcode.setBounds(783, 45, 292, 20);
		contentPane.add(textField_subjectcode);
		textField_subjectcode.setColumns(10);
		
		textField_subjectcode.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setSubjectCode(textField_subjectcode.getText());
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.setSubjectCode(textField_subjectcode.getText());
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 73, 551, 133);
		contentPane.add(scrollPane);
		
		textArea_content.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
		textArea_content.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).setContent(textArea_content.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).setContent(textArea_content.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		textArea_content.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(58, 217, 551, 114);
		contentPane.add(scrollPane_1);
		
		//JTextArea textArea_varA = new JTextArea();
		textArea_varA.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
		textArea_varA.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarA().setContent(textArea_varA.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarA().setContent(textArea_varA.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		textArea_varA.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 255, 0), new Color(0, 255, 0), new Color(0, 255, 0), new Color(0, 255, 0)));
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(58, 342, 551, 114);
		contentPane.add(scrollPane_2);
		
		//JTextArea textArea_varB = new JTextArea();
		textArea_varB.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
		textArea_varB.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarB().setContent(textArea_varB.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarB().setContent(textArea_varB.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		textArea_varB.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED, Color.RED, Color.RED));
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(58, 467, 551, 114);
		contentPane.add(scrollPane_3);
		
		//JTextArea textArea_varC = new JTextArea();
		textArea_varC.setFont(new Font("Arial Unicode MS", Font.PLAIN, 20));
		textArea_varC.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarC().setContent(textArea_varC.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Added character to" + currQ);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				qBase.getQuestions().get(currQ).getVarC().setContent(textArea_varC.getText());
				if(!ignoreSave){
					notSaved();
				}
				//System.out.println("Removed character from" + currQ);
				
			}
			
		});
		textArea_varC.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
		
		JButton btnNewButton_6 = new JButton(messages.getString("previewSingleQuestion"));
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Question q = qBase.getQuestions().get(currQ);
				
				try {
					OutputDocument.previewSingleQuestion(q);
				} catch (DocumentException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_6.setBounds(822, 626, 253, 23);
		contentPane.add(btnNewButton_6);
		
		JLabel lblScale = new JLabel(messages.getString("scale") + " (%)");
		lblScale.setBounds(858, 201, 107, 14);
		contentPane.add(lblScale);
		
		JLabel label = new JLabel(messages.getString("scale") + " (%)");
		label.setBounds(858, 315, 107, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel(messages.getString("scale") + " (%)");
		label_1.setBounds(858, 447, 107, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel(messages.getString("scale") + " (%)");
		label_2.setBounds(858, 564, 107, 14);
		contentPane.add(label_2);
		
		
		spinner_scaleContent.setModel(new SpinnerNumberModel(100, 1, 1000, 1));
		spinner_scaleContent.setBounds(925, 198, 63, 20);
		
		spinner_scaleContent.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            qBase.getQuestions().get(currQ).setImgScalePercent((int)spinner_scaleContent.getValue());
	        }
	    });
		
		contentPane.add(spinner_scaleContent);
		
		
		spinner_scaleVarA.setModel(new SpinnerNumberModel(100, 1, 1000, 1));
		spinner_scaleVarA.setBounds(925, 312, 63, 20);
		spinner_scaleVarA.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            qBase.getQuestions().get(currQ).getVarA().setImgScalePercent((int)spinner_scaleVarA.getValue());
	        }
	    });
		contentPane.add(spinner_scaleVarA);
		
		
		spinner_scaleVarB.setModel(new SpinnerNumberModel(100, 1, 1000, 1));
		spinner_scaleVarB.setBounds(925, 444, 63, 20);
		spinner_scaleVarB.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            qBase.getQuestions().get(currQ).getVarB().setImgScalePercent((int)spinner_scaleVarB.getValue());
	        }
	    });
		contentPane.add(spinner_scaleVarB);
		
		
		spinner_scaleVarC.setModel(new SpinnerNumberModel(100, 1, 1000, 1));
		spinner_scaleVarC.setBounds(925, 561, 63, 20);
		spinner_scaleVarC.addChangeListener(new ChangeListener() {

	        @Override
	        public void stateChanged(ChangeEvent e) {
	            qBase.getQuestions().get(currQ).getVarC().setImgScalePercent((int)spinner_scaleVarC.getValue());
	        }
	    });
		contentPane.add(spinner_scaleVarC);
		
		this.newBase();
		
		this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent evt) {
			     exit();
			   }
		});
		
		this.defaultPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		
	}
		
		
}
