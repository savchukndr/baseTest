package com.company.gui;

import com.company.database_handler.Connector;
import com.company.databases.*;
import interfaces.DataBaseInterface;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

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
    private JTextField testField;
    private JButton buttonCount, buttonCencel;
    private JProgressBar progressBar;
    private JLabel labelDownload;
    private JLabel labelTextAmount;
    private JTextArea textArea;
    private JRadioButton radioButtonPG, radioButtonRD, radioButtonRDPG;
    private ButtonGroup radioButtonGroup;
    private String choice;
    private int k, amountOfRaws;
    private pgsqlDB obTestPGDB = null;
    private redisDB obTestRDB = null;
    private double time4, time5, time6, time9, time10, time11;
    private Statement stmt = null;
    private ResultFrame resRDFrame;
    private ResultFrame resPGFrame;
    private ResultFrame resRDPGFrame;
    private boolean resRDExist, resPGexist, resRDPGexist;

    private Jedis jedis = null;
    private Map<String, String> carInfo;
    private Map<String, String> masterInfo;
    private int id_car = 1;
    private Random randomGenerator = new Random();
    private int counter = 0;


    public MainFrame(){
        try {
            obTestPGDB = new pgsqlDB();
            obTestRDB = new redisDB(amountOfRaws);
            resPGexist = false;
            resPGexist = false;
            resRDPGexist = false;
            k = 1;
            amountOfRaws = 1000;
            initialize();
        }catch (InterruptedException e){
            System.out.print("Exception: " + e);
        }
    }

    private void initialize() throws InterruptedException {
        //Frame
        setTitle("Base Test");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

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

        labelTextAmount = new JLabel("Please, input amount of raws:");
        testField = new JTextField(2);
        testField.setText(String.valueOf(amountOfRaws));

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
                new Insets(1,2,2,2), 0, 0));
        //radiobutton for both
        panelLeft.add(radioButtonRDPG, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(1,2,2,2), 0, 0));
        panelLeft.add(labelTextAmount, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(100,2,2,2), 2, 2));
        panelLeft.add(testField, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(1,2,2,2), 2, 2));
        //buttonCount added to panelLeft
        panelLeft.add(buttonCount, new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(20,2,2,2), 2, 2));
        panelLeft.add(buttonCencel, new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(1,2,2,2), 2, 2));



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

        add(panelTop, BorderLayout.NORTH);
        setVisible(true);
        pack();

    }

    private void startActionPerformed(ActionEvent e){
        if (testField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "The field is empty!!!\n Do not forget to input amount of raws!");
        }else {
            if (!testField.getText().matches("^\\d+$")){
                JOptionPane.showMessageDialog(null, "Wrong symbols! (Not Integer)");
            }else {
                amountOfRaws = Integer.parseInt(testField.getText());
                (t = new aTask()).execute();
                buttonCount.setEnabled(false);
                buttonCencel.setEnabled(true);
                k = 1;
                if (resPGexist) {
                    resPGFrame.dispose();
                    resPGexist = false;
                }
                if (resRDExist) {
                    resRDFrame.dispose();
                    resPGexist = false;
                }
                if (resRDPGexist) {
                    resRDPGFrame.dispose();
                    resRDPGexist = false;
                }
            }
        }
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

                }
                rs.close();

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );
                System.exit(0);
            }
        }

        void insertData(pgsqlDB ob, String tableName, int n){
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

        public void insertCar(){
            try {
                carInfo = new HashMap<>();
                for(int i=1; i < models.length; i++) {
                    for (int j=1; j < engines.length; j++) {
                        textArea.append("Inserting [" + id_car + "] key-value for cars\n");
                        carInfo.put("model", models[i]);
                        carInfo.put("engine", engines[j]);
                        jedis.hmset("carID:" + id_car, carInfo);
                        textArea.append("Inserted [" + id_car + "] key-value for cars...\n");
                        id_car++;
                        counter++;
                    }
                }
            }catch (Exception e){
                System.out.println("Error \"insertCar()\" is: " + e);
            }
        }

        public void insertMaster(){
            try {
                id_car--;
                masterInfo = new HashMap<>();
                for(int i=1; i<=amountOfRaws; i++) {
                    textArea.append("Inserting [" + i + "] key-value for masters\n");
                    masterInfo.put("name", "Name" + i);
                    int index = randomGenerator.nextInt(id_car - 1) + 1;
                    masterInfo.put("car", "carID:" + index);
                    jedis.hmset("masterID:" + i, masterInfo);
                    textArea.append("Inserted [" + i + "] key-value for masters...\n");
                    counter++;
                }
            }catch (Exception e){
                System.out.println("Error \"insertMaster()\" is: " + e);
            }
        }

        public void retreiveRecord(){
            try {
                String cId, mId;
                String valEng = "", valMod = "", valName = "", resCar = "", tmp;
                for(int i=1; i<=counter; i++){
                    mId = "masterID:" + i;
                    Map<String, String> resM = jedis.hgetAll(mId);
                    Set set = resM.entrySet();
                    for (Object aSet : set) {
                        Map.Entry me = (Map.Entry) aSet;
                        if (me.getKey().equals("name")) {
                            valName = me.getKey() + ": " + me.getValue();
                        }
                        if (me.getKey().equals("car")) {
                            for(int j=1; j<54; j++){
                                cId = "carID:" + j;
                                tmp = me.getValue() + "";
                                if (cId.equals(tmp)) {
                                    Map<String, String> resC = jedis.hgetAll(cId);
                                    Set set1 = resC.entrySet();
                                    for (Object bSet : set1) {
                                        Map.Entry ke = (Map.Entry) bSet;
                                        if (ke.getKey().equals("model")) {
                                            valMod = ke.getKey() + ": " + ke.getValue();
                                        }
                                        if (ke.getKey().equals("engine")) {
                                            valEng = ke.getKey() + ": " + ke.getValue();
                                        }
                                    }
                                    resCar = cId + ": {" + valMod + ", " + valEng +"}";
                                    break;
                                }
                            }
                            textArea.append(mId + "{" + valName + ", " + resCar + "}\n");
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("Error \"retreiveData()\" is: " + e);
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            progressBar.setIndeterminate(true);
            obTestPGDB.connectDB();
            obTestPGDB.resetDB();
            jedis = obTestRDB.connectDB();
            obTestRDB.deleteRecord(jedis);
            labelDownload.setText("Downloading...");
            textArea.setText("");
            switch (choice){
                case "PG":
                    Date startDate3 = new Date();
                    insertData(obTestPGDB, "car", amountOfRaws);
                    Date endDate3 = new Date();

                    time4 = Math.floor(checkTime(startDate3, endDate3) * 100) / 100;

                    Date startDate4 = new Date();
                    insertData(obTestPGDB, "master", amountOfRaws);
                    Date endDate4 = new Date();

                    time5 = Math.floor(checkTime(startDate4, endDate4) * 100) / 100;

                    Date startDate5 = new Date();
                    selectRecordTableDB();
                    Date endDate5 = new Date();

                    time6 = Math.floor(checkTime(startDate5, endDate5) * 100) / 100;

                    resPGFrame = new ResultFrame(Double.toString(time4), Double.toString(time5), Double.toString(time6), "PG");
                    resPGexist = true;
                    break;
                case "RD":
                    Date startDate8 = new Date();
                    insertCar();
                    Date endDate8 = new Date();

                    time9 = Math.floor(checkTime(startDate8, endDate8) * 100) / 100;

                    Date startDate9 = new Date();
                    insertMaster();
                    Date endDate9 = new Date();

                    time10 = Math.floor(checkTime(startDate9, endDate9) * 100) / 100;

                    Date startDate10 = new Date();
                    retreiveRecord();
                    Date endDate10 = new Date();

                    time11 = Math.floor(checkTime(startDate10, endDate10) * 100) / 100;
                    resRDFrame = new ResultFrame(Double.toString(time9), Double.toString(time10), Double.toString(time11), "RD");
                    resRDExist = true;
                    break;
                case "RDPG":
                    startDate3 = new Date();
                    insertData(obTestPGDB, "car", amountOfRaws);
                    endDate3 = new Date();

                    time4 = Math.floor(checkTime(startDate3, endDate3) * 100) / 100;

                    startDate4 = new Date();
                    insertData(obTestPGDB, "master", amountOfRaws);
                    endDate4 = new Date();

                    time5 = Math.floor(checkTime(startDate4, endDate4) * 100) / 100;

                    startDate5 = new Date();
                    selectRecordTableDB();
                    endDate5 = new Date();

                    time6 = Math.floor(checkTime(startDate5, endDate5) * 100) / 100;

                    startDate8 = new Date();
                    insertCar();
                    endDate8 = new Date();

                    time9 = Math.floor(checkTime(startDate8, endDate8) * 100) / 100;

                    startDate9 = new Date();
                    insertMaster();
                    endDate9 = new Date();

                    time10 = Math.floor(checkTime(startDate9, endDate9) * 100) / 100;

                    startDate10 = new Date();
                    retreiveRecord();
                    endDate10 = new Date();

                    time11 = Math.floor(checkTime(startDate10, endDate10) * 100) / 100;

                    resRDPGFrame = new ResultFrame(Double.toString(time4), Double.toString(time5), Double.toString(time6),
                                                    Double.toString(time9), Double.toString(time10), Double.toString(time11),"RDPG");
                    resRDPGexist = true;
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
    }
}
