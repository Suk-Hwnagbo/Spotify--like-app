package edu.sjcc;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

// declares a class for the app
public class SpotifyLikeAppExampleCode {

  // the current audio clip
  private static Clip audioClip;
  private static Song currentSong;

  /*
   *** IMPORTANT NOTE FOR ALL STUDENTS ***
   * 
   * This next line of code is a "path" that students will need to change in order
   * to play music on their
   * computer. The current path is for my laptop, not yours.
   * 
   * If students who do not understand whre files are located on their computer or
   * how paths work on their computer,
   * should immediately complete the extra credit on "Folders and Directories" in
   * the canvas modules.
   * 
   * Knowing how paths work is fundamental knowledge for using a computer as a
   * technical person.
   * 
   * The play function in this example code plays one song. Once student change
   * the directoryPath variable properly,
   * one of the songs should play. Students should implement their own code for
   * all the functionality in the assignment.
   * 
   * Students can use, and find their own music. You do not have to use or listen
   * to my example music! Have fun!
   * 
   * Students who do not know what a path is on their computers, and how to use a
   * path, are often unable complete
   * this assignment succesfullly. If this is your situation, please complete the
   * extra credit on Folders Path,
   * and Directories. Also, please do google and watch youtube videos if that is
   * helpful too.
   * 
   * Thank you! -Gabriel
   * 
   */

  private static String directoryPath = "C:/Users/sean0/OneDrive/Documents/GitHub/Spotify- like-app/spotifyapp/src/main/java/edu/sjcc";

  // "main" makes this class a java app that can be executed
  public static void main(final String[] args) {
    // reading audio library from json file
    // First, the program reads the song library.
    Song[] library = readAudioLibrary();

    // create a scanner for user input
    // tool that you can put in
    Scanner input = new Scanner(System.in);

    String userInput = "";
    while (!userInput.equals("q")) {
      // show it on the screen
      menu();

      // get input
      // allow me totype h-home, s->search, l->library, p->play, q->quit
      userInput = input.nextLine().toLowerCase();

      // accept upper or lower case commands

      handleMenu(userInput, library, input);
    }

    // close the scanner
    input.close();
  }

  /*
   * displays the menu for the app
   * 
   * it is command that prints text to the console
   */
  public static void menu() {
    System.out.println("---- SpotifyLikeApp ----");
    System.out.println("[H]ome");
    System.out.println("[S]earch by title");
    System.out.println("[L]ibrary");
    System.out.println("[P]lay");
    System.out.println("[Q]uit");

    System.out.println("[T} top playing");
    System.out.print("Enter q to Quit:");
  }

  /*
   * handles the user input for the app
   */
  public static void handleMenu(String userInput, Song[] library, Scanner input) {
    switch (userInput) {
      case "h":
        // it prints out home and it goes to difrrent menu
        System.out.println("-->Home<--");
        break;

      case "s": {
        System.out.println("-->Search by title<--");
        // user enters s, it's gonna search. query is what the user is searching for

        searchBytitle(library, input);
        break;
      }

      // library[i] is an object that im forcing by using toString to transform it to
      // string
      case "l":
        System.out.println("-->Library<--");
        displayLibrary(library);
        break;

      case "p":
        System.out.println("Enter the number of the song you would like to play");
        try {
          // parseInt is searching for the integer
          int songNum = Integer.parseInt(input.nextLine());
          // the songs in the library start with 1 but index starts with zero that's the
          // reason why we put -1
          if (songNum > 0 && songNum <= library.length) {
            currentSong = library[songNum - 1];
            play(currentSong);
          }

          else {
            System.out.println("Invalid number.");
          }
        } catch (Exception e) {
          System.out.println("please enter a valid number.");
        }
        break;

      case "t":
        System.out.println("-->Stop playing<--");
        if (audioClip != null && audioClip.isActive()) {
          audioClip.stop();
        }
        break;
      case "q":
        System.out.println("-->Quit<--");
        break;
      default:
        break;
    }
  }

  public static void searchBytitle(Song[] library, Scanner input) {
    String query = input.nextLine();
    // initilizae it and default statment is false. we need it because it's varaible
    // that we need for another if.
    boolean found = false;
    System.out.println("search information" + query);

    for (int i = 0; i < library.length; i++) {
      // library at i
      // if querry matches the part of the name, then it's a match
      if (library[i].name().toLowerCase().contains(query.toLowerCase())) {
        System.out.println(library[i].name());
        System.out.println("Enter t to stop playing");

        // found is for prevenint if(!found) from happening
        currentSong = library[i];
        play(currentSong);
        found = true;
        break;
      }
    }
    if (!found) {
      System.out.println("Song not found.");
    }
  }

  public static void displayLibrary(Song[] library) {
    for (int i = 0; i < library.length; i++) {
      System.out.println((i + 1) + "," + library[i].toString());
    }
  }

  /*
   * plays an audio file
   */
  public static void play(Song song) {
    // open the audio file

    // get the filePath and open a audio file

    final String filename = song.fileName();
    final String filePath = directoryPath + "/wav/" + filename;
    final File file = new File(filePath);

    // stop the current song from playing, before playing the next one
    if (audioClip != null) {
      audioClip.close();
    }

    try {
      // create clip
      audioClip = AudioSystem.getClip();

      // get input stream
      final AudioInputStream in = AudioSystem.getAudioInputStream(file);

      audioClip.open(in);
      audioClip.setMicrosecondPosition(0);
      audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // read the audio library of music
  public static Song[] readAudioLibrary() {
    // get the file path
    final String jsonFileName = "audio-library.json";
    final String filePath = directoryPath + "/" + jsonFileName;

    Song[] library = null;
    try {
      System.out.println("Reading the file " + filePath);
      JsonReader reader = new JsonReader(new FileReader(filePath));
      library = new Gson().fromJson(reader, Song[].class);
    } catch (Exception e) {
      System.out.printf("ERROR: unable to read the file %s\n", filePath);
      System.out.println();
      e.printStackTrace();
    }

    return library;
  }
}
