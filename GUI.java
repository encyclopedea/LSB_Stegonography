import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;


public class GUI extends JFrame implements ActionListener{
    private JTextArea fileName,text;
    private JLabel fileLabel,textLabel;
    private JButton load,displayOrig,displayEdited,editPic,ography,alysis,back,save,analyze;
    //private JButton displayPix;
    private Pic picture;
    private Message msg;
    
    public GUI(){
        super("Stegonography");
        setupGUI();
        
    }
    
    private void setupGUI(){
        //setting section
        ography=new JButton("Stegonography");
        ography.addActionListener(this);
        ography.setActionCommand("ography");
        
        alysis=new JButton("Stegonalysis");
        alysis.addActionListener(this);
        alysis.setActionCommand("alysis");
        
        //box section
        Box buttonBox=Box.createVerticalBox();
        buttonBox.add(Box.createVerticalStrut(100));
        buttonBox.add(ography);
        buttonBox.add(Box.createVerticalStrut(10));
        buttonBox.add(alysis);
        
        
        //adding section
        Container c=getContentPane();
        c.setLayout(new FlowLayout());
        c.add(buttonBox);
    }
    
    private void setupDecodingGUI(){
        load=new JButton("Load Picture");
        load.addActionListener(this);
        load.setActionCommand("Loada");
        
        displayOrig=new JButton("Display Picture");
        displayOrig.addActionListener(this);
        displayOrig.setActionCommand("DisplayOrig");
        
        analyze=new JButton("Analyze");
        analyze.addActionListener(this);
        analyze.setActionCommand("analyze");
        
        back=new JButton("Back");
        back.addActionListener(this);
        back.setActionCommand("back");
        
        fileLabel=new JLabel("File Name: ");
              
        fileName=new JTextArea(1,7);
        fileName.setLineWrap(true);
        fileName.setWrapStyleWord(true);
        JScrollPane filePane=new JScrollPane(fileName,
              ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
              
        
        //BOX TIME
        Box fileBoxInner=Box.createHorizontalBox();
        fileBoxInner.add(fileLabel);
        fileBoxInner.add(Box.createHorizontalStrut(10));
        fileBoxInner.add(filePane);
        
        Box fileBox=Box.createVerticalBox();
        fileBox.add(fileBoxInner);
        fileBox.add(Box.createVerticalStrut(10));
        fileBox.add(load);
        
        Box pictureBox=Box.createHorizontalBox();
        pictureBox.add(displayOrig);
        pictureBox.add(Box.createVerticalStrut(10));
        pictureBox.add(analyze);
        
        Box displayBox=Box.createVerticalBox();
        displayBox.add(fileBox);
        //displayBox.add(Box.createVerticalStrut(10));
        //displayBox.add(messageBox);
        displayBox.add(Box.createVerticalStrut(10));
        displayBox.add(pictureBox);
        displayBox.add(Box.createVerticalStrut(10));
        displayBox.add(back);
              
        
        Container c=getContentPane();
        c.setLayout(new FlowLayout());
        c.add(displayBox);
    }
    
    private void setupEncodingGUI(){
        //setting variables
        load=new JButton("Load Picture");
        load.addActionListener(this);
        load.setActionCommand("Loadg");
        
        displayOrig=new JButton("Display Original Picture");
        displayOrig.addActionListener(this);
        displayOrig.setActionCommand("DisplayOrig");
        
        displayEdited=new JButton("Display Edited Picture");
        displayEdited.addActionListener(this);
        displayEdited.setActionCommand("DisplayEdited");
        
        editPic=new JButton("STEGO!!!");
        editPic.addActionListener(this);
        editPic.setActionCommand("stego");
        
        back=new JButton("Back");
        back.addActionListener(this);
        back.setActionCommand("back");

        save=new JButton("Save");
        save.addActionListener(this);
        save.setActionCommand("save");
        
        fileLabel=new JLabel("File Name: ");
        textLabel=new JLabel("Message: ");
        
        fileName=new JTextArea(1,5);
        fileName.setLineWrap(true);
        fileName.setWrapStyleWord(true);
        JScrollPane filePane=new JScrollPane(fileName,
              ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        
        text=new JTextArea(10,15);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane textPane=new JScrollPane(text,
              ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
              ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        text.setText("");
        
        //setting up boxes
        Box fileBoxInner=Box.createHorizontalBox();
        fileBoxInner.add(fileLabel);
        fileBoxInner.add(Box.createHorizontalStrut(10));
        fileBoxInner.add(filePane);
        
        Box fileBox=Box.createVerticalBox();
        fileBox.add(fileBoxInner);
        fileBox.add(Box.createVerticalStrut(10));
        fileBox.add(load);
        
        
        Box stegoBox=Box.createVerticalBox();
        stegoBox.add(textPane);
        stegoBox.add(Box.createVerticalStrut(10));
        stegoBox.add(textPane);
        stegoBox.add(editPic);
        
        
        Box displayBox=Box.createHorizontalBox();
        displayBox.add(displayOrig);
        displayBox.add(Box.createHorizontalStrut(5));
        displayBox.add(displayEdited);
        
        
        Box container=Box.createVerticalBox();
        container.add(fileBox);
        container.add(Box.createVerticalStrut(10));
        container.add(stegoBox);
        container.add(Box.createVerticalStrut(10));
        container.add(displayBox);
        container.add(Box.createVerticalStrut(10));
        container.add(save);
        container.add(back);
        
        //adding boxes to the content pane
        Container c=getContentPane();
        c.setLayout(new FlowLayout());
        c.add(container);
    }
    
    public void actionPerformed(ActionEvent e){
        String cmd=e.getActionCommand();
        
        if(cmd.equals("ography")){
            Container c=getContentPane();
            c.removeAll();
            c.revalidate();
            c.repaint();
            setupEncodingGUI();
        }
        
        if(cmd.equals("alysis")){
            Container c=getContentPane();
            c.removeAll();
            c.revalidate();
            c.repaint();
            setupDecodingGUI();
        }
        
        
        if(cmd.equals("back")){
            Container c=getContentPane();
            c.removeAll();
            c.revalidate();
            c.repaint();
            setupGUI();
        }
        
        
        if(cmd.equals("Loadg")){
            try{
                picture=new Pic(fileName.getText(),true);
            }
            catch(NullPointerException exeption){
                picture=null;
                JOptionPane.showMessageDialog(null,"Picture not found.");
            }

        }
        
        if(cmd.equals("Loada")){
            try{
                picture=new Pic(fileName.getText(),false);
            }
            catch(NullPointerException exeption){
                picture=null;
                JOptionPane.showMessageDialog(null,"Picture not found.");
            }
        }
        
        if(cmd.equals("DisplayOrig")){
            if(picture==null){
                JOptionPane.showMessageDialog(null,"Please load a picture.");
                return;
            }
            
            Graphics g=picture.getOrig().createGraphics();
            ImageIcon icon = new ImageIcon();
            icon.setImage(picture.getOrig());
            Container c=getContentPane();
            JOptionPane.showMessageDialog(null, icon);
        }
        
        if(cmd.equals("DisplayEdited")){
            if(picture==null){
                JOptionPane.showMessageDialog(null,"Please load a picture.");
                return;
            }
            
            Graphics g=picture.getOrig().createGraphics();
            ImageIcon icon = new ImageIcon();
            icon.setImage(picture.getEdited());
            Container c=getContentPane();
            JOptionPane.showMessageDialog(null, icon);
        }
        
        if(cmd.equals("stego")){
            if(picture==null){
                JOptionPane.showMessageDialog(null,"Please load a picture.");
                return;
            }
            
            if(text.getText().length()>picture.getMaxLength()){
                JOptionPane.showMessageDialog(null, "Please shorten your message. It is currently "+
                                                     (text.getText().length()-picture.getMaxLength())+
                                                     " characters too long.");
                return;
            }
            
            msg=new Message(text.getText());
            Stego.textIntoPic(picture,msg);
        }
        
        if(cmd.equals("analyze")){
            if(picture==null){
                JOptionPane.showMessageDialog(null,"Please load a picture.");
                return;
            }
            
            msg=Stego.picIntoText(picture);
            Integer[] f=msg.getNumArray();
            //text.setText(msg.getMessage());
            JOptionPane.showMessageDialog(null,"Message:\n"+msg.getMessage());
        }
        
        if(cmd.equals("save")){
            if(picture==null){
                JOptionPane.showMessageDialog(null,"Please load a picture.");
                return;
            }
            
            String s=JOptionPane.showInputDialog("Please input the name to save this under. The picture will be saved as a PNG file.");
            picture.saveEdited(s);
        }
    }
    
    
    
    
    public static void main(String[] args){
        GUI window = new GUI();
        window.setBounds(100, 100, 600, 400);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
