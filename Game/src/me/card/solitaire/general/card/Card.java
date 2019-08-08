package me.card.solitaire.general.card;

public class Card {

    protected SuitType suit;
    protected CardType cardNo;
    protected boolean hidden;

    public Card(SuitType suit, CardType cardNo) {
        this.suit = suit;
        this.cardNo = cardNo;
    }

    public Card(SuitType suit, CardType cardNo, boolean hidden) {
        this.suit = suit;
        this.cardNo = cardNo;
        this.hidden = hidden;
    }

    public SuitType getSuit() {
        return suit;
    }

    public CardType getCardNo() {
        return cardNo;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isOppositeColor(Card card){
        return suit.isRed() ? card.getSuit().isBlack() : card.getSuit().isRed();
    }
}
