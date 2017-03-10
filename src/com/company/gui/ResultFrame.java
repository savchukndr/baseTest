package com.company.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by savch on 07.03.2017.
 */
public class ResultFrame extends JFrame{
    private String carResRDPG, masterResRDPG, selectResRDPG;
    private String carResRD, masterResRD, retreiveResRD, choise;
    private JLabel timeIntoCarLabel;
    private JLabel timeIntoMasterLabel;
    private JLabel carResLabel;

    private JLabel masterResLabel;
    private JLabel selectTimeLabel;
    private JLabel selectResLabel;

    private JLabel timeIntoCarLabelRD;
    private JLabel timeIntoMasterLabelRD;
    private JLabel carResLabelRD;

    private JLabel masterResLabelRD;
    private JLabel selectTimeLabelRD;
    private JLabel selectResLabelRD;

    public ResultFrame(String choise){
        timeIntoCarLabel = new JLabel();
        carResLabel = new JLabel();
        timeIntoMasterLabel = new JLabel();
        masterResLabel = new JLabel();
        selectTimeLabel = new JLabel();
        selectResLabel = new JLabel();

        timeIntoCarLabelRD = new JLabel();
        carResLabelRD = new JLabel();
        timeIntoMasterLabelRD = new JLabel();
        masterResLabelRD = new JLabel();
        selectTimeLabelRD = new JLabel();
        selectResLabelRD = new JLabel();
        this.choise = choise;
        initialize();
    }

    public ResultFrame(String carResRDPG, String masterResRDPG, String selectResRDPG, String choise) throws HeadlessException {
        this.carResRDPG = carResRDPG;
        this.masterResRDPG = masterResRDPG;
        this.selectResRDPG = selectResRDPG;
        this.choise = choise;

        timeIntoCarLabel = new JLabel();
        carResLabel = new JLabel();
        timeIntoMasterLabel = new JLabel();
        masterResLabel = new JLabel();
        selectTimeLabel = new JLabel();
        selectResLabel = new JLabel();

        initialize();
    }

    public ResultFrame(String carResRDPG, String masterResRDPG, String selectResRDPG,
                       String carResRD, String masterResRD, String retreiveResRD, String choise) throws HeadlessException {
        this.carResRDPG = carResRDPG;
        this.masterResRDPG = masterResRDPG;
        this.selectResRDPG = selectResRDPG;
        this.carResRD = carResRD;
        this.masterResRD = masterResRD;
        this.retreiveResRD = retreiveResRD;
        this.choise = choise;

        timeIntoCarLabel = new JLabel();
        carResLabel = new JLabel();
        timeIntoMasterLabel = new JLabel();
        masterResLabel = new JLabel();
        selectTimeLabel = new JLabel();
        selectResLabel = new JLabel();

        timeIntoCarLabelRD = new JLabel();
        carResLabelRD = new JLabel();
        timeIntoMasterLabelRD = new JLabel();
        masterResLabelRD = new JLabel();
        selectTimeLabelRD = new JLabel();
        selectResLabelRD = new JLabel();

        initialize();
    }

    public void initialize(){
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setTitle("Result time");
        setResizable(false);
        switch (choise){
            case "PG":
                setSize(400, 200);
                /*carResRDPG = "0,1234";
                masterResRDPG = "1,1111";
                selectResRDPG = "10,1010101";*/

                timeIntoCarLabel.setText("Insert into car time: ");
                carResLabel.setText(carResRDPG);
                timeIntoMasterLabel.setText("Insert into master time: ");
                masterResLabel.setText(masterResRDPG);
                selectTimeLabel.setText("Select time: ");
                selectResLabel.setText(selectResRDPG);

                add(timeIntoCarLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(carResLabel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(timeIntoMasterLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(masterResLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectTimeLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectResLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));


                break;
            case "RD":
                setSize(400, 200);
                /*carResRDPG = "0,1234";
                masterResRDPG = "1,1111";
                selectResRDPG = "10,1010101";*/

                timeIntoCarLabel.setText("Insert redcord into car time: ");
                carResLabel.setText(carResRDPG);
                timeIntoMasterLabel.setText("Insert redcord into master time: ");
                masterResLabel.setText(masterResRDPG);
                selectTimeLabel.setText("Retreive record time: ");
                selectResLabel.setText(selectResRDPG);

                add(timeIntoCarLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(carResLabel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(timeIntoMasterLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(masterResLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectTimeLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectResLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));

                break;
            case "RDPG":
                setSize(400, 400);
                /*carResRDPG = "0,1234";
                masterResRDPG = "1,1111";
                selectResRDPG = "10,1010101";

                carResRD = "0,1234";
                masterResRD = "1,1111";
                retreiveResRD = "10,1010101";*/

                timeIntoCarLabel.setText("Insert into car time: ");
                carResLabel.setText(carResRDPG);
                timeIntoMasterLabel.setText("Insert into master time: ");
                masterResLabel.setText(masterResRDPG);
                selectTimeLabel.setText("Select time: ");
                selectResLabel.setText(selectResRDPG);

                timeIntoCarLabelRD.setText("Insert record into car time: ");
                carResLabelRD.setText(carResRD);
                timeIntoMasterLabelRD.setText("Insert record into master time: ");
                masterResLabelRD.setText(masterResRD);
                selectTimeLabelRD.setText("Retreive record time: ");
                selectResLabelRD.setText(retreiveResRD);

                add(timeIntoCarLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(carResLabel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(timeIntoMasterLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(masterResLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectTimeLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectResLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));


                add(timeIntoCarLabelRD, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(15,2,2,2), 2, 2));
                add(carResLabelRD, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(15,2,2,2), 2, 2));
                add(timeIntoMasterLabelRD, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(masterResLabelRD, new GridBagConstraints(1, 4, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectTimeLabelRD, new GridBagConstraints(0, 5, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));
                add(selectResLabelRD, new GridBagConstraints(1, 5, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                        new Insets(2,2,2,2), 2, 2));

                break;
            default:
                JOptionPane.showMessageDialog(null, "My Goodness, this is unknown command.");
                break;
        }

        setVisible(true);
        pack();
    }
}
