package ru.avalon.vergentev.j120.labwork3b;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Game extends JFrame implements MouseMotionListener {
    JButton[] button = new JButton[15];
    String titleOfButton;
    HashMap<JButton, HashMap<Integer, Integer>> mapButtons, gameOverButtons, mapButtonsClone;
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
            button[i].setBackground(new Color(50,255,50));
            button[i].setSize(100, 100);
            button[i].addMouseMotionListener(this);
            add(button[i]);
        }
        setButtonStartPositions();
    }

    //метод задающий стартовую позицию расположения кнопок (удобно для проверки)
    public void setButtonStartPositions () {
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
        button[13].setLocation(2 * button[13].getWidth(), 3 * button[13].getHeight());
        button[14].setLocation(3 * button[14].getWidth(), 3 * button[14].getHeight());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        for (JButton i : button) {if (e.getSource() == i) algorithmIfButtonIsPushed(i);}
    }
    @Override
    public void mouseMoved(MouseEvent e) {}

    public void algorithmIfButtonIsPushed (JButton buttonPushed) {
        //записываем текущее расположение кнопок в коллекцию
        getButtonsCollection();
        //делаем копию расположения кнопок для метода setButtonsByPositions();
        mapButtonsClone = getButtonsCollection();
        //разрешаем двигать кнопки, которые располагаются в определённых позициях вокруг пустого узла...
        //... И при это добавляем ещё условие проверки будет ли только одна ячейка пустой
        if (availableSurroundingOfEmptyNode().containsValue(getButtonPushedCoordinates(buttonPushed)) && isThereOnlyOneEmptyNode()) {
            moveButtonInDiscretePositions(buttonPushed);
        }
        if (!isThereOnlyOneEmptyNode()) {
            setButtonsByPositions();             //если не одна ячейка делаем возврат позиций
        }
        //проверяем закончена ли игра
        if (isGameOver()) {
            gameOver();
        }
    }

    //метод записывающий положение кнопок в виде коллекции
    public HashMap<JButton, HashMap<Integer, Integer>> getButtonsCollection () {
        mapButtons = new HashMap<>();
        for (JButton i : button) {
            coordinates = new HashMap<>();
            x = (i.getX()) / i.getWidth();
            y = (i.getY()) / i.getHeight();
            coordinates.put(x, y);
            mapButtons.put(i, coordinates);
        }
        return mapButtons;
    }

    //метод показывающий доступное окружение, относительно которого можно двигать кнопки
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

    //метод поиска пустого узла
    public HashMap<Integer, Integer> searchingForTheEmptyNode () {
        //формируем коллекцию узлов по аналогии с коллекцией кнопок
        mapNodes = new HashMap<>();
        int j = 1;
        for (int n = 0; n < 4; n++) {             //проходим...
            for (int m = 0; m < 4; m++) {         //...сетку 4х4
                coordinates = new HashMap<>();
                coordinates.put(m, n);
                mapNodes.put(j, coordinates);     //создаём пару координат в аналогичную коллекцию, что координаты кнопки
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

    //метод возвращающий в удобном виде координаты нажатой кнопки
    public HashMap<Integer, Integer> getButtonPushedCoordinates (JButton buttonPushed) {
        coordinates = new HashMap<>();
        x = (buttonPushed.getX()) / buttonPushed.getWidth();
        y = (buttonPushed.getY()) / buttonPushed.getHeight();
        coordinates.put(x, y);
        return coordinates;
    }

    //проверка действительно ли только один узел пустой
    public boolean isThereOnlyOneEmptyNode () {
        //просто оцениваем длину коллекции текущего расположения кнопок, она д.б. равна только 15
        Map<JButton, Map<Integer, Integer>> mapButtonsTemp = new HashMap<>();
        for (JButton i : getButtonsCollection().keySet()) {
            if (!mapButtonsTemp.containsValue(getButtonsCollection().get(i))) {
                mapButtonsTemp.put(i, getButtonsCollection().get(i));
            }
        }
        return mapButtonsTemp.size() == 15;
    }

    //метод разрешающий двигать кнопки по дискретным узловым позициям
    public void moveButtonInDiscretePositions (JButton buttonPushed) {
        //двигаем кнопки только которые вокруг пустого узла
        int mousePositionX = ((int) getMousePosition().getX() / 100) * 100;
        int mousePositionY = ((int) getMousePosition().getY() / 100) * 100;
        buttonPushed.setLocation(mousePositionX, mousePositionY);
    }

    //метод устанавливающий позиции кнопок (нужен в комбинации если пустой узел не один, то есть кнопки наложены друг на друга)
    public void setButtonsByPositions () {
        for (JButton i : mapButtonsClone.keySet()) {
            for (Integer j : (mapButtonsClone.get(i)).keySet()) {
                i.setLocation(j * i.getWidth(), (mapButtonsClone.get(i)).get(j) * i.getHeight());
            }
        }
//        setButtonStartPositions();
    }

    //метод проверки закончена ли игра или нет
    public boolean isGameOver () {
        getButtonsCollection();
        //формируем коллекцию кнопок расположение того, когда игра заканчивается
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
        //сравниваем две коллекции
        return gameOverButtons.equals(getButtonsCollection());
    }

    //метод делающий действия если игра закончена
    public void gameOver () {
        int GameOverWindow = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "You are winner!", JOptionPane.OK_CANCEL_OPTION);
        if (GameOverWindow == JOptionPane.OK_OPTION) {
            this.dispose();
        } else {
            setButtonStartPositions();
        }
    }
}
