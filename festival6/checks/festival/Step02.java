package festival;
public class Step02 { public static void main(String[] args) throws Exception {
var c=SampleData.create();var a=new RankingService(new AverageRatingStrategy()).rank(c.getMovies());
 if(!a.stream().map(r->r.movie().getId()).toList().equals(java.util.List.of(1,3,4,2,5)))throw new AssertionError("Порядок");
 var custom=new RankingService(new RankingStrategy(){public String name(){return "id";}public double score(Movie m){return m.getId();}});
 if(custom.rank(c.getMovies()).get(0).movie().getId()!=5)throw new AssertionError("Стратегия не вызвана");
System.out.println("Step02 OK");
}}
