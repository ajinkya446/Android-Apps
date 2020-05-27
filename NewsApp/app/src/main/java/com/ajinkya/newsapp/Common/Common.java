package com.ajinkya.newsapp.Common;

import com.ajinkya.newsapp.Interface.IconBetterIdeaService;
import com.ajinkya.newsapp.Interface.NewsService;
import com.ajinkya.newsapp.Remote.IconBetterIdeaClient;
import com.ajinkya.newsapp.Remote.RetrofitClient;

/**
 * Created by reale on 10/4/2017.
 */

public class Common {
    private static final String BASE_URL="https://newsapi.org/";

    public  static final String API_KEY="bd48bfc0bde842fe942ff42b4001d60c"; // bd48bfc0bde842fe942ff42b4001d60c

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    //https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=bd48bfc0bde842fe942ff42b4001d60c
    public static String getAPIUrl(String source,String sortBy,String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }


}
