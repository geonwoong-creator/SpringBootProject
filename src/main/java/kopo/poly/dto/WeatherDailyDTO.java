package kopo.poly.dto;

import lombok.Data;

@Data
public class WeatherDailyDTO {

    private String day;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;
    private String dayTemp;
    private String dayTempMax;
    private String dayTempMin;
}
