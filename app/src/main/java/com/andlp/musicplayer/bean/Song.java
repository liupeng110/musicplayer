package com.andlp.musicplayer.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name="song")
public class Song {
     //服务器保存数据
    @Column(name="name",isId = true) private String  name="";        //歌曲名
    @Column(name="channel")               private String  channel="";    //qq或者酷狗 (防止算法不可用导致歌曲不可恢复)
    @Column(name="quality")                 private String  quality="";      //音质

    //本地保存数据(需要json过滤)
    @Column(name="path")                   private String  path="";          //歌曲本地路径(防止网址变更,只存本地路径)
    @Column(name="lrc")                      private String  lrc ="";            //lrc歌词路径
    @Column(name="lrc_offset")           private String  lrc_offset="";  //lrc偏移量
    @Column(name="lrc2")                    private String  lrc2="";           //动态歌词路径
    @Column(name="lrc2_offset")         private String  lrc2_offset="";//动态歌词偏移量

    public String getName() {  return name; }
    public void setName(String name) { this.name = name;  }
    public String getChannel() { return channel; }
    public void setChannel(String channel) {  this.channel = channel;  }
    public String getQuality() { return quality; }
    public void setQuality(String quality) { this.quality = quality;  }
    public String getPath() {  return path; }
    public void setPath(String path) { this.path = path;  }
    public String getLrc() { return lrc;  }
    public void setLrc(String lrc) { this.lrc = lrc;  }
    public String getLrc_offset() { return lrc_offset; }
    public void setLrc_offset(String lrc_offset) { this.lrc_offset = lrc_offset; }
    public String getLrc2() {  return lrc2;  }
    public void setLrc2(String lrc2) { this.lrc2 = lrc2; }
    public String getLrc2_offset() { return lrc2_offset; }
    public void setLrc2_offset(String lrc2_offset) { this.lrc2_offset = lrc2_offset;  }

}
