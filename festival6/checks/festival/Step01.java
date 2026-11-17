package festival;
public class Step01 { public static void main(String[] args) throws Exception {
var s=new BestRatingStrategy();var c=SampleData.create();
 if(s.score(c.findById(2))!=10||s.score(c.findById(1))!=8)throw new AssertionError("Максимум");
System.out.println("Step01 OK");
}}
