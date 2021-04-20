package s23603.employees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame
{
    private JMenuBar menuBar;
    private JPanel contentPanel;
    
    private JMenu fileMenu;
    private JMenuItem openTrigger;
    private JMenuItem saveTrigger;
    private JFileChooser fileChooser;
    
    private EmployeeListIO employeeListIO;
    
    
    public UserInterface(EmployeeListIO employeeListIO){
        super("Employees Data List");
        
        this.employeeListIO = employeeListIO;
        
        // Menu bar setup
        {
            openTrigger = new JMenuItem("Open");
            openTrigger.addActionListener(this::onFileOpenTriggered);
            openTrigger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
            openTrigger.setToolTipText("Opens file opening dialog and prompts you when a file is opened.");
        
            saveTrigger = new JMenuItem("Save");
            saveTrigger.addActionListener(this::onFileSaveTriggered);
            saveTrigger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
            openTrigger.setToolTipText("Opens file saving dialog and prompts you when a file is saved.");
        
            fileMenu = new JMenu("File");
            fileMenu.add(openTrigger);
            fileMenu.add(saveTrigger);
        
            menuBar = new JMenuBar();
            menuBar.add(fileMenu);
        }
        
        // Content panel setup
        {
            contentPanel = new JPanel();
        }
        
        // Windows setup
        {
            fileChooser = new JFileChooser();
            
            setJMenuBar(menuBar);
            setContentPane(contentPanel);
            setMinimumSize(new Dimension(450, 300));
            setSize(600, 400);
            setVisible(true);
        
            var frame = this;
            addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent we)
                {
                
                    int result = JOptionPane.showConfirmDialog(frame,
                            "Do you want to save before exiting?", "Exiting",
                            JOptionPane.YES_NO_CANCEL_OPTION);
    
                    switch(result){
                        case JOptionPane.YES_OPTION -> {
                            frame.onFileSaveTriggered(null);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        }
                        case JOptionPane.NO_OPTION -> frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        case JOptionPane.CANCEL_OPTION -> frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                }
            });
        }
    }
    
    private void onFileOpenTriggered(ActionEvent e)
    {
        var result = fileChooser.showOpenDialog(this);
        var file = fileChooser.getSelectedFile();
    
        if(file == null) return;
        if(result == JFileChooser.CANCEL_OPTION) return;
    
        System.out.println("Selected file: " + file.getPath());
        
        if(employeeListIO.trySave(file)){
            JOptionPane.showMessageDialog(this,"File opened.","File successfully opened.",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,"File not opened.","File has not been opened due to an error.",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onFileSaveTriggered(ActionEvent e)
    {
        var result = fileChooser.showSaveDialog(this);
        var file = fileChooser.getSelectedFile();
        
        if(file == null) return;
        if(result == JFileChooser.CANCEL_OPTION) return;
    
        System.out.println("Selected file: " + file.getPath());
    
        if(employeeListIO.trySave(file)){
            JOptionPane.showMessageDialog(this,"File saved.","File successfully saved.",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,"File not saved.","File has not been saved due to an error.",JOptionPane.ERROR_MESSAGE);
        }
    }
}