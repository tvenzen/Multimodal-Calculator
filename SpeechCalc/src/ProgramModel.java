import java.util.concurrent.Future;

import com.microsoft.cognitiveservices.speech.PhraseListGrammar;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.awt.TextField;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

public class ProgramModel {
	private String resultString;
	
	void exit() {
		System.exit(0);
	}
	public void fromMic(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
		
		//Initializes all necessary objects for recognizing speech.
        AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);
        System.out.println("Speak into your microphone.");
        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
        SpeechRecognitionResult result = task.get();
        System.out.println("RECOGNIZED: Text=" + result.getText());
        resultString = result.getText();	
        
	}
	
	//Returns speech input string
	public String getFromMic() {
		return resultString;
	}
	
	public void fromTTS(SpeechConfig speechConfig, String tts) {
		 AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();;
		 
		 SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
		 synthesizer.SpeakText(tts);
	}
	
	
	
	//This part of the code was referenced by https://www.programmersought.com/article/97257706913/
	public String calculate(String tts){//Calculation tts
		
		double total=0;
		double flag=0;
		int top=-1;// pointer to the stack
		//Stack<Double> initStack = new Stack<Double>(); Double type stack will lose precision, such as depositing in 2.1 and removing it will be 2.0
		double[] initStack =  new double[100];// Store the stack of numbers, use an array to simulate the stack
		Stack<Character> scndStack = new Stack<Character>();//Store the stack of operators
		
		for(int i=0 ; i<tts.length(); i++){
			char p = tts.charAt(i);
			flag = total = 0;
			if(p >= '0' && p <= '9'){//Deposit numbers
				while(true){
					p = tts.charAt(i);
					if(p == '.') flag = 0.1;
					else {
						if(flag == 0) total = total*10+(p-'0');
						else {//Decimal place processing
							total += ((p-'0')*flag);
							flag *= 0.1;
						}
					}					
					if(i+1<tts.length()&&((tts.charAt(i+1)>='0'&&tts.charAt(i+1)<='9')||tts.charAt(i+1)=='.')) i++;
					else {
						initStack[++top]=total;
						break;
					}
				} 
			}
			else {
				if(p=='(') scndStack.push(p);
                else if (p == '^') {
                    while(scndStack.size()>0 && (scndStack.peek()=='^')) {
                        double temp = compute(initStack[top--],initStack[top--],scndStack.pop());
                        initStack[++top] = temp;
                    }
                    scndStack.push(p);
                }
				else if(p=='+'||p=='-') {// At this time, when the operator is + or -, the content before the parentheses is taken out
					while(scndStack.size()>0&&scndStack.peek()!='(') {
						double temp=compute(initStack[top--],initStack[top--],scndStack.pop());
						initStack[++top]=temp;
					}
					scndStack.push(p);
				}
				else if(p==')') {// When the operator is), the stack contents are all taken out
					while(scndStack.size()>0&&scndStack.peek()!='(') {
						double temp = compute(initStack[top--],initStack[top--],scndStack.pop());
						initStack[++top] = temp;
					}
					scndStack.pop();//Remove the left parenthesis
				}
			}
		}
		while(scndStack.size()>0) {//Calculate the remaining formula
			double temp=compute(initStack[top--],initStack[top--],scndStack.pop());
			initStack[++top] = temp;
		}
		return "" + initStack[top];//Forcibly convert double type to String type
	}
	
	public double compute(double a,double b, char p) {//Operation in the function
		double total = 0;
		if(p=='+') {
			total = b+a;
		}else if(p=='-') {//Be careful to use the number at the top of the stack to be a minus or dividend
			total = b-a;
		}else if(p=='*') {
			total = b*a;
		}else if(p=='/') {
			total = b/a;
		}else if(p== '^') {
            total = Math.pow(b,a);
        }
		return total;
	}
	
	
}