public class Program {
	
    public static void main(String[] args) {
        ProgramView view = new ProgramView();
        ProgramModel model = new ProgramModel();
        ProgramController controller = new ProgramController(view, model);
        
        view.setSize(500,300);
        view.setVisible(true);
    }
}
