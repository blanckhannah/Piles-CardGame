import java.util.*;
public class Main {
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String YELLOW = "\u001b[33m";
    public static final String RESET = "\u001B[0m";

    public static List<Integer> cardDeck = new ArrayList<>();
    public static List<Integer> playerHand = new ArrayList<>();
    public static List<Integer> computerHand = new ArrayList<>();
    public static Stack <Integer> playerStack = new Stack<>();
    public static Stack <Integer> computerStack = new Stack<>();
    public static Stack<Integer> pile1 = new Stack<>();
    public static Stack<Integer> pile2 = new Stack<>();
    public static Stack<Integer> pile3 = new Stack<>();
    public static final int LAST_NUMBER = 12;
    public static final int HAND_SIZE = 7;

    public static void main(String[] args) throws InterruptedException {
        cardDeck = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        getPlayerHand();
        getComputerHand();
        getPlayerStack();
        getComputerStack();

        pile1 = new Stack<Integer>();
        pile1.add(0);
        pile2 = new Stack<Integer>();
        pile2.add(0);
        pile3 = new Stack<Integer>();
        pile3.add(0);

        welcome();

        // playing:
        while (playerStack.size() > 0 && computerStack.size() > 0) {
            confirm7Cards();
            resetPiles();
            //User's turn:
            printPlayerInformation();
            String userHasStartingOne = input.nextLine();
            if (userHasStartingOne.equals("Y")) {
                System.out.println("Which number(s) do you want to play?");
                String userInput = input.nextLine();
                String[] userPlayingNumbers = userInput.split(" ");
                for (String playingNumber : userPlayingNumbers) {
                    int userPlayingNumber = Integer.parseInt(playingNumber);
                    for (int i = 0; i < playerHand.size(); i++) {
                        if (!playerStack.isEmpty() && playerStack.peek() == userPlayingNumber) {
                            if (userPlayingNumber == pile1.peek() + 1) {
                                pile1.push(playerStack.peek());
                                playerStack.pop();
                            } else if (userPlayingNumber == pile2.peek() + 1) {
                                pile2.push(playerStack.peek());
                                playerStack.pop();
                            } else if (userPlayingNumber == pile3.peek() + 1) {
                                pile3.push(playerStack.peek());
                                playerStack.pop();
                            }
                        }
                        if (playerHand.get(i) == userPlayingNumber) {
                            if (userPlayingNumber == pile1.peek() + 1) {
                                pile1.push(playerHand.get(i));
                                playerHand.remove(i);
                            } else if (userPlayingNumber == pile2.peek() + 1) {
                                pile2.push(playerHand.get(i));
                                playerHand.remove(i);
                            } else if (userPlayingNumber == pile3.peek() + 1) {
                                pile3.push(playerHand.get(i));
                                playerHand.remove(i);
                            }
                        }
                        resetPiles();
                    }
                }
                if (playerStack.isEmpty()) {
                    break;
                }
                printPiles();
                if (playerHand.isEmpty()) {
                    getPlayerHand();
                }
                playerDiscard();
            } else {
                playerDiscard();
                playerHand.add(getCard());
                }

            //Computer's turn:
            int handSize = computerHand.size();
            if (!computerStack.isEmpty()) {
                printComputersInfo();
            }
            for (int i = 0; i < computerHand.size(); i++) {
                if (computerStack.isEmpty()) {
                    break;
                } else if (computerStack.peek() == pile1.peek() + 1) {
                    pile1.push(computerStack.peek());
                    computerStack.pop();
                } else if (computerStack.peek() == pile2.peek() + 1) {
                    pile2.push(computerStack.peek());
                    computerStack.pop();
                } else if (computerStack.peek() == pile3.peek() + 1) {
                    pile3.push(computerStack.peek());
                    computerStack.pop();
                }
                if (computerHand.get(i) == pile1.peek() + 1) {
                    pile1.push(computerHand.get(i));
                    computerHand.remove(i);
                } else if (computerHand.get(i) == pile2.peek() + 1) {
                    pile2.push(computerHand.get(i));
                    computerHand.remove(i);
                } else if (computerHand.get(i) == pile3.peek() + 1) {
                    pile3.push(computerHand.get(i));
                    computerHand.remove(i);
                }
            }
            computerDiscard();
            if (computerHand.size() == handSize) {
                computerHand.add(getCard());
            }
            resetPiles();
            printPiles();
            System.out.println();
            System.out.println();
        }
        endGame();
    }

