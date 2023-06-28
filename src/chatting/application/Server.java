package chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
    
    JButton send;
    JTextField text;
    JPanel p2;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server(){
        
        f.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,470,70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(10,20,25,25);
        
        back.addMouseListener(new MouseAdapter(){
             @Override
             public void mouseClicked(MouseEvent ae){
                 //setVisible(false);
                 System.exit(0);
             }
        });
        p1.add(back);
        
        // Profile
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(50,10,50,50);
        p1.add(profile);
        
        // Video Call
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        // Telephone
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);
        
        // Three dots
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel dots = new JLabel(i15);
        dots.setBounds(420,20,10,25);
        p1.add(dots);
        
        // User Name
        JLabel name = new JLabel("Gaitonde");
        name.setBounds(130,15,100,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF" , Font.BOLD , 18));
        p1.add(name);
        
        // Active Now
        JLabel status = new JLabel("Active Now");
        status.setBounds(135,35,100,30);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN SERIF" , Font.BOLD , 12));
        p1.add(status);
        
        p2 = new JPanel();
        p2.setBounds(8,75,450,580);
        f.add(p2);
        
        text = new JTextField();
        text.setBounds(8,660,320,40);
        text.setFont(new Font("SAN SERIF" , Font.BOLD , 16));
        f.add(text);
        
        send = new JButton("Send");
        send.setBounds(335,660,123,38);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.setFont(new Font("SAN SERIF" , Font.BOLD , 16));
        send.addActionListener(this);
        f.add(send);
        
        
        f.setSize(470,705);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        
        
        f.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        try{
            
        
        String out = text.getText();
        
        JPanel p3 = formatLabel(out);
       
        
        p2.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p3 , BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        p2.add(vertical , BorderLayout.PAGE_START);
        
        dout.writeUTF(out);
        
        text.setText("");
        
        f.repaint();
        f.invalidate();
        f.validate();
        
        }
        catch(Exception ae){
            ae.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style = \"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN , 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String [] args){
       new Server();
       
       try{
           ServerSocket skt = new ServerSocket(6001);
           
           while(true){
               Socket s = skt.accept();
               DataInputStream din = new DataInputStream(s.getInputStream());
               dout = new DataOutputStream(s.getOutputStream());
               
               while(true){
                   String msg = din.readUTF();
                   JPanel panel = formatLabel(msg);
                   
                   JPanel left =new JPanel(new BorderLayout());
                   left.add(panel , BorderLayout.LINE_START);
                   vertical.add(left);
                   
                   f.validate();
                   
               }
           }
       }
       catch(Exception e){
           e.printStackTrace();
       }
    }
}
