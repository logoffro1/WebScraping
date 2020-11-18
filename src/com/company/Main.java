package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main
{

    public static void main(String[] args)
    {
      //  getCoronaCases("Netherlands");
        // getITILAnswers();
        // getMeme();
        //  getJoke();
    }

    private static void getJoke()
    {

        String URL = String.format("https://www.readersdigest.ca/culture/10-short-jokes-anyone-can-remember/");
        try
        {
            final Document document = Jsoup.connect(URL).get();
            Elements divs = document.select("div.card-content");
            for (int i = 0; i < divs.size(); i++)
            {
                Element joke = divs.get(i).selectFirst("h2");
                Element punchline = divs.get(i).selectFirst("p");
                if (punchline.text().isEmpty())
                    punchline = divs.get(i).select("p").get(1);
                System.out.println(joke.text());
                System.out.println(punchline.text());
                System.out.println();
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void getMeme()
    {
        final String URL_START = "https://www.reddit.com/r/memes/comments/";
        List<String> urls = new ArrayList<>();
        String URL = String.format("https://www.reddit.com/r/memes/");
        try
        {
            final Document document = Jsoup.connect(URL).get();
            Elements divs = document.select("div.SubredditVars-r-memes");
            Element answer = document.selectFirst("span");
            System.out.println(document.html().length());
            int count = 0;
            String url = "";
            for (int i = 0; i < 20000; i++)
            {

                if (document.html().charAt(i) == URL_START.charAt(count))
                {
                    url += document.html().charAt(i);
                    count++;
                    if (count >= URL_START.length())
                    {
                        urls.add(url);
                        url = "";
                        count = 0;
                    }
                } else
                {
                    count = 0;
                }
            }
            System.out.println(urls.size());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void getCoronaCases(String findCountry)
    {
        final String[] columnNames = {"#", "Country", "Total Cases", "New Cases"};
        final String URL = "https://www.worldometers.info/coronavirus";

        try
        {
            final Document document = Jsoup.connect(URL).get();
            outterloop:
            for (Element row : document.select("table.main_table_countries tr"))
            {
                final String country = row.select("td:nth-of-type(2)").text();
                if (country.equals("")) continue;

                if (country.toLowerCase().equals(findCountry.toLowerCase()))
                {
                    for (int i = 1; i <= 4; i++)
                    {
                        String columnData = row.select(String.format("td:nth-of-type(%d)", i)).text();
                        if (columnData.equals("")) continue outterloop;

                        System.out.printf(" %s: %s |", columnNames[i - 1], columnData);
                    }
                    System.out.println();

                    break;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void getITILAnswers()
    {


        Path path = Paths.get("ITSM answers.txt");

        for (int i = 1; i < 549; i++)
        {
            String URL = String.format("https://itilexamtest.com/itil-foundation-exam-test-q%d/", i);
            try
            {
                final Document document = Jsoup.connect(URL).get();
                Elements question = document.select("p");
                Element answer = document.selectFirst("span");


                String q = String.format("Q: %s\n", question.get(0).text());
                String a = String.format("Answer: %s\n", answer.text());

                System.out.println("Question: " + i);
                System.out.println(q);
                System.out.println(a);
                ///Files.write(path,q.getBytes(), StandardOpenOption.APPEND);
                //  Files.write(path,a.getBytes(), StandardOpenOption.APPEND);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Done!");


    }
}
