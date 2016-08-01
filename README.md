# GoogleIt
A simple, extremely small program I wrote in an attempt to create a keybind for googling things from my clipboard.

There are three ways of using the program. The first, and main reason behind the programs creation, was to select text from any program, execute a keybind, and be taken to a google search of the selected text. This mode uses the argument -p [program], where program is used to specify what program you want to use for reading the selected text. If you don't supply a program, GoogleIt will try and use xsel, which is a linux program.
A second way is to copy the selected text to your clipboard (ctrl+c), and then execute GoogleIt from a keybind. GoogleIt will then use the clipboard text as the search query. This mode is the default mode, so you don't need to pass any arguments.
A third way, for convenience in the event of no selectable text, is to execute a keybind to open up GoogleIt and have it pop up a window prompting you for a query. Argument: -o

This program was meant to be compiled to a JAR file, and then executed as a custom keybind. I've been using Ctrl+G, Ctrl+Shift+G, and Ctrl+Alt+G for the various arguments you can pass to it.
