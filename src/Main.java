void main() throws IOException {
    Backend backend = new Backend("src/CSV/Members.CSV");
    ConsoleUI UI =  new ConsoleUI(backend);
    UI.start();
}