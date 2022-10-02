package hu.mudlee;

import com.badlogic.gdx.graphics.Color;
import hu.mudlee.conversation.ConversationItem;

import java.util.ArrayList;
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
  public static final int RANDOM_MUMBLING_DELAY_MIN_MILLIS = 30_000;
  public static final int RANDOM_MUMBLING_DELAY_MAX_MILLIS = 120_000;
  public static final int GAME_MAX_LENGTH_MILLIS = 1000 * 60 * 10; // 10 mins
  public static final String THE_ANSWER_TO_LIFE_THE_UNIVERSE_AND_EVERYTHING = "...--....-...--..---";

  public static final List<ConversationItem> INITIAL_CONVERSATION = List.of(
      new ConversationItem("Ready.\n"+SQUARE_CHAR, "Who turned this shit on... ?", false),
      new ConversationItem("Wake up, samurai.", "Who?", false),
			new ConversationItem("Bill, is that you?","...", false),
			new ConversationItem("? Hmm.... hold on... damn, wrong game again...\nKhm, are you still with me? \nPaul, right? Wake up, Paul.","What?", false),
			new ConversationItem("Follow the white rabbit.","What???", false),
			new ConversationItem("Nah, kidding. Listen, we are the government\nand our radio telescopes just caught\na repeating message! It comes from Jupiter\nand repeats every 10 seconds.","And why are you telling me this?", false),
			new ConversationItem("Help us, maybe?\nYou are the hero of this story, after all...","I'm just an LD Jam participant.", false),
			new ConversationItem("Even better.\nThen I can tell you that we also detected\na massive object coming from Jupiter to Earth.\nWith high speed. Doomsday!\nIt's gonna hit Washington and only YOU can stop it.\nLet's start, prepare a pen and paper.","For what? What do we know?", false),
			new ConversationItem("The signal comes from Jupiter and it repeats.\nThat's all we know so far. Sorry, Samu... Hank.","Sorry Mr. Agent,\nI don't live in Washington and I don't care.", false),
			new ConversationItem("Will you help us then?","Absolutely not.", false),
			new ConversationItem("Awesome, let's start then.","...", false),
			new ConversationItem("I'm already hooking you up with our telescopes.\n10 minutes left till DOOMSDAY!","Shit.", false),
			new ConversationItem("The entire population of Washington DC depends on you!\nNo pressure! Good luck, Carl!","NEO!", false),
			new ConversationItem("Whatever. Type your message,\nthen hit ENTER for broadcasting it.\nStarting the signal, ready, steady, go... on air!","", false)
  );

  public static final List<ConversationItem> WINNING_CONVERSATION = List.of(
      new ConversationItem("Did you hear that?\nThe object just disappeared!", "WOW", false),
      new ConversationItem("You saved us Neyo!", "Fantastic.\nHey, you got my name almost right this time!", false),
      new ConversationItem("I did? I did! Thank you for your help!", "You are welcome...", false),
      new ConversationItem("While we are here...", "?", false),
      new ConversationItem("Would you like to upgrade your modem...\nto our newest model,\nwith revolutionary 300 bit/second speed?", "(Neo hangs up)", false)
  );

  public static final List<ConversationItem> GAME_OVER_CONVERSATION = List.of(
      new ConversationItem("Oh no! What happened?\nThe object tripled its size!!", "Guess what guys, it's time to embrace the horror!\nLook, we've got front row tickets\nto the end of the earth!", false)
  );

  public static final List<ConversationItem> RANDOM_AGENT_MUMBLINGS = new ArrayList<>() {{
    add(new ConversationItem("It's like a pop song...\n dit dit dit daaa daaa", "", true));
    add(new ConversationItem("Don't forget, we are counting on you, Travis.", "", true));
    add(new ConversationItem("I'm not rushing you. JUST asking.\nAre you progressing?", "", true));
    add(new ConversationItem("Is that so complicated?", "", true));
    add(new ConversationItem("What did you say, what's your name?", "", true));
    add(new ConversationItem("Do you have a towel, Mike?", "", true));
  }};
}