    public static void welcome() {
        System.out.println(YELLOW + "Hello! Let's play Piles!" + YELLOW);
        System.out.println();
        System.out.println("Goal: Be the first to get rid of all 5 cards in your Stack");
        System.out.println("Directions:");
        System.out.println("1. Use the cards in your hand to help you get rid of your Stack");
        System.out.println("2. Piles can only be added to in numerical order, increasing by 1");
        System.out.println("3. Piles restart at 12");
        System.out.println("4. If you have multiple cards to play, enter them, separated by spaces");
        System.out.println(RESET);
    }
    public static int getCard(){
        cardDeck.add(1); cardDeck.add(2); cardDeck.add(3); cardDeck.add(4); cardDeck.add(5); cardDeck.add(6);
        cardDeck.add(7); cardDeck.add(8); cardDeck.add(9); cardDeck.add(10); cardDeck.add(11); cardDeck.add(12);
        Random random = new Random();
        int randomIndex = random.nextInt(cardDeck.size());
        return cardDeck.get(randomIndex);
    }
    public static void getPlayerHand() {
        playerHand.add(getCard());
        playerHand.add(getCard());
        playerHand.add(getCard());
        playerHand.add(getCard());
        playerHand.add(getCard());
    }
    public static void getComputerHand(){
        computerHand.add(getCard());
        computerHand.add(getCard());
        computerHand.add(getCard());
        computerHand.add(getCard());
        computerHand.add(getCard());
    }
    public static void getPlayerStack() {
        playerStack.add(getCard());
        playerStack.add(getCard());
        playerStack.add(getCard());
        playerStack.add(getCard());
        playerStack.add(getCard());
    }
    public static void getComputerStack() {
        computerStack.add(getCard());
        computerStack.add(getCard());
        computerStack.add(getCard());
        computerStack.add(getCard());
        computerStack.add(getCard());
    }
    public static void printPlayerInformation() {
        System.out.println("Here is your hand: " + BLUE + playerHand + BLUE + RESET + "   Your Stack: " + PURPLE + playerStack.peek() + PURPLE);
        System.out.println(RESET + "Pile One: " + RED + pile1.peek() + RESET + "   Pile Two: " + RED + pile2.peek() + RESET + "   Pile Three: " + RED + pile3.peek() + RESET);
        System.out.println();
        System.out.println(RESET + "Your turn! Do you have a number to add to a pile? (Y or N)" + RESET);
    }
    public static void  printPiles() {
        System.out.println(RESET + "Pile One: " + RED + pile1.peek() + RESET + "   Pile Two: " + RED + pile2.peek() + RESET + "   Pile Three: " + RED + pile3.peek() + RESET);
        System.out.println();
        System.out.println();
    }
    public static void printComputersInfo() throws InterruptedException {
        System.out.println(RESET + "Computer's Turn!    Computer's Stack: " + PURPLE + computerStack.peek() + PURPLE);
        Thread.sleep(2000);
    }
    public static void confirm7Cards() {
        while (playerHand.size() < HAND_SIZE) {
            playerHand.add(getCard());
        }
        while (computerHand.size() < HAND_SIZE) {
            computerHand.add(getCard());
        }
    }
    public static void resetPiles() {
        if (!pile1.isEmpty() && pile1.peek() == LAST_NUMBER) {
            pile1.add(0);
        }
        if (!pile2.isEmpty() && pile2.peek() == LAST_NUMBER) {
            pile2.add(0);
        }
        if (!pile3.isEmpty() && pile3.peek() == LAST_NUMBER) {
            pile3.add(0);
        }
    }
    public static void playerDiscard() {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose a card to discard"+ BLUE + playerHand + BLUE + RESET);
        while(true) {
            try {
                String userInput = input.nextLine();
                int playerDiscard = Integer.parseInt(userInput);
                if (playerHand.contains(playerDiscard)) {
                    playerHand.remove((Integer) playerDiscard);
                    break;
                } else {
                    System.out.println("Invalid number. Please choose a card to discard");
                }
            } catch (NumberFormatException error) {
                System.out.println("Invalid number. Please choose a card to discard");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }
    public static void computerDiscard() {
        if (!computerHand.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(computerHand.size());
            computerHand.remove(randomIndex);
        }
    }
    public static void endGame() {
        if (playerStack.isEmpty()) {
            System.out.println(YELLOW + "Way to go! You got rid of your stack before your opponent!" + YELLOW);
        } else {
            System.out.println(YELLOW + "Uh oh! The computer got through their stack first! Play again sometime!" + YELLOW);
        }
    }
}