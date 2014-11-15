package jersey;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ro.incrys.internship.entities.Quiz;
import services.QuizService;

import com.google.gson.Gson;

@Path("/hello")
public class Hello {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Quiz> getQuizes(){
		QuizService qs=new QuizService();
          return qs.getEntityList();
	}
	
	
	/*public JSONObject getQuizes() {
		JSONObject jsonObject;
		QuizService qs = new QuizService();
		jsonObject = new JSONObject();
		Collection<Quiz> quizes = qs.getEntityList();
		String jsonQuizes = new Gson().toJson(quizes);
		try {
			jsonObject.put("quizes", jsonQuizes);
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return jsonObject;
	}*/

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public Quiz findById(@PathParam("id") String id){
		QuizService qs=new QuizService();
	    return qs.getEntityById(Integer.parseInt(id));
	}
/*	public JSONObject findById(@PathParam("id") String id) {
		JSONObject jsonObject;
		QuizService qs = new QuizService();
		jsonObject = new JSONObject();
		Quiz quiz = qs.getEntityById(Integer.parseInt(id));
		String jsonQuiz = new Gson().toJson(quiz);
		try {
			jsonObject.put("quiz", jsonQuiz);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}*/

}
