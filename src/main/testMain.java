public class testMain {
    public static void main(String[] args){
       String str ="miui_voiceassist";
       AppType appType = AppType.parseFromString(str);
       System.out.println("appType:"+ appType);
       System.out.println("equal:"+ AppType.VOICE_ASSIST.equals(appType));
       System.out.println("equal:"+ (AppType.MIUI_PAD == appType));
       switch (appType){
           case VOICE_ASSIST:
               System.out.println("switch voice_assist");
               break;
           case MIUI_PAD:
               System.out.println("switch pad");
               break;
       }
       System.out.println(" toString:"+appType.toString());
       System.out.println(" getName:"+appType.getName());
    }
}
