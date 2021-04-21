package s23603.employees.inteface;

import s23603.employees.EmployeeListLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserInterface extends JFrame implements EmployeeListChangeListener, EmployeeListSelectionChanger
{
    public static final String[] ATTRIBUTE_NAMES = {"Name", "Surname", "Position", "Salary", "Experience"};
    
    private final JMenuBar menuBar;
    private final JSplitPane contentPanel;
    
    private final JMenu fileMenu;
    private final JMenuItem openTrigger;
    private final JMenuItem saveTrigger;
    private final JFileChooser fileChooser;
    
    private final JScrollPane tableScrollPane;
    private final JTable table;
    
    private final JTabbedPane tabs;
    private final SortingAndFilteringPanel sortingAndFilteringPanel;
    private final EmployeeListLogic employeeListLogic;
    private InspectorPanel inspectorPanel;
    
    
    public UserInterface(EmployeeListLogic employeeListLogic)
    {
        super("Employees Data List");
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if(info.getName().equals("Nimbus")){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(Exception ignored){
        }
        
        this.employeeListLogic = employeeListLogic;
        
        // Menu bar setup
        {
            openTrigger = new JMenuItem("Open...");
            openTrigger.addActionListener(this::onFileOpenTriggered);
            openTrigger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
            openTrigger.setToolTipText("Opens file opening dialog and prompts you when a file is opened.");
            
            saveTrigger = new JMenuItem("Save...");
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
            {
                table = new JTable();
                table.setModel(new ExplorerTableModel(employeeListLogic));
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                table.getSelectionModel().addListSelectionListener(e ->
                        {
                            if(table.getSelectedRow() < 0 || table.getSelectedRow() >= employeeListLogic.sizeFiltered()) return;
                            
                            inspectorPanel.inspect(employeeListLogic.getFromFiltered(table.getSelectedRow()));
                        }
                );
                
                tableScrollPane = new JScrollPane(table);
            }
            
            {
                inspectorPanel = new InspectorPanel(employeeListLogic, this, this);
                sortingAndFilteringPanel = new SortingAndFilteringPanel(employeeListLogic, this);
            }
            
            tabs = new JTabbedPane();
            tabs.addTab("Inspector", inspectorPanel);
            tabs.addTab("Sorting / Filtering", sortingAndFilteringPanel);
            
            contentPanel = new JSplitPane();
            contentPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            contentPanel.setLeftComponent(tableScrollPane);
            contentPanel.setRightComponent(tabs);
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
        
        inspectorPanel.inspect(null);
    }
    
    
    private void onFileOpenTriggered(ActionEvent e)
    {
        var result = fileChooser.showOpenDialog(this);
        var file = fileChooser.getSelectedFile();
        
        if(file == null) return;
        if(result == JFileChooser.CANCEL_OPTION) return;
        
        System.out.println("Selected file: " + file.getPath());
        
        if(employeeListLogic.tryOpen(file)){
            JOptionPane.showMessageDialog(this, "File opened.", "File successfully opened.", JOptionPane.INFORMATION_MESSAGE);
        } else{
            JOptionPane.showMessageDialog(this, "File not opened.", "File has not been opened due to an error.", JOptionPane.ERROR_MESSAGE);
        }
        
        table.updateUI();
        inspectorPanel.inspect(null);
    }
    
    private void onFileSaveTriggered(ActionEvent e)
    {
        var result = fileChooser.showSaveDialog(this);
        var file = fileChooser.getSelectedFile();
        
        if(file == null) return;
        if(result == JFileChooser.CANCEL_OPTION) return;
        
        System.out.println("Selected file: " + file.getPath());
        
        if(employeeListLogic.trySave(file)){
            JOptionPane.showMessageDialog(this, "File saved.", "File successfully saved.", JOptionPane.INFORMATION_MESSAGE);
        } else{
            JOptionPane.showMessageDialog(this, "File not saved.", "File has not been saved due to an error.", JOptionPane.ERROR_MESSAGE);
        }
        
        table.updateUI();
    }
    
    
    @Override
    public void onEmployeeListChanged()
    {
        table.updateUI();
    }
    
    @Override
    public void onEmployeeListDeselectRequested()
    {
        table.clearSelection();
    }
}

