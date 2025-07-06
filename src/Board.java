class Board {
    int[][] board;
    int boardSize = 4;
    public static void main(String[] args) {
        int[][] board = new int[][]{{2,2,2,4},{8,4,32,8},{2,8,0,32},{0,0,0,64}};
        Board b = new Board();
        b.board = board;
        b.moveDown();
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
                                valid = true;
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
        return valid;
    }
}