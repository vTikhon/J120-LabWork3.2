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
        setLayout(new GridLayout(4,4));
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
    }

    public void savingButtonCoordinatesInCollection () {
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
