void main() {
    Backend backend = new Backend();
    ConsoleUI UI =  new ConsoleUI(backend);
    UI.start();
}