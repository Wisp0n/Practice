package festival;
public class Step03 {
    public static void main(String[] args) {
        var rows=java.util.List.of(new RankedMovie(SampleData.create().findById(1),8));
        var bytes=new java.io.ByteArrayOutputStream();
        var original=System.out;
        try {
            System.setOut(new java.io.PrintStream(bytes,true,java.nio.charset.StandardCharsets.UTF_8));
            new FestivalReport().print(rows,"Средняя оценка");
            new FestivalReport().print(java.util.List.of(),"Лучшая оценка");
        } finally {System.setOut(original);}
        String text=bytes.toString(java.nio.charset.StandardCharsets.UTF_8);
        for(String expected:java.util.List.of("Правило: Средняя оценка","1 | Северный свет | 8.00","Правило: Лучшая оценка","Пока нет оценённых фильмов","Фильмы без оценок не участвуют в рейтинге"))
            if(!text.contains(expected))throw new AssertionError("Нет строки: "+expected);
        System.out.print(text);
        System.out.println("Step03 OK");
    }
}
