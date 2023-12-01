package battleship;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        BattleShip battleShip = new BattleShip();
        showMenu(battleShip);
    }

    static void showMenu(BattleShip battleShip) {
        battleShip.printSea();
        battleShip.setAircraftCarrier();
        battleShip.printSea();
        battleShip.setBattleship();
        battleShip.printSea();
        battleShip.setSubmarine();
        battleShip.printSea();
        battleShip.setCruiser();
        battleShip.printSea();
        battleShip.setDestroyer();
        battleShip.printSea();
        System.out.print("The game starts!\n\n");
        battleShip.printSeaInFog();
        System.out.print("Take a shot!\n\n");
        battleShip.takeShot();
    }

    static String[] inputShip() {
        Scanner scanner = new Scanner(System.in);
        return new String[] {scanner.next(), scanner.next()};
    }
}

class BattleShip {
    private char[][] sea;
    char[][] seaCopy = new char[10][10];
    String[][] seaInFog = new String[11][11];
    private int i1;
    private int j1;
    private int i2;
    private int j2;
    private String ship;
    private String cells;
    final static String ALPHABET = "ABCDEFGHIJ";
    String[] coordinates;

    void seaCopy() {
        for (int i = 0; i < seaCopy.length; i++) {
            for (int j = 0; j < seaCopy[i].length; j++) {
                if (sea[i][j] == 'O') {
                    seaCopy[i][j] = '~';
                } else {
                    seaCopy[i][j] = sea[i][j];
                }
            }
        }
    }

