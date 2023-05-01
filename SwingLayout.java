import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingLayout {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private JLabel msglabel;

   public SwingLayout(){
      prepareGUI();
   }
   public static void main(String[] args){
      SwingLayout SwingLayout = new SwingLayout();  
      SwingLayout.showBorderLayout();       
   }
   private void prepareGUI(){
      mainFrame = new JFrame("Patient Management System");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));

      headerLabel = new JLabel("",JLabel.CENTER );
      statusLabel = new JLabel("",JLabel.CENTER);        
      statusLabel.setSize(350,100);
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }
   private void showBorderLayout(){  

      JPanel panel = new JPanel();
      panel.setBackground(Color.darkGray);
      panel.setSize(300,300);
      BorderLayout layout = new BorderLayout();
      layout.setHgap(10);
      layout.setVgap(10);
      
      panel.setLayout(layout);        
      panel.add(new JButton("Line Start"),BorderLayout.LINE_START); 
      panel.add(new JButton("Line End"),BorderLayout.LINE_END);
      panel.add(new JButton("East"),BorderLayout.EAST);   
      panel.add(new JButton("West"),BorderLayout.WEST); 
      panel.add(new JButton("North"),BorderLayout.NORTH); 
      panel.add(new JButton("South"),BorderLayout.SOUTH); 

      controlPanel.add(panel);
      mainFrame.setVisible(true);
   }
}