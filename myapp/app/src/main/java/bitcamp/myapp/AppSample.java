package bitcamp.myapp;

public class AppSample {

  public static void main(String[] args) {
    Prompt prompt = new Prompt(System.in);

    new MainMenu(prompt).execute();
    prompt.close();
  }

}
