package hu.mudlee;

import com.badlogic.gdx.graphics.Color;
import hu.mudlee.conversation.QuestionAnswer;

import java.util.Arrays;
import java.util.List;

public class Constants {
	public static final char SQUARE_CHAR = 'Û';
	public static final Color BACKGROUND_COLOR = new Color(80f / 255f, 69f / 255f, 155 / 255f, 1);
	public static final Color SECONDARY_COLOR = new Color(136f / 255f, 126f / 255f, 203f / 255f, 1);
	public static final Color LOADER_COLOR = new Color(92f / 255f, 171f / 255f, 94f / 255f, 1);
	public static final int TYPING_FREQUENCY_MILLIS = 10;
	public static final int SPACE_BETWEEN_CONVERSATION_Q_A_MILLIS = 2000;
	public static final int SPACE_BETWEEN_CONVERSATION_PARTS = 3000;
	public static final int SIGNAL_REPEAT_FREQUENCY_MILLIS = 5_000;
	public static final int GAME_MAX_LENGTH_MILLIS = 1000 * 60 * 10; // 10 mins
	public static final String THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING = "...--....-...--..---";

	public static final List<QuestionAnswer> INITIAL_CONVERSATION = Arrays.asList(
			new QuestionAnswer("Wake up, Neo.", "What the h... who the f@ck are ya?"),
			new QuestionAnswer("Follow the white rabbit.","LoL!"),
			new QuestionAnswer("Nah, kidding.\nListen, our radio telescope caught a repeating message!\nIt comes from Alpha Ceti and repeats every 10 seconds.","And what I'm supposed to do?"),
			new QuestionAnswer("Movies always need a hero as we do now. And you will be our ...","Hero, I see. But I can't fly.\nI'm just an LD Jam participant."),
			new QuestionAnswer("No worries. But you may need pen and paper.","Go on, what do we know?"),
			new QuestionAnswer("It comes from Alpha Ceti and it repeats. As I said.\nI'm sorry... wrote.","Sorry mr. whoever, heros must resist..."),
			new QuestionAnswer("Will you?","Yes! I..."),
			new QuestionAnswer("Awesome, let's then start.","..."),
			new QuestionAnswer("I'm connecting your terminal now to our telescopes.\nWhatever you type will be broadcasted.","OK."),
			new QuestionAnswer("Just hit ENTER.","OK!!! Start the shit now."),
			new QuestionAnswer("Starting the signal, ready steady go... on air!","")
	);

	public static final List<QuestionAnswer> WINNING_CONVERSATION = Arrays.asList(
			new QuestionAnswer("WON", "GREAT")
	);

	public static final List<QuestionAnswer> GAME_OVER_CONVERSATION = Arrays.asList(
			new QuestionAnswer("Oh no!", "???"),
			new QuestionAnswer("A huge energy blast is spreading towards Earth from Alpha Ceti!!44!", "Looks like, the time is over...")

	);
}
