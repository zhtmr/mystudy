package bitcamp.menu;

import bitcamp.myapp.util.Prompt;

public interface Menu {

  public abstract void execute(Prompt prompt);

  public abstract String getTitle();

}
