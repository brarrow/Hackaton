package androidapp.hackaton.hackaton;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostPictureTest {
    @Test
    public void show(){
        PostPicture postPicture = new PostPicture();
        assertEquals(postPicture.doInBackground(new Void[1]), 4);
    }

}
