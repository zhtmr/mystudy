```mermaid
classDiagram
    direction BT
    class AnsiEscape
    class App
    class Assignment
    class AssignmentMenu
    class Board
    class BoardMenu
    class HelpMenu
    class MainMenu
    class Member
    class MemberMenu
    class Menu {
        <<Interface>>
    }
    class MenuGroup
    class MenuItem
    class Prompt

    AssignmentMenu ..> Menu
    BoardMenu ..> Menu
    HelpMenu ..> Menu
    MainMenu ..> Menu
    MemberMenu ..> Menu
    MenuGroup ..> Menu
    MenuItem ..> Menu 
```
