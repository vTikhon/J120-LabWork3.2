package ru.avalon.vergentev.j120.labwork3b;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Game extends JFrame implements MouseMotionListener {
    JButton[] button = new JButton[15];
    String titleOfButton;
    HashMap<JButton, HashMap<Integer, Integer>> mapButtons, gameOverButtons;
    HashMap<Integer, HashMap<Integer, Integer>> mapNodes, mapSurrounding;
    HashMap<Integer, Integer> coordinates, surrounding;
    int x, y, xEmpty, yEmpty;

    public Game () {
        super("Tikhon's Game15");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(416, 440);
        getContentPane().setBackground(new Color(180,180,180));
        setResizable(false);
        setLocationRelativeTo(null);
//        JPanel jPanel = new JPanel();
//        jPanel.setLayout(new GridLayout(4, 4));
//        Container container = new Container();
//        container.setLayout(new GridLayout(4, 4));
//        setLayout(new GridLayout(4, 4));
        setLayout(null);
        initializationButtons();
    }

    //METHODS
    //определяем внешний вид кнопок и добавляем на сетку
    public void initializationButtons () {
        for (int i = 0; i < button.length; i++) {
            titleOfButton = Integer.toString(i+1);
            button[i] = new JButton(titleOfButton);
            button[i].setName(titleOfButton);
            button[i].setFont(new Font("Segoe UI", Font.BOLD, 40));
            button[i].setBackground(new Color(255,255,255));
            button[i].setSize(100, 100);
            button[i].addMouseMotionListener(this);
//            button[i].setLocation(100, 100);
            add(button[i]);
        }
        button[0].setLocation(0 * button[0].getWidth(), 0 * button[0].getHeight());
        button[1].setLocation(1 * button[1].getWidth(), 0 * button[1].getHeight());
        button[2].setLocation(2 * button[2].getWidth(), 0 * button[2].getHeight());
        button[3].setLocation(3 * button[3].getWidth(), 0 * button[3].getHeight());
        button[4].setLocation(0 * button[4].getWidth(), 1 * button[4].getHeight());
        button[5].setLocation(1 * button[5].getWidth(), 1 * button[5].getHeight());
        button[6].setLocation(2 * button[6].getWidth(), 1 * button[6].getHeight());
        button[7].setLocation(3 * button[7].getWidth(), 1 * button[7].getHeight());
        button[8].setLocation(0 * button[8].getWidth(), 2 * button[8].getHeight());
        button[9].setLocation(1 * button[9].getWidth(), 2 * button[9].getHeight());
        button[10].setLocation(2 * button[10].getWidth(), 2 * button[10].getHeight());
        button[11].setLocation(3 * button[11].getWidth(), 2* button[11].getHeight());
        button[12].setLocation(0 * button[12].getWidth(), 3 * button[12].getHeight());
        button[13].setLocation(1 * button[13].getWidth(), 3 * button[13].getHeight());
        button[14].setLocation(3 * button[14].getWidth(), 3 * button[14].getHeight());

//        GridBagConstraints position0 = new GridBagConstraints();
//        position0.gridx = 0;
//        position0.gridy = 0;
//        GridBagConstraints position1 = new GridBagConstraints();
//        position1.gridx = 0;
//        position1.gridy = 1;
//        GridBagConstraints position2 = new GridBagConstraints();
//        position2.gridx = 0;
//        position2.gridy = 2;
//        GridBagConstraints position3 = new GridBagConstraints();
//        position3.gridx = 0;
//        position3.gridy = 3;
//        GridBagConstraints position4 = new GridBagConstraints();
//        position4.gridx = 0;
//        position4.gridy = 0;
//        GridBagConstraints position5 = new GridBagConstraints();
//        position5.gridx = 0;
//        position5.gridy = 0;
//        GridBagConstraints position6 = new GridBagConstraints();
//        position6.gridx = 0;
//        position6.gridy = 0;
//        GridBagConstraints position7 = new GridBagConstraints();
//        position7.gridx = 0;
//        position7.gridy = 0;
//        GridBagConstraints position8 = new GridBagConstraints();
//        position8.gridx = 0;
//        position8.gridy = 0;
//        GridBagConstraints position9 = new GridBagConstraints();
//        position9.gridx = 0;
//        position9.gridy = 0;
//        GridBagConstraints position10 = new GridBagConstraints();
//        position10.gridx = 0;
//        position10.gridy = 0;
//        GridBagConstraints position11 = new GridBagConstraints();
//        position11.gridx = 0;
//        position11.gridy = 0;
//        GridBagConstraints position12 = new GridBagConstraints();
//        position12.gridx = 0;
//        position12.gridy = 0;
//        GridBagConstraints position13 = new GridBagConstraints();
//        position13.gridx = 0;
//        position13.gridy = 0;
//        GridBagConstraints position14 = new GridBagConstraints();
//        position14.gridx = 0;
//        position14.gridy = 0;


//        add(button[0]);
//        add(button[1]);
//        add(button[2]);
//        add(button[3]);
//        add(button[4]);
//        add(button[5]);
//        add(button[6]);
//        add(button[7]);
//        add(button[8]);
//        add(button[9]);
//        add(button[10]);
//        add(button[11]);
//        add(button[12]);
//        add(button[13]);
//        add(button[14]);

//        for (int i = 1; i < 5; i++) {
//            for (int j = 1; j < 5; j++) {
//                if (j != 4) {
//                    button[i*j].setLocation(i * button[i*j].getWidth(), j * button[i*j].getHeight());
//                    add(button[i*j]);
//                }
//            }
//        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (JButton i : button) {if (e.getSource() == i) algorithmIfButtonIsPushed(i);}
    }
    @Override
    public void mouseMoved(MouseEvent e) {}

    public void algorithmIfButtonIsPushed (JButton buttonPushed) {
        getButtonsCollection();
        //двигаем кнопки только которые вокруг пустого узла
        if (availableSurroundingOfEmptyNode().containsValue(getButtonPushedCoordinates(buttonPushed)) && isThereOnlyOneEmptyNode()) {
            int mousePositionX = ((int) getMousePosition().getX() / 100) * 100;
            int mousePositionY = ((int) getMousePosition().getY() / 100) * 100;
            buttonPushed.setLocation(mousePositionX, mousePositionY);
        } else {
            setButtonsByPositions();
        }
        if (isGameOver()) {
            gameOver();
        }
//        print();
    }


    public HashMap<JButton, HashMap<Integer, Integer>> getButtonsCollection () {
        mapButtons = new HashMap<>();
        for (JButton i : button) {
            coordinates = new HashMap<>();
            x = (i.getX()) / i.getWidth();
            y = (i.getY()) / i.getHeight();
            coordinates.put(x, y);
            mapButtons.put(i, coordinates);
//            System.out.println(i.getName());
        }
        return mapButtons;
    }

    public HashMap<Integer, Integer> searchingForTheEmptyNode () {
        //формируем коллекцию узлов по аналогии с коллекцией кнопок
        mapNodes = new HashMap<>();
        int j = 1;
        for (int n = 0; n < 4; n++) {             //проходим...
            for (int m = 0; m < 4; m++) {         //...сетку 4х4
                coordinates = new HashMap<>();
                coordinates.put(m, n);
                mapNodes.put(j, coordinates);            //создаём пару координат в аналогичную коллекцию, что координаты кнопки
                j++;
            }
        }
        //сравниваем коллекции кнопок и узлов
        for (Integer i : mapNodes.keySet()) {
            if (!(mapButtons.containsValue(mapNodes.get(i)))) {
                return mapNodes.get(i);
            }
        }
        return null;
    }

    public HashMap<Integer, Integer> getButtonPushedCoordinates (JButton buttonPushed) {
        coordinates = new HashMap<>();
        x = (buttonPushed.getX()) / buttonPushed.getWidth();
        y = (buttonPushed.getY()) / buttonPushed.getHeight();
        coordinates.put(x, y);
        return coordinates;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> availableSurroundingOfEmptyNode () {
        for (Integer i : searchingForTheEmptyNode().keySet()) {
            xEmpty = i;
            yEmpty = searchingForTheEmptyNode().get(i);
        }
        mapSurrounding = new HashMap<>();
        int k = 1;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if  (i == 0 && j == 0) {}  //ничего не записывать
                else if ((Math.abs(i) + Math.abs(j)) / 2 == 1) {}  //ничего не записывать
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
        return mapSurrounding;
    }

    public boolean isThereOnlyOneEmptyNode () {
        Map<JButton, Map<Integer, Integer>> mapButtonsTemp = new HashMap<>();
        for (JButton i : getButtonsCollection().keySet()) {
            if (!mapButtonsTemp.containsValue(getButtonsCollection().get(i))) {
                mapButtonsTemp.put(i, getButtonsCollection().get(i));
            }
        }
        return mapButtonsTemp.size() == 15;
    }

    public void setButtonsByPositions () {
        getButtonsCollection();
        for (JButton i : getButtonsCollection().keySet()) {
            for (Integer j : (getButtonsCollection().get(i)).keySet()) {
                i.setLocation(j * i.getWidth(), (getButtonsCollection().get(i)).get(j) * i.getHeight());
            }
        }
        getButtonsCollection();
    }

    public HashMap<JButton, HashMap<Integer, Integer>> getGameOverPositions () {
        getButtonsCollection();
        gameOverButtons = new HashMap<>();
        for (JButton i : getButtonsCollection().keySet()) {
            if (i.getName().equals("1")) {
                coordinates = new HashMap<>();
                coordinates.put(0, 0);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("2")) {
                coordinates = new HashMap<>();
                coordinates.put(1, 0);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("3")) {
                coordinates = new HashMap<>();
                coordinates.put(2, 0);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("4")) {
                coordinates = new HashMap<>();
                coordinates.put(3, 0);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("5")) {
                coordinates = new HashMap<>();
                coordinates.put(0, 1);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("6")) {
                coordinates = new HashMap<>();
                coordinates.put(1, 1);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("7")) {
                coordinates = new HashMap<>();
                coordinates.put(2, 1);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("8")) {
                coordinates = new HashMap<>();
                coordinates.put(3, 1);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("9")) {
                coordinates = new HashMap<>();
                coordinates.put(0, 2);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("10")) {
                coordinates = new HashMap<>();
                coordinates.put(1, 2);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("11")) {
                coordinates = new HashMap<>();
                coordinates.put(2, 2);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("12")) {
                coordinates = new HashMap<>();
                coordinates.put(3, 2);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("13")) {
                coordinates = new HashMap<>();
                coordinates.put(0, 3);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("14")) {
                coordinates = new HashMap<>();
                coordinates.put(1, 3);
                gameOverButtons.put(i, coordinates);
            } else if (i.getName().equals("15")) {
                coordinates = new HashMap<>();
                coordinates.put(2, 3);
                gameOverButtons.put(i, coordinates);
            }
        }
        return gameOverButtons;
    }

    public boolean isGameOver () {
        return getGameOverPositions().equals(getButtonsCollection());
    }

    public void gameOver () {
        int GameOverWindow = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "You are winner!", JOptionPane.OK_CANCEL_OPTION);
        if (GameOverWindow == JOptionPane.OK_OPTION) {
            this.dispose();
        }
    }

    public void print () {
        System.out.println("----------------");
        for (JButton i : getButtonsCollection().keySet()) {
            System.out.println(getGameOverPositions().get(i));
        }
        System.out.println("================");
        for (JButton i : getButtonsCollection().keySet()) {
            System.out.println(getButtonsCollection().get(i));
        }
        System.out.println(getGameOverPositions().equals(getButtonsCollection()));
    }
}
