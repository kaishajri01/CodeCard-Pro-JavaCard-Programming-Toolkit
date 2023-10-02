package bank;

import bank.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
//public static String toHexString(int decimal)
public class MainWindow extends JFrame implements ActionListener{
    JLabel titre;

    int n;
    JLabel description;
    JPasswordField mdp;
    JButton valide;
    JButton effacer;
    JButton reset;
    JButton b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,v1,v2,v3,v4,v5;
    public Process p;
    protected PrintWriter stdin;

    CardLayout card;
    public MainWindow() throws InterruptedException, IOException {

        super("ATM");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1366,768));

        n=0;






        titre=new JLabel("Bank Of Africa");
        titre.setFont(new Font("Modern No. 20", Font.BOLD, 100));


        description=new JLabel("Enter PIN-Code \n If PIN-Code is entered incorrectly,\n Press <CLEAR>.\n");
        description.setFont(new Font("Modern No. 20", Font.BOLD, 40));

        mdp=new JPasswordField("");
        mdp.setColumns(15);
        mdp.setPreferredSize(new Dimension(700,30));
        mdp.setFont(new Font("Modern No. 20", Font.BOLD, 100));

        valide=new JButton("Valider");
        valide.addActionListener(this);
        valide.setFont(new Font("Modern No. 20", Font.BOLD, 60));

        effacer=new JButton("Delete");
        effacer.addActionListener(this);
        effacer.setFont(new Font("Modern No. 20", Font.BOLD, 60));

        reset=new JButton("CLEAR");
        reset.addActionListener(this);
        reset.setFont(new Font("Modern No. 20", Font.BOLD, 60));

        v1= new JButton("");v2= new JButton("");v3= new JButton("");v4= new JButton("");v5= new JButton("");
        b0=new JButton("0"); b1=new JButton("1"); b2=new JButton("2");
        b0.setSize(20,30);
        b3=new JButton("3"); b4=new JButton("4"); b5=new JButton("5");
        b6=new JButton("6"); b7=new JButton("7"); b8=new JButton("8"); b9=new JButton("9");
        b0.addActionListener(this);b1.addActionListener(this);b2.addActionListener(this);b3.addActionListener(this);
        b4.addActionListener(this);b5.addActionListener(this);b6.addActionListener(this);b7.addActionListener(this);
        b8.addActionListener(this);b9.addActionListener(this);

        b0.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b1.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b2.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b3.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b4.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b5.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b6.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b7.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b8.setFont(new Font("Modern No. 20", Font.BOLD, 60));
        b9.setFont(new Font("Modern No. 20", Font.BOLD, 60));

        JPanel center=new JPanel();
        center.setLayout(new GridLayout(6,3));
        center.add(b7);center.add(b8);center.add(b9);
        center.add(b4);center.add(b5);center.add(b6);
        center.add(b1);center.add(b2);center.add(b3);
        center.add(v1);center.add(b0);center.add(v2);
        center.add(valide);center.add(v5);center.add(reset);
        center.add(v3);center.add(mdp);center.add(v4);

        this.setLayout(new BorderLayout());
        this.add(titre,BorderLayout.NORTH);
        this.add(center,BorderLayout.CENTER);
        this.add(description,BorderLayout.SOUTH);


        String[] command =
                {
                        "cmd",
                };
        p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        stdin = new PrintWriter(p.getOutputStream());
        stdin.println("apdutool");
        stdin.println("powerup;");
        stdin.println("0x00 0xA4 0x04 0x00 0x09 0xa0 0x00 0x00 0x00 0x62 0x03 0x01 0x08 0x01 0x7F;");
        stdin.println("0x80 0xB8 0x00 0x00 0xd 0xb 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x08 0x09 0x00 0x00 0x00 0x7F;");
        stdin.println("0x00 0xA4 0x04 0x00 0xb 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x08 0x09 0x00 0x00 0x7F;");
        // write any other commands you want here
        //stdin.close();
        //int returnCode = p.waitFor();
        //System.out.println("Return code = " + returnCode);






    }
    private void messageBox(String msg,int type,String tit)
    {
        JOptionPane optionPane = new JOptionPane(msg,type);
        JDialog dialog = optionPane.createDialog(tit);
        dialog.setAlwaysOnTop(true); // to show top of all other application
        dialog.setVisible(true); // to visible the dialog

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String rn = mdp.getText();
        if (n < 3) {
            if (rn.length() < 5) {
                if (e.getSource() == b0) {
                    mdp.setText(rn + b0.getText());
                    return;
                }
                if (e.getSource() == b1) {
                    mdp.setText(rn + b1.getText());
                    return;
                }
                if (e.getSource() == b2) {
                    mdp.setText(rn + b2.getText());
                    return;
                }
                if (e.getSource() == b3) {
                    mdp.setText(rn + b3.getText());
                    return;
                }
                if (e.getSource() == b4) {
                    mdp.setText(rn + b4.getText());
                    return;
                }
                if (e.getSource() == b5) {
                    mdp.setText(rn + b5.getText());
                    return;
                }
                if (e.getSource() == b6) {
                    mdp.setText(rn + b6.getText());
                    return;
                }
                if (e.getSource() == b7) {
                    mdp.setText(rn + b7.getText());
                    return;
                }
                if (e.getSource() == b8) {
                    mdp.setText(rn + b8.getText());
                    return;
                }
                if (e.getSource() == b9) {
                    mdp.setText(rn + b9.getText());
                    return;
                }
            }
            if (e.getSource() == reset) {
                mdp.setText("");
                return;
            }
            if (e.getSource() == valide) {
                if (rn.length() != 5) {
                    messageBox("Invalid PIN(5 digits)", JOptionPane.WARNING_MESSAGE, "Warning");
                    return;
                }
                if (!rn.equals("01234")) {
                    stdin.println("0x80 0x20 0x00 0x00 0x05 0x00 0x02 0x02 0x03 0x04 0x7F;");
                    messageBox("PIN INCORRECT", JOptionPane.WARNING_MESSAGE, "Warning");
                    n=n+1;
                    return;
                } else {
                    stdin.println("0x80 0x20 0x00 0x00 0x05 0x00 0x01 0x02 0x03 0x04 0x7F;");
                    stdin.println("powerdown;");
                    stdin.close();
                    int returnCode = 0;
                    try {
                        returnCode = p.waitFor();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("Return code = " + returnCode);

                    try {
                        Actions a = new Actions();
                        this.setVisible(false);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }


        }
        else {

            messageBox("CARD BLOCKED", JOptionPane.WARNING_MESSAGE, "Warning");
            stdin.println("powerdown;");
            stdin.close();
            int returnCode = 0;
            try {
                returnCode = p.waitFor();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Return code = " + returnCode);
            return;
        }
    }

                    }





