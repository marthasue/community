package life.sc.community.dto;

import life.sc.community.exception.CustomizeErrorCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    //private Map<String,Object> extend=new HashMap<>();

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }

//    public ResultDTO addMsg(String key, Object value) {
//        this.extend.put(key, value);
//    }

        public static Object errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }
}
