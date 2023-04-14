import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
    public enum Rank { ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }
    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
    }

    public String getName() {
        return name;
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public Card playCard(int index) {
        if (index < 0 || index >= hand.size()) {
            return null;
        }
        return hand.remove(index);
    }

    public int getHandSize() {
        return hand.size();
    }

    public List<Card> getHand() {
        return hand;
    }
}

public class Game {
    private List<Player> players;
    private Deck deck;
    private List<Card> discardPile;
    private int currentPlayerIndex;
    private int direction;
    private boolean reversed;
    private int drawCount;

    public Game(List<String> playerNames) {
        players = new ArrayList<Player>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        deck = new Deck();
        discardPile = new ArrayList<Card>();
        currentPlayerIndex = 0;
        direction = 1;
        reversed = false;
        drawCount = 0;
        dealCards();
        discardPile.add(deck.drawCard());
    }

    private void dealCards() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.drawCard(deck.drawCard());
            }
        }
    }

    public boolean playCard(int playerIndex, int cardIndex) {
        if (playerIndex != currentPlayerIndex) {
            return false;
        }
        Player player = players
