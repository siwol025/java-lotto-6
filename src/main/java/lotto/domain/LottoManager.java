package lotto.domain;

import java.util.List;

public class LottoManager {
    private WinningDetails winningDetails;
    private WinningLotto winningLotto;

    private LottoManager(WinningDetails winningDetails) {
        this.winningDetails = winningDetails;
    }

    public static LottoManager from(WinningDetails winningDetails) {
        return new LottoManager(winningDetails);
    }

    public void createWinningLotto(List<Integer> numbers, int bonusNumber) {
        winningLotto = WinningLotto.of(numbers, bonusNumber);
    }

    public void awardPrize(Lotto lotto) {
        int matchedCount = winningLotto.countMatchingNumbers(lotto);
        boolean isMatchedBonusNumber = winningLotto.isMatchBonusNumber(lotto);
        Prize prize = Prize.determineRank(matchedCount, isMatchedBonusNumber);
        winningDetails.increasePrizeAmount(prize);
    }

    public WinningDetails statisticsPrize(Lottos lottos) {
        for (Lotto lotto : lottos.getLottos()) {
            awardPrize(lotto);
        }

        return winningDetails;
    }

    public double calculateProfitRate(int purchaseAmount) {
        int totalPrize = winningDetails.calculateTotalPrize();

        return ((double) totalPrize) / purchaseAmount * 100;
    }
}