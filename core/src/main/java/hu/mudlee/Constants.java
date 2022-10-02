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
	public static final int SIGNAL_REPEAT_FREQUENCY_MILLIS = 10_000;
	public static final int GAME_MAX_LENGTH_MILLIS = 1000 * 60 * 10; // 10 mins
	public static final String THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING = "...--....-...--..---";

	public static final List<QuestionAnswer> INITIAL_CONVERSATION = Arrays.asList(
			new QuestionAnswer("Wake up, samurai.", "Who?"),
			new QuestionAnswer("Bill, is that you?","..."),
			new QuestionAnswer("? Hmm.... hold on... damn, wrong game again...\nKhm, are you still with me? \nNeo, right? Wake up, Neo.","What?"),
			new QuestionAnswer("Follow the white rabbit.","What???"),
			new QuestionAnswer("Nah, kidding. Listen, we are the government\nand our radio telescopes just caught a repeating message!\nIt comes from Jupiter and repeats every 10 seconds.","And why are you telling me this?"),
			new QuestionAnswer("Help us, maybe? You are the hero of this story, after all...","I'm just an LD Jam participant."),
			new QuestionAnswer("Even better. Then I can tell you that we also detected\na massive object coming from Jupiter to Earth.\nWith high speed. Doomsday!\nIt's gonna hit Washington DC and only YOU can stop it.\nLet's start, prepare a pen and paper.","For what? What do we know?"),
			new QuestionAnswer("The signal comes from Jupiter and it repeats.\nThat's all we know so far. Sorry, samu... Neoh.","Sorry Mr. Agent, I don't live in Washington and I don't care."),
			new QuestionAnswer("Will you help us then?","Absolutely not."),
			new QuestionAnswer("Awesome, let's start then.","..."),
			new QuestionAnswer("I'm already hooking you up with our telescopes.\nWhatever you type, it will be broadcasted. ","Shit."),
			new QuestionAnswer("The entire population of Washington DC depends on you!\nNo pressure! Good luck, Carl!","NEO!"),
			new QuestionAnswer("Type your message, then hit ENTER for broadcasting it.\nStarting the signal, ready, steady, go... on air!","")
	);

	public static final List<QuestionAnswer> WINNING_CONVERSATION = Arrays.asList(
			new QuestionAnswer("Did you hear that? The object just disappeared!", "WOW"),
			new QuestionAnswer("You saved us Neyo!", "Fantastic.\nHey, you got my name almost right this time!"),
			new QuestionAnswer("I did? I did! Thank you for your help!", "You are welcome..."),
			new QuestionAnswer("While we are here...", "?"),
			new QuestionAnswer("Would you like to upgrade your modem to our newest model,\nwith revolutionary 300 bit/second speed?", "(Neo hangs up)")
	);

	public static final List<QuestionAnswer> GAME_OVER_CONVERSATION = Arrays.asList(
			new QuestionAnswer("Oh no! What happened? The object tripled its size!!", "Guess what guys, it's time to embrace the horror!\nLook, we've got front row tickets to the end of the earth!")

	);
}
