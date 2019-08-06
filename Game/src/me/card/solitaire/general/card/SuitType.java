package me.card.solitaire.general.card;

public enum SuitType {
     HEARTS("♥"), DIAMONDS("\t♦"), SPADES("♠"), CLUBS("♣");

     String id;

     SuitType(String id){
        this.id = id;
     }

     public String getID(){
         return id;
     }
}
