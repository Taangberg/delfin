void main() throws IOException {
    Backend backend = new Backend();
    ConsoleUI UI =  new ConsoleUI(backend);
    backend.setUI(UI);
    UI.start();
}