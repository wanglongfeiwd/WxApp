package com.yhxx.wxapp.domain.creator;

import com.yhxx.wxapp.domain.MusicTypeMsg;

import java.util.Map; /**
 * @Author: Wanglf
 * @Date: Created in 23:11 2018/5/2
 * @modified By:
 */
public class MusicTypeMsgCreator {
    public static MusicTypeMsg create(Map<String, String> wxContent) {
        MusicTypeMsg musicTypeMsg = new MusicTypeMsg();
        String ToUserName = wxContent.get("FromUserName");
        String FromUserName = wxContent.get("ToUserName");
        musicTypeMsg.setFromUserName(FromUserName);
        musicTypeMsg.setToUserName(ToUserName);
        musicTypeMsg.setCreateTime((int) (System.currentTimeMillis()/1000));
        musicTypeMsg.setMsgType("music");
        MusicTypeMsg.Music music = new MusicTypeMsg.Music();
        music.setDescription("英语歌");
        music.setHQMusicUrl("");
        music.setMusicUrl("");
        music.setThumbMediaId("");
        music.setTitle("my world");
        musicTypeMsg.setMusic(music);
        return musicTypeMsg;
    }
}
