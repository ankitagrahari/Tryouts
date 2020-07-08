package com.leetcode;


import java.util.*;

/**
 * Your crawler should:
 *
 * Start from the page: startUrl
 * Call HtmlParser.getUrls(url) to get all urls from a webpage of given url.
 * Do not crawl the same link twice.
 * Explore only the links that are under the same hostname as startUrl.
 * Input:
 * urls = [
 *   "http://news.yahoo.com",
 *   "http://news.yahoo.com/news",
 *   "http://news.yahoo.com/news/topics/",
 *   "http://news.google.com",
 *   "http://news.yahoo.com/us"
 * ]
 * edges = [[2,0],[2,1],[3,2],[3,1],[0,4]]
 * startUrl = "http://news.yahoo.com/news/topics/"
 * Output: [
 *   "http://news.yahoo.com",
 *   "http://news.yahoo.com/news",
 *   "http://news.yahoo.com/news/topics/",
 *   "http://news.yahoo.com/us"
 * ]
 */
interface HtmlParser{
    // Return a list of all urls from a webpage of given url.
    // This is a blocking call, that means it will do HTTP request and return when this request is finished.
    List<String> getUrls(String url, String[] urls);
}

public class WebCrawler implements HtmlParser{

    Map<Integer, Node> nodes = new HashMap<>();

    @Override
    public List<String> getUrls(String url, String[] urls) {
        List<String> outputUrls = new ArrayList<>();
        int startId = 0;
        for(int i=0; i<urls.length; i++){
            if(url.equalsIgnoreCase(urls[i])){
                startId = i;
                break;
            }
        }
        Node node = nodes.get(startId);
        for(int i=0; i<nodes.size(); i++) {
            while (node.getTo() != null) {
                node = node.getTo();
                outputUrls.add(node.getUrl());
            }
        }
        return outputUrls;
    }

    public void createGraph(int[][] edges, String[] urls){

        for(int i=0; i<urls.length; i++){
            nodes.put(i, new Node(urls[i], i));
        }

        for(int i=0; i<edges.length; i++){
            Node from = nodes.get(edges[i][0]);
            Node to = nodes.get(edges[i][1]);
            System.out.println(from.getId()+":"+from.getUrl()+"-->"+to.getId()+":"+to.getUrl());
            from.setTo(to);
        }
    }

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        String[] urls = {"http://news.yahoo.com",
                "http://news.yahoo.com/news",
                "http://news.yahoo.com/news/topics/",
                "http://news.google.com",
                "http://news.yahoo.com/us"};

        int[][] edges = {{2,0},
                        {2, 1},
                        {3, 2},
                        {3, 1},
                        {0, 4}};
        String startUrl = "http://news.yahoo.com/news/topics/";
        crawler.createGraph(edges, urls);

        crawler.nodes.forEach((k, v) -> System.out.println(k+":"+v.getId()+"-"+v.getUrl()+"-:"+ (null!=v.getTo() ? v.getTo().getId(): "")));
        System.out.println(crawler.getUrls(startUrl, urls));
    }
}

class Node{
    private String url;
    private int id;
    private Node to;

    Node(String url, int id){
        this.url = url;
        this.id = id;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
