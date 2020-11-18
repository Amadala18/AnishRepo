package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Random;

public class Controller {

    public TextArea area;
    int nextCard;
    int pCard1;
    int pCard2;
    int dCard1;
    int dCard2;

    int cardTotal = 0;
    int dCardTotal = 0;

    final int BLACKJACK = 21;
    final int DEALER_THRESHOLD = 17;

    Random generateNumber = new Random();

    public void drawButton(ActionEvent event) {
        nextCard = generateNumber.nextInt(10) + 1;
        cardTotal += nextCard;
        area.appendText("Your new card is: " + nextCard + "\n");
        area.appendText("Your new total is: " + cardTotal+ "\n");

        if (cardTotal < BLACKJACK) {
            area.appendText("Would you like to draw another card?" + "\n");
            area.appendText("\n");
        } else if (cardTotal == BLACKJACK) {
            area.appendText("You got a BlackJack. You Win!" + "\n");
            area.appendText("\n");
        } else {
            area.appendText("You busted!" + "\n");
            area.appendText("\n");
        }
    }

    public void playBlackjack(ActionEvent event) {
        dCard1 = generateNumber.nextInt(10) + 1;
        dCard2 = generateNumber.nextInt(10) + 1;
        dCardTotal = dCard1 + dCard2;

        pCard1 = generateNumber.nextInt(10) + 1;
        pCard2 = generateNumber.nextInt(10) + 1;
        cardTotal = pCard1 + pCard2;

        if (dCardTotal == BLACKJACK) {
            area.appendText("Dealer first card is: " + dCard1 + "\n");
            area.appendText("Dealer second cards is: " + dCard2 + "\n");
            area.appendText("Dealer total is: " + dCardTotal + "\n");
            area.appendText("Dealer got Blackjack!!. You lost!" + "\n");
            area.appendText("\n");

        }

        area.appendText("One of the dealer's cards is: " + dCard1 + "\n");
        area.appendText("Your first card is: " + pCard1 + "\n");
        area.appendText("Your second card is: " + pCard2 + "\n");
        area.appendText("Your total card value is: " + cardTotal + "\n");
        area.appendText("\n");

    }

    public void standButton(ActionEvent event) {
        while (dCardTotal < DEALER_THRESHOLD) {
            nextCard = generateNumber.nextInt(10) + 1;
            dCardTotal += nextCard;
        }

        area.appendText("Dealer Total: " + dCardTotal + "\n");
        area.appendText("Player Total: " + cardTotal + "\n");

        if (dCardTotal > BLACKJACK) {
            area.appendText("The dealer busted, You win!" + "\n");
            area.appendText("\n");
        } else {
            if (dCardTotal == BLACKJACK) {
                area.appendText("Dealer got Blackjack!!. You lost!" + "\n");
                area.appendText("\n");
            } else if (dCardTotal > cardTotal) {
                area.appendText("Dealer Won!!" + "\n");
            } else if (dCardTotal == cardTotal) {
                area.appendText("You are pushed!!" + "\n");
                area.appendText("\n");
            } else {
                area.appendText("You Won!!" + "\n");
                area.appendText("\n");
            }
        }
    }
}

