package ru.avalon.vergentev.j120.labwork3b;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Game extends JFrame implements MouseMotionListener {
    JButton[] button = new JButton[15];
    String titleOfButton;
    Map<Integer, Integer> coordinates, surrounding;
    Map<Integer, Map<Integer, Integer>> mapButtons, mapNodes, mapSurrounding;
    int x, y, xEmpty, yEmpty, width, height;

    public Game () {
        super("Tikhon's Game15");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(416, 440);
        getContentPane().setBackground(new Color(180,180,180));
        setResizable(false);
        setLocationRelativeTo(null);
//        Container container = new Container();
//        container.setLayout(new GridLayout(4,4));
        setLayout(new GridLayout(4, 4));
//        setLayout(null);
        initializationButtons();
    }

    //METHODS
    //определяем внешний вид кнопок и добавляем на сетку
    public void initializationButtons () {
        for (int i = 0; i < button.length; i++) {
            titleOfButton = Integer.toString(i+1);
            button[i] = new JButton(titleOfButton);
            button[i].setFont(new Font("Segoe UI", Font.BOLD, 40));
            button[i].setBackground(new Color(255,255,255));
//            button[i].setLocation(0, 0);
            button[i].setSize(100, 100);
            button[i].addMouseMotionListener(this);
            add(button[i]);
        }
//        GridBagConstraints position0 = new GridBagConstraints();
//        position0.gridx = 0;
//        position0.gridy = 0;
//        add(button[0], position0);
//
//        GridBagConstraints position1 = new GridBagConstraints();
//        position1.gridx = 1;
//        position1.gridy = 0;
//        add(button[1], position1);
//
//        GridBagConstraints position2 = new GridBagConstraints();
//        position2.gridx = 2;
//        position2.gridy = 0;
//        add(button[2], position2);
//
//        GridBagConstraints position3 = new GridBagConstraints();
//        position3.gridx = 3;
//        position3.gridy = 0;
//        add(button[3], position3);
//
//        GridBagConstraints position4 = new GridBagConstraints();
//        position4.gridx = 0;
//        position4.gridy = 1;
//        add(button[4], position4);
//
//        GridBagConstraints position5 = new GridBagConstraints();
//        position5.gridx = 1;
//        position5.gridy = 1;
//        add(button[5], position5);
//
//        GridBagConstraints position6 = new GridBagConstraints();
//        position6.gridx = 2;
//        position6.gridy = 1;
//        add(button[6], position6);
//
//        GridBagConstraints position7 = new GridBagConstraints();
//        position7.gridx = 3;
//        position7.gridy = 1;
//        add(button[7], position7);
//
//        GridBagConstraints position8 = new GridBagConstraints();
//        position8.gridx = 0;
//        position8.gridy = 2;
//        add(button[8], position8);
//
//        GridBagConstraints position9 = new GridBagConstraints();
//        position9.gridx = 1;
//        position9.gridy = 2;
//        add(button[9], position9);
//
//        GridBagConstraints position10 = new GridBagConstraints();
//        position10.gridx = 2;
//        position10.gridy = 2;
//        add(button[10], position10);
//
//        GridBagConstraints position11 = new GridBagConstraints();
//        position11.gridx = 3;
//        position11.gridy = 2;
//        add(button[11], position11);
//
//        GridBagConstraints position12 = new GridBagConstraints();
//        position12.gridx = 0;
//        position12.gridy = 3;
//        add(button[12], position12);
//
//        GridBagConstraints position13 = new GridBagConstraints();
//        position13.gridx = 1;
//        position13.gridy = 3;
//        add(button[13], position13);
//
//        GridBagConstraints position14 = new GridBagConstraints();
//        position14.gridx = 2;
//        position14.gridy = 3;
//        add(button[14], position14);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (JButton i : button) {if (e.getSource() == i) algorithmIfButtonIsPushed(i);}
    }
    @Override
    public void mouseMoved(MouseEvent e) {}

    public void algorithmIfButtonIsPushed (JButton buttonPushed) {
        savingButtonCoordinatesInCollection();
        //двигаем кнопки только которые вокруг пустого узла
        if (searchingSurroundingOfEmptyNode().containsValue(getButtonPushedCoordinates(buttonPushed))) {
            int mousePositionX = ((int) getMousePosition().getX() / 100) * 100;
            int mousePositionY = ((int) getMousePosition().getY() / 100) * 100;
            buttonPushed.setLocation(mousePositionX, mousePositionY);
        }
        savingButtonCoordinatesInCollection();
        if (!isThereOnlyOneEmptyNode()) {
            setButtonOnPositions();
        }

    }

    public HashMap savingButtonCoordinatesInCollection () {
        mapButtons = new HashMap<>();
        int j = 1;
        for (JButton i : button) {
            coordinates = new HashMap<>();
            x = (i.getX()) / i.getWidth();
            y = (i.getY()) / i.getHeight();
            coordinates.put(x, y);
            mapButtons.put(j, coordinates);
            j++;
        }
        return (HashMap) mapButtons;
    }

    public HashMap searchingForTheEmptyNode () {
        //формируем коллекцию узлов по аналогии с коллекцией кнопок
        mapNodes = new HashMap<>();
        int j = 1;
        for (int n = 0; n < 4; n++) {             //проходим...
            for (int m = 0; m < 4; m++) {         //...сетку 4х4
                coordinates = new HashMap<>();
                coordinates.put(m, n);            //создаём пару координат в аналогичную коллекцию, что координаты кнопки
                mapNodes.put(j, coordinates);
                j++;
            }
        }
        //сравниваем коллекции кнопок и узлов
        for (Integer i : mapNodes.keySet()) {
            if (!(mapButtons.containsValue(mapNodes.get(i)))) {
                return (HashMap) mapNodes.get(i);
            }
        }
        return null;
    }

    public HashMap getButtonPushedCoordinates (JButton buttonPushed) {
        coordinates = new HashMap<>();
        x = (buttonPushed.getX()) / buttonPushed.getWidth();
        y = (buttonPushed.getY()) / buttonPushed.getHeight();
        coordinates.put(x, y);
        return (HashMap) coordinates;
    }

    public HashMap searchingSurroundingOfEmptyNode () {
        for (Object i : searchingForTheEmptyNode().keySet()) {
            xEmpty = (int) i;
            yEmpty = (int) searchingForTheEmptyNode().get(i);
        }
        mapSurrounding = new HashMap<>();
        int k = 1;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {}   //ничего не записывать
                else if (xEmpty+i > 3) {}  //ничего не записывать
                else if (xEmpty+i < 0) {}  //ничего не записывать
                else if (yEmpty+j > 3) {}  //ничего не записывать
                else if (yEmpty+j < 0) {}  //ничего не записывать
                else {
                    surrounding = new HashMap<>();
                    surrounding.put(xEmpty+i, yEmpty+j);
                    mapSurrounding.put(k, surrounding);
                    k++;
                }
            }
        }
        return (HashMap) mapSurrounding;
    }

    public boolean isThereOnlyOneEmptyNode () {
        Map<Integer, Map<Integer, Integer>> mapButtonsTemp = new HashMap<>();
        for (Object i : savingButtonCoordinatesInCollection().keySet()) {
            if (!mapButtonsTemp.containsValue(savingButtonCoordinatesInCollection().get(i))) {
                mapButtonsTemp.put((Integer) i, (Map<Integer, Integer>) savingButtonCoordinatesInCollection().get(i));
            }
        }
        if (mapButtonsTemp.size() != 15) {
            return false;
        }
        return true;
    }

    public void setButtonOnPositions () {
        for (Object i : savingButtonCoordinatesInCollection().keySet()) {

        }
    }

    public void lalala () {
//        for (Integer i : mapNodes.keySet()) {
//            System.out.println(i + " " + mapNodes.get(i));
//        }
//        System.out.println("--------------");
//        for (Integer i : mapButtons.keySet()) {
//            System.out.println(i + " " + mapButtons.get(i));
//        }
//        System.out.println("=============");

//        System.out.println(searchingForTheEmptyNode());
    }


