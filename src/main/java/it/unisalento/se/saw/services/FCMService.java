package it.unisalento.se.saw.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.unisalento.se.saw.Iservices.IFCMService;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.SubjectRepository;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@Service
public class FCMService implements IFCMService {
	
	private String androidFcmKey="AAAA0cxCEbw:APA91bEwkU0JJqlzU-_rTZkd5kSUiuE2LwfWB2g7e230JuvCmAHB3UYHcQY-wd1IZgbh0I1wSGPI-ug5YXqjy9jZvEMzIDwp-EAsKMFeKb52C4liDbf76kAiBHfTXHC8roc7bVJzGinB";
	private  String androidFcmUrl="https://fcm.googleapis.com/fcm/send";
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	public void newFeedBackLesson(int idlesson) {
		Lesson lesson = lessonRepository.getOne(idlesson);
		String tokenTeacher = this.getUserToken(lesson.getTypeLesson().getSubject().getTeacher().getIduser());
		ArrayList<String> tokens = new ArrayList<String>();
		if(tokenTeacher != null) {
			tokenTeacher = tokenTeacher.substring(1, tokenTeacher.length()-1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			JSONObject msg = new JSONObject();
			msg.put("title", "Nuovo Feedback!");
		   msg.put("body", "Hai ricevuto un nuovo FeedBack per la tua lezione di " + lesson.getTypeLesson().getSubject().getTypeSubject().getName() + " del " + sdf.format(lesson.getStart()));
		   msg.put("type", "feedlesson");
		   
		   msg.put("idlesson", idlesson);
			   tokens.add(tokenTeacher);
		   this.send(tokens, msg);
		}
	}
	
	public void newFileLesson(int idlesson) {
		Lesson lesson = lessonRepository.getOne(idlesson);
		List<Student> students = subjectRepository.getStudentByIdCourse(lesson.getTypeLesson().getSubject().getIdsubject());
		JSONObject tokensObj = this.getTokens();
		ArrayList<String> tokens = new ArrayList<String>();
		for(int i=0; i<students.size(); i++) {
			try {
			   tokens.add((String)tokensObj.getJSONObject(Integer.toString(students.get(i).getIduser())).get("token"));
		   } catch (Exception e) {
			// TODO: handle exception
		   }
		}
		
		if(tokens != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			JSONObject msg = new JSONObject();
			msg.put("title", "Nuovo Upload!");
		   msg.put("body", "E' presente un nuovo file per la lezione di " + lesson.getTypeLesson().getSubject().getTypeSubject().getName() + " del " + sdf.format(lesson.getStart()));
		   msg.put("type", "filelesson");
		   
		   msg.put("idlesson", idlesson);
		   this.send(tokens, msg);
		}
	}
	
    public void send(ArrayList<String> tokens, JSONObject msg) {

		   RestTemplate restTemplate = new RestTemplate();
		   HttpHeaders httpHeaders = new HttpHeaders();
		   httpHeaders.set("Authorization", "key=" + androidFcmKey);
		   httpHeaders.set("Content-Type", "application/json");
		   JSONObject json = new JSONObject();
		   json.put("data", msg);
		   json.put("registration_ids", tokens);

		   HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(),httpHeaders);
		   String response = restTemplate.postForObject(androidFcmUrl,httpEntity,String.class);
		   System.out.println(response);
    }
    
    public JSONObject getTokens() {
    	String url="https://uniapp-86a68.firebaseio.com/tokens.json?";
	   RestTemplate restTemplate = new RestTemplate();

	   String response = restTemplate.getForObject(url, String.class);
	   return new JSONObject(response);
	   /*try {
		   System.out.println( obj.getJSONObject(Integer.toString(userid)).get("token"));
	   } catch (Exception e) {
		// TODO: handle exception
		   System.out.println("NON NE HO");
	   }*/
    }
    
   public String getUserToken(int userid) {
	   String url="https://uniapp-86a68.firebaseio.com/tokens/" + userid + "/token.json?";
	   RestTemplate restTemplate = new RestTemplate();
	
	   String response = restTemplate.getForObject(url, String.class);

 	  return response;

    }

}
