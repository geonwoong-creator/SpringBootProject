package kopo.poly.controller;

import kopo.poly.dto.BnoDTO;
import kopo.poly.service.IBnoService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
@Controller
public class BnoController {

    @Resource(name = "BnoService")
    private IBnoService bnoService;

    @PostMapping(value = "main/Bno")
    public String Bno(HttpSession session, HttpServletRequest request) throws Exception {
        String user_id = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String b_no = CmmUtil.nvl(request.getParameter("b_no")); // 제목
        String appKey = "%2BuGsicbaOj9jclJC6XTEzqSVmjkoXlPObuFuetBOAmiqsOR6KDxhKSFeP2988YGj0bX57AJaE2PHb%2F1xzxJ4ow%3D%3D";
        StringBuilder urlBuilder = new StringBuilder("https://api.odcloud.kr/api/nts-businessman/v1/status"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + appKey); /*Service Key*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        String jsonInputString = "{\"b_no\": [\"" + b_no + "\"]}";
        log.info(jsonInputString);
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        //log.info("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        log.info("REST API RESULT : " + sb.toString());
        //2. Parser
        JSONParser jsonParser = new JSONParser();

        //3. To Object
        Object obj = jsonParser.parse(sb.toString());


        //4. To JsonObject
        JSONObject jsonObj = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonObj.get("data");
        for(int i=0;i<jsonArray.size();i++){

            JSONObject jsonObj2 = (JSONObject)jsonArray.get(i);

            String bno = (String)jsonObj2.get("b_no");
            String tax_type = (String)jsonObj2.get("tax_type");
            String b_stt = (String)jsonObj2.get("b_stt");

            if(((String)jsonObj2.get("tax_type")).equals("국세청에 등록되지 않은 사업자등록번호입니다.")) {
                log.info(this.getClass().getName() + ".등록되지 않은 사업자등록번호");

            } else {
                log.info(this.getClass().getName() + ".등록된 사업자등록번호");
                BnoDTO pDTO = new BnoDTO();
                pDTO.setB_no(b_no);
                pDTO.setTax_type((String)jsonObj2.get("tax_type"));
                pDTO.setB_stt((String)jsonObj2.get("b_stt"));
                pDTO.setUser_id(user_id);

                bnoService.InsertBno(pDTO);
            }
        }

       return "/main/main";
    }

    @RequestMapping(value = "/main/UserBno")
    public String BnoInfo(HttpSession session, ModelMap model) {

        log.info(this.getClass().getName() + ".UserSetting");

        try {
            String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

            log.info("SS_USER_ID : " + Userid);

            BnoDTO pDTO = new BnoDTO();
            pDTO.setUser_id(Userid);



            BnoDTO rDTO = bnoService.getBno(pDTO);
;

            if (rDTO == null) {
                rDTO = new BnoDTO();
            }

            log.info("getUserBno success!!");

            model.addAttribute("rDTO", rDTO);
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".getUserBno END!!");
        }

        log.info(this.getClass().getName() + ".UserSetting End!!");

        return "/main/UserBno";
    }


}
