import domain.card.CardDeck;
import domain.game.WhetherAddCard;
import domain.user.Dealer;
import domain.user.Player;
import domain.user.Players;
import domain.user.User;
import factory.CardFactory;
import factory.PlayerFactory;
import util.CardDistributor;
import util.ResultGenerator;
import view.InputView;
import view.OutputView;

public class Application {
    private static final int DEFAULT_CARD_SIZE = 2;

    public static void main(String[] args) {
        final CardDeck cardDeck = new CardDeck(CardFactory.create());
        final Dealer dealer = new Dealer();
        final Players players = new Players(PlayerFactory.create(InputView.inputNames()));

        distributeInitCard(cardDeck, dealer, players);
        initBrief(dealer, players);
        addMoreCards(cardDeck, dealer, players);
        printResults(dealer, players);
    }

    private static void distributeInitCard(final CardDeck cardDeck, final Dealer dealer, final Players players) {
        distributeToUser(cardDeck, dealer);
        for (Player player : players) {
            distributeToUser(cardDeck, player);
        }
    }

    private static void distributeToUser(final CardDeck cardDeck, final User user) {
        int distributeCount = DEFAULT_CARD_SIZE;
        while (distributeCount-- > 0) {
            CardDistributor.giveOneCard(cardDeck, user);
        }
    }

    private static void initBrief(final Dealer dealer, final Players players) {
        OutputView.printDistributeMessage(players);
        OutputView.printInitStatus(dealer, players);
    }

    private static void addMoreCards(final CardDeck cardDeck, final Dealer dealer, final Players players) {
        userMoreCard(cardDeck, players);
        dealerMoreCard(cardDeck, dealer);
    }

    private static void userMoreCard(final CardDeck cardDeck, final Players players) {
        for (Player player : players) {
            giveCardsIfPlayerWant(cardDeck, player);
        }
    }

    private static void giveCardsIfPlayerWant(final CardDeck cardDeck, final Player player) {
        while (player.isNotBust() && WhetherAddCard.of(InputView.inputMoreCard(player)).isYes()) {
            CardDistributor.giveOneCard(cardDeck, player);
            OutputView.printStatus(player);
        }
    }

    private static void dealerMoreCard(final CardDeck cardDeck, final Dealer dealer) {
        if (dealer.shouldAddCard()) {
            CardDistributor.giveOneCard(cardDeck, dealer);
            OutputView.printDealerAddCard();
        }
    }

    private static void printResults(final Dealer dealer, final Players players) {
        OutputView.printUsersResult(dealer, players);
        OutputView.printLastResult(ResultGenerator.create(dealer, players));
    }
}
