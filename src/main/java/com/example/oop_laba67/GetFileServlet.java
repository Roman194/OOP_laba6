package com.example.oop_laba67;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/getfile")
public class GetFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String filename = "/WEB-INF/file";

        ServletContext context = getServletContext();

        InputStream inputStream = context.getResourceAsStream(filename);

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            PrintWriter printWriter = resp.getWriter();

            String text;

            List<Integer> weatherByDays = new ArrayList<>();

            while(true){
                text = bufferedReader.readLine();
                if(text == null)
                    break;
                weatherByDays.add(Integer.parseInt(text));
            }

            int weatherSumm = 0;
            for(int i: weatherByDays)
                weatherSumm+=i;

            double averageTemp = weatherSumm / weatherByDays.size();
            int belowAvgCount = 0;
            int higherAvgCount = 0;
            Integer[] threeHottestDays = new Integer[]{0, 0, 0};

            for(int j = 0; j < weatherByDays.size(); j++){
                int currentDay = weatherByDays.get(j);
                if(currentDay > averageTemp) {
                    higherAvgCount++;
                    if(currentDay > threeHottestDays[0]){ // optimize!
                        threeHottestDays[2] = threeHottestDays[1];
                        threeHottestDays[1] = threeHottestDays[0];
                        threeHottestDays[0] = currentDay;
                    } else if (currentDay > threeHottestDays[1]) {
                        threeHottestDays[2] = threeHottestDays[1];
                        threeHottestDays[1] = currentDay;
                    } else if (currentDay > threeHottestDays[2]) {
                        threeHottestDays[2] = currentDay;
                    }
                }
                else if (currentDay < averageTemp) {
                    belowAvgCount++;
                }
            }
            printWriter.println("Average temperature: " + averageTemp + "</br>");
            printWriter.println("Days with temperature below average count: " + belowAvgCount + "</br>");
            printWriter.println("Days with temperature higher average count: " + higherAvgCount + "</br>");
            printWriter.println("3 hottest days are: </br>");

            for(int i = 0; i < weatherByDays.size(); i++){
                int currentDay = weatherByDays.get(i);
                if(currentDay == threeHottestDays[0])
                    printWriter.println("day " + i + " : " + threeHottestDays[0] + "</br>");
                else if (currentDay == threeHottestDays[1]) {
                    printWriter.println("day " + i + " : " + threeHottestDays[1] + "</br>");
                } else if (currentDay == threeHottestDays[2]) {
                    printWriter.println("day " + i + " : " + threeHottestDays[2] + "</br>");
                }
            }

            printWriter.close();
        }

    }
}
