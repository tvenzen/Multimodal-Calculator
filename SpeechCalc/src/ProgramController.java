
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;



public class ProgramController {
	private ProgramView view;
	private ProgramModel model;
	
	public ProgramController(ProgramView view, ProgramModel model){
		this.view = view;
		this.model = model;
		
		this.view.exitListener(new exitCalculator());
		this.view.listenListener(new listenCalculator());
		this.view.ttsListener(new ttsCalculator());
	}
	
	class ttsCalculator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
				SpeechConfig speechConfig = SpeechConfig.fromSubscription("d8339008f6734574b0eb499f307d1c3f", "eastus");
				
				String textToSpeech = view.getTTSInput();
				
				model.fromTTS(speechConfig, textToSpeech);
			
				String calculationResult = model.calculate(textToSpeech);
				
	
				
				model.fromTTS(speechConfig, "equals");
				model.fromTTS(speechConfig, calculationResult);
					
				
		}
	}
	class listenCalculator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
				SpeechConfig speechConfig = SpeechConfig.fromSubscription("d8339008f6734574b0eb499f307d1c3f", "eastus");
		        
				//Starts voice recognition
				try {
					model.fromMic(speechConfig);
				
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//Grabs a string from the mic
		        String speech = model.getFromMic();
		        
		        //Maven tts trascribes "minus" as "--" sometimes, so we need this next line to fix that error.
		        view.setInput(speech.replaceAll("--", "-"));
		        
		        //Using the input string from the mic, the model calculates the math.
		        String calculationResult = model.calculate(speech.replaceAll("--", "-"));
				view.setResult(calculationResult);
		        
		        
		       
		   
		}

	}
	class exitCalculator implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			model.exit();
		}
		
		
	}
}
