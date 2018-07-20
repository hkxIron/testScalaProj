import org.apache.commons.lang3.StringUtils;
public enum AppType {
    // 语音助手
    VOICE_ASSIST("miui_voiceassist"),
    // 音箱
    SOUND_BOX("sound_box"),
    // tv
    TV("tv"),
    // pad
    MIUI_PAD("pad_miui_voiceassist"),
    // 其它
    OTHER("other");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    AppType(String name) {
        this.name = name;
    }

    public static AppType parseFromString(String str){
        if (StringUtils.isEmpty(str)) return OTHER;
        String strUpper = str.toUpperCase();
        if (str.contains("pad_miui_voiceassist")) {
            return MIUI_PAD;
        } else if (strUpper.contains("MIAI")||
                str.equals("miui_voiceassist_test")||
                str.equals("miui_voiceassist")) {
            return VOICE_ASSIST;
        } else if (strUpper.contains("SOUNDBOX")) {
            return SOUND_BOX;
        } else if (strUpper.contains("TV")) {
            return TV;
        } else {
            return OTHER;
        }
    }
}

