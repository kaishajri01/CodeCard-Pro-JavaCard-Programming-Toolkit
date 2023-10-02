package bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Actions extends JFrame implements ActionListener {
    JLabel titre;
    int soldemon;

    int execcmd;

    JRadioButton credit,debit,solde;
    JLabel mont ;
    JButton b10,b30,b50,b100,b200,b300;

    ButtonGroup g;
    public Process p;

    PrintWriter stdin;

    String SoldeAPDU="0x80 0x50 0x00 0x00 0x00 0x7F;";

    String CreditAPDU10="0x80 0x30 0x00 0x00 0x01 0x0a 0x7F;";String CreditAPDU100="0x80 0x30 0x00 0x00 0x01 0x64 0x7F;";
    String CreditAPDU30="0x80 0x30 0x00 0x00 0x01 0x1E 0x7F;";String CreditAPDU200="0x80 0x30 0x00 0x00 0x01 0xc8 0x7F;";
    String CreditAPDU50="0x80 0x30 0x00 0x00 0x01 0x32 0x7F;";String CreditAPDU300="0x80 0x30 0x00 0x00 0x01 0x12c 0x7F;";
    String DebitAPDU10="0x80 0x40 0x00 0x00 0x01 0x0a 0x7F;";String DebitAPDU100="0x80 0x40 0x00 0x00 0x01 0x64 0x7F;";
    String DebitAPDU30="0x80 0x40 0x00 0x00 0x01 0x1e 0x7F;";String DebitAPDU200="0x80 0x40 0x00 0x00 0x01 0xc8 0x7F;";
    String DebitAPDU50="0x80 0x40 0x00 0x00 0x01 0x32 0x7F;";String DebitAPDU300="0x80 0x40 0x00 0x00 0x01 0x12c 0x7F;";

    String esp="                                     ";

    public Actions() throws InterruptedException, IOException {
        super("Transactions");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1366,768));

        titre=new JLabel("Transactions");
        titre.setFont(new Font("Modern No. 20", Font.BOLD, 100));

        execcmd=0;
        soldemon=0;


        g=new ButtonGroup();

        credit = new JRadioButton("Crediter");
        credit.setFont(new Font("Modern No. 20", Font.BOLD, 70));
        debit=new JRadioButton("Debiter");
        debit.setFont(new Font("Modern No. 20", Font.BOLD, 70));
        solde=new JRadioButton("Show Solde");
        solde.setFont(new Font("Modern No. 20", Font.BOLD, 70));
        solde.addActionListener(this);

        b10=new JButton("10");b10.setFont(new Font("Modern No. 20", Font.BOLD, 60));b10.addActionListener(this);
        b30=new JButton("30");b30.setFont(new Font("Modern No. 20", Font.BOLD, 60));b30.addActionListener(this);
        b50=new JButton("50");b50.setFont(new Font("Modern No. 20", Font.BOLD, 60));b50.addActionListener(this);
        b100=new JButton("100");b100.setFont(new Font("Modern No. 20", Font.BOLD, 60));b100.addActionListener(this);
        b200=new JButton("200");b200.setFont(new Font("Modern No. 20", Font.BOLD, 60));b200.addActionListener(this);
        b300=new JButton("300");b300.setFont(new Font("Modern No. 20", Font.BOLD, 60));b300.addActionListener(this);

        JPanel east=new JPanel();
        east.setLayout(new GridLayout(3,1));
        east.add(b10);east.add(b30);east.add(b50);

        JPanel ouest=new JPanel();
        ouest.setLayout(new GridLayout(3,1));
        ouest.add(b100);ouest.add(b200);ouest.add(b300);

        g.add(credit);g.add(debit);g.add(solde);

        mont=new JLabel(esp+"$");
        mont.setFont(new Font("Modern No. 20", Font.BOLD, 60));


        JPanel center=new JPanel();
        center.setLayout(new GridLayout(1,3));
        center.add(credit);center.add(debit);center.add(solde);

        this.setLayout(new BorderLayout());
        this.add(titre,BorderLayout.NORTH);
        this.add(center,BorderLayout.CENTER);
        this.add(mont,BorderLayout.SOUTH);

        this.add(east,BorderLayout.EAST);
        this.add(ouest,BorderLayout.WEST);



        /*
        try {
            // Construct data
            String apiKey = "apikey=" + "NzI3Mjc1Njc1NzQ1MzE3YTM2NzY3MDczNTc1MDMzNGE";
            String message = "&message=" + "Connection to your bank account !";
            //String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "+21653330321";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            System.out.println(stringBuffer.toString());
            rd.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        */




    }

    private void messageBox(String msg,int type,String tit)
    {
        JOptionPane optionPane = new JOptionPane(msg,type);
        JDialog dialog = optionPane.createDialog(tit);
        dialog.setAlwaysOnTop(true); // to show top of all other application
        dialog.setVisible(true); // to visible the dialog

    }
    public void executeCMD()
    {
        String[] command =
                {
                        "cmd",
                };
        try {
            p = Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        stdin = new PrintWriter(p.getOutputStream());
        stdin.println("apdutool");
        stdin.println("powerup;");
        stdin.println("0x00 0xA4 0x04 0x00 0x09 0xa0 0x00 0x00 0x00 0x62 0x03 0x01 0x08 0x01 0x7F;");
        stdin.println("0x80 0xB8 0x00 0x00 0xd 0xb 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x08 0x09 0x00 0x00 0x00 0x7F;");
        stdin.println("0x00 0xA4 0x04 0x00 0xb 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x08 0x09 0x00 0x00 0x7F;");
        stdin.println("0x80 0x20 0x00 0x00 0x05 0x00 0x01 0x02 0x03 0x04 0x7F;");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b10) {
            if(credit.isSelected())
            {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(CreditAPDU10);
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    soldemon+=10;
                    execcmd=1;
                }
                else{
                    if(soldemon+10>30000)
                    {
                        messageBox("EXCEED_MAXIMUM_BALANCE", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(CreditAPDU10);
                        return;
                    }
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    stdin.println(CreditAPDU10);
                    soldemon+=10;
                    return;
                }

            }
            else if (debit.isSelected())
            {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(DebitAPDU10);
                    messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                    execcmd=1;
                }
                else{
                    if(soldemon-10<0)
                    {
                        messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(DebitAPDU10);
                        return;
                    }
                    stdin.println(DebitAPDU10);
                    soldemon-=10;
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");

                }

            }
            else{
                messageBox("Invalid Selection", JOptionPane.WARNING_MESSAGE,"Warning");
                return;
            }
        }
        if (e.getSource() == b30) {
            if (credit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(CreditAPDU30);
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    soldemon+=30;
                    execcmd=1;
                }
                else{
                    if(soldemon+30>30000)
                    {
                        messageBox("EXCEED_MAXIMUM_BALANCE", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(CreditAPDU30);
                        return;
                    }
                    stdin.println(CreditAPDU30);
                    soldemon+=30;
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");

                }

            }
            else if (debit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(DebitAPDU10);
                    messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                    execcmd=1;
                }
                else{
                    if(soldemon-30<0)
                    {
                        messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(DebitAPDU30);
                        return;
                    }
                    stdin.println(DebitAPDU30);
                    soldemon-=30;
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");

                }

            } else {
                messageBox("Invalid Selection", JOptionPane.WARNING_MESSAGE, "Warning");
                return;
            }
        }
        if (e.getSource() == b50) {
            if (credit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(CreditAPDU50);
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    soldemon+=50;
                    execcmd=1;
                }
                else{
                    if(soldemon+50>30000)
                    {
                        messageBox("EXCEED_MAXIMUM_BALANCE", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(CreditAPDU50);
                        return;
                    }
                    stdin.println(CreditAPDU50);
                    soldemon+=50;
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");

                }


            } else if (debit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(DebitAPDU10);
                    messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                    execcmd=1;
                }
                else{
                    if(soldemon-50<0)
                    {
                        messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(DebitAPDU50);
                        return;
                    }
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    stdin.println(DebitAPDU50);
                    soldemon-=50;
                }

            } else {
                messageBox("Invalid Selection", JOptionPane.WARNING_MESSAGE, "Warning");
                return;
            }
        }
        if (e.getSource() == b100) {
            if (credit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(CreditAPDU100);
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    soldemon+=100;
                    execcmd=1;
                }
                else{
                    if(soldemon+100>30000)
                    {
                        messageBox("EXCEED_MAXIMUM_BALANCE", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(CreditAPDU100);
                        return;
                    }
                    stdin.println(CreditAPDU100);
                    soldemon+=100;
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                }
            } else if (debit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(DebitAPDU10);
                    messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                    execcmd=1;
                }
                else{
                    if(soldemon-100<0)
                    {
                        messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(DebitAPDU100);
                        return;
                    }
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    stdin.println(DebitAPDU100);
                    soldemon-=100;
                }

            } else {
                messageBox("Invalid Selection", JOptionPane.WARNING_MESSAGE, "Warning");
                return;
            }
        }
        if (e.getSource() == b200) {
            if (credit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(CreditAPDU200);
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    soldemon+=200;
                    execcmd=1;
                }
                else{
                    if(soldemon+200>30000)
                    {
                        messageBox("EXCEED_MAXIMUM_BALANCE", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(CreditAPDU200);
                        return;
                    }
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    stdin.println(CreditAPDU200);
                    soldemon+=200;
                }

            } else if (debit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(DebitAPDU10);
                    messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                    execcmd=1;
                }
                else{
                    if(soldemon-200<0)
                    {
                        messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(DebitAPDU200);
                        return;
                    }
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    stdin.println(DebitAPDU200);
                    soldemon-=200;
                }

            } else {
                messageBox("Invalid Selection", JOptionPane.WARNING_MESSAGE, "Warning");
                return;
            }
        }
        if (e.getSource() == b300) {
            if (credit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(CreditAPDU300);
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    soldemon+=300;
                    execcmd=1;
                }
                else{
                    if(soldemon+300>30000)
                    {
                        messageBox("EXCEED_MAXIMUM_BALANCE", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(CreditAPDU300);
                        return;
                    }
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    stdin.println(CreditAPDU300);
                    soldemon+=300;
                }

            } else if (debit.isSelected()) {
                if(execcmd==0)
                {
                    executeCMD();
                    stdin.println(DebitAPDU10);
                    messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                    execcmd=1;
                }
                else{
                    if(soldemon-300<0)
                    {
                        messageBox("Negative Balance", JOptionPane.WARNING_MESSAGE, "Warning");
                        stdin.println(DebitAPDU300);
                        return;
                    }
                    messageBox("Operation succeeded", JOptionPane.INFORMATION_MESSAGE, "Succeeded");
                    stdin.println(DebitAPDU300);
                    soldemon-=300;
                }

            } else {
                messageBox("Invalid Selection", JOptionPane.WARNING_MESSAGE, "Warning");
                return;
            }
        }
        if (e.getSource()==solde)
        {
            if(execcmd==0)
            {
                executeCMD();
                stdin.println(SoldeAPDU);
                mont.setText(esp+String.valueOf(soldemon)+"$");
                execcmd=1;
            }
            else{
                stdin.println(SoldeAPDU);
                mont.setText(esp+String.valueOf(soldemon)+"$");
            }
            stdin.close();
            int returnCode = 0;
            try {
                returnCode = p.waitFor();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Return code = " + returnCode);


        }


    }
}
