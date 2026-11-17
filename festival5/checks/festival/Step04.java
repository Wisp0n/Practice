package festival;
public class Step04 { public static void main(String[] args) throws Exception {
var c=SampleData.create();var bytes=new java.io.ByteArrayOutputStream();
 var old=System.out;try{System.setOut(new java.io.PrintStream(bytes,true,java.nio.charset.StandardCharsets.UTF_8));
 Main.handleRating(c,1,11);}finally{System.setOut(old);}
 if(!bytes.toString(java.nio.charset.StandardCharsets.UTF_8).contains("Оценка отклонена:"))throw new AssertionError("Нет сообщения");
System.out.println("Step04 OK");
}}
