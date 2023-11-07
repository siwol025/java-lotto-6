package lotto.controller;

import java.util.List;
import lotto.domain.prize.WinningDetails;
import lotto.service.LottoService;
import lotto.util.InputConverter;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final LottoService lottoService;
    private final InputView inputView;
    private final OutputView outputView;

    private LottoController(LottoService lottoService, InputView inputView, OutputView outputView) {
        this.lottoService = lottoService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public static LottoController getInstance() {
        return new LottoController(LottoService.getInstance(), new InputView(), new OutputView());
    }

    public void run() {
        performLottosPurchaseProcess();
        performDrawWinningLottoProcess();
        performLottoResultProcess();
        performProfitRateCalculationProcess();
    }

    private void performLottosPurchaseProcess() {
        try {
            int purchaseAmount = InputPurchaseAmount();
            List<String> issuedLottosNumbers = lottoService.buyLotto(purchaseAmount);
            outputView.showIssuedLottoResult(issuedLottosNumbers);
        } catch (IllegalArgumentException e) {
            outputView.showErrorMessage(e.getMessage());
            performLottosPurchaseProcess();
        }
    }

    private void performDrawWinningLottoProcess() {
        try {
            final List<Integer> winningNumbers = InputWinnigNumbers();
            final int bonusNumber = InputBonusNumber();
            lottoService.drawWinningLotto(winningNumbers, bonusNumber);
        } catch (IllegalArgumentException e) {
            outputView.showErrorMessage(e.getMessage());
            performDrawWinningLottoProcess();
        }
    }

    private void performProfitRateCalculationProcess() {
        double profitRate = lottoService.getProfitRate();
        outputView.showProfitRate(profitRate);
    }

    private void performLottoResultProcess() {
        final WinningDetails winningDetails = lottoService.getWinningResult();
        outputView.showLottoResult(winningDetails);
    }

    private int InputPurchaseAmount() {
        String purchaseAmount = inputView.askPurchaseAmount();

        return InputConverter.convertStringToInt(purchaseAmount);
    }

    private List<Integer> InputWinnigNumbers() {
        String winningNumbers = inputView.askWinnigNumbers();

        return InputConverter.convertToList(winningNumbers);
    }

    private int InputBonusNumber() {
        String bonusNumber = inputView.askBonusNumber();

        return InputConverter.convertStringToInt(bonusNumber);
    }
}
