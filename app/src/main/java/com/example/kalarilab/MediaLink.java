package com.example.kalarilab;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

 public class MediaLink {
        private String link ;
        private boolean isChanged = false;
        private String media_id =  UUID.randomUUID().toString();

        public  String getLink() {
            return link;
        }

        public void setLink(String link)
        {
          this.link = link;
        }

        public void changeLink(){
            isChanged = true;
        }
        public boolean changed(){
            return isChanged;
        }
}
