package com.virajprakash.twentyfortyeight;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game extends JFrame implements KeyListener {
    int boardSize = 4;
    int[][] board = new int[boardSize][boardSize];
    List<Point> freeSpaces = new ArrayList<>();
    Grid grid;
    public Game() {
        setSize(500, 500);
        setTitle("2048");
        setResizable(true);
        requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Point point = new Point();
                point.i = i;
                point.j = j;
                freeSpaces.add(point);
            }
        }
        grid = new Grid(boardSize, board);
        add(grid);
        addKeyListener(this);
        setVisible(true);
    }
    Random random = new Random();
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        System.out.println("Up = W, down = A, left = S, right = D");
        System.out.println("Adding random points");
        for (int i = 0; i < 2; i++)
        game.addRandom();
        game.printBoard();

//        while(true) {
//            String input = scanner.nextLine();
//            boolean valid = game.isBoardValid(game.board);
//            if (!valid) {
//                System.out.println("You lost.. Try again!");
//            }
//            boolean moveValid = false;
//            // is valid function
//            if (valid) {
//                if (input.equalsIgnoreCase("w")) {
//                    moveValid = game.moveUp();
//                } else if (input.equalsIgnoreCase("a")) {
//                    moveValid = game.moveLeft();
//                } else if (input.equalsIgnoreCase("s")) {
//                    moveValid = game.moveDown();
//                } else if (input.equalsIgnoreCase("d")) {
//                    moveValid = game.moveRight();
//                } else {
//                    System.exit(0);
//                }
//                if (moveValid) {
//                    game.addRandom();
//                    System.out.println();
//                } else {
//                    System.out.println("Invalid move ..");
//                }
//                game.printBoard();
//                System.out.println();
//            }
//
//        }

    }

    int[][] transpose(int[][] x) {
        int[][] transpose = new int[boardSize][boardSize];
        for (int i = 0 ; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                transpose[i][j] = x[j][i];
            }
        }
        return transpose;
    }

    int[][] reverseColumns(int[][] x) {
        int[][] reversed = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                reversed[i][j] = x[boardSize - i - 1][j];
            }
        }
        return reversed;
    }

    void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    boolean moveDown() {
        boolean valid = false;
        // for each column
        for (int j = 0; j < boardSize; j++) {
            for (int x = boardSize - 1; x > 0; x--) {
                int currentNumber = board[x][j];
                // current position
                if (currentNumber == 0) {
                    // on a zero
                    int numberAbove = board[x-1][j];
                    if (numberAbove == 0) {
                        // on a zero and number above is zero
                        if (x-1 == 0) continue;
                        int i = x-1;
                        while (i >= 0) {
                            int nextNumber = board[i][j];
                            // next number is different
                            if (nextNumber != 0) {
                                valid = true;
                                int k = i - 1;
                                while (k >= 0) {
                                    int up = board[k][j];
                                    if (up != nextNumber && up != 0) break;
                                    if (up == nextNumber) {
                                        nextNumber = nextNumber * 2;
                                        board[k][j] = 0;
                                    }
                                    k--;
                                }
//                                valid = true;
                                board[x][j] = nextNumber;
                                board[i][j] = 0;
                                break;
                            }
                            i--;
                        }
                        continue;
                    }
                    //on a zero and number is above
                    valid = true;
                    int i = x-2;
                    while (i >= 0) {
                        int up = board[i][j];
                        if (up != numberAbove && up != 0) {
                            break;
                        }
                        if (up == numberAbove) {
                            board[i][j] = 0;
                            numberAbove = numberAbove * 2;
                            break;
                        }
                        i--;
                    }
                    board[x][j] = numberAbove;
                    board[x-1][j] = 0;
                } else {
                    // on a number
                    int numberAbove = board[x-1][j];
                    if (numberAbove == 0) {
                        // on a number and number above is zero
                        if (x-1 == 0) continue;
                        int i = x-1;
                        while (i >= 0) {
                            int nextNumber = board[i][j];
                            // next number is different
                            if (nextNumber != currentNumber && nextNumber != 0) {
                                break;
                            } else if (nextNumber == currentNumber) {
                                board[x][j] = currentNumber * 2;
                                board[i][j] = 0;
                                valid = true;
                                break;
                            }
                            i--;
                        }
                    } else if (numberAbove == currentNumber) {
                        board[x][j] = currentNumber * 2;
                        board[x-1][j] = 0;
                        valid = true;
                    }
                }

            }
        }
        calculateFreeSpaces();
       return valid;
    }

    boolean moveUp() {
        board = reverseColumns(board);
        boolean valid = moveDown();
        board = reverseColumns(board);
        calculateFreeSpaces();
        return valid;
    }

    boolean moveLeft() {
        board = transpose(board);
        board = reverseColumns(board);
        boolean valid = moveDown();
        board = reverseColumns(board);
        board = transpose(board);
        calculateFreeSpaces();
        return valid;
    }

    boolean moveRight() {
        board = transpose(board);
        boolean valid = moveDown();
        board = transpose(board);
        calculateFreeSpaces();
        return valid;
    }

    boolean moveDown(int[][] b) {
        int[][] board = new int[boardSize][boardSize];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                board[i][j] = b[i][j];
            }
        }
        boolean valid = false;
        // for each column
        for (int j = 0; j < boardSize; j++) {
            for (int x = boardSize - 1; x > 0; x--) {
                int currentNumber = board[x][j];
                // current position
                if (currentNumber == 0) {
                    // on a zero
                    int numberAbove = board[x-1][j];
                    if (numberAbove == 0) {
                        // on a zero and number above is zero
                        if (x-1 == 0) continue;
                        int i = x-1;
                        while (i >= 0) {
                            int nextNumber = board[i][j];
                            // next number is different
                            if (nextNumber != 0) {
                                valid = true;
                                int k = i - 1;
                                while (k >= 0) {
                                    int up = board[k][j];
                                    if (up != nextNumber && up != 0) break;
                                    if (up == nextNumber) {
                                        nextNumber = nextNumber * 2;
                                        board[k][j] = 0;
                                    }
                                    k--;
                                }
//                                valid = true;
                                board[x][j] = nextNumber;
                                board[i][j] = 0;
                                break;
                            }
                            i--;
                        }
                        continue;
                    }
                    //on a zero and number is above
                    int i = x-2;
                    while (i >= 0) {
                        int up = board[i][j];
                        if (up != numberAbove && up != 0) {
                            break;
                        }
                        if (up == numberAbove) {
                            board[i][j] = 0;
                            numberAbove = numberAbove * 2;
                            break;
                        }
                        i--;
                    }
                    valid = true;
                    board[x][j] = numberAbove;
                    board[x-1][j] = 0;
                } else {
                    // on a number
                    int numberAbove = board[x-1][j];
                    if (numberAbove == 0) {
                        // on a number and number above is zero
                        if (x-1 == 0) continue;
                        int i = x-1;
                        while (i >= 0) {
                            int nextNumber = board[i][j];
                            // next number is different
                            if (nextNumber != currentNumber && nextNumber != 0) {
                                break;
                            } else if (nextNumber == currentNumber) {
                                board[x][j] = currentNumber * 2;
                                board[i][j] = 0;
                                valid = true;
                                break;
                            }
                            i--;
                        }
                    } else if (numberAbove == currentNumber) {
                        board[x][j] = currentNumber * 2;
                        board[x-1][j] = 0;
                        valid = true;
                    }
                }

            }
        }
        calculateFreeSpaces();
        return valid;
    }

    boolean moveUp(int[][] board) {
        board = reverseColumns(board);
        boolean valid = moveDown(board);
        board = reverseColumns(board);
        calculateFreeSpaces();
        return valid;
    }

    boolean moveLeft(int[][] board) {
        board = transpose(board);
        board = reverseColumns(board);
        boolean valid = moveDown(board);
        board = reverseColumns(board);
        board = transpose(board);
        calculateFreeSpaces();
        return valid;
    }

    boolean moveRight(int[][] board) {
        board = transpose(board);
        boolean valid = moveDown(board);
        board = transpose(board);
        calculateFreeSpaces();
        return valid;
    }

    boolean isBoardValid(int[][] board) {
        return moveUp(board)
                ||moveDown(board)
                ||moveLeft(board)
                || moveRight(board);
    }


    void calculateFreeSpaces() {
        freeSpaces.clear();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0 ; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    Point p = new Point();
                    p.i = i;
                    p.j = j;
                    freeSpaces.add(p);
                }
            }
        }
    }
    void addRandom() {
        if (freeSpaces.size() != 0) {
            int idx = random.nextInt(freeSpaces.size());
            Point p = freeSpaces.get(idx);
            board[p.i][p.j] = Math.random() < .9 ? 2 : 4;
            freeSpaces.remove(p);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (isBoardValid(board)) {
            boolean moveValid = false;
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
                moveValid = moveUp();

                printBoard();
            } else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                moveValid = moveLeft();

                printBoard();
            } else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
                moveValid = moveDown();
                System.out.println("MoveValid is equal to " + moveValid);

                printBoard();
            } else if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                moveValid = moveRight();

                printBoard();
            }
            if (!moveValid) {
                System.out.println("Invalid move");
            } else {
                addRandom();
                grid.grid = board;
                grid.repaint();
            }
        System.out.println(e.getKeyChar());
    } else {
            grid.loss = true;
            grid.repaint();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
class Point {
    int i;
    int j;
    void print() {
        System.out.println("(" + i + ", " + j + ")");
    }
}