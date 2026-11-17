package festival;
public class Step03 { public static void main(String[] args) throws Exception {
var m=SampleData.create().findById(1);var before=m.getRatings();
 for(int v:new int[]{0,11,Integer.MIN_VALUE,Integer.MAX_VALUE}){
 try{m.addRating(v);throw new AssertionError("Принята недопустимая оценка");}
 catch(InvalidRatingException e){if(!m.getRatings().equals(before))throw new AssertionError("Состояние изменилось");}}
 m.addRating(1);m.addRating(10);if(m.getRatings().size()!=4)throw new AssertionError("Границы");
System.out.println("Step03 OK");
}}
