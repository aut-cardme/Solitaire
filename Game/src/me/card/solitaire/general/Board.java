package me.card.solitaire.general;

import me.card.solitaire.general.card.Card;
import me.card.solitaire.general.card.CardType;
import me.card.solitaire.general.card.SuitType;

import java.util.*;

public class Board {

    private Set<Card>[] columns = new Set[6];

    public Board(){

    }

    public void setup(){
        List<Card> cards = new ArrayList<>();
        Arrays.stream(CardType.values()).forEach(ct->{
            Arrays.stream(SuitType.values()).forEach(st ->{
                cards.add(new Card(st, ct));
            });
        });
        Collections.shuffle(cards);

        for(int column = 0; column < 6; column++){
            Set<Card> colCards = new HashSet<>();
            for(int row = 0; row < column; row++){
                Card hidden = getRandomAndRemove(cards);
                hidden.setHidden(true);
                colCards.add(hidden);
            }
            Card visible = getRandomAndRemove(cards);
            colCards.add(visible);
        }
    }

    private static Random random = new Random();

    private Card getRandomAndRemove(List<Card> list){
        return list.remove(random.nextInt(list.size()));
    }
}
