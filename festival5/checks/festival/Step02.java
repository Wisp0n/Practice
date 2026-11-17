package festival;
public class Step02 { public static void main(String[] args) throws Exception {
var c=SampleData.create(); var before=c.getMovies(); var r=c.sortedByDuration();
 if(!r.stream().map(Movie::getId).toList().equals(java.util.List.of(1,5,6,3,2,4)))throw new AssertionError("Порядок");
 r.clear();if(!c.getMovies().equals(before))throw new AssertionError("Изменён каталог");
System.out.println("Step02 OK");
}}
