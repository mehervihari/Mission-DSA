package Strategies.botplayingstrategies;

import Models.BotDifficultyLevel;

public class BotPlayingStrategyFactory {
    public static IBotPlayingStrategy getBotPlayingStrategyForDifficultyLevel(BotDifficultyLevel level){
        return new EasyBotPlayingStrategy();
    }
}