//    public void searchingForTheEmptyNode (JButton buttonPushed) {
//        for (int n = 0; n < 4; n++) {             //проходим...
//            for (int m = 0; m < 4; m++) {         //...сетку 4х4
//                coordinates = new HashMap<>();
//                coordinates.put(n, m);            //создаём пару координат в аналогичную коллекцию, что координаты кнопки
//                for (JButton i : button) {        //для каждого узла сетки 4х4 проходим по всему списку координат кнопок
//                    if (map.get(i) == coordinates) {         //если координаты кнопки совпадают с узлом сетки 4х4, то это пустая ячейка
//                        xEmpty = n;
//                        yEmpty = m;
//                    }
//                }
//                coordinates.clear();
//            }
//        }
////        System.out.println(xEmpty + ", " + yEmpty);
//    }




//    @Override
//    public void actionPerformed(ActionEvent e) {
//        for (JButton i : button) {
//            if (e.getSource() == i) algorithmIfButtonIsPushed(i);
//        }
//    }

//    public void algorithmIfButtonIsPushed (JButton buttonPushed) {
//        searchingForTheEmptyNode(buttonPushed);
//        System.out.println("old x, y = " + buttonPushed.getX() + ", " + buttonPushed.getY());
//        System.out.println("Empty " + xEmpty + ", " + yEmpty);
//        buttonPushed.setLocation(xEmpty, yEmpty);
//        System.out.println("new x, y = " + buttonPushed.getX() + ", " + buttonPushed.getY());
//
//        for (int n = 0; n < 4; n++) {
//            for (int m = 0; m < 4; m++) {
//                for (JButton i : button) {
//                    x = (i.getX()) / i.getWidth();
//                    y = (i.getY()) / i.getHeight();
//                    if (n != x) {
//                        xEmpty = n*buttonPushed.getWidth();
//                    } else if (m != y) {
//                        yEmpty = m*buttonPushed.getHeight();
//                    }
//                }
//            }
//        }
//        System.out.println("Empty " + xEmpty + ", " + yEmpty);
//    }





}
