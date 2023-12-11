```mermaid
classDiagram
    direction BT
    class AnsiEscape
    class App
    class Assignment
    class AssignmentMenu
    class Board
    class BoardAddHandler
    class BoardDeleteHandler
    class BoardListHandler
    class BoardMenu
    class BoardModifyHandler
    class BoardRepository
    class BoardViewHandler
    class HelpMenu
    class Member
    class MemberMenu
    class Menu {
        <<Interface>>
    }
    class MenuGroup
    class MenuHandler {
        <<Interface>>
    }
    class MenuItem
    class Prompt

    AssignmentMenu ..> Menu
    BoardAddHandler ..> MenuHandler
    BoardDeleteHandler ..> MenuHandler
    BoardListHandler ..> MenuHandler
    BoardMenu ..> Menu
    BoardModifyHandler ..> MenuHandler
    BoardViewHandler ..> MenuHandler
    HelpMenu ..> Menu
    MemberMenu ..> Menu
    MenuGroup ..> Menu
    MenuItem ..> Menu

```

![img.png](img.png)
