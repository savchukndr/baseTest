package com.company.gui;

import com.company.database_handler.Connector;
import com.company.databases.*;
import interfaces.DataBaseInterface;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by savch on 04.03.2017.
 */
public class MainFrame extends JFrame implements DataBaseInterface{

    private aTask t;
    private JButton buttonCount, buttonCencel;
    private JProgressBar progressBar;
    private JLabel labelDownload;
    private JTextArea textArea;
    private JRadioButton radioButtonPG, radioButtonRD, radioButtonRDPG;
    private ButtonGroup radioButtonGroup;
    private String choice;
    private int k = 1, amountOfRaws = 100;
    private pgsqlDB obTestPGDB = new pgsqlDB();
    private double time4, time5, time6, time9, time10, time11;
    private Connection connPG = null;
    private Statement stmt = null;

    public MainFrame(){
        try {
            initialize();
            pgsqlDB obTestPGDB = new pgsqlDB();
        }catch (InterruptedException e){
            System.out.print("Exception: " + e);
        }
    }

    private void initialize() throws InterruptedException {
        //JFrame frame = new JFrame("BaseTest program");

        //Frame
        setTitle("Base Test");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //Panels
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new GridBagLayout());
        JPanel panelTop = new JPanel();
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new GridBagLayout());

        //Introduce Label
        JLabel welcomeLabel = new JLabel("Please, choose option and start count:");

        //ProgreaaBar Label
        labelDownload = new JLabel();

        //Progress Bar
        progressBar = new JProgressBar();
        //progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(amountOfRaws);

        //Radio Buttons
        radioButtonPG = new JRadioButton("PostgreSQL");
        radioButtonPG.setActionCommand("PG");
        radioButtonRD = new JRadioButton("Redis");
        radioButtonRD.setActionCommand("RD");
        radioButtonRDPG = new JRadioButton("Both");
        radioButtonRDPG.setActionCommand("RDPG");
        choice = "RDPG";
        radioButtonRDPG.setSelected(true);

        //Start button inicialization
        buttonCount = new JButton("Start");
        buttonCount.addActionListener(this::startActionPerformed);

        //Cencel button inicialization
        buttonCencel = new JButton("Cencel");
        buttonCencel.addActionListener(this::cencelActionPerformed);

        //Text area
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

        //adding scroll to main Text Area
        JScrollPane scroll = new JScrollPane (textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


        panelTop.add(welcomeLabel);

        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(radioButtonPG);
        radioButtonGroup.add(radioButtonRD);
        radioButtonGroup.add(radioButtonRDPG);

        RadioButtonActionListener actionListener = new RadioButtonActionListener();
        radioButtonPG.addActionListener(actionListener);
        radioButtonRD.addActionListener(actionListener);
        radioButtonRDPG.addActionListener(actionListener);

        //radiobutton for PostgreSQL
        panelLeft.add(radioButtonPG, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(4,2,2,2), 0, 0));
        //radiobutton for Radis
        panelLeft.add(radioButtonRD, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(4,2,2,2), 0, 0));
        //radiobutton for both
        panelLeft.add(radioButtonRDPG, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(4,2,2,2), 0, 0));
        //buttonCount added to panelLeft
        panelLeft.add(buttonCount, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(100,2,2,2), 2, 2));
        panelLeft.add(buttonCencel, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(100,2,2,2), 2, 2));



        add(panelLeft, BorderLayout.WEST);

        //adding scroll with TextArea to panelRight
        panelRight.add(scroll, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        //adding progressBar to panelRight
        panelRight.add(progressBar, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        //adding label to panelRight
        panelRight.add(labelDownload, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(panelRight, BorderLayout.CENTER);
        labelDownload.setText("Press \"Start\" to start downloadnig");
        //progressBar.setValue(0);

        add(panelTop, BorderLayout.NORTH);
        setVisible(true);
        pack();

        //ResultWindow
        //ResultFrame resFrame = new ResultFrame();
        //resFrame.show();
        //////////////////////////

    }

    private void startActionPerformed(ActionEvent e){
        (t = new aTask()).execute();
        buttonCount.setEnabled(false);
        buttonCencel.setEnabled(true);
        k = 1;
    }

    private void cencelActionPerformed(ActionEvent e){
        buttonCount.setEnabled(true);
        buttonCencel.setEnabled(false);
        t.cancel(true);
        labelDownload.setText("Downloading is interrupted!");
    }


    //ActionListener for RadioButtons
    private class RadioButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            choice = radioButtonGroup.getSelection().getActionCommand();
        }
    }

    private class aTask extends SwingWorker<Void, String>{

        private double checkTime(Date ob1, Date ob2){
            long msElapsedTime;
            msElapsedTime = ob2.getTime() - ob1.getTime();
            Long longObject = new Long(String.valueOf(msElapsedTime));
            return longObject.doubleValue() * 0.001;
        }

        private void selectRecordTableDB(){
            try{
                System.out.println("Selecting row..");
                stmt = obTestPGDB.getConnPG().createStatement();

                String sql = "SELECT name, model, engine FROM car, master WHERE master.id_car = car.id";
                ResultSet rs = stmt.executeQuery(sql);
                //STEP 5: Extract data from result set
                while(rs.next()){
                    //Retrieve by column name
                    String name  = rs.getString("name");
                    String model = rs.getString("model");
                    String engine = rs.getString("engine");

                    textArea.append("Name: " + name + ", Model: " + model + ", Engine: " + engine + "\n");

                    //Display values
                /*System.out.print("Name: " + name);
                System.out.print(", Model: " + model);
                System.out.print(", Engine: " + engine);
                System.out.println();*/
                }
                rs.close();

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
        }

        public void insertData(pgsqlDB ob, String tableName, int n){
            int idx, idx1, id_car;//, countCar = 1;
            String Model , Engine, name;
            Random rn = new Random();

            switch (tableName) {
                case "car":
                    for(int i=1; i < models.length; i++) {
                        for(int j=1; j < engines.length; j++) {
                            Model = models[i];
                            Engine = engines[j];

                            textArea.append(ob.insertIntoTableDB(Model, Engine, k));

                            k++;
                        }
                    }
                    break;
                case "master":
                    k--;
                    for(int i=1; i <= n; i++) {
                        name = "Name" + String.valueOf(i);
                        id_car = rn.nextInt(k - 1) + 1;

                        textArea.append(ob.insertIntoTableDB(name, id_car, i));
                    }
                    break;
                default:
                    throw new IllegalArgumentException(tableName);
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            String s = "";
            progressBar.setIndeterminate(true);
            obTestPGDB.connectDB();
            obTestPGDB.resetDB();
            labelDownload.setText("Downloading...");
            textArea.setText("");
            switch (choice){
                case "PG":
                    Date startDate3 = new Date();
                    insertData(obTestPGDB, "car", amountOfRaws);
                    Date endDate3 = new Date();

                    time4 = checkTime(startDate3, endDate3);

                    Date startDate4 = new Date();
                    insertData(obTestPGDB, "master", amountOfRaws);
                    Date endDate4 = new Date();

                    time5 = checkTime(startDate4, endDate4);

                    Date startDate5 = new Date();
                    selectRecordTableDB();
                    Date endDate5 = new Date();

                    time6 = checkTime(startDate5, endDate5);

                    System.out.println(time4 + " : " + time5 + " : " + time6);
                    break;
                case "RD":
                    System.out.println("RD");
                    break;
                case "RDPG":
                    insertData(obTestPGDB, "car", amountOfRaws);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "My Goodness, this is unknown command.");
                    break;
            }
            /*for(int i=progressBar.getMinimum(); i<=progressBar.getMaximum(); i++){
                //Thread.sleep(70);
                //textArea.append(i + ": " + s + "\n");
                progressBar.setValue(i);
            }*/
            return null;
        }

        @Override
        protected void done(){
            progressBar.setIndeterminate(false);
            labelDownload.setText("Downloaded!");
            buttonCount.setEnabled(true);
            buttonCencel.setEnabled(false);
        }
    }/*

    public void insertData(pgsqlDB ob, String tableName, int n) {
        int idx, idx1, id_car;//, countCar = 1;
        String Model, Engine, name;
        Random rn = new Random();

        switch (tableName) {
            case "car":
                for (int i = 1; i < models.length; i++) {
                    for (int j = 1; j < engines.length; j++) {
                        Model = models[i];
                        Engine = engines[j];

                        textArea.append(ob.insertIntoTableDB(Model, Engine, k));
                        k++;
                    }
                }
                break;
            case "master":
                k--;
                for (int i = 1; i <= n; i++) {
                    name = "Name" + String.valueOf(i);
                    id_car = rn.nextInt(k - 1) + 1;
                    textArea.append(ob.insertIntoTableDB(name, id_car, i));
                }
                break;
            default:
                throw new IllegalArgumentException(tableName);
        }
    }*/
}
