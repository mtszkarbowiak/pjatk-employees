package s23603.employees.inteface;

import s23603.employees.EmployeeListLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main class creating and controlling user interface.

public class UserInterface extends JFrame implements EmployeeListChangeListener, EmployeeListSelectionChanger
{
    public static final String[] ATTRIBUTE_NAMES = {"Name", "Surname", "Position", "Salary", "Experience"};
    
    private final JMenuBar menuBar;
    private final JSplitPane contentPanel;
    
    private final JMenu fileMenu;
    private final JMenuItem openTrigger;
    private final JMenuItem saveTrigger;
    private final JMenu actionsMenu;
    private final JMenuItem clearTrigger;
    private final JMenuItem clearSelectionTrigger;
    private final JFileChooser fileChooser;
    
    private final JScrollPane tableScrollPane;
    private final JTable table;
    
    private final JTabbedPane tabs;
    private final SortingAndFilteringPanel sortingAndFilteringPanel;
    private final EmployeeListLogic employeeListLogic;
    private InspectorPanel inspectorPanel;
    
    
    public UserInterface(EmployeeListLogic employeeListLogic)
    {
        super("Employees");
        
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
    
            clearTrigger = new JMenuItem("Clear workspace");
            clearTrigger.addActionListener(this::onClearWorkspaceTriggered);
            clearTrigger.setToolTipText("Removes all loaded records.");
    
            clearSelectionTrigger = new JMenuItem("Clear selection");
            clearSelectionTrigger.addActionListener(this::onClearSelectionTriggered);
            clearSelectionTrigger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
            clearSelectionTrigger.setToolTipText("Clears selection.");
            
            fileMenu = new JMenu("File");
            fileMenu.add(openTrigger);
            fileMenu.add(saveTrigger);
    
            actionsMenu = new JMenu("Actions");
            actionsMenu.add(clearSelectionTrigger);
            actionsMenu.add(clearTrigger);
            
            menuBar = new JMenuBar();
            menuBar.add(fileMenu);
            menuBar.add(actionsMenu);
        }
        
        // Content panel setup
        {
            {
                table = new JTable();
                table.setModel(new ExplorerTableModel(employeeListLogic));
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                table.getSelectionModel().addListSelectionListener(e ->
                        {
                            if(table.getSelectedRow() < 0 || table.getSelectedRow() >= employeeListLogic.size()) return;
                            
                            inspectorPanel.inspect(employeeListLogic.get(table.getSelectedRow()));
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
            contentPanel.setDividerLocation(500);
        }
        
        // Windows setup
        {
            fileChooser = new JFileChooser();
            
            setJMenuBar(menuBar);
            setContentPane(contentPanel);
            setMinimumSize(new Dimension(450, 300));
            setSize(800, 500);
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
        var fileDialogResult = fileChooser.showOpenDialog(this);
        var file = fileChooser.getSelectedFile();
        
        if(file == null) return;
        if(fileDialogResult == JFileChooser.CANCEL_OPTION) return;
        
        System.out.println("Opening file: " + file.getPath());
        
        var ioResult = employeeListLogic.tryOpen(file);
        
        if(ioResult.isNotTerminatedByException() && !ioResult.hasSkippedLines())
        {
            JOptionPane.showMessageDialog(this, "File successfully opened.",
                    "File opened.", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(ioResult.isNotTerminatedByException() && ioResult.hasSkippedLines())
        {
            JOptionPane.showMessageDialog(this,
                    "File successfully opened, but some lines (" + ioResult.getSkippedLines().size()+
                            ") have been skipped.", "File opened with problems.", JOptionPane.WARNING_MESSAGE);
    
            System.out.println("--- Skipped Lines ---");
            for(var line : ioResult.getSkippedLines()){
                System.out.print(line + ", ");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                    "File has not been opened due to an error.", "File not opened.", JOptionPane.ERROR_MESSAGE);
        }
    
        onEmployeeListChanged();
        inspectorPanel.inspect(null);
    }
    
    private void onFileSaveTriggered(ActionEvent e)
    {
        var fileDialogResult = fileChooser.showSaveDialog(this);
        var file = fileChooser.getSelectedFile();
        
        if(file == null) return;
        if(fileDialogResult == JFileChooser.CANCEL_OPTION) return;
        
        System.out.println("Saving file: " + file.getPath());
        
        var ioResult = employeeListLogic.trySave(file);
        
        if(ioResult.isNotTerminatedByException())
        {
            JOptionPane.showMessageDialog(this, "File successfully saved.", "File saved.", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "File has not been saved due to an error.", "File not saved.", JOptionPane.ERROR_MESSAGE);
        }
    
        onEmployeeListChanged();
    }
    
    private void onClearWorkspaceTriggered(ActionEvent e)
    {
        employeeListLogic.clear();
        
        onEmployeeListChanged();
        inspectorPanel.inspect(null);
    }
    
    private void onClearSelectionTriggered(ActionEvent e)
    {
        onEmployeeListDeselectRequested();
        onEmployeeListChanged();
        inspectorPanel.inspect(null);
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

