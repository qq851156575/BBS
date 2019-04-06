package com.piu.software.entity;

import com.piu.base.DataEntity;

public class FileInterface extends DataEntity<FileInterface> {
    private static final long serialVersionUID = 1L;
private Download download;
    private String path;
   public FileInterface(){

   }

   public FileInterface(Download download){
       this.download=download;
   }
    public String getPath() {
        return path;
    }
    public Download getDownload() {
        return download;
    }

    public void setDownload(Download download) {
        this.download = download;
    }

    public void setPath(String path) {
        this.path = path;
    }


    
}
