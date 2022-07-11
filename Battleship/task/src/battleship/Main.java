package battleship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HashMap<String, Integer> rowConverter = new HashMap<>();
        rowConverter.put("A", 0);
        rowConverter.put("B", 1);
        rowConverter.put("C", 2);
        rowConverter.put("D", 3);
        rowConverter.put("E", 4);
        rowConverter.put("F", 5);
        rowConverter.put("G", 6);
        rowConverter.put("H", 7);
        rowConverter.put("I", 8);
        rowConverter.put("J", 9);

        //Initialize the fields with the fog of war
        String[][] fieldPlayer1 = new String[10][10];
        for (String[] s : fieldPlayer1) {
            Arrays.fill(s, "~");
        }
        String[][] fieldPlayer2 = new String[10][10];
        for (String[] s : fieldPlayer2) {
            Arrays.fill(s, "~");
        }

        //The players place their ships, first all ships of player1 then all ships of player 2
        for (int n = 0; n < 2; n++) {

            System.out.println("Player " + (n + 1) + ", place your ships on the game field");
            System.out.println();
            //Print the array
            if (n == 0) {
                printArray(fieldPlayer1);
            } else {
                printArray(fieldPlayer2);
            }

            //Initialize the ships names and length
            HashMap<String, Integer> shipsInfo = new HashMap<>();
            String[] shipsNames = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
            shipsInfo.put("Aircraft Carrier", 5);
            shipsInfo.put("Battleship", 4);
            shipsInfo.put("Submarine", 3);
            shipsInfo.put("Cruiser", 3);
            shipsInfo.put("Destroyer", 2);

            System.out.println();

            //Place all the ships one by one
            for (int i = 0; i < shipsNames.length; i++) {
                boolean jump = false;
                boolean isValid = false;
                System.out.println("Enter the coordinates of the " + shipsNames[i] + " (" + shipsInfo.get(shipsNames[i]) + " cells):");

                while (!isValid) {
                    System.out.println();
                    System.out.print("> ");
                    //Enter the coordinates
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();

                    //Check the input and place the ship
                    int row1 = 0;
                    int row2 = 0;
                    int col1 = 0;
                    int col2 = 0;
                    if (input != null && input.length() >= 4) {
                        input = input.toUpperCase();
                        if (input.contains(" ")) {
                            //the input is made by two words, therefore remove extra spaces and extract shipStart and shipEnd
                            input = input.replaceAll("\\s+", " ").trim();
                            String shipStart = "";
                            for (int j = 0; j < input.length(); j++) {
                                if (input.charAt(j) != ' ') {
                                    shipStart += input.charAt(j);
                                } else {
                                    break;
                                }
                            }
                            String shipEnd = input.substring(shipStart.length() + 1);
                            System.out.println("You entered row " + shipStart + " and col " + shipEnd);

                            final String nums = "0123456789";
                            if (nums.contains((shipStart.charAt(0)) + "")) {
                                System.out.println("Error! The first coordinate starts with a number, should start with a letter! Try again:");
                            } else if (nums.contains((shipEnd.charAt(0)) + "")) {
                                System.out.println("Error! The second coordinate starts with a number, should start with a letter! Try again:");
                            } else {
                                //shipStart and shipEnd starts with a letter
                                if (shipStart.charAt(0) < 'A' || shipStart.charAt(0) > 'J' || shipEnd.charAt(0) < 'A' || shipEnd.charAt(0) > 'J') {
                                    System.out.println("Error! The ship cannot be placed out of the field! Try again:");
                                } else {
                                    //shipStart and shipEnd have correct letters, check the row (AA1 or A1A or AAA are not valid)
                                    if (!nums.contains((shipStart.charAt(1)) + "") || !nums.contains((shipEnd.charAt(1)) + "")) {
                                        //the second char is not a number (AA1)
                                        System.out.println("Error! The coordinates are not valid! Try again:");
                                    } else if (shipStart.charAt(1) == '0' || shipEnd.charAt(1) == '0' || (shipStart.length() > 2 && shipStart.charAt(2) != '0') || (shipEnd.length() > 2 && shipEnd.charAt(2) != '0')) {
                                        //the second char is 0 or the third char is not a number (A1A)
                                        System.out.println("Error! The coordinates are not valid! Try again:");
                                    } else {
                                        //the coordinates are well formatted and inside the field, check cell availabilities
                                        row1 = rowConverter.get(shipStart.charAt(0) + "");
                                        col1 = Integer.parseInt(shipStart.substring(1)) - 1;
                                        row2 = rowConverter.get(shipEnd.charAt(0) + "");
                                        col2 = Integer.parseInt(shipEnd.substring(1)) - 1;
                                    }
                                }
                            }
                        } else {
                            System.out.println("Error! I need 2 coordinates separated by a space! Try again:");
                            jump = true;
                        }
                    } else {
                        System.out.println("Error! I need 2 coordinates separated by a space! Try again:");
                        jump = true;
                    }
                    if (!jump) {
                        if (n == 0) {
                            if (checkInput(fieldPlayer1, row1, row2, col1, col2, shipsNames[i], shipsInfo.get(shipsNames[i]))) {
                                isValid = true;
                            }
                        } else {
                            if (checkInput(fieldPlayer2, row1, row2, col1, col2, shipsNames[i], shipsInfo.get(shipsNames[i]))) {
                                isValid = true;
                            }
                        }
                    }
                    if (isValid) {
                        if (n == 0) {
                            placeShip(fieldPlayer1, row1, col1, row2, col2);
                            System.out.println();
                            printArray(fieldPlayer1);
                            System.out.println();
                        } else {
                            placeShip(fieldPlayer2, row1, col1, row2, col2);
                            System.out.println();
                            printArray(fieldPlayer2);
                            System.out.println();
                        }
                    }
                }
            }

            //Press Enter to pass the move to player2
            pressEnter();
        }

        //The game starts

        //Initialize a new array with the fog
        String[][] fieldCopyPlayer1 = new String[10][10];
        for (String[] s : fieldCopyPlayer1) {
            Arrays.fill(s, "~");
        }
        String[][] fieldCopyPlayer2 = new String[10][10];
        for (String[] s : fieldCopyPlayer2) {
            Arrays.fill(s, "~");
        }

        boolean win = false;
        int count = 0;
        while (!win) {
            count++;
            //Show the state of the battle
            if (count % 2 == 0) {
                printArray(fieldCopyPlayer1);
                System.out.println("---------------------");
                printArray(fieldPlayer2);
                System.out.println();
            } else {
                printArray(fieldCopyPlayer2);
                System.out.println("---------------------");
                printArray(fieldPlayer1);
                System.out.println();
            }

            //Enter a cell to shoot
            int player = count % 2 == 0 ? 2 : 1;
            System.out.println("Player " + player + ", it's your turn:");
            System.out.println();

            //Check the input and update both fields
            boolean jump = false;
            boolean valid = false;
            while (!valid) {
                int row = 0;
                int col = 0;
                try {
                    System.out.print("> ");
                    String input = "";
                    Scanner sc = new Scanner(System.in);
                    input = sc.nextLine().replaceAll("\\s+", "").trim().toUpperCase();
                    row = rowConverter.get(input.charAt(0) + "");
                    col = Integer.parseInt(input.substring(1)) - 1;
                    //update both fields
                    if (player == 2) {
                        fieldPlayer1[row][col] = Objects.equals(fieldPlayer1[row][col], "O") ? "X" : fieldPlayer1[row][col].equals("X") ? "X" : "M";
                        fieldCopyPlayer1[row][col] = fieldPlayer1[row][col];
                    } else {
                        fieldPlayer2[row][col] = Objects.equals(fieldPlayer2[row][col], "O") ? "X" : fieldPlayer2[row][col].equals("X") ? "X" : "M";
                        fieldCopyPlayer2[row][col] = fieldPlayer2[row][col];
                    }
                    valid = true;
                    jump = false;
                } catch (Exception e) {
                    System.out.println();
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    System.out.println();
                    jump = true;
                }
                //first show the fieldCopy
                if (!jump) {
                    System.out.println();

                    if (player == 2) {
                        if (fieldCopyPlayer1[row][col].equals("X")) {
                            if (checkShipSank(fieldPlayer1, row, col) && checkWin(fieldPlayer1)) {
                                //the game is finished
                                System.out.println("You sank the last ship. You won. Congratulations!");
                                win = true;
                            } else if (checkShipSank(fieldPlayer1, row, col)) {
                                System.out.println("You sank a ship!");
                                pressEnter();
                                System.out.println();
                                win = false;
                            } else {
                                System.out.println("You hit a ship!");
                                pressEnter();
                                System.out.println();
                                win = false;
                            }
                        } else {
                            System.out.println("You missed.");
                            pressEnter();
                            System.out.println();
                            win = false;
                        }
                    } else {
                        if (fieldCopyPlayer2[row][col].equals("X")) {
                            if (checkShipSank(fieldPlayer2, row, col) && checkWin(fieldPlayer2)) {
                                //the game is finished
                                System.out.println("You sank the last ship. You won. Congratulation!");
                                win = true;
                            } else if (checkShipSank(fieldPlayer2, row, col)) {
                                System.out.println("You sank a ship!");
                                pressEnter();
                                System.out.println();
                                win = false;
                            } else {
                                System.out.println("You hit a ship!");
                                pressEnter();
                                System.out.println();
                                win = false;
                            }
                        } else {
                            System.out.println("You missed.");
                            pressEnter();
                            System.out.println();
                            win = false;
                        }
                    }
                }
            }
        }
    }

    public static void printArray(String[][] field) {
        String firstRow = "  1 2 3 4 5 6 7 8 9 10";
        String firstColumn = "ABCDEFGHIJ";
        System.out.println(firstRow);
        for (int i = 0; i < field.length; i++) {
            System.out.print(firstColumn.charAt(i) + " ");
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void placeShip(String[][] field, int row1, int col1, int row2, int col2) {
        if (row1 == row2) {
            //the ship must be placed in a row
            int startCol = Math.min(col1, col2);
            int endCol = Math.max(col1, col2);
            for (int i = startCol; i <= endCol; i++) {
                field[row1][i] = "O";
            }
        } else if (col1 == col2) {
            //the ship must be placed in a col
            int startRow = Math.min(row1, row2);
            int endRow = Math.max(row1, row2);
            for (int i = startRow; i <= endRow; i++) {
                field[i][col1] = "O";
            }
        } else {
            System.out.println("Error!");
        }
    }

    public static boolean checkInput(String[][] field, int row1, int row2, int col1, int col2, String shipName, int shipLength) {
        boolean valid = false;
        if (col1 != col2 && row1 != row2) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        } else if (row1 == row2) {
            /* the ship is placed in a row */

            int startCol = Math.min(col1, col2);
            int endCol = Math.max(col1, col2);
            if ((endCol + 1) - (startCol + 1) + 1 == shipLength) {
                //the length of the ship is correct

                for (int i = startCol; i <= startCol; i++) {
                    if (!"~".equals(field[row1][i])) {
                        System.out.println("Error! Not all the cells are free! Try again:");
                        valid = false;
                        break;
                    } else {
                        valid = true;
                    }
                }
                if (valid) {
                    if (row1 == 0 && startCol == 0) {
                        //the ship starts from [0, 0] - check the right cell and the next row
                        valid = checkRightCell(field, row1, endCol) && checkNextRow(field, row1, endCol);
                    }
                    if (row1 == 0 && startCol > 0 && endCol < 9) {
                        //the ship is in the middle of the row - check close cells
                        //check the next cols
                        valid = checkRightCell(field, row1, endCol) && checkLeftCell(field, row1, startCol) && checkNextRow(field, row1, endCol);
                    }
                    if (row1 == 0 && endCol == 9) {
                        //the ship is at the end of the row - check the left cell and next row
                        valid = checkLeftCell(field, row1, startCol) && checkNextRow(field, row1, endCol);
                    }
                    if ((row1 > 1 && row1 < 9) && startCol == 0) {
                        //the ship is at the start of the row, middle of the col - don't check left
                        valid = checkPreviousRow(field, row1, endCol) && checkNextRow(field, row1, endCol) && checkRightCell(field, row1, endCol);
                    }
                    if ((row1 > 1 && row1 < 9) && (startCol > 0 && endCol < 9)) {
                        //the ship is in the middle of the field - check every side
                        valid = checkPreviousRow(field, row1, endCol) && checkNextRow(field, row1, endCol) && checkRightCell(field, row1, endCol) && checkLeftCell(field, row1, startCol);
                    }
                    if ((row1 > 1 && row1 < 9) && (startCol < 9 && endCol == 9)) {
                        //the ship is at the end of the row, middle of the col - don't check the right cell
                        valid = checkPreviousRow(field, row1, endCol) && checkNextRow(field, row1, endCol) && checkLeftCell(field, row1, startCol);
                    }
                    if (row1 == 9 && startCol == 0) {
                        //the ship is at [9, 0] - check the previous row and the right cell
                        valid = checkPreviousRow(field, row1, endCol) && checkRightCell(field, row1, endCol);
                    }
                    if (row1 == 9 && (startCol < 9 && endCol < 9)) {
                        //the ship is at the last row, middle - don't check the next row
                        valid = checkPreviousRow(field, row1, endCol) && checkRightCell(field, row1, endCol) && checkLeftCell(field, row1, startCol);
                    }
                    if (row1 == 9 && (startCol < 9 && endCol == 9)) {
                        //the ship is at the last corner - check the left cell and the previous row
                        valid = checkLeftCell(field, row1, startCol) && checkPreviousRow(field, row1, endCol);
                    }
                }
                if (!valid) {
                    //the coordinates are not valid
                    System.out.println("Error! You placed it too close to another one. Try again:");
                }
                return valid;
            } else {
                System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
                return false;
            }
        } else {
            /* the ship is placed in a col */

            int startRow = Math.min(row1, row2);
            int endRow = Math.max(row1, row2);

            if ((endRow + 1) - (startRow + 1) + 1 == shipLength) {
                //the length of the ship is correct

                for (int i = startRow; i <= startRow; i++) {
                    if (!"~".equals(field[i][col1])) {
                        System.out.println("Error! Not all the cells are free! Try again:");
                        valid = false;
                        break;
                    } else {
                        valid = true;
                    }
                }
                if (valid) {
                    if (col1 == 0 && startRow == 0) {
                        //the ship is at [0, 0] - check the next col and the down cell
                        valid = checkNextCol(field, startRow, endRow, col1) && checkDownCell(field, endRow, col1);
                    }
                    if (col1 == 0 && (startRow > 0 && startRow < 9)) {
                        //the ship is at the middle of the first col - don't check the previous col
                        valid = checkNextCol(field, startRow, endRow, col1) && checkUpCell(field, startRow, col1) && checkDownCell(field, endRow, col1);
                    }
                    if (col1 == 0 && startRow == 9) {
                        //the ship is at the end of the first col - check the up cell and the next col
                        valid = checkUpCell(field, startRow, col1) && checkNextCol(field, startRow, endRow, col1);
                    }
                    if ((col1 > 0 && col1 < 9) && startRow == 0) {
                        //the ship is at the middle of the first row - don't check the up cell
                        valid = checkNextCol(field, startRow, endRow, col1) && checkPreviousCol(field, startRow, endRow, col1) && checkDownCell(field, endRow, col1);
                    }
                    if ((col1 > 0 && col1 < 9) && (startRow > 0 && endRow < 9)) {
                        //the ship is at the middle of the field - check all the sides
                        valid = checkUpCell(field, startRow, col1) && checkNextCol(field, startRow, endRow, col1) && checkDownCell(field, endRow, col1) && checkPreviousCol(field, startRow, endRow, col1);
                    }
                    if ((col1 > 0 && col1 < 9) && endRow == 9) {
                        //the ship is at the middle of the last row - don't check the down cell
                        valid = checkUpCell(field, startRow, col1) && checkPreviousCol(field, startRow, endRow, col1) && checkNextCol(field, startRow, endRow, col1);
                    }
                    if (col1 == 9 && startRow == 9) {
                        //the ship is at the last col of the first row - check the previous col and the down cell
                        valid = checkPreviousCol(field, startRow, endRow, col1) && checkDownCell(field, endRow, col1);
                    }
                    if (col1 == 9 && (startRow > 0 && startRow < 9)) {
                        //the ship is at the last col of a middle row - don't check the next col
                        valid = checkUpCell(field, startRow, col1) && checkPreviousCol(field, startRow, endRow, col1) && checkDownCell(field, endRow, col1);
                    }
                    if (col1 == 9 && endRow == 9) {
                        //the ship is at the last corner - check the up cell and the previous col
                        valid = checkUpCell(field, startRow, col1) && checkPreviousCol(field, startRow, endRow, col1);
                    }
                }
                if (!valid) {
                    //the coordinates are not valid
                    System.out.println("Error! You placed it too close to another one. Try again:");
                }
                return valid;
            } else {
                System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
                return false;
            }
        }
    }

    public static boolean checkNextRow(String[][] field, int row1, int col2) {
        boolean valid = true;
        for (int i = row1 + 1; i <= col2; i++) {
            if (!"~".equals(field[row1][i])) {
                //System.out.println("Error! You placed it too close to another one. Try again:");
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean checkPreviousRow(String[][] field, int row1, int col2) {
        boolean valid = true;
        for (int i = row1 - 1; i <= col2; i++) {
            if (!"~".equals(field[row1][i])) {
                //System.out.println("Error! You placed it too close to another one. Try again:");
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean checkNextCol(String[][] field, int row1, int row2, int col1) {
        boolean valid = true;
        for (int i = row1; i <= row2; i++) {
            if (!"~".equals(field[i][col1 + 1])) {
                //System.out.println("Error! You placed it too close to another one. Try again:");
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean checkPreviousCol(String[][] field, int row1, int row2, int col1) {
        boolean valid = true;
        for (int i = row1; i <= row2; i++) {
            if (!"~".equals(field[i][col1 - 1])) {
                //System.out.println("Error! You placed it too close to another one. Try again:");
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean checkRightCell(String[][] field, int row1, int col2) {
        return "~".equals(field[row1][col2 + 1]);
    }

    public static boolean checkLeftCell(String[][] field, int row1, int col1) {
        return "~".equals(field[row1][col1 - 1]);
    }

    public static boolean checkUpCell(String[][] field, int row1, int col1) {
        return "~".equals(field[row1 - 1][col1]);
    }

    public static boolean checkDownCell(String[][] field, int row2, int col1) {
        return "~".equals(field[row2 + 1][col1]);
    }

    public static boolean checkShipSank(String[][] field, int row, int col) {
        boolean isLeftFull = false;
        boolean isRightFull = false;
        boolean isDownFull = false;
        boolean isUpFull = false;
        try {
            isUpFull = field[row - 1][col].equals("O");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            isDownFull = field[row + 1][col].equals("O");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            isLeftFull = field[row][col - 1].equals("O");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            isRightFull = field[row][col + 1].equals("O");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return !(isUpFull || isDownFull || isLeftFull || isRightFull);
    }

    public static boolean checkWin(String[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].equals("O")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pressEnter() {
        System.out.println("Press Enter and pass the move to another player");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        //Clear the screen
        clearScreen();
    }
}
