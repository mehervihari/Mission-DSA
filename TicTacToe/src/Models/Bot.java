package Models;

import Strategies.botplayingstrategies.BotPlayingStrategyFactory;
import Strategies.botplayingstrategies.IBotPlayingStrategy;

public class Bot extends Player{
    public BotDifficultyLevel botDifficultyLevel;
    public IBotPlayingStrategy botPlayingStrategy;
    public Bot(Long id, String name, Symbol symbol,
               BotDifficultyLevel botDifficultyLevel) {
        super(id, name, symbol, PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory
                .getBotPlayingStrategyForDifficultyLevel(botDifficultyLevel);
    }

    public BotDifficultyLevel getBotDifficultyLevel(){
        return botDifficultyLevel;
    }

    public void setBotDifficultyLevel(BotDifficultyLevel botDifficultyLevel){
        this.botDifficultyLevel = botDifficultyLevel;
    }

    @Override
    public Move makeMove(Board board)
    {
        Move move = botPlayingStrategy.makeMove(board);
        return move;
    }
}
