package airship;

public class Main {

//    public static final Injector INJECTOR = Guice.createInjector(new MyModule());
    public static final MyFXML FXML = new MyFXML();

//    @Override
//    public void start(Stage stage) throws IOException {
//
//
////        var hello = FXML.load(HelloCtrl.class, "com", "example", "airshipg", "hello-view.fxml");
////        var select = FXML.load(SelectCtrl.class, "com", "example", "airshipg",
////            "selecttile.fxml");
//
//
//
//        new MainCtrl(stage);
//    }

    public static void main(String[] args) {
        try {
//            throw new RuntimeException("test");
            GUI.launch(GUI.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}