    void printSeaCopy() {
        seaCopy();
        // Prints the top row of numbers
        System.out.print(" ");
        for (int j = 1; j <= sea.length; j++) {
            System.out.print(" " + j);
        }
        System.out.println();
        // Prints each row. The rows are labelled A to J.
        char letter = 'A';
        for (char[] row : sea) {
            System.out.print(letter++ + " ");
            for (int j = 0; j < sea.length; j++) {
                System.out.print(row[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    void takeShot() {
        Scanner scanner = new Scanner(System.in);
        int count = 17;
        while (true) {
            String shot = scanner.next();
            System.out.println();
            i1 = ALPHABET.indexOf(shot.charAt(0));
            j1 = Integer.parseInt(shot.substring(1)) - 1;
            if (!ALPHABET.contains(shot.substring(0, 1)) || j1 > 9) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            } else if (sea[i1][j1] == 'M' || sea[i1][j1] == 'X') {
                printSeaCopy();
                System.out.println("Please enter a new coordinate:\n");
            } else if (sea[i1][j1] == '~') {
                sea[i1][j1] = 'M';
                printSeaCopy();
                System.out.println("You missed! Try again:\n");
            } else if (sea[i1][j1] == 'O') {
                sea[i1][j1] = 'X';
                count--;
                printSeaCopy();
                if (count == 0) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    return;
                } else if (count != 0) {
                    boolean isSank = false;
                    for (int i = i1; i <= i2; i++) {
                        for (int j = j1; j <= j2; j++) {
                            if (sea[i][j] == 'X') {
                                isSank = true;
                            }
                        }
                    }
                    if (isSank) {
                        System.out.println("You sank a ship! Specify a new target:\n");
                    } else {
                        System.out.println("You hit a ship! Try again:\n");
                    }
                }
            }
        }
    }

    void setSeaInFog() {
        String[] r = {" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 0; i < seaInFog.length; i++) {
            for (int j = 0; j < seaInFog.length; j++) {
                seaInFog[i][j] = "~";
                if (i == 0 && j == 0) {
                    seaInFog[0][0] = " ";
                } else if (i == 0) {
                    seaInFog[i][j] = Integer.toString(j);
                } else if (j == 0) {
                    seaInFog[i][j] = r[i];
                }
            }
        }
    }

    void markWithM() {
        if (sea[i1][j1] == 'M') {
            seaInFog[i1 + 1][j1 + 1] = "M";
        }
    }

    void markWithX() {
        if (sea[i1][j1] == 'X') {
            seaInFog[i1 + 1][j1 + 1] = "X";
        }
    }

    void printSeaInFog() {
        setSeaInFog();
        markWithM();
        markWithX();
        for (int i = 0; i < seaInFog.length; i++) {
            for (int j = 0; j < seaInFog.length; j++) {
                System.out.print(seaInFog[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Creates the default sea by calling the method setSea
     */
    BattleShip() {
        setSea();
    }

    void setSea() {
        // Creates default sea
        this.sea = new char[10][10];
        for (char[] row : this.sea) {
            Arrays.fill(row, '~');
        }
    }

    /**
     * Prints the sea with any ships that have been added
     */
    void printSea() {
        // Prints the top row of numbers
        System.out.print(" ");
        for (int j = 1; j <= sea.length; j++) {
            System.out.print(" " + j);
        }
        System.out.println();
        // Prints each row. The rows are labelled A to J.
        char letter = 'A';
        for (char[] row : sea) {
            System.out.print(letter++ + " ");
            for (int j = 0; j < sea.length; j++) {
                System.out.print(row[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Adds the coordinates for the Aircraft Carrier to the sea.
     */
    void setAircraftCarrier() {
        ship = "Aircraft Carrier";
        cells = "5";
        checkShip();
    }

    /**
     * Adds the coordinates for the Battleship to the sea.
     */
    void setBattleship() {
        ship = "Battleship";
        cells = "4";
        checkShip();
    }

    /**
     * Adds the coordinates for the Submarine to the sea.
     */
    void setSubmarine() {
        ship = "Submarine";
        cells = "3";
        checkShip();
    }

    /**
     * Adds the coordinates for the Cruiser to the sea.
     */
    void setCruiser() {
        ship = "Cruiser";
        cells = "3";
        checkShip();
    }

    /**
     * Adds the coordinates for the Destroyer to the sea.
     */
    void setDestroyer() {
        ship = "Destroyer";
        cells = "2";
        checkShip();
    }

    /**
     * Inputs the coordinates and checks if the coordinates are valid.
     * If valid, then ships are added to the sea.
     *
     * @exception RuntimeException If RuntimeException has been thrown by extractCoordinates method
     */
    private void checkShip() {
        System.out.printf("Enter the coordinates of the %s (%s cells): ", ship, cells);
        boolean correct = false;

        while (!correct) {
            // Inputs coordinates
            String[] coordinates = Main.inputShip();
            try {
                if (coordinates.length != 2) {
                    System.out.print("Error! The coordinates are in the incorrect format! Try again: ");
                    continue;
                }
                extractCoordinates(coordinates);
            } catch (RuntimeException e) {
                System.out.print(e.getMessage());
                continue;
            }
            // Checks for if ship is either horizontal or vertical, has correct length and
            // if there are any ships nearby
            correct = checkShipOrientation() && checkShipLength() && checkShipsNearby();

            if (correct) {
                addShipToSea();
            }
        }
    }

    private void extractCoordinates(String[] coordinates) {
        // Represents the row letter position of the 1st coordinate in reference to the char[][] sea field
        i1 = ALPHABET.indexOf(coordinates[0].charAt(0));
        // Represents the column number position of the 1st coordinate in reference to the char[][] sea field
        j1 = Integer.parseInt(coordinates[0].substring(1)) - 1;
        // Represents the row letter position of the 2nd coordinate in reference to the char[][] sea field
        i2 = ALPHABET.indexOf(coordinates[1].charAt(0));
        // Represents the column number position of the 2nd coordinate in reference to the char[][] sea field
        j2 = Integer.parseInt(coordinates[1].substring(1)) - 1;

        // Orders the coordinates in ascending order
        int temp = i1;
        if (i1 > i2) {
            i1 = i2;
            i2 = temp;
        }

        temp = j1;
        if (j1 > j2) {
            j1 = j2;
            j2 = temp;
        }

        if (i1 == -1 || i2 == -1) {
            throw new RuntimeException("Error! Enter the row letters in the correct format: ");
        } else if (j1 < 0 || j1 > 9 || j2 > 9) {
            throw new RuntimeException("Error! Ensure the column number is between 1 and 10: ");
        }
    }

    private boolean checkShipOrientation() {
        if (i1 != i2 && j1 != j2) {
            System.out.print("Error! Wrong ship location! Try again: \n");
            return false;
        }
        return true;
    }

    private boolean checkShipLength() {
        int lengthShip = Math.max((Math.abs(j2 - j1) + 1), Math.abs((i2 - i1) + 1));
        if (!Objects.equals(String.valueOf(lengthShip), cells)) {
            System.out.printf("Error! Wrong length of the %s! It should be length %s. Try again: \n", ship, cells);
            return false;
        }
        return true;
    }

    private boolean checkShipsNearby() {
        for (int i = i1 - 1; i <= i2 + 1; i++) {
            for (int j = j1 - 1; j <= j2 + 1; j++) {
                if (i >= 0 && i < sea.length && j >= 0 && j < sea.length) {
                    if (sea[i][j] == 'O') {
                        System.out.print("Error! You placed it too close to another one. Try again:\n");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void addShipToSea() {
        for (int i = i1; i <= i2; i++) {
            for (int j = j1; j <= j2; j++) {
                sea[i][j] = 'O';
            }
        }
    }
}

