import java.awt.event.ActionListener;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ProgramView extends JFrame{
	private JTextField voiceInputTF = new JTextField(10);
	private JTextField ttsInputTF = new JTextField(10);
	private JTextField resultTF = new JTextField(10);
	private JLabel recognize = new JLabel("Speak into your microphone!");
	private JButton listen = new JButton("Listen");
	private JButton tts = new JButton("Text-to-Speech");
	private JButton exit = new JButton("exit");
	
	ProgramView(){
		//text fields editable by voice recognition
		resultTF.setEditable(false);
		voiceInputTF.setEditable(false);
		
		//initializes the GUI
		JPanel content = new JPanel();
		recognize.setVisible(false);
		content.add(new JLabel("Voice Input"));
		content.add(voiceInputTF);
		content.add(new JLabel("Text-to-Speech Input"));
		content.add(ttsInputTF);
		content.add(new JLabel("Result"));
		content.add(resultTF);
		content.add(listen);
		content.add(tts);
		content.add(recognize);
		content.add(exit);
		
		this.setContentPane(content);
		this.pack();
	}
	
	//Sets voice input after recording
	public void setInput(String value) {
		voiceInputTF.setText(value);
		
	}
	public void setResult(String value) {
		resultTF.setText(value);
		
	}
	public void setRecognizeTrue(){
		recognize.setVisible(true);
	}
	
	public String getVoiceInput() {//getter function to allow input to be pulled from the view 
		return voiceInputTF.getText();
	}
	
	//retrieves tts input.
	public String getTTSInput() {
		return ttsInputTF.getText();
		
	}
	
	
	
	public double getResult() {
		String result = resultTF.getText();
		Double resultNum = Double.parseDouble(result);
		return resultNum;
	}
	
	void exitListener(ActionListener listenforBtn) {//action listener function for multiply button 
		exit.addActionListener(listenforBtn);
	}
	
	void listenListener(ActionListener listenforBtn) {//action listener function for multiply button 
		listen.addActionListener(listenforBtn);
	}
	void ttsListener(ActionListener listenforBtn) {//action listener function for multiply button 
		tts.addActionListener(listenforBtn);
	}
}
