
public class Sudoko implements SudokoSolver {

    private static int[][] board;

    public Sudoko() {
        board = new int[9][9];
        setBoard(board);
    }

    /**
     * Set sudoku board, numbers 1-9 are fixed values, 0 is unsolved.
     * 
     * @param board a board to copy values from
     * @throws IllegalArgumentException if board is invalid, e.g. not 9x9
     */
    @Override
    public void setBoard(int[][] aboard) {
        board = aboard;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * Get a copy of the sudoku board
     */
    @Override
    public int[][] getBoard() {
        return Sudoko.board;
    }

    /**
     * Solve soduko
     * 
     * @return true if solution could be found
     */
    @Override
    public boolean solve() {
        /* return solveBoard(Sudoko.board); */

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] != 0) {
                    if (!isLegal(row, col, get(row, col))) {
                        return false;
                    }
                }
            }
        }
        return solveBoard(0, 0);
    }

    private boolean solveBoard(int row, int col) {
        if (col == 9) {
            col = 0;
            row++;
        }

        if (row == 9) {
            return true;
        }

        if (board[row][col] != 0) {
            return solveBoard(row, col + 1);
        }

        for (int i = 1; i <= 9; i++) {
            if (isLegal(row, col, i)) {
                set(row, col, i);

                if (solveBoard(row, col + 1)) {
                    return true;
                }
            }
        }

        /*
         * kommer den hela vägen ner
         * går det ej att lösa
         */
        return false;
    }

    /**
     * Check if digit is legal on the current board
     * 
     * @param row
     * @param col
     * @param nbr
     * @return true if legal
     */
    @Override
    public boolean isLegal(int row, int col, int nbr) {

        for (int i = 0; i < 9; i++) {
            if ((col != i && board[row][i] == nbr) || (row != i && board[i][col] == nbr)) {
                return false;
            }
        }

        int rowArea = row - row % 3;
        int colArea = col - col % 3;

        for (int i = rowArea; i < rowArea + 3; i++) {
            for (int j = colArea; j < colArea + 3; j++) {
                if ((board[i][j] == nbr) && ((i != row) && (j != col))) {
                    return false;
                }
            }
        }
        return true;

    }

    /*
     * private static boolean isRowLegal(int[][] board, int row, int col, int nbr) {
     * for (int i = 0; i < 9; i++) {
     * if (i != col) {
     * if (board[row][i] == nbr)
     * return false;
     * }
     * }
     * return true;
     * }
     * 
     * private static boolean isColLegal(int[][] board, int row, int col, int nbr) {
     * for (int i = 0; i < 9; i++) {
     * if (i != row) {
     * if (board[i][col] == nbr)
     * return false;
     * }
     * }
     * return true;
     * }
     * 
     * private static boolean isAreaLegal(int[][] board, int row, int col, int nbr)
     * {
     * int localBoxRow = row - row % 3;
     * int localBoxCol = col - col % 3;
     * 
     * for (int i = localBoxRow; i < localBoxRow + 3; i++) {
     * for (int j = localBoxCol; j < localBoxCol + 3; j++) {
     * if (board[i][j] == nbr)
     * return false;
     * }
     * }
     * return true;
     * }
     */

    /**
     * Get number on board
     * 
     * @param row
     * @param col
     * @return number on board
     */
    @Override
    public int get(int row, int col) {
        return board[row][col];
    }

    /**
     * Set number on board, numbers 1-9 are fixed values, 0 is unsolved.
     * 
     * @param row
     * @param col
     * @param nbr
     */
    public void set(int row, int col, int nbr) {
        Sudoko.board[row][col] = nbr;
    }

    /**
     * Clear the board
     */
    public void clear() {
        for (int i = 8; i > 0; i--) {
            for (int j = 8; i > 0; i--) {
                set(j, i, 0);
            }
        }
    }

}
