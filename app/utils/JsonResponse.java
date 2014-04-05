package utils;

public class JsonResponse {
	public int responseCode;
	public String response;
	public JsonResponse(){}
	public JsonResponse(int code,String res){
		responseCode=code;
		response=res;
	}

    public JsonResponse add(JsonResponse jr){
        if(jr!=null){
            this.response+=jr.response;
        }
        return this;
    }
}
