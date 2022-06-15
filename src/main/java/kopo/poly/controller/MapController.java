package kopo.poly.controller;

import kopo.poly.dto.BookDTO;
import kopo.poly.service.IBookService;
import kopo.poly.service.IMapService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
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
public class MapController {

    @Resource(name = "MapService")
    private IMapService mapService;
    @Resource(name = "BookService")
    private IBookService bookService;

    @RequestMapping(value = "/main/Mapinfo")
    public String getMapInfo(ModelMap model, HttpSession session) {

        log.info(this.getClass().getName() + ".BookInfo");

        try {
            String Userid = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

            log.info("SS_USER_ID : " + Userid);

            BookDTO pDTO = new BookDTO();
            pDTO.setUser_id(Userid);

            BookDTO rDTO = bookService.getBookInfo(pDTO);

            log.info("getBookInfo success!!");

            model.addAttribute("rDTO", rDTO);
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            log.info(this.getClass().getName() + ".getBookInfo END!!");
        }

        log.info(this.getClass().getName() + ".BookResult End!!");


        return "/main/Mapinfo";

    }

    @GetMapping(value = "/Mapinfo")
    @ResponseBody
    public List<HashMap<String, Object>> getMap(ModelMap model, HttpServletRequest request) throws Exception {

        String mCome = CmmUtil.nvl(request.getParameter("mCome"));
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553077/api/open/sdsc2/storeZoneInAdmi"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=%2BuGsicbaOj9jclJC6XTEzqSVmjkoXlPObuFuetBOAmiqsOR6KDxhKSFeP2988YGj0bX57AJaE2PHb%2F1xzxJ4ow%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("divId","UTF-8") + "=" + URLEncoder.encode("signguCd", "UTF-8")); /*시도는 ctprvnCd, 시군구는 signguCd, 행정동은 adongCd를 사용*/
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode(mCome, "UTF-8")); /*시도는 시도코드값, 시군구는 시군구코드값, 행정동은 행정동코드값을 사용*/
        //urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*xml / json*/

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

            String code = getTagValue("mainTrarNm", element);
            String name = getTagValue("signguNm", element);
            log.info("코드 : " + getTagValue("mainTrarNm", element));
            log.info("이름 : " + getTagValue("signguNm", element));

            HashMap<String, Object> tagMap = new HashMap<>();
            tagMap.put("mainTrarNm", code);
            tagMap.put("signguNm", name);

            tagList.add(tagMap);

            tagMap = null;
        }

        return tagList;
    }
    public String getTagValue(String tag, Element element) {

        String result = "";

        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();

        result = nodeList.item(0).getTextContent();

        return result;

    }
}
