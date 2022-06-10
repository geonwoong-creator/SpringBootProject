package kopo.poly.controller;

import kopo.poly.service.IFoodService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class FoodController {

    @Resource(name = "FoodService")
    private IFoodService foodService;
    //중분류
    @GetMapping(value = "main/Food")
    public String Bno(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553077/api/open/sdsc2/middleUpjongList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%2BuGsicbaOj9jclJC6XTEzqSVmjkoXlPObuFuetBOAmiqsOR6KDxhKSFeP2988YGj0bX57AJaE2PHb%2F1xzxJ4ow%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("indsLclsCd","UTF-8") + "=" + URLEncoder.encode("Q", "UTF-8")); /* 상권정보 업종 대분류 코드*/
//        URL url = new URL(urlBuilder.toString());
        String url = urlBuilder.toString();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(url);

        // XML 맨 위에 있는 첫 번째 태그
        document.getDocumentElement().normalize();
        log.info("Root Tag : " + document.getDocumentElement().getNodeName());

        // 파싱할 Tag
        NodeList nList = document.getElementsByTagName("item"); // items 태그의 item 태그 내용을 nList에 담기
        log.info("nList 개수 : " + nList.getLength()); // 100

        List<HashMap<String, Object>> tagList = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i); // nList의 i번째 데이터들 node에 저장

            Element element = (Element) node;

            String code = getTagValue("indsMclsCd", element);
            String name = getTagValue("indsMclsNm", element);
            log.info("코드 : " + getTagValue("indsMclsCd", element));
            log.info("이름 : " + getTagValue("indsMclsNm", element));

            HashMap<String, Object> tagMap = new HashMap<>();
            tagMap.put("indsMclsCd", code);
            tagMap.put("indsMclsNm", name);

            tagList.add(tagMap);

            tagMap = null;
        }
        model.addAttribute("tList", tagList);


        return "/main/FoodList";
    }

    public String getTagValue(String tag, Element element) {

        String result = "";

        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();

        result = nodeList.item(0).getTextContent();

        return result;

    }
    //소분류
    @GetMapping(value = "main/Food2")
    @ResponseBody
    public String Bno2(HttpSession session, HttpServletRequest request) throws Exception {
        String mCode = CmmUtil.nvl(request.getParameter("mCode"));
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553077/api/open/sdsc2/smallUpjongList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%2BuGsicbaOj9jclJC6XTEzqSVmjkoXlPObuFuetBOAmiqsOR6KDxhKSFeP2988YGj0bX57AJaE2PHb%2F1xzxJ4ow%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("indsLclsCd","UTF-8") + "=" + URLEncoder.encode("Q", "UTF-8")); /* 상권정보 업종 대분류 코드*/
        urlBuilder.append("&" + URLEncoder.encode("indsMclsCd","UTF-8") + "=" + URLEncoder.encode(mCode, "UTF-8")); /*상권정보 업종 중분류 코드*/
//        URL url = new URL(urlBuilder.toString());
        String url = urlBuilder.toString();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(url);

        // XML 맨 위에 있는 첫 번째 태그
        document.getDocumentElement().normalize();
        log.info("Root Tag : " + document.getDocumentElement().getNodeName());

        // 파싱할 Tag
        NodeList nList = document.getElementsByTagName("item"); // items 태그의 item 태그 내용을 nList에 담기
        log.info("nList 개수 : " + nList.getLength()); // 100

        JSONArray jsonArray =  new JSONArray();
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i); // nList의 i번째 데이터들 node에 저장

            Element element = (Element) node;


            String scode = getTagValue("indsSclsCd", element);
            String sname = getTagValue("indsSclsNm", element);
            log.info("JSON코드 : " + getTagValue("indsSclsCd", element));
            log.info("JSON이름 : " + getTagValue("indsSclsNm", element));

            HashMap<String, Object> tagMap = new HashMap<>();
            tagMap.put("indsSclsCd", scode);
            tagMap.put("indsSclsNm", sname);

            JSONObject jObj = new JSONObject(tagMap);
            jsonArray.add(jObj);

            tagMap = null;
        }

        return jsonArray.toString();
    }
    @GetMapping(value = "main/Food3")
    @ResponseBody
    public String Bno3(HttpSession session, HttpServletRequest request) throws Exception {
        String sCode = CmmUtil.nvl(request.getParameter("sCode"));
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553077/api/open/sdsc2/storeListInUpjong"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%2BuGsicbaOj9jclJC6XTEzqSVmjkoXlPObuFuetBOAmiqsOR6KDxhKSFeP2988YGj0bX57AJaE2PHb%2F1xzxJ4ow%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("divId","UTF-8") + "=" + URLEncoder.encode("indsSclsCd", "UTF-8")); /* 상권정보 업종 대분류 코드*/
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode(sCode, "UTF-8")); /*상권정보 업종 중분류 코드*/
//        URL url = new URL(urlBuilder.toString());
        String url = urlBuilder.toString();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(url);

        // XML 맨 위에 있는 첫 번째 태그
        document.getDocumentElement().normalize();
        log.info("Root Tag : " + document.getDocumentElement().getNodeName());

        // 파싱할 Tag
        NodeList nList = document.getElementsByTagName("item"); // items 태그의 item 태그 내용을 nList에 담기
        log.info("nList 개수 : " + nList.getLength()); // 100

        JSONArray jsonArray =  new JSONArray();
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i); // nList의 i번째 데이터들 node에 저장

            Element element = (Element) node;

            String Mcode = getTagValue("bizesId", element);
            String Mname = getTagValue("bizesNm", element);
            String Maddr = getTagValue("rdnmAdr", element);

            log.info("JSON코드 : " + getTagValue("bizesId", element));
            log.info("JSON코드 : " + getTagValue("bizesNm", element));
            log.info("JSON이름 : " + getTagValue("rdnmAdr", element));

            HashMap<String, Object> tagMap = new HashMap<>();
            tagMap.put("bizesId", Mcode);
            tagMap.put("bizesNm", Mname);
            tagMap.put("rdnmAdr", Maddr);

            JSONObject jObj = new JSONObject(tagMap);
            jsonArray.add(jObj);

            tagMap = null;
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/main/FoodMap")
    public String FoodMap(HttpServletRequest request, ModelMap model) {

        log.info(this.getClass().getName() + ".FoodMap Start!");

        String maName = CmmUtil.nvl(request.getParameter("code"));
        String maAddr = CmmUtil.nvl(request.getParameter("name"));

        log.info("Mname : " + maName);
        log.info("Mcode : " + maAddr);

        model.addAttribute("maName", maName);
        model.addAttribute("maAddr", maAddr);
        return "/main/FoodMap";
    }

}
