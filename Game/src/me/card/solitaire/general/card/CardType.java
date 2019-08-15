package me.card.solitaire.general.card;

public enum CardType {
    ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
    EIGHT("8"), NINE("9"), TEN("X"), JACK("J"), QUEEN("Q"), KING("K");

    String id;

    CardType(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public boolean isTypeAfter(CardType type) {
        switch (this) {
            case ACE: {
                return type == null;
            }
            default: {
                return type != null && ordinal() - type.ordinal() == 1;
            }
        }
    }
}
