package festival;
public class Step01 { public static void main(String[] args) throws Exception {
var c=SampleData.create(); var r=c.searchByTitle("  СЕВЕРНЫЙ  ");
 if(r.size()!=1||r.get(0).getId()!=1)throw new AssertionError("Поиск");
 r.clear(); if(c.getMovies().size()!=6)throw new AssertionError("Список общий");
 if(c.searchByTitle(" ").size()!=6||!c.searchByTitle("zzz").isEmpty())throw new AssertionError("Пустой запрос");
System.out.println("Step01 OK");
}}
