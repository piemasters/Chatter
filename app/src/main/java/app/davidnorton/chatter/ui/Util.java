package app.davidnorton.chatter.ui;

public class Util {

    public static String getMessageNode(String sendingUid, String receivingUid) {
        String node = "";

        int result = sendingUid.compareTo(receivingUid);
        if(result == 0) {

        }
        else if (result < 0) {
            node = sendingUid + "-" + receivingUid;
        }
        else if(result > 0) {
            node = receivingUid + "-" +sendingUid  ;
        }

        return node;
    }
}
