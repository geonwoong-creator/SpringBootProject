package kopo.poly.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.WeatherDTO;
import kopo.poly.dto.WeatherDailyDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("WeatherService")
public class WeatherService implements IWeatherService {

    @Value("${weather.api.key}")
    private String apiKey;


@Override
public WeatherDTO getWeather(WeatherDTO pDTO) throws Exception {

    log.info(this.getClass().getName() + "getWeather Start!");

    String lat = CmmUtil.nvl(pDTO.getLat());
    String lon = CmmUtil.nvl(pDTO.getLon());

    String apiParam = "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
    log.info("apiparam" + apiParam);

    String json = NetworkUtil.get(IWeatherService.apiURL + apiParam);
    log.info("json" + json);

    // json구조
    Map<String, Object> rMap = new ObjectMapper().readValue(json, LinkedHashMap.class);

    //현제 날씨
    Map<String, Double> current = (Map<String, Double>) rMap.get("current");

    double currentTemp = current.get("temp"); //현제기온
    log.info("현제기온 :" + currentTemp);

    //일별 날씨 조회
    List<Map<String, Object>> dailyList = (List<Map<String, Object>>) rMap.get("daily");

    //7일동안 날씨 정보 저장
    List<WeatherDailyDTO> pList = new LinkedList<>();

    for (Map<String, Object> dailyMap : dailyList) {

        String day = DateUtil.getLongDateTime(dailyMap.get("dt"), "yyyy-MM-dd"); //기준날짜
        String sunrise = DateUtil.getLongDateTime(dailyMap.get("sunrise")); //해뜨는시간
        String sunset = DateUtil.getLongDateTime(dailyMap.get("sunset"));
        String moonrise = DateUtil.getLongDateTime(dailyMap.get("moonrise"));
        String moonset = DateUtil.getLongDateTime(dailyMap.get("moonset"));

        log.info("----------");
        log.info("today" + day);


        Map<String, Double> dailyTemp = (Map<String, Double>) dailyMap.get("temp");

        String dayTemp = String.valueOf(dailyTemp.get("day"));
        String dayTempMax = String.valueOf(dailyTemp.get("max"));
        String dayTempMin = String.valueOf(dailyTemp.get("min"));

        WeatherDailyDTO wdDTO = new WeatherDailyDTO();

        wdDTO.setDay(day);
        wdDTO.setSunrise(sunrise);
        wdDTO.setSunset(sunset);
        wdDTO.setMoonrise(moonrise);
        wdDTO.setMoonset(moonset);
        wdDTO.setDayTemp(dayTemp);
        wdDTO.setDayTempMax(dayTempMax);
        wdDTO.setDayTempMin(dayTempMin);

        pList.add(wdDTO);

        wdDTO = null;
    }
    WeatherDTO rDTO = new WeatherDTO();

    rDTO.setLat(lat);
    rDTO.setLon(lon);
    rDTO.setCurrentTemp(currentTemp);
    rDTO.setDailyList(pList);

    log.info(this.getClass().getName() + ".weather end!");

    return rDTO;
}
}

