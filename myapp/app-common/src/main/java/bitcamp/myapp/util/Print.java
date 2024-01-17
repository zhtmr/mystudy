package bitcamp.myapp.util;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Print {
  public static void main(String[] args) throws IOException {
    folder(".", 1);
  }

  static File folder(String path, int level) throws IOException {
    File dir = new File(path);
    File[] files = dir.listFiles(
        pathname -> pathname.isDirectory() || (pathname.isFile() && pathname.getName()
            .endsWith(".java")));
    for (File f : Objects.requireNonNull(files)) {
      System.out.print(indent(level));

      if (f.isDirectory() && !f.isHidden()) {
        System.out.printf("%s/\n", f.getName());
        folder(f.getCanonicalPath(), level + 1);
      } else if (f.isFile()) {
        System.out.printf("|-- %s\n", f.getName());
      }
    }
    return null;
  }

  static String indent(int level) {
    return "   ".repeat(Math.max(0, level));
  }
}
