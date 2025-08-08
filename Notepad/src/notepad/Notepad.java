package notepad;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Notepad extends JFrame implements ActionListener{

    private JTextArea area;
    private JScrollPane scpane;
    String text = "";
    
    JMenuBar menuBar;
    JMenu file, edit, mode;
    JMenuItem newdoc, open, save, print, exit;
    JMenuItem copy, paste, cut, selectall;
    JMenuItem light, dark;
    JButton zoomIn,zoomOut;
    int font=24;
    
    public Notepad() {
        setTitle("Notepad");
        ImageIcon notepadIcon = new ImageIcon(ClassLoader.getSystemResource("icon/notepad.png"));
        Image icon = notepadIcon.getImage();
        setIconImage(icon);
        
        setSize(1950, 1050);
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, font));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        
        menuBar = new JMenuBar(); 
        menuBar.setBackground(new Color(255,247,247));
        
        file = new JMenu("File"); 
        edit = new JMenu("Edit");
        mode = new JMenu("Mode");
        
        file.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        edit.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        mode.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        
        newdoc = new JMenuItem("New");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        print = new JMenuItem("Print");
        exit = new JMenuItem("Exit");
        
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        cut = new JMenuItem("Cut");
        selectall = new JMenuItem("Select All");
        
        light = new JMenuItem("Light");
        dark = new JMenuItem("Dark");
        
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));       
        
        light.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        dark.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        
        newdoc.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        print.addActionListener(this);
        exit.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        cut.addActionListener(this);
        selectall.addActionListener(this);
        light.addActionListener(this);
        dark.addActionListener(this);
        
        setJMenuBar(menuBar);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(mode);
        
        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);
        
        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectall);
        
        mode.add(light);
        mode.add(dark);
        
        zoomIn = new JButton("+");
        zoomIn.setBackground(new Color(255,247,247));
        zoomIn.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        zoomIn.setBorderPainted(false);
        zoomIn.setFocusPainted(false);
        zoomIn.addActionListener(this);
        menuBar.add(zoomIn);
        
        zoomOut = new JButton("-");
        zoomOut.setBackground(new Color(255,247,247));
        zoomOut.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        zoomOut.setBorderPainted(false);
        zoomOut.setFocusPainted(false);
        zoomOut.addActionListener(this);
        menuBar.add(zoomOut);
           
        scpane = new JScrollPane(area); 
        menuBar.setBorder(BorderFactory.createEmptyBorder());
        scpane.setBorder(BorderFactory.createLineBorder(Color.white, 7));
        scpane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.LIGHT_GRAY; 
            }
        });
        add(scpane, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("New")) {
            area.setText("");
        
        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setAcceptAllFileFilterUsed(false); 
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt"); 
            chooser.addChoosableFileFilter(restrict);
            int result = chooser.showOpenDialog(this);
            
            if(result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
				
                try{
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read( br, null );
                    br.close();
                    area.requestFocus();
                }catch(Exception e){
                    System.out.print(e);
                }
            }
        } else if(ae.getActionCommand().equals("Save")){
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);
            
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(ae.getActionCommand().equals("Print")){
            try{
                area.print();
            }catch(Exception e){}
        }else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        }else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText();
        }else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition());
        }else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        }else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll();
        }else if (ae.getActionCommand().equals("Light")) {

            area.setBackground(Color.WHITE);
            area.setForeground(Color.BLACK);
            area.setCaretColor(Color.BLACK);
            
            menuBar.setBackground(new Color(255,247,247));
            
            zoomOut.setBackground(new Color(255,247,247));
            zoomIn.setBackground(new Color(255,247,247));
            zoomOut.setForeground(Color.black);
            zoomIn.setForeground(Color.black);
            
            scpane.getVerticalScrollBar().setBackground(Color.gray);
            scpane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = Color.LIGHT_GRAY; 
                }
            });

            file.setForeground(Color.BLACK);
            edit.setForeground(Color.BLACK);
            mode.setForeground(Color.BLACK);
            scpane.setBorder(BorderFactory.createLineBorder(Color.white, 7));
        } 
        else if (ae.getActionCommand().equals("Dark")) {

            area.setBackground(new Color(30,30,30));
            area.setForeground(Color.WHITE);
            area.setCaretColor(Color.WHITE);

            menuBar.setBackground(new Color(51,51,51));
            scpane.setBorder(BorderFactory.createLineBorder(new Color(30,30,30), 7));
            scpane.getVerticalScrollBar().setBackground(new Color(51,51,51));
            
            zoomOut.setBackground(new Color(51,51,51));
            zoomIn.setBackground(new Color(51,51,51));
            zoomIn.setForeground(Color.WHITE);
            zoomOut.setForeground(Color.WHITE);
            
            scpane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = new Color(100, 100, 100);
                }
            });

            file.setForeground(Color.WHITE);
            edit.setForeground(Color.WHITE);
            mode.setForeground(Color.WHITE);
        }else if(ae.getActionCommand().equals("+")) {
        	if(font<30)
        	{
        		font++;
        		area.setFont(new Font("SAN_SERIF", Font.PLAIN, font));
        	}
        }else if(ae.getActionCommand().equals("-")) {
        	if(font>20)
        	{
        		font--;
        		area.setFont(new Font("SAN_SERIF", Font.PLAIN, font));
        	}
        }
    }



    public static void main(String[] args) {
        new Notepad();
    }

}